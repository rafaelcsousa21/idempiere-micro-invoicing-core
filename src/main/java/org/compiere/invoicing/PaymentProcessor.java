package org.compiere.invoicing;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;

import org.compiere.model.PaymentInterface;
import org.idempiere.common.util.CLogger;

/**
 * Payment Processor Abstract Class
 *
 * @author Jorg Janke
 * @version $Id: PaymentProcessor.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public abstract class PaymentProcessor {

    /** Logger */
  protected CLogger log = CLogger.getCLogger(getClass());
  /** Payment Processor Logger */
  @SuppressWarnings("unused")
  private static CLogger s_log = CLogger.getCLogger(PaymentProcessor.class);
  /** Encoding (ISO-8859-1 - UTF-8) */
  public static final String ENCODING = "UTF-8";
  /** Encode Parameters */
  private boolean m_encoded = false;
  /** Ampersand */
  public static final char AMP = '&';
  /** Equals */
  public static final char EQ = '=';

    protected PaymentInterface p_mp = null;
  //
  private int m_timeout = 30;

  /** ********************************************************************** */

    /** *********************************************************************** */
  // Validation methods. Override if you have specific needs.

    /**
   * Check for delimiter fields &= and add length of not encoded
   *
   * @param name name
   * @param value value
   * @param maxLength maximum length
   * @return name[5]=value or name=value
   */
  protected String createPair(String name, String value, int maxLength) {
    //  Nothing to say
    if (name == null || name.length() == 0 || value == null || value.length() == 0) return "";

    if (value.length() > maxLength) value = value.substring(0, maxLength);

    StringBuilder retValue = new StringBuilder(name);
    if (m_encoded)
      try {
        value = URLEncoder.encode(value, ENCODING);
      } catch (UnsupportedEncodingException e) {
        log.log(Level.SEVERE, value + " - " + e.toString());
      }
    else if (value.indexOf(AMP) != -1 || value.indexOf(EQ) != -1)
      retValue.append("[").append(value.length()).append("]");
    //
    retValue.append(EQ);
    retValue.append(value);
    return retValue.toString();
  } // createPair

} //  PaymentProcessor
