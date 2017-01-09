/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.JIPSMessage;
import de.karnik.jips.common.ProjectListener;
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.common.lang.Translator;
import de.karnik.jips.common.processing.JIPSObject;
import de.karnik.jips.gui.MainFrame;
import de.karnik.jips.gui.desk.MainDesktop;
import de.karnik.jips.gui.frames.NewProjectConfigurationFrame;
import de.karnik.jips.gui.menu.BaseMenuBar;
import de.karnik.jips.processing.BaseConnection;
import de.karnik.jips.processing.BaseConnector;
import de.karnik.jips.processing.BaseProcess;

import java.awt.*;


/**
 * The JIPSProject class contains class fields and methods for JIPS projects.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.3
 */
public class JIPSProject implements ProjectListener, ProjectUIListener, JIPSObject {


  /**
   * Sets the saved status. If <strong>true</strong> the project has been saved.
   */
  public boolean saved = false;
  /**
   * The main desktop object.
   */
  private MainDesktop mainDesktop;
  /**
   * DataModel to handle Project-Properties.
   */
  private JIPSProjectDataModel jpdm = null;
  private NewProjectConfigurationFrame npcf = null;
  /**
   * The object to hold the JIPS translation.
   */
  private Translator trans = null;
  private JIPSVariables vars = null;

  /**
   * This class is uninstantiable with the standard constructor.
   */
  private JIPSProject() throws JIPSException {
    vars = JIPSVariables.getInstance();
    trans = Translator.getInstance();
  }

  /**
   * Constructs a new jips project with a specified internal project id.
   *
   * @param projectID
   * @param projectName
   * @throws JIPSException
   */
  public JIPSProject(String projectID, String projectName) throws JIPSException {
    this();

    jpdm = new JIPSProjectDataModel();
    jpdm.setProjectID(projectID);
    jpdm.setProjectName(projectName);

    npcf = new NewProjectConfigurationFrame(jpdm);
    npcf.setProjectListener(this);

  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.ProjectListener#setJIPSProjectDataModel(de.karnik.jips.common.JIPSProjectDataModel)
   */
  public void projectDataEntered() throws JIPSException {

    if (mainDesktop == null) {
      mainDesktop = new MainDesktop();
      mainDesktop.addProjectUIListener(this);
      mainDesktop.setEnabled(true);

      MainFrame.getInstance().createNewInternalFrame(this);

      BaseMenuBar bmb = MainFrame.getInstance().getRunMenuBar();

      if (bmb != null) {
        if (bmb.getMenuBarItem("project_run") != null)
          bmb.getMenuBarItem("project_run").setEnabled(true);
        if (bmb.getMenuBarItem("project_pause") != null)
          bmb.getMenuBarItem("project_pause").setEnabled(false);
        if (bmb.getMenuBarItem("project_stop") != null)
          bmb.getMenuBarItem("project_stop").setEnabled(false);
      }

      MsgHandler.consoleMSG(
              new JIPSMessage(
                      JIPSMessage.INFORMATION, jpdm.getProjectName() + " " + trans.getTranslation("created"), ""),
              false);

      addInitialProcesses();
    }
  }

  private void addInitialProcesses() throws JIPSException {
    if (mainDesktop != null && npcf != null) {

      for (int i = 0; i < npcf.getInitialInputCount(); i++)
        this.addProcess("Input", new Point(20, i * 120 + 10));

      for (int i = 0; i < npcf.getInitialOutputCount(); i++)
        this.addProcess("Output", new Point(220, i * 120 + 10));

      for (int i = 0; i < npcf.getInitialViewerCount(); i++)
        this.addProcess("JIPS Viewer", new Point(420, i * 120 + 10));

    }
  }

  public void runProcesses() throws JIPSException {
      /*
      int connects = 0;
    	for( int i = 0; i < connectors.size(); i++ ) {
    		if( connectors.get( i ).isConnected() )
    			connects++;
    	}
    	
    	if( connects == connectors.size() ) {

    		for( int i = 0; i < processes.size(); i++ ) {
    			BaseProcess bp = processes.get( i );
    			if( bp.isReady() )
    				bp.run();
    		}
    	} else {
            MsgHandler.consoleMSG( 
            		new JIPSMessage( 
            				JIPSMessage.ERROR, "Connect all connectors..." , ""), 
            				false );
    	}*/

  }

  public void pauseProcesses() {

  }

  public void stopProcesses() {

  }

  public void saveProject() throws JIPSException {
  }

  /**
   * Returns the mainDesktop object.
   *
   * @return the main desktop
   */
  public MainDesktop getMainDesktop() {
    return mainDesktop;
  }

  public BaseProcess getProcess(String processID) throws JIPSException {
    return jpdm.getBaseProcess(processID);
  }


  public void addConnection(BaseConnector con1, BaseConnector con2) throws JIPSException {

    BaseConnection c = null;

    if (con1.getType() == BaseConnector.INPUT_CONNECTOR
            && con2.getType() == BaseConnector.OUTPUT_CONNECTOR) {

      c = new BaseConnection(con2, con1);
    }

    if (con1.getType() == BaseConnector.OUTPUT_CONNECTOR
            && con2.getType() == BaseConnector.INPUT_CONNECTOR) {
      c = new BaseConnection(con1, con2);
    }


    c.setID(CommonFunctions.getUUID());
    c.getUI().addProjectUIListeners(this);
    jpdm.addBaseConnection(c);

    mainDesktop.addConnection(c.getUI());
    mainDesktop.repaint();

    if (vars.debugMode) {
      MsgHandler.debugMSG("New Connection added. ID: " + c.getID(), true);
      MsgHandler.debugMSG("   From Input: " + c.getBaseInputConnectorID(), true);
      MsgHandler.debugMSG("   To  Output: " + c.getBaseOutputConnectorID(), true);
    }

  }

  public int addProcess(String processName, Point p) throws JIPSException {

    int count = -1;

    BaseProcess baseProcess = ProcessFactory.getProcess(processName, this);

    if (baseProcess != null) {

      baseProcess.getUI().addProjectUIListeners(this);
      mainDesktop.addProcess(baseProcess.getUI());
      baseProcess.getUI().setLocation(p);

      jpdm.addBaseProcess(baseProcess);
      count = jpdm.getBaseProcessCount();

      mainDesktop.repaint();

      if (vars.debugMode) MsgHandler.debugMSG("New Process added. ID: " + baseProcess.getID(), true);
    }

    return count;
  }

  public void removeProcess(String processID) throws JIPSException {
    BaseProcess bp = jpdm.getBaseProcess(processID);

    if (bp != null)
      mainDesktop.removeProcess(bp.getUI());

    jpdm.removeBaseProcess(processID);
  }

  public void removeProcess(BaseProcess baseProcess) throws JIPSException {
    removeProcess(baseProcess.getID());
  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.ProjectUIListener#setSelected()
   */
  public void setSelected() throws JIPSException {
    mainDesktop.setSelectedProcesses(jpdm.getSelectedProcesses());
  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.ProjectUIListener#deselectAll()
   */
  public void deselectAll() throws JIPSException {
    mainDesktop.setSelectedProcesses(new JIPSObjectList<BaseProcess>());

    for (int i = 0; i < jpdm.getBaseProcessCount(); i++)
      jpdm.getBaseProcess(i).getUI().setSelected(false);
  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.ProjectUIListener#setProcess(java.lang.String)
   */
  public void setProcess(String processID) throws JIPSException {
    mainDesktop.setBaseProcess(getProcess(processID).getUI());

  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.ProjectListener#inputImageRequested(java.lang.String, java.lang.String)
   */
  public void inputImageRequested(String connectorID, String processID) throws JIPSException {

    if (vars.debugMode)
      MsgHandler.debugMSG("getInputImage -> connectorID: "
              + connectorID + ", processID: "
              + processID, true);

    if (connectorID != null) {
      BaseProcess bp = jpdm.getBaseProcess(processID);

      BaseConnector output = jpdm.getConnectedOutputConnector(connectorID);

      if (null == output) {
        throw new JIPSException("0031", new Exception("No output connector available."), false);
      }

      bp.setTempImageStorage(output.getImageStorage());
    }
  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.ProjectListener#outputImageProcessed(java.lang.String, java.lang.String)
   */
  public void outputImageProcessed(String connectorID, String processID) throws JIPSException {

    BaseProcess bp = jpdm.getBaseProcess(processID);

    if (vars.debugMode) {
      MsgHandler.debugMSG("setOuputImage -> connectorID: " + connectorID + ", processID: " + processID, true);
      MsgHandler.debugMSG("                 image: " + bp.getTempImageStorage().toString(), true);
    }

    BaseConnector bc = bp.getBaseConnector(connectorID);
    bc.setImageStorage(bp.getTempImageStorage());
    bc.setStatus(BaseConnector.READY);
  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.ProjectListener#connectorClicked(java.lang.String)
   */
  public void connectorClicked(String connectorID) throws JIPSException {
    if (vars.debugMode)
      MsgHandler.debugMSG("connectorClicked -> connectorID: " + connectorID, true);


    BaseConnector bc1 = jpdm.getBaseConnector(connectorID);
    if (bc1 != null) {
      if (bc1.getType() == BaseConnector.OUTPUT_CONNECTOR || !bc1.isConnected()) {

        BaseConnector bc2 = mainDesktop.getSelectedConnector();

        if ((bc2 != null) && (bc2 != bc1) && (bc2.getType() != bc1.getType())) {

          addConnection(mainDesktop.getSelectedConnector(), bc1);

          mainDesktop.getSelectedConnector().setConnected(true);
          bc1.setConnected(true);

          mainDesktop.setSelectedConnector(null);
          mainDesktop.repaint();

        } else {
          mainDesktop.setSelectedConnector(bc1);
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.ProjectListener#connectorStatusChanged(java.lang.String)
   */
  public void connectorStatusChanged(int status, String connectorID) {

    JIPSObjectList<BaseConnector> connectors = jpdm.getConnectedConnectors(connectorID);

    for (int i = 0; i < connectors.size(); i++)
      connectors.get(i).setStatus(status);
  }

  /**
   * Returns the internal project id.
   *
   * @return the project id
   */
  public String getID() {
    return jpdm.getProjectID();
  }

  /**
   * Sets the internal project id.
   *
   * @param projectID the project id
   */
  public void setID(String projectID) {
    jpdm.setProjectID(projectID);
  }

  public JIPSProjectDataModel getJIPSProjectDataModel() {
    if (jpdm != null)
      return jpdm;

    return null;
  }

  public void setBusy(String processID) {

    JIPSObjectList<BaseConnector> connectors = null;
    BaseProcess bc = jpdm.getBaseProcess(processID);

    if (bc != null)
      connectors = bc.getBaseConnectors();

    for (int i = 0; i < connectors.size(); i++) {
      connectors.get(i).setStatus(BaseConnector.BUSY);
    }
  }

  /**
   * Removes a connection from the connections vector.
   *
   * @param connectionID The connectionID to remove.
   */
  private void removeConnection(String connectionID) throws JIPSException {

    BaseConnection bc = jpdm.getBaseConnection(connectionID);

    if (bc != null) {
      BaseConnector bcIn = jpdm.getBaseConnector(bc.getBaseInputConnectorID());
      BaseConnector bcOut = jpdm.getBaseConnector(bc.getBaseOutputConnectorID());

      if (bcIn != null && bcOut != null) {
        bcIn.setConnected(false);
        bcOut.setConnected(false);
      }

      mainDesktop.remove(bc.getUI());
      jpdm.removeBaseConnection(connectionID);
    }
  }

  public void deleteSelectedProcesses() throws JIPSException {

    JIPSObjectList<BaseProcess> processesToRemove = jpdm.getSelectedProcesses();
    JIPSObjectList<BaseConnection> connectionsToRemove = jpdm.getSelectedConnections();

    // remove connections asn processes
    for (BaseConnection aConnectionsToRemove : connectionsToRemove) removeConnection(aConnectionsToRemove.getID());
    for (BaseProcess aProcessesToRemove : processesToRemove) removeProcess(aProcessesToRemove);

    connectionsToRemove = null;
    processesToRemove = null;

    mainDesktop.setSelectedProcesses(new JIPSObjectList<BaseProcess>());
    mainDesktop.repaint();
    System.gc();
  }
}
