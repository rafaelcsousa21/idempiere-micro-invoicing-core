package org.compiere.wf;

import org.compiere.model.I_AD_Note;
import org.compiere.orm.PO;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_Note
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Note extends PO implements I_AD_Note {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_Note(Properties ctx, int AD_Note_ID) {
        super(ctx, AD_Note_ID);
        /** if (AD_Note_ID == 0) { setAD_Message_ID (0); setAD_Note_ID (0); } */
    }

    /**
     * Load Constructor
     */
    public X_AD_Note(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 7 - System - Client - Org
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_AD_Note[").append(getId()).append("]");
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

    /**
     * Set Message.
     *
     * @param AD_Message_ID System Message
     */
    public void setMessageId(int AD_Message_ID) {
        if (AD_Message_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_Message_ID, null);
        else set_ValueNoCheck(COLUMNNAME_AD_Message_ID, Integer.valueOf(AD_Message_ID));
    }

    /**
     * Get Notice.
     *
     * @return System Notice
     */
    public int getNoteId() {
        Integer ii = (Integer) getValue(COLUMNNAME_AD_Note_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Table.
     *
     * @param AD_Table_ID Database Table information
     */
    public void setTableId(int AD_Table_ID) {
        if (AD_Table_ID < 1) set_ValueNoCheck(COLUMNNAME_AD_Table_ID, null);
        else set_ValueNoCheck(COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
    }

    /**
     * Set User/Contact.
     *
     * @param AD_User_ID User within the system - Internal or Business Partner Contact
     */
    public void setUserId(int AD_User_ID) {
        if (AD_User_ID < 1) set_Value(COLUMNNAME_AD_User_ID, null);
        else set_Value(COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
    }

    /**
     * Set Description.
     *
     * @param Description Optional short description of the record
     */
    public void setDescription(String Description) {
        set_Value(COLUMNNAME_Description, Description);
    }

    /**
     * Get Processed.
     *
     * @return The document has been processed
     */
    public boolean isProcessed() {
        Object oo = getValue(COLUMNNAME_Processed);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Processed.
     *
     * @param Processed The document has been processed
     */
    public void setProcessed(boolean Processed) {
        set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
    }

    /**
     * Set Process Now.
     *
     * @param Processing Process Now
     */
    public void setProcessing(boolean Processing) {
        set_Value(COLUMNNAME_Processing, Boolean.valueOf(Processing));
    }

    /**
     * Set Record ID.
     *
     * @param Record_ID Direct internal record ID
     */
    public void setRecordId(int Record_ID) {
        if (Record_ID < 0) set_ValueNoCheck(COLUMNNAME_Record_ID, null);
        else set_ValueNoCheck(COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
    }

    /**
     * Get Reference.
     *
     * @return Reference for this record
     */
    public String getReference() {
        return (String) getValue(COLUMNNAME_Reference);
    }

    /**
     * Set Reference.
     *
     * @param Reference Reference for this record
     */
    public void setReference(String Reference) {
        set_Value(COLUMNNAME_Reference, Reference);
    }

    /**
     * Set Text Message.
     *
     * @param TextMsg Text Message
     */
    public void setTextMsg(String TextMsg) {
        set_Value(COLUMNNAME_TextMsg, TextMsg);
    }

    @Override
    public int getTableId() {
        return I_AD_Note.Table_ID;
    }
}
