/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.gui.pluginsview;

import de.karnik.jips.PluginStorage;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.plugin.Plugin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class PluginList extends JComponent implements MouseListener {

	private static final long serialVersionUID = -3534235692380513220L;
	private JLabel topLabel = null;
	private String title = null;
	private Vector<Plugin> plugins = null;
	private Vector<PluginView> pluginViews = new Vector<PluginView>();

	public PluginList(String title, int pluginType ) throws JIPSException {

		PluginStorage ps = PluginStorage.getInstance();
		plugins = ps.getPluginsByType( pluginType );

		setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();


		this.title = title;

		for(int i = 0; i < plugins.size(); i++ ) {
			PluginView pv = new PluginView( plugins.get( i ) );
			pv.setPreferredSize(new Dimension(220, 70));
			pv.addMouseListener( this );

			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.5;
			c.gridx = 0;
			c.gridy = i;
			add( pv, c );
			pluginViews.add( pv );
		}

		JLabel button = new JLabel( "" );
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       //reset to default
		c.weighty = 1.0;   //request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 0;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = plugins.size();       //third row
		add(button, c);


	}

	public int getPluginCount() {
		return plugins.size();
	}

	public String getTitle() {
		return title;
	}

	protected void setTitle(String title ) {
		this.title = title;
		topLabel.setText( this.title );
	}

	public String getTitleWithCount() {
		return getTitle() + " (" + getPluginCount() + ")";
	}

	public PluginView getSelected() {
		for(int i = 0; i < pluginViews.size(); i++ )
			if( pluginViews.get( i ).isSelected() )
				return pluginViews.get( i );

		return null;
	}

	public void deselectAllPluginViews(PluginView pv ) {
		for(int i = 0; i < pluginViews.size(); i++ )
			if( !pluginViews.get( i ).equals( pv ) )
				pluginViews.get( i ).setSelected( false );
	}

	public void deselectAllPluginViews() {
		deselectAllPluginViews( null );
	}

	@Override
	public void mouseClicked(MouseEvent e ) {
		PluginView pv = ( PluginView )e.getComponent();
		deselectAllPluginViews( pv );

		pv.setSelected( !pv.isSelected() );

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
