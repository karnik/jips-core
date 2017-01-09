/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.frames;

import de.karnik.jips.JIPSExceptionHandler;
import de.karnik.jips.MsgHandler;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.gui.FrameHelper;
import de.karnik.jips.gui.objects.Borders;
import jiconfont.icons.GoogleMaterialDesignIcons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * The AboutFrame class contains class fields and methods for the preferences frame of jips.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.2
 */
public class PrefFrame extends BaseSubFrame implements ActionListener,
        ItemListener {

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -8919992546634768147L;

  /**
   * The general preferences panel.
   */
  private JPanel generalPane = null;
  /**
   * The button panel.
   */
  private JPanel buttonPane = null;
  /**
   * The array with the available languages.
   */
  private String[][] availableLanguages = null;

  /**
   * The acutal theme.
   */
  private int theme;
  /**
   * Indicates if the status of the frame has changed.
   */
  private boolean stateChanged = false;

  /**
   * Constructs a new PrefFrame object.
   *
   * @param name the name of the frame
   */
  public PrefFrame(String name) throws JIPSException {

    super(name, 500, 400);
    init();
    generateAll();
    setVisible(true);
  }

  /**
   * Initializes the frame.
   */
  private void init() {

    setResizable(false);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    theme = vars.getLookAndFeel();
    availableLanguages = trans.getAvailableLanguages();
  }

  /**
   * Generates the content of the frame.
   */
  private void generateAll() throws JIPSException {

    Icon icon = iconFactory.getIcon(GoogleMaterialDesignIcons.SETTINGS, 64);

    if (icon == null) {
      icon = new ImageIcon();
    }

    this.setDescription(this.name, trans.getTranslation("desc_config_jips"), icon);

    addTab2Pane(trans.getTranslation("general"), generateGeneralTab(), Borders.E5_BORDER);
    addTab2Pane(trans.getTranslation("language"), generateLanguageTab(), Borders.E5_BORDER);
    addTab2Pane(trans.getTranslation("appearance"), generateAppearanceTab(), Borders.E5_BORDER);

    addButtons();
  }

  /**
   * Generates and returns general preferences tab panel.
   *
   * @return a panel with the general preferences.
   */
  private JPanel generateGeneralTab() {

    // =======================================================
    // set baselayout
    // =======================================================
    generalPane = new JPanel();
    generalPane.setBorder(Borders.E3_BORDER);

    return generalPane;
  }

  /**
   * Generates and returns language preferences tab panel.
   *
   * @return a panel with the language preferences.
   */
  private JPanel generateLanguageTab() {
    JPanel jp = new JPanel(gbl);

    int selected = 0;

    // get langs
    String[] langs = new String[trans.getAvailableLangCount()];
    for (int i = 0; i < availableLanguages.length; i++) {
      if (availableLanguages[i][0].equals(vars.getLanguage()))
        selected = i;
      langs[i] = availableLanguages[i][1];
    }

    // =======================================================
    // create labels and buttons
    // =======================================================

    //      								 	 						  				x  y  w  h   wx    wy
    goh.addComponent(jp, gbl, goh.getBoldLabel("desc_lang", true, ""), 0, 0, 2, 1, 1.0, 1.0);
    goh.addComponent(jp, gbl, goh.getComboBox(langs, selected, false, this), 2, 0, 2, 1, 1.0, 1.0);
    goh.addComponent(jp, gbl, goh.getLightItalicMultiLabel("extrainfo_lang", true), 0, 1, 4, 1, 1.0, 1.0);
    goh.addComponent(jp, gbl, goh.getLabel("", false, ""), 0, 2, 4, 20, 20.0, 20.0);

    jp.setBorder(Borders.E3_BORDER);

    return jp;
  }

  /**
   * Generates and returns appearance preferences tab panel.
   *
   * @return a panel with the appearance preferences.
   */
  private JPanel generateAppearanceTab() {

    // =======================================================
    // set baselayout
    // =======================================================

    JPanel jp = new JPanel(gbl);
    ButtonGroup appearanceBG = new ButtonGroup();

    // =======================================================
    // create labels and buttons
    // =======================================================

    JRadioButton nimbusThemeButton = goh.getBoldRadioButton("info_nimbustheme", true, checkTheme(JIPSVariables.NIMBUS_LAF), this);
    JRadioButton gtkThemeButton = goh.getBoldRadioButton("info_gtktheme", true, checkTheme(JIPSVariables.GTK_LAF), this);
    JRadioButton windowsThemeButton = goh.getBoldRadioButton("info_windowstheme", true, checkTheme(JIPSVariables.WINDOWS_LAF), this);

    //      								 	 										 x  y  w  h   wx    wy
    goh.addComponent(jp, gbl, goh.getBoldMultiLabel("desc_laf", true), 0, 0, 5, 1, 20.0, 20.0);
//    goh.addComponent(jp, gbl, synthThemeButton, 0, 1, 1, 1, 1.0, 1.0);
//    goh.addComponent(jp, gbl, goh.getLightItalicMultiLabel("desc_synththeme", true), 1, 1, 4, 1, 10.0, 10.0);
    goh.addComponent(jp, gbl, nimbusThemeButton, 0, 2, 1, 1, 1.0, 1.0);
    goh.addComponent(jp, gbl, goh.getLightItalicMultiLabel("desc_nimbustheme", true), 1, 2, 4, 1, 10.0, 10.0);
    goh.addComponent(jp, gbl, gtkThemeButton, 0, 3, 1, 1, 1.0, 1.0);
    goh.addComponent(jp, gbl, goh.getLightItalicMultiLabel("desc_gtktheme", true), 1, 3, 4, 1, 10.0, 10.0);
    goh.addComponent(jp, gbl, windowsThemeButton, 0, 4, 1, 1, 1.0, 1.0);
    goh.addComponent(jp, gbl, goh.getLightItalicMultiLabel("desc_windowstheme", true), 1, 4, 4, 1, 10.0, 10.0);
    goh.addComponent(jp, gbl, goh.getLabel("", false, ""), 0, 5, 5, 1, 20.0, 20.0);

    if (!vars.isGtkAvail()) gtkThemeButton.setEnabled(false);
    if (!vars.isWindowsAvail()) windowsThemeButton.setEnabled(false);

    // group the theme RadioButtons
//    appearanceBG.add(synthThemeButton);
    appearanceBG.add(nimbusThemeButton);
    appearanceBG.add(gtkThemeButton);
    appearanceBG.add(windowsThemeButton);

    jp.setBorder(Borders.E3_BORDER);

    return jp;
  }

  /**
   * Compares a theme with the actual theme.
   *
   * @param i the theme to compare
   * @return <strong>false</strong> if the themes are different
   */
  private boolean checkTheme(int i) {
    if (i == theme) return true;
    return false;
  }

  /**
   * Adds the buttons to the bottom of the frame.
   */
  private void addButtons() {

    buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    cp.add(buttonPane, BorderLayout.SOUTH);

    okButton = goh.getButton("ok", true);
    okButton.addActionListener(this);
    buttonPane.add(okButton);

    cancelButton = goh.getButton("cancel", true);
    cancelButton.addActionListener(this);
    buttonPane.add(cancelButton);

    applyButton = goh.getButton("apply", true);
    applyButton.addActionListener(this);
    applyButton.setEnabled(false);
    buttonPane.add(applyButton);
  }

  /**
   * Invokes functions when the state has changed.
   */
  private void stateChanged() {
    applyButton.setEnabled(true);
    stateChanged = true;

  }

  /**
   * Invokes functions when the apply button is pressed.
   */
  private void applyButtonPressed() throws JIPSException {
    // set the new theme
    if (vars.getLookAndFeel() != theme) {
      FrameHelper.hideAllWindows();
      FrameHelper.showIndeterminateProgressBar();
      vars.setLookAndFeel(theme);
      FrameHelper.showAllWindows();
      FrameHelper.hideIndeterminateProgressBar();
    }

    vars.saveConfig();
    applyButton.setEnabled(false);
    stateChanged = false;
  }

  /**
   * Invokes functions when the ok button is pressed.
   */
  private void okButtonPressed() throws JIPSException {
    if (applyButton.isEnabled()) applyButtonPressed();
    cancelButtonPressed();
  }

  /* (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(ActionEvent e) {

    try {

      if (vars.debugMode) MsgHandler.debugMSG("Action: " + e.getActionCommand(), true);

      // theme
      if (e.getActionCommand().equals(trans.getTranslation("info_nimbustheme"))) {
        theme = JIPSVariables.NIMBUS_LAF;
        stateChanged();
      } else if (e.getActionCommand().equals(trans.getTranslation("info_gtktheme"))) {
        theme = JIPSVariables.GTK_LAF;
        stateChanged();
      } else if (e.getActionCommand().equals(trans.getTranslation("info_windowstheme"))) {
        theme = JIPSVariables.WINDOWS_LAF;
        stateChanged();
        // lang
      } else if (e.getActionCommand().equals("comboBoxChanged")) {

        JComboBox jcb = (JComboBox) e.getSource();
        String langname = availableLanguages[jcb.getSelectedIndex()][0];

        if (!langname.equals(vars.getLanguage())) {
          vars.setLanguage(langname);
          stateChanged();
        }

        // buttons
      } else if (e.getActionCommand().equals(applyButton.getText())) {
        applyButtonPressed();
      } else if (e.getActionCommand().equals(okButton.getText())) {
        okButtonPressed();
      } else if (e.getActionCommand().equals(cancelButton.getText())) {
        cancelButtonPressed();
      }
    } catch (JIPSException je) {
      JIPSExceptionHandler.handleException(je);
    }
  }


  /* (non-Javadoc)
   * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
   */
  public void itemStateChanged(ItemEvent e) {
    if (vars.debugMode) MsgHandler.debugMSG("Event: " + e.paramString(), true);
  }

  /**
   * Returns the stateChanged.
   *
   * @return the stateChanged. <strong>true</strong> if the state has changed
   */
  public boolean isStateChanged() {
    return stateChanged;
  }
}
