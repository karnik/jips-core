/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.frames;

import de.karnik.jips.CommonFunctions;
import de.karnik.jips.MsgHandler;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.lang.Translator;
import de.karnik.jips.gui.objects.Borders;
import de.karnik.jips.gui.objects.ConfigMaskReader;
import de.karnik.jips.gui.objects.inputtypes.InputType;
import de.karnik.jips.processing.BaseProcess;
import jiconfont.icons.GoogleMaterialDesignIcons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * The InputMask class contains class fields and methods for the input masks.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.5
 */
public class ConfigMask extends BaseSubFrame implements ActionListener {

  private static final long serialVersionUID = 6168725484293416447L;

  /**
   *
   */
  private Vector<InputType> inputType = null;

  /**
   *
   */
  private BaseProcess baseProcess = null;

  /**
   * Constructs a new InputMask object.
   *
   * @param name the name of the frame
   */
  public ConfigMask(String name, BaseProcess bp) throws JIPSException {
    super(Translator.getInstance().getTranslation("plugin_config"), 600, 0);

    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());
    setResizable(false);

    baseProcess = bp;

    ConfigMaskReader cmr = new ConfigMaskReader(baseProcess.getConfiguration("configFile"), baseProcess);
    inputType = cmr.getIt();

    Icon icon = iconFactory.getIcon(GoogleMaterialDesignIcons.EXTENSION, 64);

    if (icon == null) {
      icon = new ImageIcon();
    }

    setDescription(bp.getConfiguration("name"), trans.getTranslation("desc_plugin_config"), icon);

    generateAll();
    generateButtons();

    pack();
    setLocation(CommonFunctions.getMiddleOfScreen(getWidth(), getHeight()));
    setVisible(true);
  }

  private void generateAll() {
    addTab2Pane(trans.getTranslation("preferences"), generateMask(), Borders.E5_BORDER);
    addTab2Pane(trans.getTranslation("about") + " " + baseProcess.getConfiguration("name"), generateAbout(), Borders.E5_BORDER);

  }

  private JPanel generateMask() {
    JPanel jp = new JPanel(gbl);

    for (InputType anInputType : inputType) addInputItem(jp, anInputType);

    return jp;
  }

  private JPanel generateAbout() {
    JPanel jp = new JPanel(gbl);
    jp.setBorder(Borders.E3_BORDER);

    // 															  x  y  w  h   wx    wy
    goh.addComponent(jp, gbl, goh.getBoldLabel("provider", true, ":"), 0, 0, 2, 1, 3.0, 3.0);
    goh.addComponent(jp, gbl, goh.getLabel(baseProcess.getConfiguration("provider-name"), false, ""), 2, 0, 2, 1, 3.0, 3.0);
    goh.addComponent(jp, gbl, goh.getBoldLabel("version", true, ":"), 0, 1, 2, 1, 3.0, 3.0);
    goh.addComponent(jp, gbl, goh.getLabel(baseProcess.getConfiguration("version"), false, ""), 2, 1, 2, 1, 3.0, 3.0);
    goh.addComponent(jp, gbl, goh.getBoldLabel("contact", true, ":"), 0, 2, 2, 1, 3.0, 3.0);
    goh.addComponent(jp, gbl, goh.getLabel(baseProcess.getConfiguration("contact"), false, ""), 2, 2, 2, 1, 3.0, 3.0);
    goh.addComponent(jp, gbl, goh.getBoldLabel("website", true, ":"), 0, 3, 2, 1, 3.0, 3.0);
    goh.addComponent(jp, gbl, goh.getLabel(baseProcess.getConfiguration("url"), false, ""), 2, 3, 2, 1, 3.0, 3.0);

    goh.addComponent(jp, gbl, goh.getLabel("", false, ""), 0, 4, 4, 1, 20.0, 20.0);


    return jp;
  }

  private void addInputItem(JPanel jp, InputType it) {
    goh.addComponent(jp, gbl, it,
            it.getComponentLocation(),
            it.getComponentDimension(),
            GridBagConstraints.WEST,
            GridBagConstraints.BOTH);
  }

  private void generateButtons() {
    JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    okButton = goh.getButton("ok", true, this, null);
    cancelButton = goh.getButton("cancel", true, this, null);

    jp.add(okButton);
    jp.add(cancelButton);

    add(jp, BorderLayout.SOUTH);
  }

  private void okButtonPressed() {

    for (InputType anInputType : inputType) {

      if (anInputType.getId() != null)
        baseProcess.setConfiguration(anInputType.getId(),
                anInputType.getValue());
    }
    cp = null;
    cancelButtonPressed();
  }

  /* (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(ActionEvent e) {
    if (vars.debugMode) MsgHandler.debugMSG("Action: " + e.getActionCommand(), true);

    if (e.getActionCommand().equals(okButton.getText())) {
      okButtonPressed();
    } else if (e.getActionCommand().equals(cancelButton.getText())) {
      cancelButtonPressed();
    }

  }
}
