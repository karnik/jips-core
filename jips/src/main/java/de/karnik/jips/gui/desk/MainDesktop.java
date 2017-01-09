/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.desk;

import de.karnik.jips.JIPSExceptionHandler;
import de.karnik.jips.JIPSObjectList;
import de.karnik.jips.MsgHandler;
import de.karnik.jips.ProjectUIListener;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.JIPSMessage;
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.common.lang.Translator;
import de.karnik.jips.gui.desk.connector.BaseConnectionUI;
import de.karnik.jips.gui.desk.processing.BaseProcessUI;
import de.karnik.jips.gui.pluginsview.PluginPane;
import de.karnik.jips.gui.pluginsview.PluginView;
import de.karnik.jips.processing.BaseConnector;
import de.karnik.jips.processing.BaseProcess;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Vector;

/**
 * The MainDesktop class contains class fields and methods for the main desktop.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.3
 */
public class MainDesktop extends JComponent implements MouseInputListener, DesktopUpdateListener, KeyListener {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -2300189974257273586L;
  /**
   *
   */
  protected Rectangle currentRect = null;
  /**
   *
   */
  protected Rectangle rectToDraw = null;
  /**
   *
   */
  protected Rectangle previousRectDrawn = new Rectangle();
    /**
     * The desktop raster size.
     */
    private int rSize  = 0;
    /**
     * The desktop x size.
     */
    private int xSize  = 0;
    /**
     * The desktop y size.
     */
    private int ySize  = 0;
    /**
     * The desktop dimension.
     */
    private Dimension d = null;
    private BaseConnector selectedConnector = null;
    /**
     * The selection area color.
     */
    private Color selectColor = null;
    /**
     * The selection frame color.
     */
    private Color selectFrameColor = null;
    private Color desktopColor = null;
    private Color gridColor = null;
    private float[] dash_array= { 10.0f, 5.0f, 10.0f, 5.0f };
  private BasicStroke bs1 = new BasicStroke(2f, BasicStroke.CAP_SQUARE,
          BasicStroke.JOIN_ROUND, 1f, dash_array, 0 );
    private Color outerConnectionColor = null;
    private Point mousePos = new Point();
    /**
     * The object to hold the JIPS variables and functions.
     */
    private JIPSVariables vars = null;
    /**
     * The object to hold the JIPS translation.
     */
    private Translator trans = null;
    private Vector<ProjectUIListener> projectUIListeners  = new Vector<ProjectUIListener>();

    private JIPSObjectList<BaseProcess> selectedProcesses  = new JIPSObjectList<BaseProcess>();

    private PluginView selectedPluginView = null;
    
    private BaseProcessUI baseProcess = null;

    private boolean selecting = false;
    private boolean attachedPluginView = false;

    /**
     * Constructs a new JIPS main desktop with the specified options.
     *
	 * @param projectID the project id
     * @param projectName the project name
     */
    public MainDesktop() throws JIPSException {
        super();
        setLayout( null );

        vars 		= JIPSVariables.getInstance();
        trans 		= Translator.getInstance();
        xSize 		= vars.getDesktopWidth();
        ySize 		= vars.getDesktopHeight();

        outerConnectionColor = vars.getColor( "connection_color_outer" );
        
        if( vars.isDesktopRasterEnabled() ) {
            rSize 		= vars.getDesktopRasterSize();
        } else {
            rSize = 1;
        }

        selectColor 		= vars.getColor( "select_background_color" );
        selectFrameColor 	= vars.getColor( "select_color" );
        desktopColor 		= vars.getColor( "desktop_color" );
        gridColor			= vars.getColor( "grid_color" );
        
        d = new Dimension( xSize, ySize );
        setMinimumSize( d );
        setPreferredSize( d );
        setMaximumSize( d );

        setFocusable( true );
        
        addMouseListener( this );
        addMouseMotionListener( this );

        setEnabled( true );
        
        addKeyListener( this );
        setVisible( true );
        
    }

    public void addProcess( BaseProcessUI baseProcess  ) throws JIPSException  {
        baseProcess.addDesktopUpdateListener( this );
       // baseProcess.addMouseMotionListener( this );
        add( baseProcess );
        
        MsgHandler.consoleMSG(
                new JIPSMessage(
                        JIPSMessage.INFORMATION, baseProcess.getName() + " " + trans.getTranslation( "added" ) , ""),
        				false );
        
    }

    public void addConnection( BaseConnectionUI baseConnection ) throws JIPSException {
    	//baseConnection.addMouseMotionListener( this );
    	add( baseConnection );
    }
    
    public void removeProcess( BaseProcessUI baseProcess ) throws JIPSException {
        remove( baseProcess );
        validate();
        
        MsgHandler.consoleMSG( 
        		new JIPSMessage( 
        				JIPSMessage.WARNING, baseProcess.getName() + " " + trans.getTranslation( "removed" ) , ""), 
        				false );
    }

    public void addProjectUIListener( ProjectUIListener listener ) {
        projectUIListeners.add( listener );
    }

    public void removeProjectUIListener( ProjectUIListener listener ) {
        projectUIListeners.remove( listener );
    }

    /**
     * Returns the raster size.
     * @return the raster size
     */
    public int getRasterSize() {
        return this.rSize;
    }

    //Draws a 20x20 grid using the current color.
    private void drawGrid(Graphics g, int gridSpace) {

      //Draw vertical lines.
      int x = 0;
      while (x < xSize) {
        g.drawLine(x, 0, x, ySize);
        x += gridSpace;
      }

      //Draw horizontal lines.
      int y = 0;
      while (y < ySize) {
        g.drawLine(0, y, xSize, y);
        y += gridSpace;
      }
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    public void paintComponent( Graphics g ) {

        g.setColor( desktopColor );
        g.fillRect( 0, 0, xSize, ySize );
        g.setColor( gridColor );
        if( vars.isDesktopRasterEnabled() ) {
            rSize = vars.getDesktopRasterSize();

            drawGrid(  g, rSize );

        } else {
            rSize = 1;
        }

        try {
        	if( this.selectedConnector != null )
        		drawNewConnectionLine( (Graphics2D )g );
        } catch( JIPSException je ) {
            JIPSExceptionHandler.handleException( je, true );
        }

        //If currentRect exists, paint a box on top.
        if ( currentRect != null ) {

            g.setColor( selectColor );
            g.fillRect(rectToDraw.x, rectToDraw.y,
                       rectToDraw.width - 1, rectToDraw.height - 1 );

            g.setColor( selectFrameColor );
            g.drawRect(rectToDraw.x, rectToDraw.y,
                    rectToDraw.width - 1, rectToDraw.height - 1 );

        }
        
        if( attachedPluginView ) {
        	Graphics2D g2d = ( Graphics2D )g;
        	
            AffineTransform origXform = g2d.getTransform();
            AffineTransform newXform = (AffineTransform)(origXform.clone());
            //center of rotation is center of the panel
            newXform.translate( mousePos.x, mousePos.y );
            g2d.setTransform( newXform );
            selectedPluginView.getBaseProcessUI().paint( g2d );

            
            super.paintComponent(g2d);
            g2d.setTransform(origXform); 
        	
        	
        }
        
        setEnabled( true );
    }

    /**
     * Draws a temp connection line from the selected connetor to mouse position. 
     * 
     * @param g2d The Graphics2D-Objekt to paint on.
     */
    public void drawNewConnectionLine( Graphics2D g2d ) throws JIPSException {

    	// save normal stroke and create vector
    	Stroke bs = g2d.getStroke();
        Vector<Point> points = new Vector<Point>();

        // calculate the difference between mouse and connector
        int spaceX = mousePos.x - selectedConnector.getUI().getAbsX();
        int spaceY = mousePos.y - selectedConnector.getUI().getAbsY();

        // create points for the connection line
        points.add( new Point( selectedConnector.getUI().getAbsX(), selectedConnector.getUI().getAbsY() ) );
        points.add( new Point( ( selectedConnector.getUI().getAbsX() + ( spaceX / 2 ) ), selectedConnector.getUI().getAbsY() ) );
        points.add( new Point( ( selectedConnector.getUI().getAbsX() + ( spaceX / 2 ) ), ( selectedConnector.getUI().getAbsY() + spaceY ) ) );
        points.add( new Point( mousePos.x, mousePos.y ) );

        // set stroke and draw inner linie
        g2d.setStroke( bs1 );
        g2d.setColor( outerConnectionColor );
        for( int j = 0; j < points.size()-1; j++ )
            g2d.drawLine( points.get( j ).x, points.get( j ).y, points.get( j + 1 ).x, points.get( j + 1 ).y );

    	// set normal stroke
        g2d.setStroke( bs );
    }

    protected void updateDrawableRect( int compWidth, int compHeight ) {

        int currentRectHeight = currentRect.height;
        int currentRectWidth = currentRect.width;

        int currentRectX = currentRect.x;
        int currentRectY = currentRect.y;

        //Make the width and height positive, if necessary.
        if ( currentRectWidth < 0) {
             currentRectWidth = 0 - currentRectWidth;
             currentRectX = currentRectX - currentRectWidth + 1;
            if ( currentRectX < 0) {
                 currentRectWidth += currentRectX;
                 currentRectX = 0;
            }
        }
        if ( currentRectHeight < 0) {
            currentRectHeight = 0 - currentRectHeight;
            currentRectY = currentRectY - currentRectHeight + 1;
            if ( currentRectY < 0 ) {
                 currentRectHeight += currentRectY;
                 currentRectY = 0;
            }
        }

        //The rectangle shouldn't extend past the drawing area.
        if ( ( currentRectX + currentRectWidth ) > compWidth ) {
               currentRectWidth = compWidth - currentRectX;
        }
        if ( ( currentRectY + currentRectHeight ) > compHeight ) {
               currentRectHeight = compHeight - currentRectY;
        }

        //Update rectToDraw after saving old value.
        if ( rectToDraw != null) {
             previousRectDrawn.setBounds(
                        rectToDraw.x, rectToDraw.y,
                        rectToDraw.width, rectToDraw.height);
             rectToDraw.setBounds( currentRectX, currentRectY, currentRectWidth, currentRectHeight );
        } else {
             rectToDraw = new Rectangle( currentRectX, currentRectY, currentRectWidth, currentRectHeight );
        }
    }

    // ===
    // Listener
    // ===

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed( MouseEvent e ) {

        try {

            if( e.getButton() == MouseEvent.BUTTON1 ) {
                int x = e.getX();
                int y = e.getY();

                selecting = true;

                // set selected
                if( !e.isControlDown() )
                    for( int i = 0; i < projectUIListeners.size(); i++ )
                        projectUIListeners.get( i ).deselectAll();

                currentRect = new Rectangle(x, y, 0, 0);
                updateDrawableRect( getWidth(), getHeight() );
            }
        } catch( JIPSException je ) {
            JIPSExceptionHandler.handleException( je, true );
        }
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     */
    public void mouseDragged( MouseEvent e) {
        if( selecting ) {

            try {
                updateSize( e );

                Component[] components = getComponents();

                for( int i = 0; i < components.length; i++ ) {

                    if( components[ i ] instanceof BaseConnectionUI ) {
                    	BaseConnectionUI bcui = ( BaseConnectionUI )components[ i ];
                        if( rectToDraw.intersects( bcui.getFirstRect() )
                        		|| rectToDraw.intersects( bcui.getSecondRect() )
                        		|| rectToDraw.intersects( bcui.getThirdRect() ) ) {
                            bcui.setSelected( true );
                        } else {
                            bcui.setSelected( false );
                        }
                    }                	
                	
                    if( components[ i ] instanceof BaseProcessUI ) {
                        BaseProcessUI bp = ( BaseProcessUI )components[ i ];
                        if( rectToDraw.intersects( bp.getBounds() ) ) {
                            bp.setSelected( true );
                        } else {
                            bp.setSelected( false );
                        }
                    }
                }
            } catch( JIPSException je ) {
                JIPSExceptionHandler.handleException( je, true );
            }

            try {
            	Thread.sleep( 5 );
            } catch( InterruptedException ie ) {
            	
            }
        }
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased( MouseEvent e ) {

        if( e.getButton() == MouseEvent.BUTTON1 ) {
            updateSize( e );
            int x = e.getX();
            int y = e.getY();

            currentRect = new Rectangle( x, y, 0, 0 );

            updateDrawableRect( getWidth(), getHeight() );
            selecting = false;
            repaint();
        }
    }

    public void mouseMoved( MouseEvent e ) {
    	
        if( this.selectedConnector != null || attachedPluginView ) {
            mousePos.x = e.getX();
            mousePos.y = e.getY();
            repaint();
            
            try {
            	Thread.sleep( 5 );
            } catch( InterruptedException ie ) {}
        }

    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked( MouseEvent e ) {

        if( e.getButton() == MouseEvent.BUTTON1 ) {
            if( !e.isControlDown() )
                try {
                    for( int i = 0; i < projectUIListeners.size(); i++ )
                        projectUIListeners.get( i ).deselectAll();
                    
                    
                    if( attachedPluginView ) {
                        for( int i = 0; i < projectUIListeners.size(); i++ ) {
                            projectUIListeners.get( i ).addProcess( selectedPluginView.getPlugin().getPluginName(), e.getPoint() );
                        }
                    }
                    
                } catch( JIPSException je ) {
                    JIPSExceptionHandler.handleException( je, true );
                }
        }
        
        if( selectedConnector != null ) {
        	this.selectedConnector = null;
        	repaint();
        }
    }

    public void mouseExited( MouseEvent e ) {
    	if( attachedPluginView )
			try {
				removeSelectedPluginView();
			} catch( JIPSException je ) {
				JIPSExceptionHandler.handleException( je, true );
			}
    	
    }
    	
    public void mouseEntered( MouseEvent e ) {
    	try {
    		selectedPluginView = PluginPane.getInstance().getSelected();
			
			if( selectedPluginView != null )
				attachedPluginView = true;
			else
				attachedPluginView = false;
				
		} catch (JIPSException je) {
			JIPSExceptionHandler.handleException( je, true );
		}
    }

    /*
     * Update the size of the current rectangle
     * and call repaint.  Because currentRect
     * always has the same origin, translate it
     * if the width or height is negative.
     *
     * For efficiency (though
     * that isn't an issue for this program),
     * specify the painting region using arguments
     * to the repaint() call.
     *
     */
    void updateSize( MouseEvent e ) {
        int x = e.getX();
        int y = e.getY();
        currentRect.setSize(x - currentRect.x,
                            y - currentRect.y);
        updateDrawableRect( getWidth(), getHeight() );
        Rectangle totalRepaint = rectToDraw.union( previousRectDrawn );
        repaint( totalRepaint.x, totalRepaint.y,
                 totalRepaint.width, totalRepaint.height );
    }

    /*
     * This is not a bug (bug in the test).
     * In the test user foes the following:
     *  bx = e.getX() - sx;
     *  by = e.getY() - sy;
     *  ((Component) selection).setLocation (bx, by);
     *
     *  in this code e is MOUSE_DRAGGED event;
     *  sx, sy are initialized on start of drag and isn't changed during it.
     *  selection is a source of the event (button which we drag)
     *
     *  The problem is that coordinates of MouseEvent are releated
     *  to the source components, but Component.setLocation() consider
     *  coordinates as coordinates related to component's parent.
     *  So we have the follows situation:
     *  0. Button location in the frame is 32, 32
     *  1. we start drag (e.g. MouseEvent has x=0 and y=0)
     *  2. move tthe button to (0,0) in his parent
     *  3. drag mouse on 1 pixel,  new MouseEvent will have (32+1, 32+1) location.
     *  4. we move button to (33, 33) (next grag event will be with (1,1) location)
     *  and so on, so forth.
     *
     *  To fix the test we should translate event's position into parents coordinates
     *  before changing location of the button:
     *          bx = e.getX() - sx;
     *          by = e.getY() - sy;
     *          Point loc = ((Component) selection).getLocation();
     *          ((Component) selection).setLocation(loc.x + bx, loc.y + by);
     *
     */
    public void updatePosition( MouseEvent e, int originX, int originY ) throws JIPSException {

            float restX = 0;
            float restY = 0;

            int x = e.getX();
            int y = e.getY();

            int deskW  = getWidth();
            int deskH  = getHeight();

            Point loc = null;

            for ( int i = 0; i < selectedProcesses.size(); i++ ) {


                restX = 0;
                restY = 0;

                for( int j = 0; j < projectUIListeners.size(); j++ )
                    projectUIListeners.get( j ).setProcess( selectedProcesses.get( i ).getID() );

                loc = baseProcess.getLocation();


                // just move obejct, if the new position is located in the dekstop
                if( loc.x + x > 0 && loc.y + y > 0 && loc.x + x < deskW && loc.y + y < deskH ) {
                    int newX = loc.x + x - originX;
                    int newY = loc.y + y - originY;

                    // 	set new postion anchored to the grid
                    if( newX != 0 && newY != 0 ) {
                        restX = newX % rSize;
                        restY = newY % rSize;

                        if( restX >= rSize / 2 ) newX = newX + rSize - ( newX % rSize );
                        if( restX < rSize / 2 ) newX = newX - ( newX % rSize );

                        if( restY >= rSize / 2 ) newY = newY + rSize - ( newY % rSize );
                        if( restY < rSize / 2 ) newY = newY - ( newY % rSize );
                    }

                    baseProcess.setLocation( newX - baseProcess.getEmtpyBorder(),
                                    newY - baseProcess.getEmtpyBorder() );
                    repaint();
                }
                
            }
    }

    public void setSelectedProcesses( JIPSObjectList<BaseProcess> selectedProcesses ) {
        this.selectedProcesses = selectedProcesses;
    }

    public void setBaseProcess(BaseProcessUI baseProcess) {
        this.baseProcess = baseProcess;
    }

	public BaseConnector getSelectedConnector() {
		return selectedConnector;
	}

	public void setSelectedConnector(BaseConnector selectedConnector) {
		this.selectedConnector = selectedConnector;
	}

	public void tellPosition( Point compPos, int x, int y ) {
		
        if( this.selectedConnector != null ) {
    		mousePos.x = compPos.x + x;
            mousePos.y = compPos.y + y;
        
            repaint();
        }
	}
	
	private void removeSelectedPluginView() throws JIPSException {
		attachedPluginView = false;
		selectedPluginView = null;
		repaint();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed( KeyEvent e ) {
		if( e.getKeyCode() == KeyEvent.VK_DELETE ) {
			try {
				for( int i = 0; i < projectUIListeners.size(); i++ )
					projectUIListeners.get( i ).deleteSelectedProcesses();
	        } catch( JIPSException je ) {
	            JIPSExceptionHandler.handleException( je, true );
	        }
		}
		
		if( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
			try {
				PluginPane.getInstance().deselectAllPluginViews();
				removeSelectedPluginView();
			} catch( JIPSException je ) {
				JIPSExceptionHandler.handleException( je, true );
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased( KeyEvent e ) {}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped( KeyEvent e ) {}
}
