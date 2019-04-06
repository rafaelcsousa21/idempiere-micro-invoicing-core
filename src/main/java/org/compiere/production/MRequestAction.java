package org.compiere.production;

import kotliquery.Row;

/**
 * Request History Model
 *
 * @author Jorg Janke
 * @version $Id: MRequestAction.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MRequestAction extends X_R_RequestAction {
    /**
     *
     */
    private static final long serialVersionUID = 2902231219773596011L;

    /**
     * Persistency Constructor
     *
     * @param ctx                context
     * @param R_RequestAction_ID id
     */
    public MRequestAction(int R_RequestAction_ID) {
        super(R_RequestAction_ID);
    } //	MRequestAction

    /**
     * Load Construtor
     *
     * @param ctx context
     */
    public MRequestAction(Row row) {
        super(row);
    } //	MRequestAction

    /**
     * Parent Action Constructor
     *
     * @param request   parent
     * @param newRecord new (copy all)
     */
    public MRequestAction(MRequest request, boolean newRecord) {
        this(0);
        setClientOrg(request);
        setRequestId(request.getRequestId());
    } //	MRequestAction

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        return true;
    } //	beforeSave
} //	MRequestAction
