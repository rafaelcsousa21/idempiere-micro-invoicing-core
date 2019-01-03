package org.compiere.invoicing.test

import company.bigger.test.support.randomString
import org.compiere.crm.MCountry
import org.compiere.process.ProcessInfo
import org.idempiere.common.util.ServerContext
import org.idempiere.process.AcctSchemaDefaultCopy
import org.idempiere.process.InitialClientSetup
import java.util.*
import kotlin.test.assertTrue

class SetupClientTests : BaseComponentTest() {
    companion object {
        internal fun createClient(ctx: Properties, unlogClients: () -> Unit) {
            val clientName = randomString(20)
            val orgValue = randomString(20)
            val orgName = randomString(20)
            val userClient = randomString(20)
            val eMail = randomString(5) + "@test.com"
            val adminEmail = randomString(5) + "@test.com"
            val countryId = 101
            // TODO: fix this bug - the list of the countries needs to be prepared before calling the InitialClientSetup
            val country = MCountry.get(ctx, countryId)
            // TODO: fix the context
            ServerContext.setCurrentInstance(Properties())
            // TODO: fix the system client
            unlogClients()
            val clientSetup = InitialClientSetup(
                p_ClientName = clientName, p_OrgValue = orgValue, p_OrgName = orgName, p_AdminUserName = adminEmail,
                p_NormalUserName = userClient, p_EMail = eMail, p_UseDefaultCoA = false, p_C_Currency_ID = 102, // EUR
                p_C_Country_ID = country.id, // Germany - Deutschland
                p_CoAFile = "src/test/resources/coa/AccountingCZ.csv",
                p_Phone = randomString(10), p_Phone2 = randomString(10), p_Fax = "", p_AdminUserEmail = adminEmail,
                p_NormalUserEmail = eMail, p_CityName = randomString(9), p_Postal = randomString(6),
                p_Address1 = randomString(20)
            )
            val pi = ProcessInfo("", 0)
            assertTrue(clientSetup.startProcess(ctx, pi))
            val acctSchemaDefaultCopy = AcctSchemaDefaultCopy(
                1000000, false
            )
            assertTrue(acctSchemaDefaultCopy.startProcess(ctx, pi))
        }
    }
}