/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */
package de.karnik.jips.gui.dialog;

import de.karnik.jips.CommonFunctions;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.JIPSMessage;
import de.karnik.jips.common.helper.IconFactory;
import de.karnik.jips.gui.objects.Borders;
import jiconfont.icons.GoogleMaterialDesignIcons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The MessageDialog class displays debug-, warning-, error- and other messages and questions.
 * It cannot be instantiated.
 *
 * @author <a href="mailto:markus.karnik@gmail.com">Markus Karnik</a>
 * @version 1.1
 * @since v.0.0.6
 */
public class MessageDialog extends JDialog implements ActionListener {

  private static final long serialVersionUID = -7234380229044468894L;

  private boolean detailed = false;

  private int x = 600;
  private int y1 = 230;
  private int y2 = 450;

  private Icon icon = null;
  private Icon errorIcon = null;
  private Icon infoIcon = null;
  private Icon warningIcon = null;

  private JLabel informationLabel = null;
  private JPanel headerPanel = null;
  private JLabel iconLabel = null;
  private JPanel hideButtonPanel = null;
  private JPanel showButtonPanel = null;
  private JPanel javaPanel = null;
  private JPanel helpPanel = null;
  private JPanel detailedPanel = null;

  private String errorInformation = null;
  private String code = null;
  private String errorMessage = null;
  private String javaMessage = null;
  private String helpMessage = null;
  private int type = JIPSMessage.WARNING;

  @SuppressWarnings("unused")
  private MessageDialog() {
  }

  /**
   * Shows a message dialog. There a three possible types.
   *
   * @param type             The dialog type.
   * @param errorMessage     The error message.
   * @param errorInformation Additional information.
   * @param javaMessage      The detailes java Output.
   * @param helpMessage      A detailes help message.
   * @param code             The error or warning code.
   */
  public MessageDialog(int type, String errorMessage,
                       String errorInformation, String javaMessage,
                       String helpMessage, String code) {

    this.type = type;
    this.errorMessage = errorMessage;
    this.errorInformation = errorInformation;
    this.javaMessage = javaMessage;
    this.helpMessage = helpMessage;
    this.code = code;

    createIcons();

    setModal(true);
    setDialogTitle(this.code);

    detailedPanel = new JPanel(new GridLayout(3, 1));
    this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    init();
    build();
  }

  public MessageDialog(JIPSException je, JIPSMessage jm, String message) {
    this(jm.getType(), jm.getMsg(), message, je.getExceptionMessage(), jm.getHint(), je.getCode());
  }

  public MessageDialog(int type, String errorMessage, String errorInformation, String code) {
    this(type, errorMessage, errorInformation, "", "", code);
  }

  private void createIcons() {
    IconFactory iconFactory = IconFactory.getInstance();
    infoIcon = iconFactory.getIcon(GoogleMaterialDesignIcons.INFO_OUTLINE, 64, IconFactory.ICON_COLOR_INFO);
    warningIcon = iconFactory.getIcon(GoogleMaterialDesignIcons.WARNING, 64, IconFactory.ICON_COLOR_WARNING);
    errorIcon = iconFactory.getIcon(GoogleMaterialDesignIcons.ERROR, 64, IconFactory.ICON_COLOR_ERROR);
  }

  private void init() {
    generateHeaderPanel();
    generateLabel();

    iconLabel = getIconLabel();
    javaPanel = getInfoArea("Detailed Message:", this.javaMessage);
    helpPanel = getInfoArea("Hint:", this.helpMessage);
    hideButtonPanel = getHideButtons();
    showButtonPanel = getShowButtons();
  }

  private void setDialogTitle(String code) {
    String title = null;

    switch (type) {
      case JIPSMessage.INFORMATION:
        title = "INFORMATION";
        break;
      case JIPSMessage.ERROR:
        title = "ERROR";
        break;
      case JIPSMessage.WARNING:
        title = "WARNING";
        break;
    }

    if (code != null)
      title = title + " ( Code " + code + " )";

    setTitle(title);
  }

  private void rebuild() {

    remove(informationLabel);
    remove(headerPanel);
    remove(hideButtonPanel);
    remove(showButtonPanel);
    remove(iconLabel);
    remove(javaPanel);
    remove(helpPanel);
    remove(detailedPanel);

    build();
    validate();
  }

  private void build() {
    setLayout(new BorderLayout());
    add(headerPanel, BorderLayout.NORTH);

    if (detailed) {
      setLocation(CommonFunctions.getMiddleOfScreen(x, y2));
      setSize(x, y2);
      detailedPanel.add(informationLabel);
      detailedPanel.add(javaPanel);
      detailedPanel.add(helpPanel);

      add(detailedPanel, BorderLayout.CENTER);
      add(hideButtonPanel, BorderLayout.SOUTH);

    } else {
      setLocation(CommonFunctions.getMiddleOfScreen(x, y1));
      setSize(x, y1);
      add(informationLabel, BorderLayout.CENTER);
      add(showButtonPanel, BorderLayout.SOUTH);
    }
  }


  private void generateHeaderPanel() {
    headerPanel = new JPanel(new BorderLayout());

    switch (type) {
      case JIPSMessage.INFORMATION:
        icon = this.infoIcon;
        break;
      case JIPSMessage.ERROR:
        icon = this.errorIcon;
        break;
      case JIPSMessage.WARNING:
        icon = this.warningIcon;
        break;
    }

    JLabel headerLabel = new JLabel("<html><h3>" + this.errorMessage + "</h3></html>");
    JLabel iconLabel = new JLabel(icon);

    headerLabel.setBackground(Color.WHITE);
    iconLabel.setBackground(Color.WHITE);

    headerLabel.setOpaque(true);
    iconLabel.setOpaque(true);

    headerLabel.setBorder(Borders.E5_BORDER);
    iconLabel.setBorder(Borders.E5_BORDER);

    headerPanel.setBorder(Borders.L1DGB_BORDER);
    headerPanel.add(headerLabel, BorderLayout.CENTER);
    headerPanel.add(iconLabel, BorderLayout.EAST);
  }

  private JLabel getIconLabel() {
    switch (type) {
      case JIPSMessage.INFORMATION:
        icon = this.infoIcon;
        break;
      case JIPSMessage.ERROR:
        icon = this.errorIcon;
        break;
      case JIPSMessage.WARNING:
        icon = this.warningIcon;
        break;
    }

    JLabel iconLabel = new JLabel(icon);
    iconLabel.setVerticalAlignment(JLabel.TOP);

    return iconLabel;
  }

  public JPanel getInfoArea(String title, String text) {

    JPanel jp = new JPanel(new BorderLayout());

    JLabel jl = new JLabel("<html><strong>" + title + "</strong></html>");
    JTextArea jta = new JTextArea(text, 4, 4);
    jta.setEditable(false);

    JScrollPane jsp = new JScrollPane(jta);

    jp.add(jl, BorderLayout.NORTH);
    jp.add(jsp, BorderLayout.CENTER);

    jp.setBorder(Borders.E4_BORDER);

    return jp;
  }

  public void generateLabel() {
    informationLabel = new JLabel("<html>" + this.errorInformation + "</html>");
    informationLabel.setVerticalAlignment(JLabel.TOP);
    informationLabel.setBorder(Borders.E4_BORDER);

  }

  public JPanel getShowButtons() {

    JPanel jp = new JPanel(new FlowLayout());

    JButton showButton = new JButton("Show Details");
    JButton closeButton = new JButton("Close");

    showButton.addActionListener(this);
    closeButton.addActionListener(this);

    jp.add(showButton);
    jp.add(closeButton);
    jp.setBorder(Borders.E4_BORDER);

    return jp;
  }

  public JPanel getHideButtons() {
    JPanel jp = new JPanel(new FlowLayout());

    JButton hideButton = new JButton("Hide Details");
    JButton closeButton = new JButton("Close");

    hideButton.addActionListener(this);
    closeButton.addActionListener(this);

    jp.add(hideButton);
    jp.add(closeButton);
    jp.setBorder(Borders.E4_BORDER);

    return jp;
  }

  public void actionPerformed(ActionEvent e) {

    if (e.getActionCommand().equals("Hide Details")) {
      this.detailed = false;
      rebuild();
    } else if (e.getActionCommand().equals("Show Details")) {
      this.detailed = true;
      rebuild();
    } else if (e.getActionCommand().equals("Close")) {
      this.dispose();
    }

  }
}
