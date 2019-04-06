package org.compiere.production;

import kotliquery.Row;
import org.compiere.model.I_PA_ColorSchema;
import org.idempiere.common.util.CCache;

import java.awt.*;

/**
 * Performance Color Schema
 *
 * @author Jorg Janke
 * @version $Id: MColorSchema.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MColorSchema extends X_PA_ColorSchema {
    /**
     *
     */
    private static final long serialVersionUID = 4645092884363283719L;
    /**
     * Cache
     */
    private static CCache<Integer, MColorSchema> s_cache =
            new CCache<Integer, MColorSchema>(I_PA_ColorSchema.Table_Name, 20);

    /**
     * Standard Constructor
     *
     * @param ctx               context
     * @param PA_ColorSchema_ID id
     * @param trxName           trx
     */
    public MColorSchema(int PA_ColorSchema_ID) {
        super(PA_ColorSchema_ID);
    } //	MColorSchema

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MColorSchema(Row row) {
        super(row);
    } //	MColorSchema

    /**
     * Get Color
     *
     * @param ctx               context
     * @param PA_ColorSchema_ID id
     * @param percent           percent
     * @return color
     */
    public static Color getColor(int PA_ColorSchema_ID, int percent) {
        MColorSchema cs = get(PA_ColorSchema_ID);
        return cs.getColor(percent);
    } //	getColor

    /**
     * Get MColorSchema from Cache
     *
     * @param ctx               context
     * @param PA_ColorSchema_ID id
     * @return MColorSchema
     */
    public static MColorSchema get(int PA_ColorSchema_ID) {
        if (PA_ColorSchema_ID == 0) {
            MColorSchema retValue = new MColorSchema(0);
            retValue.setDefault();
            return retValue;
        }
        Integer key = PA_ColorSchema_ID;
        MColorSchema retValue = s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MColorSchema(PA_ColorSchema_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * Set Default. Red (50) - Yellow (100) - Green
     */
    public void setDefault() {
        setName("Default");
        setMark1Percent(50);
        setPrintColor1Id(102); // 	red
        setMark2Percent(100);
        setPrintColor2Id(113); // 	yellow
        setMark3Percent(9999);
        setPrintColor3Id(103); // 	green
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
            AD_PrintColor_ID = getPrintColor1Id();
        else if (percent <= getMark2Percent() || getMark3Percent() == 0)
            AD_PrintColor_ID = getPrintColor2Id();
        else if (percent <= getMark3Percent() || getMark4Percent() == 0)
            AD_PrintColor_ID = getPrintColor3Id();
        else AD_PrintColor_ID = getPrintColor4Id();
        if (AD_PrintColor_ID == 0) {
            if (getPrintColor3Id() != 0) AD_PrintColor_ID = getPrintColor3Id();
            else if (getPrintColor2Id() != 0) AD_PrintColor_ID = getPrintColor2Id();
            else if (getPrintColor1Id() != 0) AD_PrintColor_ID = getPrintColor1Id();
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
