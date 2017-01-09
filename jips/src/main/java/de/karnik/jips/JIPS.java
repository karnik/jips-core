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
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.common.lang.Translator;
import de.karnik.jips.gui.FrameHelper;
import de.karnik.jips.gui.MainFrame;
import de.karnik.jips.gui.splash.JIPSSplashScreen;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.ParseException;

import javax.swing.*;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * The JIPSApp class contains main function of JIPS.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.1
 */
public class JIPS {

  /**
   * Starts the JIPS program.
   *
   * @param args the command line arguments
   */
  public static void main(String args[]) throws Exception {
    try {

      // create splash screen
      JIPSSplashScreen splashScreen = new JIPSSplashScreen(4);
      splashScreen.setText("Init...", JIPSConstants.WELCOME_MSG);

      // create JIPS variables
      JIPSVariables vars = JIPSVariables.getInstance();
      // create the translator
      Translator translator = Translator.getInstance();

      // print the program information
      System.out.println(JIPSConstants.WELCOME_MSG);

      // create the parser
      CommandLineParser parser = new GnuParser();
      vars.createOptions();
      try {
        // parse the command line arguments
        CommandLine line = parser.parse(vars.getOptions(), args);
        vars.testOptions(line);
      } catch (ParseException exp) {
        exitJIPS("Exit: " + exp.getMessage());
      }


      // show some system information
      if (vars.debugMode) {
        MsgHandler.debugMSG("System Information", true);
        MsgHandler.debugMSG("  Java: " + System.getProperty("java.vendor") + " "
                + System.getProperty("java.version"), true);
        MsgHandler.debugMSG("  OS:   " + System.getProperty("os.name") + " "
                + System.getProperty("os.version") + " ("
                + System.getProperty("os.arch") + ")", true);

        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader) cl).getURLs();

        MsgHandler.debugMSG("  Classpath:   ", true);
        for (URL url : urls) {
          MsgHandler.debugMSG("   " + url.getFile(), true);

        }
      }

      // generate colors
      splashScreen.setLeftText(translator.getTranslation("gen_colors"));
      vars.generateColors();

      // find and load plugins
      splashScreen.setLeftText(translator.getTranslation("load_plugins"));
      PluginStorage.getInstance();

      // try to set LookAndFeel
      splashScreen.setLeftText(translator.getTranslation("set_look"));
      vars.setLookAndFeel(vars.getLookAndFeel());

      // open MainFrame
      splashScreen.setLeftText(translator.getTranslation("load_mainframe"));
      createMainFrame();

    } catch (JIPSException je) {
      JIPSExceptionHandler.handleException(je, true);
    }


  }

  public static void exitJIPS(String msg) {
    System.out.println(msg);
    System.exit(0);
  }

  public static void createMainFrame() {
    // Causes doRun.run() to be executed asynchronously on the AWT
    // event dispatching thread. This will happen after all pending
    // AWT events have been processed. This method should be used when
    // an application thread needs to update the GUI.
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        try {
          Translator translator = Translator.getInstance();
          MainFrame mf = MainFrame.getInstance();
          FrameHelper.addFrame(mf);
          mf.setTitle(translator.getTranslation("jips_full"));
          //mf.setVisible( true );
        } catch (JIPSException je) {
          JIPSExceptionHandler.handleException(je, true);
        }
      }
    });
  }
}
