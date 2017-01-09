/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.gui.frames;

import de.karnik.jips.JIPSExceptionHandler;
import de.karnik.jips.JIPSProjectDataModel;
import de.karnik.jips.MsgHandler;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.ProjectListener;
import de.karnik.jips.common.config.JIPSConstants;
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.common.helper.IconFactory;
import de.karnik.jips.common.lang.Translator;
import de.karnik.jips.gui.objects.Borders;
import de.karnik.jips.gui.objects.InformationLabel;
import jiconfont.icons.GoogleMaterialDesignIcons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

/**
 * The NewProjectConfigurationFrame...
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.7
 */
public class NewProjectConfigurationFrame extends BaseSubFrame implements ActionListener, KeyListener {

  private static final long serialVersionUID = 1L;


  /**
   * DataModel to handle Project-Properties.
   */
  private JIPSProjectDataModel jpdm;
  private ProjectListener pl = null;

  private JTextField projectName = null;
  private JTextArea projectDesc = null;
  private InformationLabel iLabel;
  private JComboBox inputsBox = null;
  private JComboBox outputsBox = null;
  private JComboBox viewersBox = null;

  private String workspace;

  private JIPSVariables vars = null;
  private IconFactory iconFactory;

  public NewProjectConfigurationFrame(JIPSProjectDataModel jpdm) throws JIPSException {
    super(Translator.getInstance().getTranslation("new_project"),
            400, 300);

    vars = JIPSVariables.getInstance();
    iconFactory = IconFactory.getInstance();

    this.jpdm = jpdm;

    workspace = vars.getWorkspaceDir();

    init();
    generateAll();
    this.setVisible(true);
  }

  /**
   * Initializes the frame.
   */
  private void init() {
    setResizable(false);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    iLabel = new InformationLabel();
  }

  /**
   * Generates the content of the frame.
   */
  private void generateAll() throws JIPSException {

    Icon icon = iconFactory.getIcon(GoogleMaterialDesignIcons.LIGHTBULB_OUTLINE, 64, IconFactory.ICON_COLOR_INFO);

    if (icon == null) {
      icon = new ImageIcon();
    }

    this.setDescription(trans.getTranslation("create_project"),
            trans.getTranslation("desc_create_project"),
            icon);

    addTab2Pane(trans.getTranslation("general"), generateGeneralTab(), Borders.E5_BORDER);
    addTab2Pane(trans.getTranslation("processes"), generateInitialProcessesTab(), Borders.E5_BORDER);

    generateButtons();
  }

  /**
   * Generates and returns general preferences tab panel.
   *
   * @return a panel with the general preferences.
   */
  private JPanel generateGeneralTab() throws JIPSException {

    // =======================================================
    // set baselayout
    // =======================================================
    JPanel jp = new JPanel(gbl);
    jp.setBorder(Borders.E3_BORDER);


    projectName = goh.getTextField(jpdm.getProjectName(), 40, false, null);
    projectName.addKeyListener(this);
    projectDesc = new JTextArea();
    //      								 	 							    x  y  w  h   wx    wy
    goh.addComponent(jp, gbl, goh.getBoldLabel("projectname", true, ":"), 0, 0, 6, 1, 3.0, 3.0);
    goh.addComponent(jp, gbl, projectName, 0, 1, 6, 1, 3.0, 3.0);
    goh.addComponent(jp, gbl, goh.getBoldLabel("projectdesc", true, ":"), 0, 2, 6, 1, 3.0, 3.0);
    goh.addComponent(jp, gbl, goh.getTextArea("", 2, 40, true, projectDesc), 0, 3, 6, 3, 3.0, 2.0);
    goh.addComponent(jp, gbl, goh.getBoldLabel("", false, ""), 0, 4, 6, 1, 20.0, 20.0);

    return jp;
  }

  /**
   * Generates and returns general preferences tab panel.
   *
   * @return a panel with the general preferences.
   */
  private JPanel generateInitialProcessesTab() {

    // =======================================================
    // set baselayout
    // =======================================================
    JPanel jp = new JPanel(gbl);
    jp.setBorder(Borders.E3_BORDER);

    String values[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    inputsBox = goh.getComboBox(values, 1, false, this);
    outputsBox = goh.getComboBox(values, 0, false, this);
    viewersBox = goh.getComboBox(values, 1, false, this);
    //      								 	 							    x  y  w  h   wx    wy
    goh.addComponent(jp, gbl, goh.getBoldLabel("inputs", true, ":"), 0, 0, 2, 1, 3.0, 3.0);
    goh.addComponent(jp, gbl, inputsBox, 2, 0, 4, 1, 3.0, 3.0);
    goh.addComponent(jp, gbl, goh.getBoldLabel("outputs", true, ":"), 0, 1, 2, 1, 3.0, 3.0);
    goh.addComponent(jp, gbl, outputsBox, 2, 1, 4, 1, 3.0, 3.0);
    goh.addComponent(jp, gbl, goh.getBoldLabel("viewers", true, ":"), 0, 2, 2, 1, 3.0, 3.0);
    goh.addComponent(jp, gbl, viewersBox, 2, 2, 4, 1, 3.0, 3.0);
    goh.addComponent(jp, gbl, goh.getBoldLabel("", false, ""), 0, 3, 6, 1, 20.0, 20.0);

    return jp;
  }

  private void generateButtons() {
    JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    okButton = goh.getButton("ok", true, this, null);
    cancelButton = goh.getButton("cancel", true, this, null);

    iLabel.setPreferredSize(new Dimension(230, 20));

    jp.add(iLabel);
    jp.add(okButton);
    jp.add(cancelButton);

    add(jp, BorderLayout.SOUTH);
  }

  /**
   * Invokes functions when the ok button is pressed.
   */
  private void okButtonPressed() throws JIPSException {

    jpdm.setProjectName(projectName.getText());
    jpdm.setProjectDescription(projectDesc.getText());

    if (pl != null)
      pl.projectDataEntered();

    cancelButtonPressed();
  }

  public int getInitialViewerCount() {
    return viewersBox.getSelectedIndex();
  }

  public int getInitialInputCount() {
    return inputsBox.getSelectedIndex();
  }

  public int getInitialOutputCount() {
    return outputsBox.getSelectedIndex();
  }

  public void actionPerformed(ActionEvent e) {
    if (vars.debugMode) MsgHandler.debugMSG("Action: " + e.getActionCommand(), true);

    try {
      if (e.getActionCommand().equals(okButton.getText())) {
        okButtonPressed();
      } else if (e.getActionCommand().equals(cancelButton.getText())) {
        cancelButtonPressed();
      }

    } catch (JIPSException je) {
      JIPSExceptionHandler.handleException(je);
    }

  }

  public void removeProjectListener() {
    pl = null;
  }

  public void setProjectListener(ProjectListener pl) {
    this.pl = pl;
  }

  public void projectNameExists() {
    iLabel.setData(iconFactory.getIcon(GoogleMaterialDesignIcons.ERROR_OUTLINE, 20, IconFactory.ICON_COLOR_WARNING), trans.getTranslation("project_exists"));
    if (okButton != null) okButton.setEnabled(false);
    this.repaint();
  }

  public void projectNameEmpty() {
    iLabel.setData(iconFactory.getIcon(GoogleMaterialDesignIcons.ERROR_OUTLINE, 20, IconFactory.ICON_COLOR_WARNING), trans.getTranslation("project_name_empty"));
    if (okButton != null) okButton.setEnabled(false);
    this.repaint();
  }


  public void projectNameOK() {
    iLabel.clearData();
    if (okButton != null) okButton.setEnabled(true);
    this.repaint();
  }

  /* (non-Javadoc)
   * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
   */
  public void keyReleased(KeyEvent arg0) {

    if (projectName.getText().length() == 0) {
      projectNameEmpty();
      return;
    }

    if (new File(workspace + JIPSConstants.SYSTEM_FILE_SEPERATOR + projectName.getText()).exists()) {
      projectNameExists();
    } else {
      projectNameOK();
    }
  }

  /* (non-Javadoc)
   * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
   */
  public void keyPressed(KeyEvent ke) {
  }

  /* (non-Javadoc)
   * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
   */
  public void keyTyped(KeyEvent k) {
  }

}
