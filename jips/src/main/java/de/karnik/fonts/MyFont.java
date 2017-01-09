/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.fonts;

import java.awt.*;

public class MyFont extends Font {

	/**
	 * The serial version uid.
	 */
	private static final long serialVersionUID = 5609043538379632401L;

	/**
	 * @param font
	 */
	public MyFont( Font font, int style ) {
		super( font.getAttributes() );
		
		switchStyle( style );
	}

	public MyFont( Font font ) {
		super ( font.getAttributes() );
		switchStyle( MyFont.PLAIN );
	}

	public MyFont( String name, int style, int size ) {
		super ( name, style, size );
	}

    private void switchStyle( int style ) {
    	switch( style ) {
			case MyFont.PLAIN:
				setFontStyle( style );
				break;
    		case MyFont.BOLD:
    			setFontStyle( style );
    			break;
    		case MyFont.ITALIC:
    			setFontStyle( style );
    			break;
    		case MyFont.BOLD | MyFont.ITALIC:
    			setFontStyle( style );
    			break;
    		default:
    			setFontStyle( MyFont.PLAIN );
    			break;
    	}
    }

	public void setFontStyle( int style ) {
		this.style = style;
	}

}
