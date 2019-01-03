package org.compiere.accounting

import org.compiere.model.IDocFactory
import org.idempiere.common.base.IServicesHolder

class DefaultDocumentFactoryHolder : IServicesHolder<IDocFactory> {
    override val services: List<IDocFactory>
        get() = mutableListOf(DefaultDocumentFactory())
}