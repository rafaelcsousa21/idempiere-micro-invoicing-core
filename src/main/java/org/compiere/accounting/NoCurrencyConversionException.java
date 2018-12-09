package org.compiere.accounting;

import java.sql.Timestamp;
import java.text.DateFormat;
import org.compiere.conversionrate.MConversionType;
import org.compiere.model.HasName;
import org.compiere.product.MCurrency;
import org.compiere.util.DisplayType;
import org.idempiere.common.exceptions.AdempiereException;

import org.idempiere.common.util.Env;

/**
 * Any exception that occurs when no currency conversion rate was found
 *
 * @author Teo Sarca, http://www.arhipac.ro
 */
public class NoCurrencyConversionException extends AdempiereException {

  /** */
  private static final long serialVersionUID = 1593966161685137709L;

  /**
   * @param C_Currency_ID
   * @param C_Currency_ID_To
   * @param ConvDate
   * @param C_ConversionType_ID
   * @param AD_Client_ID
   * @param AD_Org_ID
   */
  public NoCurrencyConversionException(
      int C_Currency_ID,
      int C_Currency_ID_To,
      Timestamp ConvDate,
      int C_ConversionType_ID,
      int AD_Client_ID,
      int AD_Org_ID) {
    super(
        buildMessage(
            C_Currency_ID,
            C_Currency_ID_To,
            ConvDate,
            C_ConversionType_ID,
            AD_Client_ID,
            AD_Org_ID));
  }

  private static final String buildMessage(
      int C_Currency_ID,
      int C_Currency_ID_To,
      Timestamp ConvDate,
      int C_ConversionType_ID,
      int AD_Client_ID,
      int AD_Org_ID) {
    DateFormat df = DisplayType.getDateFormat(DisplayType.Date);

    StringBuffer sb =
        new StringBuffer("@NoCurrencyConversion@ ")
            .append(MCurrency.getISO_Code(Env.getCtx(), C_Currency_ID))
            .append("->")
            .append(MCurrency.getISO_Code(Env.getCtx(), C_Currency_ID_To));
    //
    sb.append(", @Date@: ");
    if (ConvDate != null) sb.append(df.format(ConvDate));
    else sb.append("*");
    //
    sb.append(", @C_ConversionType_ID@: ");
    if (C_ConversionType_ID > 0) {
      final String sql =
          "SELECT "
              + HasName.Companion.getCOLUMNNAME_Name()
              + " FROM "
              + MConversionType.Table_Name
              + " WHERE "
              + MConversionType.COLUMNNAME_C_ConversionType_ID
              + "=?";
      String name = DB.getSQLValueString(null, sql, C_ConversionType_ID);
      sb.append(name);
    } else {
      sb.append("*");
    }
    return sb.toString();
  }
}
