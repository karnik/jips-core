/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */
package de.karnik.jips.gui.splash;

import de.karnik.jips.IO;
import de.karnik.jips.common.JIPSException;

import java.awt.*;

/**
 * The JIPSSplashScreen class contains class fields and methods for the splash screen.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.6
 */
public class JIPSSplashScreen {

  private SplashScreen splashScreen;
  private Image barImage = null;
  private String leftText = "";
  private String rightText = "";
  private int vspace = 25;
  private int hspace = 5;
  private int maxSteps = 0;
  private int stepCounter = 0;

  public JIPSSplashScreen() throws JIPSException {
    this(5);
  }

  public JIPSSplashScreen(int maxSteps) throws JIPSException {
    this.maxSteps = maxSteps;
    barImage = IO.getImage("statusbar.png");
    splashScreen = SplashScreen.getSplashScreen();
  }

  private void drawText() {
    if (splashScreen != null) {
      // create graphics and get values
      Dimension size = splashScreen.getSize();
      Graphics2D g = splashScreen.createGraphics();
      int textWidth = g.getFontMetrics().stringWidth(rightText);

      // clear
      g.setComposite(AlphaComposite.Clear);
      g.fillRect(0, 0, size.width, size.height);
      g.setComposite(AlphaComposite.DstAtop);

      // draw text
      g.setColor(Color.BLACK);
      g.drawString(leftText, hspace, size.height - vspace);
      g.drawString(rightText, size.width - textWidth - hspace, size.height - vspace);

      drawStatusBar(g, size);

      splashScreen.update();
      try {
        //Thread.sleep( 400 );
      } catch (Exception e) {

      }
    }
  }

  private void drawStatusBar(Graphics2D g, Dimension size) {

    int stepSize = (int) (size.width / (maxSteps - 1));
    int newWidth = stepSize * stepCounter;

    if (stepCounter == maxSteps - 1)
      newWidth = size.width - 1;

    g.drawImage(barImage, 1, size.height - 20, newWidth, size.height - 1,
            0, 0, newWidth, 20, null);

    stepCounter++;
  }

  public void setText(String leftText, String rightText) {

    if (leftText != null)
      this.leftText = leftText;

    if (rightText != null)
      this.rightText = rightText;

    drawText();
  }

  public void setLeftText(String text) {
    setText(text, null);
  }

  public void setRightText(String text) {
    setText(null, text);
  }
}
