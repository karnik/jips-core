/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.processing;

import de.karnik.jips.JIPSExceptionHandler;
import de.karnik.jips.JIPSObjectList;
import de.karnik.jips.MsgHandler;
import de.karnik.jips.common.ImageStorage;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.ProjectListener;
import de.karnik.jips.common.processing.JIPSObject;
import de.karnik.jips.common.processing.JIPSProcess;
import de.karnik.jips.common.processing.JIPSProcessListener;
import de.karnik.jips.gui.desk.connector.BaseConnectorUI;
import de.karnik.jips.gui.desk.processing.BaseProcessUI;
import de.karnik.jips.gui.frames.ConfigMask;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ...
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.7
 */
public class BaseProcess implements Runnable, BaseProcessListener, JIPSProcessListener, JIPSObject {

  protected HashMap<String, String> configuration = new HashMap<String, String>();
  private int inputs = 0;
  private int outputs = 0;
  private int processStart = 0;
  private int processEnd = 0;
  private JIPSProcess jipsProcess = null;
  private long startTime = 0;
  private ImageStorage tempImageStorage = null;
  private JIPSObjectList<BaseConnector> connectors = new JIPSObjectList<>();

  private ProjectListener projectListener = null;
  private String processID = null;
  private BaseProcessUI bpUI = null;

  public BaseProcess() {
  }

  public void addBaseConnector(BaseConnector bc) {
    connectors.add(bc);
  }

  public BaseConnector removeBaseConnector(String id) {
    return connectors.removeByID(id);
  }

  public JIPSObjectList<BaseConnector> getBaseConnectors() {
    return connectors;
  }

  public BaseConnector getBaseConnector(String id) {
    return connectors.getByID(id);
  }

  private BaseConnector getBaseConnectorByName(String name, boolean isInput) {

    if (null == name)
      return null;

    for (BaseConnector connector : connectors)
      if (null != connector.getConnectorName() && connector.getConnectorName().equals(name)) {
        if ((isInput && connector.getType() == BaseConnector.INPUT_CONNECTOR)
                || (!isInput && connector.getType() == BaseConnector.OUTPUT_CONNECTOR)) {
          return connector;
        }
      }
    return null;
  }

  public int getInputCount() {
    return this.inputs;
  }

  public void setInputCount(int inputs) {
    this.inputs = inputs;
  }

  public int getOutputCount() {
    return this.outputs;
  }

  public void setOutputCount(int outputs) {
    this.outputs = outputs;
  }

  public void setProjectListener(ProjectListener projectListener) {
    this.projectListener = projectListener;
  }

  public void removeProjectListener() {
    this.projectListener = null;
  }

  public BaseProcessUI getUI() throws JIPSException {
    ArrayList<BaseConnectorUI> inputs = new ArrayList<BaseConnectorUI>();
    ArrayList<BaseConnectorUI> outputs = new ArrayList<BaseConnectorUI>();

    for (BaseConnector connector : connectors) {
      if (connector.getType() == BaseConnector.INPUT_CONNECTOR)
        inputs.add(connector.getUI());

      if (connector.getType() == BaseConnector.OUTPUT_CONNECTOR)
        outputs.add(connector.getUI());
    }

    if (bpUI == null) {
      bpUI = new BaseProcessUI(inputs.size(), outputs.size());

      bpUI.addInputs(inputs);
      bpUI.addOutputs(outputs);

      bpUI.setName(configuration.get("name"));
      bpUI.addBaseProcessListeners(this);
    }

    return bpUI;
  }


  public ImageStorage getInput(String inputName) throws JIPSException {
    tempImageStorage = null;

    BaseConnector bc = getBaseConnectorByName(inputName, true);

    if (projectListener != null && bc != null) {
      projectListener.inputImageRequested(bc.getID(), processID);

    } else {
      return null;
    }

    if (tempImageStorage != null) {
      return tempImageStorage.clone();
    } else {
      return null;
    }

  }

  public void setOutput(String outputName, ImageStorage imageStorage) throws JIPSException {

    tempImageStorage = imageStorage;

    BaseConnector bc = getBaseConnectorByName(outputName, false);

    if (null != projectListener && null != tempImageStorage && null != bc) {
      projectListener.outputImageProcessed(bc.getID(), processID);
    } else {
      // TODO: return info to project listener
    }
  }

  public String getConfiguration(String key) {
    return configuration.get(key);
  }

  public void getConfiguration(String key, StringBuffer value) {
    value.setLength(0);
    value.append(configuration.get(key));
  }

  public void setConfiguration(String key, String value) {
    configuration.put(key, value);
  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.processing.BaseProcessListener#showConfigurationDialog()
   */
  public void showConfigurationDialog() throws JIPSException {
    new ConfigMask("Maske", this);
  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.processing.BaseProcessListener#refreshGUI()
   */
  public void refreshGUI() {
    bpUI.setName(configuration.get("name"));
  }


  /* (non-Javadoc)
   * @see de.karnik.jips.common.processing.BaseProcessListener#startProcess()
   */
  public void startProcess() throws JIPSException {
    startTime = System.nanoTime();

    Thread t = new Thread(this);
    t.start();
  }

  public void run() {

    try {
      // sets the process busy
      if (projectListener != null)
        projectListener.setBusy(this.processID);

      // starts the main-routine
      if (jipsProcess != null)
        jipsProcess.run();

      // disables the ProgressBar
      if (this.getUI().isProgressBarEnabled())
        this.getUI().setProgressBarEnabled(false);

      long estimatedTime = System.nanoTime() - startTime;
      MsgHandler.executionTimeConsoleMSG(configuration.get("name"),
              estimatedTime,
              MsgHandler.SECONDS, true);

    } catch (JIPSException je) {
      JIPSExceptionHandler.handleException(je);
    }
  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.processing.JIPSProcessListener#initProgressBar(int, int)
   */
  public void initProgressBar(int start, int end) throws JIPSException {
    this.processStart = start;
    this.processEnd = end;

    if (!this.getUI().isProgressBarEnabled())
      this.getUI().setProgressBarEnabled(true);

  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.processing.JIPSProcessListener#setProgressBar(int)
   */
  public void setProgressBar(int value) throws JIPSException {

    if (value >= this.processStart && value <= this.processEnd) {
      this.getUI().setPercent((float) value / (float) this.processEnd);
    } else {
      this.getUI().setPercent(0.0f);
    }
  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.processing.JIPSObject#getID()
   */
  public String getID() {
    return processID;
  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.processing.JIPSObject#setID(java.lang.String)
   */
  public void setID(String id) {
    this.processID = id;
  }

  public JIPSProcess getJipsProcess() {
    return jipsProcess;
  }

  public void setJipsProcess(JIPSProcess jipsProcess) {
    this.jipsProcess = jipsProcess;
    this.jipsProcess.setJipsProcessListener(this);
  }

  public boolean isReady() {
    return true;
  }

  public ImageStorage getTempImageStorage() {
    return tempImageStorage;
  }

  public void setTempImageStorage(ImageStorage image) {
    this.tempImageStorage = image;
  }

}
