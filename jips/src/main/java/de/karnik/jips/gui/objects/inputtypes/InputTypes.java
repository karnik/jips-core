/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */
package de.karnik.jips.gui.objects.inputtypes;

/**
 * The InputTypes interface defines fields and methods for input types.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.1
 * @since v.0.0.6
 */
public interface InputTypes {

	public static final int NUMBER       = 0;
	public static final int STRING       = 1;

	public String getValue();
	public void resetValue();
	public void addComponent();

}
