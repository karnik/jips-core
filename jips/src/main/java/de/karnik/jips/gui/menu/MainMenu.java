/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.gui.menu;

import de.karnik.jips.MsgHandler;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.config.JIPSConstants;
import de.karnik.jips.common.config.JIPSStatus;
import de.karnik.jips.common.helper.IconFactory;
import de.karnik.jips.common.lang.Translator;
import org.jdom.Element;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Iterator;

/**
 * The MainMenu class contains class fields and methods to handle the main menu of JIPS.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.1
 */
public class MainMenu extends BaseMenu {

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -3841191154344336825L;

  /**
   * The object to hold the JMenuBar.
   */
  private static JMenuBar mainMenuBar;

  /**
   * The menu item map.
   */
  private static HashMap<String, JMenuItem> menuItemMap = null;
  /**
   * The menu map.
   */
  private static HashMap<String, JMenu> menuMap = null;

  public MainMenu(String menufile, ActionListener al) throws JIPSException {
    super(menufile, al);
    generate();
  }

  /**
   * Generates an JMenuItem with the specified options.
   *
   * @param name             the MenuItem name
   * @param shortcut         the MenuItem shortcut
   * @param mnem             the MenuItem mnemonic
   * @param iconCodeAsString the MenuItem icon code as String
   * @param trans            the translation object to translate the text
   * @param keyMask          the action key for the shortcuts
   * @param al               the ActionListener
   * @return the new JMenuItem
   */
  private static JMenuItem genMenuItem(String name, String shortcut, String mnem,
                                       String iconCodeAsString, Translator trans, int keyMask,
                                       ActionListener al) throws JIPSException {

    JMenuItem jmi;

    Icon icon = IconFactory.getInstance().getIcon(iconCodeAsString, 20);

    if (icon == null) {
      icon = new ImageIcon();
    }

    if (trans != null) {
      jmi = new JMenuItem(trans.getTranslation(name), icon);
    } else {
      jmi = new JMenuItem(name, icon);
    }

    // set the shortcut
    if (shortcut.equals("0")) {
      jmi.setAccelerator(KeyStroke.getKeyStroke(keyMask, 0));
    } else if (!shortcut.equals("")) {
      jmi.setAccelerator(KeyStroke.getKeyStroke(shortcut.toCharArray()[0], keyMask));
    }

    // set the mnemonic
    if (null != trans && !mnem.equals(""))
      jmi.setMnemonic(trans.getTranslation(mnem).toCharArray()[0]);
    jmi.addActionListener(al);

    jmi.setIconTextGap(5);
    jmi.setMargin(new Insets(0, 0, 0, 0));

    return jmi;
  }

  /**
   * Set status for the menu with the specified key.
   *
   * @param key   the menu key
   * @param value the value to set
   */
  public static void setMenu(String key, boolean value) {
    menuMap.get(key).setEnabled(value);
  }

  /**
   * Set status for the menuitem with the specified key.
   *
   * @param key   the menuitem key
   * @param value the value to set
   */
  public static void setMenuItem(String key, boolean value) {
    menuItemMap.get(key).setEnabled(value);
  }

  /**
   * Set status for all Items.
   *
   * @param status <strong>true</strong> enables the acctive menu items,
   *               <strong>false</strong> disables all
   */
  public static void setAll(boolean status) {

    Iterator menuList, menuItemList;
    menuList = menuMap.values().iterator();
    menuItemList = menuItemMap.values().iterator();

    while (menuList.hasNext())
      ((JMenu) menuList.next()).setEnabled(status);

    while (menuItemList.hasNext())
      ((JMenuItem) menuItemList.next()).setEnabled(status);

    // set special menu items
    if (status) {

      if (JIPSStatus.openendProjects == 0)
        menuItemMap.get("close_project").setEnabled(false);

    }
  }

  /**
   * Returns a HashMap with all menu items.
   *
   * @return the HashMap
   */
  public static HashMap<String, JMenuItem> getMenuItemMap() {
    return menuItemMap;
  }

  /**
   * Returns a HashMap with all menus.
   *
   * @return the HashMap
   */
  public static HashMap<String, JMenu> getMenuMap() {
    return menuMap;
  }

  public static JMenuBar getMainMenuBar() {
    return mainMenuBar;
  }

  /**
   * Set status for mainMenuBar.
   *
   * @param status <strong>true</strong> to enable the  menuitem,
   *               <strong>false</strong> otherwise
   */
  public static void setMainMenuBar(boolean status) {
    mainMenuBar.setEnabled(status);
  }

  /**
   * Initializes the MainMenuBar and sets it to the MainFrame.
   */
  private void generate() throws JIPSException {

    // create the main menu
    mainMenuBar = new JMenuBar();
    mainMenuBar.setBorderPainted(false);

    // generate maps
    menuItemMap = new HashMap<String, JMenuItem>();
    menuMap = new HashMap<String, JMenu>();

    // add menus and menu items
    generateMenuMap();
    generateItemMap();

  }

  /**
   * Generates the <code>JMenuItems</code> for the main menu. All items will
   * be stored in the Hashmap <code>menuItemMap</code>.
   */
  private void generateItemMap() throws JIPSException {
    JMenuItem menuItem;
    Iterator menuIterator, menuItemList;
    String menuName;
    Element menuElement;
    int inputEvent = 0;

    menuIterator = menuMap.keySet().iterator();

    // go through all menus of the main menu
    while (menuIterator.hasNext()) {

      // get menuname and all related items
      menuName = menuIterator.next().toString();
      menuItemList = getIteratorListOfSecondChild("menu", menuName);

      // go through all items and add them to the menu and to the HashMap
      while (menuItemList.hasNext()) {
        menuElement = (Element) menuItemList.next();

        // ignore everything except menuitems
        if (menuElement.getName().equals("menuitem")) {
          // typ seperators
          if (menuElement.getChildText("typ").equals("Separator")) {
            menuMap.get(menuName).addSeparator();

            // typ menuitems
          } else if (menuElement.getChildText("typ").equals("MenuItem")) {

            // get keyEvent
            inputEvent = getKeyAction(menuElement.getChildText("keymask"));

            // create item
            menuItem = genMenuItem(menuElement.getChildText("name"),
                    menuElement.getChildText("shortcut"),
                    menuElement.getChildText("mnem"),
                    menuElement.getChildText("icon"),
                    trans, inputEvent, al);
            // add the item to menu and HashMap
            menuMap.get(menuName).add(menuItem);
            menuItemMap.put(menuElement.getChildText("name"), menuItem);

          }
        }
      }
    }
  }

  /**
   * Creates the menu hashmap and adds the menus to the main menu.
   * All menues will be stored in the Hashmap <code>menuMap</code>.
   */
  private void generateMenuMap() {

    JMenu menu;
    Element menuElement;

    Iterator menuList = getIteratorListOfFirstChildren("menu");

    // go through the iterator list and add the menus
    while (menuList.hasNext()) {
      menuElement = (Element) menuList.next();
      menu = new JMenu();
      menu.setText(trans.getTranslation(
              menuElement.getAttribute("id").getValue()));

      String val = trans.getTranslation(menuElement.getChildText("mnem"));
      if (val != null) {
        menu.setMnemonic((Integer) (JIPSConstants.KEY_MAP.get(val)));

      } else {
        MsgHandler.debugMSG("Cannot add Mnemonic! Not Entry for: " + menuElement.getChildText("mnem"), true);
      }

      menuMap.put(menuElement.getAttribute("id").getValue(), menu);
      mainMenuBar.add(menu);
    }

  }

  /**
   * Returns the equal integer-keyEvent-value for the searchstring.
   * <br />
   * Possible values are: <br />
   * <code>CTRL</code><br />
   * <code>ALT</code><br />
   * <code>VK_DELETE</code><br />
   * <code>VK_F1</code><br />
   *
   * @param value the searchstring
   * @return the key event
   */
  private int getKeyAction(String value) {
    int key = 0;

    if (value.equals("CTRL")) {
      key = InputEvent.CTRL_MASK;
    } else if (value.equals("ALT")) {
      key = InputEvent.ALT_MASK;
    } else if (value.equals("")) {
      key = 0;
    } else if (value.equals("VK_DELETE")) {
      key = KeyEvent.VK_DELETE;
    } else if (value.equals("VK_F1")) {
      key = KeyEvent.VK_F1;
    }

    return key;
  }

  /**
   * Returns the menu item with the specified key.
   *
   * @param key the key to search for
   * @return the JMenuItem, or null
   */
  public JMenuItem getMenuItem(String key) {
    return menuItemMap.get(key);
  }

  /**
   * Returns the menu with the specified key.
   *
   * @param key the key to search for
   * @return the JMenu, or null
   */
  public JMenu getMenu(String key) {
    return menuMap.get(key);
  }
}