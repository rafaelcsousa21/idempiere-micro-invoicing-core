package org.compiere.accounting;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.Enumeration;

/**
 * Mutable Tree Node (not a PO).
 *
 * @author Jorg Janke
 * @version $Id: MTreeNode.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MTreeNode extends DefaultMutableTreeNode {
    /**
     *
     */
    private static final long serialVersionUID = -6871590404494812487L;
    /**
     * Window - 1
     */
    public static int TYPE_WINDOW = 1;
    /**
     * Report - 2
     */
    public static int TYPE_REPORT = 2;
    /**
     * Process - 3
     */
    public static int TYPE_PROCESS = 3;
    /**
     * Workflow - 4
     */
    public static int TYPE_WORKFLOW = 4;
    /**
     * Variable - 6
     */
    public static int TYPE_SETVARIABLE = 6;
    /**
     * Choice - 7
     */
    public static int TYPE_USERCHOICE = 7;
    /**
     * Action - 8
     */
    public static int TYPE_DOCACTION = 8;
    /**
     * Node ID
     */
    private int m_node_ID;
    /**
     * Name
     */
    private String m_name;
    /**
     * Description
     */
    private String m_description;

    /** ********************************************************************** */
    /**
     * Parent ID
     */
    private int m_parent_ID;
    /**
     * Summaty
     */
    private boolean m_isSummary;
    /**
     * Last found ID
     */
    private int m_lastID = -1;
    /**
     * Last found Node
     */
    private MTreeNode m_lastNode = null;

    /**
     * Construct Model TreeNode
     *
     * @param node_ID        node
     * @param seqNo          sequence
     * @param name           name
     * @param description    description
     * @param parent_ID      parent
     * @param isSummary      summary
     * @param imageIndicator image indicator
     * @param onBar          on bar
     * @param color          color
     */
    public MTreeNode(
            int node_ID,
            int seqNo,
            String name,
            String description,
            int parent_ID,
            boolean isSummary,
            String imageIndicator,
            boolean onBar,
            Color color) {
        super();
        //	log.fine( "MTreeNode Node_ID=" + node_ID + ", Parent_ID=" + parent_ID + " - " + name);
        m_node_ID = node_ID;
        m_name = name;
        m_description = description;
        if (m_description == null) m_description = "";
        m_parent_ID = parent_ID;
        setSummary(isSummary);
        setImageIndicator(imageIndicator);
    } //  MTreeNode

    /**
     * ************************************************************************ Get Node ID
     *
     * @return node id (e.g. AD_Menu_ID)
     */
    public int getNodeId() {
        return m_node_ID;
    } //  getID

    /**
     * Get Name
     *
     * @return name
     */
    public String getName() {
        return m_name;
    } //  setName

    /**
     * Return parent
     *
     * @return Parent_ID (e.g. AD_Menu_ID)
     */
    public int getParentId() {
        return m_parent_ID;
    } //	getParent

    /**
     * Print Name
     *
     * @return info
     */
    public String toString() {
        return //   m_node_ID + "/" + m_parent_ID + " " + m_seqNo + " - " +
                m_name;
    } //  toString

    /**
     * Set Summary (allow children)
     *
     * @param isSummary true if summary
     */
    public void setAllowsChildren(boolean isSummary) {
        super.setAllowsChildren(isSummary);
        m_isSummary = isSummary;
    } //  setAllowsChildren

    /**
     * Allow children to be added to this node
     *
     * @return true if summary node
     */
    public boolean isSummary() {
        return m_isSummary;
    } //  isSummary

    /** ********************************************************************** */

    /**
     * ************************************************************************ Set Summary (allow
     * children)
     *
     * @param isSummary summary node
     */
    public void setSummary(boolean isSummary) {
        m_isSummary = isSummary;
        super.setAllowsChildren(isSummary);
    } //  setSummary

    /**
     * Set Image Indicator and Index
     *
     * @param imageIndicator image indicator (W/X/R/P/F/T/B) MWFNode.ACTION_
     */
    public void setImageIndicator(String imageIndicator) {
        if (imageIndicator != null) {
        }
    } //  setImageIndicator

    /**
     * Return the Node with ID in list of children
     *
     * @param ID id
     * @return VTreeNode with ID or null
     */
    public MTreeNode findNode(int ID) {
        if (m_node_ID == ID) return this;
        //
        if (ID == m_lastID && m_lastNode != null) return m_lastNode;
        //
        Enumeration<?> en = preorderEnumeration();
        while (en.hasMoreElements()) {
            MTreeNode nd = (MTreeNode) en.nextElement();
            if (ID == nd.getNodeId()) {
                m_lastID = ID;
                m_lastNode = nd;
                return nd;
            }
        }
        return null;
    } //  findNode
} //  MTreeNode
