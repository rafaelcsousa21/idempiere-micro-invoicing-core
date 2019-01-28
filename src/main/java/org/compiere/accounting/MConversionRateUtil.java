/** */
package org.compiere.accounting;

import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.conversionrate.MConversionType;
import org.compiere.product.MCurrency;
import org.compiere.util.Msg;
import org.idempiere.common.util.Env;

/** @author Teo Sarca, SC ARHIPAC SERVICE SRL */
public final class MConversionRateUtil {

    private MConversionRateUtil() {
    // nothing
  }

    /** Return the message to show when no exchange rate is found */
  public static String getErrorMessage(
      Properties ctx,
      String adMessage,
      int currencyFromID,
      int currencyToID,
      int convertionTypeID,
      Timestamp date,
      String trxName) {
    if (convertionTypeID == 0)
      convertionTypeID = MConversionType.getDefault(Env.getClientId(ctx));
    String retValue =
        Msg.getMsg(
            ctx,
            adMessage,
            new Object[] {
              MCurrency.get(ctx, currencyFromID).getISO_Code(),
              MCurrency.get(ctx, currencyToID).getISO_Code(),
              new MConversionType(ctx, convertionTypeID, trxName).getName(),
              date
            });
    return retValue;
  }
}
