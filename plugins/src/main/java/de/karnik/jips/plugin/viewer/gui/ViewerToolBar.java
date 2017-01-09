/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.plugin.viewer.gui;

import de.karnik.jips.common.helper.IconFactory;
import de.karnik.jips.plugin.viewer.common.ViewerConstants;
import de.karnik.jips.plugin.viewer.listener.ToolBarListener;
import de.karnik.jips.plugin.viewer.locale.ViewerTranslator;
import jiconfont.icons.GoogleMaterialDesignIcons;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Generates the toolbar.
 *
 * @author <a href="mailto:markus.karnik@gmail.com">Markus Karnik</a>
 * @version 1.0
 */
public class ViewerToolBar extends JToolBar implements ActionListener {

  private static final long serialVersionUID = 7203007325427713398L;

  /**
   * The freeScaleButton.
   */
  @Getter
  private JButton freeScaleButton = null;
  /**
   * The strechedButton.
   */
  @Getter
  private JButton strechedButton = null;
  /**
   * The orginalSizeButton.
   */
  @Getter
  private JButton orginalSizeButton = null;
  /**
   * The rotatePlus90Button.
   */
  @Getter
  private JButton rotatePlus90Button = null;
  /**
   * The rotateMinus90Button.
   */
  @Getter
  private JButton rotateMinus90Button = null;
  /**
   * The minusButton.
   */
  @Getter
  private JButton minusButton = null;
  /**
   * The plusButtonButton.
   */
  @Getter
  private JButton plusButton = null;
  /**
   * The text field to show the scaling.
   */
  @Getter
  private JTextField sizeField = null;
  /**
   * Rotation option. <strong>true</strong> rotation is possible,
   * <strong>false</strong> otherwise.
   */
  @Getter
  @Setter
  private boolean rotate = false;
  /**
   * Strech option. <strong>true</strong> streching is possible,
   * <strong>false</strong> otherwise.
   */
  @Getter
  @Setter
  private boolean strech = false;

  /**
   * Old value of the size fiels.
   */
  private String oldTextOfSizeField = "";

  /**
   * Vector to store toolbar listener.
   */
  private Vector<ToolBarListener> toolBarListeners = new Vector<>();

  /**
   * Build the base toolbar with buttons and icons.
   */
  ViewerToolBar() {

    ViewerTranslator vt = ViewerTranslator.getInstance();

    IconFactory iconFactory = IconFactory.getInstance();

    // Load icons
    Icon rotate90ButtonIcon = iconFactory.getIcon(GoogleMaterialDesignIcons.ROTATE_RIGHT);
    Icon rotate270ButtonIcon = iconFactory.getIcon(GoogleMaterialDesignIcons.ROTATE_LEFT);
    Icon strechButtonIcon = iconFactory.getIcon(GoogleMaterialDesignIcons.ZOOM_OUT_MAP);
    Icon orginalSizeButtonIcon = iconFactory.getIcon(GoogleMaterialDesignIcons.PHOTO_SIZE_SELECT_ACTUAL);
    Icon freeScaleButtonIcon = iconFactory.getIcon(GoogleMaterialDesignIcons.PHOTO_SIZE_SELECT_LARGE);
    Icon minusButtonIcon = iconFactory.getIcon(GoogleMaterialDesignIcons.ZOOM_OUT);
    Icon plusButtonIcon = iconFactory.getIcon(GoogleMaterialDesignIcons.ZOOM_IN);

    // Add buttons
    freeScaleButton = addButton(vt.getTranslation("freeScaleButton"), freeScaleButtonIcon, this);
    strechedButton = addButton(vt.getTranslation("strechedButton"), strechButtonIcon, this);
    orginalSizeButton = addButton(vt.getTranslation("orginalSizeButton"), orginalSizeButtonIcon, this);

    addSeparator();
    minusButton = addButton(vt.getTranslation("minusButton"), minusButtonIcon, this);

    sizeField = addTextField(vt.getTranslation("sizeField"), this);
    oldTextOfSizeField = "100%";

    plusButton = addButton(vt.getTranslation("plusButton"), plusButtonIcon, this);

    addSeparator();
    rotateMinus90Button = addButton(vt.getTranslation("rotateMinus90Button"), rotate270ButtonIcon, this);
    rotatePlus90Button = addButton(vt.getTranslation("rotatePlus90Button"), rotate90ButtonIcon, this);
  }

  /**
   * Add a ToolBarListener.
   *
   * @param tbl The ToolBarListenern to add.
   */
  void addToolBarListener(ToolBarListener tbl) {
    toolBarListeners.add(tbl);
  }

  /**
   * Removes a ToolBarListener.
   *
   *  @param tbl The ToolBarListenern to remove.
   */
  public void removeToolBarListener( ToolBarListener tbl ) {
    toolBarListeners.remove(tbl);
  }

  /**
   * Create and return a button with text and an icon. Als adds the button to the toolbar.
   *
   * @param text Text of the button.
   * @param ii Icon of the button.
   * @param vtl ActionListener of the button.
   * @return The button.
   */
  private JButton addButton(String text, Icon ii, ActionListener vtl) {

    JButton button = new JButton( ii );
    button.setMargin( new Insets( 1,1,1,1 ) );

    button.addActionListener( vtl );
    button.setToolTipText(text);

    add( button );

    return button;
  }

  /**
   * Create and return a text field with text and an icon. Als adds the text field to the toolbar.

   *
   * @param text Text of the text field.
   * @param vtl Der ActionListener of the text field..
   * @return The text field..
   */
  private JTextField addTextField( String text, ActionListener vtl ) {
    JTextField textfield = new JTextField( text, 5 );
    textfield.addActionListener( vtl );
    textfield.setMaximumSize(new Dimension(50, this.getPreferredSize().height));

    add( textfield );

    return textfield;
  }

  /**
   * Check the SizeField for changes and calls the scaling functions if a change happens.
   *
   * @param scale The scaling as string with or without % symbol.
   */
  private void setScaleField( String scale ) {

    scale = removeChar( scale, '%' );
    scale = scale.trim();

    try {
      int intScaleFactor = Integer.parseInt( scale );
      float floatScaleFactor = (float) intScaleFactor / 100.0f;

      for (ToolBarListener toolBarListener : toolBarListeners) {
        toolBarListener
                .setScale(floatScaleFactor);
      }
      oldTextOfSizeField = getSizeField().getText();
    } catch ( NumberFormatException nfe ) {
      getSizeField().setText( oldTextOfSizeField );
    }
  }

  /**
   * Removes a occurrences of a character from a string.
   *
   * @param text The string to remove the characters from.
   * @param charToReplace The character that is removed.
   * @return The string without the character.
   */
  private String removeChar( String text, char charToReplace ) {

    StringBuilder tempBuf = new StringBuilder(text);

    for (int i=0; i < tempBuf.length(); i++ ) {
      if ( tempBuf.charAt( i ) == charToReplace ) {
        tempBuf.deleteCharAt( i );
      }
    }

    return tempBuf.toString();
  }

  /**
   * @param sizeFieldText The sizeField to set.
   */
  void setSizeFieldText(String sizeFieldText) {
    sizeField.setText( sizeFieldText );
  }

  public void actionPerformed( ActionEvent ae ) {

    if( ae.getSource().equals( plusButton ) ) {
      for (ToolBarListener toolBarListener : toolBarListeners) toolBarListener.increaseScale();

    } else if( ae.getSource().equals( minusButton ) ) {
      for (ToolBarListener toolBarListener : toolBarListeners) toolBarListener.decreaseScale();

    } else if( ae.getSource().equals( freeScaleButton ) ) {
      for (ToolBarListener toolBarListener : toolBarListeners)
        toolBarListener.setModus(ViewerConstants.SIZE_USER_DEFINED);

    } else if( ae.getSource().equals( strechedButton ) ) {
      for (ToolBarListener toolBarListener : toolBarListeners) toolBarListener.setModus(ViewerConstants.SIZE_STRECHED);

    } else if( ae.getSource().equals( orginalSizeButton ) ) {
      for (ToolBarListener toolBarListener : toolBarListeners) toolBarListener.setModus(ViewerConstants.SIZE_ORIGINAL);

    } else if( ae.getSource().equals( rotatePlus90Button ) ) {
      for (ToolBarListener toolBarListener : toolBarListeners) toolBarListener.rotateImage(90);

    } else if( ae.getSource().equals( rotateMinus90Button ) ) {
      for (ToolBarListener toolBarListener : toolBarListeners) toolBarListener.rotateImage(-90);

    } else {
      setScaleField( ae.getActionCommand() );
    }
  }

}
