/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.processing;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.processing.JIPSObject;
import de.karnik.jips.gui.desk.connector.BaseConnectionUI;

/**
 * The Connection class contains class fields and methods to draw a connection between two connectors.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.5
 */
public class BaseConnection implements JIPSObject {

	/**
	 * The serial version uid.
	 */
	private static final long serialVersionUID = -6829068887194023746L;

	private String connectionID = null;

	private BaseConnectionUI bcUI = null;
	
	/**
	 * Connector with the input.
	 */
	private String baseInputConnectorID = null;
	/**
	 * Connector with the output.
	 */
	private String baseOutputConnectorID = null;

	public BaseConnection( BaseConnector output, BaseConnector input ) throws JIPSException {
		baseInputConnectorID = input.getID();
		baseOutputConnectorID = output.getID();
		bcUI = new BaseConnectionUI( output.getUI(), input.getUI() );
	}

	public BaseConnectionUI getUI() throws JIPSException {
		return bcUI;		
	}
	
	/* (non-Javadoc)
	 * @see de.karnik.jips.common.processing.JIPSObject#getID()
	 */
	public String getID() {
		return connectionID;
	}

	
	/* (non-Javadoc)
	 * @see de.karnik.jips.common.processing.JIPSObject#setID(java.lang.String)
	 */
	public void setID( String id ) {
		this.connectionID = id;
	}

	public String getBaseInputConnectorID() {
		return baseInputConnectorID; 
	}
	
	public String getBaseOutputConnectorID() {
		return baseOutputConnectorID; 
	}
}
