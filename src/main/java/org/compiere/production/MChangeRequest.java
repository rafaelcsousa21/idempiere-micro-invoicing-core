package org.compiere.production;

import kotliquery.Row;
import org.compiere.util.MsgKt;
import org.eevolution.model.I_PP_Product_BOM;

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
     * @param M_ChangeRequest_ID ix
     */
    public MChangeRequest(int M_ChangeRequest_ID) {
        super(M_ChangeRequest_ID);
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
        this(0);
        setClientOrg(request);
        StringBuilder msgset =
                new StringBuilder()
                        .append(MsgKt.getElementTranslation("R_Request_ID"))
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
     */
    public MChangeRequest(Row row) {
        super(row);
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
                    "Error", MsgKt.parseTranslation("@NotFound@: @M_BOM_ID@ / @M_ChangeNotice_ID@"));
            return false;
        }

        //	Derive ChangeNotice from BOM if defined
        if (newRecord && getProductBOMId() != 0 && getChangeNoticeId() == 0) {
            I_PP_Product_BOM bom = MPPProductBOM.get(getProductBOMId());
            if (bom.getChangeNoticeId() != 0) {
                setChangeNoticeId(bom.getChangeNoticeId());
            }
        }

        return true;
    } //	beforeSave
} //	MChangeRequest
