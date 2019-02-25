package org.compiere.wf;

import org.compiere.model.I_AD_Message;
import org.compiere.orm.PO;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_Message
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Message extends PO implements I_AD_Message {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_Message(Properties ctx, int AD_Message_ID) {
        super(ctx, AD_Message_ID);
        /**
         * if (AD_Message_ID == 0) { setAD_Message_ID (0); setEntityType (null); // @SQL=select
         * get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) from dual setMsgText (null); setMsgType (null);
         * // I setValue (null); }
         */
    }

    /**
     * Load Constructor
     */
    public X_AD_Message(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 4 - System
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_AD_Message[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Message.
     *
     * @return System Message
     */
    public int getMessageId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_Message_ID);
        if (ii == null) return 0;
        return ii;
    }

    @Override
    public int getTableId() {
        return I_AD_Message.Table_ID;
    }
}
