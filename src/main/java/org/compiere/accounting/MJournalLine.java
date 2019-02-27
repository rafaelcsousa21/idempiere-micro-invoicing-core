package org.compiere.accounting;

import org.compiere.model.IDoc;
import org.compiere.model.IPODoc;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.product.MCurrency;
import org.compiere.util.Msg;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import static software.hsharp.core.util.DBKt.executeUpdate;
import static software.hsharp.core.util.DBKt.getSQLValue;

/**
 * Journal Line Model
 *
 * @author Jorg Janke
 * @author Cristina Ghita
 * <li>BF [ 2855807 ] orgId from account
 * https://sourceforge.net/tracker/?func=detail&aid=2855807&group_id=176962&atid=879332
 * @version $Id: MJournalLine.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MJournalLine extends X_GL_JournalLine implements IPODoc {
    /**
     *
     */
    private static final long serialVersionUID = 7584093911055786835L;
    /**
     * Parent
     */
    private MJournal m_parent = null;
    /**
     * Currency Precision
     */
    private int m_precision = 2;
    /**
     * Account Combination
     */
    private MAccount m_account = null;
    /**
     * Account Element
     */
    private MElementValue m_accountElement = null;

    /**
     * Standard Constructor
     *
     * @param ctx               context
     * @param GL_JournalLine_ID id
     * @param trxName           transaction
     */
    public MJournalLine(Properties ctx, int GL_JournalLine_ID) {
        super(ctx, GL_JournalLine_ID);
        if (GL_JournalLine_ID == 0) {
            //	setGL_JournalLine_ID (0);		//	PK
            //	setGL_Journal_ID (0);			//	Parent
            //	setCurrencyId (0);
            //	setC_ValidCombination_ID (0);
            setLine(0);
            setAmtAcctCr(Env.ZERO);
            setAmtAcctDr(Env.ZERO);
            setAmtSourceCr(Env.ZERO);
            setAmtSourceDr(Env.ZERO);
            setCurrencyRate(Env.ONE);
            //	setConversionTypeId (0);
            setDateAcct(new Timestamp(System.currentTimeMillis()));
            setIsGenerated(true);
        }
    } //	MJournalLine

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MJournalLine(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MJournalLine

    /**
     * Parent Constructor
     *
     * @param parent journal
     */
    public MJournalLine(MJournal parent) {
        this(parent.getCtx(), 0);
        setClientOrg(parent);
        setGL_Journal_ID(parent.getGL_Journal_ID());
        setCurrencyId(parent.getCurrencyId());
        setConversionTypeId(parent.getConversionTypeId());
        setDateAcct(parent.getDateAcct());
    } //	MJournalLine

    /**
     * Get Parent
     *
     * @return parent
     */
    public MJournal getParent() {
        if (m_parent == null) m_parent = new MJournal(getCtx(), getGL_Journal_ID());
        return m_parent;
    } //	getParent

    /**
     * Set Currency Info
     *
     * @param C_Currency_ID       currenct
     * @param C_ConversionType_ID type
     * @param CurrencyRate        rate
     */
    public void setCurrency(int C_Currency_ID, int C_ConversionType_ID, BigDecimal CurrencyRate) {
        setCurrencyId(C_Currency_ID);
        if (C_ConversionType_ID != 0) setConversionTypeId(C_ConversionType_ID);
        if (CurrencyRate != null && CurrencyRate.signum() == 0) setCurrencyRate(CurrencyRate);
    } //	setCurrency

    /**
     * Set C_Currency_ID and precision
     *
     * @param C_Currency_ID currency
     */
    public void setCurrencyId(int C_Currency_ID) {
        if (C_Currency_ID == 0) return;
        super.setCurrencyId(C_Currency_ID);
        m_precision = MCurrency.getStdPrecision(getCtx(), C_Currency_ID);
    } //	setCurrencyId

    /**
     * Get Currency Precision
     *
     * @return precision
     */
    public int getPrecision() {
        return m_precision;
    } //	getPrecision

    /**
     * Set Currency Rate
     *
     * @param CurrencyRate check for null (->one)
     */
    public void setCurrencyRate(BigDecimal CurrencyRate) {
        if (CurrencyRate == null) {
            log.warning("was NULL - set to 1");
            super.setCurrencyRate(Env.ONE);
        } else if (CurrencyRate.signum() < 0) {
            log.warning("negative - " + CurrencyRate + " - set to 1");
            super.setCurrencyRate(Env.ONE);
        } else super.setCurrencyRate(CurrencyRate);
    } //	setCurrencyRate

    /**
     * Set Accounted Amounts only if not 0. Amounts overwritten in beforeSave - set conversion rate
     *
     * @param AmtAcctDr Dr
     * @param AmtAcctCr Cr
     */
    public void setAmtAcct(BigDecimal AmtAcctDr, BigDecimal AmtAcctCr) {
        //	setConversion
        double rateDR = 0;
        if (AmtAcctDr != null && AmtAcctDr.signum() != 0) {
            rateDR = AmtAcctDr.doubleValue() / getAmtSourceDr().doubleValue();
            super.setAmtAcctDr(AmtAcctDr);
        }
        double rateCR = 0;
        if (AmtAcctCr != null && AmtAcctCr.signum() != 0) {
            rateCR = AmtAcctCr.doubleValue() / getAmtSourceCr().doubleValue();
            super.setAmtAcctCr(AmtAcctCr);
        }
        if (rateDR != 0 && rateCR != 0 && rateDR != rateCR) {
            log.warning("Rates Different DR=" + rateDR + "(used) <> CR=" + rateCR + "(ignored)");
            rateCR = 0;
        }
        if (rateDR < 0 || Double.isInfinite(rateDR) || Double.isNaN(rateDR)) {
            log.warning("DR Rate ignored - " + rateDR);
            return;
        }
        if (rateCR < 0 || Double.isInfinite(rateCR) || Double.isNaN(rateCR)) {
            log.warning("CR Rate ignored - " + rateCR);
            return;
        }

        if (rateDR != 0) setCurrencyRate(BigDecimal.valueOf(rateDR));
        if (rateCR != 0) setCurrencyRate(BigDecimal.valueOf(rateCR));
    } //	setAmtAcct

    /**
     * Set C_ValidCombination_ID
     *
     * @param C_ValidCombination_ID id
     */
    public void setC_ValidCombination_ID(int C_ValidCombination_ID) {
        super.setC_ValidCombination_ID(C_ValidCombination_ID);
        m_account = null;
        m_accountElement = null;
    } //	setC_ValidCombination_ID

    /**
     * Set C_ValidCombination_ID
     *
     * @param acct account
     */
    public void setC_ValidCombination_ID(MAccount acct) {
        if (acct == null) throw new IllegalArgumentException("Account is null");
        super.setC_ValidCombination_ID(acct.getC_ValidCombination_ID());
        m_account = acct;
        m_accountElement = null;
    } //	setC_ValidCombination_ID

    /**
     * Get Account (Valid Combination)
     *
     * @return combination or null
     */
    public MAccount getAccount_Combi() {
        if (m_account == null && getC_ValidCombination_ID() != 0)
            m_account = new MAccount(getCtx(), getC_ValidCombination_ID());
        return m_account;
    } //	getValidCombination

    /**
     * Get Natural Account Element Value
     *
     * @return account
     */
    public MElementValue getAccountElementValue() {
        if (m_accountElement == null) {
            MAccount vc = getAccount_Combi();
            if (vc != null && vc.getAccount_ID() != 0)
                m_accountElement = new MElementValue(getCtx(), vc.getAccount_ID());
        }
        return m_accountElement;
    } //	getAccountElement

    /**
     * Is it posting to a Control Acct
     *
     * @return true if control acct
     */
    public boolean isDocControlled() {
        MElementValue acct = getAccountElementValue();
        if (acct == null) {
            log.warning("Account not found for C_ValidCombination_ID=" + getC_ValidCombination_ID());
            return false;
        }
        return acct.isDocControlled();
    } //	isDocControlled

    /**
     * ************************************************************************ Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        if (newRecord && getParent().isComplete()) {
            log.saveError("ParentComplete", Msg.translate(getCtx(), "GL_JournalLine"));
            return false;
        }
        // idempiere 344 - nmicoud
        if (!getOrCreateCombination()) return false;
        if (getC_ValidCombination_ID() <= 0) {
            log.saveError(
                    "SaveError",
                    Msg.parseTranslation(getCtx(), "@FillMandatory@" + "@C_ValidCombination_ID@"));
            return false;
        }
        fillDimensionsFromCombination();
        // end idempiere 344 - nmicoud

        //	Acct Amts
        BigDecimal rate = getCurrencyRate();
        BigDecimal amt = rate.multiply(getAmtSourceDr());
        if (amt.scale() > getPrecision()) amt = amt.setScale(getPrecision(), BigDecimal.ROUND_HALF_UP);
        setAmtAcctDr(amt);
        amt = rate.multiply(getAmtSourceCr());
        if (amt.scale() > getPrecision()) amt = amt.setScale(getPrecision(), BigDecimal.ROUND_HALF_UP);
        setAmtAcctCr(amt);
        //	Set Line Org to Doc Org if still not set
        if (getOrgId() <= 0) {
            setOrgId(getParent().getOrgId());
        }
        return true;
    } //	beforeSave

    /**
     * After Save. Update Journal/Batch Total
     *
     * @param newRecord true if new record
     * @param success   true if success
     * @return success
     */
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) return success;
        return updateJournalTotal();
    } //	afterSave

    /**
     * After Delete
     *
     * @param success true if deleted
     * @return true if success
     */
    protected boolean afterDelete(boolean success) {
        if (!success) return success;
        return updateJournalTotal();
    } //	afterDelete

    /**
     * Update Journal and Batch Total
     *
     * @return true if success
     */
    private boolean updateJournalTotal() {
        //	Update Journal Total
        StringBuilder sql =
                new StringBuilder("UPDATE GL_Journal j")
                        .append(
                                " SET (TotalDr, TotalCr) = (SELECT COALESCE(SUM(AmtAcctDr),0), COALESCE(SUM(AmtAcctCr),0)") // croo Bug# 1789935
                        .append(
                                " FROM GL_JournalLine jl WHERE jl.IsActive='Y' AND j.GL_Journal_ID=jl.GL_Journal_ID) ")
                        .append("WHERE GL_Journal_ID=")
                        .append(getGL_Journal_ID());
        int no = executeUpdate(sql.toString());
        if (no != 1) log.warning("afterSave - Update Journal #" + no);

        //	Update Batch Total
        int GL_JournalBatch_ID =
                getSQLValue(
                        "SELECT GL_JournalBatch_ID FROM GL_Journal WHERE GL_Journal_ID=?",
                        getGL_Journal_ID());
        if (GL_JournalBatch_ID != 0) { // idempiere 344 - nmicoud
            sql =
                    new StringBuilder("UPDATE GL_JournalBatch jb")
                            .append(
                                    " SET (TotalDr, TotalCr) = (SELECT COALESCE(SUM(TotalDr),0), COALESCE(SUM(TotalCr),0)")
                            .append(" FROM GL_Journal j WHERE jb.GL_JournalBatch_ID=j.GL_JournalBatch_ID) ")
                            .append("WHERE GL_JournalBatch_ID=")
                            .append("(SELECT DISTINCT GL_JournalBatch_ID FROM GL_Journal WHERE GL_Journal_ID=")
                            .append(getGL_Journal_ID())
                            .append(")");
            no = executeUpdate(sql.toString());
            if (no != 1) log.warning("Update Batch #" + no);
        }
        return no == 1;
    } //	updateJournalTotal

    /**
     * Update combination and optionally *
     */
    private boolean getOrCreateCombination() {
        if (getC_ValidCombination_ID() == 0
                || (!isNew()
                && (is_ValueChanged("Account_ID")
                || is_ValueChanged("C_SubAcct_ID")
                || is_ValueChanged("M_Product_ID")
                || is_ValueChanged("C_BPartner_ID")
                || is_ValueChanged("AD_OrgTrx_ID")
                || is_ValueChanged("AD_Org_ID")
                || is_ValueChanged("C_LocFrom_ID")
                || is_ValueChanged("C_LocTo_ID")
                || is_ValueChanged("C_SalesRegion_ID")
                || is_ValueChanged("C_Project_ID")
                || is_ValueChanged("C_Campaign_ID")
                || is_ValueChanged("C_Activity_ID")
                || is_ValueChanged("User1_ID")
                || is_ValueChanged("User2_ID")))) {
            MJournal gl = new MJournal(getCtx(), getGL_Journal_ID());

            // Validate all mandatory combinations are set
            MAcctSchema as = (MAcctSchema) getParent().getC_AcctSchema();
            String errorFields = "";
            for (MAcctSchemaElement elem : MAcctSchemaElement.getAcctSchemaElements(as)) {
                if (!elem.isMandatory()) continue;
                String et = elem.getElementType();
                if (MAcctSchemaElement.ELEMENTTYPE_Account.equals(et) && getAccount_ID() == 0)
                    errorFields += "@" + I_GL_JournalLine.COLUMNNAME_Account_ID + "@, ";
                if (MAcctSchemaElement.ELEMENTTYPE_Activity.equals(et) && getBusinessActivityId() == 0)
                    errorFields += "@" + I_GL_JournalLine.COLUMNNAME_C_Activity_ID + "@, ";
                if (MAcctSchemaElement.ELEMENTTYPE_BPartner.equals(et) && getBusinessPartnerId() == 0)
                    errorFields += "@" + I_GL_JournalLine.COLUMNNAME_C_BPartner_ID + "@, ";
                if (MAcctSchemaElement.ELEMENTTYPE_Campaign.equals(et) && getCampaignId() == 0)
                    errorFields += "@" + I_GL_JournalLine.COLUMNNAME_C_Campaign_ID + "@, ";
                if (MAcctSchemaElement.ELEMENTTYPE_Organization.equals(et) && getOrgId() == 0)
                    errorFields += "@" + I_GL_JournalLine.COLUMNNAME_AD_Org_ID + "@, ";
                if (MAcctSchemaElement.ELEMENTTYPE_OrgTrx.equals(et) && getTransactionOrganizationId() == 0)
                    errorFields += "@" + I_GL_JournalLine.COLUMNNAME_AD_OrgTrx_ID + "@, ";
                if (MAcctSchemaElement.ELEMENTTYPE_Product.equals(et) && getM_Product_ID() == 0)
                    errorFields += "@" + I_GL_JournalLine.COLUMNNAME_M_Product_ID + "@, ";
                if (MAcctSchemaElement.ELEMENTTYPE_Project.equals(et) && getProjectId() == 0)
                    errorFields += "@" + I_GL_JournalLine.COLUMNNAME_C_Project_ID + "@, ";
                if (MAcctSchemaElement.ELEMENTTYPE_SalesRegion.equals(et) && getC_SalesRegion_ID() == 0)
                    errorFields += "@" + I_GL_JournalLine.COLUMNNAME_C_SalesRegion_ID + "@, ";
                if (MAcctSchemaElement.ELEMENTTYPE_UserElementList1.equals(et) && getUser1Id() == 0)
                    errorFields += "@" + I_GL_JournalLine.COLUMNNAME_User1_ID + "@, ";
                if (MAcctSchemaElement.ELEMENTTYPE_UserElementList2.equals(et) && getUser2Id() == 0)
                    errorFields += "@" + I_GL_JournalLine.COLUMNNAME_User2_ID + "@, ";
            }
            if (errorFields.length() > 0) {
                log.saveError(
                        "Error",
                        Msg.parseTranslation(
                                getCtx(), "@IsMandatory@: " + errorFields.substring(0, errorFields.length() - 2)));
                return false;
            }

            MAccount acct =
                    MAccount.get(
                            getCtx(),
                            getClientId(),
                            getOrgId(),
                            gl.getC_AcctSchema_ID(),
                            getAccount_ID(),
                            getC_SubAcct_ID(),
                            getM_Product_ID(),
                            getBusinessPartnerId(),
                            getTransactionOrganizationId(),
                            getC_LocFrom_ID(),
                            getC_LocTo_ID(),
                            getC_SalesRegion_ID(),
                            getProjectId(),
                            getCampaignId(),
                            getBusinessActivityId(),
                            getUser1Id(),
                            getUser2Id(),
                            0,
                            0,
                            null);

            if (acct != null) {
                acct.saveEx(); // get ID from transaction
                setC_ValidCombination_ID(acct.getId());
                if (acct.getAlias() != null && acct.getAlias().length() > 0)
                    setAlias_ValidCombination_ID(acct.getId());
                else setAlias_ValidCombination_ID(0);
            }
        }
        return true;
    } //	getOrCreateCombination

    /**
     * Fill Accounting Dimensions from line combination *
     */
    private void fillDimensionsFromCombination() {
        if (getC_ValidCombination_ID() > 0) {
            MAccount combi = new MAccount(getCtx(), getC_ValidCombination_ID());
            setAccount_ID(combi.getAccount_ID() > 0 ? combi.getAccount_ID() : 0);
            setC_SubAcct_ID(combi.getC_SubAcct_ID() > 0 ? combi.getC_SubAcct_ID() : 0);
            setM_Product_ID(combi.getM_Product_ID() > 0 ? combi.getM_Product_ID() : 0);
            setBusinessPartnerId(combi.getBusinessPartnerId() > 0 ? combi.getBusinessPartnerId() : 0);
            setTransactionOrganizationId(combi.getTransactionOrganizationId() > 0 ? combi.getTransactionOrganizationId() : 0);
            setOrgId(combi.getOrgId() > 0 ? combi.getOrgId() : 0);
            setC_LocFrom_ID(combi.getC_LocFrom_ID() > 0 ? combi.getC_LocFrom_ID() : 0);
            setC_LocTo_ID(combi.getC_LocTo_ID() > 0 ? combi.getC_LocTo_ID() : 0);
            setC_SalesRegion_ID(combi.getC_SalesRegion_ID() > 0 ? combi.getC_SalesRegion_ID() : 0);
            setProjectId(combi.getProjectId() > 0 ? combi.getProjectId() : 0);
            setCampaignId(combi.getCampaignId() > 0 ? combi.getCampaignId() : 0);
            setBusinessActivityId(combi.getBusinessActivityId() > 0 ? combi.getBusinessActivityId() : 0);
            setUser1Id(combi.getUser1Id() > 0 ? combi.getUser1Id() : 0);
            setUser2Id(combi.getUser2Id() > 0 ? combi.getUser2Id() : 0);
        }
    } // fillDimensionsFromCombination

    @Override
    public void setDoc(IDoc doc) {
    }

    @Override
    public void setProcessedOn(String processed, boolean b, boolean b1) {
    }
} //	MJournalLine
