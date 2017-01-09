/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips;

import de.karnik.jips.common.processing.JIPSObject;

import java.util.ArrayList;

public class JIPSObjectList<E extends JIPSObject> extends ArrayList<E> {

	private static final long serialVersionUID = 810725588271773229L;

	/**
	 * Removes the JIPSObject which owns the given unique ID. 
	 * 
	 * @param id The unique ID of the object to remove.
	 * @return The element that was removed from the list
	 *         or <strong>null</strong> if the unique ID does not exist.
	 */
	public E removeByID( String id ) {
		
		int index = getIndexByID( id );
		
		if( index != -1)
			return remove( index );
		
		return null;
	}
	
	/**
	 * Returns the JIPSObject which owns the given unique ID. 
	 * 
	 * @param id The unique ID of the object to return.
	 * @return The element which owns the given unique ID
	 *         or <strong>null</strong> if the unique id does not exist.
	 */
	public E getByID( String id ) {
		
		int index = getIndexByID( id );
		
		if( index != -1)
			return get( index );
		
		return null;
	}	
	
	private int getIndexByID( String id ) {
		for( int i = 0; i < this.size(); i++ ) {
			if( get( i ) instanceof JIPSObject ) {
				if( ( ( JIPSObject )get( i ) ).getID().equals( id ) )
					return i;
				}
			}
		return -1;
	}
	

}
