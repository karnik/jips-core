/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.gui.objects;

import de.karnik.jips.MsgHandler;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.config.JIPSVariables;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The ImageCanvas class contains class fields and methods to show an image.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.1
 */
public class ImageCanvas extends JComponent {


    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -7621914058245345864L;
    /**
     * The image to show.
     */
    private BufferedImage img = null;
    /**
     * The object to hold the JIPS variables and functions.
     */
    private JIPSVariables vars = null;

    /**
     * Constructs a new ImageCanvas object with the specified parameters.
     *
     * @param myImage the image to show
     * @param v the object with the JIPS variables
     */
    public ImageCanvas( BufferedImage myImage, JIPSVariables v ) throws JIPSException {

        vars = v;

        if( vars.debugMode ) MsgHandler.debugMSGStart( "ImageCanvas", true );

        // create a MediaTracker
        img = myImage;
        MediaTracker mt = new MediaTracker( this );
        mt.addImage( img, 0 );

        // try to load the image into the MediaTracker
        try {
            mt.waitForAll();
            setSize( img.getHeight(), img.getWidth() );
        } catch( InterruptedException ie ) {
            throw new JIPSException( "000B", ie, false );
        }
        if( vars.debugMode ) MsgHandler.debugMSGEnd( "ImageCanvas", true );
      }

    /* (non-Javadoc)
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
    public void paint( Graphics g ){

          // get the width and height of the image
          // place the image in the middle of the canvas
          int xPos = 0, yPos = 0;

          int gWidth 	= getWidth();
          int gHeight 	= getHeight();

          int iWidth 	= img.getWidth( this );
          int iHeight 	= img.getHeight( this );

          xPos = (gWidth - iWidth) / 2;
          yPos = (gHeight - iHeight) / 2;

          g.drawImage( img, xPos, yPos, this );
      }

    /* (non-Javadoc)
     * @see java.awt.Component#getPreferredSize()
     */
    public Dimension getPreferredSize() {
            return new Dimension( img.getWidth(this), img.getHeight(this) );
      }

    /* (non-Javadoc)
     * @see java.awt.Component#getMinimumSize()
     */
    public Dimension getMinimumSize(){
            return getPreferredSize();
      }

    /**
     * Sets the image to show.
     *
     * @param image the image to show
     */
    public void setImg(BufferedImage image) {
          img = image;
          setSize( img.getHeight(this), img.getWidth(this) );
      }
}