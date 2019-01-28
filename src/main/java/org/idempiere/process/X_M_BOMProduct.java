package org.idempiere.process;

import org.compiere.model.I_M_BOMProduct;
import org.compiere.orm.PO;
import org.idempiere.common.util.KeyNamePair;
import org.idempiere.orm.I_Persistent;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

public class X_M_BOMProduct extends PO implements I_M_BOMProduct, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_M_BOMProduct(Properties ctx, int M_BOMProduct_ID, String trxName) {
    super(ctx, M_BOMProduct_ID, trxName);
    /**
     * if (M_BOMProduct_ID == 0) { setBOMProductType (null); // S setBOMQty (Env.ZERO); // 1
     * setIsPhantom (false); setLeadTimeOffset (0); setLine (0); // @SQL=SELECT NVL(MAX(Line),0)+10
     * AS DefaultValue FROM M_BOMProduct WHERE M_BOM_ID=@M_BOM_ID@ setM_BOM_ID (0);
     * setM_BOMProduct_ID (0); }
     */
  }

  /** Load Constructor */
  public X_M_BOMProduct(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  /**
   * AccessLevel
   *
   * @return 3 - Client - Org
   */
  protected int getAccessLevel() {
    return accessLevel.intValue();
  }

  @Override
  public int getTableId() {
    return Table_ID;
  }


  public String toString() {
    StringBuffer sb = new StringBuffer("X_M_BOMProduct[").append(getId()).append("]");
    return sb.toString();
  }

    /** Standard Product = S */
  public static final String BOMPRODUCTTYPE_StandardProduct = "S";
    /** Alternative = A */
  public static final String BOMPRODUCTTYPE_Alternative = "A";
  /** Alternative (Default) = D */
  public static final String BOMPRODUCTTYPE_AlternativeDefault = "D";
  /** Outside Processing = X */
  public static final String BOMPRODUCTTYPE_OutsideProcessing = "X";
  /**
   * Set Component Type.
   *
   * @param BOMProductType BOM Product Type
   */
  public void setBOMProductType(String BOMProductType) {

    set_Value(COLUMNNAME_BOMProductType, BOMProductType);
  }

  /**
   * Get Component Type.
   *
   * @return BOM Product Type
   */
  public String getBOMProductType() {
    return (String) get_Value(COLUMNNAME_BOMProductType);
  }

  /**
   * Set BOM Quantity.
   *
   * @param BOMQty Bill of Materials Quantity
   */
  public void setBOMQty(BigDecimal BOMQty) {
    set_Value(COLUMNNAME_BOMQty, BOMQty);
  }

    /**
   * Set Phantom.
   *
   * @param IsPhantom Phantom Component
   */
  public void setIsPhantom(boolean IsPhantom) {
    set_Value(COLUMNNAME_IsPhantom, Boolean.valueOf(IsPhantom));
  }

    /**
   * Set Lead Time Offset.
   *
   * @param LeadTimeOffset Optional Lead Time offset before starting production
   */
  public void setLeadTimeOffset(int LeadTimeOffset) {
    set_Value(COLUMNNAME_LeadTimeOffset, Integer.valueOf(LeadTimeOffset));
  }

  /**
   * Get Lead Time Offset.
   *
   * @return Optional Lead Time offset before starting production
   */
  public int getLeadTimeOffset() {
    Integer ii = (Integer) get_Value(COLUMNNAME_LeadTimeOffset);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Line No.
   *
   * @param Line Unique line for this document
   */
  public void setLine(int Line) {
    set_Value(COLUMNNAME_Line, Integer.valueOf(Line));
  }

  /**
   * Get Line No.
   *
   * @return Unique line for this document
   */
  public int getLine() {
    Integer ii = (Integer) get_Value(COLUMNNAME_Line);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set Attribute Set Instance.
   *
   * @param M_AttributeSetInstance_ID Product Attribute Set Instance
   */
  public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
    if (M_AttributeSetInstance_ID < 0) set_Value(COLUMNNAME_M_AttributeSetInstance_ID, null);
    else
      set_Value(COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
  }

  /**
   * Get Attribute Set Instance.
   *
   * @return Product Attribute Set Instance
   */
  public int getMAttributeSetInstance_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Alternative Group.
   *
   * @return Product BOM Alternative Group
   */
  public int getM_BOMAlternative_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_BOMAlternative_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get BOM.
   *
   * @return Bill of Material
   */
  public int getM_BOM_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_BOM_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get BOM Component.
   *
   * @return Bill of Material Component (Product)
   */
  public int getM_BOMProduct_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_BOMProduct_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Change Notice.
   *
   * @return Bill of Materials (Engineering) Change Notice (Version)
   */
  public int getM_ChangeNotice_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_ChangeNotice_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Set BOM Product.
   *
   * @param M_ProductBOM_ID Bill of Material Component Product
   */
  public void setM_ProductBOM_ID(int M_ProductBOM_ID) {
    if (M_ProductBOM_ID < 1) set_Value(COLUMNNAME_M_ProductBOM_ID, null);
    else set_Value(COLUMNNAME_M_ProductBOM_ID, Integer.valueOf(M_ProductBOM_ID));
  }

  /**
   * Get BOM Product.
   *
   * @return Bill of Material Component Product
   */
  public int getM_ProductBOM_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_ProductBOM_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get Product Operation.
   *
   * @return Product Manufacturing Operation
   */
  public int getM_ProductOperation_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_M_ProductOperation_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set Sequence.
   *
   * @param SeqNo Method of ordering records; lowest number comes first
   */
  public void setSeqNo(int SeqNo) {
    set_Value(COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
  }

  /**
   * Get Sequence.
   *
   * @return Method of ordering records; lowest number comes first
   */
  public int getSeqNo() {
    Integer ii = (Integer) get_Value(COLUMNNAME_SeqNo);
    if (ii == null) return 0;
    return ii;
  }
}
