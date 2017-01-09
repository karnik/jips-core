/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips;

import de.karnik.jips.common.JIPSException;

import java.awt.*;

/**
 * The ProjectUIListener interace...
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.7
 */
public interface ProjectUIListener {

    public void setSelected() throws JIPSException;
    public void deselectAll() throws JIPSException;
    public void setProcess( String processID ) throws JIPSException;
    public void deleteSelectedProcesses() throws JIPSException;

    public int addProcess( String processName, Point p  ) throws JIPSException;
}
