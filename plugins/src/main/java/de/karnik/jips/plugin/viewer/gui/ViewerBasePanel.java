/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.plugin.viewer.gui;

import de.karnik.jips.plugin.viewer.common.ViewerConstants;
import de.karnik.jips.plugin.viewer.listener.PanelListener;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.util.Vector;

/**
 * Abstract viewer base panel.
 *
 * @author <a href="mailto:markus.karnik@gmail.com">Markus Karnik</a>
 * @version 1.0
 */
public abstract class ViewerBasePanel extends JComponent {

  private static final long serialVersionUID = -8413659519671277979L;

  /**
   * Display mode. Default is <i>SIZE_ORIGINAL</i>.
   *
   * @see de.karnik.jips.plugin.viewer.gui.ViewerBasePanel
   */
  @Getter
  protected int modus = ViewerConstants.SIZE_ORIGINAL;
  /**
   * Value of scaling as float. Default is 1.0f.<br />
   * 1.0f is defined as 100%.
   */
  @Getter
  @Setter
  protected float scale = 1.0f;

  /**
   * Maximum scaling value.
   */
  float maxScale = 10.001f;
  /**
   * Minimum scaling value.
   */
  float minScale = 0.001f;

  /**
   * Vector to store panel listener.
   */
  Vector<PanelListener> panelListeners = new Vector<PanelListener>();


  /**
   * Adds a panel listener.
   *
   * @param pl The panel listener to add.
   */
  void addPanelListener(PanelListener pl) {
    panelListeners.add(pl);
  }

  /**
   * Removes a panel listener.
   *
   * @param pl The panel listener to remove.
   */
  void removePanelListener(PanelListener pl) {
    panelListeners.remove(pl);
  }

  /**
   * Increases the scaling by the given value.
   *
   * @param scale The value to increase.
   */
  public abstract void increaseScale(float scale);

  /**
   * Decreases the scaling by the given value.
   *
   * @param scale The value to decrease.
   */
  public abstract void decreaseScale(float scale);
}
