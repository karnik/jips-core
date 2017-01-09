/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.plugin.viewer.gui;

import de.karnik.jips.common.ImageStorage;
import de.karnik.jips.plugin.viewer.common.ViewerConstants;
import de.karnik.jips.plugin.viewer.listener.PanelListener;
import de.karnik.jips.plugin.viewer.listener.ToolBarListener;

import java.awt.*;

/**
 * Viewer with toolbar.
 *
 * @author <a href="mailto:markus.karnik@gmail.com">Markus Karnik</a>
 * @version 1.0
 */
public class ViewerWithToolBar extends ViewerBase implements ToolBarListener, PanelListener {

  private static final long serialVersionUID = 7082022890958365430L;

  ViewerWithToolBar() {
    super();

    toolbar = new ViewerToolBar();
    add(toolbar, BorderLayout.NORTH);
  }

  /**
   * Updates the buttons of the toolbar.
   *
   * @param modus The display mode.
   */
  private void updateButtons(int modus) {
    switch (modus) {
      case ViewerConstants.SIZE_USER_DEFINED:
        setButtonsEnabled(true);
        toolbar.getFreeScaleButton().setEnabled(false);
        break;

      case ViewerConstants.SIZE_STRECHED:
        setButtonsEnabled(true);
        toolbar.getStrechedButton().setEnabled(false);
        toolbar.getMinusButton().setEnabled(false);
        toolbar.getPlusButton().setEnabled(false);
        toolbar.getSizeField().setEditable(false);
        break;

      case ViewerConstants.SIZE_ORIGINAL:
        setButtonsEnabled(true);
        toolbar.getOrginalSizeButton().setEnabled(false);
        toolbar.getMinusButton().setEnabled(false);
        toolbar.getPlusButton().setEnabled(false);
        toolbar.getSizeField().setEditable(false);
        break;
    }

    toolbar.getRotatePlus90Button().setEnabled(toolbar.isRotate());
    toolbar.getRotateMinus90Button().setEnabled(toolbar.isRotate());

    if (!toolbar.isStrech())
      toolbar.getStrechedButton().setEnabled(false);

  }

  /**
   * En-/Disables all buttons.
   *
   * @param value <strong>true</strong> for enabling all buttons, <strong>false</strong> otherwise.
   */
  public void setButtonsEnabled(boolean value) {
    toolbar.getFreeScaleButton().setEnabled(value);
    toolbar.getStrechedButton().setEnabled(value);
    toolbar.getOrginalSizeButton().setEnabled(value);

    toolbar.getMinusButton().setEnabled(value);
    toolbar.getPlusButton().setEnabled(value);

    toolbar.getRotatePlus90Button().setEnabled(value);
    toolbar.getRotateMinus90Button().setEnabled(value);
    toolbar.getSizeField().setEditable(value);
  }

  /* (non-Javadoc)
   * @see de.karnik.viewer.gui.ViewerBase#setData(java.lang.String, byte[])
   */
  @Override
  public void setData(ImageStorage data) {
    super.setData(data);

    toolbar.addToolBarListener(this);
    viewerPanel.addPanelListener(this);

    if (viewerPanel instanceof ViewerImagePanel) {
      toolbar.setRotate(true);
      toolbar.setStrech(true);
    }

    updateButtons(modus);
  }

  public void renewSizeTextField(float newText) {
    toolbar.setSizeFieldText(Math.round(viewerPanel.getScale() * 100) + "%");
  }

  /* (non-Javadoc)
   * @see de.karnik.viewer.listener.ViewerPanelListener#setScale(float)
   */
  public void setScale(float newScale) {
    super.setScale(newScale);

    if (viewerPanel != null)
      viewerPanel.setScale(newScale);
  }

  /* (non-Javadoc)
   * @see de.karnik.viewer.listener.ViewerPanelListener#riseScale()
   */
  public void increaseScale() {
    viewerPanel.increaseScale(ViewerConstants.scaleModifier);
  }

  /* (non-Javadoc)
   * @see de.karnik.viewer.listener.ViewerPanelListener#lowerScale()
   */
  public void decreaseScale() {
    viewerPanel.decreaseScale(ViewerConstants.scaleModifier);
  }

  /* (non-Javadoc)
   * @see de.karnik.viewer.listener.ViewerPanelListener#setModus(int)
   */
  public void setModus(int newModus) {
    super.setModus(newModus);

    if (viewerPanel instanceof ViewerImagePanel) {
      ((ViewerImagePanel) viewerPanel).setModus(newModus);
      updateButtons(newModus);
    }
  }

  /* (non-Javadoc)
   * @see de.karnik.viewer.listener.ViewerImagePanelListener#rotateImage(int)
   */
  public void rotateImage(int degree) {
    if (viewerPanel instanceof ViewerImagePanel)
      ((ViewerImagePanel) viewerPanel).addRotation(degree);
  }

}