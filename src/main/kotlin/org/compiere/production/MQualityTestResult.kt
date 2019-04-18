package org.compiere.production

import kotliquery.Row
import org.idempiere.icommon.model.IPO

class MQualityTestResult : X_M_QualityTestResult {

    constructor(M_QualityTestResult_ID: Int) : super(M_QualityTestResult_ID) {}

    constructor(row: Row) : super(row)

    companion object {
        private const val serialVersionUID = -5529865718027582930L
    }
}
