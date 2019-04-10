package org.compiere.accounting;

import org.compiere.bo.MCurrencyKt;
import org.compiere.conversionrate.MConversionType;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.Env;

import java.sql.Timestamp;

/**
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public final class MConversionRateUtil {

    private MConversionRateUtil() {
        // nothing
    }

    /**
     * Return the message to show when no exchange rate is found
     */
    public static String getErrorMessage(

            String adMessage,
            int currencyFromID,
            int currencyToID,
            int convertionTypeID,
            Timestamp date,
            String trxName) {
        if (convertionTypeID == 0)
            convertionTypeID = MConversionType.getDefault(Env.getClientId());
        return MsgKt.getMsg(
                adMessage,
                new Object[]{
                        MCurrencyKt.getCurrency(currencyFromID).getISOCode(),
                        MCurrencyKt.getCurrency(currencyToID).getISOCode(),
                        new MConversionType(convertionTypeID).getName(),
                        date
                });
    }
}
