/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.plugin.colorchanger;

import de.karnik.jips.common.ImageStorage;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.processing.JIPSProcess;

public class ColorChanger extends JIPSProcess {

  public static final String INPUT_0 = "in0";
  public static final String OUTPUT_0 = "out0";
  private static final int SWAP_RED_BLUE = 0;
  private static final int SWAP_GREEN_BLUE = 1;
  private static final int SWAP_RED_GREEN = 2;
  private int type = SWAP_RED_BLUE;

  public ColorChanger() {
    super();
  }

  public void run() throws JIPSException {

    if (this.getConfiguration("swapRB").equals("true")) {
      type = SWAP_RED_BLUE;
    } else if (this.getConfiguration("swapGB").equals("true")) {
      type = SWAP_GREEN_BLUE;
    } else if (this.getConfiguration("swapRG").equals("true")) {
      type = SWAP_RED_GREEN;
    } else {
      type = SWAP_RED_BLUE;
    }

    ImageStorage imageStorage = this.getInput(INPUT_0);

    System.out.println(imageStorage.getType());

    int w = imageStorage.getWidth();
    int h = imageStorage.getHeight();

    for (int y = 0; y < h; y++)
      for (int x = 0; x < w; x++)
        imageStorage.setRGB(x, y, swapColor(imageStorage.getRGB(x, y)));

    this.setOutput(OUTPUT_0, imageStorage);

  }

  /**
   * Swap colors!
   *
   * @param rgb color-vector to swap.
   * @return The rotated color-vector.
   */
  private int swapColor(int rgb) {
    switch (type) {
      case SWAP_RED_BLUE:
        return (rgb & 0xff00ff00)
                | ((rgb & 0xff0000) >> 16)
                | ((rgb & 0xff) << 16);
      case SWAP_GREEN_BLUE:
        return (rgb & 0xffff0000)
                | ((rgb & 0xff00) >> 8)
                | ((rgb & 0xff) << 8);
      case SWAP_RED_GREEN:
        return (rgb & 0xff0000ff)
                | ((rgb & 0xff0000) >> 8)
                | ((rgb & 0xff00) << 8);
    }
    return 0;
  }

}