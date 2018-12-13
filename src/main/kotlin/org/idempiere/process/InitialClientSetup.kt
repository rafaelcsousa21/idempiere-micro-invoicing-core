package org.idempiere.process

import org.compiere.crm.EMail
import org.compiere.orm.MSysConfig
import org.compiere.process.SvrProcess
import org.compiere.product.MCurrency
import org.idempiere.common.exceptions.AdempiereException
import org.idempiere.common.util.Env
import org.idempiere.common.util.KeyNamePair
import org.idempiere.common.util.Util
import software.hsharp.core.util.executeUpdate
import java.io.File
import java.util.logging.Level

/**
 * Process to create a new client (tenant)
 *
 * @author Carlos Ruiz
 * [ 2598506 ] FR - Implement Initial Client Setup
 */
class InitialClientSetup(
        // Process Parameters
    private var p_ClientName: String,
    private var p_OrgValue: String? = null,
    private var p_OrgName: String,
    private var p_AdminUserName: String,
    private var p_NormalUserName: String,
    private var p_IsSetInitialPassword: Boolean = true,
    private var p_C_Currency_ID: Int = 0,
    private var p_C_Country_ID: Int = 0,
    private var p_C_Region_ID: Int = 0,
    private var p_CityName: String,
    private var p_Postal: String,
    private var p_Address1: String,
    private var p_Phone: String,
    private var p_Phone2: String,
    private var p_Fax: String,
    private var p_EMail: String,
    private var p_TaxID: String? = null,
    private var p_C_City_ID: Int = 0,
    private var p_IsUseBPDimension: Boolean = true,
    private var p_IsUseProductDimension: Boolean = true,
    private var p_IsUseProjectDimension: Boolean = false,
    private var p_IsUseCampaignDimension: Boolean = false,
    private var p_IsUseSalesRegionDimension: Boolean = false,
    private var p_IsUseActivityDimension: Boolean = false,
    private var p_UseDefaultCoA: Boolean = false,
    private var p_CoAFile: String? = null,
    private var p_InactivateDefaults: Boolean = false,
    private var p_AdminUserEmail: String,
    private var p_NormalUserEmail: String
) : SvrProcess() {

    /**
     * Prepare
     */
    override fun prepare() {
        val para = parameter
        for (i in para.indices) {
            val name = para[i].parameterName
            if (para[i].parameter == null)
            else if (name == "ClientName")
                p_ClientName = para[i].parameter as String
            else if (name == "OrgValue")
                p_OrgValue = para[i].parameter as String
            else if (name == "OrgName")
                p_OrgName = para[i].parameter as String
            else if (name == "AdminUserName")
                p_AdminUserName = para[i].parameter as String
            else if (name == "NormalUserName")
                p_NormalUserName = para[i].parameter as String
            else if (name == "IsSetInitialPassword")
                p_IsSetInitialPassword = para[i].parameterAsBoolean
            else if (name == "C_Currency_ID")
                p_C_Currency_ID = para[i].parameterAsInt
            else if (name == "C_Country_ID")
                p_C_Country_ID = para[i].parameterAsInt
            else if (name == "C_Region_ID")
                p_C_Region_ID = para[i].parameterAsInt
            else if (name == "CityName")
                p_CityName = para[i].parameter as String
            else if (name == "C_City_ID")
                p_C_City_ID = para[i].parameterAsInt
            else if (name == "Postal")
                p_Postal = para[i].parameter as String
            else if (name == "Address1")
                p_Address1 = para[i].parameter as String
            else if (name == "IsUseBPDimension")
                p_IsUseBPDimension = para[i].parameterAsBoolean
            else if (name == "IsUseProductDimension")
                p_IsUseProductDimension = para[i].parameterAsBoolean
            else if (name == "IsUseProjectDimension")
                p_IsUseProjectDimension = para[i].parameterAsBoolean
            else if (name == "IsUseCampaignDimension")
                p_IsUseCampaignDimension = para[i].parameterAsBoolean
            else if (name == "IsUseSalesRegionDimension")
                p_IsUseSalesRegionDimension = para[i].parameterAsBoolean
            else if (name == "IsUseActivityDimension")
                p_IsUseActivityDimension = para[i].parameterAsBoolean
            else if (name == "UseDefaultCoA")
                p_UseDefaultCoA = para[i].parameterAsBoolean
            else if (name == "CoAFile")
                p_CoAFile = para[i].parameter as String
            else if (name == "InactivateDefaults")
                p_InactivateDefaults = para[i].parameterAsBoolean
            else if (name == "Phone")
                p_Phone = para[i].parameter as String
            else if (name == "Phone2")
                p_Phone2 = para[i].parameter as String
            else if (name == "Fax")
                p_Fax = para[i].parameter as String
            else if (name == "EMail")
                p_EMail = para[i].parameter as String
            else if (name == "TaxID")
                p_TaxID = para[i].parameter as String
            else if (name == "AdminUserEmail")
                p_AdminUserEmail = para[i].parameter as String
            else if (name == "NormalUserEmail")
                p_NormalUserEmail = para[i].parameter as String
            else
                log.log(Level.SEVERE, "Unknown Parameter: $name")
        }
    }

    /**
     * Process
     * @return info
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun doIt(): String {

        var msglog = StringBuilder("InitialClientSetup")
                .append(": ClientName=").append(p_ClientName)
                .append(", OrgValue=").append(p_OrgValue)
                .append(", OrgName=").append(p_OrgName)
                .append(", AdminUserName=").append(p_AdminUserName)
                .append(", NormalUserName=").append(p_NormalUserName)
                .append(", IsSetInitialPassword=").append(p_IsSetInitialPassword)
                .append(", C_Currency_ID=").append(p_C_Currency_ID)
                .append(", C_Country_ID=").append(p_C_Country_ID)
                .append(", C_Region_ID=").append(p_C_Region_ID)
                .append(", CityName=").append(p_CityName)
                .append(", C_City_ID=").append(p_C_City_ID)
                .append(", IsUseBPDimension=").append(p_IsUseBPDimension)
                .append(", IsUseProductDimension=").append(p_IsUseProductDimension)
                .append(", IsUseProjectDimension=").append(p_IsUseProjectDimension)
                .append(", IsUseCampaignDimension=").append(p_IsUseCampaignDimension)
                .append(", IsUseSalesRegionDimension=").append(p_IsUseSalesRegionDimension)
                .append(", IsUseActivityDimension=").append(p_IsUseActivityDimension)
                .append(", UseDefaultCoA=").append(p_UseDefaultCoA)
                .append(", InactivateDefaults=").append(p_InactivateDefaults)
                .append(", CoAFile=").append(p_CoAFile)

        if (log.isLoggable(Level.INFO)) log.info(msglog.toString())

        // Validations
        if (p_UseDefaultCoA)
            p_CoAFile = null

        // Validate Mandatory parameters
        if (p_ClientName.length == 0 || p_OrgName.length == 0 || p_AdminUserName.length == 0 || p_NormalUserName.length == 0 ||
                p_C_Currency_ID <= 0 || p_C_Country_ID <= 0 || !p_UseDefaultCoA && (p_CoAFile == null || p_CoAFile!!.length == 0))
            throw IllegalArgumentException("Missing required parameters")

        // Validate Uniqueness of client and users name
        // 	Unique Client Name+
        if (executeUpdate("UPDATE AD_Client SET CreatedBy=0 WHERE Name=?", listOf(p_ClientName), false, null) != 0)
            throw AdempiereException("@NotUnique@ " + p_ClientName)

        // 	Unique User Names
        if (executeUpdate("UPDATE AD_User SET CreatedBy=0 WHERE Name=?", listOf(p_AdminUserName), false, null) != 0)
            throw AdempiereException("@NotUnique@ " + p_AdminUserName)
        if (executeUpdate("UPDATE AD_User SET CreatedBy=0 WHERE Name=?", listOf(p_NormalUserName), false, null) != 0)
            throw AdempiereException("@NotUnique@ " + p_NormalUserName)

        // City_ID overrides CityName if both used
        if (p_C_City_ID > 0) {
            val city = MCity.get(ctx, p_C_City_ID)
            if (city!!.name != p_CityName) {
                msglog = StringBuilder("City name changed from ").append(p_CityName).append(" to ").append(city.name)
                if (log.isLoggable(Level.INFO)) log.info(msglog.toString())
                p_CityName = city.name
            }
        }

        // Validate existence and read permissions on CoA file
        val email_login = MSysConfig.getBooleanValue(MSysConfig.USE_EMAIL_FOR_LOGIN, false)
        if (email_login) {
            if (Util.isEmpty(p_AdminUserEmail))
                throw AdempiereException("AdminUserEmail is required")
            if (!EMail.validate(p_AdminUserEmail))
                throw AdempiereException("AdminUserEmail $p_AdminUserEmail is incorrect")
            if (Util.isEmpty(p_NormalUserEmail))
                throw AdempiereException("NormalUserEmail is required")
            if (!EMail.validate(p_NormalUserEmail))
                throw AdempiereException("NormalUserEmail $p_NormalUserEmail is incorrect")
        }
        if (Util.isEmpty(p_CoAFile, true))
            p_CoAFile = MSysConfig.getValue(MSysConfig.DEFAULT_COA_PATH,
                    File.separator + "data" +
                            File.separator + "import" +
                            File.separator + "AccountingDefaultsOnly.csv")
        val coaFile = File(p_CoAFile!!)
        if (!coaFile.exists())
            throw AdempiereException("CoaFile $p_CoAFile does not exist")
        if (!coaFile.canRead())
            throw AdempiereException("Cannot read CoaFile " + p_CoAFile!!)
        if (!coaFile.isFile)
            throw AdempiereException("CoaFile $p_CoAFile is not a file")
        if (coaFile.length() <= 0L)
            throw AdempiereException("CoaFile $p_CoAFile is empty")

        // Process
        val ms = MSetup(Env.getCtx(), WINDOW_THIS_PROCESS)
        try {
            if (!ms.createClient(p_ClientName, p_OrgValue, p_OrgName, p_AdminUserName, p_NormalUserName, p_Phone,
                    p_Phone2, p_Fax, p_EMail, p_TaxID, p_AdminUserEmail, p_NormalUserEmail, p_IsSetInitialPassword)) {
                ms.rollback()
                throw AdempiereException("Create client failed")
            }

            addLog(ms.info)

            //  Generate Accounting
            val currency = MCurrency.get(ctx, p_C_Currency_ID)
            val currency_kp = KeyNamePair(p_C_Currency_ID, currency.description)
            if (!ms.createAccounting(currency_kp,
                            p_IsUseProductDimension, p_IsUseBPDimension, p_IsUseProjectDimension, p_IsUseCampaignDimension, p_IsUseSalesRegionDimension, p_IsUseActivityDimension,
                            coaFile, p_InactivateDefaults)) {
                ms.rollback()
                throw AdempiereException("@AccountSetupError@")
            }

            //  Generate Entities
            if (!ms.createEntities(p_C_Country_ID, p_CityName, p_C_Region_ID, p_C_Currency_ID, p_Postal, p_Address1)) {
                ms.rollback()
                throw AdempiereException("@AccountSetupError@")
            }
            addLog(ms.info)

            // 	Create Print Documents
            // PrintUtil.setupPrintForm(ms.getClientId());
        } catch (e: Exception) {
            ms.rollback()
            throw e
        }

        return "@OK@"
    }

    companion object {

        /** WindowNo for this process  */
        val WINDOW_THIS_PROCESS = 9999
    }
}
