/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.plugin.viewer.listener;

// TODO: CHECK IF NEEDED

/**
 * Interface to define the ViewerErrorListener.
 *
 * @author <a href="mailto:markus.karnik@gmail.com">Markus Karnik</a>
 * @version 1.0
 */
public interface ViewerErrorListener {
  /**
   * Error handling for scalling errors.
   *
   * @param msg The error message.
   */
  void scaleErrorOccurred(String msg);

  /**
   * Error handling for file type errors.
   *
   * @param msg The error message.
   */
  void filetypeErrorOccurred(String msg);
}
