/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.plugin.median;

import de.karnik.jips.common.ImageStorage;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.processing.JIPSProcess;

import java.awt.image.DataBuffer;
import java.util.Arrays;

public class Median extends JIPSProcess {

  public static final String INPUT_0 = "in0";
  public static final String OUTPUT_0 = "out0";

  //  returns a byte array of length 4
  private static byte[] intToDWord(int i) {
    byte[] dword = new byte[4];
    dword[0] = (byte) (i & 0x00FF);
    dword[1] = (byte) ((i >> 8) & 0x000000FF);
    dword[2] = (byte) ((i >> 16) & 0x000000FF);
    dword[3] = (byte) ((i >>> 24) & 0x000000FF);
    return dword;
  }

  public void run() throws JIPSException {

    ImageStorage imageStorage = this.getInput(INPUT_0).toGray();
    ImageStorage copy = imageStorage.clone();

    int matrixSize = 3;
    String size = this.getConfiguration("size");

    try {
      matrixSize = Integer.parseInt(size);
    } catch (NumberFormatException nfe) {
      throw new JIPSException("FFFF", nfe, false);
    }

    int[] medianValues = new int[matrixSize * matrixSize];
    byte[] newByte = new byte[4];
    int w = imageStorage.getWidth();
    int h = imageStorage.getHeight();

    this.initProgressBar(0, h);

    if (imageStorage.getColorModel().getTransferType() == DataBuffer.TYPE_BYTE) {

      for (int y = 1; y < h - 1; y++) {
        for (int x = 1; x < w - 1; x++) {

          int counter = 0;
          for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
              medianValues[counter] = copy.getByte(x + i, y + j) & 0xFF;
              counter++;
            }
          }

          Arrays.sort(medianValues);
          float tempMatrixSize = (float) (matrixSize * matrixSize) / (float) 2;
          newByte = intToDWord(medianValues[Math.round(tempMatrixSize)]);
          imageStorage.setByte(x, y, newByte[0]);
        }
        this.setProgressBar(y);
      }
    }
    this.setOutput(OUTPUT_0, imageStorage);
  }

}
