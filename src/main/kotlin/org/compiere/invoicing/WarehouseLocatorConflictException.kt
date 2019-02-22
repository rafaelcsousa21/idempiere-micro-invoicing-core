package org.compiere.invoicing

import org.compiere.model.I_M_Locator
import org.compiere.model.I_M_Warehouse
import org.idempiere.common.exceptions.AdempiereException

/**
 * Throwed when a document warehouse does not match with document or document line locator.
 * @author Teo Sarca, www.arhipac.ro
 */
class WarehouseLocatorConflictException
/**
 * @param wh warehouse
 * @param locator locator
 * @param lineNo Document Line#
 */
    (wh: I_M_Warehouse?, locator: I_M_Locator?, lineNo: Int) : AdempiereException(
    "@WarehouseLocatorConflict@" +
            " @M_Warehouse_ID@: " + (if (wh != null) wh.name else "?") +
            " @M_Locator_ID@: " + (if (locator != null) locator.value else "?") +
            " @Line@: " + lineNo
) {
    companion object {
        /**
         *
         */
        private val serialVersionUID = 4812283712626432829L
    }
}