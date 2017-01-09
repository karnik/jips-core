/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.plugin.viewer.common;

import java.awt.*;

/**
 * Interface to define the viewer config parameters.
 *
 * @author <a href="mailto:markus.karnik@gmail.com">Markus Karnik</a>
 * @version 1.0
 */
public interface ViewerConstants {

  /**
   * Value to set original size.
   */
  int SIZE_ORIGINAL = 0;
  /**
   * Value to set streched value (window size).
   */
  int SIZE_STRECHED = 1;
  /**
   * Value to use custom size.
   */
  int SIZE_USER_DEFINED = 2;
  /**
   * The value to increase/decrease the scaling when calling decreaseScale() or increaseScale().
   */
  float scaleModifier = 0.10f;

  /**
   * The light value of the grid behind the shown image.
   */
  Color GRID_LIGHT = new Color(250, 250, 250);
  /**
   * The dark value of the grid behind the shown image.
   */
  Color GRID_DARK = new Color(200, 200, 200);
  /**
   * The view pane background color.
   */
  Color PANE_BACKGROUND = new Color(150, 150, 150);
  /**
   * The image border color.
   */
  Color IMAGE_BORDER_COLOR = new Color(0, 0, 0);
  /**
   * Outer selection color.
   */
  Color SELECTION_COLOR_OUTER = new Color(0, 0, 0);
  /**
   * Inner selection color.
   */
  Color SELECTION_COLOR_INNER = new Color(255, 255, 255);
}
