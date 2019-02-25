package org.compiere.invoicing;

import org.compiere.model.I_A_Asset_Group_Acct;
import org.compiere.orm.PO;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for A_Asset_Group_Acct
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_A_Asset_Group_Acct extends PO implements I_A_Asset_Group_Acct {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_A_Asset_Group_Acct(Properties ctx, int A_Asset_Group_Acct_ID) {
        super(ctx, A_Asset_Group_Acct_ID);
        /**
         * if (A_Asset_Group_Acct_ID == 0) { setA_Accumdepreciation_Acct (0); setA_Asset_Acct (0);
         * setA_Asset_Group_Acct_ID (0); setA_Asset_Group_ID (0); setA_Depreciation_Acct (0);
         * setA_Depreciation_F_ID (0); setA_Depreciation_ID (0); setA_Disposal_Loss_Acct (0);
         * setA_Disposal_Revenue_Acct (0); setA_Split_Percent (Env.ZERO); // 1 setAccountingSchemaId (0);
         * setPostingType (null); // 'A' setUseLifeMonths_F (0); // 0 setUseLifeYears_F (0); // 0 }
         */
    }

    /**
     * Load Constructor
     */
    public X_A_Asset_Group_Acct(Properties ctx, ResultSet rs) {
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

    @Override
    public int getTableId() {
        return Table_ID;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_A_Asset_Group_Acct[").append(getId()).append("]");
        return sb.toString();
    }

}
