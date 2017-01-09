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

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Panel to render the image.
 *
 * @author <a href="mailto:markus.karnik@gmail.com">Markus Karnik</a>
 * @version 1.0
 */
public class ViewerImagePanel extends ViewerBasePanel implements MouseInputListener {

  private static final long serialVersionUID = -7621914058245345864L;

  /**
   * The current selection rectangle.
   */
  private Rectangle currentRect = null;
  /**
   * The selection rectancle to draw.
   */
  private Rectangle rectToDraw = null;
  /**
   * The perviously drawn selection rectangle.
   */
  private Rectangle previousRectDrawn = new Rectangle();

  /**
   * The dotted line for the selection. Offset 0.
   */
  private BasicStroke bs1 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
          1.0f, new float[]{10, 10}, 0.0f);
  /**
   * The dotted line for the selection. Offset 10.
   */
  private BasicStroke bs2 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
          1.0f, new float[]{10, 10}, 10.0f);

  /**
   * The selection point. Needed to calc the scroll bar position.
   */
  private Point selectionPoint = null;

  /**
   * The image to show.
   */
  private BufferedImage img = null;

  /**
   * The image width.
   */
  @Getter
  private int imgWidth = 0;
  /**
   * The image height.
   */
  @Getter
  private int imgHeight = 0;

  /**
   * The radian value as double value. Default is 0.0d.
   */
  private double rad = 0.0d;

  /**
   * The x asis scrollbar postion.
   */
  private float scrollXPos = 0.0f;
  /**
   * The y asis scrollbar postion.
   */
  private float scrollYPos = 0.0f;

  /**
   * Indicator when a selection was made (true).
   */
  private boolean selectionZoom = false;
  /**
   * Indicator when a selection is currently made (true).
   */
  private boolean activeSelection = false;

  /**
   * Counter to reduce repaints when scrolling.
   */
  private int selectionRunCounter = 0;

  private ViewerImagePanel() {
    setDoubleBuffered(true);
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  /**
   * Creates a standard ViewerImagePanel to show a picture.
   *
   * @param myImage The image to show.
   * @param modus   The display mode.
   * @param scale   The scale. (1.0f = 100%)
   * @see de.karnik.jips.plugin.viewer.gui.ViewerBasePanel
   */
  ViewerImagePanel(BufferedImage myImage, int modus, float scale) {
    this();

    setModus(modus);
    setScale(scale);

    if (myImage != null) {
      img = myImage;
      imgWidth = img.getWidth();
      imgHeight = img.getHeight();
    }
  }

  /* (non-Javadoc)
   * @see java.awt.Component#paint(java.awt.Graphics)
   */
  @Override
  public void paintComponent(Graphics g) {

    int xPos = 0;
    int yPos = 0;

    int viewPortWidth = getParent().getWidth();
    int viewPortHeight = getParent().getHeight();

    int newImageWidth = 0;
    int newImageHeight = 0;

    // switch display mode
    switch (modus) {

      // scale image for mode SIZE_ORGINAL
      case ViewerConstants.SIZE_ORIGINAL:
        newImageWidth = imgWidth;
        newImageHeight = imgHeight;
        scale = 1.0f;
        break;

      // scale image for mode SIZE_STRECHED
      case ViewerConstants.SIZE_STRECHED:

        float dynWidthScale = 0.0f;
        float dynHeightScale = 0.0f;

        // check and resize images with rotation differnt to 0, 90, 180, 270 and 360
        if (rad == Math.toRadians(0.0d) || rad == Math.toRadians(180.0d) || rad == Math.toRadians(360.0d)) {
          dynWidthScale = ((float) viewPortWidth / (float) imgWidth);
          dynHeightScale = ((float) viewPortHeight / (float) imgHeight);
        } else if (rad == Math.toRadians(90.0d) || rad == Math.toRadians(270.0d)) {
          dynWidthScale = ((float) viewPortHeight / (float) imgWidth);
          dynHeightScale = ((float) viewPortWidth / (float) imgHeight);
        }

        if (dynHeightScale > dynWidthScale) {
          newImageWidth = (int) ((float) imgWidth * dynWidthScale);
          newImageHeight = (int) ((float) imgHeight * dynWidthScale);
          scale = dynWidthScale;
        } else {
          newImageWidth = (int) ((float) imgWidth * dynHeightScale);
          newImageHeight = (int) ((float) imgHeight * dynHeightScale);
          scale = dynHeightScale;
        }

        break;

      // scale image for mode SIZE_USER_DEFINED
      case ViewerConstants.SIZE_USER_DEFINED:
        newImageWidth = (int) ((float) imgWidth * scale);
        newImageHeight = (int) ((float) imgHeight * scale);

        // avoid setting the scroll bar position on every repaint
        if (selectionZoom) {
          selectionRunCounter++;
          if (selectionRunCounter > 1) {
            setScrollPosition();
            super.repaint();
            selectionZoom = false;
            selectionRunCounter = 0;
          }
        }

        break;
    }

    // draw pane background
    drawBackground(g);

    // center horizontally when the image width is smaller than the viewports
    if (newImageWidth <= viewPortWidth || modus == ViewerConstants.SIZE_STRECHED)
      xPos = (viewPortWidth - newImageWidth) / 2;
    // center vertically when the image height is smaller than the viewports
    if (newImageHeight <= viewPortHeight || modus == ViewerConstants.SIZE_STRECHED)
      yPos = (viewPortHeight - newImageHeight) / 2;

    // roate image when rotation is != 0
    if (rad != Math.toRadians(0.0d) && rad != Math.toRadians(360.0d))
      ((Graphics2D) g).rotate(rad, viewPortWidth / 2, viewPortHeight / 2);

    // draw the grid behind the image (ony visible when image has alpha)
    drawGrid(g, 10, xPos, yPos, newImageWidth, newImageHeight);

    // draw the image
    g.drawImage(img, xPos, yPos, newImageWidth, newImageHeight, this);

    g.setColor(ViewerConstants.IMAGE_BORDER_COLOR);
    g.drawRect(xPos, yPos, newImageWidth, newImageHeight);

    // draw selection rectangle
    if (currentRect != null && modus == ViewerConstants.SIZE_USER_DEFINED) {

      Graphics2D g2d = (Graphics2D) g;

      g2d.setStroke(bs1);
      g.setColor(ViewerConstants.SELECTION_COLOR_OUTER);
      g.drawRect(rectToDraw.x, rectToDraw.y,
              rectToDraw.width - 1, rectToDraw.height - 1);

      g2d.setStroke(bs2);
      g.setColor(ViewerConstants.SELECTION_COLOR_INNER);
      g.drawRect(rectToDraw.x, rectToDraw.y,
              rectToDraw.width - 1, rectToDraw.height - 1);
    }

    for (PanelListener pl : panelListeners) {
      pl.renewSizeTextField(scale);
    }
  }

  /**
   * Renders the grid that is shown behind the image (only visible when image has some alpha values).
   *
   * @param g         The graphics to paint on.
   * @param gridSpace The sitze of the grid elements.
   * @param xPos      The xPos to start painting.
   * @param yPos      The yPos to start painting.
   * @param width     The with of the grid.
   * @param height    The height of the grid.
   */
  private void drawGrid(Graphics g, int gridSpace, int xPos, int yPos, int width, int height) {

    int y = yPos;
    int x = xPos;

    int lineCount = 0;

    int totalHeight = height + y;
    int totalWidth = width + x;

    while (y < totalHeight) {

      x = xPos;
      // switch starting color on changing rows
      if (lineCount % 2 == 0)
        g.setColor(ViewerConstants.GRID_LIGHT);
      else
        g.setColor(ViewerConstants.GRID_DARK);

      while (x < totalWidth) {

        // switch color of every column
        if (g.getColor().equals(ViewerConstants.GRID_LIGHT)) {
          g.setColor(ViewerConstants.GRID_DARK);
        } else {
          g.setColor(ViewerConstants.GRID_LIGHT);
        }

        // check and substract overflow in x and y axis
        int xSubstract = 0;
        if ((x + gridSpace) > totalWidth)
          xSubstract = (x + gridSpace) - totalWidth;

        int ySubstract = 0;
        if ((y + gridSpace) > ySubstract)
          ySubstract = (y + gridSpace) - totalHeight;

        // paint rectangle
        g.fillRect(x, y, gridSpace - xSubstract, gridSpace - ySubstract);

        x += gridSpace;
      }

      lineCount++;
      y += gridSpace;
    }

  }

  /**
   * Draws a dark grey background to the whole viewer pane.
   *
   * @param g Das Graphics-Object, auf das gezeichnet werden soll
   */
  private void drawBackground(Graphics g) {

    int width = getParent().getWidth();
    int height = getParent().getHeight();

    g.setColor(ViewerConstants.PANE_BACKGROUND);

    if (width < imgWidth * scale)
      width = (int) (imgWidth * scale);

    if (height < imgHeight * scale)
      height = (int) (imgHeight * scale);

    g.fillRect(0, 0, width, height);
    setSize(width, height);
  }

  /**
   * Updates the selection rectangle.
   *
   * @param compWidth  Width of the component to draw on.
   * @param compHeight Height of the component to draw on.
   */
  private void updateDrawableRect(int compWidth, int compHeight) {
    int x = currentRect.x;
    int y = currentRect.y;
    int width = currentRect.width;
    int height = currentRect.height;

    // avoid negative values
    if (width < 0) {
      width = 0 - width;
      x = x - width + 1;
      if (x < 0) {
        width += x;
        x = 0;
      }
    }

    if (height < 0) {
      height = 0 - height;
      y = y - height + 1;
      if (y < 0) {
        height += y;
        y = 0;
      }
    }

    // prevent overflows (selection bigger than pane)
    if ((x + width) > compWidth) {
      width = compWidth - x;
    }
    if ((y + height) > compHeight) {
      height = compHeight - y;
    }

    // save and update values
    if (rectToDraw != null) {
      previousRectDrawn.setBounds(
              rectToDraw.x, rectToDraw.y,
              rectToDraw.width, rectToDraw.height);
      rectToDraw.setBounds(x, y, width, height);
    } else {
      rectToDraw = new Rectangle(x, y, width, height);
    }
  }

  /**
   * Adds the given value to the current rotation and repaints the image.
   * If rotation value gets bigger then 360 or smaller then 0, the
   * function will substract or add 360 automatically.
   *
   * @param rad The radiant to add.
   */
  private void addRotation(double rad) {

    double newRad = this.rad + rad;

    if (newRad > Math.toRadians(360.0d))
      newRad = newRad - Math.toRadians(360.0d);

    if (newRad < Math.toRadians(0.0d))
      newRad = newRad + Math.toRadians(360.0d);

    this.rad = newRad;
    repaint();
  }

  /**
   * Adds the given value to the current rotation and repaints the image.
   * If rotation value gets bigger then 360 or smaller then 0, the
   * function will substract or add 360 automatically.
   *
   * @param degree The rotation to add in degrees.
   */
  void addRotation(int degree) {
    addRotation(Math.toRadians((double) degree));
  }

  /**
   * Updates the size of the selection rectangle and repaints the selection.
   *
   * @param e The current mouse event.
   */
  private void updateSize(MouseEvent e) {
    updateSize(e, true);
  }

  /**
   * Updates the size of the selection rectangle and repaints (optional) the selection.
   *
   * @param e The current mouse event.
   */
  private void updateSize(MouseEvent e, boolean repaint) {
    currentRect.setSize(e.getX() - currentRect.x, e.getY() - currentRect.y);
    updateDrawableRect(getWidth(), getHeight());
    Rectangle totalRepaint = rectToDraw.union(previousRectDrawn);
    if (repaint)
      repaint(totalRepaint.x, totalRepaint.y, totalRepaint.width, totalRepaint.height);
  }

  /**
   * Returns the viewport size.
   *
   * @return The size of the viewport.
   */
  private Rectangle getViewportSize() {
    int viewPortWidth = getParent().getWidth();
    int viewPortHeight = getParent().getHeight();

    return new Rectangle(viewPortWidth, viewPortHeight);
  }

  /* (non-Javadoc)
   * @see java.awt.Component#getPreferredSize()
   */
  public Dimension getPreferredSize() {
    return new Dimension(getWidth(), getHeight());
  }

  /* (non-Javadoc)
   * @see java.awt.Component#getMinimumSize()
   */
  public Dimension getMinimumSize() {
    return getPreferredSize();
  }

  /**
   * Sets a new image to display.
   *
   * @param image the image to display.
   */
  void setImg(BufferedImage image) {
    img = image;
    imgWidth = img.getWidth();
    imgHeight = img.getHeight();
    setSize(getParent().getWidth(), getParent().getHeight());
  }

  /**
   * Sets the scroll bar position.
   *
   * @param xPos x as float.
   * @param yPos y as float.
   */
  private void setScrollPosition(float xPos, float yPos) {

    Container cont;

    try {
      cont = getParent().getParent();
    } catch (NullPointerException npe) {
      return;
    }

    // set scroll bar
    if (cont instanceof JScrollPane) {
      JScrollPane jsp = (JScrollPane) (getParent().getParent());

      jsp.validate();

      jsp.getHorizontalScrollBar().setValue(Math.round(xPos));
      jsp.getVerticalScrollBar().setValue(Math.round(yPos));
      jsp.revalidate();
    }
  }

  /**
   * Sets the scroll bar position (using class members scrollXPos and scrollYPos).
   */
  private void setScrollPosition() {
    setScrollPosition(scrollXPos, scrollYPos);
  }

  /**
   * Calcs the scrollbar position when scale was set by making a selection.
   *
   * @param selection The selection rectangle.
   * @param newScale  The scale.
   */
  private void calculateScrollBarPosition(Rectangle selection, float newScale) {
    if (scale < newScale && newScale > minScale && scale != maxScale) {

      if (newScale > maxScale)
        newScale = maxScale;

      setSelectPosition(selection);
      setScale(newScale);

      scrollXPos = ((float) selectionPoint.x * newScale) - (float) getViewportSize().width / 2;
      scrollYPos = ((float) selectionPoint.y * newScale) - (float) getViewportSize().height / 2;

      selectionZoom = true;
    }
  }

  /**
   * Calcs and sets the center of the given selection.
   *
   * @param selection The selection.
   */
  private void setSelectPosition(Rectangle selection) {

    // calc borders
    float verticalBorder = ((float) getWidth() - ((float) imgWidth * scale)) / 2;
    float horizontalBorder = ((float) getHeight() - ((float) imgHeight * scale)) / 2;

    // calc center of selection
    float centeredXPoint = ((float) selection.x - verticalBorder + ((float) selection.width / 2));
    float centeredYPoint = ((float) selection.y - horizontalBorder + ((float) selection.height / 2));

    // set selection point
    selectionPoint = new Point(Math.round(centeredXPoint / scale), Math.round(centeredYPoint / scale));
  }

  /* (non-Javadoc)
   * @see de.karnik.viewer.gui.ViewerBasePanel#getModus()
   */
  public int getModus() {
    return modus;
  }

  /**
   * Set display mode.
   *
   * @param newModus The display mode.
   */
  void setModus(int newModus) {
    modus = newModus;

    // if mode is original size, then resetz the scroll position.
    if (modus == ViewerConstants.SIZE_ORIGINAL)
      setScrollPosition(0, 0);

    repaint();
  }

  /* (non-Javadoc)
   * @see de.karnik.viewer.gui.ViewerBasePanel#getScale()
   */
  public float getScale() {
    return scale;
  }

  /* (non-Javadoc)
   * @see de.karnik.viewer.gui.ViewerBasePanel#setScale(float)
   */
  public void setScale(float newScale) {
    if (newScale > minScale) {
      if (newScale > maxScale) {
        scale = maxScale;
      } else {
        scale = newScale;
      }
      repaint();
    }
  }

  /* (non-Javadoc)
   * @see de.karnik.viewer.gui.ViewerBasePanel#increaseScale(float)
   */
  public void increaseScale(float newScale) {
    selectionZoom = false;
    setScale(scale + newScale);
  }

  /* (non-Javadoc)
   * @see de.karnik.viewer.gui.ViewerBasePanel#decreaseScale(float)
   */
  public void decreaseScale(float newScale) {
    selectionZoom = false;
    setScale(scale - newScale);
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
   */
  public void mousePressed(MouseEvent e) {

    if (modus == ViewerConstants.SIZE_USER_DEFINED
            && e.getButton() == MouseEvent.BUTTON1) {
      int x = e.getX();
      int y = e.getY();

      currentRect = new Rectangle(x, y, 0, 0);
      updateDrawableRect(getWidth(), getHeight());

      activeSelection = true;
    }
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
   */
  public void mouseDragged(MouseEvent e) {
    if (modus == ViewerConstants.SIZE_USER_DEFINED && activeSelection)
      updateSize(e);
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
   */
  public void mouseReleased(MouseEvent e) {
    if (modus == ViewerConstants.SIZE_USER_DEFINED
            && e.getButton() == MouseEvent.BUTTON1) {
      int x = e.getX();
      int y = e.getY();
      updateSize(e);

      // calc scaling for zoom
      Rectangle selection = rectToDraw.union(rectToDraw);

      if (((float) selection.width * (float) selection.height) > 0) {

        // set new scaling
        float widthScale = (float) getViewportSize().width / ((float) selection.width / scale);
        float heightScale = (float) getViewportSize().height / ((float) selection.height / scale);

        if (heightScale < widthScale)
          calculateScrollBarPosition(selection, heightScale);

        if (widthScale < heightScale)
          calculateScrollBarPosition(selection, widthScale);
      }

      currentRect = new Rectangle(x, y, 0, 0);
      updateDrawableRect(getWidth(), getHeight());
      activeSelection = false;
    }
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
   */
  public void mouseClicked(MouseEvent e) {
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
   */
  public void mouseEntered(MouseEvent e) {
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
   */
  public void mouseExited(MouseEvent e) {
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
   */
  public void mouseMoved(MouseEvent e) {
  }

}