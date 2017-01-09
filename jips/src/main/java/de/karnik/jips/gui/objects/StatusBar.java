/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.gui.objects;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.lang.Translator;
import de.karnik.jips.gui.GUIObjectHelper;

import javax.swing.*;
import java.awt.*;

/**
 * The StatusFrame class contains class fields and methods for the status bar.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.3
 */
public class StatusBar extends JPanel {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -1182517498160756134L;
  /**
   * Status which determines the grid.
   */
  private static boolean grid = false;
    /**
     * The status field no 1.
     */
    private static StatusField field1;
    /**
     * The status field no 2.
     */
    private static JProgressBar field2;
    /**
     * The status field no 3.
     */
    private static JProgressBar field3;
    /**
     * The status field no 4.
     */
    private static StatusField field4;

    /**
     * The gui object helper object.
     */
    private GUIObjectHelper goh;

    /**
     * The object to hold the translator functions.
     */
    private Translator trans = null;

    /**
     * The GridbagLayout for the status bar.
     */
    private GridBagLayout gbl = new GridBagLayout();

    /**
     * Constructs the status bar.
     */
    public StatusBar() throws JIPSException {

        trans = Translator.getInstance();

        goh = GUIObjectHelper.getInstance();

        setLayout( gbl );

        field1 = new StatusField( " " + trans.getTranslation( "stb_ready" ), 120);
        field2 = new JProgressBar( 0, 100 );
        field2.setBorder( Borders.BL_BORDER );
        field3 = new JProgressBar( 0, 100 );

        field3.setToolTipText( trans.getTranslation( "desc_memorybar" ) );

        field3.setBorder( Borders.BL_BORDER );
        field4 = new StatusField( "", 50 );

        //      						   							x   y  w  h   wx    wy
        goh.addComponent( this, gbl, field1, 						0,  0, 3, 1,  1.0,  1.0  );
        goh.addComponent( this, gbl, field2, 						3,  0, 4, 1,  1.0,  1.0  );
        goh.addFillComponent( this, gbl, new StatusField( "", 50 ), 7,  0, 1, 1 );
        goh.addComponent( this, gbl, field3, 						8,  0, 4, 1,  1.0,  1.0  );
        goh.addComponent( this, gbl, field4, 						12, 0, 2, 1,  1.0,  1.0  );

    }

    /**
     * Returns the text of the status field no 1.
     *
     * @return the text of status field no 1
     */
    public static String getField1() {
        return field1.getText();
    }

    /**
     * Sets the text of the status field no 1.
     *
     * @param f1 the text to set
     */
    public static void setField1( String f1 ) {
        field1.setText( " " + f1 );
    }

    /**
     * Sets the value of the progress bar relativ to the maximum.
     *
     * @param value the value to set
     * @param max the maximum value
     */
    public static void setField2( int value, int max ) {

        if( value < 100 ) {
            value *= 100;
            max *= 100;
        }
        int onePercent =  max / 100;

        field2.setValue( value / onePercent );
        field2.setStringPainted( true );
        field2.setString( Integer.toString( (value / onePercent) ) + "%" );
    }

    /**
     * Refreshs the Memory-Bar.
     */
    public static void refreshMem() {

        Runtime rt = Runtime.getRuntime();

        double total = ( double )rt.totalMemory();
        double free  = ( double )rt.freeMemory();
        double div = ( double )( 1024 * 1024 );

        field3.setMaximum( ( int )total );
        field3.setValue( ( int )( total - free ) );
        field3.setStringPainted( true );

        field3.setString( Long.toString( Math.round( ( total - free ) / div ) )
                          + " / " + Long.toString( Math.round( total / div ) )
                          + " MB" );
    }

    /**
     * Resets field no 2.
     */
    public static void resetField2() {
        field2.setValue( 0 );
        field2.setStringPainted( false );
    }

    /**
     * Refreshs the status field no 4.
     */
    public static void refreshField4() {

        StringBuffer sb = new StringBuffer();

        if( grid ) sb.append( " R" ); else sb.append( "  " );

        field4.setVerticalTextPosition( StatusField.EAST );
        field4.setText( sb.toString() );
    }

    public static boolean isGrid() {
        return grid;
    }

    public static void setGrid( boolean g ) {
        grid = g;
        refreshField4();
    }
}
