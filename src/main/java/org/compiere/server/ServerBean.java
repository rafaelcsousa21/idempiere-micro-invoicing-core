package org.compiere.server;

import org.compiere.model.IProcessInfo;
import org.compiere.model.Server;
import org.compiere.process.ProcessUtil;
import org.idempiere.common.util.CLogger;

import java.util.Properties;

/**
 * Adempiere Server Bean.
 *
 * @author Jorg Janke
 * @author Low Heng Sin
 * - Added remote transaction management
 * - Added support to run db process remotely on server
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL - BF [ 1757523 ]
 * @version $Id: ServerBean.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class ServerBean implements Server {
    //

    /*************************************************************************
     *  Process Remote
     *
     *  @param ctx Context
     *  @param pi Process Info
     *  @return resulting Process Info
     */
    public IProcessInfo process(Properties ctx, IProcessInfo pi) {
        //	Start Process
        ProcessUtil.startJavaProcess(ctx, pi);
        return pi;
    }    //	process

}    //	ServerBean
