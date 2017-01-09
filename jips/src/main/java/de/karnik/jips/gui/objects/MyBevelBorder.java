/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.gui.objects;

import javax.swing.border.BevelBorder;
import java.awt.*;

public class MyBevelBorder extends BevelBorder {

  private static final long serialVersionUID = 6522069262980925469L;

  public MyBevelBorder(int bevelType ) {
    super( bevelType );
  }

  protected void paintRaisedBevel(Component c, Graphics g, int x, int y,
                                  int width, int height)  {
    Color oldColor = g.getColor();
    int h = height;
    int w = width;

    g.translate(x, y);

    g.setColor(getHighlightOuterColor(c));
    g.drawLine(0, 0, 0, h-1);
    g.drawLine(1, 0, w-1, 0);

    g.setColor(getShadowOuterColor(c));
    g.drawLine(0, h-1, w-1, h-1);
    g.drawLine(w-1, 0, w-1, h-2);

    g.translate(-x, -y);
    g.setColor(oldColor);

  }

  protected void paintLoweredBevel(Component c, Graphics g, int x, int y,
                                   int width, int height)  {
    Color oldColor = g.getColor();
    int h = height;
    int w = width;

    g.translate(x, y);


    g.setColor(getShadowOuterColor(c));
    g.drawLine(1, 1, 1, h-2);
    g.drawLine(2, 1, w-2, 1);

    g.setColor(getHighlightOuterColor(c));
    g.drawLine(1, h-1, w-1, h-1);
    g.drawLine(w-1, 1, w-1, h-2);


    g.translate(-x, -y);
    g.setColor(oldColor);

  }
}
