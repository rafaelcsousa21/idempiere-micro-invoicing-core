package org.idempiere.process;

import java.util.logging.Level;
import org.compiere.invoicing.MInOut;
import org.compiere.model.IProcessInfoParameter;
import org.compiere.order.MShipper;
import org.compiere.process.SvrProcess;

/**
 * Create Package from Shipment for Shipper
 *
 * @author Jorg Janke
 * @version $Id: PackageCreate.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class PackageCreate extends SvrProcess {
  /** Shipper */
  private int p_M_Shipper_ID = 0;
  /** Parent */
  private int p_M_InOut_ID = 0;
  /** No of Packages */
  private int p_no_of_packages = 0;

  /** Prepare - e.g., get Parameters. */
  protected void prepare() {
    IProcessInfoParameter[] para = getParameter();
    for (int i = 0; i < para.length; i++) {
      String name = para[i].getParameterName();
      if (para[i].getParameter() == null) ;
      else if (name.equals("M_Shipper_ID")) p_M_Shipper_ID = para[i].getParameterAsInt();
      else if (name.equals("M_InOut_ID")) // BF [ 1754889 ] Create Package error
      p_M_InOut_ID = para[i].getParameterAsInt();
      else if (name.equals("NoOfPackages")) p_no_of_packages = para[i].getParameterAsInt();
      else log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
    }

    // Bug [ 1754889 ] Create Package error
    // Commenting these lines because this process is called also from window "Ship/Receipt Confirm"
    // if (p_M_InOut_ID == 0)
    // p_M_InOut_ID = getRecord_ID();

  } //	prepare

  /**
   * Process
   *
   * @return message
   * @throws Exception
   */
  protected String doIt() throws Exception {
    if (log.isLoggable(Level.INFO))
      log.info("doIt - M_InOut_ID=" + p_M_InOut_ID + ", M_Shipper_ID=" + p_M_Shipper_ID);
    if (p_M_InOut_ID == 0) throw new IllegalArgumentException("No Shipment");
    if (p_M_Shipper_ID == 0) throw new IllegalArgumentException("No Shipper");

    MInOut shipment = new MInOut(getCtx(), p_M_InOut_ID);
    if (shipment.getId() != p_M_InOut_ID)
      throw new IllegalArgumentException("Cannot find Shipment ID=" + p_M_InOut_ID);
    MShipper shipper = new MShipper(getCtx(), p_M_Shipper_ID);
    if (shipper.getId() != p_M_Shipper_ID)
      throw new IllegalArgumentException("Cannot find Shipper ID=" + p_M_InOut_ID);
    //

    if (p_no_of_packages == 0) p_no_of_packages = shipment.getNoPackages();
    if (p_no_of_packages <= 0) throw new IllegalArgumentException("No of Packages should not be 0");

    throw new NotImplementedException();

    /*
    StringBuilder info = new StringBuilder();
    if (p_no_of_packages == 1)
    {
    	MPackage pack = MPackage.create (shipment, shipper, null);
    	info.append(pack.getDocumentNo());
    }
    else
    {
    	for (int i = 0; i < p_no_of_packages; i++)
    	{
    		MPackage pack = MPackage.createPackage (shipment, shipper, null);
    		if (i != 0)
    			info.append(", ");
    		info.append(pack.getDocumentNo());
    	}
    }

    return info.toString();*/
  } //	doIt
} //	PackageCreate
