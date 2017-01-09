/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */
package de.karnik.jips.gui;

import de.karnik.jips.CommonFunctions;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.common.lang.Translator;
import de.karnik.jips.gui.frames.BaseFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Vector;

/**
 * Helper Class for frame handling.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.7
 */
public class FrameHelper {

    /**
     * Vector to store the frames.
     */
    private static Vector<BaseFrame> frames = new Vector<BaseFrame>();

    /**
     * The Window for the theme change message.
     */
    private static Window w = null;

    /**
     * Removes the given BaseFrame from the base frame-vector.
     *
     * @param baseFrame The BaseFrame to remove.
     * @return <strong>true</strong> if the baseFrame has been removed. <strong>false</strong> otherwise.
     */
    public static boolean removeFrame( BaseFrame baseFrame ) {
        return FrameHelper.frames.remove( baseFrame );
    }

    /**
     * Returns the BaseFrame with the specified index.
     *
     * @param index The index.
     * @return The BaseFrame with the specified index.
     * @throws JIPSException
     */
    public static BaseFrame getFrame( int index ) throws JIPSException {
        BaseFrame baseFrame = null;

        try {
            baseFrame = FrameHelper.frames.get( index );
        } catch (ArrayIndexOutOfBoundsException aioobe ) {
            throw new JIPSException( "002D", aioobe, false );
        }

        return baseFrame;
    }

    /**
     * Returns the BaseFrame with the specified title.
     *
     * @param title The title.
     * @return The BaseFrame with the specified title.
     */
    public static BaseFrame getFrame( String title ) {
        Iterator frameIterator = FrameHelper.frames.iterator();
        while ( frameIterator.hasNext() ) {
            Object object = frameIterator.next();
            if ( object instanceof BaseFrame )
                if ( ( ( BaseFrame ) object ).getTitle().equals( title ) )
                    return ( BaseFrame ) object;
        }
        return null;
    }

    /**
     * Adds a BaseFrame to the frame-vector.
     *
     * @param baseFrame The BaseFrame to add.
     * @return <strong>true</strong> if the BaseFrame has been added. <strong>false</strong> otherwise.
     */
    public static boolean addFrame( BaseFrame baseFrame ) {
        return FrameHelper.frames.add( baseFrame );
    }

    public static void updateSwingSets() throws JIPSException {

        JIPSVariables vars = JIPSVariables.getInstance();
        JFrame.setDefaultLookAndFeelDecorated( vars.isDecorated() );
        JDialog.setDefaultLookAndFeelDecorated( vars.isDecorated() );

        try {
            Iterator frameIterator = frames.iterator();
            while ( frameIterator.hasNext() ) {
                Object object = frameIterator.next();
                  if ( object instanceof BaseFrame ) {
                      ( ( BaseFrame) object ).setUndecorated( vars.isDecorated() );
                      SwingUtilities.updateComponentTreeUI( ( BaseFrame ) object );
                }
            }
        } catch ( IllegalComponentStateException icse ) {
            throw new JIPSException( "002E", icse, true );
        }
    }

    /**
     * Sets all BaseFrames in the frame-vector to the given visibility.
     *
     * @param visible <strong>true</strong> to set all BaseFrames visible. <strong>false</strong> otherwise.
     */
    public static void setWindowsVisible( boolean visible ) {

        Iterator frameIterator = FrameHelper.frames.iterator();
        while( frameIterator.hasNext() ) {
            Object object = frameIterator.next();
            if( object instanceof BaseFrame ) {
                if( visible )  {( ( BaseFrame )object ).setVisible( true );
                } else {
                    ( ( BaseFrame )object ).setVisible( false );
                    ( ( BaseFrame )object ).dispose();
                }
            }
        }
    }

    /**
     * Hides all BaseFrames in the frame-vector.
     */
    public static void hideAllWindows() {
        FrameHelper.setWindowsVisible( false );
    }

    /**
     * Shows all BaseFrames in the frame-vector.
     */
    public static void showAllWindows() {
        FrameHelper.setWindowsVisible( true );
    }

    /**
     * Shows the indeterminate JProgressBar.
     */
    public static void showIndeterminateProgressBar() throws JIPSException {

        Translator trans = Translator.getInstance();

        int x = 340;
        int y = 80;

        w = new Window( null );

        w.setSize( x, y );
        w.setLocation( CommonFunctions.getMiddleOfScreen( x, y ) );
        w.setBackground( Color.BLACK );

        Panel p = new Panel( new BorderLayout() );
        p.setBackground( Color.lightGray );

        Label l = new Label();
        Font newFont = new Font( Font.SANS_SERIF, Font.BOLD, 20 );

        p.add( l, BorderLayout.CENTER );
        w.add( p );
        w.setVisible( true );

        p.setBounds( p.getX()+1, p.getY()+1, p.getWidth()-2, p.getHeight()-2 );
        l.setFont( newFont );
        l.setForeground( Color.BLACK );
        l.setAlignment( Label.CENTER );
        l.setText( trans.getTranslation( "change_theme" ) );
        w.repaint();

    }

    /**
     * Hides the indeterminate JProgressBar.
     */
    public static void hideIndeterminateProgressBar() {
        if( w != null ) {
            w.dispose();
            w = null;
        }

    }
}