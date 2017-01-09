/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.config.JIPSConstants;
import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Scanner;
import java.util.Vector;

/**
 * The IO class contains several useful class fields and methods for I/O.
 * It cannot be instantiated.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.1
 */
public final class IO {

  /**
   * This class is uninstantiable.
   */
  private IO() {
  }


  /**
   * Writes a line of text to the log-file.
   *
   * @param text the text to write
   */
  public static void writeToLog(String text) {
    writeLogFile(JIPSConstants.JIPS_LOG_FILE, text);
  }

  /**
   * Searches for the absolute URL of a resource (also for JAR-Files).
   *
   * @param path     the relative path to the resource
   * @param critical if <strong>true</strong> the program exits on Errors
   * @return the URL of the resource, or null if the file cannot be opened
   */
  public static URL getFilePath(String path, boolean critical) throws JIPSException {

    // find the absolut path of the wanted resource
    URL url = ClassLoader.getSystemResource(path);

    // if status is critical, exit the program when the resource cannot be found
    // otherwise return null
    if ((critical) && (url == null)) {
      String[] loc = {"de.karnik.common.IO", "getFilePath( String path, boolean critical )", "-"};
      throw new JIPSException("0003", true, path, loc);

    } else if ((!critical) && (url == null)) {
      MsgHandler.warningMSG("Code 0003 - de.karnik.common.IO.getFilePath( String path, boolean critical ) ", true);
      MsgHandler.warningMSG("   Cannot find file: " + path, true);
    }
    return url;
  }

  /**
   * @param dir
   * @param name
   * @return
   */
  public static Vector<File> getFilesFromDir(File dir, String name) {

    Vector<File> fileVector = new Vector<File>();
    Vector<File> tempFiles = new Vector<File>();

    File[] files = dir.listFiles();

    if (files != null) {
      for (int i = 0; i < files.length; i++) {

        if (files[i].isDirectory()) {
          tempFiles = getFilesFromDir(files[i], name);

          for (int j = 0; j < tempFiles.size(); j++) {
            fileVector.add(tempFiles.get(j));
          }

        } else {
          if (files[i].getName().equals(name) || files[i].getName().endsWith(name))
            fileVector.add(files[i]);
        }
      }
    }

    return fileVector;
  }

  public static InputStream getFileInputStream(String path, boolean isPathAbsolute, boolean critical) throws JIPSException {
    File f = null;
    InputStream is = null;

    try {

      // try to open the requested resource
      if (isPathAbsolute) {
        f = new File(path);
        is = new FileInputStream(f);
      } else {
        // find the absolut path of the wanted resource
        URL url = ClassLoader.getSystemResource(path);
        is = ClassLoader.getSystemResourceAsStream(path);
      }

    } catch (NullPointerException | FileNotFoundException npe) {

      if ((critical == true)) {
        String[] loc = {"de.karnik.common.IO", "getFileInputStream( String path, boolean critical )", "-"};
        throw new JIPSException("0024", critical, path, loc);

      } else if ((critical == false) && (f == null)) {
        MsgHandler.warningMSG("Code 0024 - de.karnik.common.IO.getFileInputStream( String path, boolean critical ) ", true);
        MsgHandler.warningMSG("   Path is empty: " + path, true);
      }
    }

    return is;

  }

  public static InputStream getFileInputStream(String path, boolean critical) throws JIPSException {
    return getFileInputStream(path, false, critical);
  }


  public static FileOutputStream getFileOutputStream(String path, boolean critical) throws JIPSException {

    FileOutputStream os = null;

    try {
      os = new FileOutputStream(path);

    } catch (FileNotFoundException fne) {

      if ((critical)) {
        //String[] loc = { "de.karnik.common.IO", "getFileInputStream( String path, boolean critical )", "-" };
        //throw new JIPSException( "0024", critical, path, loc );
        //            	 TODO
      } else {
        // TODO
      }
    }

    return os;

  }

  /**
   * Finds a resource (also for JAR-Files).
   *
   * @param path           the relative or absolute path to the resource
   * @param isPathAbsolute if <strong>true</strong> the path has to be a full path
   * @param critical       if <strong>true</strong> the program exits on Errors
   * @return the wanted resource, or null if the file cannot be opened
   */
  public static File getFile(String path, boolean isPathAbsolute, boolean critical) throws JIPSException {
    File f = null;
    URL url = null;

    if (null == path)
      return null;

    // try to open the requested resource
    if (isPathAbsolute) {
      return new File(path);

    } else {
      url = ClassLoader.getSystemResource(path);
    }


    // if status is critical, exit the program when the resource cannot be opened
    // otherwise return null
    if (null == url && critical) {
      String[] loc = {"de.karnik.common.IO", "getFile( String path, boolean critical )", "-"};
      throw new JIPSException("0004", critical, path, loc);
    } else if (null == url && !critical) {
      MsgHandler.warningMSG("Code 0004 - de.karnik.common.IO.getFile( String path, boolean critical ) ", true);
      MsgHandler.warningMSG("   Cannot find file: " + path, true);

      return null;
    }

    f = new File(url.getPath());
    return f;
  }

  /**
   * Finds a resource (also for JAR-Files).
   *
   * @param path     the relative path to the resource
   * @param critical if <strong>true</strong> the program exits on Errors
   * @return the wanted resource, or null if the file cannot be opened
   */
  public static File getFile(String path, boolean critical) throws JIPSException {
    return getFile(path, false, critical);
  }

  /**
   * Writes a XML-file back into the file.
   *
   * @param myDoc the Document to write
   */
  public static void writeXMLDocument(Document myDoc, String file) throws JIPSException {

    // open an XMLOuputter and write the document to the specified File
    XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());

    try {
      FileOutputStream out = new FileOutputStream(file);
      xmlOutput.output(myDoc, out);
      out.flush();
      out.close();
    } catch (IOException ioe) {
      throw new JIPSException("0005", ioe, true);
    }
  }


  /**
   * Opens a Textfile for reading (also for JAR-Files).
   *
   * @param path     the relative path to the resource
   * @param critical if <strong>true</strong> the program exits on errors
   * @return a BufferedReader connected with the textfile, or null if the
   * file cannot be opened
   */
  public static BufferedReader openTextFile(String path, boolean critical) throws JIPSException {
    FileReader fr;
    BufferedReader br;

    // try to open the resource
    // if status is critical, exit the program when the resource cannot be opened
    // otherwise return null
    try {
      fr = new FileReader(ClassLoader.getSystemResource(path).getPath());
      br = new BufferedReader(fr);

      return br;

    } catch (IOException ioe) {
      if (critical) {
        throw new JIPSException("0008", ioe, critical);
      } else {
        MsgHandler.warningMSG("Code 0008 - de.karnik.common.IO.openTextFile( String path, boolean critical ) ", true);
        MsgHandler.warningMSG("   Cannot open file: " + path, true);
      }
    } catch (NullPointerException npe) {
      if (critical) {
        throw new JIPSException("0015", npe, critical);
      } else {
        MsgHandler.warningMSG("Code 0015 - de.karnik.common.IO.openTextFile( String path, boolean critical ) ", true);
        MsgHandler.warningMSG("   Cannot find file: " + path, true);
      }
    }
    return null;
  }

  /**
   * Writes a line of text to the log-file.
   *
   * @param path the relative path to the resource
   * @param text the text to write
   */
  public static void writeLogFile(String path, String text) {
    FileWriter fw;
    BufferedWriter bw;

    // try to open the resource for writing
    // if status is critical, exit the program when the resource cannot be opened
    // otherwise return null
    try {
      new File(path).createNewFile();
      File f = new File(path);

      fw = new FileWriter(f, true);
      bw = new BufferedWriter(fw);

      bw.write(text);
      bw.newLine();
      bw.flush();
      bw.close();

    } catch (IOException ioe) {
      MsgHandler.warningMSG("Code 0025 - de.karnik.common.IO.writeLogFile( String path, boolean critical )", false);
      MsgHandler.warningMSG("   Cannot write to file: " + path, false);
    } catch (NullPointerException npe) {
      MsgHandler.warningMSG("Code 0026 - de.karnik.common.IO.writeLogFile( String path, boolean critical )", false);
      MsgHandler.warningMSG("   Cannot find file: " + path, false);
    }

    bw = null;
    fw = null;
  }

  /**
   * Loads an image with the given name from the JIPS_IMAGE_PATH.
   *
   * @param imageName The name of the image to load.
   * @return The image as Image.
   * @throws JIPSException
   */
  public static Image getImage(String imageName) throws JIPSException {
    ImageIcon ii;
    URL url = IO.getFilePath(JIPSConstants.JIPS_IMAGE_PATH + imageName, false);

    if (url == null) {
      ii = new ImageIcon();
    } else {
      ii = new ImageIcon(url);
    }

    return ii.getImage();
  }

  /**
   * Loads an image with the given name from the JIPS_IMAGE_PATH.
   *
   * @param imageName The name of the image to load.
   * @return The image as ImageIcon.
   * @throws JIPSException
   */
  public static ImageIcon getImageIcon(String imageName) throws JIPSException {
    ImageIcon ii;
    URL url = IO.getFilePath(JIPSConstants.JIPS_IMAGE_PATH + imageName, false);

    if (url == null) {
      ii = new ImageIcon();
    } else {
      ii = new ImageIcon(url);
    }

    return ii;
  }

  /**
   * Reads and returns the content of a BufferedReader.
   * The Function will read until EOF.
   *
   * @param br the BufferedReader to read from
   * @return a String with the content of the BufferedReader,
   * or null if the BufferedReader becomes interrupted
   */
  public static String getText(BufferedReader br) throws JIPSException {
    String tempString1 = "";
    String tempString2 = "";

    // try to read the input or throw an error
    try {
      while ((tempString1 = br.readLine()) != null) {
        tempString2 += tempString1 + "\n";
      }
    } catch (IOException ioe) {
      throw new JIPSException("0009", ioe, false);
    }

    br = null;
    return tempString2;
  }

  /**
   * Reads and returns the content of a TextFile.
   * The Function will read until EOF.
   *
   * @param path the Path to the String
   * @return a String with the content of the File,
   * or null if an error occurred
   */
  public static String getTextFile(String path) throws JIPSException {

    Scanner s;
    StringBuffer sb = new StringBuffer();
    URL url = ClassLoader.getSystemResource(path);

    if (url == null)
      return null;

    String fullPath = url.getPath();

    try {
      s = new Scanner(new File(fullPath));
    } catch (IOException ioe) {
      throw new JIPSException("0017", ioe, false);
    }

    while (s.hasNextLine())
      sb.append(s.nextLine() + "\n");

    s.close();
    return sb.toString();
  }
}