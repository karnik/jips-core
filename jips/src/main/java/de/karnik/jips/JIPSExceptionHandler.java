/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */
package de.karnik.jips;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.JIPSMessage;
import de.karnik.jips.common.config.JIPSConstants;

/**
 * The JIPSExceptionHandler class contains the exception handling functions.
 *
 * @author <a href="mailto:markus.karnik@gmail.com">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.6
 */
public class JIPSExceptionHandler {

  /**
   * This class is uninstantiable.
   */
  private JIPSExceptionHandler() {
    // nothing
  }

  public static void handleException(JIPSException je) {
    handleException(je, true);
  }

  private static JIPSMessage getJIPSMessage(String id) {

    JIPSMessage message = JIPSConstants.JIPS_MESSAGES.get(id);

    if (message != null)
      return message;

    return null;
  }

  private static JIPSMessage createUnknownMessage(JIPSException je) {
    return new JIPSMessage("unknown", je.getExceptionMessage());
  }

  /**
   * Handles the given JIPSException.
   *
   * @param je  The JIPSException to handle.
   * @param log Statusflag for logging the exception.
   */
  public static void handleException(JIPSException je, boolean log) {

    JIPSMessage jm = null;
    StringBuilder message = new StringBuilder();

    if (je.getCode() != null) {
      jm = getJIPSMessage(je.getCode());
      if (jm == null) jm = createUnknownMessage(je);
    } else {
      jm = createUnknownMessage(je);
    }

    if (je.getFilePath() != null)
      message.append("<strong>File: </strong>").append(je.getFilePath()).append("<br /><br />");

    message.append("<strong>Class: </strong>").append(je.getLocation()[0]).append("<br />");
    message.append("<strong>Method: </strong>").append(je.getLocation()[1]).append("<br />");
    message.append("<strong>Line: </strong>").append(je.getLocation()[2]);

    if (je.isCritical())
      jm.setType(JIPSMessage.ERROR);
    else
      jm.setType(JIPSMessage.WARNING);

    MsgHandler.showDialog(je, jm, message.toString(), log);

    if (je.isCritical())
      JIPS.exitJIPS(jm.getMsg());
  }
}
