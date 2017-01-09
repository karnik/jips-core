/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.processing;

import de.karnik.jips.common.JIPSException;

public interface BaseProcessListener {

  public void showConfigurationDialog() throws JIPSException;

  public void startProcess() throws JIPSException;

  public void refreshGUI();

}
