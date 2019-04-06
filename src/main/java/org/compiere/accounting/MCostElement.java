package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.orm.MRefList;
import org.compiere.orm.Query;
import org.compiere.util.Msg;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Env;
import org.idempiere.icommon.model.IPO;

import java.util.List;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.getSQLValue;

/**
 * Cost Element Model
 *
 * @author Jorg Janke
 * @author Teo Sarca, www.arhipac.ro
 * <li>BF [ 2664529 ] More then one Labor/Burden//Overhead is not allowed
 * <li>BF [ 2667470 ] MCostElement.getMaterialCostElement should check only material
 * @author red1
 * <li>FR: [ 2214883 ] Remove SQL code and Replace for Query -- JUnit tested
 * @version $Id: MCostElement.java,v 1.2 2006/07/30 00:58:04 jjanke Exp $
 */
public class MCostElement extends X_M_CostElement {

    /**
     *
     */
    private static final long serialVersionUID = 3196322266971717530L;
    /**
     * Cache
     */
    private static CCache<Integer, MCostElement> s_cache =
            new CCache<Integer, MCostElement>(I_M_CostElement.Table_Name, 20);
    /**
     * Logger
     */
    private static CLogger s_log = CLogger.getCLogger(MCostElement.class);

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx              context
     * @param M_CostElement_ID id
     */
    public MCostElement(int M_CostElement_ID) {
        super(M_CostElement_ID);
        if (M_CostElement_ID == 0) {
            //	setName (null);
            setCostElementType(X_M_CostElement.COSTELEMENTTYPE_Material);
            setIsCalculated(false);
        }
    } //	MCostElement

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MCostElement(Row row) {
        super(row);
    } //	MCostElement

    // MZ Goodwill
    // end MZ

    /**
     * Get Material Cost Element or create it
     *
     * @param po            parent
     * @param CostingMethod method
     * @return cost element
     */
    public static MCostElement getMaterialCostElement(IPO po, String CostingMethod) {
        if (CostingMethod == null || CostingMethod.length() == 0) {
            s_log.severe("No CostingMethod");
            return null;
        }
        //
        final String whereClause = "AD_Client_ID=? AND CostingMethod=? AND CostElementType=?";
        MCostElement retValue =
                new Query(I_M_CostElement.Table_Name, whereClause)
                        .setParameters(
                                po.getClientId(), CostingMethod, X_M_CostElement.COSTELEMENTTYPE_Material)
                        .setOrderBy("AD_Org_ID")
                        .firstOnly();
        if (retValue != null) return retValue;

        //	Create New
        retValue = new MCostElement(0);
        retValue.setClientOrg(po.getClientId(), 0);
        String name =
                MRefList.getListName(
                        X_M_CostElement.COSTINGMETHOD_AD_Reference_ID, CostingMethod);
        if (name == null || name.length() == 0) name = CostingMethod;
        retValue.setName(name);
        retValue.setCostElementType(X_M_CostElement.COSTELEMENTTYPE_Material);
        retValue.setCostingMethod(CostingMethod);
        retValue.saveEx();

        //
        return retValue;
    } //	getMaterialCostElement

    /**
     * Get first Material Cost Element
     *
     * @param ctx           context
     * @param CostingMethod costing method
     * @return Cost Element or null
     */
    public static MCostElement getMaterialCostElement(String CostingMethod) {
        final String whereClause = "AD_Client_ID=? AND CostingMethod=? AND CostElementType=?";
        List<MCostElement> list =
                new Query(I_M_CostElement.Table_Name, whereClause)
                        .setParameters(
                                Env.getClientId(), CostingMethod, X_M_CostElement.COSTELEMENTTYPE_Material)
                        .setOrderBy(I_M_CostElement.COLUMNNAME_AD_Org_ID)
                        .list();
        MCostElement retValue = null;
        if (list.size() > 0) retValue = list.get(0);
        if (list.size() > 1)
            if (s_log.isLoggable(Level.INFO))
                s_log.info("More then one Material Cost Element for CostingMethod=" + CostingMethod);
        return retValue;
    } //	getMaterialCostElement

    /**
     * Get first Material Cost Element
     *
     * @param ctx           context
     * @param CostingMethod costing method
     * @return Cost Element or null
     */
    public static MCostElement getMaterialCostElement(
            String CostingMethod, int AD_Org_ID) {
        final String whereClause =
                "AD_Client_ID=? AND CostingMethod=? AND CostElementType=? AND orgId In (0, ?)";
        List<MCostElement> list =
                new Query(I_M_CostElement.Table_Name, whereClause)
                        .setParameters(
                                Env.getClientId(),
                                CostingMethod,
                                X_M_CostElement.COSTELEMENTTYPE_Material,
                                AD_Org_ID)
                        .setOrderBy(I_M_CostElement.COLUMNNAME_AD_Org_ID + " Desc")
                        .list();
        MCostElement retValue = null;
        if (list.size() > 0) retValue = list.get(0);
        if (list.size() > 1)
            if (s_log.isLoggable(Level.INFO))
                s_log.info("More then one Material Cost Element for CostingMethod=" + CostingMethod);
        return retValue;
    } //	getMaterialCostElement

    /**
     * Get active Material Cost Element for client
     *
     * @param po parent
     * @return cost element array
     */
    public static List<MCostElement> getCostElementsWithCostingMethods(IPO po) {
        final String whereClause = "AD_Client_ID=? AND CostingMethod IS NOT NULL";
        return new Query(MCostElement.Table_Name, whereClause)
                .setParameters(po.getClientId())
                .setOnlyActiveRecords(true)
                .list();
    } //	getCostElementCostingMethod

    /**
     * Get active Material Cost Element for client
     *
     * @param po parent
     * @return cost element array
     */
    public static MCostElement[] getCostingMethods(IPO po) {
        final String whereClause = "AD_Client_ID=? AND CostElementType=? AND CostingMethod IS NOT NULL";
        List<MCostElement> list =
                new Query(I_M_CostElement.Table_Name, whereClause)
                        .setParameters(po.getClientId(), X_M_CostElement.COSTELEMENTTYPE_Material)
                        .setOnlyActiveRecords(true)
                        .list();
        //
        MCostElement[] retValue = new MCostElement[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	getMaterialCostElement

    /**
     * Get Cost Element from Cache
     *
     * @param ctx              context
     * @param M_CostElement_ID id
     * @return Cost Element
     */
    public static MCostElement get(int M_CostElement_ID) {
        Integer key = new Integer(M_CostElement_ID);
        MCostElement retValue = s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MCostElement(M_CostElement_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * Get All Cost Elements for current clientId
     *
     * @return array cost elements
     */
    public static MCostElement[] getElements() {
        int AD_Org_ID = 0; // Org is always ZERO - see beforeSave

        final String whereClause = "AD_Client_ID = ? AND AD_Org_ID=?";
        List<MCostElement> list =
                new Query(I_M_CostElement.Table_Name, whereClause)
                        .setParameters(Env.getClientId(), AD_Org_ID)
                        .list();
        MCostElement[] retValue = new MCostElement[list.size()];
        list.toArray(retValue);
        return retValue;
    }

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        //	Check Unique Costing Method
        if ((X_M_CostElement.COSTELEMENTTYPE_Material.equals(getCostElementType())
                //			|| COSTELEMENTTYPE_Resource.equals(getCostElementType())
                //			|| COSTELEMENTTYPE_BurdenMOverhead.equals(getCostElementType())
                //			|| COSTELEMENTTYPE_Overhead.equals(getCostElementType())
                || X_M_CostElement.COSTELEMENTTYPE_OutsideProcessing.equals(getCostElementType()))
                && (newRecord || isValueChanged(I_M_CostElement.COLUMNNAME_CostingMethod))) {
            String sql =
                    "SELECT  COALESCE(MAX(M_CostElement_ID),0) FROM M_CostElement "
                            + "WHERE AD_Client_ID=? AND CostingMethod=? AND CostElementType=?";
            int id =
                    getSQLValue(
                            sql, getClientId(), getCostingMethod(), getCostElementType());
            if (id > 0 && id != getId()) {
                log.saveError("AlreadyExists", Msg.getElement("CostingMethod"));
                return false;
            }
        }

        //	Maintain Calculated
    /*
    if (COSTELEMENTTYPE_Material.equals(getCostElementType()))
    {
    	String cm = getCostingMethod();
    	if (cm == null || cm.length() == 0
    		|| COSTINGMETHOD_StandardCosting.equals(cm))
    		setIsCalculated(false);
    	else
    		setIsCalculated(true);
    }
    else
    {
    	if (isCalculated())
    		setIsCalculated(false);
    	if (getCostingMethod() != null)
    		setCostingMethod(null);
    }*/

        if (getOrgId() != 0) setOrgId(0);
        return true;
    } //	beforeSave

    /**
     * Before Delete
     *
     * @return true if can be deleted
     */
    protected boolean beforeDelete() {
        String cm = getCostingMethod();
        if (cm == null || !X_M_CostElement.COSTELEMENTTYPE_Material.equals(getCostElementType()))
            return true;

        //	Costing Methods on AS level
        MAcctSchema[] ass = MAcctSchema.getClientAcctSchema(getClientId());
        for (int i = 0; i < ass.length; i++) {
            if (ass[i].getCostingMethod().equals(getCostingMethod())) {
                log.saveError(
                        "CannotDeleteUsed",
                        Msg.getElement("C_AcctSchema_ID") + " - " + ass[i].getName());
                return false;
            }
        }

        //	Costing Methods on PC level
        int M_Product_Category_ID = 0;
        final String whereClause = "AD_Client_ID=? AND CostingMethod=?";
        MProductCategoryAcct retValue =
                new Query(I_M_Product_Category_Acct.Table_Name, whereClause)
                        .setParameters(getClientId(), getCostingMethod())
                        .first();
        if (retValue != null) M_Product_Category_ID = retValue.getProductCategoryId();
        if (M_Product_Category_ID != 0) {
            log.saveError(
                    "CannotDeleteUsed",
                    Msg.getElement("M_Product_Category_ID")
                            + " (ID="
                            + M_Product_Category_ID
                            + ")");
            return false;
        }
        return true;
    } //	beforeDelete

    /**
     * Is this a Costing Method
     *
     * @return true if not Material cost or no costing method.
     */
    public boolean isCostingMethod() {
        return X_M_CostElement.COSTELEMENTTYPE_Material.equals(getCostElementType())
                && getCostingMethod() != null;
    } //	isCostingMethod

    /**
     * Is Avg Invoice Costing Method
     *
     * @return true if AverageInvoice
     */
    public boolean isAverageInvoice() {
        String cm = getCostingMethod();
        return cm != null
                && cm.equals(X_M_CostElement.COSTINGMETHOD_AverageInvoice)
                && X_M_CostElement.COSTELEMENTTYPE_Material.equals(getCostElementType());
    } //	isAverageInvoice

    /**
     * Is Avg PO Costing Method
     *
     * @return true if AveragePO
     */
    public boolean isAveragePO() {
        String cm = getCostingMethod();
        return cm != null
                && cm.equals(X_M_CostElement.COSTINGMETHOD_AveragePO)
                && X_M_CostElement.COSTELEMENTTYPE_Material.equals(getCostElementType());
    } //	isAveragePO

    /**
     * Is FiFo Costing Method
     *
     * @return true if Fifo
     */
    public boolean isFifo() {
        String cm = getCostingMethod();
        return cm != null
                && cm.equals(X_M_CostElement.COSTINGMETHOD_Fifo)
                && X_M_CostElement.COSTELEMENTTYPE_Material.equals(getCostElementType());
    } //	isFifo

    /**
     * Is Last Invoice Costing Method
     *
     * @return true if LastInvoice
     */
    public boolean isLastInvoice() {
        String cm = getCostingMethod();
        return cm != null
                && cm.equals(X_M_CostElement.COSTINGMETHOD_LastInvoice)
                && X_M_CostElement.COSTELEMENTTYPE_Material.equals(getCostElementType());
    } //	isLastInvoice

    /**
     * Is Last PO Costing Method
     *
     * @return true if LastPOPrice
     */
    public boolean isLastPOPrice() {
        String cm = getCostingMethod();
        return cm != null
                && cm.equals(X_M_CostElement.COSTINGMETHOD_LastPOPrice)
                && X_M_CostElement.COSTELEMENTTYPE_Material.equals(getCostElementType());
    } //	isLastPOPrice

    /**
     * Is LiFo Costing Method
     *
     * @return true if Lifo
     */
    public boolean isLifo() {
        String cm = getCostingMethod();
        return cm != null
                && cm.equals(X_M_CostElement.COSTINGMETHOD_Lifo)
                && X_M_CostElement.COSTELEMENTTYPE_Material.equals(getCostElementType());
    } //	isLiFo

    /**
     * Is Std Costing Method
     *
     * @return true if StandardCosting
     */
    public boolean isStandardCosting() {
        String cm = getCostingMethod();
        return cm != null
                && cm.equals(X_M_CostElement.COSTINGMETHOD_StandardCosting)
                && X_M_CostElement.COSTELEMENTTYPE_Material.equals(getCostElementType());
    } //	isStandardCosting

    /**
     * Is User Costing Method
     *
     * @return true if User Defined
     */
    public boolean isUserDefined() {
        String cm = getCostingMethod();
        return cm != null
                && cm.equals(X_M_CostElement.COSTINGMETHOD_UserDefined)
                && X_M_CostElement.COSTELEMENTTYPE_Material.equals(getCostElementType());
    } //	isAveragePO

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MCostElement[");
        sb.append(getId())
                .append("-")
                .append(getName())
                .append(",Type=")
                .append(getCostElementType())
                .append(",Method=")
                .append(getCostingMethod())
                .append("]");
        return sb.toString();
    } //	toString
} //	MCostElement
