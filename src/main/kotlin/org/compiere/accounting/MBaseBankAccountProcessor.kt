package org.compiere.accounting

import software.hsharp.core.util.DB
import software.hsharp.core.util.queryOf
import java.math.BigDecimal

/**
 * Get Bank Account Processor
 *
 * @param ctx context
 * @param tender optional Tender see TENDER_
 * @param CCType optional CC Type see CC_
 * @param AD_Client_ID Client
 * @param C_Currency_ID Currency (ignored)
 * @param Amt Amount (ignored)
 * @param trxName transaction
 * @return Array of BankAccount[0] & PaymentProcessor[1] or null
 */
fun findBankAccountProcessors(
    tender: String,
    CCType: String,
    AD_Client_ID: Int,
    C_Currency_ID: Int,
    Amt: BigDecimal
): Array<MBankAccountProcessor>? {
    val sql = StringBuffer(
        "SELECT bap.* " +
                "FROM C_BankAccount_Processor bap, C_PaymentProcessor pp, C_BankAccount ba " +
                "WHERE pp.C_PaymentProcessor_ID = bap.C_PaymentProcessor_ID" +
                " AND ba.C_BankAccount_ID = bap.C_BankAccount_ID" +
                " AND ba.AD_Client_ID=? AND pp.IsActive='Y'" + // 	#1

                " AND ba.IsActive='Y' AND bap.IsActive='Y' " +
                " AND (bap.C_Currency_ID IS NULL OR bap.C_Currency_ID=?)" + // 	#2

                " AND (bap.MinimumAmt IS NULL OR bap.MinimumAmt = 0 OR bap.MinimumAmt <= ?)"
    ) // 	#3
    if (MPayment.TENDERTYPE_DirectDeposit == tender)
        sql.append(" AND bap.AcceptDirectDeposit='Y' AND pp.AcceptDirectDeposit='Y' ")
    else if (MPayment.TENDERTYPE_DirectDebit == tender)
        sql.append(" AND bap.AcceptDirectDebit='Y' AND pp.AcceptDirectDebit='Y' ")
    else if (MPayment.TENDERTYPE_Check == tender)
        sql.append(" AND bap.AcceptCheck='Y' AND pp.AcceptCheck='Y' ")
    else if (MPayment.CREDITCARDTYPE_ATM == CCType)
        sql.append(" AND bap.AcceptATM='Y' AND pp.AcceptATM='Y' ")
    else if (MPayment.CREDITCARDTYPE_Amex == CCType)
        sql.append(" AND bap.AcceptAMEX='Y' AND pp.AcceptAMEX='Y' ")
    else if (MPayment.CREDITCARDTYPE_Visa == CCType)
        sql.append(" AND bap.AcceptVISA='Y' AND pp.AcceptVISA='Y' ")
    else if (MPayment.CREDITCARDTYPE_MasterCard == CCType)
        sql.append(" AND bap.AcceptMC='Y' AND pp.AcceptMC='Y' ")
    else if (MPayment.CREDITCARDTYPE_Diners == CCType)
        sql.append(" AND bap.AcceptDiners='Y' AND pp.AcceptDiners='Y' ")
    else if (MPayment.CREDITCARDTYPE_Discover == CCType)
        sql.append(" AND bap.AcceptDiscover='Y' AND pp.AcceptDiscover='Y' ")
    else if (MPayment.CREDITCARDTYPE_PurchaseCard == CCType)
        sql.append(" AND bap.AcceptCORPORATE='Y' AND pp.AcceptCORPORATE='Y' ") //  CreditCards
    sql.append(" ORDER BY ba.IsDefault DESC ")

    val query =
        queryOf(sql.toString(), listOf(AD_Client_ID, C_Currency_ID, Amt)).map { row -> MBankAccountProcessor(row) }
            .asList
    return DB.current.run(query).toTypedArray()
} //  find