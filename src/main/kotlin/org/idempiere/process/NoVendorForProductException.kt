package org.idempiere.process

import org.idempiere.common.exceptions.AdempiereException

/**
 * Throwed when there is no Vendor Info for given Product.
 * @author Teo Sarca, www.arhipac.ro
 *  * FR [ 2457781 ] Introduce NoVendorForProductException
 */
class NoVendorForProductException
/**
 * @param productName M_Product Name
 */
    (productName: String) : AdempiereException("@NoVendorForProduct@ $productName") {
    companion object {
        /**
         *
         */
        private val serialVersionUID = 3412903630540562323L
    }
}