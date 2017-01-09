/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.processing;

import de.karnik.jips.JIPSExceptionHandler;
import de.karnik.jips.common.ImageStorage;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.ProjectListener;
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.common.processing.JIPSObject;
import de.karnik.jips.gui.desk.connector.BaseConnectorUI;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

public class BaseConnector  implements MouseInputListener, JIPSObject {

  public static final int INPUT_CONNECTOR = 0;
  public static final int OUTPUT_CONNECTOR = 1;
  public static final int NORMAL = 0;
  public static final int READY = 1;
  public static final int BUSY = 2;
  private BaseConnectorUI bcUI = null;
  private ProjectListener	projectListener  = null;
  private ImageStorage imageStorage = null;
  private String connectorName = null;
  private String connectorID = null;
  private int type = 0;
  private boolean connected = false;
  private Color conColor = null;
  private Color readyColor = null;
  private Color busyColor = null;

  private int status = 0;

  private JIPSVariables vars = JIPSVariables.getInstance();

  public BaseConnector() throws JIPSException {

    bcUI = new BaseConnectorUI( 0,0,0,0 );
    bcUI.addMouseMotionListener( this );
    bcUI.addMouseListener( this );

    conColor = vars.getColor( "con_color" );
    readyColor = vars.getColor( "con_color_ready" );
    busyColor = vars.getColor( "con_color_busy" );

    setStatus( NORMAL );
  }

  public void setStatus( int status ) {

    if( status != status && projectListener != null )
      projectListener.connectorStatusChanged( status, connectorID );


    this.status = status;

    switch( this.status ) {
      case NORMAL:
        bcUI.setBackground( conColor );
        break;
      case READY:
        bcUI.setBackground( readyColor );
        break;
      case BUSY:
        bcUI.setBackground( busyColor );
        break;
    }
    bcUI.repaint();
  }

  public final BaseConnectorUI getUI() throws JIPSException {
    return bcUI;
  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.processing.JIPSObject#getID()
   */
  public String getID() {
    return connectorID;
  }

  /* (non-Javadoc)
   * @see de.karnik.jips.common.processing.JIPSObject#setID(java.lang.String)
   */
  public void setID( String id ) {
    this.connectorID = id;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getConnectorName() {
    return this.connectorName;
  }

  public void setConnectorName(String connectorName) {
    this.connectorName = connectorName;
  }

  public void setProjectListener( ProjectListener listener ) {
    this.projectListener = listener;
  }

  public void removeProjectListener() {
    this.projectListener = null;
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
   */
  public void mouseClicked(MouseEvent e) {}

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
   */
  public void mouseEntered(MouseEvent e) {
    bcUI.setBackground( bcUI.getBackground().brighter() );
    bcUI.repaint();
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
   */
  public void mouseExited(MouseEvent e) {
    this.setStatus( this.status );
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
   */
  public void mousePressed(MouseEvent e) {
    try {
      if( this.projectListener != null )
        this.projectListener.connectorClicked( this.connectorID );
    } catch( JIPSException je ) {
      JIPSExceptionHandler.handleException( je, true );
    }
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
   */
  public void mouseReleased(MouseEvent e) {}

  public void mouseDragged(MouseEvent e) {
    // TODO Auto-generated method stub

  }

  public void mouseMoved(MouseEvent e) {
    // TODO Auto-generated method stub

  }

  public boolean isConnected() {
    return connected;
  }

  public void setConnected(boolean connected) {
    this.connected = connected;
  }

  public ImageStorage getImageStorage() {
    return imageStorage;
  }

  public void setImageStorage(ImageStorage imageStorage) {
    this.imageStorage = imageStorage;
  }

}
