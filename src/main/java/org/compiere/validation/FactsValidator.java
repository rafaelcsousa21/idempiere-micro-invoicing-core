package org.compiere.validation;

import org.compiere.model.IFact;
import org.compiere.model.I_C_AcctSchema;
import org.idempiere.icommon.model.IPO;

import java.util.List;

public interface FactsValidator {

    /**
     * Get Client to be monitored
     *
     * @return clientId
     */
    int getClientId();

    /**
     * @param facts
     * @param po
     * @return error message or null - if not null, the pocument will be marked as Invalid.
     */
    String factsValidate(I_C_AcctSchema schema, List<IFact> facts, IPO po);
}
