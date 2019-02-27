package org.compiere.invoicing.test

import company.bigger.test.support.randomString
import org.compiere.accounting.*
import org.compiere.bank.MBank
import org.compiere.bank.MBankAccount
import org.compiere.invoicing.MInventory
import org.compiere.invoicing.MInventoryLine
import org.compiere.invoicing.MPaymentTerm
import org.compiere.invoicing.test.SetupClientTests.Companion.createClient
import org.compiere.model.*
import org.compiere.order.MPaySchedule
import org.compiere.orm.*
import org.compiere.orm.MClient
import org.compiere.orm.MDocType
import org.compiere.process.DocAction
import org.compiere.product.MAttributeSetInstance
import org.compiere.product.MProduct
import org.compiere.product.MUOM
import org.compiere.tax.MTax
import org.compiere.tax.MTaxCategory
import org.idempiere.common.util.Env
import org.idempiere.icommon.model.IPO
import org.junit.Before
import org.slf4j.impl.SimpleLogger
import software.hsharp.core.util.DB
import software.hsharp.core.util.HikariCPI
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal val sessionUrl =
    System.getenv("SESSION_URL") ?: "jdbc:postgresql://localhost:5433/idempiere?autosave=conservative"

abstract class BaseComponentTest {
    companion object {
        val NEW_AD_CLIENT_ID = 1000000
        const val EUR = 102
    }

    init {
        System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "WARN")
        HikariCPI.default(sessionUrl, "adempiere", "adempiere")
    }

    protected fun loginClient(idClient: Int) {
        val ctx = Env.getCtx()
        val AD_CLIENT_ID = idClient
        val AD_CLIENT_ID_s = AD_CLIENT_ID.toString()
        ctx.setProperty(Env.AD_CLIENT_ID, AD_CLIENT_ID_s)
        Env.setContext(ctx, Env.AD_CLIENT_ID, AD_CLIENT_ID_s)
    }

    private var _taxCategory: I_C_TaxCategory? = null
    protected val taxCategory: I_C_TaxCategory get() = _taxCategory!!
    private var _tax: I_C_Tax? = null
    protected val tax: I_C_Tax get() = _tax!!

    protected val org: I_AD_Org
        get() = MOrg(ctx, 1000000)
    protected val warehouse: I_M_Warehouse
        get() = MWarehouse(ctx, 1000000)
    private var _paymentTerm: I_C_PaymentTerm? = null
    protected val paymentTerm: I_C_PaymentTerm get() = _paymentTerm!!
    private var _bankAccount: I_C_BankAccount? = null
    protected val bankAccount get() = _bankAccount!!
    private var _charge: I_C_Charge? = null
    protected val charge get() = _charge!!

    @Before
    fun prepareEnv() {
        DB.run {
            val query = Query(this.ctx, "AD_Client", "ad_client_id=$NEW_AD_CLIENT_ID")
            val result = query.list<MClient>()
            if (result.isEmpty()) {
                createClient(ctx) { loginClient(0) }
            }

            loginClient(NEW_AD_CLIENT_ID)
            if (_taxCategory == null) ensureTaxCategory10Pct()
            if (_paymentTerm == null) ensurePaymentTerm14Days()
            if (_bankAccount == null) ensureBankAccount()
            if (_charge == null) ensureCharge()
        }
    }

    private fun ensureBankAccount() {
        val newBank = MBank(ctx, 0)
        newBank.name = "B-" + randomString(10)
        newBank.routingNo = newBank.name
        newBank.save()
        val newBankAccount = MBankAccount(ctx, 0)
        newBankAccount.bankId = newBank.id
        newBankAccount.name = newBank.name
        newBankAccount.setSearchKey(newBankAccount.name)
        newBankAccount.currencyId = EUR
        newBankAccount.accountNo = newBank.name
        newBankAccount.save()
        _bankAccount = getById(newBankAccount.id, I_C_BankAccount.Table_Name)
        assertNotNull(_bankAccount)
    }

    private fun ensureCharge() {
        val newCharge = MCharge(ctx, 0)
        newCharge.name = "CH-" + randomString(5)
        newCharge.save()
        _charge = getById(newCharge.id, I_C_Charge.Table_Name)
    }

    private fun ensurePaymentTerm14Days() {
        val newPaymentTerm = MPaymentTerm(ctx, 0)
        newPaymentTerm.setIsValid(true)
        newPaymentTerm.name = "P-" + randomString(10)
        newPaymentTerm.setSearchKey(newPaymentTerm.name)
        newPaymentTerm.save()
        _paymentTerm = getById(newPaymentTerm.id, I_C_PaymentTerm.Table_Name)
        assertNotNull(_paymentTerm)
        val paySchedule = MPaySchedule(ctx, 0)
        paySchedule.paymentTermId = paymentTerm.id
        paySchedule.percentage = 100.toBigDecimal()
        paySchedule.save()
    }

    fun <T : IPO> getById(id: Int, tableName: String): T {
        val modelFactory: IModelFactory = DefaultModelFactory()
        val result = modelFactory.getPO(tableName, id)
        println(result)
        assertNotNull(result)
        @Suppress("UNCHECKED_CAST")
        val obj = result as T
        assertNotNull(obj)
        assertEquals(id, obj.id)
        return obj
    }

    val ctx get() = Env.getCtx()
    val AD_CLIENT_ID get() = ctx.getProperty(Env.AD_CLIENT_ID).toInt()

    protected fun getProductById(product_id: Int): I_M_Product {
        val modelFactory: IModelFactory = DefaultModelFactory()
        val result = modelFactory.getPO(I_M_Product.Table_Name, product_id)
        println(result)
        assertNotNull(result)
        val product = result as I_M_Product
        assertNotNull(product)
        assertEquals(product_id, product.id)
        return product
    }

    fun ensureTaxCategory10Pct() {
        val newCategory = MTaxCategory(ctx, 0)
        newCategory.name = "T-" + randomString(10)
        newCategory.save()
        _taxCategory = getById(newCategory.id, I_C_TaxCategory.Table_Name)
        assertNotNull(_taxCategory)
        val newTax = MTax(ctx, newCategory.name, 10.toBigDecimal(), taxCategory.id)
        newTax.setIsActive(true)
        newTax.setIsDefault(true)
        newTax.save()
        _tax = getById(newTax.id, I_C_Tax.Table_Name)
        assertNotNull(_tax)
    }

    fun createAProduct(name: String, productType: String): I_M_Product {
        val standardProduct = MProduct.get(ctx, "name = 'Standard'").first()
        val ctx = Env.getCtx()
        val product = MProduct(ctx, 0)
        product.name = name
        product.value = name
        product.c_UOM_ID = MUOM.getDefault_UOM_ID(ctx)
        product.m_Product_Category_ID = standardProduct.m_Product_Category_ID
        product.c_TaxCategory_ID = taxCategory.c_TaxCategory_ID
        product.productType = productType // I_M_Product.PRODUCTTYPE_Service
        product.save()

        val org = MOrg.getOfClient(product).first()
        val warehouse = MWarehouse.getForOrg(ctx, org.id).first()
        val attributeSetInstance = MAttributeSetInstance.get(ctx, 0, product.id)

        val inventory = MInventory(warehouse)
        inventory.documentTypeId = MDocType.getOfClient(ctx).first { it.docSubTypeInv == "PI" }.id
        inventory.save()

        val inventoryLine = MInventoryLine(
            inventory,
            warehouse.defaultLocator.id,
            product.id,
            attributeSetInstance.id,
            0.toBigDecimal(),
            0.toBigDecimal()
        )
        inventoryLine.chargeId = charge.id
        inventoryLine.qtyInternalUse = 0.toBigDecimal()
        inventoryLine.save()

        assertTrue(
            MCostDetail.createInventory(
                MAcctSchema.getClientAcctSchema(ctx, NEW_AD_CLIENT_ID).first(),
                org.id,
                product.id,
                attributeSetInstance.id,
                inventoryLine.id,
                MCostElement.getElements(ctx).first().id,
                // amount (costs), quantity
                0.8.toBigDecimal(), 1.toBigDecimal(),
                "initial", null
            )
        )

        inventory.docStatus = MInventory.DOCSTATUS_Completed
        inventory.save()

        inventory.setDocAction(DocAction.STATUS_Completed)
        inventory.save()


        inventory.completeIt()

        return getProductById(product.id)
    }
}
