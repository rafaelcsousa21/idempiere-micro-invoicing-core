package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_GL_Distribution;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;

import static software.hsharp.core.util.DBKt.getSQLValue;

/**
 * GL Distribution Line Model
 *
 * @author Jorg Janke
 * @version $Id: MDistributionLine.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MDistributionLine extends X_GL_DistributionLine {
    /**
     *
     */
    private static final long serialVersionUID = 6148743556518054326L;
    /**
     * The Parent
     */
    private I_GL_Distribution m_parent = null;
    /**
     * The Amount
     */
    private BigDecimal m_amt = null;
    /**
     * The Quantity
     */
    private BigDecimal m_qty = null;
    /**
     * The Base Account
     */
    private I_C_ValidCombination m_account = null;

    /**
     * Standard Constructor
     *
     * @param GL_DistributionLine_ID id
     */
    public MDistributionLine(int GL_DistributionLine_ID) {
        super(GL_DistributionLine_ID);
        if (GL_DistributionLine_ID == 0) {
            //
            setOverwriteAcct(false);
            setOverwriteActivity(false);
            setOverwriteBPartner(false);
            setOverwriteCampaign(false);
            setOverwriteLocFrom(false);
            setOverwriteLocTo(false);
            setOverwriteOrg(false);
            setOverwriteOrgTrx(false);
            setOverwriteProduct(false);
            setOverwriteProject(false);
            setOverwriteSalesRegion(false);
            setOverwriteUser1(false);
            setOverwriteUser2(false);
            //
            setPercent(Env.ZERO);
        }
    } //	MDistributionLine

    /**
     * Load Constructor
     *
     */
    public MDistributionLine(Row row) {
        super(row);
    } //	MDistributionLine

    /**
     * Get Parent
     *
     * @return Returns the parent.
     */
    public I_GL_Distribution getParent() {
        if (m_parent == null)
            m_parent = new MDistribution(getGLDistributionId());
        return m_parent;
    } //	getParent

    /**
     * Set Parent
     *
     * @param parent The parent to set.
     */
    public void setParent(I_GL_Distribution parent) {
        m_parent = parent;
    } //	setParent

    /**
     * Get Account Combination based on Account and Overwrite
     *
     * @return account
     */
    public I_C_ValidCombination getAccount() {
        return MAccount.get(
                m_account.getClientId(),
                isOverwriteOrg() && getOrgId() != 0 ? getOrgId() : m_account.getOrgId(),
                m_account.getAccountingSchemaId(),
                isOverwriteAcct() && getAccountId() != 0 ? getAccountId() : m_account.getAccountId(),
                m_account.getSubAccountId(),
                //
                isOverwriteProduct() ? getProductId() : m_account.getProductId(),
                isOverwriteBPartner() ? getBusinessPartnerId() : m_account.getBusinessPartnerId(),
                isOverwriteOrgTrx() ? getTransactionOrganizationId() : m_account.getTransactionOrganizationId(),
                isOverwriteLocFrom() ? getLocationFromId() : m_account.getLocationFromId(),
                isOverwriteLocTo() ? getLocationToId() : m_account.getLocationToId(),
                isOverwriteSalesRegion() ? getSalesRegionId() : m_account.getSalesRegionId(),
                isOverwriteProject() ? getProjectId() : m_account.getProjectId(),
                isOverwriteCampaign() ? getCampaignId() : m_account.getCampaignId(),
                isOverwriteActivity() ? getBusinessActivityId() : m_account.getBusinessActivityId(),
                isOverwriteUser1() ? getUser1Id() : m_account.getUser1Id(),
                isOverwriteUser2() ? getUser2Id() : m_account.getUser2Id(),
                m_account.getUserElement1Id(),
                m_account.getUserElement2Id()
        );
    } //	setAccount

    /**
     * Set Account
     *
     * @param acct account
     */
    public void setAccount(I_C_ValidCombination acct) {
        m_account = acct;
    } //	setAccount

    /**
     * ************************************************************************ Get Distribution
     * Amount
     *
     * @return Returns the amt.
     */
    public BigDecimal getAmt() {
        return m_amt;
    } //	getAmt

    /**
     * Set Distribution Amount
     *
     * @param amt The amt to set.
     */
    public void setAmt(BigDecimal amt) {
        m_amt = amt;
    } //	setAmt

    /**
     * ************************************************************************ Get Distribution
     * Quantity
     *
     * @return Returns the qty.
     */
    public BigDecimal getQty() {
        return m_qty;
    } //	getQty

    /**
     * Set Distribution Quantity
     *
     * @param qty The qty to set.
     */
    public void setQty(BigDecimal qty) {
        m_qty = qty;
    } //	setQty

    /**
     * Set Distribution Amount
     *
     * @param amt       The amt to set to be multiplied by percent.
     * @param precision precision
     */
    public void calculateAmt(BigDecimal amt, int precision) {
        m_amt = amt.multiply(getPercent());
        m_amt = m_amt.divide(Env.ONEHUNDRED, precision, BigDecimal.ROUND_HALF_UP);
    } //	setAmt

    /**
     * Set Distribution Quantity
     *
     * @param qty The qty to set to be multiplied by percent.
     */
    public void calculateQty(BigDecimal qty) {
        m_qty = qty.multiply(getPercent());
        m_qty = m_qty.divide(Env.ONEHUNDRED, BigDecimal.ROUND_HALF_UP);
    } //	setAmt

    /**
     * ************************************************************************ Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        if (getLine() == 0) {
            String sql =
                    "SELECT COALESCE(MAX(Line),0)+10 FROM GL_DistributionLine WHERE GL_Distribution_ID=?";
            int ii = getSQLValue(sql, getGLDistributionId());
            setLine(ii);
        }
        //	Reset not selected Overwrite
        if (!isOverwriteAcct() && getAccountId() != 0) setAccountId(0);
        if (!isOverwriteActivity() && getBusinessActivityId() != 0) setBusinessActivityId(0);
        if (!isOverwriteBPartner() && getBusinessPartnerId() != 0) setBusinessPartnerId(0);
        if (!isOverwriteCampaign() && getCampaignId() != 0) setCampaignId(0);
        if (!isOverwriteLocFrom() && getLocationFromId() != 0) setLocationFromId(0);
        if (!isOverwriteLocTo() && getLocationToId() != 0) setLocationToId(0);
        if (!isOverwriteOrg() && getOrgId() != 0) setOrgId(0);
        if (!isOverwriteOrgTrx() && getTransactionOrganizationId() != 0) setTransactionOrganizationId(0);
        if (!isOverwriteProduct() && getProductId() != 0) setProductId(0);
        if (!isOverwriteProject() && getProjectId() != 0) setProjectId(0);
        if (!isOverwriteSalesRegion() && getSalesRegionId() != 0) setSalesRegionId(0);
        if (!isOverwriteUser1() && getUser1Id() != 0) setUser1Id(0);
        if (!isOverwriteUser2() && getUser2Id() != 0) setUser2Id(0);

        //	Account Overwrite cannot be 0
        if (isOverwriteAcct() && getAccountId() == 0) {
            log.saveError("Error", MsgKt.parseTranslation("@Account_ID@ = 0"));
            return false;
        }
        //	Org Overwrite cannot be 0
        if (isOverwriteOrg() && getOrgId() == 0) {
            log.saveError("Error", MsgKt.parseTranslation("@Org_ID@ = 0"));
            return false;
        }
        return true;
    } //	beforeSave

    /**
     * After Save
     *
     * @param newRecord new
     * @param success   success
     * @return success
     */
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) return success;
        getParent();
        m_parent.validate();
        m_parent.saveEx();
        return success;
    } //	afterSave
} //	MDistributionLine
