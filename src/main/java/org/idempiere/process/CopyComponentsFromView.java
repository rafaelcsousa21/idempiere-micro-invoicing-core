package org.idempiere.process;

import org.compiere.model.IProcessInfoParameter;
import org.compiere.model.ViewColumn;
import org.compiere.model.ViewComponent;
import org.compiere.orm.MTable;
import org.compiere.orm.MViewColumn;
import org.compiere.orm.MViewComponent;
import org.compiere.orm.PO;
import org.compiere.process.SvrProcess;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.AdempiereSystemError;

import java.util.logging.Level;

/**
 * Copy components from one view to other
 *
 * @author Diego Ruiz - Bx Service GmbH
 * @version $Id: CopyComponentsFromView
 */
public class CopyComponentsFromView extends SvrProcess {

    /**
     * Target Table
     */
    private int p_target_AD_Table_ID = 0;
    /**
     * Source Table
     */
    private int p_source_AD_Table_ID = 0;

    /**
     * Column Count
     */
    private int m_count = 0;

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            if (name.equals("AD_Table_ID")) p_source_AD_Table_ID = iProcessInfoParameter.getParameterAsInt();
            else log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
        p_target_AD_Table_ID = getRecordId();
    } //	prepare

    /**
     * Process
     *
     * @return info
     * @throws Exception
     */
    protected String doIt() throws Exception {
        if (p_target_AD_Table_ID == 0)
            throw new AdempiereSystemError("@NotFound@ @AD_Table_ID@ " + p_target_AD_Table_ID);
        if (p_source_AD_Table_ID == 0)
            throw new AdempiereSystemError("@NotFound@ @AD_Table_ID@ " + p_source_AD_Table_ID);
        if (log.isLoggable(Level.INFO))
            log.info(
                    "Source AD_Table_ID="
                            + p_source_AD_Table_ID
                            + ", Target AD_Table_ID="
                            + p_target_AD_Table_ID);

        MTable targetTable = new MTable(p_target_AD_Table_ID);
        ViewComponent[] targetViewComponents = targetTable.getViewComponent(true);
        if (targetViewComponents.length > 0)
            throw new AdempiereSystemError(MsgKt.getMsg("ErrorCopyView"));

        MTable sourceTable = new MTable(p_source_AD_Table_ID);
        ViewComponent[] sourceViewComponents = sourceTable.getViewComponent(true);

        for (int i = 0; i < sourceViewComponents.length; i++) {
            MViewComponent viewComponentTarget = new MViewComponent(targetTable);
            PO.copyValues((PO)sourceViewComponents[i], viewComponentTarget);
            viewComponentTarget.setViewTableId(targetTable.getTableTableId());
            viewComponentTarget.setEntityType(targetTable.getEntityType());

            viewComponentTarget.setIsActive(sourceViewComponents[i].isActive());
            viewComponentTarget.saveEx();

            copyViewColumns(sourceViewComponents[i], viewComponentTarget);

            m_count++;
        }

        //
        return "#" + m_count;
    } //	doIt

    /**
     * Copy view columns from one component to another
     */
    public void copyViewColumns(ViewComponent sourceComponent, ViewComponent targetComponent) {

        ViewColumn[] sourceColumns = sourceComponent.getColumns(true);

        for (ViewColumn sourceColumn : sourceColumns) {
            MViewColumn columnTarget = new MViewColumn(targetComponent);
            PO.copyValues((PO)sourceColumn, columnTarget);
            columnTarget.setViewComponentId(targetComponent.getViewComponentId());
            columnTarget.setEntityType(targetComponent.getEntityType());

            columnTarget.setIsActive(sourceColumn.isActive());
            columnTarget.saveEx();
        }
    }
}
