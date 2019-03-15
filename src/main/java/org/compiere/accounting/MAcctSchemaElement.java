package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_AcctSchema_Element;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.orm.MColumn;
import org.compiere.orm.Query;
import org.compiere.util.Msg;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdate;

/**
 * Account Schema Element Object
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * <li>BF [ 1795817 ] Acct Schema Elements "Account" and "Org" should be mandatory
 * @author victor.perez@e-evolution.com, www.e-evolution.com
 * <li>RF [ 2214883 ] Remove SQL code and Replace for Query
 * http://sourceforge.net/tracker/index.php?func=detail&aid=2214883&group_id=176962&atid=879335
 * @version $Id: MAcctSchemaElement.java,v 1.4 2006/08/10 01:00:44 jjanke Exp $
 */
public class MAcctSchemaElement extends X_C_AcctSchema_Element {

    /**
     *
     */
    private static final long serialVersionUID = 4215184252533527719L;
    /**
     * Logger
     */
    private static CLogger s_log = CLogger.getCLogger(MAcctSchemaElement.class);
    /**
     * Cache
     */
    private static CCache<Integer, MAcctSchemaElement[]> s_cache =
            new CCache<Integer, MAcctSchemaElement[]>(I_C_AcctSchema_Element.Table_Name, 10);
    /**
     * User Element Column Name
     */
    private String m_ColumnName = null;

    /**
     * *********************************************************************** Standard Constructor
     *
     * @param ctx                     context
     * @param C_AcctSchema_Element_ID id
     * @param trxName                 transaction
     */
    public MAcctSchemaElement(Properties ctx, int C_AcctSchema_Element_ID) {
        super(ctx, C_AcctSchema_Element_ID);
        if (C_AcctSchema_Element_ID == 0) {
            //	setC_AcctSchema_Element_ID (0);
            //	setAccountingSchemaId (0);
            //	setElementId (0);
            //	setElementType (null);
            setIsBalanced(false);
            setIsMandatory(false);
            //	setName (null);
            //	setOrg_ID (0);
            //	setSeqNo (0);
        }
    } //	MAcctSchemaElement

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MAcctSchemaElement(Properties ctx, Row row) {
        super(ctx, row);
    } //	MAcctSchemaElement

    /**
     * Parent Constructor
     *
     * @param as accounting schema
     */
    public MAcctSchemaElement(MAcctSchema as) {
        this(as.getCtx(), 0);
        setClientOrg(as);
        setC_AcctSchema_ID(as.getAccountingSchemaId());

        //	setElementId (0);
        //	setElementType (null);
        //	setName (null);
        //	setSeqNo (0);

    } //	MAcctSchemaElement

    /**
     * Factory: Return ArrayList of Account Schema Elements
     *
     * @param as Accounting Schema
     * @return ArrayList with Elements
     */
    public static MAcctSchemaElement[] getAcctSchemaElements(MAcctSchema as) {
        Integer key = new Integer(as.getAccountingSchemaId());
        MAcctSchemaElement[] retValue = (MAcctSchemaElement[]) s_cache.get(key);
        if (retValue != null) return retValue;

        if (s_log.isLoggable(Level.FINE)) s_log.fine("C_AcctSchema_ID=" + as.getAccountingSchemaId());
        ArrayList<MAcctSchemaElement> list = new ArrayList<MAcctSchemaElement>();

        final String whereClause = "C_AcctSchema_ID=? AND IsActive=?";
        List<MAcctSchemaElement> elements =
                new Query(as.getCtx(), I_C_AcctSchema_Element.Table_Name, whereClause)
                        .setParameters(as.getAccountingSchemaId(), "Y")
                        .setOrderBy("SeqNo")
                        .list();

        for (MAcctSchemaElement ase : elements) {
            if (s_log.isLoggable(Level.FINE)) s_log.fine(" - " + ase);
            if (ase.isMandatory() && ase.getDefaultValue() == 0)
                s_log.log(Level.SEVERE, "No default value for " + ase.getName());
            list.add(ase);
        }

        retValue = new MAcctSchemaElement[list.size()];
        list.toArray(retValue);
        s_cache.put(key, retValue);
        return retValue;
    } //  getAcctSchemaElements

    /**
     * Get Column Name of ELEMENTTYPE
     *
     * @param elementType ELEMENTTYPE
     * @return column name or "" if not found
     */
    public static String getColumnName(String elementType) {
        if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Organization)) return "AD_Org_ID";
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Account))
            return I_C_ValidCombination.COLUMNNAME_Account_ID;
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_BPartner))
            return I_C_ValidCombination.COLUMNNAME_C_BPartner_ID;
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Product))
            return I_C_ValidCombination.COLUMNNAME_M_Product_ID;
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Activity))
            return I_C_ValidCombination.COLUMNNAME_C_Activity_ID;
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_LocationFrom))
            return I_C_ValidCombination.COLUMNNAME_C_LocFrom_ID;
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_LocationTo))
            return I_C_ValidCombination.COLUMNNAME_C_LocTo_ID;
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Campaign))
            return I_C_ValidCombination.COLUMNNAME_C_Campaign_ID;
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_OrgTrx))
            return I_C_ValidCombination.COLUMNNAME_AD_OrgTrx_ID;
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Project))
            return I_C_ValidCombination.COLUMNNAME_C_Project_ID;
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_SalesRegion))
            return I_C_ValidCombination.COLUMNNAME_C_SalesRegion_ID;
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserElementList1))
            return I_C_ValidCombination.COLUMNNAME_User1_ID;
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserElementList2))
            return I_C_ValidCombination.COLUMNNAME_User2_ID;
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserColumn1))
            return I_C_ValidCombination.COLUMNNAME_UserElement1_ID;
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserColumn2))
            return I_C_ValidCombination.COLUMNNAME_UserElement2_ID;
        //
        return "";
    } //  getColumnName

    /**
     * Get Default element value
     *
     * @return default
     */
    public int getDefaultValue() {
        String elementType = getElementType();
        int defaultValue = 0;
        if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Organization))
            defaultValue = getOrg_ID();
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Account))
            defaultValue = getC_ElementValue_ID();
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_BPartner))
            defaultValue = getBusinessPartnerId();
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Product))
            defaultValue = getM_Product_ID();
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Activity))
            defaultValue = getBusinessActivityId();
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_LocationFrom))
            defaultValue = getLocationId();
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_LocationTo))
            defaultValue = getLocationId();
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Campaign))
            defaultValue = getCampaignId();
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_OrgTrx))
            defaultValue = getOrg_ID();
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_Project))
            defaultValue = getProjectId();
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_SalesRegion))
            defaultValue = getC_SalesRegion_ID();
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserElementList1))
            defaultValue = getC_ElementValue_ID();
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserElementList2))
            defaultValue = getC_ElementValue_ID();
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserColumn1)) defaultValue = 0;
        else if (elementType.equals(X_C_AcctSchema_Element.ELEMENTTYPE_UserColumn2)) defaultValue = 0;
        return defaultValue;
    } //	getDefault

    /**
     * Get Display ColumnName
     *
     * @return column name
     */
    public String getDisplayColumnName() {
        String et = getElementType();
        if (X_C_AcctSchema_Element.ELEMENTTYPE_UserColumn1.equals(et)
                || X_C_AcctSchema_Element.ELEMENTTYPE_UserColumn2.equals(et)) {
            if (m_ColumnName == null) m_ColumnName = MColumn.getColumnName(getCtx(), getAD_Column_ID());
            return m_ColumnName;
        }
        return getColumnName(et);
    } //	getDisplayColumnName

    /**
     * String representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder msgreturn =
                new StringBuilder("AcctSchemaElement[")
                        .append(getId())
                        .append("-")
                        .append(getName())
                        .append("(")
                        .append(getElementType())
                        .append(")=")
                        .append(getDefaultValue())
                        .append(",Pos=")
                        .append(getSeqNo())
                        .append("]");
        return msgreturn.toString();
    } //  toString

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true if it can be saved
     */
    @Override
    protected boolean beforeSave(boolean newRecord) {
        if (getOrgId() != 0) setOrgId(0);
        String et = getElementType();
        if (isMandatory()
                && (X_C_AcctSchema_Element.ELEMENTTYPE_UserElementList1.equals(et)
                || X_C_AcctSchema_Element.ELEMENTTYPE_UserElementList2.equals(et)
                || X_C_AcctSchema_Element.ELEMENTTYPE_UserColumn1.equals(et)
                || X_C_AcctSchema_Element.ELEMENTTYPE_UserColumn2.equals(et))) setIsMandatory(false);
        // Acct Schema Elements "Account" and "Org" should be mandatory - teo_sarca BF [ 1795817 ]
        if (X_C_AcctSchema_Element.ELEMENTTYPE_Account.equals(et)
                || X_C_AcctSchema_Element.ELEMENTTYPE_Organization.equals(et)) {
            if (!isMandatory()) setIsMandatory(true);
            if (!isActive()) setIsActive(true);
        }
        //
        else if (isMandatory()) {
            String errorField = null;
            if (X_C_AcctSchema_Element.ELEMENTTYPE_Account.equals(et) && getC_ElementValue_ID() == 0)
                errorField = I_C_AcctSchema_Element.COLUMNNAME_C_ElementValue_ID;
            else if (X_C_AcctSchema_Element.ELEMENTTYPE_Activity.equals(et) && getBusinessActivityId() == 0)
                errorField = I_C_AcctSchema_Element.COLUMNNAME_C_Activity_ID;
            else if (X_C_AcctSchema_Element.ELEMENTTYPE_BPartner.equals(et) && getBusinessPartnerId() == 0)
                errorField = I_C_AcctSchema_Element.COLUMNNAME_C_BPartner_ID;
            else if (X_C_AcctSchema_Element.ELEMENTTYPE_Campaign.equals(et) && getCampaignId() == 0)
                errorField = I_C_AcctSchema_Element.COLUMNNAME_C_Campaign_ID;
            else if (X_C_AcctSchema_Element.ELEMENTTYPE_LocationFrom.equals(et)
                    && getLocationId() == 0) errorField = I_C_AcctSchema_Element.COLUMNNAME_C_Location_ID;
            else if (X_C_AcctSchema_Element.ELEMENTTYPE_LocationTo.equals(et) && getLocationId() == 0)
                errorField = I_C_AcctSchema_Element.COLUMNNAME_C_Location_ID;
            else if (X_C_AcctSchema_Element.ELEMENTTYPE_Organization.equals(et) && getOrg_ID() == 0)
                errorField = I_C_AcctSchema_Element.COLUMNNAME_Org_ID;
            else if (X_C_AcctSchema_Element.ELEMENTTYPE_OrgTrx.equals(et) && getOrg_ID() == 0)
                errorField = I_C_AcctSchema_Element.COLUMNNAME_Org_ID;
            else if (X_C_AcctSchema_Element.ELEMENTTYPE_Product.equals(et) && getM_Product_ID() == 0)
                errorField = I_C_AcctSchema_Element.COLUMNNAME_M_Product_ID;
            else if (X_C_AcctSchema_Element.ELEMENTTYPE_Project.equals(et) && getProjectId() == 0)
                errorField = I_C_AcctSchema_Element.COLUMNNAME_C_Project_ID;
            else if (X_C_AcctSchema_Element.ELEMENTTYPE_SalesRegion.equals(et)
                    && getC_SalesRegion_ID() == 0)
                errorField = I_C_AcctSchema_Element.COLUMNNAME_C_SalesRegion_ID;
            if (errorField != null) {
                log.saveError(
                        "Error", Msg.parseTranslation(getCtx(), "@IsMandatory@: @" + errorField + "@"));
                return false;
            }
        }
        //
        if (getAD_Column_ID() == 0
                && (X_C_AcctSchema_Element.ELEMENTTYPE_UserColumn1.equals(et)
                || X_C_AcctSchema_Element.ELEMENTTYPE_UserColumn2.equals(et))) {
            log.saveError("Error", Msg.parseTranslation(getCtx(), "@IsMandatory@: @AD_Column_ID@"));
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
    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) return success;
        //	Default Value
        if (isMandatory() && is_ValueChanged(I_C_AcctSchema_Element.COLUMNNAME_IsMandatory)) {
            if (X_C_AcctSchema_Element.ELEMENTTYPE_Activity.equals(getElementType()))
                updateData(I_C_AcctSchema_Element.COLUMNNAME_C_Activity_ID, getBusinessActivityId());
            else if (X_C_AcctSchema_Element.ELEMENTTYPE_BPartner.equals(getElementType()))
                updateData(I_C_AcctSchema_Element.COLUMNNAME_C_BPartner_ID, getBusinessPartnerId());
            else if (X_C_AcctSchema_Element.ELEMENTTYPE_Product.equals(getElementType()))
                updateData(I_C_AcctSchema_Element.COLUMNNAME_M_Product_ID, getM_Product_ID());
            else if (X_C_AcctSchema_Element.ELEMENTTYPE_Project.equals(getElementType()))
                updateData(I_C_AcctSchema_Element.COLUMNNAME_C_Project_ID, getProjectId());
        }

        //	Clear Cache
        s_cache.clear();

        //	Resequence
        if (newRecord || is_ValueChanged(I_C_AcctSchema_Element.COLUMNNAME_SeqNo)) {
            StringBuilder msguvd = new StringBuilder("AD_Client_ID=").append(getClientId());
            MAccount.updateValueDescription(getCtx(), msguvd.toString());
        }
        return success;
    } //	afterSave

    /**
     * Update ValidCombination and Fact with mandatory value
     *
     * @param element element
     * @param id      new default
     */
    private void updateData(String element, int id) {
        StringBuilder msguvd = new StringBuilder(element).append("=").append(id);
        MAccount.updateValueDescription(getCtx(), msguvd.toString());
        //
        StringBuilder sql =
                new StringBuilder("UPDATE C_ValidCombination SET ")
                        .append(element)
                        .append("=")
                        .append(id)
                        .append(" WHERE ")
                        .append(element)
                        .append(" IS NULL AND AD_Client_ID=")
                        .append(getClientId());
        int noC = executeUpdate(sql.toString());
        //
        sql =
                new StringBuilder("UPDATE Fact_Acct SET ")
                        .append(element)
                        .append("=")
                        .append(id)
                        .append(" WHERE ")
                        .append(element)
                        .append(" IS NULL AND C_AcctSchema_ID=")
                        .append(getC_AcctSchema_ID());
        int noF = executeUpdate(sql.toString());
        //
        if (log.isLoggable(Level.FINE)) log.fine("ValidCombination=" + noC + ", Fact=" + noF);
    } //	updateData

    @Override
    protected boolean beforeDelete() {
        String et = getElementType();
        // Acct Schema Elements "Account" and "Org" should be mandatory - teo_sarca BF [ 1795817 ]
        if (X_C_AcctSchema_Element.ELEMENTTYPE_Account.equals(et)
                || X_C_AcctSchema_Element.ELEMENTTYPE_Organization.equals(et)) {
            log.saveError("Error", Msg.parseTranslation(getCtx(), "@DeleteError@ @IsMandatory@"));
            return false;
        }
        return true;
    }

    /**
     * After Delete
     *
     * @param success success
     * @return success
     */
    @Override
    protected boolean afterDelete(boolean success) {
        if (!success) return success;
        //	Update Account Info
        MAccount.updateValueDescription(getCtx(), "AD_Client_ID=" + getClientId());
        //
        s_cache.clear();
        return success;
    } //	afterDelete
} //  AcctSchemaElement
