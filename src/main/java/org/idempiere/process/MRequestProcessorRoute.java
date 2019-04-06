package org.idempiere.process;

import kotliquery.Row;

public class MRequestProcessorRoute extends X_R_RequestProcessor_Route {
    /**
     *
     */
    private static final long serialVersionUID = 8739358607059413339L;

    /**
     * Standard Constructor
     *
     * @param ctx                         context
     * @param R_RequestProcessor_Route_ID id
     */
    public MRequestProcessorRoute(int R_RequestProcessor_Route_ID) {
        super(R_RequestProcessor_Route_ID);
    } //	MRequestProcessorRoute

    /**
     * Load Constructor
     *
     * @param ctx context
     * @param rs  result set
     */
    public MRequestProcessorRoute(Row row) {
        super(row);
    } //	MRequestProcessorRoute
} //	MRequestProcessorRoute
