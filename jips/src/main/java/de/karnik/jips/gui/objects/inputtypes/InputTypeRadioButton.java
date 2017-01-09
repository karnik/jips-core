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
 * The InputTypeRadioButton class contains class fields and methods for radio buttons.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.1
 * @since v.0.0.6
 */
public class InputTypeRadioButton extends InputType {

	/**
	 * The serial version uid.
	 */
	private static final long serialVersionUID = 918689497595794059L;

	private String group = null;
	private boolean selected = false;

	public InputTypeRadioButton( String value, String id, String group, boolean selected, boolean trans ) throws JIPSException {
		super( value, id, STRING, trans );
		setGroup( group );
		setSelected( selected );
	}

	public void addComponent() {
		comp = goh.getRadioButton( value, translate, selected, null );
	    add( comp );
	}

	public void resetValue() {}

	public String getValue() {
		if( ( ( JRadioButton )comp ).isSelected() ) return "true";
		return "false";
	}

	/**
	 * @return Returns the group.
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group The group to set.
	 */
	public void setGroup( String group ) {
		this.group = group;
	}

	/**
	 * @return Returns the selected.
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected The selected to set.
	 */
	public void setSelected( boolean selected ) {
		this.selected = selected;
	}
}
