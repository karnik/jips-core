/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.plugin.gauss1d;

import de.karnik.jips.common.ImageStorage;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.processing.JIPSProcess;

import java.awt.image.Kernel;

public class Gauss1D extends JIPSProcess {

  public static final String INPUT_0 = "in0";
  public static final String OUTPUT_0 = "out0";
  static final long serialVersionUID = 5377089073023183684L;
  protected float radius;
  protected Kernel kernel;
  private ImageStorage is0 = null;
  private ImageStorage is1 = null;

  public static void convolve(Kernel kernel,
                              int[] inPixels, int[] outPixels,
                              int width, int height) {

    float[] matrix = kernel.getKernelData(null);
    int cols = kernel.getWidth();
    int cols2 = cols / 2;

    for (int y = 0; y < height; y++) {
      int index = y;
      int ioffset = y * width;
      for (int x = 0; x < width; x++) {
        float r = 0, g = 0, b = 0, a = 0;
        int moffset = cols2;
        for (int col = -cols2; col <= cols2; col++) {
          float f = matrix[moffset + col];

          if (f != 0) {
            int ix = x + col;
            if (ix < 0) {
              ix = 0;
            } else if (ix >= width) {
              ix = width - 1;
            }
            int rgb = inPixels[ioffset + ix];
            a += f * ((rgb >> 24) & 0xff);
            r += f * ((rgb >> 16) & 0xff);
            g += f * ((rgb >> 8) & 0xff);
            b += f * (rgb & 0xff);
          }
        }
        int ia = clamp((int) (a + 0.5));
        int ir = clamp((int) (r + 0.5));
        int ig = clamp((int) (g + 0.5));
        int ib = clamp((int) (b + 0.5));
        outPixels[index] = (ia << 24) | (ir << 16) | (ig << 8) | ib;
        index += height;
      }
    }
  }

  public static Kernel genKernel(float radius) {
    int r = (int) Math.ceil(radius);
    int rows = r * 2 + 1;

    float[] matrix = new float[rows];

    float sigma = radius / 3;
    float sigma22 = 2 * sigma * sigma;
    float sigmaPi2 = 2 * (float) Math.PI * sigma;
    float sqrtSigmaPi2 = (float) Math.sqrt(sigmaPi2);

    float radius2 = radius * radius;
    float total = 0;
    int index = 0;
    for (int row = -r; row <= r; row++) {
      float distance = row * row;
      if (distance > radius2)
        matrix[index] = 0;
      else
        matrix[index] = (float) Math.exp(-(distance) / sigma22) / sqrtSigmaPi2;
      total += matrix[index];
      index++;
    }
    for (int i = 0; i < rows; i++)
      matrix[i] /= total;


    for (int i = 0; i < matrix.length; i++)
      System.out.println(matrix[i]);

    return new Kernel(rows, 1, matrix);
  }

  public static int clamp(int c) {
    if (c < 0) return 0;
    if (c > 255) return 255;
    return c;
  }

  @Override
  public void run() throws JIPSException {
    this.radius = 3;
    kernel = genKernel(radius);

    is0 = this.getInput(INPUT_0);
    is1 = is0.clone();

    int width = is0.getWidth();
    int height = is0.getHeight();

    int[] inPixels = new int[width * height];
    int[] outPixels = new int[width * height];
    is0.getRGB(0, 0, width, height, inPixels, 0, width);

    convolve(kernel, inPixels, outPixels, width, height);
    convolve(kernel, outPixels, inPixels, height, width);

    is1.setRGB(0, 0, width, height, inPixels, 0, width);
    this.setOutput(OUTPUT_0, is1);

  }
}
