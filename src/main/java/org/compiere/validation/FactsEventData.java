package org.compiere.validation;

import java.util.List;
import org.compiere.model.IFact;
import org.compiere.model.I_C_AcctSchema;
import org.idempiere.icommon.model.IPO;

/** @author hengsin */
public class FactsEventData {
  private I_C_AcctSchema acctSchema;
  private List<IFact> facts;
  private IPO po;

}
