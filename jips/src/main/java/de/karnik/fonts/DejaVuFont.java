/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.fonts;

import de.karnik.jips.IO;
import de.karnik.jips.common.JIPSException;

import java.awt.*;
import java.io.IOException;

/**
 * @author karnik
 *
 */
public class DejaVuFont {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * This class is uninstantiable.
     */
    private DejaVuFont() {}

    /**
     * Returns a new created DejaVu font object with bold style.
     *
     * @param size the size of the new font object
     * @return the DejaVu font object
     */
    public static Font getBold( int size ) throws JIPSException {
        Font dejaVu = null;

        try {
            dejaVu = Font.createFont( Font.TRUETYPE_FONT,
                                      IO.getFileInputStream( "de/karnik/fonts/DejaVuSans-Bold.ttf",
                                      true ) );
            dejaVu = dejaVu.deriveFont( Font.PLAIN, size );
        } catch( FontFormatException ffe ) {
            throw new JIPSException( "000C", ffe, true );
        } catch( IOException ioe ) {
            throw new JIPSException( "000D", ioe, true );
        }

        return dejaVu;
    }

    /**
     * Returns a new created DejaVu font object with plain style.
     *
     * @param size the size of the new font object
     * @return the DejaVu font object
     */
    public static Font getNormal( int size ) throws JIPSException {
        Font dejaVu = null;

        try {
            dejaVu = Font.createFont( Font.TRUETYPE_FONT,
                                      IO.getFileInputStream( "de/karnik/fonts/DejaVuSans.ttf",
                                      true ) );

            dejaVu = dejaVu.deriveFont( Font.PLAIN, size );
        } catch( FontFormatException ffe ) {
            throw new JIPSException( "000E", ffe, true );
        } catch( IOException ioe ) {
            throw new JIPSException( "000F", ioe, true );
        }

        return dejaVu;
    }

    /**
     * Returns a new created DejaVu font object with bold an oblique style.
     *
     * @param size the size of the new font object
     * @return the DejaVu font object
     */
    public static Font getBoldOblique( int size ) throws JIPSException {
        Font dejaVu = null;

        try {
            dejaVu = Font.createFont( Font.TRUETYPE_FONT,
                                      IO.getFileInputStream( "de/karnik/fonts/DejaVuSans-BoldOblique.ttf",
                                      true ) );

            dejaVu = dejaVu.deriveFont( Font.PLAIN, size );
        } catch( FontFormatException ffe ) {
            throw new JIPSException( "0010", ffe, true );
        } catch( IOException ioe ) {
            throw new JIPSException( "0011", ioe, true );
        }

        return dejaVu;
    }
}