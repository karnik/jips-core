/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.gui.menu;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.common.lang.Translator;
import de.karnik.xml.XMLControl;

import java.awt.event.ActionListener;

/**
 * The BaseMenu class contains class fields and methods for all menus.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.4
 */
public class BaseMenu extends XMLControl {

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -1810757909128052600L;

	/**
	 * The frame where the menu should be placed.
	 */
	//protected MainFrame master;
	/**
	 * The object to hold the translator functions.
	 */
	protected Translator trans;
	/**
	 * The object to hold the JIPS variables and functions.
	 */
	protected JIPSVariables vars;

    /**
     * The object to hold the ActionListener;
     */
    protected ActionListener al;

	/**
	 * Constructs a new BaseMenu object with the specified parameters.
	 *
	 * @param al The action listener.
	 * @param menufile the xml file with the menu objects
	 */
	public BaseMenu( String menufile, ActionListener al ) throws JIPSException {
		super( menufile, true );

		vars = JIPSVariables.getInstance();
		trans = Translator.getInstance();
		this.al = al;
	}
}