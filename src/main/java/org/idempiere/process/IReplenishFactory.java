package org.idempiere.process;

import org.compiere.util.ReplenishInterface;

public interface IReplenishFactory {

  /**
   * @param className
   * @return Replenish instance
   */
  public ReplenishInterface newReplenishInstance(String className);
}
