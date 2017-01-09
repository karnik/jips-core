/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */
package de.karnik.jips.gui.objects;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * The Validation class contains class fields and methods for document validation.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.5
 */
public class Validation extends PlainDocument {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 1879299245790169547L;
    private int limit;
    private boolean number = false;

    /**
     * Konstruktor für das Validationdokument
     * @param int limit: maximale Anzahl der einzugebenen Zeichen
     */
    public Validation( int newLimit ){
        super();
        if ( limit < 0 ){
            limit = 0;
        } else {
            limit = newLimit;
        }
    }

    /**
     * Funktion �berschreibt die Methode insertString von PlaintDocument
     * @param int offset: Position
     * @param String str: der String
     * @param AttributeSet attr: Attributset
     */
    public void insertString (int offset, String str, AttributeSet attr)
            throws BadLocationException {
        if (str == null) return;

        if( isNumber() ) {

            if( !str.equals( "-" ) && !str.equals( "+" ) ) {
            	try {
            		Integer.parseInt( str );
            	} catch ( NumberFormatException nfe ) {
            		return;
            	}
            }
        }

        if ( ( getLength() + str.length() ) <= limit){
            super.insertString( offset, str, attr );
        }

     }

    /**
     * @return Returns the number.
     */
    public boolean isNumber() {
        return number;
    }

    /**
     * @param number The number to set.
     */
    public void setNumber(boolean number) {
        this.number = number;
    }
}
