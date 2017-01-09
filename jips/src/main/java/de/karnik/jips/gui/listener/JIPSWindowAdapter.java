/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.gui.listener;

import de.karnik.jips.JIPS;
import de.karnik.jips.JIPSExceptionHandler;
import de.karnik.jips.MsgHandler;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.common.lang.Translator;
import de.karnik.jips.gui.menu.MainMenu;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The JIPSWindowAdapter class contains class fields and methods for important
 * window operations.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 0.5
 * @since v.0.0.2
 */
public final class JIPSWindowAdapter extends WindowAdapter {

	/**
	 * The object to hold the JIPS variables and functions.
	 */
	private JIPSVariables vars = null;
	/**
	 * The name of the frame. (Just for Debug-Messages)
	 */
	private String name = null;
	/**
	 * The object to hold the translator functions.
	 */
	private Translator trans = null;
	/**
	 * The closeAll option. If this option is <strong>true</strong> the whole
	 * program will try to exit with an exit question dialog when the window
	 * becomes closed.
	 */
	private boolean closeAll = false;

	/**
	 * This class is uninstantiable with the default constructor.
	 */
	private JIPSWindowAdapter() {}

	/**
	 * Constructs a new JIPSWindowAdapter object with the specified parameters.
	 *
	 * @param n the name of the frame
	 */
	public JIPSWindowAdapter( String n ) throws JIPSException {
		vars = JIPSVariables.getInstance();
		trans = Translator.getInstance();
		name = n;
	}

	/**
	 * Constructs a new JIPSWindowAdapter object with the specified parameters.
	 *
	 * @param n the name of the frame
	 * @param cA the closeAll option
	 */
	public JIPSWindowAdapter( String n, boolean cA ) throws JIPSException {
		name = n;
		closeAll = cA;
		vars = JIPSVariables.getInstance();
		trans = Translator.getInstance();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing( WindowEvent e ) {
		try {
			if( vars.debugMode ) MsgHandler.debugMSGEnd( "Frame: " + name, true );

			// if closeAll is set, try to exit the program
			if( closeAll  && MsgHandler.exitQuestion() )
				JIPS.exitJIPS( trans.getTranslation( "exit" ) );

			MainMenu.setAll( true );
		} catch ( JIPSException je ) {
			JIPSExceptionHandler.handleException( je );
		}
	}

	/** Returns the closeAll.
	 * @return the closeAll
	 */
	public boolean isCloseAll() {
		return closeAll;
	}

	/**
	 * Sets the closeAll. You have to specify a translation
	 * for the exit question dialog.
	 *
	 * @param closeAll the closeAll to set
	 */
	public void setCloseAll( boolean closeAll ) throws JIPSException {
		this.closeAll = closeAll;
		trans = Translator.getInstance();
	}
}
