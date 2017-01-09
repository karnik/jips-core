/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.plugin.add;

import de.karnik.jips.common.ImageStorage;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.processing.JIPSProcess;

public class Add extends JIPSProcess {

  public static final String INPUT_0 = "in0";
  public static final String INPUT_1 = "in1";
  public static final String OUTPUT_0 = "out0";

  public void run() throws JIPSException {

    ImageStorage is1 = this.getInput(INPUT_0);
    ImageStorage is2 = this.getInput(INPUT_1);

    int w1 = is1.getWidth();
    int h1 = is1.getHeight();

    int w = is2.getWidth();
    int h = is2.getHeight();

    if (w1 < w) w = w1;
    if (h1 < h) h = h1;

    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        int tempRGB = setAlpha(0.4f, is1.getRGB(x, y));
        is2.setRGB(x, y, combine(is2.getRGB(x, y), tempRGB));
      }
    }
    this.setOutput(OUTPUT_0, is2);
  }

  private int combine(int one, int second) {

    int newOne = one & second;

    return newOne;
  }

  private int setAlpha(float alpha, int rgb) {

    // alpha holen
    int tempAlpha = (rgb >> 24) & 0xff;
    // alpha 0 setzen
    rgb = rgb & 0x00ffffff;
    // neuen alpha wert setzen
    rgb = rgb | (((int) (tempAlpha * alpha) & 0xff) << 24);

    return rgb;
  }
}
