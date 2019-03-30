package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.crm.MLocation;
import org.compiere.crm.X_C_BPartner;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.orm.MOrg;
import org.compiere.orm.Query;
import org.compiere.product.X_M_Product;
import org.compiere.production.X_C_Project;
import org.idempiere.common.util.CLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Account Object Entity to maintain all segment values. C_ValidCombination
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, www.e-evolution.com
 * <li>RF [ 2214883 ] Remove SQL code and Replace for Query
 * http://sourceforge.net/tracker/index.php?func=detail&aid=2214883&group_id=176962&atid=879335
 * @author Teo Sarca, www.arhipac.ro
 * <li>FR [ 2694043 ] Query. first/firstOnly usage best practice
 * @version $Id: MAccount.java,v 1.4 2006/07/30 00:58:04 jjanke Exp $
 */
public class MAccount extends X_C_ValidCombination implements I_C_ValidCombination {
    /**
     *
     */
    private static final long serialVersionUID = 7980515458720808532L;
    /**
     * Logger
     */
    private static CLogger s_log = CLogger.getCLogger(MAccount.class);
    /**
     * Account Segment
     */
    private MElementValue m_accountEV = null;

    /**
     * ************************************************************************ Default constructor
     *
     * @param ctx                   context
     * @param C_ValidCombination_ID combination
     * @param trxName               transaction
     */
    public MAccount(Properties ctx, int C_ValidCombination_ID) {
        super(ctx, C_ValidCombination_ID);
        if (C_ValidCombination_ID == 0) {
            //	setAccountId (0);
            //	setAccountingSchemaId (0);
            setIsFullyQualified(false);
        }
    } //  MAccount

    /**
     * Load constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MAccount(Properties ctx, Row row) {
        super(ctx, row);
    } //  MAccount

    /**
     * Parent Constructor
     *
     * @param as account schema
     */
    public MAccount(MAcctSchema as) {
        this(as.getCtx(), 0);
        setClientOrg(as);
        setAccountingSchemaId(as.getAccountingSchemaId());
    } //	Account

    /**
     * Get existing Account or create it
     *
     * @param ctx              context
     * @param AD_Client_ID
     * @param AD_Org_ID
     * @param C_AcctSchema_ID
     * @param Account_ID
     * @param C_SubAcct_ID
     * @param M_Product_ID
     * @param C_BPartner_ID
     * @param AD_OrgTrx_ID
     * @param C_LocFrom_ID
     * @param C_LocTo_ID
     * @param C_SalesRegion_ID
     * @param C_Project_ID
     * @param C_Campaign_ID
     * @param C_Activity_ID
     * @param User1_ID
     * @param User2_ID
     * @param UserElement1_ID
     * @param UserElement2_ID
     * @return account or null
     */
    public static MAccount get(
            Properties ctx,
            int AD_Client_ID,
            int AD_Org_ID,
            int C_AcctSchema_ID,
            int Account_ID,
            int C_SubAcct_ID,
            int M_Product_ID,
            int C_BPartner_ID,
            int AD_OrgTrx_ID,
            int C_LocFrom_ID,
            int C_LocTo_ID,
            int C_SalesRegion_ID,
            int C_Project_ID,
            int C_Campaign_ID,
            int C_Activity_ID,
            int User1_ID,
            int User2_ID,
            int UserElement1_ID,
            int UserElement2_ID) {
        StringBuilder info = new StringBuilder();
        info.append("AD_Client_ID=").append(AD_Client_ID).append(",AD_Org_ID=").append(AD_Org_ID);
        //	Schema
        info.append(",C_AcctSchema_ID=").append(C_AcctSchema_ID);
        //	Account
        info.append(",Account_ID=").append(Account_ID).append(" ");

        ArrayList<Object> params = new ArrayList<Object>();
        //		Mandatory fields
        StringBuilder whereClause =
                new StringBuilder("AD_Client_ID=?") // 	#1
                        .append(" AND AD_Org_ID=?")
                        .append(" AND C_AcctSchema_ID=?")
                        .append(" AND Account_ID=?"); // 	#4
        params.add(AD_Client_ID);
        params.add(AD_Org_ID);
        params.add(C_AcctSchema_ID);
        params.add(Account_ID);
        //	Optional fields
        if (C_SubAcct_ID == 0) whereClause.append(" AND C_SubAcct_ID IS NULL");
        else {
            whereClause.append(" AND C_SubAcct_ID=?");
            params.add(C_SubAcct_ID);
        }
        if (M_Product_ID == 0) whereClause.append(" AND M_Product_ID IS NULL");
        else {
            whereClause.append(" AND M_Product_ID=?");
            params.add(M_Product_ID);
        }
        if (C_BPartner_ID == 0) whereClause.append(" AND C_BPartner_ID IS NULL");
        else {
            whereClause.append(" AND C_BPartner_ID=?");
            params.add(C_BPartner_ID);
        }
        if (AD_OrgTrx_ID == 0) whereClause.append(" AND AD_OrgTrx_ID IS NULL");
        else {
            whereClause.append(" AND AD_OrgTrx_ID=?");
            params.add(AD_OrgTrx_ID);
        }
        if (C_LocFrom_ID == 0) whereClause.append(" AND C_LocFrom_ID IS NULL");
        else {
            whereClause.append(" AND C_LocFrom_ID=?");
            params.add(C_LocFrom_ID);
        }
        if (C_LocTo_ID == 0) whereClause.append(" AND C_LocTo_ID IS NULL");
        else {
            whereClause.append(" AND C_LocTo_ID=?");
            params.add(C_LocTo_ID);
        }
        if (C_SalesRegion_ID == 0) whereClause.append(" AND C_SalesRegion_ID IS NULL");
        else {
            whereClause.append(" AND C_SalesRegion_ID=?");
            params.add(C_SalesRegion_ID);
        }
        if (C_Project_ID == 0) whereClause.append(" AND C_Project_ID IS NULL");
        else {
            whereClause.append(" AND C_Project_ID=?");
            params.add(C_Project_ID);
        }
        if (C_Campaign_ID == 0) whereClause.append(" AND C_Campaign_ID IS NULL");
        else {
            whereClause.append(" AND C_Campaign_ID=?");
            params.add(C_Campaign_ID);
        }
        if (C_Activity_ID == 0) whereClause.append(" AND C_Activity_ID IS NULL");
        else {
            whereClause.append(" AND C_Activity_ID=?");
            params.add(C_Activity_ID);
        }
        if (User1_ID == 0) whereClause.append(" AND User1_ID IS NULL");
        else {
            whereClause.append(" AND User1_ID=?");
            params.add(User1_ID);
        }
        if (User2_ID == 0) whereClause.append(" AND User2_ID IS NULL");
        else {
            whereClause.append(" AND User2_ID=?");
            params.add(User2_ID);
        }
        if (UserElement1_ID == 0) whereClause.append(" AND UserElement1_ID IS NULL");
        else {
            whereClause.append(" AND UserElement1_ID=?");
            params.add(UserElement1_ID);
        }
        if (UserElement2_ID == 0) whereClause.append(" AND UserElement2_ID IS NULL");
        else {
            whereClause.append(" AND UserElement2_ID=?");
            params.add(UserElement2_ID);
        }
        //	whereClause.append(" ORDER BY IsFullyQualified DESC");

        MAccount existingAccount =
                new Query(ctx, MAccount.Table_Name, whereClause.toString())
                        .setParameters(params)
                        .setOnlyActiveRecords(true)
                        .firstOnly();

        //	Existing
        if (existingAccount != null) return existingAccount;

        //	New
        MAccount newAccount = new MAccount(ctx, 0);
        newAccount.setClientOrg(AD_Client_ID, AD_Org_ID);
        newAccount.setAccountingSchemaId(C_AcctSchema_ID);
        newAccount.setAccountId(Account_ID);
        //	--  Optional Accounting fields
        newAccount.setSubAccountId(C_SubAcct_ID);
        newAccount.setProductId(M_Product_ID);
        newAccount.setBusinessPartnerId(C_BPartner_ID);
        newAccount.setTransactionOrganizationId(AD_OrgTrx_ID);
        newAccount.setLocationFromId(C_LocFrom_ID);
        newAccount.setLocationToId(C_LocTo_ID);
        newAccount.setSalesRegionId(C_SalesRegion_ID);
        newAccount.setProjectId(C_Project_ID);
        newAccount.setCampaignId(C_Campaign_ID);
        newAccount.setBusinessActivityId(C_Activity_ID);
        newAccount.setUser1Id(User1_ID);
        newAccount.setUser2Id(User2_ID);
        newAccount.setUserElement1Id(UserElement1_ID);
        newAccount.setUserElement2Id(UserElement2_ID);
        //
        if (!newAccount.save()) {
            s_log.log(Level.SEVERE, "Could not create new account - " + info);
            return null;
        }
        if (s_log.isLoggable(Level.FINE)) s_log.fine("New: " + newAccount);
        return newAccount;
    } //	get

    /**
     * Get from existing Accounting fact
     *
     * @param fa accounting fact
     * @return account
     */
    public static MAccount get(X_Fact_Acct fa) {
        MAccount acct =
                get(
                        fa.getCtx(),
                        fa.getClientId(),
                        fa.getOrgId(),
                        fa.getAccountingSchemaId(),
                        fa.getAccountId(),
                        fa.getSubAccountId(),
                        fa.getProductId(),
                        fa.getBusinessPartnerId(),
                        fa.getTransactionOrganizationId(),
                        fa.getLocationFromId(),
                        fa.getLocationToId(),
                        fa.getSalesRegionId(),
                        fa.getProjectId(),
                        fa.getCampaignId(),
                        fa.getBusinessActivityId(),
                        fa.getUser1Id(),
                        fa.getUser2Id(),
                        fa.getUserElement1Id(),
                        fa.getUserElement2Id());
        return acct;
    } //	get

    /**
     * Factory: default combination
     *
     * @param acctSchema   accounting schema
     * @param optionalNull if true, the optional values are null
     * @return Account
     */
    public static MAccount getDefault(MAcctSchema acctSchema, boolean optionalNull) {
        MAccount vc = new MAccount(acctSchema);
        //  Active Elements
        MAcctSchemaElement[] elements = acctSchema.getAcctSchemaElements();
        for (int i = 0; i < elements.length; i++) {
            MAcctSchemaElement ase = elements[i];
            String elementType = ase.getElementType();
            int defaultValue = ase.getDefaultValue();
            boolean setValue = ase.isMandatory() || (!ase.isMandatory() && !optionalNull);
            //
            if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_Organization))
                vc.setOrgId(defaultValue);
            else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_Account))
                vc.setAccountId(defaultValue);
            else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_SubAccount) && setValue)
                vc.setSubAccountId(defaultValue);
            else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_BPartner) && setValue)
                vc.setBusinessPartnerId(defaultValue);
            else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_Product) && setValue)
                vc.setProductId(defaultValue);
            else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_Activity) && setValue)
                vc.setBusinessActivityId(defaultValue);
            else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_LocationFrom) && setValue)
                vc.setLocationFromId(defaultValue);
            else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_LocationTo) && setValue)
                vc.setLocationToId(defaultValue);
            else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_Campaign) && setValue)
                vc.setCampaignId(defaultValue);
            else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_OrgTrx) && setValue)
                vc.setTransactionOrganizationId(defaultValue);
            else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_Project) && setValue)
                vc.setProjectId(defaultValue);
            else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_SalesRegion) && setValue)
                vc.setSalesRegionId(defaultValue);
            else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_UserElementList1) && setValue)
                vc.setUser1Id(defaultValue);
            else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_UserElementList2) && setValue)
                vc.setUser2Id(defaultValue);
            else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_UserColumn1) && setValue)
                vc.setUserElement1Id(defaultValue);
            else if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_UserColumn2) && setValue)
                vc.setUserElement2Id(defaultValue);
        }
        if (s_log.isLoggable(Level.FINE))
            s_log.fine(
                    "Client_ID="
                            + vc.getClientId()
                            + ", Org_ID="
                            + vc.getOrgId()
                            + " - AcctSchema_ID="
                            + vc.getAccountingSchemaId()
                            + ", Account_ID="
                            + vc.getAccountId());
        return vc;
    } //  getDefault

    /**
     * Get Account
     *
     * @param ctx                   context
     * @param C_ValidCombination_ID combination
     * @return Account
     */
    public static MAccount get(Properties ctx, int C_ValidCombination_ID) {
        //	Maybe later cache
        return new MAccount(ctx, C_ValidCombination_ID);
    } //  getAccount

    /**
     * Update Value/Description after change of account element value/description.
     *
     * @param ctx     context
     * @param where   where clause
     * @param trxName transaction
     */
    public static void updateValueDescription(Properties ctx, final String where) {
        List<MAccount> accounts =
                new Query(ctx, MAccount.Table_Name, where)
                        .setOrderBy(MAccount.COLUMNNAME_C_ValidCombination_ID)
                        .list();

        for (MAccount account : accounts) {
            account.setValueDescription();
            account.saveEx();
        }
    } //	updateValueDescription

    /**
     * ************************************************************************ Return String
     * representation
     *
     * @return String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MAccount=[");
        sb.append(getValidAccountCombinationId());
        if (getCombination() != null) sb.append(",").append(getCombination());
        else {
            //	.append(",Client=").append( getClientId())
            sb.append(",Schema=")
                    .append(getAccountingSchemaId())
                    .append(",Org=")
                    .append(getOrgId())
                    .append(",Acct=")
                    .append(getAccountId())
                    .append(" ");
            if (getSubAccountId() != 0) sb.append(",C_SubAcct_ID=").append(getSubAccountId());
            if (getProductId() != 0) sb.append(",M_Product_ID=").append(getProductId());
            if (getBusinessPartnerId() != 0) sb.append(",C_BPartner_ID=").append(getBusinessPartnerId());
            if (getTransactionOrganizationId() != 0) sb.append(",AD_OrgTrx_ID=").append(getTransactionOrganizationId());
            if (getLocationFromId() != 0) sb.append(",C_LocFrom_ID=").append(getLocationFromId());
            if (getLocationToId() != 0) sb.append(",C_LocTo_ID=").append(getLocationToId());
            if (getSalesRegionId() != 0) sb.append(",C_SalesRegion_ID=").append(getSalesRegionId());
            if (getProjectId() != 0) sb.append(",C_Project_ID=").append(getProjectId());
            if (getCampaignId() != 0) sb.append(",C_Campaign_ID=").append(getCampaignId());
            if (getBusinessActivityId() != 0) sb.append(",C_Activity_ID=").append(getBusinessActivityId());
            if (getUser1Id() != 0) sb.append(",User1_ID=").append(getUser1Id());
            if (getUser2Id() != 0) sb.append(",User2_ID=").append(getUser2Id());
            if (getUserElement1Id() != 0) sb.append(",UserElement1_ID=").append(getUserElement1Id());
            if (getUserElement2Id() != 0) sb.append(",UserElement2_ID=").append(getUserElement2Id());
        }
        sb.append("]");
        return sb.toString();
    } //	toString

    /**
     * Set Account_ID
     *
     * @param Account_ID id
     */
    public void setAccountId(int Account_ID) {
        m_accountEV = null; // 	reset
        super.setAccountId(Account_ID);
    } //	setAccount

    /**
     * Set Account_ID
     *
     * @return element value
     */
    public MElementValue getAccount() {
        if (m_accountEV == null) {
            if (getAccountId() != 0)
                m_accountEV = new MElementValue(getCtx(), getAccountId());
        }
        return m_accountEV;
    } //	setAccount

    /**
     * Get Account Type
     *
     * @return Account Type of Account Element
     */
    public String getAccountType() {
        if (m_accountEV == null) getAccount();
        if (m_accountEV == null) {
            log.log(Level.SEVERE, "No ElementValue for Account_ID=" + getAccountId());
            return "";
        }
        return m_accountEV.getAccountType();
    } //	getAccountType

    /**
     * Is this a Balance Sheet Account
     *
     * @return boolean
     */
    public boolean isBalanceSheet() {
        String accountType = getAccountType();
        return (MElementValue.ACCOUNTTYPE_Asset.equals(accountType)
                || MElementValue.ACCOUNTTYPE_Liability.equals(accountType)
                || MElementValue.ACCOUNTTYPE_OwnerSEquity.equals(accountType));
    } //	isBalanceSheet

    /**
     * Is this an Activa Account
     *
     * @return boolean
     */
    public boolean isActiva() {
        return MElementValue.ACCOUNTTYPE_Asset.equals(getAccountType());
    } //	isActive

    /**
     * Set Value and Description and Fully Qualified Flag for Combination
     */
    public void setValueDescription() {
        StringBuilder combi = new StringBuilder();
        StringBuilder descr = new StringBuilder();
        boolean fullyQualified = true;
        //
        MAcctSchema as = new MAcctSchema(getCtx(), getAccountingSchemaId()); // 	In Trx!
        MAcctSchemaElement[] elements = MAcctSchemaElement.getAcctSchemaElements(as);
        for (int i = 0; i < elements.length; i++) {
            if (i > 0) {
                combi.append(as.getSeparator());
                descr.append(as.getSeparator());
            }
            MAcctSchemaElement element = elements[i];
            String combiStr = "_"; // 	not defined
            String descrStr = "_";

            if (MAcctSchemaElement.ELEMENTTYPE_Organization.equals(element.getElementType())) {
                if (getOrgId() != 0) {
                    MOrg org = new MOrg(getCtx(), getOrgId()); // 	in Trx!
                    combiStr = org.getSearchKey();
                    descrStr = org.getName();
                } else {
                    combiStr = "*";
                    descrStr = "*";
                    // fullyQualified = false; IDEMPIERE 159 - allow combination with org *
                }
            } else if (MAcctSchemaElement.ELEMENTTYPE_Account.equals(element.getElementType())) {
                if (getAccountId() != 0) {
                    if (m_accountEV == null)
                        m_accountEV = new MElementValue(getCtx(), getAccountId());
                    combiStr = m_accountEV.getSearchKey();
                    descrStr = m_accountEV.getName();
                } else if (element.isMandatory()) {
                    log.warning("Mandatory Element missing: Account");
                    fullyQualified = false;
                }
            } else if (MAcctSchemaElement.ELEMENTTYPE_SubAccount.equals(element.getElementType())) {
                if (getSubAccountId() != 0) {
                    X_C_SubAcct sa = new X_C_SubAcct(getCtx(), getSubAccountId());
                    combiStr = sa.getSearchKey();
                    descrStr = sa.getName();
                }
            } else if (MAcctSchemaElement.ELEMENTTYPE_Product.equals(element.getElementType())) {
                if (getProductId() != 0) {
                    X_M_Product product = new X_M_Product(getCtx(), getProductId());
                    combiStr = product.getSearchKey();
                    descrStr = product.getName();
                } else if (element.isMandatory()) {
                    log.warning("Mandatory Element missing: Product");
                    fullyQualified = false;
                }
            } else if (MAcctSchemaElement.ELEMENTTYPE_BPartner.equals(element.getElementType())) {
                if (getBusinessPartnerId() != 0) {
                    X_C_BPartner partner = new X_C_BPartner(getCtx(), getBusinessPartnerId());
                    combiStr = partner.getSearchKey();
                    descrStr = partner.getName();
                } else if (element.isMandatory()) {
                    log.warning("Mandatory Element missing: Business Partner");
                    fullyQualified = false;
                }
            } else if (MAcctSchemaElement.ELEMENTTYPE_OrgTrx.equals(element.getElementType())) {
                if (getTransactionOrganizationId() != 0) {
                    MOrg org = new MOrg(getCtx(), getTransactionOrganizationId()); // in Trx!
                    combiStr = org.getSearchKey();
                    descrStr = org.getName();
                } else if (element.isMandatory()) {
                    log.warning("Mandatory Element missing: Trx Org");
                    fullyQualified = false;
                }
            } else if (MAcctSchemaElement.ELEMENTTYPE_LocationFrom.equals(element.getElementType())) {
                if (getLocationFromId() != 0) {
                    MLocation loc = new MLocation(getCtx(), getLocationFromId()); // 	in Trx!
                    combiStr = loc.getPostal();
                    descrStr = loc.getCity();
                } else if (element.isMandatory()) {
                    log.warning("Mandatory Element missing: Location From");
                    fullyQualified = false;
                }
            } else if (MAcctSchemaElement.ELEMENTTYPE_LocationTo.equals(element.getElementType())) {
                if (getLocationToId() != 0) {
                    MLocation loc = new MLocation(getCtx(), getLocationFromId()); // 	in Trx!
                    combiStr = loc.getPostal();
                    descrStr = loc.getCity();
                } else if (element.isMandatory()) {
                    log.warning("Mandatory Element missing: Location To");
                    fullyQualified = false;
                }
            } else if (MAcctSchemaElement.ELEMENTTYPE_SalesRegion.equals(element.getElementType())) {
                if (getSalesRegionId() != 0) {
                    MSalesRegion loc = new MSalesRegion(getCtx(), getSalesRegionId());
                    combiStr = loc.getSearchKey();
                    descrStr = loc.getName();
                } else if (element.isMandatory()) {
                    log.warning("Mandatory Element missing: SalesRegion");
                    fullyQualified = false;
                }
            } else if (MAcctSchemaElement.ELEMENTTYPE_Project.equals(element.getElementType())) {
                if (getProjectId() != 0) {
                    X_C_Project project = new X_C_Project(getCtx(), getProjectId());
                    combiStr = project.getSearchKey();
                    descrStr = project.getName();
                } else if (element.isMandatory()) {
                    log.warning("Mandatory Element missing: Project");
                    fullyQualified = false;
                }
            } else if (MAcctSchemaElement.ELEMENTTYPE_Campaign.equals(element.getElementType())) {
                if (getCampaignId() != 0) {
                    X_C_Campaign campaign = new X_C_Campaign(getCtx(), getCampaignId());
                    combiStr = campaign.getSearchKey();
                    descrStr = campaign.getName();
                } else if (element.isMandatory()) {
                    log.warning("Mandatory Element missing: Campaign");
                    fullyQualified = false;
                }
            } else if (MAcctSchemaElement.ELEMENTTYPE_Activity.equals(element.getElementType())) {
                if (getBusinessActivityId() != 0) {
                    X_C_Activity act = new X_C_Activity(getCtx(), getBusinessActivityId());
                    combiStr = act.getSearchKey();
                    descrStr = act.getName();
                } else if (element.isMandatory()) {
                    log.warning("Mandatory Element missing: Campaign");
                    fullyQualified = false;
                }
            } else if (MAcctSchemaElement.ELEMENTTYPE_UserElementList1.equals(element.getElementType())) {
                if (getUser1Id() != 0) {
                    MElementValue ev = new MElementValue(getCtx(), getUser1Id());
                    combiStr = ev.getSearchKey();
                    descrStr = ev.getName();
                }
            } else if (MAcctSchemaElement.ELEMENTTYPE_UserElementList2.equals(element.getElementType())) {
                if (getUser2Id() != 0) {
                    MElementValue ev = new MElementValue(getCtx(), getUser2Id());
                    combiStr = ev.getSearchKey();
                    descrStr = ev.getName();
                }
            } else if (MAcctSchemaElement.ELEMENTTYPE_UserColumn1.equals(element.getElementType())) {
                getUserElement1Id();
            } else if (MAcctSchemaElement.ELEMENTTYPE_UserColumn2.equals(element.getElementType())) {
                getUserElement2Id();
            }
            combi.append(combiStr);
            descr.append(descrStr);
        }
        //	Set Values
        super.setCombination(combi.toString());
        super.setDescription(descr.toString());
        if (fullyQualified != isFullyQualified()) setIsFullyQualified(fullyQualified);
        if (log.isLoggable(Level.FINE))
            log.fine(
                    "Combination="
                            + getCombination()
                            + " - "
                            + getDescription()
                            + " - FullyQualified="
                            + fullyQualified);
    } //	setValueDescription

    /**
     * Validate combination
     *
     * @return true if valid
     */
    public boolean validate() {
        boolean ok = true;
        //	Validate Sub-Account
        if (getSubAccountId() != 0) {
            X_C_SubAcct sa = new X_C_SubAcct(getCtx(), getSubAccountId());
            if (sa.getElementValueId() != getAccountId()) {
                log.saveError(
                        "Error",
                        "C_SubAcct.C_ElementValue_ID="
                                + sa.getElementValueId()
                                + "<>Account_ID="
                                + getAccountId());
                ok = false;
            }
        }
        return ok;
    } //	validate

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        setValueDescription();
        return validate();
    } //	beforeSave
} //	Account
