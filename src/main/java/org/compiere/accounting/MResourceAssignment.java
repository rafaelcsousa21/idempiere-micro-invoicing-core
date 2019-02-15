package org.compiere.accounting;

import org.idempiere.common.util.Env;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Resource Assignment Model
 *
 * @author Jorg Janke
 * @version $Id: MResourceAssignment.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MResourceAssignment extends X_S_ResourceAssignment {
  /** */
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
   * @param rs result set
   */
  public MResourceAssignment(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MResourceAssignment

  /**
   * After Save
   *
   * @param newRecord new
   * @param success success
   * @return true
   */
  //	protected boolean afterSave (boolean newRecord, boolean success)
  //	{
  //		/*
  //		if (!success)
  //		    return success;
  //		v_Description := :new.Name;
  //	IF (:new.Description IS NOT NULL AND LENGTH(:new.Description) > 0) THEN
  //		v_Description := v_Description || ' (' || :new.Description || ')';
  //	END IF;
  //
  //	-- Update Expense Line
  //	UPDATE S_TimeExpenseLine
  //	  SET  Description = v_Description,
  //		Qty = :new.Qty
  //	WHERE S_ResourceAssignment_ID = :new.S_ResourceAssignment_ID
  //	  AND (Description <> v_Description OR Qty <> :new.Qty);
  //
  //	-- Update Order Line
  //	UPDATE C_OrderLine
  //	  SET  Description = v_Description,
  //		QtyOrdered = :new.Qty
  //	WHERE S_ResourceAssignment_ID = :new.S_ResourceAssignment_ID
  //	  AND (Description <> v_Description OR QtyOrdered <> :new.Qty);
  //
  //	-- Update Invoice Line
  //	UPDATE C_InvoiceLine
  //	  SET  Description = v_Description,
  //		QtyInvoiced = :new.Qty
  //	WHERE S_ResourceAssignment_ID = :new.S_ResourceAssignment_ID
  //	  AND (Description <> v_Description OR QtyInvoiced <> :new.Qty);
  //	  */
  //		return success;
  //	}	//	afterSave

  /**
   * String Representation
   *
   * @return string
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("MResourceAssignment[ID=");
    sb.append(getId())
        .append(",S_Resource_ID=")
        .append(getS_Resource_ID())
        .append(",From=")
        .append(getAssignDateFrom())
        .append(",To=")
        .append(getAssignDateTo())
        .append(",Qty=")
        .append(getQty())
        .append("]");
    return sb.toString();
  } //  toString

  /**
   * Before Delete
   *
   * @return true if not confirmed
   */
  protected boolean beforeDelete() {
    //	 allow to delete, when not confirmed
    if (isConfirmed()) return false;

    return true;
  } //	beforeDelete
} //	MResourceAssignment
