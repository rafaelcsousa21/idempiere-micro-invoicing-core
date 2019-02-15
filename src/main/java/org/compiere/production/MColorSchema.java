package org.compiere.production;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.I_PA_ColorSchema;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.Env;

/**
 * Performance Color Schema
 *
 * @author Jorg Janke
 * @version $Id: MColorSchema.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MColorSchema extends X_PA_ColorSchema {
  /** */
  private static final long serialVersionUID = 4645092884363283719L;

    /**
   * Get Color
   *
   * @param ctx context
   * @param PA_ColorSchema_ID id
   * @param percent percent
   * @return color
   */
  public static Color getColor(Properties ctx, int PA_ColorSchema_ID, int percent) {
    MColorSchema cs = get(ctx, PA_ColorSchema_ID);
    return cs.getColor(percent);
  } //	getColor

  /**
   * Get MColorSchema from Cache
   *
   * @param ctx context
   * @param PA_ColorSchema_ID id
   * @return MColorSchema
   */
  public static MColorSchema get(Properties ctx, int PA_ColorSchema_ID) {
    if (PA_ColorSchema_ID == 0) {
      MColorSchema retValue = new MColorSchema(ctx, 0);
      retValue.setDefault();
      return retValue;
    }
    Integer key = PA_ColorSchema_ID;
    MColorSchema retValue = (MColorSchema) s_cache.get(key);
    if (retValue != null) return retValue;
    retValue = new MColorSchema(ctx, PA_ColorSchema_ID);
    if (retValue.getId() != 0) s_cache.put(key, retValue);
    return retValue;
  } //	get

  /** Cache */
  private static CCache<Integer, MColorSchema> s_cache =
      new CCache<Integer, MColorSchema>(I_PA_ColorSchema.Table_Name, 20);

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param PA_ColorSchema_ID id
   * @param trxName trx
   */
  public MColorSchema(Properties ctx, int PA_ColorSchema_ID) {
    super(ctx, PA_ColorSchema_ID);
    if (PA_ColorSchema_ID == 0) {
      //	setName (null);
      //	setMark1Percent (50);
      //	setAD_PrintColor1_ID (102);		//	red
      //	setMark2Percent (100);
      //	setAD_PrintColor2_ID (113);		//	yellow
    }
  } //	MColorSchema

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName trx
   */
  public MColorSchema(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MColorSchema

  /** Set Default. Red (50) - Yellow (100) - Green */
  public void setDefault() {
    setName("Default");
    setMark1Percent(50);
    setAD_PrintColor1_ID(102); // 	red
    setMark2Percent(100);
    setAD_PrintColor2_ID(113); // 	yellow
    setMark3Percent(9999);
    setAD_PrintColor3_ID(103); // 	green
  } //	setDefault

  /**
   * Before Save
   *
   * @param newRecord new
   * @return true
   */
  protected boolean beforeSave(boolean newRecord) {
    if (getMark1Percent() > getMark2Percent()) setMark1Percent(getMark2Percent());
    if (getMark2Percent() > getMark3Percent() && getMark3Percent() != 0)
      setMark2Percent(getMark3Percent());
    if (getMark3Percent() > getMark4Percent() && getMark4Percent() != 0)
      setMark4Percent(getMark4Percent());
    //
    return true;
  } //	beforeSave

  /**
   * Get Color
   *
   * @param percent percent
   * @return color
   */
  public Color getColor(int percent) {
    int AD_PrintColor_ID = 0;
    if (percent <= getMark1Percent() || getMark2Percent() == 0)
      AD_PrintColor_ID = getAD_PrintColor1_ID();
    else if (percent <= getMark2Percent() || getMark3Percent() == 0)
      AD_PrintColor_ID = getAD_PrintColor2_ID();
    else if (percent <= getMark3Percent() || getMark4Percent() == 0)
      AD_PrintColor_ID = getAD_PrintColor3_ID();
    else AD_PrintColor_ID = getAD_PrintColor4_ID();
    if (AD_PrintColor_ID == 0) {
      if (getAD_PrintColor3_ID() != 0) AD_PrintColor_ID = getAD_PrintColor3_ID();
      else if (getAD_PrintColor2_ID() != 0) AD_PrintColor_ID = getAD_PrintColor2_ID();
      else if (getAD_PrintColor1_ID() != 0) AD_PrintColor_ID = getAD_PrintColor1_ID();
    }
    if (AD_PrintColor_ID == 0) return Color.black;
    //
    return Color.black;
  } //	getColor

  /**
   * String Representation
   *
   * @return info
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("MColorSchema[");
    sb.append(getId()).append("-").append(getName()).append("]");
    return sb.toString();
  } //	toString
} //	MColorSchema
