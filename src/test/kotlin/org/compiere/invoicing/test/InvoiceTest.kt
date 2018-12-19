package org.compiere.invoicing.test

import company.bigger.test.support.asResource
import company.bigger.test.support.randomString
import org.compiere.accounting.*
import org.compiere.accounting.MProduct
import org.compiere.crm.*
import org.compiere.invoicing.MInOut
import org.compiere.invoicing.MInOutLine
import org.compiere.invoicing.MInvoice
import org.compiere.model.*
import org.compiere.order.X_M_InOut
import org.compiere.orm.DefaultModelFactory
import org.compiere.orm.IModelFactory
import org.compiere.orm.MDocType
import org.compiere.process.DocAction
import org.compiere.process.ProcessInfo
import org.compiere.product.*
import org.compiere.production.MLocator
import org.compiere.production.MProduction
import org.idempiere.common.util.Env
import org.idempiere.process.ProductionCreate
import org.junit.Before
import org.junit.Test
import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.math.BigDecimal
import java.sql.Date
import java.sql.Timestamp
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail

data class InvoiceImportantTestAttributes(
    val grandTotal: BigDecimal,
    val grandTotalVAT: BigDecimal,
    val reverseCharge: Boolean,
    val dueDate1: Date,
    val dueDate2: Date
)

data class MaterialMovementImportantTestAttributes(
    val productName: String,
    val moveDate: Date?,
    val amountIn: BigDecimal,
    val amountOut: BigDecimal
)

class InvoiceTest: BaseComponentTest() {
    companion object {
        const val QTY = 1
        private var index = 0
        const val MAT = "Mat1-"
        const val BOM = "BOM1-"
    }

    private var _testProduct: I_M_Product? = null
    private val testProduct get() = _testProduct!!
    private var _salesPriceList: I_M_PriceList? = null
    private val salesPriceList get() = _salesPriceList!!
    private var _now: Timestamp? = null
    private val now get() = _now!!

    @Before
    fun createProdAndReceipt() {
        DB.run {
            _now = Timestamp(System.currentTimeMillis())
            val always = Timestamp(0)

            fun createSalesPriceList(): MPriceList {
                val salesPriceList = MPriceList(ctx, 0, null)
                salesPriceList.setIsDefault(true)
                salesPriceList.setIsSOPriceList(true)
                salesPriceList.name = "S-" + randomString(10)
                salesPriceList.c_Currency_ID = EUR
                salesPriceList.save()
                val check1: MPriceList = getById(salesPriceList.id, I_M_PriceList.Table_Name)
                assertNotNull(check1)

                return salesPriceList
            }

            // make sure the pricelist is there
            _salesPriceList = MPriceList.getDefault(ctx, true) ?: createSalesPriceList()

            fun createSalesPriceListVersion(): MPriceListVersion {
                val priceListVersion = MPriceListVersion(salesPriceList)
                priceListVersion.name = salesPriceList.name
                priceListVersion.validFrom = always
                priceListVersion.m_DiscountSchema_ID = MDiscountSchema(ctx, 1000000, null).id
                priceListVersion.save()

                val check2: MPriceListVersion = getById(priceListVersion.id, I_M_PriceList_Version.Table_Name)
                assertNotNull(check2)

                return priceListVersion
            }

            // get the product on hand
            val product: MProduct = createAProduct(MAT + randomString(5), I_M_Product.PRODUCTTYPE_Item) as MProduct
            _testProduct = product

            // put the product on the pricelist
            val currentPriceListVersion = salesPriceList.getPriceListVersion(now) ?: createSalesPriceListVersion()
            val price = 1.toBigDecimal()
            val productPrice = MProductPrice(currentPriceListVersion, product.id, price, price, price)
            productPrice.save()

            val vendor = createBPartner()

            val vendorShipment = MInOut(ctx, 0, null)
            vendorShipment.setAD_Org_ID(org.orgId)
            vendorShipment.setIsSOTrx(false)
            vendorShipment.movementType = X_M_InOut.MOVEMENTTYPE_VendorReceipts
            vendorShipment.setC_DocType_ID()
            vendorShipment.c_BPartner_ID = vendor.id
            vendorShipment.c_BPartner_Location_ID = vendor.locations.first().c_BPartner_Location_ID
            vendorShipment.m_Warehouse_ID = warehouse.m_Warehouse_ID
            vendorShipment.save()
            val receipt = getById<MInOut>(vendorShipment.id, I_M_InOut.Table_Name)
            assertNotNull(receipt)
            val receiptLine = MInOutLine(receipt)
            receiptLine.setAD_Org_ID(org.orgId)
            receiptLine.product = product
            receiptLine.movementQty = 1000000.toBigDecimal()
            receiptLine.m_Locator_ID = MLocator(ctx, MLocator(ctx, 1000000, null).id, null).id
            receiptLine.save()
            val line = getById<MInOutLine>(receiptLine.id, I_M_InOutLine.Table_Name)
            assertNotNull(line)
            vendorShipment.setDocAction(DocAction.STATUS_Completed)
            vendorShipment.completeIt()
            val storageOnHand = MStorageOnHand.getOfProduct(ctx, product.id, null).first()
            assertEquals(0.0.toBigDecimal(), receiptLine.movementQty - storageOnHand.qtyOnHand)
        }
    }

    private fun createBPartner(): I_C_BPartner {
        val newPartner = MBPartner.getTemplate(ctx, AD_CLIENT_ID)
        val name = "Test " + randomString(10)
        newPartner.setName(name)
        val value = "t-" + randomString(5)
        newPartner.setValue(value)
        newPartner.save()

        val defaultCountry = MCountry.getDefault(ctx)
        val defaultRegion = MRegion.getDefault(ctx)
        val location = MLocation(defaultCountry, defaultRegion)
        location.save()
        val partnerLocation = MBPartnerLocation(newPartner)
        partnerLocation.c_Location_ID = location.c_Location_ID
        partnerLocation.save()

        return newPartner
    }

    @Test
    fun `get invoice by id`() {
        DB.run {
            loginClient(11)
            val invoice_id = 106

            val invoice: MInvoice = getById(invoice_id, I_C_Invoice.Table_Name)
            assertNotNull(invoice)
            assertEquals(invoice_id, invoice.id)
            val lines = invoice.lines
            assertNotNull(lines)
            assertEquals(6, lines.count())
        }
    }

    private fun createOrder(c_DocType_ID: Int, product_id: Int): Triple<MOrder, Int, Int> {
        val order = MOrder(Env.getCtx(), 0, null)
        order.setAD_Org_ID(1000000)
        order.m_Warehouse_ID = 1000000
        order.setIsSOTrx(true)
        order.c_DocType_ID = c_DocType_ID // 133 on credit order (generates invoice), 130 prepay order, 132 standard order
        order.c_DocTypeTarget_ID = c_DocType_ID
        order.m_PriceList_ID = MPriceList.getDefault(ctx, true).id
        order.setC_Currency_ID(EUR) // EUR
        order.c_PaymentTerm_ID = paymentTerm.id

        val partner = createBPartner()

        val product = MProduct.get(Env.getCtx(), product_id)

        order.setBPartner(partner)
        order.save()

        val orderLine = MOrderLine(order)
        orderLine.product = product
        orderLine.c_Tax_ID = tax.c_Tax_ID
        val qty = QTY
        orderLine.setQty(qty.toBigDecimal())
        orderLine.save()

        return Triple(order, partner.id, product_id)
    }

    @Test
    fun `create invoice from prepay order after receiving the payment`() {
        DB.run {
            createInvoiceFromOrder(1000030, testProduct.id, BigDecimal("1.10")) {
                val payment = MPayment(ctx, 0, null)
                payment.c_BPartner_ID = it.c_BPartner_ID
                payment.setAD_Org_ID(org.orgId)
                payment.c_BankAccount_ID = bankAccount.id
                payment.setC_Currency_ID(EUR) // EUR
                payment.payAmt = 1.10.toBigDecimal()
                payment.save()

                val pay: MPayment = getById(payment.id, I_C_Payment.Table_Name)
                assertNotNull(pay)

                it.c_Payment_ID = pay.id
                it.save()
            }
        }
    }

    private fun createInvoiceFromOrder(c_DocType_ID: Int, productId: Int, expectedPrice: BigDecimal, doAfterOrderTask: (MOrder) -> Unit) {
        val (order, id, product_id) = createOrder(c_DocType_ID, productId)
        doAfterOrderTask(order)

        order.setDocAction(DocAction.STATUS_Completed)
        val completion = order.completeIt()
        order.save()

        val invoice = completion.result as I_C_Invoice
        assertEquals(1, order.invoices.count())
        assertEquals(invoice, order.invoices.first())
        assertEquals(id, invoice.c_BPartner_ID)
        val lines = invoice.getLines(false)
        assertEquals(1, lines.count())
        val line = lines.first()
        assertEquals(product_id, line.m_Product_ID)
        assertEquals(QTY.toBigDecimal(), line.qtyInvoiced)

        val modelFactory: IModelFactory = DefaultModelFactory()
        val result = modelFactory.getPO(I_C_Invoice.Table_Name, invoice.id, null)
        println(result)
        assertNotNull(result)
        val invoice1 = result as MInvoice
        assertNotNull(invoice1)
        assertEquals(invoice.id, invoice1.id)
        assertEquals(expectedPrice, invoice1.getGrandTotal(false))
        val lines1 = invoice1.lines
        assertNotNull(lines1)
        assertEquals(1, lines1.count())

        "/sql/invoice_details.sql".asResource {
            val sql = it.replace("\$P{RECORD_ID}", invoice1.id.toString())
            val loadQuery =
                queryOf(sql,listOf())
                .map { row ->
                    InvoiceImportantTestAttributes(
                        row.bigDecimal("grandtotal"), row.bigDecimal("grandtotalvat"),
                        row.boolean("reverse_charge"), row.sqlDate("due_previous_business_day"),
                        row.sqlDate("due_previous_5business_days")
                    )
                }.asList
            val list = DB.current.run(loadQuery)
            assertEquals(1, list.count())
            val details = list.first()
            // TODO: fix accounting and then - assertFalse(details.reverseCharge)
            assertEquals(expectedPrice, details.grandTotal)
            assertEquals(expectedPrice - (expectedPrice / 1.10.toBigDecimal()), details.grandTotalVAT)
            val now = java.util.Date()
            assertTrue(details.dueDate1 < now)
            assertTrue(details.dueDate2 < now)
        }
        index++
        `should have specific no of material movements after all runs`()
    }

    @Test
    fun `create invoice from order (on credit)`() {
        DB.run {
            val salesOrderTypes = MDocType.getOfDocBaseType(ctx, MDocType.DOCBASETYPE_SalesOrder)
            val onCreditOrder = salesOrderTypes.first { it.name == "Credit Order" }
            createInvoiceFromOrder(onCreditOrder.id, testProduct.id, BigDecimal("1.10")) {}
        }
    }

    @Test
    fun `create invoice from BOM order with production step in between (on credit)`() {
        DB.run {
            val bomProduct = createAProduct(BOM + randomString(5), I_M_Product.PRODUCTTYPE_Item) as MProduct
            bomProduct.setIsBOM(true)
            bomProduct.save()
            val innerProduct = MProductBOM(ctx, 0, null)
            innerProduct.bomQty = 10.toBigDecimal()
            innerProduct.m_ProductBOM_ID = testProduct.id
            innerProduct.m_Product_ID = bomProduct.id
            innerProduct.line = 10
            innerProduct.save()

            // put the product on the pricelist
            val currentPriceListVersion = salesPriceList.getPriceListVersion(now)!!
            val price = 11.0.toBigDecimal()
            val productPrice = MProductPrice(currentPriceListVersion, bomProduct.id, price, price, price)
            productPrice.save()

            createInvoiceFromOrder(1000033, bomProduct.m_Product_ID, BigDecimal("12.10")) {
                val orderLine = it.lines.first()
                val production = MProduction(orderLine)
                production.setAD_Org_ID(1000000)
                production.m_Product_ID =
                        orderLine.m_Product_ID // TODO: Why? Should not this be done automatically in the constructor?
                production.productionQty =
                        orderLine.qtyOrdered // TODO: Why? Should not this be done automatically in the constructor?
                production.m_Locator_ID = 1000000
                production.save()

                val productionCreate = ProductionCreate(m_production = production)
                val pi = ProcessInfo("", 0)
                productionCreate.startProcess(ctx, pi)

                val prod: MProduction = getById(production.id, I_M_Production.Table_Name)

                prod.setDocAction(DocAction.STATUS_Completed)
                prod.save()

                prod.completeIt()
            }
        }
    }

    @Test
    fun `create invoice from order (on credit) without pricelist should fail`() {
        DB.run {
            val product = createAProduct("Other 1-" + randomString(5), I_M_Product.PRODUCTTYPE_Item)
            try {
                createInvoiceFromOrder(1000033, product.id, BigDecimal("1.10")) {}
                fail("Invoice was created for a product not on a pricelist")
            } catch (e: Exception) {
            }
        }
    }

    @Test
    fun `create invoice from order (on credit) without amount on hand should fail`() {
        DB.run {
            val product = createAProduct("Other 1-" + randomString(5), I_M_Product.PRODUCTTYPE_Item)
            val pl = MPriceList(ctx, 1000000, null)
            val plv = pl.getPriceListVersion(Timestamp.from(Instant.now()))!!
            val price = 10.toBigDecimal()
            val pp = MProductPrice(ctx, plv.id, product.id, price, price, price, null)
            pp.save()
            try {
                createInvoiceFromOrder(1000033, product.id, BigDecimal("11.00")) {}
                fail("Invoice was created for a product with negative inventory")
            } catch (e: Exception) {
            }
        }
    }

    fun `should have specific no of material movements after all runs`() {
        if (index == 3) {
            "/sql/recent_material_movements.sql".asResource {
                val loadQuery =
                    queryOf(it, listOf())
                    .map { row ->
                        MaterialMovementImportantTestAttributes(
                            row.string("pro_name"), row.sqlDate("move_date"),
                            row.bigDecimal("amout_in"), row.bigDecimal("amout_out")
                        )
                    }
                    .asList

                val list = DB.current.run(loadQuery)
                kotlin.test.assertEquals(11, list.count())
                val standards = list.filter { it.productName.startsWith(MAT) }
                val bom1s = list.filter { it.productName.startsWith(BOM) }
                kotlin.test.assertEquals(9, standards.count())
                kotlin.test.assertEquals(6 * 1000000 - 2 * 1 - 10, standards.sumBy { (it.amountIn - it.amountOut).toInt() })
                kotlin.test.assertEquals(2, bom1s.count())
                kotlin.test.assertEquals(1 - 1, bom1s.sumBy { (it.amountIn - it.amountOut).toInt() })
            }
        }
    }
}
