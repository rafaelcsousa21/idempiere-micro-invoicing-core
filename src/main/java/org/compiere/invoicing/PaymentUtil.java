package org.compiere.invoicing;

/**
 * @author Elaine
 */
public class PaymentUtil {

    public static String encrpytCreditCard(String value) {
        if (value == null) return "";
        else if (value.length() <= 4) return value;

        Integer valueLength = value.length();

        StringBuilder encryptedCC = new StringBuilder();

        for (int i = 0; i < (valueLength - 4); i++) {
            encryptedCC.append("0");
        }

        encryptedCC.append(value.substring(valueLength - 4, valueLength));

        return encryptedCC.toString();
    }

    public static String encrpytCvv(String creditCardVV) {
        if (creditCardVV == null) return "";
        else {
            Integer valueLength = creditCardVV.length();

            StringBuilder encryptedCC = new StringBuilder();

            for (int i = 0; i < valueLength; i++) {
                encryptedCC.append("0");
            }
            return encryptedCC.toString();
        }
    }

}
