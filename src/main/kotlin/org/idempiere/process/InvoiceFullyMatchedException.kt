package org.idempiere.process

import org.idempiere.common.exceptions.AdempiereException

/**
 * Throwed when an invoice is fully matched so no more receipts can be generated.
 * @author Teo Sarca, www.arhipac.ro
 */
class InvoiceFullyMatchedException : AdempiereException("@$AD_Message@") {
    companion object {
        /**
         *
         */
        private val serialVersionUID = -7474922528576404203L

        val AD_Message = "InvoiceFullyMatched"
    }
}