/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.plugin.viewer.gui;

import de.karnik.jips.common.ImageStorage;
import de.karnik.jips.plugin.viewer.listener.ViewerErrorListener;

import javax.swing.*;
import java.awt.*;

/**
 * The viewer frame.
 *
 * @author <a href="mailto:markus.karnik@gmail.com">Markus Karnik</a>
 * @version 1.0
 */
public class ViewerFrame extends JFrame implements ViewerErrorListener {

  private static final long serialVersionUID = 2630441847534130935L;

  /**
   * The base container.
   */
  private Container contentPane = null;

  /**
   * Default constructor with default values DISPOSE_ON_CLOSE,
   * size 800x600 and Borderlayout.
   *
   * @param name Der Titel des Frames.
   */
  private ViewerFrame( String name ) {
    super( name );
    setSize(800, 600);

    setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
    contentPane = getContentPane();
    contentPane.setLayout( new BorderLayout() );
  }

  public ViewerFrame(String name, ImageStorage imageStorage, int modus, float scale) {
    this( name );

    ViewerBase viewer = new ViewerWithToolBar();
    viewer.addViewerErrorListener( this );
    viewer.setModus( modus );
    viewer.setScale( scale );

    contentPane.add(viewer, BorderLayout.CENTER);
    viewer.setData(imageStorage);
    setVisible( true );
  }

  /* (non-Javadoc)
   * @see java.awt.Window#dispose()
   */
  public void dispose() {
    super.dispose();
  }

  /* (non-Javadoc)
   * @see de.karnik.viewer.listener.ViewerErrorListener#scaleErrorOccurred(java.lang.String)
   */
  public void scaleErrorOccurred( String msg ){
    // TODO: implement
    System.out.println( msg );
  }

  /* (non-Javadoc)
   * @see de.karnik.viewer.listener.ViewerErrorListener#filetypeErrorOccurred(java.lang.String)
   */
  public void filetypeErrorOccurred( String msg ) {
    // TODO: implement
    System.out.println( msg );
  }
}
