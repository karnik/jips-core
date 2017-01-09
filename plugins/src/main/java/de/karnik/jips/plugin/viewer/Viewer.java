/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.plugin.viewer;

import de.karnik.jips.common.ImageStorage;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.processing.JIPSProcess;
import de.karnik.jips.plugin.viewer.common.ViewerConstants;
import de.karnik.jips.plugin.viewer.gui.ViewerFrame;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * JIPSProcess to start the image viewer plugin.
 *
 * @author <a href="mailto:markus.karnik@gmail.com">Markus Karnik</a>
 * @version 1.0
 */
public class Viewer extends JIPSProcess {

  private static final String INPUT_0 = "in0";

  public Viewer() {
    super();
  }

  /**
   * Only for testing purposes. Do not use!
   *
   * @param args The command line args.
   * @throws IOException
   * @throws JIPSException
   */
  public static void main(String[] args) throws IOException, JIPSException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
    // Set System L&F
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    new ViewerFrame("JIPS Viewer - ONLY FOR TESTS - DO NOT USE", Viewer.loadImage(args[0]), ViewerConstants.SIZE_STRECHED, 1.0f);
  }

  private static ImageStorage loadImage(String path) throws IOException, JIPSException {
    ImageReader reader = Viewer.getReader(path);
    ImageReadParam params = reader.getDefaultReadParam();
    BufferedImage bi = reader.read(0, params);
    return new ImageStorage(bi);
  }

  private static ImageReader getReader(String filename) throws IOException {
    File f = new File(filename);
    ImageInputStream iis = ImageIO.createImageInputStream(f);
    Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

    ImageReader reader = readers.next();
    reader.setInput(iis, true);
    return reader;
  }

  public void run() throws JIPSException {
    ImageStorage is = this.getInput(INPUT_0);

    if (null == is)
      throw new JIPSException("F000", new Exception(INPUT_0 + " is null!"), false);

    String title = getConfiguration("name");

    new ViewerFrame(title, is, ViewerConstants.SIZE_STRECHED, 1.0f);
  }

}
