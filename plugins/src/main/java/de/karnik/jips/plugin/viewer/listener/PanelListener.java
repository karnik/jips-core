/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.plugin.viewer.listener;

/**
 * Interface to define the PanelListener.
 *
 * @author <a href="mailto:markus.karnik@gmail.com">Markus Karnik</a>
 * @version 1.0
 */
public interface PanelListener {

  /**
   * Renews/sets the value of the scaling text fild.
   *
   * @param newText The text to set.
   */
  void renewSizeTextField(float newText);
}
