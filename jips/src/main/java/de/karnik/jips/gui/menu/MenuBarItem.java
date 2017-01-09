/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.menu;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.helper.IconFactory;
import de.karnik.jips.gui.objects.Borders;

import javax.swing.*;
import java.awt.*;

/**
 * The MenuBarItem class contains class fields and methods for the menu bar item.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.4
 */
public class MenuBarItem extends JButton {

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 4939981727799237425L;

  /**
   * The inset of the menu bar item.
   */
  private int inset = 4;

  /**
   * The inset of the menu bar item.
   */
  private Insets insets = new Insets(inset, inset, inset, inset);

  /**
   * Constructs a new MenuBarItem (Separator) object.
   */
  public MenuBarItem() {
    super();

    setBorder(Borders.E2_BORDER);
    //setContentAreaFilled( false );
    setInset(inset * inset, 0, inset * inset, 0);
    setMargin(insets);
    setSize(new Dimension(20, 20));
  }

  /**
   * Constructs a new MenuBarItem object with the specified parameters.
   *
   * @param actionCommand  the action Command for this menu bar item
   * @param iconCodeAsText the icon for the menubaricon
   */
  public MenuBarItem(String actionCommand, String iconCodeAsText) throws JIPSException {
    this();

    Icon icon = IconFactory.getInstance().getIcon(iconCodeAsText, 20);
    if (icon == null) {
      icon = new ImageIcon();
    }

    setIcon(icon);
    setActionCommand(actionCommand);
    setToolTipText(actionCommand);
  }

  /**
   * Sets the new inset of the menu bar item.
   *
   * @param i the inset to set
   */
  public void setInset(int i) {
    inset = i;
    insets.set(inset, inset, inset, inset);
  }

  /**
   * Sets the new inset of the menu bar item.
   *
   * @param i the inset to set
   */
  public void setInset(Insets i) {
    insets = i;
  }

  /**
   * Sets the new inset of the menu bar item.
   *
   * @param top    the inset from the top.
   * @param left   the inset from the left.
   * @param bottom the inset from the bottom.
   * @param right  the inset from the right.
   */
  public void setInset(int top, int left, int bottom, int right) {
    insets.set(top, left, bottom, right);
  }
}
