package org.compiere.accounting

import org.idempiere.common.exceptions.AdempiereException

/**
 *
 * @author hengsin
 */
class AverageCostingNegativeQtyException : AdempiereException {

    constructor(message: String) : super(message) {}

    companion object {

        /**
         * generated serial version id
         */
        private val serialVersionUID = 4165497320719149773L
    }
}