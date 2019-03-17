package org.compiere.accounting;

import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Document Tax Line
 *
 * @author Jorg Janke
 * @version $Id: DocTax.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public final class DocTax {
    /**
     * Tax Due Acct
     */
    public static final int ACCTTYPE_TaxDue = 0;
    /**
     * Tax Credit
     */
    public static final int ACCTTYPE_TaxCredit = 1;
    /**
     * Tax Expense
     */
    public static final int ACCTTYPE_TaxExpense = 2;
    /**
     * Logger
     */
    private static CLogger log = CLogger.getCLogger(DocTax.class);
    /**
     * Tax ID
     */
    private int m_C_Tax_ID = 0;
    /**
     * Amount
     */
    private BigDecimal m_amount = null;
    /**
     * Tax Rate
     */
    private BigDecimal m_rate = null;
    /**
     * Name
     */
    private String m_name = null;
    /**
     * Base Tax Amt
     */
    private BigDecimal m_taxBaseAmt = null;
    /**
     * Included Tax
     */
    private BigDecimal m_includedTax = Env.ZERO;
    /**
     * Sales Tax
     */
    private boolean m_salesTax = false;

    /**
     * Create Tax
     *
     * @param C_Tax_ID   tax
     * @param name       name
     * @param rate       rate
     * @param taxBaseAmt tax base amount
     * @param amount     amount
     * @param salesTax   sales tax flag
     */
    public DocTax(
            int C_Tax_ID,
            String name,
            BigDecimal rate,
            BigDecimal taxBaseAmt,
            BigDecimal amount,
            boolean salesTax) {
        m_C_Tax_ID = C_Tax_ID;
        m_name = name;
        m_rate = rate;
        m_taxBaseAmt = taxBaseAmt; // IDEMPIERE-2160 Add by Hideaki Hagiwara
        m_amount = amount;
        m_salesTax = salesTax;
    } //	DocTax

    /**
     * Get Account
     *
     * @param AcctType see ACCTTYPE_*
     * @param as       account schema
     * @return Account
     */
    public MAccount getAccount(int AcctType, MAcctSchema as) {
        if (AcctType < ACCTTYPE_TaxDue || AcctType > ACCTTYPE_TaxExpense) return null;
        //
        String sql =
                "SELECT T_Due_Acct, T_Credit_Acct, T_Expense_Acct "
                        + "FROM C_Tax_Acct WHERE C_Tax_ID=? AND C_AcctSchema_ID=?";
        int validCombination_ID = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, m_C_Tax_ID);
            pstmt.setInt(2, as.getAccountingSchemaId());
            rs = pstmt.executeQuery();
            if (rs.next()) validCombination_ID = rs.getInt(AcctType + 1); //  1..3
        } catch (SQLException e) {
            log.log(Level.SEVERE, sql, e);
        } finally {

            rs = null;
            pstmt = null;
        }
        if (validCombination_ID == 0) return null;
        return MAccount.get(as.getCtx(), validCombination_ID);
    } //  getAccount

    /**
     * Get Amount
     *
     * @return gross amount
     */
    public BigDecimal getAmount() {
        return m_amount;
    }

    /**
     * Get C_Tax_ID
     *
     * @return tax id
     */
    public int getTaxId() {
        return m_C_Tax_ID;
    } //	getTaxId

    /**
     * Add to Included Tax
     *
     * @param amt amount
     */
    public void addIncludedTax(BigDecimal amt) {
        m_includedTax = m_includedTax.add(amt);
    } //	addIncludedTax

    /**
     * Get Included Tax Difference
     *
     * @return tax ampunt - included amount
     */
    public BigDecimal getIncludedTaxDifference() {
        return m_amount.subtract(m_includedTax);
    } //	getIncludedTaxDifference

    /**
     * Included Tax differs from tax amount
     *
     * @return true if difference
     */
    public boolean isIncludedTaxDifference() {
        return Env.ZERO.compareTo(getIncludedTaxDifference()) != 0;
    } //	isIncludedTaxDifference

    /**
     * Get AP Tax Type
     *
     * @return AP tax type (Credit or Expense)
     */
    public int getAPTaxType() {
        if (isSalesTax()) return ACCTTYPE_TaxExpense;
        return ACCTTYPE_TaxCredit;
    } //	getAPTaxAcctType

    /**
     * Is Sales Tax
     *
     * @return sales tax
     */
    public boolean isSalesTax() {
        return m_salesTax;
    } //	isSalesTax

    /**
     * Return String representation
     *
     * @return tax anme and base amount
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("Tax=(");
        sb.append(m_name);
        sb.append(" Amt=").append(m_amount);
        sb.append(")");
        return sb.toString();
    } //	toString
} //	DocTax
