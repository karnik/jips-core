/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.common;

public interface ProjectListener {
  void inputImageRequested(String connectorID, String processID) throws JIPSException;
  void outputImageProcessed(String connectorID, String processID) throws JIPSException;
  void setBusy(String processID);
  void connectorClicked(String connectorID) throws JIPSException;
  void connectorStatusChanged(int status, String connectorID);
  void projectDataEntered() throws JIPSException;
}
