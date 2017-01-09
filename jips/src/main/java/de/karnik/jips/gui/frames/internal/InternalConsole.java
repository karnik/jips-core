/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */
package de.karnik.jips.gui.frames.internal;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.JIPSMessage;
import de.karnik.jips.gui.GUIObjectHelper;
import de.karnik.jips.gui.objects.Borders;

import javax.swing.*;
import java.awt.*;

/**
 * The InternalConsole...
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.1
 * @since v.0.0.7
 */
public class InternalConsole extends  JPanel {//JInternalFrame {

    /**
     *
     */
    private static final long serialVersionUID = -8604253934732548176L;
  private static InternalConsole instance = null;
    private JScrollPane scrollPane = null;
    private JList messageList = null;
    private JPanel header = null;
    private DefaultListModel listModel = null;

    private InternalConsole( String name ) throws JIPSException {

    	GUIObjectHelper goh = GUIObjectHelper.getInstance();
    	setLayout( new BorderLayout() );

    	header = new JPanel( new FlowLayout( FlowLayout.LEFT, 0, 0 ) );
    	header.add( goh.getBoldLabel( "console", true, ":" ) );
        header.setPreferredSize( new Dimension( 400, 20 ) );

      listModel = new DefaultListModel();

        InternalConsoleCellRenderer renderer = new InternalConsoleCellRenderer();

        messageList = new JList( listModel ); //data has type Object[]
        messageList.setCellRenderer( renderer );
        messageList.setSelectionMode( ListSelectionModel.SINGLE_INTERVAL_SELECTION );
        messageList.setLayoutOrientation( JList.VERTICAL );
        messageList.setVisibleRowCount( -1 );

      scrollPane = new JScrollPane( messageList );
        scrollPane.setPreferredSize( new Dimension( 400, 60 ) );

      add( header, BorderLayout.NORTH );
        add( scrollPane, BorderLayout.CENTER );
        setBorder( Borders.E3_BORDER );
        setVisible( true );
    }

  /**
   * Returns the mainframe instance.
   *
   * @return the main frame object.
   */
  public static InternalConsole getInstance() throws JIPSException {

    if (instance == null) {
      instance = new InternalConsole("Console");
    }
    return instance;
  }

    public void addMessage( JIPSMessage jm ) {
        if( jm != null )
            listModel.addElement( jm );
        
        Point point = new Point( 0, (int)(messageList.getSize().getHeight()) );
        this.scrollPane.getViewport().setViewPosition( point );
    }
}
