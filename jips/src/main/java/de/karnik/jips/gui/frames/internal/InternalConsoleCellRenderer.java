/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.frames.internal;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.JIPSMessage;
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.common.helper.IconFactory;
import jiconfont.icons.GoogleMaterialDesignIcons;

import javax.swing.*;
import java.awt.*;

/**
 * The InternalConsoleCellRenderer...
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.7
 */
class InternalConsoleCellRenderer extends JLabel implements ListCellRenderer {
  /**
   *
   */
  private static final long serialVersionUID = 5960628708705156792L;

  private int iconSize = 18;

  private Icon errorIcon = null;
  private Icon infoIcon = null;
  private Icon warningIcon = null;

  private JIPSVariables vars = null;
  private IconFactory iconFactory = null;

  public InternalConsoleCellRenderer() {
    setOpaque(true);

    this.setPreferredSize(new Dimension(400, 22));

    try {
      vars = JIPSVariables.getInstance();
      iconFactory = IconFactory.getInstance();

      iconFactory.getIcon(GoogleMaterialDesignIcons.ERROR_OUTLINE, iconSize);

      infoIcon = iconFactory.getIcon(GoogleMaterialDesignIcons.INFO_OUTLINE, iconSize, IconFactory.ICON_COLOR_INFO);
      warningIcon = iconFactory.getIcon(GoogleMaterialDesignIcons.WARNING, iconSize, IconFactory.ICON_COLOR_WARNING);
      errorIcon = iconFactory.getIcon(GoogleMaterialDesignIcons.ERROR, iconSize, IconFactory.ICON_COLOR_ERROR);

    } catch (JIPSException e) {
      errorIcon = scaleIcon((ImageIcon) UIManager.getIcon("OptionPane.errorIcon"),
              iconSize);
      infoIcon = scaleIcon((ImageIcon) UIManager.getIcon("OptionPane.informationIcon"),
              iconSize);
      warningIcon = scaleIcon((ImageIcon) UIManager.getIcon("OptionPane.warningIcon"),
              iconSize);
    }
  }

  private ImageIcon scaleIcon(ImageIcon icon, int size) {
    return scaleIcon(icon, size, size);
  }

  private ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
    ImageIcon newIcon = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING));
    return newIcon;
  }

  public Component getListCellRendererComponent(JList list,
                                                Object value,
                                                int index,
                                                boolean isSelected,
                                                boolean cellHasFocus) {

    if (!(value instanceof JIPSMessage))
      return null;

    Color defaultBG;
    if ((index % 2) == 0) {
      defaultBG = Color.WHITE;
    } else {
      defaultBG = new Color(240, 240, 240);
    }

    JIPSMessage jm = (JIPSMessage) value;

    switch (jm.getType()) {
      case JIPSMessage.WARNING:
        setIcon(warningIcon);
        break;
      case JIPSMessage.INFORMATION:
        setIcon(infoIcon);
        break;
      case JIPSMessage.ERROR:
        setIcon(errorIcon);
        break;
    }

    setText(jm.getMsg());
    this.setToolTipText(jm.getHint());

    Color background;
    Color foreground;

    // check if this cell represents the current DnD drop location
    JList.DropLocation dropLocation = list.getDropLocation();
    if (dropLocation != null
            && !dropLocation.isInsert()
            && dropLocation.getIndex() == index) {

      background = Color.BLUE;
      foreground = Color.WHITE;

      // check if this cell is selected
    } else if (isSelected) {
      background = Color.BLUE;
      foreground = defaultBG;

      // unselected, and not the DnD drop location
    } else {
      background = defaultBG;
      foreground = Color.BLACK;
    }
    ;

    setBackground(background);
    setForeground(foreground);

    return this;
  }
}
