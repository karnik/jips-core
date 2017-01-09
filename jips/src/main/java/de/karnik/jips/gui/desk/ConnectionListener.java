/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.gui.desk;

import de.karnik.jips.common.JIPSException;

public interface ConnectionListener {
  public void createNewConnection() throws JIPSException;

  public void removeNewConnection() throws JIPSException;
}