package org.compiere.bank;

import org.apache.commons.validator.routines.IBANValidator;

public class IBAN {

    /**
     * @param iban
     * @return normalized IBAN
     */
    public static String normalizeIBAN(String iban) {
        if (iban != null) {
            return iban.trim().replace(" ", "");
        }
        return null;
    }

    /**
     * @param iban
     * @return boolean indicating if specific IBAN is valid
     */
    public static boolean isValid(String iban) {
        IBANValidator iBANValidator = IBANValidator.getInstance();

        return iBANValidator.isValid(iban);
    }
}
