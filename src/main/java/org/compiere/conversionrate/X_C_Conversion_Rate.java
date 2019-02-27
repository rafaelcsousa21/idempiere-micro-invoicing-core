package org.compiere.conversionrate;

import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Generated Model for C_Conversion_Rate
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Conversion_Rate extends PO implements I_C_Conversion_Rate {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_Conversion_Rate(Properties ctx, int C_Conversion_Rate_ID) {
        super(ctx, C_Conversion_Rate_ID);
        /**
         * if (C_Conversion_Rate_ID == 0) { setC_Conversion_Rate_ID (0); setConversionTypeId (0);
         * setCurrencyId (0); setC_Currency_ID_To (0); setDivideRate (Env.ZERO); setMultiplyRate
         * (Env.ZERO); setValidFrom (new Timestamp( System.currentTimeMillis() )); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_Conversion_Rate(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 6 - System - Client
     */
    protected int getAccessLevel() {
        return I_C_Conversion_Rate.accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_C_Conversion_Rate[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Conversion Rate.
     *
     * @return Rate used for converting currencies
     */
    public int getC_Conversion_Rate_ID() {
        Integer ii = (Integer) getValue(I_C_Conversion_Rate.COLUMNNAME_C_Conversion_Rate_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Currency Type.
     *
     * @param C_ConversionType_ID Currency Conversion Rate Type
     */
    public void setConversionTypeId(int C_ConversionType_ID) {
        if (C_ConversionType_ID < 1)
            setValue(I_C_Conversion_Rate.COLUMNNAME_C_ConversionType_ID, null);
        else
            setValue(
                    I_C_Conversion_Rate.COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
    }

    /**
     * Get Currency.
     *
     * @return The Currency for this record
     */
    public int getCurrencyId() {
        Integer ii = (Integer) getValue(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Currency.
     *
     * @param C_Currency_ID The Currency for this record
     */
    public void setCurrencyId(int C_Currency_ID) {
        if (C_Currency_ID < 1) setValue(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID, null);
        else setValue(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
    }

    /**
     * Get Currency To.
     *
     * @return Target currency
     */
    public int getC_Currency_ID_To() {
        Integer ii = (Integer) getValue(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID_To);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Currency To.
     *
     * @param C_Currency_ID_To Target currency
     */
    public void setC_Currency_ID_To(int C_Currency_ID_To) {
        setValue(I_C_Conversion_Rate.COLUMNNAME_C_Currency_ID_To, Integer.valueOf(C_Currency_ID_To));
    }

    /**
     * Get Divide Rate.
     *
     * @return To convert Source number to Target number, the Source is divided
     */
    public BigDecimal getDivideRate() {
        BigDecimal bd = (BigDecimal) getValue(I_C_Conversion_Rate.COLUMNNAME_DivideRate);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Divide Rate.
     *
     * @param DivideRate To convert Source number to Target number, the Source is divided
     */
    public void setDivideRate(BigDecimal DivideRate) {
        setValue(I_C_Conversion_Rate.COLUMNNAME_DivideRate, DivideRate);
    }

    /**
     * Get Multiply Rate.
     *
     * @return Rate to multiple the source by to calculate the target.
     */
    public BigDecimal getMultiplyRate() {
        BigDecimal bd = (BigDecimal) getValue(I_C_Conversion_Rate.COLUMNNAME_MultiplyRate);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Multiply Rate.
     *
     * @param MultiplyRate Rate to multiple the source by to calculate the target.
     */
    public void setMultiplyRate(BigDecimal MultiplyRate) {
        setValue(I_C_Conversion_Rate.COLUMNNAME_MultiplyRate, MultiplyRate);
    }

    /**
     * Get Valid from.
     *
     * @return Valid from including this date (first day)
     */
    public Timestamp getValidFrom() {
        return (Timestamp) getValue(I_C_Conversion_Rate.COLUMNNAME_ValidFrom);
    }

    /**
     * Set Valid from.
     *
     * @param ValidFrom Valid from including this date (first day)
     */
    public void setValidFrom(Timestamp ValidFrom) {
        setValue(I_C_Conversion_Rate.COLUMNNAME_ValidFrom, ValidFrom);
    }

    /**
     * Get Valid to.
     *
     * @return Valid to including this date (last day)
     */
    public Timestamp getValidTo() {
        return (Timestamp) getValue(I_C_Conversion_Rate.COLUMNNAME_ValidTo);
    }

    /**
     * Set Valid to.
     *
     * @param ValidTo Valid to including this date (last day)
     */
    public void setValidTo(Timestamp ValidTo) {
        setValue(I_C_Conversion_Rate.COLUMNNAME_ValidTo, ValidTo);
    }

    @Override
    public int getTableId() {
        return I_C_Conversion_Rate.Table_ID;
    }
}
