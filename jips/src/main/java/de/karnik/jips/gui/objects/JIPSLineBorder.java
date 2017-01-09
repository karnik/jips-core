/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.objects;

import javax.swing.border.EmptyBorder;
import java.awt.*;

public class JIPSLineBorder extends EmptyBorder {

  /**
   *
   */
  private static final long serialVersionUID = 302449154901407477L;
  private Color color = Color.WHITE;

  public JIPSLineBorder(Color c, Insets borderInsets ) {
    super( borderInsets );
    this.color = c;
  }

  public JIPSLineBorder(Color c, int top, int left, int bottom, int right ) {
    super( top, left, bottom, right );
    this.color = c;
  }

  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height ) {
    Color oldColor = g.getColor();
    g.setColor( color );

    if( bottom > 0 ) {
      for(int i = 0; i < bottom; i++ )  {
        g.drawLine( x, y+height-i-1,x+width, y+height-i-1 );
      }
    }

    if( left > 0 ) {

    }

    if( right > 0 ) {

    }

    if( top > 0 ) {

    }
    g.setColor( oldColor );
  }

  public boolean isBorderOpaque() { return true; }
}
