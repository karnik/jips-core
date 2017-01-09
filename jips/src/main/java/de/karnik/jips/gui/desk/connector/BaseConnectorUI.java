/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.desk.connector;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.gui.desk.processing.BaseProcessUI;

import javax.swing.*;
import java.awt.*;

/**
 * The BaseConnector class contains class fields and methods for all graphical connectors in JIPS.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.4
 */
public class BaseConnectorUI extends JComponent {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -1617188016516346595L;

    protected int absX = 0;
    protected int absY = 0;

    protected BasicStroke fatStroke = new BasicStroke( 2.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER );
    protected BasicStroke normalStroke = new BasicStroke( 1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER );

    private Color connectorBorderColor = Color.BLACK;
    private Color background = Color.WHITE;

    /**
     * The polygon for the triangle.
     */
    private Polygon triangle = new Polygon();

    /**
     * This class is uninstantiable with the default constructor.
     */
    private BaseConnectorUI() {}

    /**
     * Constructs a new BaseConnector object with the specified options.
     *
     * @param width the width of the connector
     * @param height the height of the connector
     * @param x the x position of the connector
     * @param y the y position of the connector
     */
    public BaseConnectorUI( int width, int height, int x, int y ) throws JIPSException {
        this();
    	setBounds( x, y, width, height );

        if( width > 4 && height > 4 )
            buildPolygon();
    }

    private void buildPolygon() {
        triangle.reset();
        triangle.addPoint( 4, 4 );
        triangle.addPoint( ( getWidth() - 4 ), ( ( getHeight() ) / 2 ) );
        triangle.addPoint( 4, ( getHeight() - 4 ) );
    }

    public void refresh() {
        buildPolygon();
    }

    private void findAbsMidPosition() {
        BaseProcessUI bp = ( BaseProcessUI )getParent();

        absX = ( bp.getX() + getX() ) + getWidth() / 2;
        absY = ( bp.getY() + getY() ) + getHeight() / 2;

    }

    public void paintComponent( Graphics g ) {

        findAbsMidPosition();

        Graphics2D g2 = ( Graphics2D )g;

        g2.setStroke( fatStroke );

        g2.setColor( background );
        g2.fillRect( 0, 0, getWidth(), getHeight() );
        g2.setColor( connectorBorderColor );
        g2.drawRect( 1, 1, ( getWidth() - 2 ), ( getHeight() - 2 ) );

        g2.setColor( connectorBorderColor );
        g2.fillPolygon( triangle );
    }

    /**
     * @return Returns the absX.
     */
    public int getAbsX() {
        return absX;
    }

    /**
     * @param absX The absX to set.
     */
    public void setAbsX(int absX) {
        this.absX = absX;
    }

    /**
     * @return Returns the absY.
     */
    public int getAbsY() {
        return absY;
    }

    /**
     * @param absY The absY to set.
     */
    public void setAbsY(int absY) {
        this.absY = absY;
    }

    public Color getBackground() {
        return background;
    }

  public void setBackground(Color background) {
    this.background = background;
  }
}
