package org.compiere.production;

import org.idempiere.common.util.Env;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Project Type Phase Model
 *
 * @author Jorg Janke
 * @version $Id: MProjectTypePhase.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MProjectTypePhase extends X_C_Phase {
    /**
     *
     */
    private static final long serialVersionUID = -5111329904215151458L;

    /**
     * Standard Constructor
     *
     * @param ctx        context
     * @param C_Phase_ID id
     * @param trxName    trx
     */
    public MProjectTypePhase(Properties ctx, int C_Phase_ID) {
        super(ctx, C_Phase_ID);
        if (C_Phase_ID == 0) {
            //	setC_Phase_ID (0);			//	PK
            //	setC_ProjectType_ID (0);	//	Parent
            //	setName (null);
            setSeqNo(0);
            setStandardQty(Env.ZERO);
        }
    } //	MProjectTypePhase

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName
     */
    public MProjectTypePhase(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MProjectTypePhase

    /**
     * Get Project Type Phases
     *
     * @return Array of phases
     */
    public MProjectTypeTask[] getTasks() {
        ArrayList<MProjectTypeTask> list = new ArrayList<MProjectTypeTask>();
        String sql = "SELECT * FROM C_Task WHERE C_Phase_ID=? ORDER BY SeqNo";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, getC_Phase_ID());
            rs = pstmt.executeQuery();
            while (rs.next()) list.add(new MProjectTypeTask(getCtx(), rs));
        } catch (SQLException ex) {
            log.log(Level.SEVERE, sql, ex);
        } finally {
            rs = null;
            pstmt = null;
        }
        //
        MProjectTypeTask[] retValue = new MProjectTypeTask[list.size()];
        list.toArray(retValue);
        return retValue;
    } //	getPhases
} //	MProjectTypePhase
