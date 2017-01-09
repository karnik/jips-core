/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.objects;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.config.JIPSConstants;
import de.karnik.jips.common.lang.Translator;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * The JIPSFileFilter class.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.7
 */
public class JIPSFileFilter extends FileFilter {

	/**
	 * The object to hold the translator functions.
	 */
	protected Translator trans = null;
  private String filetype = null;

  public JIPSFileFilter( String filetype ) throws JIPSException {
		this.filetype = filetype;
		trans = Translator.getInstance();
	}
	
	@Override
	public boolean accept(File f) {
	      if ( f.isDirectory() ) return true;
	      
	      
	      if( filetype == null ) { 
	    	  for( int i = 0; i < JIPSConstants.KNOWN_IMAGE_TYPES.length; i++ )
	    		  if( f.getName().toLowerCase().endsWith( JIPSConstants.KNOWN_IMAGE_TYPES[ i ] ) )
	    			  return true;
	      } else {
	    	  if( f.getName().toLowerCase().endsWith( filetype ) )
    			  return true;
	      }
	      return false;
	}

	@Override
	public String getDescription() {

		if( filetype == null ) {
			return trans.getTranslation( "all_supported_filetypes" );
		} else {
			return filetype.replace('.', ' ').trim().toUpperCase();
		}
	}

}
