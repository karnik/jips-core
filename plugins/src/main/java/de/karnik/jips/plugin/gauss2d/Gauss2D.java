/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.plugin.gauss2d;

import de.karnik.jips.common.ImageStorage;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.processing.JIPSProcess;

public class Gauss2D extends JIPSProcess {

  public static final String INPUT_0 = "in0";
  public static final String OUTPUT_0 = "out0";
  // n muss ein ungerader int-Wert sein
  // ( (n << img.width) && (n << img.lenght))
  private static int n = 7, nh = n / 2;
  private ImageStorage is0 = null;
  private ImageStorage is1 = null;
  private float[][] kernel = new float[n][n];
  private float sum_kernel = 0.0f;

  private void calcGaussKernel() {
    double dist = 0;
    double a = 1.0f;
    if (n > 3) {
      a = -2 * nh * nh / Math.log(0.01);
    }

    for (int y = 0; y < n; y++) {
      for (int x = 0; x < n; x++) {
        dist = Math.sqrt((x - nh) * (x - nh) + (y - nh) * (y - nh));
        sum_kernel += kernel[y][x] = (float) (Math.exp(-dist * dist / a));
      }
    }
  }

  @Override
  public void run() throws JIPSException {
    is0 = this.getInput(INPUT_0);
    is1 = is0.clone();

    int rF, gF, bF;
    float sumR, sumG, sumB, weight;

    calcGaussKernel();

    try {

      for (int y = nh; y < is0.getHeight() - nh; y++) {
        for (int x = nh; x < is0.getWidth() - nh; x++) {
          sumR = sumG = sumB = 0.0f;
          for (int yy = -nh; yy <= nh; yy++) {
            for (int xx = -nh; xx <= nh; xx++) {
              weight = kernel[yy + nh][xx + nh];

              int rgb = is0.getRGB((int) (x + xx), (int) (y + yy));
              sumR += weight * ((rgb >> 16) & 0xff);
              sumG += weight * ((rgb >> 8) & 0xff);
              sumB += weight * (rgb & 0xff);
            }
          }

          rF = (int) (sumR / sum_kernel);
          gF = (int) (sumG / sum_kernel);
          bF = (int) (sumB / sum_kernel);

          is1.setRGB(x, y,
                  (int) (((rF & 0xff) << 16)
                          | ((gF & 0xff) << 8)
                          | (bF & 0xff))
          );
        }
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
      return;

    }
    this.setOutput(OUTPUT_0, is1);
    //border_painting();
  }

}
