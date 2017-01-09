/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.pluginsview;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.lang.Translator;
import de.karnik.jips.common.plugin.Plugin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

public class PluginPane extends JPanel implements ItemListener {

  private static final long serialVersionUID = 1L;
  private static PluginPane instance;

  private JPanel cards = null;
  private Vector<PluginList> pluginLists = new Vector<PluginList>();

  private PluginPane() throws JIPSException {
    super();

    setLayout(new BorderLayout());

    Translator trans = Translator.getInstance();

    pluginLists.add(new PluginList(trans.getTranslation("ea_menu"), Plugin.TYPE_IO));
    pluginLists.add(new PluginList(trans.getTranslation("point_menu"), Plugin.TYPE_POINT_OPERATION));
    pluginLists.add(new PluginList(trans.getTranslation("filter_menu"), Plugin.TYPE_FILTER_OPERATION));
    pluginLists.add(new PluginList(trans.getTranslation("edge_menu"), Plugin.TYPE_EDGE_OPERATION));
    pluginLists.add(new PluginList(trans.getTranslation("fourier_menu"), Plugin.TYPE_FOURIER_OPERATION));
    pluginLists.add(new PluginList(trans.getTranslation("undefined_menu"), Plugin.TYPE_UNDEFINED));

    String comboBoxItems[] = new String[pluginLists.size()];
    cards = new JPanel(new CardLayout());

    for (int i = 0; i < pluginLists.size(); i++) {
      comboBoxItems[i] = pluginLists.get(i).getTitleWithCount();
      cards.add(pluginLists.get(i), pluginLists.get(i).getTitleWithCount());
    }

    JPanel comboPanel = new JPanel();
    JComboBox cb = new JComboBox(comboBoxItems);
    cb.setEditable(false);
    cb.addItemListener(this);
    comboPanel.add(cb);

    add(comboPanel, BorderLayout.NORTH);
    add(cards, BorderLayout.CENTER);
  }

  public static PluginPane getInstance() throws JIPSException {
    if (instance == null)
      instance = new PluginPane();

    return instance;
  }

  public PluginView getSelected() {
    for (int i = 0; i < pluginLists.size(); i++) {
      PluginView pv = pluginLists.get(i).getSelected();

      if (pv != null) return pv;
    }

    return null;
  }

  public void deselectAllPluginViews() {
    for (int i = 0; i < pluginLists.size(); i++) {
      pluginLists.get(i).deselectAllPluginViews();
    }
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    CardLayout cl = (CardLayout) (cards.getLayout());
    cl.show(cards, (String) e.getItem());

    deselectAllPluginViews();
  }
}