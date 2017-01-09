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
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.common.lang.Translator;
import de.karnik.jips.gui.GUIObjectHelper;
import de.karnik.jips.gui.objects.Borders;

import javax.swing.*;
import java.awt.*;

/**
 * The InputType class contains class fields and methods for InputTypes.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.1
 * @since v.0.0.5
 */
public abstract class InputType extends JComponent implements InputTypes {

  protected static final int MAX_LENGTH = 20;
  /**
	 * The serial version uid.
	 */
	private static final long serialVersionUID = 7284992304166319796L;
	/**
	 * The object to hold the GUIObjectHelper.
	 */
	protected GUIObjectHelper goh = null;
	/**
	 * The object to hold the JIPS variables and functions.
	 */
	protected JIPSVariables vars = null;
	/**
	 * The object to hold the translator functions.
	 */
	protected Translator trans = null;
	protected String value = null;
	protected String id = null;

	protected int inputValueType = 0;
	protected boolean translate = false;

	protected JComponent comp = null;

	protected Point compLoc = new Point( 0, 0 );
	protected Dimension compDim = new Dimension( 0, 0 );

	/**
	 * Constructs a new InputType with the given values.
	 *
	 * @param value The value of the InputType
	 * @param id the id of the InputType for the configuration array
	 * @param inputValueType the value type of the InputType
	 * @param translate if <strong>true</strong>, the value will be translated
	 */
	public InputType( String value, String id, int inputValueType, boolean translate ) throws JIPSException {

		setLayout( new FlowLayout( FlowLayout.LEFT, 1, 1 ) );

		vars = JIPSVariables.getInstance();
		trans = Translator.getInstance();

		goh = GUIObjectHelper.getInstance();
		
		setValue( value );
		setId( id );
		setInputValueType( inputValueType );
		setTranslate( translate );

		setBorder( Borders.E2_BORDER );

		if( vars.graphicalDebugMode ) setBorder( Borders.L1R_BORDER );

	}

	/**
	 * @return Returns the comp.
	 */
	public JComponent getComp() {
		return comp;
	}

	public void setComponentLocation( int x, int y ) {
		compLoc = new Point( x, y );
	}

	public Point getComponentLocation() {
		return compLoc;
	}

	public void setComponentDimension( int width, int height ) {
		compDim = new Dimension( width, height );
	}

	public Dimension getComponentDimension() {
		return compDim;
	}

	public void setComponentBounds( int x, int y, int width, int height ) {
		compLoc = new Point( x, y );
		compDim = new Dimension( width, height );
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the inputValueType.
	 */
	public int getInputValueType() {
		return inputValueType;
	}

  /**
   * @param inputValueType The inputValueType to set.
   */
  public void setInputValueType(int inputValueType) {
    this.inputValueType = inputValueType;
  }

	/**
	 * @return Returns the translate.
	 */
	public boolean isTranslate() {
		return translate;
	}

	/**
	 * @param translate The translate to set.
	 */
	public void setTranslate(boolean translate) {
		this.translate = translate;
	}

	/**
	 * @param value The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
