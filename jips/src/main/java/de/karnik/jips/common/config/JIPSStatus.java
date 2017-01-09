/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.common.config;

/**
 * The JIPSStatus class contains class fields with the status of JIPS.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.4
 */
public abstract class JIPSStatus {

	/**
	 * The count of the opened Projects.
	 */
	public static int openendProjects = 0;
	
	/**
	 * The save status. If <strong>true</strong> the selected Project has been saved.
	 */
	public static boolean isSaved = false;
	
	/**
	 * This class is uninstantiable.
	 */
	private JIPSStatus() {}
}
