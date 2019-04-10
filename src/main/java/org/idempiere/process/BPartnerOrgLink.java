package org.idempiere.process;

import org.compiere.accounting.MWarehouse;
import org.compiere.crm.MBPartner;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Locator;
import org.compiere.orm.MOrg;
import org.compiere.orm.MOrgInfo;
import org.compiere.orm.MOrgKt;
import org.compiere.orm.MRoleKt;
import org.compiere.orm.MRoleOrgAccess;
import org.compiere.process.SvrProcess;
import org.compiere.production.MLocator;
import org.idempiere.common.util.AdempiereUserError;

import java.util.List;
import java.util.logging.Level;

/**
 * Link Business Partner to Organization. Either to existing or create new one
 *
 * @author Jorg Janke
 * @version $Id: BPartnerOrgLink.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class BPartnerOrgLink extends SvrProcess {
    /**
     * Existing Org
     */
    private int p_AD_Org_ID;
    /**
     * Info for New Org
     */
    private int p_AD_OrgType_ID;
    /**
     * Business Partner
     */
    private int p_C_BPartner_ID;
    /**
     * Role
     */
    private int p_AD_Role_ID;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            switch (name) {
                case "AD_Org_ID":
                    p_AD_Org_ID = iProcessInfoParameter.getParameterAsInt();
                    break;
                case "AD_OrgType_ID":
                    p_AD_OrgType_ID = iProcessInfoParameter.getParameterAsInt();
                    break;
                case "AD_Role_ID":
                    p_AD_Role_ID = iProcessInfoParameter.getParameterAsInt();
                    break;
                default:
                    log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
                    break;
            }
        }
        p_C_BPartner_ID = getRecordId();
    } //	prepare

    /**
     * Perform process.
     *
     * @return Message (text with variables)
     * @throws Exception if not successful
     */
    protected String doIt() throws Exception {
        if (log.isLoggable(Level.INFO))
            log.info(
                    "C_BPartner_ID="
                            + p_C_BPartner_ID
                            + ", AD_Org_ID="
                            + p_AD_Org_ID
                            + ", AD_OrgType_ID="
                            + p_AD_OrgType_ID
                            + ", AD_Role_ID="
                            + p_AD_Role_ID);
        if (p_C_BPartner_ID == 0) throw new AdempiereUserError("No Business Partner ID");
        MBPartner bp = new MBPartner(p_C_BPartner_ID);
        if (bp.getId() == 0)
            throw new AdempiereUserError("Business Partner not found - C_BPartner_ID=" + p_C_BPartner_ID);
        //	BP Location
        List<I_C_BPartner_Location> locs = bp.getLocations(false);
        if (locs.size() == 0)
            throw new IllegalArgumentException("Business Partner has no Location");
        //	Location
        int C_Location_ID = locs.get(0).getLocationId();
        if (C_Location_ID == 0)
            throw new IllegalArgumentException("Business Partner Location has no Address");

        //	Create Org
        boolean newOrg = p_AD_Org_ID == 0;
        MOrg org = MOrgKt.getOrg(p_AD_Org_ID);
        if (newOrg) {
            org.setSearchKey(bp.getSearchKey());
            org.setName(bp.getName());
            org.setDescription(bp.getDescription());
            if (!org.save()) throw new Exception("Organization not saved");
        } else //	check if linked to already
        {
            int C_BPartner_ID = org.getLinkedBusinessPartnerId();
            if (C_BPartner_ID > 0)
                throw new IllegalArgumentException(
                        "Organization '"
                                + org.getName()
                                + "' already linked (to C_BPartner_ID="
                                + C_BPartner_ID
                                + ")");
        }
        p_AD_Org_ID = org.getOrgId();

        //	Update Org Info
        MOrgInfo oInfo = org.getInfo();
        oInfo.setOrgTypeId(p_AD_OrgType_ID);
        if (newOrg) oInfo.setLocationId(C_Location_ID);

        //	Create Warehouse
        MWarehouse wh = null;
        if (!newOrg) {
            MWarehouse[] whs = MWarehouse.getForOrg(p_AD_Org_ID);
            if (whs.length > 0) wh = whs[0]; // 	pick first
        }
        //	New Warehouse
        if (wh == null) {
            wh = new MWarehouse(org);
            if (!wh.save()) throw new Exception("Warehouse not saved");
        }
        //	Create Locator
        I_M_Locator mLoc = wh.getDefaultLocator();
        if (mLoc == null) {
            mLoc = new MLocator(wh, "Standard");
            mLoc.setIsDefault(true);
            mLoc.saveEx();
        }

        //	Update/Save Org Info
        oInfo.setWarehouseId(wh.getWarehouseId());
        if (!oInfo.save()) throw new Exception("Organization Info not saved");

        //	Update BPartner
        bp.setLinkedOrganizationId(p_AD_Org_ID);
        if (bp.getOrgId() != 0) bp.setClientOrg(bp.getClientId(), 0); // 	Shared BPartner

        //	Save BP
        if (!bp.save()) throw new Exception("Business Partner not updated");

        //	Limit to specific Role
        if (p_AD_Role_ID != 0) {
            boolean found = false;
            MRoleOrgAccess[] orgAccesses = MRoleOrgAccess.getOfOrg(p_AD_Org_ID);
            //	delete all accesses except the specific
            for (MRoleOrgAccess orgAccess1 : orgAccesses) {
                if (orgAccess1.getRoleId() == p_AD_Role_ID) found = true;
                else orgAccess1.delete(true);
            }
            //	create access
            if (!found) {
                MRoleOrgAccess orgAccess = new MRoleOrgAccess(org, p_AD_Role_ID);
                orgAccess.saveEx();
            }
        }

        //	Reset Client Role
        MRoleKt.getDefaultRole(true);

        return "Business Partner - Organization Link created";
    } //	doIt
} //	BPartnerOrgLink
