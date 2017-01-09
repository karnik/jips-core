/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.plugin.viewer.listener;

/**
 * Interface to define the ToolBarListener.
 *
 * @author <a href="mailto:markus.karnik@gmail.com">Markus Karnik</a>
 * @version 1.0
 */
public interface ToolBarListener {

	/**
   * Sets the scaling value.
   *
   * @param newScale The new scale value.
   */
	void setScale(float newScale);

	/**
   * Increases the scale value by a defined value.
   */
  void increaseScale();

	/**
   * Decreases the scale value by a defined value.
   */
  void decreaseScale();

	/**
   * Sets the scaling mode
   *
   * @param newModus The scaling mode to set.
   */
	void setModus(int newModus);

	/**
   * Rotates the image.
   *
   * @param degree The rotation in degrees.
   */
	void rotateImage(int degree);

}
