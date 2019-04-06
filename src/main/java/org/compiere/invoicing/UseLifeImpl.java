package org.compiere.invoicing;

import org.compiere.model.SetGetModel;
import org.compiere.model.UseLife;
import org.compiere.orm.PO;
import org.compiere.orm.SetGetUtil;
import org.idempiere.common.util.CLogger;

import java.sql.Timestamp;
import java.util.logging.Level;

/**
 * Asset properties - classification of assets, service period, life use.
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * @version $Id$
 */
public class UseLifeImpl implements UseLife {
    private static final String FIELD_UseLifeYears = "UseLifeYears";
    private static final String FIELD_UseLifeMonths = "UseLifeMonths";
    private static final String FIELD_FiscalPostfix = "_F";

    private SetGetModel m_obj;
    private CLogger log = CLogger.getCLogger(getClass());
    private boolean fiscal;

    /**
     *
     */
    public UseLifeImpl(SetGetModel obj, boolean fiscal) {
        m_obj = obj;
        this.fiscal = fiscal;
    }

    /**
     *
     */
    public static UseLifeImpl get(SetGetModel obj) {
        return new UseLifeImpl(obj, false);
    }

    /**
     *
     */
    public static UseLifeImpl get(SetGetModel obj, boolean fiscal) {
        return new UseLifeImpl(obj, fiscal);
    }

    /**
     *
     */
    private static final String getFieldName(String fieldName, boolean fiscal) {
        String field = fieldName;
        if (fiscal) {
            field += FIELD_FiscalPostfix;
        }
        return field;
    }

    /**
     * Copy UseLifeMonths, UseLifeMonths_F, UseLifeYears, UseLifeYears_F fields from "from" to "to"
     *
     * @param to   destination model
     * @param from source model
     */
    public static void copyValues(PO to, PO from) {
        SetGetUtil.copyValues(
                to,
                from,
                new String[]{"UseLifeMonths", "UseLifeYears", "UseLifeMonths_F", "UseLifeYears_F"},
                null);
    }

    public int getTableId() {
        return m_obj.getTableId();
    }

    public String getTableName() {
        return m_obj.getTableName();
    }

    /**
     *
     */
    public boolean isFiscal() {
        return fiscal;
    }

    /**
     *
     */
    public boolean setAttrValue(String name, Object value) {
        return m_obj.setAttrValue(name, value);
    }

    /**
     *
     */
    public Object getAttrValue(String name) {
        return m_obj.getAttrValue(name);
    }

    /**
     *
     */
    public boolean isAttrValueChanged(String name) {
        return m_obj.isAttrValueChanged(name);
    }

    /**
     * @return use life months
     */
    public int getUseLifeMonths() {
        Object obj = m_obj.getAttrValue(getFieldName(FIELD_UseLifeMonths, fiscal));
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        return 0;
    }

    /**
     * Set UseLifeMonths and UseLifeYears
     *
     * @param value use life months
     */
    public void setUseLifeMonths(int value) {
        if (log.isLoggable(Level.FINE)) log.fine("Entering: value=" + value + ", " + this);
        m_obj.setAttrValue(getFieldName(FIELD_UseLifeMonths, fiscal), value);
        m_obj.setAttrValue(getFieldName(FIELD_UseLifeYears, fiscal), value / 12);
        if (log.isLoggable(Level.FINE)) log.fine("Leaving: value=" + value + ", " + this);
    }

    /**
     * @return use life years
     */
    public int getUseLifeYears() {
        Object obj = m_obj.getAttrValue(getFieldName(FIELD_UseLifeYears, fiscal));
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        return 0;
    }

    /**
     * Set UseLifeYears and UseLifeMonths
     *
     * @param value use life years
     */
    public void setUseLifeYears(int value) {
        if (log.isLoggable(Level.FINE)) log.fine("Entering: value=" + value + ", " + this);
        m_obj.setAttrValue(getFieldName(FIELD_UseLifeYears, fiscal), value);
        m_obj.setAttrValue(getFieldName(FIELD_UseLifeMonths, fiscal), value * 12);
        if (log.isLoggable(Level.FINE)) log.fine("Leaving: value=" + value + ", " + this);
    }

    /**
     * @return Asset Service Date (PIF)
     */
    public Timestamp getAssetServiceDate() {
        if (m_obj instanceof UseLife) {
            return ((UseLife) m_obj).getAssetServiceDate();
        } else {
            Object obj = m_obj.getAttrValue("AssetServiceDate");
            if (obj instanceof Timestamp) {
                return (Timestamp) obj;
            }
        }
        return null;
    }

    /**
     * Validates and corrects errors in model
     */
    public boolean validate() {
        return validate(true);
    }

    /**
     * Validates and corrects errors in model
     */
    public boolean validate(boolean saveError) {
        if (log.isLoggable(Level.FINE)) log.fine("Entering: " + this);

        int useLifeYears;
        int useLifeMonths;
        useLifeYears = getUseLifeYears();
        useLifeMonths = getUseLifeMonths();

        if (useLifeMonths == 0) {
            useLifeMonths = useLifeYears * 12;
        }
        if (useLifeMonths % 12 != 0) {
            if (saveError)
                log.saveError(
                        "Error",
                        "@Invalid@ @UseLifeMonths@=" + useLifeMonths + "(@Diff@=" + (useLifeMonths % 12) + ")");
            return false;
        }
        if (useLifeYears == 0) {
            useLifeYears = useLifeMonths / 12;
        }

        setUseLifeMonths(useLifeMonths);
        setUseLifeYears(useLifeYears);

        if (log.isLoggable(Level.FINE)) log.fine("Leaving [RETURN TRUE]");
        return true;
    }

    /**
     * String representation (intern)
     */
    public String toString() {
        return "UseLifeImpl[UseLife="
                + getUseLifeYears()
                + "|"
                + getUseLifeMonths()
                + ", isFiscal="
                + isFiscal()
                + ", AssetServiceDate="
                + getAssetServiceDate()
                // + ", A_Asset_Class=" + getA_Asset_ClassId() //commented by @win
                + ", m_obj="
                + m_obj
                + "]";
    }

}
