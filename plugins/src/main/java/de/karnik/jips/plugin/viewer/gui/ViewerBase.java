/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.plugin.viewer.gui;

import de.karnik.jips.common.ImageStorage;
import de.karnik.jips.plugin.viewer.common.ViewerConstants;
import de.karnik.jips.plugin.viewer.listener.ViewerErrorListener;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Viewer basic class.
 *
 * @author <a href="mailto:markus.karnik@gmail.com">Markus Karnik</a>
 * @version 1.0
 */
public class ViewerBase extends JComponent {

  private static final long serialVersionUID = 7769409920271075526L;

  /**
   * The viewer toolbar.
   */
  ViewerToolBar toolbar = null;
  /**
   * The viewer pane.
   */
  ViewerBasePanel viewerPanel = null;
  /**
   * Display mode of the viewer. Default is <i>ViewerBasePanel.SIZE_STRECHED</i>.
   *
   * @see de.karnik.jips.plugin.viewer.gui.ViewerBasePanel
   */
  @Getter
  @Setter
  int modus = ViewerConstants.SIZE_STRECHED;
  /**
   * The viewer scroll pane.
   */
  private JScrollPane jsp = null;
  /**
   * Vector to store the viewer error listener.
   */
  private Vector<ViewerErrorListener> viewerErrorListeners = new Vector<ViewerErrorListener>();
  /**
   * Scaling of the viewer. Default is 100%;
   */
  @Getter
  @Setter
  private float scale = 1.0f;

  ViewerBase() {

    setLayout(new BorderLayout());
    jsp = new JScrollPane();

    add(jsp, BorderLayout.CENTER);
    addLabel("No data available...");
  }

  /* (non-Javadoc)
   * @see java.awt.Component#paint(java.awt.Graphics)
   */
  public void paint(Graphics g) {
    this.revalidate();
    super.paint(g);
  }

  /**
   * Add a label to the image pane.
   *
   * @param text The text of the label.
   */
  private void addLabel(String text) {
    JLabel jl = new JLabel(text);

    jl.setOpaque(true);
    jl.setBackground(Color.darkGray);
    jl.setForeground(Color.white);
    jl.setVerticalAlignment(JLabel.CENTER);
    jl.setHorizontalAlignment(JLabel.CENTER);

    jsp.setAutoscrolls(false);
    jsp.setViewportView(jl);
  }

  /**
   * Set the image data.
   *
   * @param imageStorage The image data.
   */
  public void setData(ImageStorage imageStorage) {

    addLabel("Load Data...");

    viewerPanel = new ViewerImagePanel(null, modus, scale);
    jsp.setViewportView(viewerPanel);
    ((ViewerImagePanel) viewerPanel).setImg(imageStorage.getImageData());
    viewerPanel.repaint();
  }

  /**
   * Adds a viewer error listener.
   *
   * @param viewerErrorListener The ViewerErrorListener to add.
   */
  public void addViewerErrorListener(ViewerErrorListener viewerErrorListener) {
    viewerErrorListeners.add(viewerErrorListener);
  }

  /**
   * Removes a viewer error listener.
   *
   * @param viewerErrorListener Der ViewerErrorListener zum Entfernen.
   */
  public void removeViewerErrorListener(ViewerErrorListener viewerErrorListener) {
    viewerErrorListeners.remove(viewerErrorListener);
  }

}
