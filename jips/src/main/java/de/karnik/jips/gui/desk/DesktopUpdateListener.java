/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */
package de.karnik.jips.gui.desk;

import de.karnik.jips.common.JIPSException;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * The DesktopUpdateListener interface contains methods for updateing the desktop.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.6
 */
public interface DesktopUpdateListener {

    public void updatePosition( MouseEvent e, int originX, int originY ) throws JIPSException;
    public void tellPosition( Point compPos, int x, int y );
}
