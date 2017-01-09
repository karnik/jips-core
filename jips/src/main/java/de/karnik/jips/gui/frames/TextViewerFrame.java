/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.frames;


import de.karnik.jips.IO;
import de.karnik.jips.common.JIPSException;

/**
 * The TextViewerFrame class contains class fields and methods for a frame to view textfiles
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.3
 */
public class TextViewerFrame extends BaseFrame {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 502778991506490556L;

    /**
     * The path to the text file.
     */
    private String filePath;
    /**
     * The string with the content of the textfile.
     */
    private String text;

    /**
     * Constructs a new TextViewerFrame object.
     *
     * @param name the name of the frame
     * @param x the width of the frame
     * @param y the height of the frame
     * @param t the translator object to translate the menu items
     * @param v the variables object
     * @param path the path to the text file
     */
    public TextViewerFrame ( String name, int x, int y, String path ) throws JIPSException {

        super( name, x, y );
        filePath = path;

        init();
        generateAll();

        setVisible( true );
    }

    /**
     * Initializes the frame.
     */
    protected void init() throws JIPSException {
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );

        text = IO.getTextFile( filePath );
        if( text == null )
            text = trans.getTranslation( "file_not_found" );
    }

    /**
     * Generates the content of the frame.
     */
    protected void generateAll() {
        goh.addComponent( cp, gbl, goh.getTextArea( text, 10,10, false ), 0, 3, 4, 4, 1.0, 1.0 );
    }

}
