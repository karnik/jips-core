/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.gui.objects;


import javax.swing.*;
import java.awt.*;

/**
 * The StatusField class contains class fields and methods for the status fields.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.3
 */
public class StatusField extends JLabel {

	/**
	 * The serial version uid.
	 */
	private static final long serialVersionUID = 6596738968271306502L;

	/**
	 * Constructs the status field.
	 *
	 * @param initText the initial text
	 * @param width the minimum width
	 */
	public StatusField( String initText, int width ) {

		Dimension d = new Dimension( width, 22 );

		this.setMinimumSize( d );
		this.setPreferredSize( d );

		setBorder( Borders.BL_BORDER );
		setText( initText );
	}
}
