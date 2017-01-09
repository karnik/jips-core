/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.gui.desk.connector;

import de.karnik.jips.CommonFunctions;
import de.karnik.jips.JIPSExceptionHandler;
import de.karnik.jips.ProjectUIListener;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.config.JIPSVariables;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class BaseConnectionUI extends JComponent implements MouseInputListener {

  private static final long serialVersionUID = 9220864492693429348L;

  private Color outerConnectionColor = null;
  private Color innerConnectionColor = null;
  private Color selectedInnerConnectionColor = null;

  private JIPSVariables vars;

  private BasicStroke bs1 = null;
  private BasicStroke bs2 = null;

  private boolean selected = false;

  private Vector<ProjectUIListener> projectUIListeners = new Vector<ProjectUIListener>();

  /**
   * Connector with the output.
   */
  private BaseConnectorUI output = null;
  /**
   * Connector with the input.
   */
  private BaseConnectorUI input = null;

  private Point start = new Point( 0,0 );
  private Point end = new Point( 0,0 );
  private int length = 0;

  /**
   * This class is uninstantiable with the default constructor.
   */
  private BaseConnectionUI() {}


  public BaseConnectionUI( BaseConnectorUI output, BaseConnectorUI input ) throws JIPSException {
    this();


    vars = JIPSVariables.getInstance();

    this.output = output;
    this.input = input;

    outerConnectionColor = vars.getColor( "connection_color_outer" );
    innerConnectionColor = vars.getColor( "connection_color_inner" );
    selectedInnerConnectionColor = vars.getColor( "select_color" );

    bs1 = new BasicStroke( 4 );
    bs2 = new BasicStroke( 2 );

    addMouseListener( this );
    addMouseMotionListener( this );

    this.setOpaque( false );

    calcConnectionBounds();
  }

  public void removeProjectUIListeners(ProjectUIListener listener ) {
    projectUIListeners.remove( listener );
  }

  public void addProjectUIListeners(ProjectUIListener listener ) {
    projectUIListeners.add( listener );
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected ) {
    this.selected = selected;
    repaint();
  }

  private void calcConnectionBounds() {

    int connectorWidth = input.getWidth();
    int connectorHeight = input.getHeight();
    int width = input.getAbsX() - output.getAbsX();
    int height = input.getAbsY() - output.getAbsY();

    if( width < 0 ) width *= -1;

    if( height < 0 ) height *= -1;

    width += connectorWidth;
    height += connectorHeight;

    int x = 0;
    int y = 0;

    if( CommonFunctions.compare( input.getAbsX(), output.getAbsX() ) < 1 ) {
      x = input.getAbsX();
      start.x = connectorWidth / 2;
      end.x = getWidth() - ( connectorWidth / 2 );
    } else {
      x = output.getAbsX();
      start.x = getWidth() - ( connectorWidth / 2 );
      end.x = connectorWidth / 2;
    }

    if( CommonFunctions.compare( input.getAbsY(), output.getAbsY() ) < 1 ) {
      y = input.getAbsY();
      start.y = connectorHeight / 2;
      end.y = getHeight() - ( connectorHeight / 2 );
    } else {
      y = output.getAbsY();
      start.y = getHeight() - ( connectorHeight / 2 );
      end.y = connectorHeight / 2;
    }

    setBounds( x - ( connectorWidth / 2 ), y - ( connectorHeight / 2 ), width, height);
  }

  public void paintComponent( Graphics g ) {
    calcConnectionBounds();

    Graphics2D g2 = ( Graphics2D )g;
    Stroke bs = g2.getStroke();

    //if( length == 0 )
    length = ( end.x - start.x ) / 2;

    g2.setStroke( bs1 );
    g2.setColor( outerConnectionColor );
    drawLine( g2 );

    g2.setStroke( bs2 );
    if( isSelected() )
      g2.setColor( selectedInnerConnectionColor );
    else
      g2.setColor( innerConnectionColor );
    drawLine( g2 );

    g2.setStroke( bs );
  }

  private void drawLine( Graphics2D g2 ) {
    g2.drawLine( start.x, start.y, start.x + length, start.y );
    g2.drawLine( start.x + length, start.y, start.x + length, end.y );
    g2.drawLine( start.x + length, end.y, end.x, end.y );
  }

  private boolean isBetween( int first, int second, int search ) {

    if( search < first && search > second )
      return true;

    if( search > first && search < second )
      return true;

    return false;
  }

  private boolean isBetween( Point p1, Point p2, Point search, boolean horizontal ) {
    int connectorWidth = input.getWidth();
    int connectorHeight = input.getHeight();

    if( horizontal ) {

      if( isBetween( p1.x, p2.x, search.x )
              && isBetween( p1.y + ( connectorHeight / 2 ), p2.y - ( connectorHeight / 2 ), search.y ) )
        return true;

    } else {
      if( isBetween( p1.y, p2.y, search.y )
              && isBetween( p1.x + ( connectorWidth / 2 ), p2.x - ( connectorWidth / 2 ), search.x ) )
        return true;
    }

    return false;
  }

  private boolean intersects( int x, int y ) {

    Point search = new Point( x, y );

    if( isBetween( start, new Point( start.x + length, start.y ) , search, true ) )
      return true;

    if( isBetween( new Point( start.x + length, start.y ), new Point( start.x + length, end.y ), search, false ) )
      return true;

    if( isBetween( new Point( start.x + length, end.y ), end, search, true ) )
      return true;

    return false;
  }

  public Rectangle getFirstRect() {
    return getRect( 0 );
  }

  public Rectangle getSecondRect() {
    return getRect( 1 );
  }

  public Rectangle getThirdRect() {
    return getRect( 2 );
  }

  private Rectangle getRect( int what ) {
    Rectangle rect = new Rectangle();
    int connectorWidth = input.getWidth();
    int connectorHeight = input.getHeight();
    	/*
    	if( isBetween( start, new Point( start.x + length, start.y ) , search, true ) )
    		return true;
    	
    	if( isBetween( new Point( start.x + length, start.y ), new Point( start.x + length, end.y ), search, false ) )
    		return true;
    	
    	if( isBetween( new Point( start.x + length, end.y ), end, search, true ) )
    		return true;*/

    switch( what ) {
      case 0:
        rect.setBounds( start.x, start.y - ( connectorHeight / 2 ), length, connectorHeight );
      case 1:
        rect.setBounds( start.x, start.y - ( connectorHeight / 2 ), length, connectorHeight );
      case 2:
        rect.setBounds( start.x + length, end.y + ( connectorHeight / 2 ), length, connectorHeight );
    }

    return rect;
  }

  @Override
  public void mouseClicked( MouseEvent e ) {
    try {

      if( e.getButton() == MouseEvent.BUTTON1 ) {

        int x = e.getX();
        int y = e.getY();

        if( !e.isControlDown() )
          for(int i = 0; i < projectUIListeners.size(); i++ )
            projectUIListeners.get( i ).deselectAll();

        if( intersects( x, y ) )
          setSelected( !isSelected() );
      }

    } catch ( JIPSException je ) {
      JIPSExceptionHandler.handleException( je );
    }
  }


  @Override
  public void mouseEntered(MouseEvent e) {
    // TODO Auto-generated method stub

  }


  @Override
  public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub

  }


  @Override
  public void mousePressed(MouseEvent e) {
    // TODO Auto-generated method stub

  }


  @Override
  public void mouseReleased(MouseEvent e) {
    // TODO Auto-generated method stub

  }


  @Override
  public void mouseDragged(MouseEvent e) {
    // TODO Auto-generated method stub

  }


  @Override
  public void mouseMoved(MouseEvent e) {
    // TODO Auto-generated method stub

  }


}
