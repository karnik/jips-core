/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.objects;

import de.karnik.jips.common.JIPSException;

import javax.swing.*;

public class MemStatusThread extends SwingWorker<Void, Void> {

  public MemStatusThread() {}

  @Override
  public Void doInBackground() throws JIPSException {
    while ( true ) {
      StatusBar.refreshMem();
      try {
        Thread.sleep( 1000 );
      } catch( InterruptedException  ie ) {
        throw new JIPSException( "0028", ie, false );
      }
    }
  }
}