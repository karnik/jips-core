/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.common.config;

import de.karnik.jips.IO;
import de.karnik.jips.MsgHandler;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.lang.Translator;
import de.karnik.jips.gui.FrameHelper;
import de.karnik.xml.XMLControl;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.util.HashMap;
import java.util.Locale;

/**
 * The JIPSVariables class contains several useful class fields and methods.
 * JIPSVariables holds almost every config option for JIPS.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 0.3
 * @since v.0.0.1
 */
public final class JIPSVariables extends XMLControl {

    public final static int NIMBUS_LAF = 0;
    public final static int GTK_LAF = 1;
    public final static int WINDOWS_LAF = 2;
    /**
     * The XML language tag.
     */
    public final static String LANG_TAG = "lang";
    /**
     * The XML LookAndFeel tag.
     */
    public final static String LAF_TAG = "laf";
    /**
     * The XML desktop raster enabled tag.
     */
    public final static String DESKTOP_RASTER_ENABLED = "desktop_raster_enabled";
    /**
     * The XML desktop raster tag.
     */
    public final static String DESKTOP_RASTER_TAG = "desktop_raster_size";
    /**
     * The XML desktop height tag.
     */
    public final static String DESKTOP_HEIGHT_TAG = "desktop_height";
    /**
     * The XML desktop width tag.
     */
    public final static String DESKTOP_WIDTH_TAG = "desktop_width";
    /**
     * The XML workspace dir tag.
     */
    public final static String WORKSPACE_DIR_TAG = "workspace";
    /**
     * The XML plugins dir tag.
     */
    public final static String PLUGINS_DIR_TAG = "plugins";
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -3089440301909898960L;
    /**
     * Static varibales instance for the singleton.
     */
    private static JIPSVariables instance = null;
    /**
     * The HashMap with the colors.
     */
    private static HashMap<String, Color> colors = new HashMap<String, Color>();
    /**
     * The Array with the color XML tags.
     */
    private static String[] colorTags = {
            "select_color", "select_background_color", "grid_color",
            "desktop_color", "process_color", "process_button_color", "border_color",
            "con_color", "con_color_ready", "con_color_busy", "connection_color_inner",
            "connection_color_outer", "plugin_pane_bg_color"
    };
    /**
     * The debug mode switch.
     */
    public boolean debugMode = false;
    /**
     * The graphical debug mode switch.
     */
    public boolean graphicalDebugMode = false;
    /**
     * The language to use.
     */
    private String language = "en";
    /**
     * The LookAndFeel to use.
     */
    private int lookAndFeel = 0;
    private boolean decorated = false;
    private boolean windowsAvail = false;
    private boolean gtkAvail = false;
    /**
     * Status for the desktop raster.
     */
    private boolean desktopRasterEnabled = false;
    /**
     * The size of the main desktop raster.
     */
    private int desktopRasterSize = 0;
    /**
     * The width of the main desktop.
     */
    private int desktopWidth = 0;
    /**
     * The height of the main desktop.
     */
    private int desktopHeight = 0;
    /**
     * Workspace directory.
     */
    private String workspaceDir = null;
    private String pluginsDir = null;
    private Options options = new Options();

    /**
     * Constructs a new JIPSVariables object.
     */
    private JIPSVariables() throws JIPSException {

        super(JIPSConstants.JIPS_CONFIG_FILE, true);

        // Read variables
        setLanguage(getValueOfFirstChild(LANG_TAG));

        try {
            lookAndFeel = Integer.parseInt(getValueOfFirstChild(LAF_TAG));
        } catch (NumberFormatException nfe) {
            throw new JIPSException("001A", nfe, true);
        }

        try {
            // Projekt destop stuff
            desktopHeight = Integer.parseInt(getValueOfFirstChild(DESKTOP_HEIGHT_TAG));
            desktopWidth = Integer.parseInt(getValueOfFirstChild(DESKTOP_WIDTH_TAG));
            desktopRasterSize = Integer.parseInt(getValueOfFirstChild(DESKTOP_RASTER_TAG));
        } catch (NumberFormatException nfe) {
            throw new JIPSException("0019", nfe, true);
        }

        desktopRasterEnabled = Boolean.parseBoolean(getValueOfFirstChild(DESKTOP_RASTER_ENABLED));

        // read all colors
        generateColors();

    }

    /**
     * Returns the jips variables instance.
     *
     * @return the jips varibales object.
     */
    public static JIPSVariables getInstance() throws JIPSException {

        if (instance == null) {
            instance = new JIPSVariables();
        }
        return instance;
    }

    /**
     * Generates the HashMap with the colors.
     */
    public void generateColors() throws JIPSException {
        int alpha = 255;
        int counter = 0;
        for (String colorTag : colorTags) {

            try {
                alpha = Integer.parseInt(getValueOfFirstChild(colorTag, "alpha"));
            } catch (NumberFormatException nfe) {
                throw new JIPSException("001B", nfe, true);
            }

            colors.put(colorTag, convertHex2Color(
                    getValueOfFirstChild(colorTag), alpha));
            counter++;
        }

        if (this.debugMode) MsgHandler.debugMSG(counter + " Color(s) generated...", true);

    }

    /**
     * Converst a hex color string live #ff00ff int a new Color.
     *
     * @param hexColor the hexcode
     * @param alpha    the alpha value in the range (0 - 255)
     * @return the new color
     */
    private Color convertHex2Color(String hexColor, int alpha) throws JIPSException {

        Color tempColor = null;

        try {
            tempColor = Color.decode(hexColor);
        } catch (NumberFormatException nfe) {
            throw new JIPSException("0018", nfe, true);
        }
        int r = tempColor.getRed();
        int g = tempColor.getGreen();
        int b = tempColor.getBlue();

        return new Color(r, g, b, alpha);
    }


    /**
     * Creates the possible options for jips.
     */
    public void createOptions() throws JIPSException {

        Translator translator = Translator.getInstance();

        Option help = new Option("help", translator.getTranslation("cmd_help-info"));
        Option debug = new Option("debug", translator.getTranslation("cmd_help_debug"));
        Option graphicalDebug = new Option("graphicalDebug", translator.getTranslation("cmd_help_graphical-debug"));
        Option pluginsDir = new Option("pluginsDir", true, translator.getTranslation("cmd_help-plugins-dir"));

        options.addOption(help);
        options.addOption(debug);
        options.addOption(graphicalDebug);
        options.addOption(pluginsDir);
    }

    /**
     * Returns the possible options for jips.
     *
     * @return The possible options for jips.
     */
    public Options getOptions() {
        return options;
    }

    /**
     * Prints the Help.
     */
    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar <jips>.jar", options);
    }

    /**
     * Tests the given commandline options.
     *
     * @param line The CommandLine with the options.
     */
    public void testOptions(CommandLine line) throws JIPSException {

        if (line.hasOption("help")) {
            printHelp();
            System.exit(0);
        }
        if (line.hasOption("debug")) {
            debugMode = true;
        }
        if (line.hasOption("graphicalDebug")) {
            graphicalDebugMode = true;
        }
        if (line.hasOption("pluginsDir")) {
            pluginsDir = IO.getFile(line.getOptionValue("pluginsDir"), true, true).toString();
        }
    }

    /**
     * Returns the JIPS language.
     *
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the JIPS language.
     * See config file for valid languages.
     *
     * @param lang the language to set
     */
    public void setLanguage(String lang) throws JIPSException {
        language = lang;
        Locale.setDefault(new Locale(language));
        Translator.getInstance().loadLocale();
    }

    /**
     * Saves the actual configuration to the config file.
     */
    public void saveConfig() throws JIPSException {
        // put the class values to the Document
        setValueOfFirstChild(LANG_TAG, language);
        setValueOfFirstChild(LAF_TAG, Integer.toString(lookAndFeel));

        // write out the file
        IO.writeXMLDocument(getMyDoc(), getXmlFilePath());
    }

    /**
     * Returns the LookAndFeel.
     *
     * @return the lookAndFeel
     */
    public int getLookAndFeel() {
        return lookAndFeel;
    }

    /**
     * Sets the LookAndFeel and updates the UI.
     *
     * @param laf the lookAndFeel to set
     */
    public void setLookAndFeel(int laf) throws JIPSException {

        String lafString = "";

        LookAndFeelInfo[] lafInfo = UIManager.getInstalledLookAndFeels();

        for (LookAndFeelInfo aLafInfo : lafInfo) {
            if (aLafInfo.getName().equals("Windows")) this.windowsAvail = true;
            if (aLafInfo.getName().contains("GTK")) this.gtkAvail = true;
        }

        lookAndFeel = laf;

        if (laf == WINDOWS_LAF && !this.windowsAvail)
            lookAndFeel = NIMBUS_LAF;

        if (laf == GTK_LAF && !this.gtkAvail)
            lookAndFeel = NIMBUS_LAF;

        try {
            // set the selected LookAndFeel
            switch (lookAndFeel) {
                case NIMBUS_LAF:
                    decorated = false;
                    lafString = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
                    break;
                case GTK_LAF:
                    decorated = false;
                    lafString = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
                    break;
                case WINDOWS_LAF:
                    decorated = false;
                    lafString = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
                    break;
            }

            UIManager.setLookAndFeel(lafString);

        } catch (Exception e) {
            throw new JIPSException("0027", e, true);
        }

        if (debugMode) MsgHandler.debugMSG("JIPSVariables.setLookAndFeel(): " + lookAndFeel, true);

        //frames
        FrameHelper.updateSwingSets();
    }

    /**
     * Returns the main desktop raster size.
     *
     * @return the desktopRasterSize
     */
    public int getDesktopRasterSize() {
        return desktopRasterSize;
    }

    /**
     * Sets the main desktop raster size.
     *
     * @param desktopRasterSize the desktopRasterSize to set
     */
    public void setDesktopRasterSize(int desktopRasterSize) {
        this.desktopRasterSize = desktopRasterSize;
    }

    /**
     * Returns the main desktop height.
     *
     * @return the desktopHeight
     */
    public int getDesktopHeight() {
        return desktopHeight;
    }

    /**
     * Sets the main desktop height.
     *
     * @param desktopHeight the desktopHeight to set
     */
    public void setDesktopHeight(int desktopHeight) {
        this.desktopHeight = desktopHeight;
    }

    /**
     * Returns the main desktop width.
     *
     * @return the desktopWidth
     */
    public int getDesktopWidth() {
        return desktopWidth;
    }

    /**
     * Sets the main desktop raster width.
     *
     * @param desktopWidth the desktopWidth to set
     */
    public void setDesktopWidth(int desktopWidth) {
        this.desktopWidth = desktopWidth;
    }

    /**
     * Returns the color for the search key.
     *
     * @param search the search key
     * @return the color for the search key, or null
     */
    public Color getColor(String search) {
        return colors.get(search);
    }

    /**
     * Set the color for the specified key.
     *
     * @param search the search key
     * @param color  the color to set
     */
    public void setColor(String search, Color color) {
        colors.put(search, color);
    }

    /**
     * Returns the desktop raster status.
     *
     * @return The desktop raster status.
     */
    public boolean isDesktopRasterEnabled() {
        return desktopRasterEnabled;
    }

    /**
     * Sets the desktop raster status.
     *
     * @param desktopRasterEnabled The desktop raster status.
     *                             <strong>true</strong> to enable the raster.
     */
    public void setDesktopRasterEnabled(boolean desktopRasterEnabled) {
        this.desktopRasterEnabled = desktopRasterEnabled;
    }

    public boolean isGtkAvail() {
        return gtkAvail;
    }

    public boolean isWindowsAvail() {
        return windowsAvail;
    }

    public boolean isDecorated() {
        return decorated;
    }

    public String getWorkspaceDir() throws JIPSException {
        if (null == workspaceDir) {
            workspaceDir = IO.getFile(
                    getValueOfFirstChild(WORKSPACE_DIR_TAG), true, true).toString();
        }

        return workspaceDir;
    }

    public void setWorkspaceDir(String workspaceDir) {
        this.workspaceDir = workspaceDir;
    }

    public String getPluginsDir() throws JIPSException {
        if (null == pluginsDir) {
            pluginsDir = IO.getFile(
                    getValueOfFirstChild(PLUGINS_DIR_TAG), true, true).toString();
        }

        return pluginsDir;
    }
}
