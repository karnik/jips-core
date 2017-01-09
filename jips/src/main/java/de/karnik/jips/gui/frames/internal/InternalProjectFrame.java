/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.gui.frames.internal;

import javax.swing.*;

/**
 * The InternalProjectFrame...
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.1
 * @since v.0.0.7
 */
public class InternalProjectFrame extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3807721912929876086L;
	
	private String projectID = null;
	
	public InternalProjectFrame( String frameName, boolean resizable,
								 boolean closeable, boolean maximizable,
								 boolean iconifiable ) {
		
		super( frameName, resizable, closeable, maximizable, iconifiable );
	}
	
	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

}
