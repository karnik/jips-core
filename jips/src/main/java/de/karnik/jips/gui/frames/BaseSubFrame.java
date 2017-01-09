/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.gui.frames;

import de.karnik.jips.MsgHandler;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.gui.FrameHelper;
import de.karnik.jips.gui.menu.MainMenu;
import de.karnik.jips.gui.objects.Borders;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * The BaseSubFrame class contains class fields and methods for all SubFrames in JIPS.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.7
 */
public class BaseSubFrame extends BaseFrame {

  /**
   *
   */
  private static final long serialVersionUID = 1984124078754249334L;

  /**
   * The object to hold the main tab.
   */
  protected JTabbedPane jtp = null;
  protected JPanel 	  headerPanel = null;

  public BaseSubFrame(String n, int x, int y) throws JIPSException {
    super(n, x, y);
  }

  /**
   * Adds an new component to the content panel tab.
   *
   * @param name the name of the new panel
   * @param c the component to add
   * @param border the border of the tab
   * @return the id of the selected tab
   */
  protected void addTab2Pane( String name, Component c, Border border ) {
    if( jtp == null ) {
      jtp = new JTabbedPane();
      jtp.setBorder( border );
      cp.add( jtp, BorderLayout.CENTER );
    }
    jtp.addTab( name, c );
  }

  /**
   * Adds an new component to the content panel tab.
   * If there is no tabbed pane one will be created.
   *
   * @param c the component to add
   * @param border the border of the tab
   * @param index the index for the tab
   */
  protected void addTab2Pane( Component c, Border border ) {
    if( jtp == null ) {
      jtp = new JTabbedPane();
      jtp.setBorder( border );
      cp.add( jtp, BorderLayout.CENTER );
    }

    jtp.addTab( c.getName(), c );
  }

  protected void setDescription( String title, String message ) {
    this.setDescription( title, message, null );
  }

  protected void setDescription( String title, String message, Icon icon ) {
    JLabel iconLabel = null;
    JLabel headerLabel = null;

    if( headerPanel != null )
      remove( headerPanel );

    headerPanel = new JPanel( new BorderLayout() );
    headerPanel.setBorder( Borders.L1DGB_BORDER );

    headerLabel = new JLabel( "<html><h3>" + title + "</h3>" +
            message + "</html>" );

    headerLabel.setBackground( Color.WHITE );
    headerLabel.setOpaque( true );
    headerLabel.setBorder( Borders.E5_BORDER);
    headerPanel.add( headerLabel, BorderLayout.CENTER );

    if( icon != null ) {
      iconLabel = new JLabel( icon );
      iconLabel.setBackground( Color.WHITE );
      iconLabel.setOpaque( true );
      //iconLabel.setBorder( Borders.E5_BORDER);
      headerPanel.add( iconLabel, BorderLayout.EAST );
    }

    add( headerPanel, BorderLayout.NORTH );
    this.repaint();

  }

  /**
   * Invokes functions when the cancel button is pressed.
   */
  protected void cancelButtonPressed() {
    if( vars.debugMode ) MsgHandler.debugMSGEnd( "Frame: " + name, true );

    setVisible( false );
    dispose();
    FrameHelper.removeFrame( this );
    MainMenu.setAll( true );
  }
}
