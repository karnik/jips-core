/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.objects.inputtypes;

import de.karnik.jips.JIPSExceptionHandler;
import de.karnik.jips.MsgHandler;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.config.JIPSConstants;
import de.karnik.jips.gui.objects.JIPSFileFilter;
import de.karnik.jips.gui.objects.Validation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The InputTypeFileChooser class contains class fields and methods for file choosers.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.1
 * @since v.0.0.6
 */
public class InputTypeFileChooser extends InputType implements ActionListener {

	/**
	 * The serial version uid.
	 */
	private static final long serialVersionUID = 8548341869235546555L;

	private int length = 0;

	public InputTypeFileChooser( String value, String id, int length,  boolean trans ) throws JIPSException {
		super( value, id, STRING, trans );
		setLength( length );
	}

	public void addComponent() {
		Validation v = null;
		v = new Validation( 1024 );
		comp = goh.getTextField( value, length, translate, v );
		JButton jb = goh.getButton( "choose_file", true, this ,null );

		add( comp );
		add( jb );
	}

	public void resetValue() {}

	public String getValue() {
		return ( ( JTextField )comp ).getText();
	}

	/**
	 * @return Returns the length.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length The length to set.
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	private void runJFileChooser() throws JIPSException {
	
		
		JFileChooser chooser = new JFileChooser();
		
		for( int i = 0; i < JIPSConstants.KNOWN_IMAGE_TYPES.length; i++ )
			chooser.addChoosableFileFilter( new JIPSFileFilter( JIPSConstants.KNOWN_IMAGE_TYPES[ i ] ) );		  
		  
		chooser.addChoosableFileFilter( new JIPSFileFilter( null ) );
		chooser.setMultiSelectionEnabled(false);
		  
		if ( chooser.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION )
			( ( JTextField )comp ).setText( chooser.getSelectedFile().getAbsolutePath() );
		     
	}
	
	
	
	public void actionPerformed( ActionEvent e ) {
		if( vars.debugMode ) MsgHandler.debugMSG( "Action: " + e.getActionCommand(), true );

		try {
			if( e.getActionCommand().equals( trans.getTranslation( "choose_file" ) ) )
				runJFileChooser();
		} catch( JIPSException je ) {
			JIPSExceptionHandler.handleException( je );
		}

	}

}


