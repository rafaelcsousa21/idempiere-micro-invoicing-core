package org.compiere.accounting;

import kotliquery.Row;
import org.compiere.model.I_C_ValidCombination;
import org.idempiere.common.exceptions.DBException;
import org.idempiere.common.util.CLogger;

import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdateEx;


/**
 * Accounting Fact Model
 *
 * @author Jorg Janke
 * @author Teo Sarca, http://www.arhipac.ro
 * <li>FR [ 2079083 ] Add MFactAcct.deleteEx method
 * @version $Id: MFactAcct.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MFactAcct extends X_Fact_Acct {
    /**
     *
     */
    private static final long serialVersionUID = 5251847162314796574L;
    /**
     * Static Logger
     */
    private static CLogger s_log = CLogger.getCLogger(MFactAcct.class);

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx          context
     * @param Fact_Acct_ID id
     */
    public MFactAcct(int Fact_Acct_ID) {
        super(Fact_Acct_ID);
    } //	MFactAcct

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MFactAcct(Row row) {
        super(row);
    } //	MFactAcct

    /**
     * Delete Accounting
     *
     * @param AD_Table_ID table
     * @param Record_ID   record
     * @return number of rows or -1 for error
     * @deprecated Since ADempiere 3.5.2a; please use {@link #deleteEx(int, int, String)} instead.
     */
    public static int delete(int AD_Table_ID, int Record_ID) {
        int no = -1;
        try {
            no = deleteEx(AD_Table_ID, Record_ID);
        } catch (DBException e) {
            s_log.log(Level.SEVERE, "failed: AD_Table_ID=" + AD_Table_ID + ", Record_ID" + Record_ID, e);
        }
        return no;
    } //	delete

    /**
     * Delete Accounting
     *
     * @param AD_Table_ID table
     * @param Record_ID   record
     * @return number of rows deleted
     * @throws DBException on database exception
     */
    public static int deleteEx(int AD_Table_ID, int Record_ID) throws DBException {
        final String sql = "DELETE Fact_Acct WHERE AD_Table_ID=? AND Record_ID=?";
        int no = executeUpdateEx(sql, new Object[]{AD_Table_ID, Record_ID});
        if (s_log.isLoggable(Level.FINE))
            s_log.fine("delete - AD_Table_ID=" + AD_Table_ID + ", Record_ID=" + Record_ID + " - #" + no);
        return no;
    }

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MFactAcct[");
        sb.append(getId())
                .append("-Acct=")
                .append(getAccountId())
                .append(",Dr=")
                .append(getAmtSourceDr())
                .append("|")
                .append(getAmtAcctDr())
                .append(",Cr=")
                .append(getAmtSourceCr())
                .append("|")
                .append(getAmtAcctCr())
                .append("]");
        return sb.toString();
    } //	toString

    /**
     * Derive MAccount from record
     *
     * @return Valid Account Combination
     */
    public I_C_ValidCombination getMAccount() {
        I_C_ValidCombination acct =
                MAccount.get(
                        getClientId(),
                        getOrgId(),
                        getAccountingSchemaId(),
                        getAccountId(),
                        getSubAccountId(),
                        getProductId(),
                        getBusinessPartnerId(),
                        getTransactionOrganizationId(),
                        getLocationFromId(),
                        getLocationToId(),
                        getSalesRegionId(),
                        getProjectId(),
                        getCampaignId(),
                        getBusinessActivityId(),
                        getUser1Id(),
                        getUser2Id(),
                        getUserElement1Id(),
                        getUserElement2Id()
                );
        if (acct != null && acct.getId() == 0) acct.saveEx();
        return acct;
    } //	getMAccount
} //	MFactAcct
