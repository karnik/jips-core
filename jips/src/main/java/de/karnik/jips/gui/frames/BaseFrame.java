/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.gui.frames;

import de.karnik.jips.CommonFunctions;
import de.karnik.jips.IO;
import de.karnik.jips.MsgHandler;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.config.JIPSConstants;
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.common.helper.IconFactory;
import de.karnik.jips.common.lang.Translator;
import de.karnik.jips.gui.GUIObjectHelper;
import de.karnik.jips.gui.listener.JIPSWindowAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The BaseFrame class contains class fields and methods for all Frames in JIPS.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.1
 * @since v.0.0.2
 */
public abstract class BaseFrame extends JFrame {

  /**
   * The serial version UID.
   */
  private static final long serialVersionUID = -4563000561533259963L;

  /**
   * The width of this component in pixels.
   */
  protected int width;
  /**
   * The height of this component in pixels.
   */
  protected int height;

  /**
   * The name of the frame.
   */
  protected String name;

  /**
   * The object to hold the JIPS variables and functions.
   */
  protected JIPSVariables vars = null;
  /**
   * The icon factory.
   */
  protected IconFactory iconFactory;
  /**
   * The object to hold the translator functions.
   */
  protected Translator trans = null;

  /**
   * The object to hold WindowAdapter for closing and resizing operations.
   */
  protected JIPSWindowAdapter jwa = null;
  /**
   * The object to hold the base container.
   */
  protected Container cp = null;
  /**
   * The object to hold the GridBagLayout.
   */
  protected GridBagLayout gbl = null;

  /**
   * The object to hold the GUIObjectHelper.
   */
  protected GUIObjectHelper goh = null;

  /**
   * The ok button.
   */
  protected JButton okButton = null;
  /**
   * The cancel button.
   */
  protected JButton cancelButton = null;
  /**
   * The apply button.
   */
  protected JButton applyButton = null;

  /**
   * This class is uninstantiable with the default constructor.
   */
  private BaseFrame() {
  }

  /**
   * Constructs a new JIPSWindowAdapter object with the specified parameters.
   * Sets basic options and calls the super constructor (JFrame).
   *
   * @param n      the name of the frame
   * @param width  the width of this component in pixels
   * @param height the height of this component in pixels
   */
  public BaseFrame(String n, int width, int height) throws JIPSException {
    super(n);

    this.width = width;
    this.height = height;

    name = n;
    trans = Translator.getInstance();
    vars = JIPSVariables.getInstance();
    iconFactory = IconFactory.getInstance();

    setSize(width, height);
    setLocation(CommonFunctions.getMiddleOfScreen(width, height));

    // set icon
    URL iconURL = IO.getFilePath(
            JIPSConstants.JIPS_WINDOW_ICON, false);

    if (iconURL != null) {
      Image icon = Toolkit.getDefaultToolkit().getImage(iconURL);
      setIconImage(icon);
    }

    cp = getContentPane();
    cp.setLayout(new BorderLayout());

    gbl = new GridBagLayout();
    goh = GUIObjectHelper.getInstance();

    jwa = new JIPSWindowAdapter(getTitle());
    addWindowListener(jwa);

    if (vars.debugMode) MsgHandler.debugMSGStart("Frame: " + name, true);
  }


  /**
   * Opens the specified URL in the standard browser.
   *
   * @param url the URL to open
   */
  public void openURL(URL url) throws JIPSException {
    if (vars.debugMode) MsgHandler.debugMSG("BaseFrame.openURL( URL url )", true);
    try {
      if (Desktop.isDesktopSupported()) {
        Desktop desktop = Desktop.getDesktop();

        if (desktop.isSupported(Desktop.Action.BROWSE)) {
          desktop.browse(url.toURI());
        }
      }
    } catch (URISyntaxException use) {
      throw new JIPSException("0013", use, false);
    } catch (Exception e) {
      throw new JIPSException("0014", e, false);
    }
  }

  /**
   * Opens the specified URL in the standard browser.
   *
   * @param url the String-URL to open
   */
  public void openURL(String url) throws JIPSException {
    if (vars.debugMode) MsgHandler.debugMSG("BaseFrame.openURL( String url )", true);
    try {
      openURL(new URL(url));
    } catch (MalformedURLException mue) {
      throw new JIPSException("0012", mue, false);
    }
  }

  /**
   * Compares the ActionCommand of an event with a String.
   *
   * @param event     the event with the ActionCommand
   * @param compareTo the String to compare
   * @return
   */
  public boolean compareEvent(ActionEvent event, String compareTo) {
    boolean status = false;
    if (event.getActionCommand().equals(compareTo)) status = true;
    return status;
  }
}