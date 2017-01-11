/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui;

import de.karnik.jips.*;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.JIPSMessage;
import de.karnik.jips.common.config.JIPSConstants;
import de.karnik.jips.gui.frames.AboutFrame;
import de.karnik.jips.gui.frames.BaseFrame;
import de.karnik.jips.gui.frames.PrefFrame;
import de.karnik.jips.gui.frames.internal.InternalConsole;
import de.karnik.jips.gui.frames.internal.InternalProjectFrame;
import de.karnik.jips.gui.menu.BaseMenuBar;
import de.karnik.jips.gui.menu.MainMenu;
import de.karnik.jips.gui.menu.MenuBarToggleItem;
import de.karnik.jips.gui.objects.Borders;
import de.karnik.jips.gui.objects.MemStatusThread;
import de.karnik.jips.gui.objects.StatusBar;
import de.karnik.jips.gui.pluginsview.PluginPane;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyVetoException;


/**
 * The MainFrame class contains class fields and methods for the main frame.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.1
 */
public class MainFrame extends BaseFrame implements ActionListener, ComponentListener, InternalFrameListener {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 176233491791178125L;
  /**
   * Static varibales instance for the singleton.
   */
  private static MainFrame instance = null;
    private MainMenu mainMenu						= null;
    private StatusBar statusBar						= null;
    private JIPSObjectList<JIPSProject> projects	= new JIPSObjectList<JIPSProject>();
    private BaseMenuBar	mainMenuBar					= null;
    private BaseMenuBar	runMenuBar					= null;
    private JDesktopPane jDesktopPane				= null;
    private int projectCounter						= 0;
	private JSplitPane jsplit1 						= null;
    private JPanel menuBarPanel						= null;

  private MainFrame(String name) throws JIPSException {
    super(name, 1200, 1000);
    init();
    generateAll();
    setVisible(true);
    addComponentListener(this);

    // display welcome message
    MsgHandler.consoleMSG(
            new JIPSMessage(
                    JIPSMessage.INFORMATION, JIPSConstants.WELCOME_MSG + " - " + trans.getTranslation("welcome"), ""),
            false);

  }

  /**
     * Returns the mainframe instance.
     *
     * @return the main frame object.
     */
    public static MainFrame getInstance( String name ) throws JIPSException {

        if( instance == null ) {
            instance = new MainFrame( name );
        }
        return instance;
    }

    /**
     * Returns the mainframe instance.
     *
     * @return the main frame object.
     */
    public static MainFrame getInstance() throws JIPSException {

        if( instance == null ) {
            instance = new MainFrame( "blank" );
        }
        return instance;
    }

    private void init() throws JIPSException {

        setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
        jwa.setCloseAll( true );

        mainMenu = new MainMenu( JIPSConstants.JIPS_MAINMENU_CONFIG, this );
        menuBarPanel = new JPanel( new FlowLayout( FlowLayout.LEFT, 0, 0 ) );
        mainMenuBar = new BaseMenuBar( JIPSConstants.JIPS_MAINMENUBAR_CONFIG, this );
        runMenuBar = new BaseMenuBar( JIPSConstants.JIPS_RUNMENUBAR_CONFIG, this );
        
    	if( runMenuBar.getMenuBarItem( "project_run" ) != null )
    		runMenuBar.getMenuBarItem( "project_run" ).setEnabled( false );
    	if( runMenuBar.getMenuBarItem( "project_pause" ) != null )
    		runMenuBar.getMenuBarItem( "project_pause" ).setEnabled( false );
    	if( runMenuBar.getMenuBarItem( "project_stop" ) != null )
    		runMenuBar.getMenuBarItem( "project_stop" ).setEnabled( false );

        jDesktopPane = new JDesktopPane();

        statusBar = new StatusBar();
        MemStatusThread rMem = new MemStatusThread();
        rMem.execute();
    }

    private void generateAll() throws JIPSException {
    	JScrollPane pluginScroll = null;
    	
    	// add menu
        setJMenuBar( MainMenu.getMainMenuBar() );

        // add the menu bar panel
        menuBarPanel.setBorder( Borders.E2_BORDER );
        cp.add( menuBarPanel, BorderLayout.NORTH );

        // add menu bar
        menuBarPanel.add( mainMenuBar.getJtb() );
        menuBarPanel.add( runMenuBar.getJtb() );

        // add the status bar
        cp.add( statusBar, BorderLayout.SOUTH );

        // add the desktop pane
        pluginScroll = new JScrollPane( PluginPane.getInstance() );
        pluginScroll.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        
        jsplit1 = new JSplitPane( JSplitPane.VERTICAL_SPLIT, jDesktopPane, InternalConsole.getInstance() );
        
        jsplit1.setContinuousLayout( true );
        jsplit1.setOneTouchExpandable( false );
        jsplit1.setDividerLocation( getHeight() - 200 );
        
        cp.add( pluginScroll, BorderLayout.WEST );
        cp.add( jsplit1, BorderLayout.CENTER );
        
    }

    /**
     * Creates a new InternalFrame with the specified project as content.
     *
     * @param project The project to add to the Frame.
     * @return The new JInternalFrame.
     */
    public boolean createNewInternalFrame( JIPSProject project ) throws JIPSException {
        InternalProjectFrame iFrame;
        JScrollPane jsp = new JScrollPane( project.getMainDesktop() );

        iFrame = new InternalProjectFrame( project.getJIPSProjectDataModel().getProjectName(),  // title
                                     true,                    // resizable
                                     true,                    // closeable
                                     true,                    // maximizable
                                     true );                  // iconifiable


        iFrame.setProjectID( project.getID() );
        iFrame.setBounds( 20, 20, jDesktopPane.getWidth() / 2, jDesktopPane.getHeight() / 2 );
        iFrame.add( jsp );
        iFrame.setVisible( true );
        iFrame.addInternalFrameListener( this );
                
        projects.add( project );
        jDesktopPane.add( iFrame );
        
        try {
        	iFrame.setSelected( true );
        	iFrame.setMaximum( true );
        } catch( PropertyVetoException pve ) {
        	throw new JIPSException( "0030", pve, false );
        }

        projectCounter++;
        
        return true;
    }

    /**
     * Creates a new project.
     */
    public void createNewProject() throws JIPSException {
        StatusBar.setField1( trans.getTranslation( "stb_working" ) );

        Runnable runnable = new Runnable() {
          public void run() {
              for ( int i = 0; i < 101; i++ ) {
                  try {
                      Thread.sleep( 10 );
                  } catch ( InterruptedException ex ) {}
                  StatusBar.setField2( i, 101 );
              }
              StatusBar.resetField2();
              StatusBar.setField1( trans.getTranslation( "stb_ready" ) );
          }
        };

        Thread thread = new Thread( runnable );
        thread.start();

        // create new project
      new JIPSProject(CommonFunctions.getUUID(),
              trans.getTranslation( "new_project" )
                            + " " + ( projectCounter + 1 ) );
    }

    /**
     * Shows active projects.
     */
    public void showProject( int i ) {
        MsgHandler.debugMSG( "Show Project with id: ", true );
        MsgHandler.debugMSG( "   ProjectID:       " + projects.get( i ).getID(), true );
        MsgHandler.debugMSG( "   ProjectName:     " + projects.get( i ).getJIPSProjectDataModel().getProjectName(), true );
    }

    private void closeProject() {
        JInternalFrame iFrame = jDesktopPane.getSelectedFrame();
        if( iFrame != null )
            iFrame.dispose();
    }

    /**
     * @return Returns the projects.
     */
    public JIPSObjectList<JIPSProject> getProjects() {
        return projects;
    }

    public JIPSProject getSelectedProject() {
        JInternalFrame iFrame  = jDesktopPane.getSelectedFrame();
        String projectID = null;

        if( iFrame instanceof InternalProjectFrame )
            projectID = ( ( InternalProjectFrame )iFrame ).getProjectID();

        if( projectID != null )
            for( int i = 0; i < projects.size(); i++ )
                if( projects.get( i ).getID().equals( projectID ) )
                    return projects.get( i );

        return null;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public JPanel getMenuBarPanel() {
        return menuBarPanel;
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed( ActionEvent event ) {

        try {

            // show the ActionCommand
            if( vars.debugMode ) MsgHandler.debugMSG( ( "MenuAction: " +
                    event.getActionCommand()
                    .toString() ), true );

            // 	try to create a new project
            if( compareEvent( event, trans.getTranslation( "new_project" ) ) ) {
                createNewProject();
            }

            // try to close the selected project
            else if( compareEvent( event, trans.getTranslation( "close_project" ) ) ) {
                closeProject();
            }

            // 	show the about frame
            else if( compareEvent( event, trans.getTranslation( "about" ) ) ) {
                FrameHelper.addFrame( new AboutFrame(
                                        trans.getTranslation( "about_jips") ) );
                MainMenu.setAll( false );
            }

            // save the project
            else if( compareEvent( event, trans.getTranslation( "save" ) ) ) {
            	getSelectedProject().saveProject();
            }
            
            // run all processes
            else if( compareEvent( event, trans.getTranslation( "project_run" ) ) ) {
            	if( runMenuBar.getMenuBarItem( "project_run" ) != null )
            		runMenuBar.getMenuBarItem( "project_run" ).setEnabled( false );
            	if( runMenuBar.getMenuBarItem( "project_pause" ) != null )
            		runMenuBar.getMenuBarItem( "project_pause" ).setEnabled( true );
            	if( runMenuBar.getMenuBarItem( "project_stop" ) != null )
            		runMenuBar.getMenuBarItem( "project_stop" ).setEnabled( true );
            	
            	getSelectedProject().runProcesses();

            }

            // pause all processes
            else if( compareEvent( event, trans.getTranslation( "project_pause" ) ) ) {
            	if( runMenuBar.getMenuBarItem( "project_run" ) != null )
            		runMenuBar.getMenuBarItem( "project_run" ).setEnabled( true );
            	if( runMenuBar.getMenuBarItem( "project_pause" ) != null )
            		runMenuBar.getMenuBarItem( "project_pause" ).setEnabled( false );
            	if( runMenuBar.getMenuBarItem( "project_stop" ) != null )
            		runMenuBar.getMenuBarItem( "project_stop" ).setEnabled( true );
            	
            	getSelectedProject().pauseProcesses();
            }
            
            // stop all processes
            else if( compareEvent( event, trans.getTranslation( "project_stop" ) ) ) {
            	if( runMenuBar.getMenuBarItem( "project_run" ) != null )
            		runMenuBar.getMenuBarItem( "project_run" ).setEnabled( true );
            	if( runMenuBar.getMenuBarItem( "project_pause" ) != null )
            		runMenuBar.getMenuBarItem( "project_pause" ).setEnabled( false );
            	if( runMenuBar.getMenuBarItem( "project_stop" ) != null )
            		runMenuBar.getMenuBarItem( "project_stop" ).setEnabled( false );
            	
            	getSelectedProject().stopProcesses();
            }
            
            // 	show the raster
            else if( compareEvent( event, trans.getTranslation( "grid" ) ) ) {
                vars.setDesktopRasterEnabled( !vars.isDesktopRasterEnabled() );
                StatusBar.setGrid( vars.isDesktopRasterEnabled() );

                // toggle toggle-button, if action was fired from menu
                Object o = mainMenuBar.getMenuBarItem( "grid" );
                if ( o instanceof MenuBarToggleItem ) {
                    MenuBarToggleItem mbti = ( MenuBarToggleItem )o;
                    if( mbti != event.getSource() )
                        mbti.setSelected( !mbti.isSelected() );
                }

                repaint();
            }

            // show the prefs frame
            else if( compareEvent( event, trans.getTranslation( "preferences" ) ) ) {
                FrameHelper.addFrame( new PrefFrame( trans.getTranslation( "preferences") ) );

                MainMenu.setAll( false );
            }

            // open a browser with the jips homepage
            else if( compareEvent( event, trans.getTranslation( "website" ) ) ) {
                openURL( JIPSConstants.JIPS_URL );
            }

            // open a browser with the jips doc
            else if( compareEvent( event, trans.getTranslation( "help" ) ) ) {
                openURL( IO.getFilePath( JIPSConstants.JIPS_DOC, false ) );
            }

            // exit jips
            else if( compareEvent( event, trans.getTranslation( "exit" ) ) ) {

                if( vars.debugMode ) MsgHandler.debugMSGEnd( "Frame: " + getTitle(), true );

                if( MsgHandler.exitQuestion() )
                    JIPS.exitJIPS( trans.getTranslation( "exit" ) );
            }
        } catch ( JIPSException je ) {
            JIPSExceptionHandler.handleException( je );
        }

    }

    public boolean compareEvent ( ActionEvent event, String compareTo ) {
        boolean status = false;
        if( event.getActionCommand().equals( compareTo ) ) status = true;
        return status;
    }

    public boolean startsWith( ActionEvent event, String compareTo ) {
        boolean status = false;
        if( event.getActionCommand().startsWith( compareTo ) ) status = true;
        return status;
    }

    private void resizeSplitPane() {
    }
    
	public void componentHidden(ComponentEvent e) {
		resizeSplitPane();
	}

	public void componentMoved(ComponentEvent e) {
		resizeSplitPane();
	}

	public void componentResized(ComponentEvent e) {
		resizeSplitPane();
	}

	public void componentShown(ComponentEvent e) {
		resizeSplitPane();
	}

	public BaseMenuBar getRunMenuBar() {
		return runMenuBar;
	}

	@Override
	public void internalFrameActivated( InternalFrameEvent e ) {
		if( e.getInternalFrame() instanceof InternalProjectFrame ) {
			InternalProjectFrame ipf = ( InternalProjectFrame )e.getInternalFrame();
			this.setTitle( ipf.getName() );
		}
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		if( e.getInternalFrame() instanceof InternalProjectFrame ) {
			InternalProjectFrame ipf = ( InternalProjectFrame )e.getInternalFrame();
		
			String projectID = ipf.getProjectID();
			
			projects.removeByID( projectID );
			projectCounter--;
			
			System.gc();
			
		}		
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {}
}
