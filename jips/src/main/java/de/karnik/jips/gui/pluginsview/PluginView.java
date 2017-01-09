/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.gui.pluginsview;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.common.lang.Translator;
import de.karnik.jips.common.plugin.Plugin;
import de.karnik.jips.gui.desk.connector.BaseConnectorUI;
import de.karnik.jips.gui.desk.processing.BaseProcessUI;
import de.karnik.jips.processing.BaseConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.text.AttributedString;
import java.util.ArrayList;

public class PluginView extends JLabel {

  private static final long serialVersionUID = 1L;
  protected Translator trans = null;
  private BaseProcessUI bpui = null;
  private Plugin plugin = null;
  private JIPSVariables vars = null;
  private Color bgColor = null;
  private boolean selected = false;
  private Font boldFont = null;
  private Font smallFont = null;
  private Font smallBoldFont = null;

  public PluginView(Plugin plugin ) throws JIPSException {

    vars = JIPSVariables.getInstance();
    trans = Translator.getInstance();

    boldFont = getFont().deriveFont( Font.BOLD );
    smallFont = getFont().deriveFont( 8.0f );
    smallBoldFont = getFont().deriveFont( Font.BOLD, 8.0f );

    this.plugin = plugin;
    bpui = new BaseProcessUI(plugin.getPluginInputCount(), plugin.getPluginOutputCount());
    bpui.addInputs( createBaseConnectors( true ) );
    bpui.addOutputs( createBaseConnectors( false ) );
    bpui.setName( this.plugin.getPluginName() );

    setSelected( false );
  }

  public Plugin getPlugin() {
    return plugin;
  }

  public BaseProcessUI getBaseProcessUI() {
    return bpui;
  }

  public boolean isSelected() {
    return this.selected;
  }

  public void setSelected(boolean selected ) {
    this.selected = selected;

    if( this.selected )
      bgColor = vars.getColor( "select_background_color" );
    else
      bgColor = vars.getColor( "plugin_pane_bg_color" );

    repaint();
  }

  private ArrayList<BaseConnectorUI> createBaseConnectors(boolean input ) throws JIPSException {
    if( input )
      return getBaseConnectors(plugin.getPluginInputCount(), BaseConnector.INPUT_CONNECTOR);
    else
      return getBaseConnectors(plugin.getPluginOutputCount(), BaseConnector.OUTPUT_CONNECTOR);
  }

  private ArrayList<BaseConnectorUI> getBaseConnectors(int size, int type ) throws JIPSException {
    ArrayList<BaseConnectorUI> baseConnectors = new ArrayList<BaseConnectorUI>();
    BaseConnector bc = null;

    for(int i = 0; i < size; i++ ) {
      bc = new BaseConnector();
      bc.setType( type );
      baseConnectors.add( bc.getUI() );
    }

    return baseConnectors;
  }


  public void paintComponent( Graphics g ) {

    Graphics2D g2d = ( Graphics2D )g;

    g2d.setColor( bgColor );
    g2d.fillRect( 1, 1, this.getWidth()-1, this.getHeight()-1 );

    RenderingHints qualityHints = new RenderingHints( RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON );
    qualityHints.put( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
    g2d.setRenderingHints( qualityHints );

    AffineTransform origXform = g2d.getTransform();
    AffineTransform newXform = (AffineTransform)(origXform.clone());
    //center of rotation is center of the panel
    newXform.scale( 0.6, 0.6 );
    newXform.translate( 220, 0 );
    g2d.setTransform( newXform );
    bpui.paint( g2d );
    //draw image centered in panel
    super.paintComponent(g2d);
    g2d.setTransform(origXform);

    // draw info
    g2d.setColor( Color.BLACK );

    AttributedString as = new AttributedString( plugin.getPluginName() );
    as.addAttribute( TextAttribute.FONT, boldFont );
    g2d.drawString( as.getIterator(), 10, boldFont.getSize() + 10 );


    addInfoText( trans.getTranslation( "provider" ), plugin.getPluginProviderName(), 40, g2d );
    addInfoText( trans.getTranslation( "version" ), plugin.getPluginVersion(), 50, g2d );
  }

  private void addInfoText( String title, String value, int y, Graphics2D g2d ) {
    if( value == null )
      value = "-";

    String temp = title;
    String temp1 = temp + ": " + value;

    AttributedString as = new AttributedString( temp1 );

    as.addAttribute( TextAttribute.FONT, smallFont, temp.length(), temp1.length() );
    as.addAttribute( TextAttribute.FONT, smallBoldFont, 0, temp.length() );

    g2d.drawString( as.getIterator(), 10, y );
  }
}
