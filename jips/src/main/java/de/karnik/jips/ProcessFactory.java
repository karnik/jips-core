/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */
package de.karnik.jips;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.ProjectListener;
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.common.plugin.Plugin;
import de.karnik.jips.common.processing.JIPSProcess;
import de.karnik.jips.processing.BaseConnector;
import de.karnik.jips.processing.BaseProcess;

/**
 * ...
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.7
 */
public abstract class ProcessFactory {


  public static BaseProcess getProcess(String processName, ProjectListener projectListener) throws JIPSException {

    BaseProcess bp = null;
    JIPSProcess jipsProcess;
    PluginStorage ps = PluginStorage.getInstance();
    Plugin plug = ps.getPlugin(processName);
    JIPSVariables vars = JIPSVariables.getInstance();

    if (plug != null) {
      try {

        // TODO: Refactor, uses plugin directly instead of internal configuration
        bp = new BaseProcess();
        bp.setProjectListener(projectListener);

        bp.setID(CommonFunctions.getUUID());
        if (vars.debugMode) MsgHandler.debugMSG("Creating new Process. ID: " + bp.getID(), true);

        jipsProcess = (JIPSProcess) plug.getPluginMainClass().newInstance();

        if (jipsProcess != null)
          bp.setJipsProcess(jipsProcess);

        bp.setConfiguration("plugin_name", processName);
        bp.setConfiguration("name", plug.getPluginName());

        if (null != plug.getPluginProviderName() && !plug.getPluginProviderName().equals(""))
          bp.setConfiguration("provider-name", plug.getPluginProviderName());
        else
          bp.setConfiguration("provider-name", "-");

        if (null != plug.getPluginUrl() && !plug.getPluginUrl().equals(""))
          bp.setConfiguration("url", plug.getPluginUrl());
        else
          bp.setConfiguration("url", "-");

        if (null != plug.getPluginContact() && !plug.getPluginContact().equals(""))
          bp.setConfiguration("contact", plug.getPluginContact());
        else
          bp.setConfiguration("contact", "-");

        if (null != plug.getPluginVersion() && !plug.getPluginVersion().equals(""))
          bp.setConfiguration("version", plug.getPluginVersion());
        else
          bp.setConfiguration("version", "-");

        bp.setConfiguration("configFile",
                vars.getPluginsDir()
                        + "/" + plug.getPluginDir()
                        + "/maskdef.xml");

        bp.setInputCount(plug.getPluginInputCount());
        bp.setOutputCount(plug.getPluginOutputCount());

        // add inputs
        for (String inputName : plug.getPluginInputNames()) {

          BaseConnector bc = new BaseConnector();
          bc.setType(BaseConnector.INPUT_CONNECTOR);
          bc.setID(CommonFunctions.getUUID());
          bc.setConnectorName(inputName);
          bc.setProjectListener(projectListener);

          bp.addBaseConnector(bc);
          if (vars.debugMode) MsgHandler.debugMSG("New InputConnector added. ID: " + bc.getID(), true);
        }

        // add outputs
        for (String outputName : plug.getPluginOutputNames()) {

          BaseConnector bc = new BaseConnector();
          bc.setType(BaseConnector.OUTPUT_CONNECTOR);
          bc.setID(CommonFunctions.getUUID());
          bc.setConnectorName(outputName);
          bc.setProjectListener(projectListener);

          bp.addBaseConnector(bc);
          if (vars.debugMode) MsgHandler.debugMSG("New OutputConnector added. ID: " + bc.getID(), true);
        }


      } catch (IllegalAccessException iae) {
        // TODO: add code?
        throw new JIPSException("", iae, true);
      } catch (InstantiationException ie) {
        // TODO: add code?
        throw new JIPSException("", ie, true);
      } catch (ClassCastException cce) {
        // TODO: add code?
        throw new JIPSException("", cce, true);
      }
    }
    return bp;
  }

}
