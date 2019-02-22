package org.compiere.wf;

import org.compiere.model.I_AD_Message;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_Message
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Message extends PO implements I_AD_Message, I_Persistent {

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
    public int getAD_Message_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_AD_Message_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Message Text.
     *
     * @return Textual Informational, Menu or Error Message
     */
    public String getMsgText() {
        return (String) get_Value(COLUMNNAME_MsgText);
    }

    /**
     * Get Search Key.
     *
     * @return Search key for the record in the format required - must be unique
     */
    public String getValue() {
        return (String) get_Value(COLUMNNAME_Value);
    }

    @Override
    public int getTableId() {
        return I_AD_Message.Table_ID;
    }
}
