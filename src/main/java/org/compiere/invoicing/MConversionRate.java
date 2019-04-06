package org.compiere.invoicing;

import org.compiere.accounting.MClientKt;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class MConversionRate extends org.compiere.conversionrate.MConversionRate {

    public MConversionRate(int C_Conversion_Rate_ID) {
        super(C_Conversion_Rate_ID);
    }

    /**
     * Convert an amount to base Currency
     *
     * @param CurFrom_ID          The C_Currency_ID FROM
     * @param ConvDate            conversion date - if null - use current date
     * @param C_ConversionType_ID conversion rate type - if 0 - use Default
     * @param Amt                 amount to be converted
     * @param AD_Client_ID        client
     * @param AD_Org_ID           organization
     * @return converted amount
     */
    public static BigDecimal convertBase(

            BigDecimal Amt,
            int CurFrom_ID,
            Timestamp ConvDate,
            int C_ConversionType_ID,
            int AD_Client_ID,
            int AD_Org_ID) {
        return convert(

                Amt,
                CurFrom_ID,
                MClientKt.getClientWithAccounting().getCurrencyId(),
                ConvDate,
                C_ConversionType_ID,
                AD_Client_ID,
                AD_Org_ID);
    } //	convertBase
}
