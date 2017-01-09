/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.plugin.dilatation;

import de.karnik.jips.common.ImageStorage;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.processing.JIPSProcess;

public class Dilatation extends JIPSProcess {

  public static final String INPUT_0 = "in0";
  //private int whitePixel = 0xFFFFFFFF;
  public static final String OUTPUT_0 = "out0";
  private int blackPixel = 0xFF000000;

  @Override
  public void run() throws JIPSException {

    boolean[][] dilate = {{true, true, true},
            {true, true, true},
            {true, true, true}};

    ImageStorage is1 = dilation(this.getInput(INPUT_0), dilate);
    this.setOutput(OUTPUT_0, is1);
  }

  private ImageStorage dilation(ImageStorage is, boolean[][] dilate) {

    ImageStorage is1 = is.clone();

    boolean dilation = false;

    for (int y = 1; y < is.getHeight() - 1; y++) {
      for (int x = 1; x < is.getWidth() - 1; x++) {
        dilation = false;
        for (int yy = -1; yy <= 1; yy++) {
          for (int xx = -1; xx <= 1; xx++) {
            if (dilate[xx + 1][yy + 1] && (is.getRGB(x + xx, y + yy) == blackPixel)) {
              dilation = true;
            }
          }
        }
        if (dilation) is1.setRGB(x, y, blackPixel);

      }
    }

    return is1;

  }

}
