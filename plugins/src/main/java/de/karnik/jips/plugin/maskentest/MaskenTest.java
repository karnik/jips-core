/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.plugin.maskentest;

import de.karnik.jips.common.ImageStorage;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.processing.JIPSProcess;

public class MaskenTest extends JIPSProcess {

  public static final String INPUT_0 = "in0";
  public static final String OUTPUT_0 = "out0";

  @Override
  public void run() throws JIPSException {

    int[][] maske = {
            {Integer.parseInt(getConfiguration("11")),
                    Integer.parseInt(getConfiguration("12")),
                    Integer.parseInt(getConfiguration("13"))
            },
            {Integer.parseInt(getConfiguration("21")),
                    Integer.parseInt(getConfiguration("22")),
                    Integer.parseInt(getConfiguration("23"))
            },
            {Integer.parseInt(getConfiguration("31")),
                    Integer.parseInt(getConfiguration("32")),
                    Integer.parseInt(getConfiguration("33"))
            }
    };


    ImageStorage is1 = this.getInput(INPUT_0);
    ImageStorage is2 = is1.clone();


    for (int y = 1; y <= is1.getHeight() - 2; y++) {
      for (int x = 1; x <= is1.getWidth() - 2; x++) {

        int summe = 0;
        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            //System.out.println( x+j );
            //System.out.println( y+i );
            int red = getRed(is2.getRGB(x + j, y + i));
            summe += (red * maske[1 + j][1 + i]);
          }
        }

        is1.setRGB(x, y, getNewRGB(is1.getRGB(x, y), summe));

      }
    }

    this.setOutput(OUTPUT_0, is2);
  }

  private int getNewRGB(int rgb, int newVal) {

    if (newVal > 255) newVal = 255;
    if (newVal < 0) newVal = 0;

    return (rgb & 0xff000000) |
            ((newVal & 0xff) << 16) |
            ((newVal & 0xff) << 8) |
            ((newVal & 0xff));

  }

  private int getRed(int rgb) {
    return (rgb >> 16) & 0xff;
  }

}
