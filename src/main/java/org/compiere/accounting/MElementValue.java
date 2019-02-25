package org.compiere.accounting;

import org.compiere.model.HasName;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_Fact_Acct;
import org.compiere.orm.MTree_Base;
import org.compiere.orm.POResultSet;
import org.compiere.orm.Query;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Natural Account
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL BF [ 1883533 ] Change to summary - valid combination
 * issue BF [ 2320411 ] Translate "Already posted to" message
 * @version $Id: MElementValue.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 */
public class MElementValue extends X_C_ElementValue {
    /**
     *
     */
    private static final long serialVersionUID = 4765839867934329276L;

    /**
     * Standard Constructor
     *
     * @param ctx               context
     * @param C_ElementValue_ID ID or 0 for new
     * @param trxName           transaction
     */
    public MElementValue(Properties ctx, int C_ElementValue_ID) {
        super(ctx, C_ElementValue_ID);
        if (C_ElementValue_ID == 0) {
            //	setElementId (0);	//	Parent
            //	setName (null);
            //	setValue (null);
            setIsSummary(false);
            setAccountSign(X_C_ElementValue.ACCOUNTSIGN_Natural);
            setAccountType(X_C_ElementValue.ACCOUNTTYPE_Expense);
            setIsDocControlled(false);
            setIsForeignCurrency(false);
            setIsBankAccount(false);
            //
            setPostActual(true);
            setPostBudget(true);
            setPostEncumbrance(true);
            setPostStatistical(true);
        }
    } //	MElementValue

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MElementValue(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MElementValue

    /**
     * Full Constructor
     *
     * @param ctx             context
     * @param Value           value
     * @param Name            name
     * @param Description     description
     * @param AccountType     account type
     * @param AccountSign     account sign
     * @param IsDocControlled doc controlled
     * @param IsSummary       summary
     * @param trxName         transaction
     */
    public MElementValue(
            Properties ctx,
            String Value,
            String Name,
            String Description,
            String AccountType,
            String AccountSign,
            boolean IsDocControlled,
            boolean IsSummary) {
        this(ctx, 0);
        setSearchKey(Value);
        setName(Name);
        setDescription(Description);
        setAccountType(AccountType);
        setAccountSign(AccountSign);
        setIsDocControlled(IsDocControlled);
        setIsSummary(IsSummary);
    } //	MElementValue

    /**
     * Import Constructor
     *
     * @param imp import
     */
    public MElementValue(X_I_ElementValue imp) {
        this(imp.getCtx(), 0);
        setClientOrg(imp);
        set(imp);
    } //	MElementValue

    /**
     * Set/Update Settings from import
     *
     * @param imp import
     */
    public void set(X_I_ElementValue imp) {
        setSearchKey(imp.getSearchKey());
        setName(imp.getName());
        setDescription(imp.getDescription());
        setAccountType(imp.getAccountType());
        setAccountSign(imp.getAccountSign());
        setIsSummary(imp.isSummary());
        setIsDocControlled(imp.isDocControlled());
        setElementId(imp.getC_Element_ID());
        //
        setPostActual(imp.isPostActual());
        setPostBudget(imp.isPostBudget());
        setPostEncumbrance(imp.isPostEncumbrance());
        setPostStatistical(imp.isPostStatistical());
        //
        //	setC_BankAccount_ID(imp.getC_BankAccount_ID());
        //	setIsForeignCurrency(imp.isForeignCurrency());
        //	setCurrencyId(imp.getCurrencyId());
        //	setIsBankAccount(imp.isIsBankAccount());
        //	setValidFrom(null);
        //	setValidTo(null);
    } //	set

    /**
     * User String Representation
     *
     * @return info value - name
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSearchKey()).append(" - ").append(getName());
        return sb.toString();
    } //	toString

    @Override
    protected boolean beforeSave(boolean newRecord) {
        if (getOrgId() != 0) setOrgId(0);
        //
        // Transform to summary level account
        if (!newRecord && isSummary() && is_ValueChanged(I_C_ElementValue.COLUMNNAME_IsSummary)) {
            //
            // Check if we have accounting facts
            boolean match =
                    new Query(
                            getCtx(),
                            I_Fact_Acct.Table_Name,
                            I_Fact_Acct.COLUMNNAME_Account_ID + "=?"
                    )
                            .setParameters(getElementValueId())
                            .match();
            if (match) {
                throw new AdempiereException("@AlreadyPostedTo@");
            }
            //
            // Check Valid Combinations - teo_sarca FR [ 1883533 ]
            String whereClause = MAccount.COLUMNNAME_Account_ID + "=?";
            POResultSet<MAccount> rs = null;
            try {
                rs =
                        new Query(getCtx(), I_C_ValidCombination.Table_Name, whereClause)
                                .setParameters(getId())
                                .scroll();
                while (rs.hasNext()) {
                    rs.next().deleteEx(true);
                }
            } finally {
                POResultSet.close(rs);
                rs = null;
            }
        }
        return true;
    } //	beforeSave

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) return success;
        if (newRecord || is_ValueChanged(I_C_ElementValue.COLUMNNAME_Value)) {
            // afalcone [Bugs #1837219]
            int ad_Tree_ID = (new MElement(getCtx(), getElementId())).getTreeId();
            String treeType = (new MTree(getCtx(), ad_Tree_ID)).getTreeType();

            if (newRecord) insert_Tree(treeType, getElementId());

            update_Tree(treeType);
        }

        //	Value/Name change
        if (!newRecord
                && (is_ValueChanged(I_C_ElementValue.COLUMNNAME_Value)
                || is_ValueChanged(HasName.Companion.getCOLUMNNAME_Name()))) {
            MAccount.updateValueDescription(
                    getCtx(), "Account_ID=" + getElementValueId());
            if ("Y".equals(Env.getContext(getCtx(), "$Element_U1")))
                MAccount.updateValueDescription(
                        getCtx(), "User1_ID=" + getElementValueId());
            if ("Y".equals(Env.getContext(getCtx(), "$Element_U2")))
                MAccount.updateValueDescription(
                        getCtx(), "User2_ID=" + getElementValueId());
        }

        return success;
    } //	afterSave

    @Override
    protected boolean afterDelete(boolean success) {
        if (success) delete_Tree(MTree_Base.TREETYPE_ElementValue);
        return success;
    } //	afterDelete

    public void setADClientID(int a) {
        super.setADClientID(a);
    }
} //	MElementValue
