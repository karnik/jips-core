/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.imageops;

import de.karnik.jips.common.JIPSException;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;

public class BasicImageOperations {

    public static BufferedImage toBufferedImage( Image image ) throws JIPSException {

        if( image instanceof BufferedImage ) {
            return ( BufferedImage )image;
        }

        // ImageIcon erzeugen und auf Alphawerte testen
        image = new ImageIcon(image).getImage();
        boolean hasAlpha = hasAlpha(image);

        // Ein BufferedImage erzeugen
        BufferedImage bimage = null;

        int type = BufferedImage.TYPE_INT_RGB;
        if (hasAlpha) {
            type = BufferedImage.TYPE_INT_ARGB;
        }
        bimage = new BufferedImage( image.getWidth( null ), image.getHeight( null ), type );

        // Bilddaten an das BufferedImage bergeben
        Graphics g = bimage.createGraphics();

        g.drawImage( image, 0, 0, null );
        g.dispose();

        return bimage;
    }

    public static boolean hasAlpha( Image image ) throws JIPSException{

        // Variablen fr das ColorModel und den PixelGrabber
        ColorModel cm = null;
        PixelGrabber pg = null;

        // Abfragen, ob das Image ein BufferedImage ist
        // und evtl. die Rckgabe
        if ( image instanceof BufferedImage ) {
            BufferedImage bimage = ( BufferedImage )image;
            return bimage.getColorModel().hasAlpha();
        }

        // Erzeuge einen PixelGrabber
        pg = new PixelGrabber(image, 0, 0, 1, 1, true);

        // Versuche ein Pixel auszulesen
        try {
            pg.grabPixels();
        } catch (Exception e) {
            throw new JIPSException( "0006", e, false );
        }

        // Versuche das Colormodel auszulesen
        try {
            cm = pg.getColorModel();
        } catch (Exception e) {
            throw new JIPSException( "0007", e, false );
        }

        return cm.hasAlpha();
    }
}