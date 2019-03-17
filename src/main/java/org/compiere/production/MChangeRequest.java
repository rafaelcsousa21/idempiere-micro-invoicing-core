package org.compiere.production;

import kotliquery.Row;
import org.compiere.util.Msg;

import java.util.Properties;

/**
 * Change Request Model
 *
 * @author Jorg Janke
 * @version $Id: MChangeRequest.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MChangeRequest extends X_M_ChangeRequest {
    /**
     *
     */
    private static final long serialVersionUID = 8374119541472311165L;

    /**
     * Standard Constructor
     *
     * @param ctx                context
     * @param M_ChangeRequest_ID ix
     * @param trxName            trx
     */
    public MChangeRequest(Properties ctx, int M_ChangeRequest_ID) {
        super(ctx, M_ChangeRequest_ID);
        if (M_ChangeRequest_ID == 0) {
            //	setName (null);
            setIsApproved(false);
            setProcessed(false);
        }
    } //	MChangeRequest

    /**
     * CRM Request Constructor
     *
     * @param request request
     * @param group   request group
     */
    public MChangeRequest(MRequest request, MGroup group) {
        this(request.getCtx(), 0);
        setClientOrg(request);
        StringBuilder msgset =
                new StringBuilder()
                        .append(Msg.getElement(getCtx(), "R_Request_ID"))
                        .append(": ")
                        .append(request.getDocumentNo());
        setName(msgset.toString());
        setHelp(request.getSummary());
        //
        setProductBOMId(group.getPPProductBOMId());
        setChangeNoticeId(group.getChangeNoticeId());
    } //	MChangeRequest

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName trx
     */
    public MChangeRequest(Properties ctx, Row row) {
        super(ctx, row);
    } //	MChangeRequest

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true/false
     */
    protected boolean beforeSave(boolean newRecord) {
        //	Have at least one
        if (getProductBOMId() == 0 && getChangeNoticeId() == 0) {
            log.saveError(
                    "Error", Msg.parseTranslation(getCtx(), "@NotFound@: @M_BOM_ID@ / @M_ChangeNotice_ID@"));
            return false;
        }

        //	Derive ChangeNotice from BOM if defined
        if (newRecord && getProductBOMId() != 0 && getChangeNoticeId() == 0) {
            MPPProductBOM bom = MPPProductBOM.get(getCtx(), getProductBOMId());
            if (bom.getChangeNoticeId() != 0) {
                setChangeNoticeId(bom.getChangeNoticeId());
            }
        }

        return true;
    } //	beforeSave
} //	MChangeRequest
