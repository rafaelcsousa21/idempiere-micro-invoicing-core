package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.orm.MClient;
import org.compiere.orm.X_AD_Tree;
import org.compiere.util.MsgKt;

import static org.compiere.orm.MTree_Base.TREETYPE_ElementValue;
import static org.compiere.orm.MTree_Base.TREETYPE_User1;
import static org.compiere.orm.MTree_Base.TREETYPE_User2;

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
     * @param C_Element_ID id
     */
    public MElement(int C_Element_ID) {
        super(C_Element_ID);
        if (C_Element_ID == 0) {
            setIsBalancing(false);
            setIsNaturalAccount(false);
        }
    } //	MElement

    /**
     * Load Constructor
     */
    public MElement(Row row) {
        super(row);
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
        this(0);
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
        if (m_tree == null) m_tree = new X_AD_Tree(getTreeId());
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
            if (!TREETYPE_User1.equals(treeType) && !TREETYPE_User2.equals(treeType)) {
                log.saveError(
                        "Error", MsgKt.parseTranslation("@TreeType@ <> @ElementType@ (U)"), false);
                return false;
            }
        } else {
            if (!TREETYPE_ElementValue.equals(treeType)) {
                log.saveError(
                        "Error", MsgKt.parseTranslation("@TreeType@ <> @ElementType@ (A)"), false);
                return false;
            }
        }
        return true;
    } //	beforeSave
} //	MElement
