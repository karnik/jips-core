/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.desk.processing;

import de.karnik.jips.JIPSExceptionHandler;
import de.karnik.jips.ProjectUIListener;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.common.helper.IconFactory;
import de.karnik.jips.gui.desk.DesktopUpdateListener;
import de.karnik.jips.gui.desk.connector.BaseConnectorUI;
import de.karnik.jips.gui.objects.inputtypes.InputType;
import de.karnik.jips.processing.BaseProcessListener;
import jiconfont.icons.GoogleMaterialDesignIcons;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

/**
 * The BaseProcess class contains class fields and methods for all graphical objects in JIPS.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.4
 */
public class BaseProcessUI extends JComponent implements MouseInputListener {

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -5144782131752909145L;
  protected InputType[] it = null;
  protected int emptyBorder = 5;
  protected BasicStroke fatStroke = new BasicStroke(2.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);
  protected BasicStroke normalStroke = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);
  protected JCheckBox jcb = new JCheckBox("test");
  /**
   * Location of the Run-Button.
   */
  protected Point bLoc1 = null;
  /**
   * Location of the Preferences-Button.
   */
  protected Point bLoc2 = null;
  /**
   * Location of the Autorun-Checkbox.
   */
  protected Point bLoc3 = null;
  /**
   * Dimension for the Run-Button.
   */
  protected Dimension bDim1 = null;
  /**
   * Dimension for the Preferences-Button.
   */
  protected Dimension bDim2 = null;
  /**
   * Dimension for the Autorun-Checkbox.
   */
  protected Dimension bDim3 = null;
  /**
   * The polygon for the triangle.
   */
  private Polygon arrow = new Polygon();
  private String name = "Process";
  private int heightHeader = 80;
  private int height = heightHeader;
  private int width = 120;
  private boolean progressBarEnabled = false;
  private float percent = 1.0f;
  private int connectorSize = 18;

  private boolean selected = false;

  private Vector<BaseProcessListener> baseProcessListeners = new Vector<BaseProcessListener>();
  private Vector<DesktopUpdateListener> desktopUpdateListeners = new Vector<DesktopUpdateListener>();
  private Vector<ProjectUIListener> projectUIListeners = new Vector<ProjectUIListener>();

  private Color processBorderColor = null;
  private Color processBGColor = null;
  private Color processButtonColor = null;

  private int originX = 0;
  private int originY = 0;

  private boolean dragging = false;

  private JIPSVariables vars;

  private Image prefIcon = null;

  private BaseProcessUI() {
  }

  public BaseProcessUI(int in, int out) throws JIPSException {
    this();
    setLayout(null);

    vars = JIPSVariables.getInstance();

    prefIcon = IconFactory.getInstance()
            .getIconAsImage(GoogleMaterialDesignIcons.SETTINGS, 18);

    addMouseListener(this);
    addMouseMotionListener(this);

    if (in > out) {
      for (int i = 0; i < in; i++)
        height += connectorSize;
    } else {
      for (int i = 0; i < out; i++)
        height += connectorSize;
    }

    setColor();

    setBounds(0, 0, width, height);

    bDim1 = new Dimension(30, 30);
    bDim2 = new Dimension(20, 20);

    bLoc1 = new Point(width / 2 - 15, heightHeader - 33);
    bLoc2 = new Point(emptyBorder + 5, emptyBorder + 22);

    buildGoArrow();
  }

  public void addInputs(ArrayList<BaseConnectorUI> inputs) throws JIPSException {

    for (int i = 0; i < inputs.size(); i++) {
      inputs.get(i).setBounds(0, (heightHeader + (i * connectorSize)) - connectorSize,
              (connectorSize - 2), (connectorSize - 2));

      inputs.get(i).refresh();
      add(inputs.get(i));
    }
  }

  public void addOutputs(ArrayList<BaseConnectorUI> outputs) throws JIPSException {
    for (int i = 0; i < outputs.size(); i++) {

      outputs.get(i).setBounds((getWidth() - connectorSize),
              (heightHeader + (i * connectorSize)) - connectorSize,
              (connectorSize - 2), (connectorSize - 2));

      outputs.get(i).refresh();
      add(outputs.get(i));

    }
  }

  public void paintComponent(Graphics g) {

    for (int i = 0; i < baseProcessListeners.size(); i++)
      baseProcessListeners.get(i).refreshGUI();

    Graphics2D g2 = (Graphics2D) g.create();

    g2.setStroke(fatStroke);

    // draw rect
    g2.setColor(processBGColor);
    g2.fillRect(emptyBorder, emptyBorder,
            (width - (2 * emptyBorder)), (height - (2 * emptyBorder)));
    g2.setColor(processBorderColor);
    g2.drawRect(emptyBorder, emptyBorder,
            (width - (2 * emptyBorder + 1)), (height - (2 * emptyBorder + 1)));

    // draw bevel
    drawBevelBorder(g2, emptyBorder + 2, emptyBorder + 2,
            width - emptyBorder - 3, height - emptyBorder - 3);

    // draw name
    g2.setFont(g2.getFont().deriveFont(Font.BOLD));
    g2.setColor(processBorderColor);
    g2.drawString(name, emptyBorder + 5, emptyBorder + 14);

    // draw run
    addRunButton(g2);

    // draw pref-button
    addPreferencesButton(g2);

    g2.setStroke(normalStroke);
    // draw statusbar
    if (progressBarEnabled) {
      g2.setColor(Color.BLACK);
      g2.fillRect(4 + emptyBorder, height - 4 - 2 * emptyBorder,
              width - 8 - 2 * emptyBorder, 6);
      g2.setColor(Color.WHITE);
      g2.fillRect(5 + emptyBorder, height - 3 - 2 * emptyBorder,
              (int) ((width - 10 - 2 * emptyBorder) * percent), 4);
    }
  }

  private void drawBevelBorder(Graphics2D g2, int x1, int y1, int x2, int y2) {
    Color saveColor = g2.getColor();

    // draw bevel
    g2.setColor(brighter(processBGColor, 30));
    g2.drawLine(x1, y1, x2, y1);
    g2.drawLine(x1, y1, x1, y2);
    g2.setColor(processBGColor.darker());
    g2.drawLine(x1, y2, x2, y2);
    g2.drawLine(x2, y1, x2, y2);

    g2.setColor(saveColor);
  }

  private Color brighter(Color c, int factor) {

    int r = c.getRed();
    int g = c.getGreen();
    int b = c.getBlue();

    if (r + factor >= 0 && r + factor <= 255) r = r + factor;
    if (g + factor >= 0 && g + factor <= 255) g = g + factor;
    if (b + factor >= 0 && b + factor <= 255) b = b + factor;

    return new Color(r, g, b, c.getAlpha());
  }

  private void buildGoArrow() {
    int temp = heightHeader - 25;

    arrow.addPoint(width / 2 - 4, temp);
    arrow.addPoint(width / 2 + 4, temp + 8);
    arrow.addPoint(width / 2 - 4, temp + 16);
  }

  public void setColor() {
    processBorderColor = vars.getColor("border_color");
    processButtonColor = vars.getColor("process_button_color");
    if (isSelected()) {
      processBGColor = vars.getColor("select_background_color");
    } else {
      processBGColor = vars.getColor("process_color");
    }
    repaint();
  }

  public void addPreferencesButton(Graphics2D g2d) {
    g2d.setColor(processButtonColor);
    g2d.fillRect(bLoc2.x, bLoc2.y, bDim2.width, bDim2.height);
    g2d.drawImage(prefIcon, bLoc2.x + 1, bLoc2.y + 1, bDim2.width - 1, bDim2.height - 1, null);
    g2d.setColor(processBorderColor);
    g2d.drawRect(bLoc2.x, bLoc2.y, bDim2.width, bDim2.height);
  }

  public void addRunButton(Graphics2D g2d) {
    g2d.setColor(processButtonColor);
    g2d.fillRect(bLoc1.x, bLoc1.y,
            bDim1.width, bDim1.height);
    g2d.setColor(processBorderColor);
    g2d.drawRect(bLoc1.x, bLoc1.y,
            bDim1.width, bDim1.height);
    g2d.fillPolygon(arrow);
  }

  /**
   * @return Returns the selected.
   */
  public boolean isSelected() {
    return selected;
  }

  /**
   * @param selected The selected to set.
   */
  public void setSelected(boolean selected) throws JIPSException {
    this.selected = selected;
    setColor();

    for (int i = 0; i < projectUIListeners.size(); i++)
      projectUIListeners.get(i).setSelected();
  }

  public void removeProjectUIListeners(ProjectUIListener listener) {
    projectUIListeners.remove(listener);
  }

  public void addProjectUIListeners(ProjectUIListener listener) {
    projectUIListeners.add(listener);
  }

  public void removeBaseProcessListeners(BaseProcessListener listener) {
    baseProcessListeners.remove(listener);
  }

  public void addBaseProcessListeners(BaseProcessListener listener) {
    baseProcessListeners.add(listener);
  }

  /**
   * Fügt einen DesktopUpdateListener hinzu.
   *
   * @param viewerErrorListener Der ViewerErrorListener, welcher hinzugefügt werde soll.
   */
  public void addDesktopUpdateListener(DesktopUpdateListener desktopUpdateListener) {
    desktopUpdateListeners.add(desktopUpdateListener);
  }

  /**
   * Entfernt einen DesktopUpdateListener.
   *
   * @param viewerErrorListener Der ViewerErrorListener zum Entfernen.
   */
  public void removeDesktopUpdateListener(DesktopUpdateListener desktopUpdateListener) {
    desktopUpdateListeners.remove(desktopUpdateListener);
  }

  // ===
  // MouseListener
  // ===

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
   */
  public void mouseClicked(MouseEvent e) {
    try {

      if (e.getButton() == MouseEvent.BUTTON1) {

        int x = e.getX();
        int y = e.getY();

        // set selected
        if (!e.isControlDown())
          for (int i = 0; i < projectUIListeners.size(); i++)
            projectUIListeners.get(i).deselectAll();

        setSelected(true);

        // fire options button
        if (x > bLoc2.x && x < bLoc2.x + bDim2.width && y > bLoc2.y && y < bLoc2.y + bDim2.height) {
          for (int i = 0; i < baseProcessListeners.size(); i++)
            baseProcessListeners.get(i).showConfigurationDialog();
        }

        // fire run button
        if (x > bLoc1.x && x < bLoc1.x + bDim1.width && y > bLoc1.y && y < bLoc1.y + bDim1.height) {
          for (int i = 0; i < baseProcessListeners.size(); i++)
            baseProcessListeners.get(i).startProcess();
        }

      }

    } catch (JIPSException je) {
      JIPSExceptionHandler.handleException(je);
    }
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
   */
  public void mousePressed(MouseEvent e) {

    try {
      // if mouse button 1 is pressed
      if (e.getButton() == MouseEvent.BUTTON1) {

        originX = e.getX();
        originY = e.getY();

        if (!e.isControlDown() && !isSelected())
          for (int i = 0; i < projectUIListeners.size(); i++)
            projectUIListeners.get(i).deselectAll();

        setSelected(true);
        dragging = true;
      }
    } catch (JIPSException je) {
      JIPSExceptionHandler.handleException(je, true);
    }
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
   */
  public void mouseDragged(MouseEvent e) {
    try {
      if (dragging) {
        for (int i = 0; i < desktopUpdateListeners.size(); i++)
          desktopUpdateListeners.get(i).updatePosition(e, originX, originY);
        try {
          Thread.sleep(5);
        } catch (InterruptedException ie) {
        }
      }
    } catch (JIPSException je) {
      JIPSExceptionHandler.handleException(je, true);
    }
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
   */
  public void mouseReleased(MouseEvent e) {
    // 	if mouse button 1 is released
    if (e.getButton() == MouseEvent.BUTTON1)
      dragging = false;
  }

  public void mouseEntered(MouseEvent e) {
    for (int i = 0; i < desktopUpdateListeners.size(); i++)
      desktopUpdateListeners.get(i).tellPosition(getLocation(), e.getX(), e.getY());
  }

  public void mouseMoved(MouseEvent e) {
    for (int i = 0; i < desktopUpdateListeners.size(); i++)
      desktopUpdateListeners.get(i).tellPosition(getLocation(), e.getX(), e.getY());
  }

  public void mouseExited(MouseEvent e) {
  }

  public int getEmtpyBorder() {
    return emptyBorder;
  }

  public void setEmtpyBorder(int emtpyBorder) {
    this.emptyBorder = emtpyBorder;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public float getPercent() {
    return percent;
  }

  public void setPercent(float percent) {

    if (percent < 0.0f || percent > 1.0f)
      this.percent = 1.0f;
    else
      this.percent = percent;

    this.repaint();
  }

  public boolean isProgressBarEnabled() {
    return progressBarEnabled;
  }

  public void setProgressBarEnabled(boolean statusBarEnabled) {
    this.progressBarEnabled = statusBarEnabled;
    this.setPercent(0.0f);
  }
}

