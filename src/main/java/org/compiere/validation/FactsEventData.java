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

  /**
   * @param acctSchema
   * @param facts
   * @param po
   */
  public FactsEventData(I_C_AcctSchema acctSchema, List<IFact> facts, IPO po) {
    super();
    this.acctSchema = acctSchema;
    this.facts = facts;
    this.po = po;
  }

  /** @return the acctSchema */
  public I_C_AcctSchema getAcctSchema() {
    return acctSchema;
  }

  /** @return the facts */
  public List<IFact> getFacts() {
    return facts;
  }

  /** @return the po */
  public IPO getPo() {
    return po;
  }
}
