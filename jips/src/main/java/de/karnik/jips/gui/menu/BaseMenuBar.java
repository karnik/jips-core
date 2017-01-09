/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.menu;

import de.karnik.jips.common.JIPSException;
import org.jdom.Element;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  The BaseMenuBar class contains class fields and methods for all menu bars.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.4
 */
public class BaseMenuBar extends BaseMenu {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The menu item map.
     */
    private HashMap<String,AbstractButton> menuItemMap = null;

    private JToolBar jtb = new JToolBar();

    /**
     * Constructs a new BaseMenuBar object with the specified parameters.
     *
     * @param menufile the master frame
     * @param menufile the xml file with the menu objects
     */
    public BaseMenuBar( String menufile, ActionListener al ) throws JIPSException {
        super( menufile, al );

        menuItemMap  = new HashMap<String,AbstractButton>();
        generate();
    }

    /**
     * Adds an menu bar item to the menu bar.
     *
     * @param key the key for the item
     * @param item the menu bat item to set
     */
    public void addMenuBarItem( String key, MenuBarItem item ) {
        menuItemMap.put( key, item );
        jtb.add( item );
    }
    
    /**
     * Returns an abstract button (menu bar item) with the 
     * given search string.
     * 
     * @param name The search string.
     * @return The abstract button.
     */
    public AbstractButton getMenuBarItem( String name ) {
    	
    	return menuItemMap.get( name );
    }

    /**
     * Creates the menu hashmap and adds the buttons to the menu bar.
     * All buttons will be stored in the Hashmap <code>menuItemMap</code>.
     */
    private void  generate() throws JIPSException {

    	AbstractButton mbi = null;
        Element menuElement = null;

        Iterator menuList = getIteratorListOfFirstChildren( "baritem" );

        // go through the iterator list and add the menus
        while( menuList.hasNext() ) {
              menuElement = ( Element ) menuList.next();

              if( menuElement.getChildText( "typ" ).equals( "MenuBarItem" ) ) {

                  mbi =  new MenuBarItem( trans.getTranslation( menuElement.getChildText( "name" ) ),
                                            menuElement.getChildText( "icon" ) );
                  mbi.addActionListener( al );
                  menuItemMap.put( menuElement.getChildText( "name" ), mbi );
                  jtb.add( mbi );
              } else if( menuElement.getChildText( "typ" ).equals( "MenuBarToggleItem" ) ) {
                  mbi =  new MenuBarToggleItem( trans.getTranslation( menuElement.getChildText( "name" ) ),
                          menuElement.getChildText( "icon" ) );
                  mbi.addActionListener( al );
                  menuItemMap.put( menuElement.getChildText( "name" ), mbi );
                  jtb.add( mbi );
              } else if ( menuElement.getChildText( "typ" ).equals( "Separator" ) ) {
                  jtb.addSeparator();
              }
        }

    }

    public JToolBar getJtb() {
        return jtb;
    }



}
