package org.compiere.wf;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Work Flow Commitment Block
 *
 * @author Jorg Janke
 * @version $Id: MWFBlock.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWFBlock extends X_AD_WF_Block {
  /** */
  private static final long serialVersionUID = -2084396539959122888L;

    /**
   * Standard Constructor
   *
   * @param ctx context
   * @param AD_WF_Block_ID id
   * @param trxName transaction
   */
  public MWFBlock(Properties ctx, int AD_WF_Block_ID, String trxName) {
    super(ctx, AD_WF_Block_ID, trxName);
  } //	MWFBlock

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MWFBlock(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MWFBlock
} //	MWFBlock
