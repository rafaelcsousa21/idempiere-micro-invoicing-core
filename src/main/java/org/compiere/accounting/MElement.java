package org.compiere.accounting;

import org.compiere.orm.MClient;
import org.compiere.orm.X_AD_Tree;
import org.compiere.util.Msg;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Accounting Element Model.
 *
 * @author Jorg Janke
 * @version $Id: MElement.java,v 1.3 2006/07/30 00:58:04 jjanke Exp $
 */
public class MElement extends X_C_Element {
    /**
     *
     */
    private static final long serialVersionUID = 7656092284157893366L;
    /**
     * Tree Used
     */
    private X_AD_Tree m_tree = null;

    /**
     * Standard Constructor
     *
     * @param ctx          context
     * @param C_Element_ID id
     * @param trxName      transaction
     */
    public MElement(Properties ctx, int C_Element_ID) {
        super(ctx, C_Element_ID);
        if (C_Element_ID == 0) {
            //	setName (null);
            //	setTreeId (0);
            //	setElementType (null);	// A
            setIsBalancing(false);
            setIsNaturalAccount(false);
        }
    } //	MElement

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MElement(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MElement

    /**
     * Full Constructor
     *
     * @param client      client
     * @param Name        name
     * @param ElementType type
     * @param AD_Tree_ID  tree
     */
    public MElement(MClient client, String Name, String ElementType, int AD_Tree_ID) {
        this(client.getCtx(), 0);
        setClientOrg(client);
        setName(Name);
        setElementType(ElementType); // A
        setTreeId(AD_Tree_ID);
        setIsNaturalAccount(X_C_Element.ELEMENTTYPE_Account.equals(ElementType));
    } //	MElement

    /**
     * Get Tree
     *
     * @return tree
     */
    public X_AD_Tree getTree() {
        if (m_tree == null) m_tree = new X_AD_Tree(getCtx(), getTreeId());
        return m_tree;
    } //	getTree

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        if (getOrgId() != 0) setOrgId(0);
        String elementType = getElementType();
        //	Natural Account
        if (X_C_Element.ELEMENTTYPE_UserDefined.equals(elementType) && isNaturalAccount())
            setIsNaturalAccount(false);
        //	Tree validation
        X_AD_Tree tree = getTree();
        if (tree == null) return false;
        String treeType = tree.getTreeType();
        if (X_C_Element.ELEMENTTYPE_UserDefined.equals(elementType)) {
            if (X_AD_Tree.TREETYPE_User1.equals(treeType) || X_AD_Tree.TREETYPE_User2.equals(treeType)) ;
            else {
                log.saveError(
                        "Error", Msg.parseTranslation(getCtx(), "@TreeType@ <> @ElementType@ (U)"), false);
                return false;
            }
        } else {
            if (!X_AD_Tree.TREETYPE_ElementValue.equals(treeType)) {
                log.saveError(
                        "Error", Msg.parseTranslation(getCtx(), "@TreeType@ <> @ElementType@ (A)"), false);
                return false;
            }
        }
        return true;
    } //	beforeSave
} //	MElement
