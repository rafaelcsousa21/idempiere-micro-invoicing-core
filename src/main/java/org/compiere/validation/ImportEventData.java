package org.compiere.validation;

import org.compiere.process.ImportProcess;
import org.idempiere.orm.PO;

/**
 * @author hengsin
 */
public class ImportEventData {
    private ImportProcess importProcess;
    private PO source;
    private PO target;

    /**
     * @param importProcess
     * @param source
     * @param target
     */
    public ImportEventData(ImportProcess importProcess, PO source, PO target) {
        super();
        this.importProcess = importProcess;
        this.source = source;
        this.target = target;
    }
}
