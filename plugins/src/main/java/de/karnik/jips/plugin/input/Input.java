/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.plugin.input;

import de.karnik.jips.common.ImageStorage;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.processing.InputProcess;
import de.karnik.jips.common.processing.JIPSProcess;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;

public class Input extends JIPSProcess implements InputProcess {

  public static final String OUTPUT_0 = "out0";

  public void run() throws JIPSException {

    String filepath = this.getConfiguration("filepath");

    if (filepath == null || filepath.equals("") || filepath.equals("null")) {
      throw new JIPSException("FFFF", new Exception("Empty Value!"), false);
    }

    initProgressBar(0, 6);

    ImageStorage image = null;

    try {
      setProgressBar(1);

      image = createImage(filepath);

      setProgressBar(6);

      setOutput(OUTPUT_0, image);

    } catch (IOException | TransformerException e) {
      throw new JIPSException("FFFF", e, false);
    }
  }

  private ImageStorage createImage(String path) throws IOException, JIPSException, TransformerException {

    BufferedImage bi = null;

    ImageReader reader = getReader(path);

    setProgressBar(2);

    ImageReadParam params = reader.getDefaultReadParam();
    bi = reader.read(0, params);

    setProgressBar(4);

    IIOMetadata metaData = reader.getImageMetadata(0);

    String metaFormatName[] = null;
    if (metaData != null) {
      metaFormatName = metaData.getMetadataFormatNames();

      if (metaFormatName == null || metaFormatName.equals("null"))
        throw new JIPSException("FFFF", new Exception("Wrong Format Name!"), false);

      Node data = metaData.getAsTree(metaFormatName[1]);

      System.out.println(data.toString());

      // Set up the output transformer
      TransformerFactory transfac = TransformerFactory.newInstance();
      Transformer trans = transfac.newTransformer();
      trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      trans.setOutputProperty(OutputKeys.INDENT, "yes");

      // Print the DOM node

      StringWriter sw = new StringWriter();
      StreamResult result = new StreamResult(sw);
      DOMSource source = new DOMSource(data);
      trans.transform(source, result);
      String xmlString = sw.toString();

      System.out.println(xmlString);


    } else {
      throw new JIPSException("FFFF", new Exception("Error while reading ImageData!"), false);
    }

    setProgressBar(5);


    ImageStorage is = new ImageStorage(bi);

    return is;

  }

  private ImageReader getReader(String filename) throws IOException {
    File f = new File(filename);
    ImageInputStream iis = ImageIO.createImageInputStream(f);
    Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

    ImageReader reader = readers.next();
    reader.setInput(iis, true);
    return reader;
  }


}
