/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.common.helper;

import jiconfont.IconCode;
import jiconfont.icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;

/**
 * The IconFactory class wrapper the functionality of jIconFont.
 * For more details on jIconFont see <a href="http://jiconfont.github.io/">http://jiconfont.github.io/</a>.
 *
 * @author <a href="mailto:markus.karnik@gmail">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.15
 */
public class IconFactory {

  /**
   * The default icon color (Default is #000000).
   */
  public static final Color ICON_COLOR_DEFAULT = new Color(0, 0, 0);
  public static final Color ICON_COLOR_INFO = new Color(0, 183, 250);
  public static final Color ICON_COLOR_ERROR = new Color(250, 102, 0);
  public static final Color ICON_COLOR_WARNING = new Color(250, 213, 0);
  /**
   * The default icon size (Default is 20).
   */
  public static final int ICON_SIZE_DEFAULT = 20;

  private IconFactory() {
    IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
  }

  public static IconFactory getInstance() {
    return Holder.INSTANCE;
  }

  /**
   * Returns the icon corresponding to the given iconCode.
   * Size is set to ICON_SIZE_DEFAULT and color is set to ICON_COLOR_DEFAULT.
   *
   * @param iconCode The icon code.
   * @return The icon.
   */
  public Icon getIcon(IconCode iconCode) {
    return getIcon(iconCode, ICON_SIZE_DEFAULT);
  }

  /**
   * Returns the icon corresponding to the given iconCode and size.
   * The color is set to ICON_COLOR_DEFAULT.
   *
   * @param iconCode The icon code.
   * @param size     The size.
   * @return The icon.
   */
  public Icon getIcon(IconCode iconCode, float size) {
    return getIcon(iconCode, size, ICON_COLOR_DEFAULT);
  }

  /**
   * Returns the icon corresponding to the given iconCode, size and color.
   *
   * @param iconCode The icon code.
   * @param size     The size.
   * @param color    The color.
   * @return The icon.
   */
  public Icon getIcon(IconCode iconCode, float size, Color color) {
    return IconFontSwing.buildIcon(iconCode, size, color);
  }

  /**
   * Returns the icon corresponding to the given iconCode.
   * Size is set to ICON_SIZE_DEFAULT and color is set to ICON_COLOR_DEFAULT.
   *
   * @param iconCode The icon code.
   * @return The icon as image.
   */
  public Image getIconAsImage(IconCode iconCode) {
    return getIconAsImage(iconCode, ICON_SIZE_DEFAULT);
  }

  /**
   * Returns the icon corresponding to the given iconCode and size.
   * The color is set to ICON_COLOR_DEFAULT.
   *
   * @param iconCode The icon code.
   * @param size     The size.
   * @return The icon as image.
   */
  public Image getIconAsImage(IconCode iconCode, float size) {
    return getIconAsImage(iconCode, size, ICON_COLOR_DEFAULT);
  }

  /**
   * Returns the icon corresponding to the given iconCode, size and color.
   *
   * @param iconCode The icon code.
   * @param size     The size.
   * @param color    The color.
   * @return The icon as image.
   */
  public Image getIconAsImage(IconCode iconCode, float size, Color color) {
    return IconFontSwing.buildImage(iconCode, size, color);
  }

  /**
   * Returns the icon corresponding to the given iconCode.
   * Size is set to ICON_SIZE_DEFAULT and color is set to DEFAULT_ICON_COLOR.
   *
   * @param iconCodeAsString The icon code as string.
   * @return The icon.
   */
  public Icon getIcon(String iconCodeAsString) {
    return getIcon(iconCodeAsString, ICON_SIZE_DEFAULT);
  }

  /**
   * Returns the icon corresponding to the given iconCode and size.
   * The color is set to ICON_COLOR_DEFAULT.
   *
   * @param iconCodeAsString The icon code as string.
   * @param size             The size.
   * @return The icon.
   */
  public Icon getIcon(String iconCodeAsString, float size) {
    return getIcon(iconCodeAsString, size, ICON_COLOR_DEFAULT);
  }

  /**
   * Returns the icon corresponding to the given iconCode (string value), size and color.
   *
   * @param iconCodeAsString The icon code as string.
   * @param size             The size.
   * @param color            The color.
   * @return The icon.
   */
  public Icon getIcon(String iconCodeAsString, float size, Color color) {
    IconCode iconCode;
    try {
      return getIcon(GoogleMaterialDesignIcons.valueOf(iconCodeAsString), size, ICON_COLOR_DEFAULT);
    } catch (IllegalArgumentException e) {
      // TODO: show warning message
      return getIcon(GoogleMaterialDesignIcons.WEEKEND, size, ICON_COLOR_ERROR);
    }
  }

  private static class Holder {
    static final IconFactory INSTANCE = new IconFactory();
  }
}