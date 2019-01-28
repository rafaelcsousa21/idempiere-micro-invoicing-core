/** */
package org.compiere.invoicing;

import org.idempiere.common.exceptions.AdempiereException;

/**
 * Asset Related General Exception. This is the root of all Asset Related Exceptions.
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public class AssetException extends AdempiereException {

  /** */
  private static final long serialVersionUID = -2163958702697438015L;

  public AssetException(String message) {
    super(message);
  }

}
