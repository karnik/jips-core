/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.objects.inputtypes;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.gui.objects.Validation;

import javax.swing.*;

/**
 * The InputTypeTextField class contains class fields and methods for text fields.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.1
 * @since v.0.0.6
 */
public class InputTypeTextField extends InputType {

	/**
	 * The serial version uid.
	 */
	private static final long serialVersionUID = 8548341869235546555L;

	private int length = 0;

	public InputTypeTextField( String value, String id, int length,  boolean trans ) throws JIPSException {
		super( value, id, STRING, trans );
		setLength( length );
	}

	public void addComponent() {
		Validation v = null;
		int fieldLength = MAX_LENGTH;
		if( length < MAX_LENGTH ) fieldLength = length;

		v = new Validation( length );
		if( inputValueType == NUMBER ) v.setNumber( true );

		comp  = goh.getTextField( value, fieldLength, translate, v  );
		add( comp );
	}

	public void resetValue() {}

	public String getValue() {
		return ( ( JTextField )comp ).getText();
	}

	/**
	 * @return Returns the length.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length The length to set.
	 */
	public void setLength(int length) {
		this.length = length;
	}

}

