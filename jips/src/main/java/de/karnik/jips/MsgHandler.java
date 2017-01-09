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
import de.karnik.jips.common.JIPSMessage;
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.common.helper.IconFactory;
import de.karnik.jips.common.lang.Translator;
import de.karnik.jips.gui.dialog.MessageDialog;
import de.karnik.jips.gui.frames.internal.InternalConsole;
import jiconfont.icons.GoogleMaterialDesignIcons;

import javax.swing.*;
import java.math.BigDecimal;

/**
 * The MsgHandler class displays debug-, warning-, error- and other messages and questions.
 * It cannot be instantiated.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.1
 * @since v.0.0.1
 */
public class MsgHandler {

  public static final int SECONDS = 0;
  public static final int SECONDS_MILLI = 1;
  public static final int SECONDS_MICRO = 2;
  public static final int SECONDS_NANO = 3;

  /**
   * This class is uninstantiable.
   */
  private MsgHandler() {
  }

  public static void showDialog(JIPSException je, JIPSMessage jm, String message, boolean log) {

    MessageDialog md = new MessageDialog(je, jm, message);
    md.setVisible(true);

    if (log) {
      IO.writeToLog("[ DIALOG ]\t\t" + je.getCode() + " - " + jm.getMsg());
      IO.writeToLog("~>" + message);
      IO.writeToLog("~>" + je.getExceptionMessage());
      IO.writeToLog("~>" + jm.getHint());
    }
  }

  /**
   * Prints a message with the "[ JIPS ]" tag to the "standard" output stream.
   *
   * @param msg the String to print
   * @param log if true, the text is also written to the log file
   */
  public static void msg(String msg, boolean log) {
    String text = "[ JIPS ] \t\t" + msg;
    System.out.println(text);
    if (log) IO.writeToLog(text);
  }

  /**
   * Prints a debug-message with the "[ DEBUG ] [ START ]" tags to the "standard" output stream.
   *
   * @param msg the String to print
   * @param log if true, the text is also written to the log file
   */
  public static void debugMSGStart(String msg, boolean log) {
    String text = "[ DEBUG ] [ START ]\t" + msg;
    System.out.println(text);
    if (log) IO.writeToLog(text);
  }

  /**
   * Prints a debug-message with the "[ DEBUG ] [ END ]" tags to the "standard" output stream.
   * The output
   *
   * @param msg the String to print
   * @param log if true, the text is also written to the log file
   */
  public static void debugMSGEnd(String msg, boolean log) {
    String text = "[ DEBUG ] [ END ]\t" + msg;
    System.out.println(text);
    if (log) IO.writeToLog(text);
  }

  /**
   * Prints a debug-message with the "[ DEBUG ]" tag to the "standard" output stream.
   *
   * @param msg the String value to print
   * @param log if true, the text is also written to the log file
   */
  public static void debugMSG(String msg, boolean log) {
    String text = "[ DEBUG ]\t\t" + msg;
    System.out.println(text);
    if (log) IO.writeToLog(text);
  }

  /**
   * Prints a debug-message with the "[ DEBUG ]" tag to the "standard" output stream.
   *
   * @param msg the int value to print
   * @param log if true, the text is also written to the log file
   */
  public static void debugMSG(int msg, boolean log) {
    String text = "[ DEBUG ]\t\t" + msg;
    System.out.println(text);
    if (log) IO.writeToLog(text);
  }

  /**
   * Prints a debug-message with the "[ DEBUG ]" tag to the "standard" output stream.
   *
   * @param msg the long value to print
   * @param log if true, the text is also written to the log file
   */
  public static void debugMSG(long msg, boolean log) {
    String text = "[ DEBUG ]\t\t" + msg;
    System.out.println(text);
    if (log) IO.writeToLog(text);
  }

  /**
   * Prints a message with the "[ JIPS ]" tag to the "internal console".
   *
   * @param jm  the long value to print
   * @param log if true, the text is also written to the log file
   */
  public static void consoleMSG(JIPSMessage jm, boolean log) throws JIPSException {
    InternalConsole ic = InternalConsole.getInstance();
    ic.addMessage(jm);
    if (log) IO.writeToLog("[ JIPS ]\t" + jm.getMsg());
  }

  /**
   * Prints a estimated time of a method with the "[ JIPS ]" tag to the "internal console".
   *
   * @param name          The name of the method.
   * @param estimatedTime The estimated time in nano seconds.
   * @param typ
   * @param log           If true, the text is also written to the log file.
   */
  public static void executionTimeConsoleMSG(String name, long estimatedTime, int typ, boolean log) throws JIPSException {
    InternalConsole ic = InternalConsole.getInstance();
    StringBuffer sb = new StringBuffer("Execution time of ");
    double tempTime = 0;
    String ext = null;

    switch (typ) {
      case SECONDS:
        tempTime = (double) ((double) estimatedTime / (double) (1000 * 1000 * 1000));
        ext = "s";
        break;
      case SECONDS_MILLI:
        tempTime = (double) ((double) estimatedTime / (double) (1000 * 1000));
        ext = "ms";
        break;
      case SECONDS_MICRO:
        tempTime = (double) ((double) estimatedTime / (double) 1000);
        ext = "us";
        break;
      case SECONDS_NANO:
        tempTime = (double) estimatedTime;
        ext = "ns";
        break;
    }

    BigDecimal myDec = new BigDecimal(tempTime);
    myDec = myDec.setScale(3, BigDecimal.ROUND_HALF_UP);

    sb.append(name).append(": ").append(myDec).append(" ").append(ext);

    ic.addMessage(new JIPSMessage(JIPSMessage.INFORMATION, sb.toString(), "Execution time!"));
    if (log) IO.writeToLog(sb.toString());
  }

  /**
   * Prints a warning-message with the "[ WARNING ]" tag to the "standard" output stream.
   *
   * @param msg the String to print
   * @param log if true, the text is also written to the log file
   */
  public static void warningMSG(String msg, boolean log) {
    String text = "[ WARNING ]\t\t" + msg;
    System.out.println(text);
    if (log) IO.writeToLog(text);
  }

  /**
   * Pops up a question dialog.
   *
   * @param question The question.
   * @return <strong>true</strong> for yes, false otherwise.
   */
  public static boolean yesNoQuestion(String question) {
    return (JOptionPane.showConfirmDialog(null, question) != JOptionPane.YES_OPTION);

  }

  /**
   * Pops up a dialog with an exit question.
   *
   * @return <strong>true</strong> for yes, <strong>false</strong> otherwise
   */
  public static boolean exitQuestion() throws JIPSException {
    int status = 0;
    Translator trans = Translator.getInstance();
    JIPSVariables vars = JIPSVariables.getInstance();
    IconFactory iconFactory = IconFactory.getInstance();

    status = JOptionPane.showConfirmDialog(null,
            trans.getTranslation("exit_question"),
            trans.getTranslation("exit_confim"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            iconFactory.getIcon(GoogleMaterialDesignIcons.HELP_OUTLINE, 48, IconFactory.ICON_COLOR_INFO));

    if (vars.debugMode) debugMSG("MsgHandler.exitQuestion() : " + status, true);
    return status == 0;

  }
}