/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.processing.BaseConnection;
import de.karnik.jips.processing.BaseConnector;
import de.karnik.jips.processing.BaseProcess;

public class JIPSProjectDataModel {

  /**
   * The internal project id.
   */
  private String projectID;

  /**
   * The name of the project.
   */
  private String projectName;

  /**
   * The description of the project.
   */
  private String projectDescription;

  private JIPSObjectList<BaseProcess> processes = new JIPSObjectList<BaseProcess>();
  private JIPSObjectList<BaseConnection> connections = new JIPSObjectList<BaseConnection>();

  public void addBaseConnection(BaseConnection bc) {
    connections.add(bc);
  }

  public BaseConnection removeBaseConnection(String id) {
    return connections.removeByID(id);
  }

  public BaseConnection getBaseConnection(String id) {
    return connections.getByID(id);
  }

  public BaseConnection getBaseConnection(int id) {
    return connections.get(id);
  }

  public JIPSObjectList<BaseConnection> getSelectedConnections() throws JIPSException {
    JIPSObjectList<BaseConnection> selected = new JIPSObjectList<BaseConnection>();

    for (int i = 0; i < connections.size(); i++) {
      if (connections.get(i).getUI().isSelected())
        selected.add(connections.get(i));
    }

    return selected;
  }

  public BaseConnector getBaseConnector(String connectorID) {
    for (int i = 0; i < processes.size(); i++) {
      BaseConnector bc = processes.get(i).getBaseConnector(connectorID);
      if (bc != null) return bc;
    }

    return null;
  }

  public JIPSObjectList<BaseConnector> getConnectedConnectors(String connectorID) {

    JIPSObjectList<BaseConnector> bc = new JIPSObjectList<BaseConnector>();

    for (int i = 0; i < connections.size(); i++) {
      String input = connections.get(i).getBaseInputConnectorID();
      String output = connections.get(i).getBaseOutputConnectorID();

      if (input.equals(connectorID))
        bc.add(getBaseConnector(output));

      if (output.equals(connectorID))
        bc.add(getBaseConnector(input));
    }

    return bc;
  }

  public BaseConnector getConnectedOutputConnector(String inputConnectorID) {

    String connectorID = null;

    for (BaseConnection connection : connections)
      if (connection.getBaseInputConnectorID().equals(inputConnectorID))
        connectorID = connection.getBaseOutputConnectorID();

    if (connectorID != null) {
      for (BaseProcess process : processes) {
        BaseConnector tempBc = process.getBaseConnector(connectorID);
        if (null != tempBc) {
          return tempBc;
        }
      }
    }

    return null;
  }

  public void addBaseProcess(BaseProcess bp) {
    processes.add(bp);
  }

  public BaseProcess removeBaseProcess(String id) {
    return processes.removeByID(id);
  }

  public BaseProcess getBaseProcess(String id) {
    return processes.getByID(id);
  }

  public BaseProcess getBaseProcess(int id) {
    return processes.get(id);
  }

  public JIPSObjectList<BaseProcess> getSelectedProcesses() throws JIPSException {
    JIPSObjectList<BaseProcess> selected = new JIPSObjectList<BaseProcess>();

    for (int i = 0; i < processes.size(); i++) {
      if (processes.get(i).getUI().isSelected())
        selected.add(processes.get(i));
    }

    return selected;
  }

  public int getBaseProcessCount() {
    return processes.size();
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getProjectDescription() {
    return projectDescription;
  }

  public void setProjectDescription(String projectDescription) {
    this.projectDescription = projectDescription;
  }

  public String getProjectID() {
    return projectID;
  }

  public void setProjectID(String projectID) {
    this.projectID = projectID;
  }
}
