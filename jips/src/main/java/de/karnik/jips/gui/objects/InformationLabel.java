/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.objects;

import javax.swing.*;
import java.awt.*;

public class InformationLabel extends JLabel {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public void setData(Icon icon, String text ) {
    this.setIcon( icon );
    this.setText( text );
    this.setFont( this.getFont().deriveFont( Font.ITALIC | Font.BOLD ) );

  }

  public void clearData() {
    this.setIcon( null );
    this.setText( "" );
  }

}
