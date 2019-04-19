package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_M_Product_PO;
import org.compiere.orm.Query;
import org.compiere.util.MsgKt;
import org.idempiere.common.exceptions.DBException;

import java.util.List;

import static software.hsharp.core.util.DBKt.getSQLValue;


/**
 * Product PO Model
 *
 * @author Jorg Janke
 * @version $Id: MProductPO.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MProductPO extends X_M_Product_PO {
    /**
     *
     */
    private static final long serialVersionUID = -1883198806060209516L;

    /**
     * Persistency Constructor
     *
     * @param ignored ignored
     */
    public MProductPO(int ignored) {
        super(0);
        if (ignored != 0) throw new IllegalArgumentException("Multi-Key");
        else {
            setIsCurrentVendor(true); // Y
        }
    } //	MProduct_PO

    /**
     * Load Constructor
     *
     */
    public MProductPO(Row row) {
        super(row);
    } //	MProductPO

    /**
     * Get current PO of Product
     *
     * @param M_Product_ID product
     * @return PO - current vendor first
     */
    public static I_M_Product_PO[] getOfProduct(int M_Product_ID) {
        final String whereClause = "M_Product_ID=?";
        List<I_M_Product_PO> list =
                new Query<I_M_Product_PO>(I_M_Product_PO.Table_Name, whereClause)
                        .setParameters(M_Product_ID)
                        .setOnlyActiveRecords(true)
                        .setOrderBy("IsCurrentVendor DESC")
                        .list();
        return list.toArray(new I_M_Product_PO[0]);
    } //	getOfProduct

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    @Override
    protected boolean beforeSave(boolean newRecord) {
        if ((newRecord && isActive() && isCurrentVendor())
                || (!newRecord
                && ((isValueChanged("IsActive") && isActive()) // now active
                || (isValueChanged("IsCurrentVendor") && isCurrentVendor()) // now current vendor
                || isValueChanged("C_BPartner_ID")
                || isValueChanged("M_Product_ID")))) {
            int cnt =
                    getSQLValue(
                            "SELECT COUNT(*) FROM M_Product_PO WHERE IsActive='Y' AND IsCurrentVendor='Y' AND C_BPartner_ID!=? AND M_Product_ID=?",
                            getBusinessPartnerId(),
                            getProductId());
            if (cnt > 0) {
                log.saveError(
                        "SaveError",
                        MsgKt.getMsg(DBException.SAVE_ERROR_NOT_UNIQUE_MSG, true)
                                + MsgKt.getElementTranslation(I_M_Product_PO.COLUMNNAME_IsCurrentVendor));
                return false;
            }
        }

        return true;
    }
} //	MProductPO
