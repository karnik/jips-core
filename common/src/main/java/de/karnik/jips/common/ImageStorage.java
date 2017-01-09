/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.common;

import de.karnik.jips.common.processing.JIPSObject;

import java.awt.color.ColorSpace;
import java.awt.image.*;

public class ImageStorage implements Cloneable, JIPSObject {


  /**
   * 8-bit grayscale (unsigned)
   */
  public static final int GRAY8 = 0;

  /**
   * 16-bit grayscale (unsigned)
   */
  public static final int GRAY16 = 1;

  /**
   * 32-bit floating-point grayscale
   */
  public static final int GRAY32 = 2;

  /**
   * 8-bit indexed color
   */
  public static final int COLOR_256 = 3;

  /**
   * 32-bit RGB color
   */
  public static final int COLOR_RGB = 4;

  private BufferedImage bi = null;
  private String imageID = null;

  public ImageStorage(BufferedImage bi) {
    this.bi = bi;
  }

  public BufferedImage getImageData() {
    return bi;
  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.processing.JIPSObject#getID()
   */
  public String getID() {
    return imageID;
  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.processing.JIPSObject#setID(java.lang.String)
   */
  public void setID(String id) {
    this.imageID = id;
  }

  public ColorModel getColorModel() {
    return bi.getColorModel();
  }

  public int getType() {
    return bi.getType();
  }

  public ImageStorage clone() {

    WritableRaster raster = bi.copyData(null);
    BufferedImage copy = new BufferedImage(bi.getColorModel(), raster, bi.isAlphaPremultiplied(), null);

    ImageStorage is = new ImageStorage(copy);
    is.setID(this.getID());

    return is;
  }

  public void setRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize) {
    bi.setRGB(startX, startY, w, h, rgbArray, offset, scansize);
  }

  public int[] getRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize) {
    return bi.getRGB(startX, startY, w, h, rgbArray, offset, scansize);
  }

  public int getRGB(int x, int y) {
    return bi.getRGB(x, y);
  }

  public void setRGB(int x, int y, int value) {
    bi.setRGB(x, y, value);
  }

  public int getWidth() {
    return bi.getWidth();
  }

  public int getHeight() {
    return bi.getHeight();
  }

  /**
   * Converts the Image to the CS_GRAY Colorspace.
   *
   * @return The new image.
   */
  public ImageStorage toGray() {

    if (this.getType() != ColorSpace.CS_GRAY) {

      ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
      ColorConvertOp op = new ColorConvertOp(cs, null);

      op.filter(bi, bi);
    }

    return this;
  }

  /**
   * Converts the Image to the CS_sRGB Colorspace.
   *
   * @return The new image.
   */
  public ImageStorage toRGB() {

    if (this.getType() != ColorSpace.CS_sRGB) {

      ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
      ColorConvertOp op = new ColorConvertOp(cs, null);

      op.filter(bi, bi);
    }

    return this;
  }

  public byte getByte(int x, int y) {

    byte tempByte[] = null;

    if (bi.getColorModel().getTransferType() == DataBuffer.TYPE_BYTE) {

      tempByte = (byte[]) bi.getRaster().getDataElements(x, y, null);
      return tempByte[0];
    }

    return -1;
  }

  public void setByte(int x, int y, byte data) {

    byte tempByte[] = new byte[4];
    tempByte[0] = data;

    if (bi.getColorModel().getTransferType() == DataBuffer.TYPE_BYTE)
      bi.getRaster().setDataElements(x, y, tempByte);

  }

}
