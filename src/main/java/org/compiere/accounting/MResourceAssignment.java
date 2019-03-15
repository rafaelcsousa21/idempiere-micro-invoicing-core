package org.compiere.accounting;

import kotliquery.Row;
import org.idempiere.common.util.Env;

import java.sql.Timestamp;
import java.util.Properties;

/**
 * Resource Assignment Model
 *
 * @author Jorg Janke
 * @version $Id: MResourceAssignment.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MResourceAssignment extends X_S_ResourceAssignment {
    /**
     *
     */
    private static final long serialVersionUID = 4230793339153210998L;

    /**
     * Stnadard Constructor
     *
     * @param ctx
     * @param S_ResourceAssignment_ID
     */
    public MResourceAssignment(Properties ctx, int S_ResourceAssignment_ID) {
        super(ctx, S_ResourceAssignment_ID);
        getP_info().setUpdateable(true); // 	default table is not updateable
        //	Default values
        if (S_ResourceAssignment_ID == 0) {
            setAssignDateFrom(new Timestamp(System.currentTimeMillis()));
            setQty(Env.ONE);
            setName(".");
            setIsConfirmed(false);
        }
    } //	MResourceAssignment

    /**
     * Load Contsructor
     *
     * @param ctx context
     * @param rs  result set
     */
    public MResourceAssignment(Properties ctx, Row row) {
        super(ctx, row);
    } //	MResourceAssignment

    /**
     * String Representation
     *
     * @return string
     */
    public String toString() {
        String sb = "MResourceAssignment[ID=" + getId() +
                ",S_Resource_ID=" +
                getS_Resource_ID() +
                ",From=" +
                getAssignDateFrom() +
                ",To=" +
                getAssignDateTo() +
                ",Qty=" +
                getQty() +
                "]";
        return sb;
    } //  toString

    /**
     * Before Delete
     *
     * @return true if not confirmed
     */
    protected boolean beforeDelete() {
        //	 allow to delete, when not confirmed
        return !isConfirmed();
    } //	beforeDelete
} //	MResourceAssignment
