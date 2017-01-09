/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.frames;


import de.karnik.jips.IO;
import de.karnik.jips.JIPSExceptionHandler;
import de.karnik.jips.MsgHandler;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.config.JIPSConstants;
import de.karnik.jips.gui.menu.MainMenu;
import de.karnik.jips.gui.objects.Borders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The AboutFrame class contains class fields and methods for the about frame of jips.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.3
 */
public class AboutFrame extends BaseSubFrame implements ActionListener {

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 7490415048817521263L;

  /**
   * The panel with the jips information.
   */
  private JPanel infoPane = null;
  /**
   * The panel with the external library information.
   */
  private JPanel licencePane = null;
  /**
   * The panel with the close button.
   */
  private JPanel buttonPane = null;

  /**
   * The path to the jips licence.
   */
  private String licence = "licences/jips-licence.txt";

  /**
   * Constructs a new AboutFrame object.
   *
   * @param name the name of the frame
   */
  public AboutFrame(String name) throws JIPSException {

    super(name, 640, 480);
    init();
    generateAll();
    setResizable(false);
    setVisible(true);
  }

  /**
   * Initializes the frame.
   */
  private void init() {
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  }

  /**
   * Generates the content of the frame.
   */
  private void generateAll() throws JIPSException {

    ImageIcon ii = IO.getImageIcon("jips-about-182x64.png");

    if (ii == null) {
      ii = new ImageIcon();
    }

    this.setDescription(this.name, trans.getTranslation("desc_about_jips"), ii);

    addTab2Pane(trans.getTranslation("info"), generateInfoTab(), Borders.E5_BORDER);
    addTab2Pane(trans.getTranslation("external_libs"), generateLicenceTab(), Borders.E5_BORDER);
    addCloseButton();
  }

  /**
   * Adds the close button to the bottom of the frame.
   */
  private void addCloseButton() {

    JButton closeButton;

    buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    cp.add(buttonPane, BorderLayout.SOUTH);

    closeButton = goh.getButton("close", true, this, null);
    buttonPane.add(closeButton);
  }

  /**
   * Generates and returns the jips information panel.
   *
   * @return a panel with the jips information
   */
  private JPanel generateInfoTab() throws JIPSException {

    infoPane = new JPanel(new BorderLayout());
    infoPane.setBorder(Borders.E3_BORDER);

    // =======================================================
    // set jipsInfo layout
    // =======================================================
    JPanel jipsInfo = new JPanel(gbl);
    jipsInfo.setBorder(BorderFactory.createTitledBorder(
            Borders.L1LG_BORDER, "JIPS"));

    ImageIcon ii = IO.getImageIcon("gplv3-127x51.png");
    JLabel jl = goh.getLabel("", false, "");
    jl.setIcon(ii);

    //      								 	 										 		x  y  w  h   wx    wy
    goh.addComponent(jipsInfo, gbl, goh.getBoldLabel("author", true, ":"), 0, 0, 2, 1, 1.0, 1.0);
    goh.addComponent(jipsInfo, gbl, goh.getLabel(JIPSConstants.JIPS_AUTHOR, false, ""), 3, 0, 6, 1, 1.0, 1.0);
    goh.addComponent(jipsInfo, gbl, goh.getButton("view_licence", true, this, licence), 9, 0, 2, 2, 1.0, 1.0);
    goh.addComponent(jipsInfo, gbl, goh.getBoldLabel("contact", true, ":"), 0, 1, 2, 1, 1.0, 1.0);
    goh.addComponent(jipsInfo, gbl, goh.getLabel(JIPSConstants.JIPS_AUTHOR_MAIL, false, ""), 3, 1, 6, 1, 1.0, 1.0);
    goh.addComponent(jipsInfo, gbl, goh.getBoldLabel("version", true, ":"), 0, 2, 2, 1, 1.0, 1.0);
    goh.addComponent(jipsInfo, gbl, goh.getLabel(JIPSConstants.JIPS_VERSION, false, ""), 3, 2, 6, 1, 1.0, 1.0);
    goh.addComponent(jipsInfo, gbl, goh.getBoldLabel("build", true, ":"), 0, 3, 2, 1, 1.0, 1.0);
    goh.addComponent(jipsInfo, gbl, goh.getLabel(JIPSConstants.JIPS_BUILD, false, ""), 3, 3, 6, 1, 1.0, 1.0);
    goh.addComponent(jipsInfo, gbl, goh.getMultiLabel("gpl1", 0, true, null), 0, 4, 10, 1, 1.0, 1.0);
    goh.addComponent(jipsInfo, gbl, jl, 9, 4, 2, 1, 1.0, 1.0);
    goh.addComponent(jipsInfo, gbl, goh.getMultiLabel("gpl2", 0, true, null), 0, 5, 10, 1, 1.0, 1.0);
    goh.addComponent(jipsInfo, gbl, goh.getLabel("", false, ""), 0, 6, 12, 1, 20.0, 20.0);

    // =======================================================
    // set sysInfo
    // =======================================================

    JPanel sysInfo = new JPanel(gbl);
    sysInfo.setBorder(BorderFactory.createTitledBorder(
            Borders.L1LG_BORDER, "System"));

    //      								 	 										 			 x  y  w  h   wx    wy
    goh.addComponent(sysInfo, gbl, goh.getBoldLabel("os_name", true, ":"), 0, 0, 1, 1, 1.0, 1.0);
    goh.addComponent(sysInfo, gbl, goh.getLabel(System.getProperty("os.name"), false, ""), 1, 0, 3, 1, 1.0, 1.0);
    goh.addComponent(sysInfo, gbl, goh.getBoldLabel("os_version", true, ":"), 0, 1, 1, 1, 1.0, 1.0);
    goh.addComponent(sysInfo, gbl, goh.getLabel(System.getProperty("os.version"), false, ""), 1, 1, 3, 1, 1.0, 1.0);
    goh.addComponent(sysInfo, gbl, goh.getBoldLabel("os_version", true, ":"), 0, 2, 1, 1, 1.0, 1.0);
    goh.addComponent(sysInfo, gbl, goh.getLabel(System.getProperty("os.arch"), false, ""), 1, 2, 3, 1, 1.0, 1.0);

    goh.addComponent(sysInfo, gbl, goh.getBoldLabel("java_vendor", true, ":"), 4, 0, 1, 1, 1.0, 1.0);
    goh.addComponent(sysInfo, gbl, goh.getLabel(System.getProperty("java.vendor"), false, ""), 5, 0, 3, 1, 1.0, 1.0);
    goh.addComponent(sysInfo, gbl, goh.getLabel(System.getProperty("java.vendor.url"), false, ""), 5, 1, 3, 1, 1.0, 1.0);
    goh.addComponent(sysInfo, gbl, goh.getBoldLabel("java_version", true, ":"), 4, 2, 1, 1, 1.0, 1.0);
    goh.addComponent(sysInfo, gbl, goh.getLabel(System.getProperty("java.version"), false, ""), 5, 2, 3, 1, 1.0, 1.0);
    goh.addComponent(sysInfo, gbl, goh.getBoldLabel("java_vmversion", true, ":"), 4, 3, 1, 1, 1.0, 1.0);
    goh.addComponent(sysInfo, gbl, goh.getLabel(System.getProperty("java.vm.version"), false, ""), 5, 3, 3, 1, 1.0, 1.0);

    goh.addComponent(sysInfo, gbl, goh.getLabel("", false, ""), 0, 4, 8, 1, 20.0, 20.0);

    // add the panels
    infoPane.add(jipsInfo, BorderLayout.CENTER);
    infoPane.add(sysInfo, BorderLayout.SOUTH);

    return infoPane;
  }

  /**
   * Generates and returns the external libraries information panel.
   *
   * @return a panel with the external library information
   */
  private JScrollPane generateLicenceTab() {

    // =======================================================
    // set baselayout
    // =======================================================
    licencePane = new JPanel(new GridLayout(3, 1));

    licencePane.setBorder(Borders.E3_BORDER);

    JScrollPane jsp = new JScrollPane(licencePane);

    JPanel jdom =
            generateLibPane("JDOM", "The JDOM Project",
                    "1.0", "http://www.jdom.org/",
                    "licences/jdom-licence.txt");

    JPanel jai =
            generateLibPane("Java Advanced Imaging Core", "aastha@dev.java.net, bpb@dev.java.net",
                    "1.1.3", "https://jai.dev.java.net/",
                    "licences/jai-licence.txt");

    JPanel jaiIO =
            generateLibPane("Java Advanced Imaging Image I/O Tools", "aastha@dev.java.net, bpb@dev.java.net",
                    "1.1.0", "https://jai-imageio.dev.java.net/",
                    "licences/jai-io-licence.txt");

    licencePane.add(jdom);
    licencePane.add(jai);
    licencePane.add(jaiIO);

    return jsp;
  }

  /**
   * Generates and returns a panel with the information for on external library.
   *
   * @param name        the name of the library
   * @param author      the author of the library
   * @param version     the version of the of the library
   * @param url         the url of the library
   * @param licenceFile the licence file of the library
   * @return with the information for on external library
   */
  private JPanel generateLibPane(String name, String author, String version,
                                 String url, String licenceFile) {
    JPanel jp = new JPanel(gbl);
    jp.setBorder(BorderFactory.createTitledBorder(
            Borders.L1LG_BORDER, name));

    //      								 	 										 x  y  w  h   wx    wy
    goh.addComponent(jp, gbl, goh.getBoldLabel("author", true, ":"), 0, 0, 1, 1, 1.0, 1.0);
    goh.addComponent(jp, gbl, goh.getLabel(author, false, ""), 1, 0, 9, 1, 1.0, 1.0);
    goh.addComponent(jp, gbl, goh.getBoldLabel("version", true, ":"), 0, 1, 1, 1, 1.0, 1.0);
    goh.addComponent(jp, gbl, goh.getLabel(version, false, ""), 1, 1, 9, 1, 1.0, 1.0);
    goh.addComponent(jp, gbl, goh.getBoldLabel("url", true, ":"), 0, 2, 1, 1, 1.0, 1.0);
    goh.addComponent(jp, gbl, goh.getLabel(url, false, ""), 1, 2, 9, 1, 1.0, 1.0);
    goh.addComponent(jp, gbl, goh.getButton("view_licence", true, this, licenceFile), 1, 3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
    goh.addComponent(jp, gbl, goh.getLabel("", false, ""), 0, 4, 10, 1, 20.0, 20.0);

    return jp;
  }

  /**
   * Excecutes the actions for the close button.
   */
  private void closeButtonPressed() {
    if (vars.debugMode) MsgHandler.debugMSGEnd("Frame: " + name, true);

    setVisible(false);
    dispose();
    MainMenu.setAll(true);
  }

  private void openTextViewerFrame(String title, String command) throws JIPSException {
    new TextViewerFrame(title, 640, 480, command);
  }

  /* (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    MsgHandler.debugMSG("Action: " + event.getActionCommand(), true);

    try {
      if (compareEvent(event, trans.getTranslation("close"))) {
        closeButtonPressed();
      } else {
        openTextViewerFrame(((JButton) event.getSource()).getText(),
                event.getActionCommand());
      }
    } catch (JIPSException je) {
      JIPSExceptionHandler.handleException(je);
    }
  }

}
