package org.compiere.wf;

import kotliquery.Row;
import org.compiere.model.I_AD_WF_Block;
import org.compiere.orm.BasePOName;

import java.util.Properties;

/**
 * Generated Model for AD_WF_Block
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_WF_Block extends BasePOName implements I_AD_WF_Block {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_WF_Block(Properties ctx, int AD_WF_Block_ID) {
        super(ctx, AD_WF_Block_ID);
    }

    /**
     * Load Constructor
     */
    public X_AD_WF_Block(Properties ctx, Row row) {
        super(ctx, row);
    }

    /**
     * AccessLevel
     *
     * @return 6 - System - Client
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_AD_WF_Block[").append(getId()).append("]");
        return sb.toString();
    }

    @Override
    public int getTableId() {
        return I_AD_WF_Block.Table_ID;
    }
}
