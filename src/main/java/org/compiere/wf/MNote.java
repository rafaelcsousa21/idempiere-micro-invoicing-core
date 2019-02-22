package org.compiere.wf;

import java.sql.ResultSet;
import java.util.Properties;


/**
 * Note Model
 *
 * @author Jorg Janke
 * @version $Id: MNote.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 */
public class MNote extends X_AD_Note {
    /**
     *
     */
    private static final long serialVersionUID = -422120961441035731L;

    /**
     * Standard Constructor
     *
     * @param ctx        context
     * @param AD_Note_ID id
     * @param trxName    transaction
     */
    public MNote(Properties ctx, int AD_Note_ID) {
        super(ctx, AD_Note_ID);
        if (AD_Note_ID == 0) {
            setProcessed(false);
            setProcessing(false);
        }
    } //	MNote

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MNote(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MNote

    /**
     * New Mandatory Constructor
     *
     * @param ctx           context
     * @param AD_Message_ID message
     * @param AD_User_ID    targeted user
     * @param trxName       transaction
     */
    public MNote(Properties ctx, int AD_Message_ID, int AD_User_ID) {
        this(ctx, 0);
        setAD_Message_ID(AD_Message_ID);
        setAD_User_ID(AD_User_ID);
    } //	MNote

    /**
     * New Mandatory Constructor
     *
     * @param ctx             context
     * @param AD_MessageValue message
     * @param AD_User_ID      targeted user
     * @param trxName         transaction
     */
    public MNote(Properties ctx, String AD_MessageValue, int AD_User_ID) {
        this(ctx, MMessage.getAD_Message_ID(ctx, AD_MessageValue), AD_User_ID);
    } //	MNote

    /**
     * Create Note
     *
     * @param ctx           context
     * @param AD_Message_ID message
     * @param AD_User_ID    user
     * @param AD_Table_ID   table
     * @param Record_ID     record
     * @param TextMsg       text message
     * @param Reference     reference
     * @param trxName       transaction
     */
    public MNote(
            Properties ctx,
            int AD_Message_ID,
            int AD_User_ID,
            int AD_Table_ID,
            int Record_ID,
            String Reference,
            String TextMsg,
            String trxName) {
        this(ctx, AD_Message_ID, AD_User_ID);
        setRecord(AD_Table_ID, Record_ID);
        setReference(Reference);
        setTextMsg(TextMsg);
    } //	MNote

    /**
     * New Constructor
     *
     * @param ctx             context
     * @param AD_MessageValue message
     * @param AD_User_ID      targeted user
     * @param AD_Client_ID    client
     * @param AD_Org_ID       org
     * @param trxName         transaction
     */
    public MNote(
            Properties ctx,
            String AD_MessageValue,
            int AD_User_ID,
            int AD_Client_ID,
            int AD_Org_ID,
            String trxName) {
        this(ctx, MMessage.getAD_Message_ID(ctx, AD_MessageValue), AD_User_ID);
        setClientOrg(AD_Client_ID, AD_Org_ID);
    } //	MNote

    /**
     * Set AD_Message_ID. Looks up No Message Found if 0
     *
     * @param AD_Message_ID id
     */
    public void setAD_Message_ID(int AD_Message_ID) {
        if (AD_Message_ID == 0)
            super.setAD_Message_ID(MMessage.getAD_Message_ID(getCtx(), "NoMessageFound"));
        else super.setAD_Message_ID(AD_Message_ID);
    } //	setAD_Message_ID

    /**
     * Set Client Org
     *
     * @param AD_Client_ID client
     * @param AD_Org_ID    org
     */
    public void setClientOrg(int AD_Client_ID, int AD_Org_ID) {
        super.setClientOrg(AD_Client_ID, AD_Org_ID);
    } //	setClientOrg

    /**
     * Set Record
     *
     * @param AD_Table_ID table
     * @param Record_ID   record
     */
    public void setRecord(int AD_Table_ID, int Record_ID) {
        setAD_Table_ID(AD_Table_ID);
        setRecord_ID(Record_ID);
    } //	setRecord

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuffer sb =
                new StringBuffer("MNote[")
                        .append(getId())
                        .append(",AD_Message_ID=")
                        .append(getAD_Message_ID())
                        .append(",")
                        .append(getReference())
                        .append(",Processed=")
                        .append(isProcessed())
                        .append("]");
        return sb.toString();
    } //	toString
} //	MNote
