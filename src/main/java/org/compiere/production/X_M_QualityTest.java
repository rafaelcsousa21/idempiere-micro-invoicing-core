package org.compiere.production;

import org.compiere.model.I_M_QualityTest;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for M_QualityTest
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_M_QualityTest extends BasePOName implements I_M_QualityTest, I_Persistent {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_M_QualityTest(Properties ctx, int M_QualityTest_ID) {
        super(ctx, M_QualityTest_ID);
    }

    /**
     * Load Constructor
     */
    public X_M_QualityTest(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 3 - Client - Org
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_M_QualityTest[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Quality Test.
     *
     * @return Quality Test
     */
    public int getM_QualityTest_ID() {
        Integer ii = (Integer) get_Value(COLUMNNAME_M_QualityTest_ID);
        if (ii == null) return 0;
        return ii;
    }

    @Override
    public int getTableId() {
        return I_M_QualityTest.Table_ID;
    }
}
