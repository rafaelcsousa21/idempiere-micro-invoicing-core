package org.compiere.validation;

import org.compiere.process.ImportProcess;
import org.idempiere.orm.PO;

/** @author hengsin */
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

  /** @return the importProcess */
  public ImportProcess getImportProcess() {
    return importProcess;
  }

  /** @return the source */
  public PO getSource() {
    return source;
  }

  /** @return the target */
  public PO getTarget() {
    return target;
  }
}
