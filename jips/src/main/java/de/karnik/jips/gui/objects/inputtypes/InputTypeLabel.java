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

import javax.swing.*;

/**
 * The InputTypeLabel class contains class fields and methods for labels.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.1
 * @since v.0.0.6
 */
public class InputTypeLabel extends InputType {

	/**
	 * The serial version uid.
	 */
	private static final long serialVersionUID = 8548341869235546555L;

	public InputTypeLabel( String value, String id, boolean trans ) throws JIPSException {
		super( value, id, STRING, trans );
	}

	public void addComponent() {
			comp  = goh.getBoldLabel( value, translate, ""  );
			add( comp );
	}

	public void resetValue() {}

	public String getValue() {
		return ( ( JLabel )comp ).getText();
	}

}
