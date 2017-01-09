/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.common.config;

import de.karnik.jips.common.JIPSMessage;

import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * The JIPSConstants class contains several useful class fields.
 * It cannot be instantiated.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.4
 * @since v.0.0.1
 */
public abstract class JIPSConstants {

  /**
   * The JIPS version.
   */
  public static final String JIPS_VERSION = "0.0.15";
  /**
   * The build version.
   */
  public static final String JIPS_BUILD = "20080423";
  /**
   * The JIPS author.
   */
  public static final String JIPS_AUTHOR = "Markus Karnik";
  /**
   * The JIPS author mail.
   */
  public static final String JIPS_AUTHOR_MAIL = "markus.karnik@gmail.com";
  /**
   * The JIPS url.
   */
  public static final String JIPS_URL = "http://jips.karnik.de";
  /**
   * The JIPS splash image.
   */
  public static final String JIPS_SPLASH_IMG = "images/splash.png";
  /**
   * The JIPS window icon.
   */
  public static final String JIPS_WINDOW_ICON = "images/jips-icon-16x16.png";
  /**
   * The path to the XML file.
   */
  public final static String JIPS_CONFIG_FILE = "config/config.xml";
  /**
   * The JIPS doc-url.
   */
  public static final String JIPS_DOC = "doc/index.html";
  /**
   * The JIPS main menu config file.
   */
  public static final String JIPS_MAINMENU_CONFIG = "config/main_menu.xml";
  /**
   * The JIPS main menu bar config file.
   */
  public static final String JIPS_MAINMENUBAR_CONFIG = "config/main_menubar.xml";
  /**
   * The JIPS run menu bar config file.
   */
  public static final String JIPS_RUNMENUBAR_CONFIG = "config/run_menubar.xml";
  /**
   * The directory with the JIPS images.
   */
  public static final String JIPS_IMAGE_PATH = "images/";
  /**
   * The system file seperator.
   */
  public static final String SYSTEM_FILE_SEPERATOR = System.getProperty("file.separator");
  /**
   * The system path seperator.
   */
  public static final String SYSTEM_PATH_SEPERATOR = System.getProperty("path.separator");
  /**
   * The system line seperator.
   */
  public static final String SYSTEM_LINE_SEPERATOR = System.getProperty("line.separator");
  /**
   * The path for the JIPS log file.
   */
  public static final String JIPS_LOG_FILE = "jips.log";
  public static final HashMap<String, Integer> KEY_MAP = getKeyMap();
  public static final HashMap<String, JIPSMessage> JIPS_MESSAGES = getJIPSMessages();
  public static final String[] KNOWN_IMAGE_TYPES = {".jpg", ".png", ".bmp", ".jpeg", ".gif"};
  public static final String[] PLUGIN_CATEGORIES = {"ea_menu", "point_menu", "filter_menu", "edge_menu", "fourier_menu"};
  /**
   * The JIPS year.
   */
  private static final String JIPS_YEAR = "2006 - 2016";
  /**
   * The JIPS welcome message.
   */
  public static final String WELCOME_MSG = "JIPS v." + JIPS_VERSION +
          ", Copyright \u00A9 " + JIPS_YEAR +
          "  " + JIPS_AUTHOR;

  /**
   * This class is uninstantiable.
   */
  private JIPSConstants() {
  }

  /**
   * Generates and returns the messageMap.
   *
   * @return The messageMap.
   */
  private static HashMap<String, JIPSMessage> getJIPSMessages() {
    HashMap<String, JIPSMessage> tempMap = new HashMap<String, JIPSMessage>();

    tempMap.put("0001",
            new JIPSMessage(
                    "Error while reading the XML-File!",
                    "de.karnik.xml.XMLControl.XMLControl( String xmlFilePath, boolean critical )")
    );
    tempMap.put("0002", new JIPSMessage("Error while reading the XML-File!"));
    tempMap.put("0003",
            new JIPSMessage(
                    "Cannot find File!",
                    "There is no file with the specified path!")
    );
    tempMap.put("0004",
            new JIPSMessage(
                    "Cannot find File!",
                    "There is no file with the specified path!")
    );
    tempMap.put("0005", new JIPSMessage("Cannot write XML-File!"));
    tempMap.put("0006", new JIPSMessage("Error while reading Pixels!"));
    tempMap.put("0007",
            new JIPSMessage(
                    "Error while reading the ColorModels!",
                    "Error while opening the Splash-Image. Possible reason is a corrupted or removed file.")
    );
    tempMap.put("0008",
            new JIPSMessage(
                    "Cannot open File!",
                    "Error while opening a Textfile. Possible reason is a corrupted or removed file.")
    );
    tempMap.put("0009",
            new JIPSMessage(
                    "Error while reading the Buffer!",
                    "Error while reading the BufferedReader. Possible reason is a corrupted or removed file.")
    );
    tempMap.put("000A",
            new JIPSMessage(
                    "JIPS-Logo cannot be displayed!",
                    "The JIPS-Logo for the AboutFrame cannot be displayed. Maybe the Image is missing!")
    );
    tempMap.put("000B",
            new JIPSMessage(
                    "The Image cannot be displayed!",
                    "The Image cannot be loaded into the MediaTracker.")
    );
    tempMap.put("000C",
            new JIPSMessage(
                    "TrueType font file is corrupted: de/karnik/fonts/DejaVuSans-Bold.ttf",
                    "The TrueType font file is corrupted!")
    );
    tempMap.put("000D",
            new JIPSMessage(
                    "TrueType font cannot be loaded: de/karnik/fonts/DejaVuSans-Bold.ttf",
                    "The TrueType font file cannot be loaded. Maybe the file is missing!")
    );
    tempMap.put("000E",
            new JIPSMessage(
                    "TrueType font file is corrupted: de/karnik/fonts/DejaVuSans.ttf",
                    "The TrueType font file is corrupted!")
    );
    tempMap.put("000F",
            new JIPSMessage(
                    "TrueType font cannot be loaded: de/karnik/fonts/DejaVuSans.ttf",
                    "The TrueType font file cannot be loaded. Maybe the file is missing!")
    );
    tempMap.put("0010",
            new JIPSMessage(
                    "TrueType font file is corrupted: de/karnik/fonts/DejaVuSans-BoldOblique.ttf",
                    "The TrueType font file is corrupted!")
    );
    tempMap.put("0011",
            new JIPSMessage(
                    "TrueType font cannot be loaded: de/karnik/fonts/DejaVuSans-BoldOblique.ttf",
                    "The TrueType font file cannot be loaded. Maybe the file is missing!")
    );
    tempMap.put("0012", new JIPSMessage("Cannot convert URL."));
    tempMap.put("0013", new JIPSMessage("Problem with URL-Syntax."));
    tempMap.put("0014", new JIPSMessage("Error while initializing Browser!"));
    tempMap.put("0015",
            new JIPSMessage(
                    "Cannot find File!",
                    "There is no file to open.")
    );
    tempMap.put("0016",
            new JIPSMessage(
                    "Error while loading JIPS-Logo!",
                    "There are problems while reading the image file!")
    );
    tempMap.put("0017",
            new JIPSMessage(
                    "Error while reading the file!",
                    "There are problems while reading the specified text file!")
    );
    tempMap.put("0018",
            new JIPSMessage(
                    "Problem while reading color values!",
                    "There is a problem with a color config value in config.xml! Maybe format is incorrect!")
    );
    tempMap.put("0019",
            new JIPSMessage(
                    "Cannot read desktop dimension!",
                    "There is a problem with a desktop dimension config values in config.xml! Maybe format is incorrect!")
    );
    tempMap.put("001A",
            new JIPSMessage(
                    "Cannot read look and feel!",
                    "There is a problem while reading the config value of the look and feel! Maybe format is incorrect!")
    );
    tempMap.put("001B",
            new JIPSMessage(
                    "Can't read alpha value!",
                    "There is a problem while reading the config value of transparency ! Maybe format is incorrect or value is missing.")
    );
    tempMap.put("001C",
            new JIPSMessage(
                    "Can't load library!",
                    "There is a problem while loading external libraries! Please install all required libraries.")
    );

    tempMap.put("001F",
            new JIPSMessage(
                    "Error while parsing File!",
                    "The xml value could not be translated to a boolean value. Check XML-File!")
    );
    tempMap.put("0020",
            new JIPSMessage(
                    "Error while parsing File!",
                    "The xml value could not be translated to an integer value. Check XML-File!")
    );
    tempMap.put("0021",
            new JIPSMessage(
                    "Error while parsing File!",
                    "Error with the bounds definition. You have to define at least 4 coordinates. Check XML-File!")
    );
    tempMap.put("0022",
            new JIPSMessage(
                    "Error while parsing File!",
                    "The field length could not be translated to an integer value. Check XML-File!")
    );
    tempMap.put("0023",
            new JIPSMessage(
                    "Incompatible XML-File detected.",
                    "JIPS detected a incompatible XML-File-Version.")
    );
    tempMap.put("0024",
            new JIPSMessage(
                    "URL is empty or null!",
                    "JIPS cannot create a URL from the given path.")
    );
    tempMap.put("0027",
            new JIPSMessage(
                    "Cannot set Look &amp; Feel.",
                    "Look &amp; Feel is not support or cannot be set.")
    );
    tempMap.put("0028", new JIPSMessage("Memory Refresh-Thread was interrupted."));
    tempMap.put("0029",
            new JIPSMessage(
                    "Incorrect properties-File!",
                    "Please check the given properties-File for incorrect values.")
    );
    tempMap.put("002A",
            new JIPSMessage(
                    "Problems while loading a plugin!",
                    "Please check the given properties-File for incorrect values.")
    );
    tempMap.put("002B",
            new JIPSMessage(
                    "Problems while loading a plugin!",
                    "Please check the given properties-File for incorrect values.")
    );
    tempMap.put("002C", new JIPSMessage("Error while plugin execution!"));
    tempMap.put("002D",
            new JIPSMessage(
                    "Can't find frame.",
                    "The specified frame can't be found in the frames-array!")
    );
    tempMap.put("002E",
            new JIPSMessage(
                    "Can't set the new theme.",
                    "New theme can't be set when one or more components are visible.")
    );
    tempMap.put("002F",
            new JIPSMessage(
                    "Can't get Process!",
                    "JIPS can't get the Process because the given process id doesn't exist.")
    );

    tempMap.put("0030",
            new JIPSMessage(
                    "Can't select the new Project.",
                    "The new Project Frame can't be selected.")
    );

    tempMap.put("0031",
            new JIPSMessage(
                    "Could not load input image!",
                    "Input might not be connected.")
    );

    // PLUGIN CODES

    tempMap.put("F000",
            new JIPSMessage(
                    "Input has no data!",
                    "The Input is not connected or has no data.")
    );

    tempMap.put("FFFF",
            new JIPSMessage(
                    "Problem while executing a plugin!",
                    "An Error occured while executing a plugin.")
    );
    return tempMap;
  }

  /**
   * Generates and returns the keyMap.
   *
   * @return the keyMap.
   */
  private static HashMap<String, Integer> getKeyMap() {
    HashMap<String, Integer> tempMap = new HashMap<String, Integer>();

    // numbers
    tempMap.put("0", KeyEvent.VK_0);
    tempMap.put("1", KeyEvent.VK_1);
    tempMap.put("2", KeyEvent.VK_2);
    tempMap.put("3", KeyEvent.VK_3);
    tempMap.put("4", KeyEvent.VK_4);
    tempMap.put("5", KeyEvent.VK_5);
    tempMap.put("6", KeyEvent.VK_6);
    tempMap.put("7", KeyEvent.VK_7);
    tempMap.put("8", KeyEvent.VK_8);
    tempMap.put("9", KeyEvent.VK_9);

    // characters
    tempMap.put("A", KeyEvent.VK_A);
    tempMap.put("B", KeyEvent.VK_B);
    tempMap.put("C", KeyEvent.VK_C);
    tempMap.put("D", KeyEvent.VK_D);
    tempMap.put("E", KeyEvent.VK_E);
    tempMap.put("F", KeyEvent.VK_F);
    tempMap.put("G", KeyEvent.VK_G);
    tempMap.put("H", KeyEvent.VK_H);
    tempMap.put("I", KeyEvent.VK_I);
    tempMap.put("J", KeyEvent.VK_J);
    tempMap.put("K", KeyEvent.VK_K);
    tempMap.put("L", KeyEvent.VK_L);
    tempMap.put("M", KeyEvent.VK_M);
    tempMap.put("N", KeyEvent.VK_N);
    tempMap.put("O", KeyEvent.VK_O);
    tempMap.put("P", KeyEvent.VK_P);
    tempMap.put("Q", KeyEvent.VK_Q);
    tempMap.put("R", KeyEvent.VK_R);
    tempMap.put("S", KeyEvent.VK_S);
    tempMap.put("T", KeyEvent.VK_T);
    tempMap.put("U", KeyEvent.VK_U);
    tempMap.put("V", KeyEvent.VK_V);
    tempMap.put("W", KeyEvent.VK_W);
    tempMap.put("X", KeyEvent.VK_X);
    tempMap.put("Y", KeyEvent.VK_Y);
    tempMap.put("Z", KeyEvent.VK_Z);

    // special keys
    tempMap.put("Alt", KeyEvent.VK_ALT);
    tempMap.put("Windows", KeyEvent.VK_WINDOWS);
    tempMap.put("Tab", KeyEvent.VK_TAB);
    tempMap.put(" ", KeyEvent.VK_SPACE);
    tempMap.put("Shift", KeyEvent.VK_SHIFT);
    tempMap.put(";", KeyEvent.VK_SEMICOLON);

    return tempMap;
  }

}
