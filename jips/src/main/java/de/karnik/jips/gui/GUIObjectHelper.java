/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.gui;

import de.karnik.fonts.MyFont;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.common.lang.Translator;
import de.karnik.jips.gui.objects.Borders;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The GUIObjectHelper class contains class fields and methods for easyer graphical usage of components.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.3
 */
public class GUIObjectHelper {

  public static final int FONT_TYPE_NORMAL = 0;
  public static final int FONT_TYPE_BOLD = 1;
  public static final int FONT_TYPE_ITALIC = 2;
  private static GUIObjectHelper instance = null;
    /**
     * The object to hold the GridBagLayout.
     */
    protected GridBagLayout gbl = null;
    /**
     * The object to hold the GridBagConstraints.
     */
    protected GridBagConstraints gbc = null;
    /**
     * A dimension object with a height and width of 0.
     */
    protected Dimension nullDim =  new Dimension( 0, 0 );
    /**
     * The object to hold the translator functions.
     */
    private Translator trans = null;

  // =============================================================
  // Labels
  // =============================================================
  /**
   * The object to hold the JIPS variables and functions.
   */
  private JIPSVariables vars = null;

    /**
     * Constructs a new GUIObjectHelper object.
     */
    private GUIObjectHelper() throws JIPSException {
        trans = Translator.getInstance();
        vars = JIPSVariables.getInstance();

        gbl = new GridBagLayout();
        gbc = new GridBagConstraints( 0, 0,
                                      0, 0,
                                      0, 0,
                                      GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST,
                                      new Insets( 2, 2, 2, 2 ),
                                      0, 0);
    }

  /**
   * Returns the mainframe instance.
   *
   * @return the main frame object.
   */
  public static GUIObjectHelper getInstance() throws JIPSException {

    if (instance == null) {
      instance = new GUIObjectHelper();
    }
    return instance;
  }

  /**
     * Returns a bold label with the specified values and options.
     *
     * @param text the text to show
     * @param t the translate option. if <strong>true</strong>, the text is going to be translated
     * @param append some text to append. maybe a ":" in lists
     * @return the bold label
     */
    public JLabel getBoldLabel( String text, boolean t, String append ) {
        JLabel jl = null;

        if ( t ) text = trans.getTranslation( text );

        jl = new JLabel( text + append );
        MyFont jlFont = new MyFont( jl.getFont(), MyFont.BOLD );
        jl.setFont( jlFont );

        if( vars.graphicalDebugMode ) jl.setBorder( Borders.L1R_BORDER );

        return jl;
    }

    /**
     * Returns a label with light gray text and the specified values and options.
     *
     * @param text the text to show
     * @param t the translate option. if <strong>true</strong>, the text is going to be translated
     * @return the label
     */
    public JLabel getLightLabel( String text, boolean t ) {
        JLabel jl = null;

        if ( t ) text = trans.getTranslation( text );

        jl = new JLabel( text );
        jl.setForeground( Color.DARK_GRAY );

        if( vars.graphicalDebugMode ) jl.setBorder( Borders.L1R_BORDER );

        return jl;
    }

    // =============================================================
  // RadioButtons
  // =============================================================

  /**
   * Returns a label with light gray and italic text.
     *
     * @param text the text to show
     * @param t the translate option. if <strong>true</strong>, the text is going to be translated
     * @param append some text to append. maybe a ":" in lists
     * @return the label
     */
    public JLabel getLightItalicLabel( String text, boolean t, String append ) {
        JLabel jl = null;

        if ( t ) text = trans.getTranslation( text );

        jl = new JLabel( text + append );
        MyFont jlFont = new MyFont( jl.getFont(), MyFont.ITALIC );
        jl.setFont( jlFont );
        jl.setForeground( Color.DARK_GRAY );

        if( vars.graphicalDebugMode ) jl.setBorder( Borders.L1R_BORDER );

        return jl;
    }

    /**
     * Returns a label with light gray and italic text. The is also the option to
     * put n blanks in front of the text.
     *
     * @param text the text to show
     * @param blanks the counter for the leading blanks
     * @param t the translate option. if <strong>true</strong>, the text is going to be translated
     * @return the label
     */
    public JLabel getLightItalicLabel( String text, int blanks, boolean t ) {
        JLabel jl = null;
        String blankString = "";

        for( int i = 0; i < blanks; i++ )
            blankString += "&nbsp;";

        if ( t ) text = trans.getTranslation( text );

        jl = new JLabel( blankString + text );
        MyFont jlFont = new MyFont( jl.getFont(), MyFont.ITALIC );
        jl.setFont( jlFont );
        jl.setForeground( Color.DARK_GRAY );

        if( vars.graphicalDebugMode ) jl.setBorder( Borders.L1R_BORDER );

        return jl;
    }

    // =============================================================
  // Buttons
  // =============================================================

  /**
   * Returns a label with the specified values and options.
     *
     * @param text the text to show
     * @param t the translate option. if <strong>true</strong>, the text is going to be translated
     * @param append some text to append. maybe a ":" in lists
     * @return the label
     */
    public JLabel getLabel( String text, boolean t, String append ) {
        JLabel jl = null;

        if ( t ) text = trans.getTranslation( text );

        jl = new JLabel( text + append );

        if( vars.graphicalDebugMode ) jl.setBorder( Borders.L1R_BORDER );

        return jl;
    }

    /**
     * Returns a radio button with the specified options.
     *
     * @param text the text for the radio button
     * @param t the translate option. if <strong>true</strong>, the text is going to be translated
     * @param status if <strong>true</strong> the button is selected
     * @param al the action listener for this object
     * @return the radio button
     */
    public JRadioButton getRadioButton( String text, boolean t, boolean status, ActionListener al ) {
        JRadioButton jrb = null;

        if( t ) text = trans.getTranslation( text );

        jrb = new JRadioButton( text, status );

        if( al != null ) jrb.addActionListener( al );

        if( vars.graphicalDebugMode ) jrb.setBorderPainted( true );
        if( vars.graphicalDebugMode ) jrb.setBorder( Borders.L1R_BORDER );

        return jrb;
    }

    /**
     * Returns a radio button with the specified options an bold text.
     *
     * @param text the text for the radio button
     * @param t the translate option. if <strong>true</strong>, the text is going to be translated
     * @param status if <strong>true</strong> the button is selected
     * @param al the action listener for this object
     * @return the radio button
     */
    public JRadioButton getBoldRadioButton( String text, boolean t, boolean status, ActionListener al ) {
        JRadioButton jrb = null;

        if( t ) text = trans.getTranslation( text );

        jrb = new JRadioButton( text, status );
        MyFont jlFont = new MyFont( jrb.getFont(), MyFont.BOLD );
        jrb.setFont( jlFont );

        if( al != null ) jrb.addActionListener( al );

        if( vars.graphicalDebugMode ) jrb.setBorderPainted( true );
        if( vars.graphicalDebugMode ) jrb.setBorder( Borders.L1R_BORDER );

        return jrb;
    }

    /**
     * Returns a button with the specified options.
     *
     * @param text the text for the button
     * @param t the translate option. if <strong>true</strong>, the text is going to be translated
     * @return the button
     */
    public JButton getButton( String text, boolean t ) {
        JButton jb = null;

        if( t ) text = trans.getTranslation( text );

        jb = new JButton( text );

        return jb;
    }

    // =============================================================
  // ComboBoxes
  // =============================================================

  /**
   * Returns a button with the specified options.
     *
     * @param text the text for the button
     * @param t the translate option. if <strong>true</strong>, the text is going to be translated
     * @param al the action listener for the button
     * @param actionCommand the actionCommand for the Button. if null, the text will be used
     * @return the button
     */
    public JButton getButton( String text, boolean t, ActionListener al,
                                    String actionCommand ) {
        JButton jb = null;

        if( t ) text = trans.getTranslation( text );

        jb = new JButton( text );

        if( al != null )
            jb.addActionListener( al );
        if( actionCommand != null )
            jb.setActionCommand( actionCommand );

        return jb;
    }

    // =============================================================
  // TextAreas
  // =============================================================

  /**
   * Returns a button with the specified options, no border and underlined blue text.
     *
     * @param text the text for the button
     * @param t the translate option. if <strong>true</strong>, the text is going to be translated
     * @param al the action listener for the button
     * @param actionCommand the actionCommand for the Button. if null, the text will be used
     * @return the button
     */
    public JButton getLinkButton( String text, boolean t, ActionListener al,
                                    String actionCommand ) {
        JButton jb = null;

        if( t ) text = trans.getTranslation( text );

        jb = new JButton( text );
        jb.setBorder( Borders.E3_BORDER );
        jb.setContentAreaFilled( false );
        jb.setForeground( Color.BLUE );
        jb.setAlignmentX( JButton.LEFT_ALIGNMENT );

        if( al != null )
            jb.addActionListener( al );
        if( actionCommand != null )
            jb.setActionCommand( actionCommand );

        return jb;
    }
    
    /**
     * Returns a button with the specified options and bold text.
     *
     * @param text the text for the button
     * @param t the translate option. if <strong>true</strong>, the text is going to be translated
     * @return the button
     */
    public JButton getBoldButton( String text, boolean t ) {
        JButton jb = null;

        if( t ) text = trans.getTranslation( text );

        jb = new JButton( text );
        MyFont jlFont = new MyFont( jb.getFont(), MyFont.BOLD );
        jb.setFont( jlFont );

        return jb;
    }

    /**
     * Returns a combo box with the specified values.
     *
     * @param values the values for the combo box
     * @param selected the index of the selected value
     * @param t the translate option. if <strong>true</strong>, the text is going to be translated
     * @return the combo box
     */
    public JComboBox getComboBox( String[] values, int selected, boolean t, ActionListener al ) {
        JComboBox jcb = null;

        if( t ) {
            for( int i = 0; i < values.length; i++)
                values[ i ] = trans.getTranslation( values[ i ] );
        }

        jcb = new JComboBox( values );
        jcb.setSelectedIndex( selected );

        if( al != null ) jcb.addActionListener( al );

        if( vars.graphicalDebugMode ) jcb.setBorder( Borders.L1R_BORDER );

        jcb.setPreferredSize( nullDim );
        jcb.setMaximumSize( nullDim );

        return jcb;
    }

    /**
     * Returns a scroll pane with an embedded text area.
     *
     * @param text the text to show
     * @param rows the rows of the text area
     * @param cols tje cols of the text area
     * @param edit the edit option. if <strong>true</strong>, the text is editable
     * @param jta reference to the text area
     * @return the JScrollPane
     */
    public JScrollPane getTextArea( String text, int rows, int cols, boolean edit, JTextArea jta ) {

        if( jta != null ) {
        	jta.setRows( rows );
        	jta.setColumns( cols );
        	jta.setText( text );
        	jta.setEditable( edit );

          JScrollPane jsp = new JScrollPane( jta );

        	return jsp;
        }
        return null;
    }

  /**
     * Returns a scroll pane with an embedded text area.
     *
     * @param text the text to show
     * @param rows the rows of the text area
     * @param cols tje cols of the text area
     * @param edit the edit option. if <strong>true</strong>, the text is editable
     * @return the JScrollPane
     */
    public JScrollPane getTextArea( String text, int rows, int cols, boolean edit ) {

        JTextArea jta = new JTextArea( text, rows, cols);
        jta.setEditable( edit );

        JScrollPane jsp = new JScrollPane( jta );

        return jsp;
    }

    /**
     * Returns a JTextArea with Background <strong>null</strong> and
     * normal text. Text is becomes wrapped.
     *
     * @param text the text to show
     * @param type the font type
     * @param t the translate option. if <strong>true</strong>, the text is going to be translated
     * @param color the foregroundcolor
     * @return the JLabel
     */
    public JLabel getMultiLabel( String text, int type, boolean t, Color color ) {

        MyFont jlFont = null;

        if( t ) text = trans.getTranslation( text );

        StringBuffer sb = new StringBuffer( "<html><body><font face=arial size=2>" );
        sb.append( text );
        sb.append( "</font></body></html>" );

        JLabel jl = new JLabel ( sb.toString() );

        switch( type ) {
            case FONT_TYPE_BOLD:
                jlFont = new MyFont( jl.getFont(), MyFont.BOLD );
                break;
            case FONT_TYPE_ITALIC:
                jlFont = new MyFont( jl.getFont(), MyFont.ITALIC );
                break;
            case ( FONT_TYPE_ITALIC | FONT_TYPE_BOLD ):
                jlFont = new MyFont( jl.getFont(), ( MyFont.BOLD | MyFont.ITALIC ) );
                break;
        }

        if( jl != null ) jl.setFont( jlFont );
        if( color != null ) jl.setForeground( color );

        if( vars.graphicalDebugMode ) jl.setBorder( Borders.L1R_BORDER );

        return jl;
    }

    /**
     * Returns a JTextArea with Background <strong>null</strong> and
     * bold text. Text is becomes wrapped.
     *
     * @param text the text to show
     * @param t the translate option. if <strong>true</strong>, the text is going to be translated
     * @return the JLabel
     */
    public JLabel getBoldMultiLabel( String text, boolean t ) {

        JLabel jl = getMultiLabel( text, FONT_TYPE_BOLD, t, null );

        return jl;
    }

    /**
     * Returns a JTextArea with Background <strong>null</strong> and
     * light gray italic text. Text becomes wrapped.
     *
     * @param text the text to show
     * @param t the translate option. if <strong>true</strong>, the text is going to be translated
     * @return the JLabel
     */
    public JLabel getLightItalicMultiLabel( String text, boolean t ) {

        JLabel jl = getMultiLabel( text, FONT_TYPE_ITALIC, t, Color.DARK_GRAY );

        return jl;
    }

    // =============================================================
    // JTextField
    // =============================================================
    /**
     * Returns a JTextField with the specified text.
     *
     * @param text the text to show
     * @param l the length of the TextField
     * @param t the translate option. if <strong>true</strong>, the text is going to be translated
     * @param v
     * @return the JTextField
     */
    public JTextField getTextField( String text, int l, boolean t, PlainDocument v ) {

        if( t ) text = trans.getTranslation( text );

        JTextField jtf = new JTextField( text, l );
        if( v != null ) jtf.setDocument( v );
        jtf.setText( text );

        if( vars.graphicalDebugMode ) jtf.setBorder( Borders.L1R_BORDER );

        return jtf;
    }


    // =============================================================
    // GridBagLayout
    // =============================================================

    /**
     * Adds a component with the specified GridBagConstraint values to the specified container.
     *
     * @param cont the container
     * @param gbl the layout object
     * @param c the component to set the constraint
     * @param p the point for the grid values
     * @param d the dimension of tge grid
     * @param a the align of the grid
     * @param f the fill type of the grid
     */
    public void addComponent( Container cont, GridBagLayout gbl,
                                Component c, Point p, Dimension d,
                                int a, int f ) {

        addComponent( cont, gbl, c, p.x, p.y, d.width, d.height,
                           a, f, new Insets( 1, 1, 1, 1), 0, 0, 1.0, 1.0 );
    }

    /**
     * Adds a component with the specified GridBagConstraint values to the specified container.
     *
     * @param cont the container
     * @param gbl the layout object
     * @param c the component to set the constraint
     * @param x the gridx value
     * @param y the gridy value
     * @param width the gridwidth value
     * @param height the gridheight value
     */
    public void addComponent( Container cont, GridBagLayout gbl,
                                Component c, int x, int y, int width, int height ) {

        addComponent( cont, gbl, c, x, y, width, height,
                         GridBagConstraints.WEST, GridBagConstraints.BOTH,
                         new Insets( 1, 1, 1, 1), 0, 0, 1.0, 1.0 );
    }

    /**
     * Adds a component with the specified GridBagConstraint values to the specified container.
     *
     * @param cont the container
     * @param gbl the layout object
     * @param c the component to set the constraint
     * @param x the gridx value
     * @param y the gridy value
     * @param width the gridwidth value
     * @param height the gridheight value
     */
    public void addFillComponent( Container cont, GridBagLayout gbl,
                                      Component c, int x, int y, int width, int height ) {

        addComponent( cont, gbl, c, x, y, width, height,
                       GridBagConstraints.WEST, GridBagConstraints.BOTH,
                       new Insets( 1, 1, 1, 1), 0, 0, 40.0, 40.0 );
    }

    /**
     * Adds a component with the specified GridBagConstraint values to the specified container.
     *
     * @param cont the container
     * @param gbl the layout object
     * @param c the component to set the constraint
     * @param x the gridx value
     * @param y the gridy value
     * @param width the gridwidth value
     * @param height the gridheight value
     * @param a the align of the grid
     * @param f the fill type of the grid
     */
    public void addComponent( Container cont, GridBagLayout gbl,
                                Component c, int x, int y, int width, int height, int a, int f ) {

        addComponent( cont, gbl, c, x, y, width, height,
                        a, f, new Insets( 1, 1, 1, 1), 0, 0, 40.0, 40.0 );
    }

    /**
     * Adds a component with the specified GridBagConstraint values to the specified container.
     *
     * @param cont the container
     * @param gbl the layout object
     * @param c the component to set the constraint
     * @param x the gridx value
     * @param y the gridy value
     * @param width the gridwidth value
     * @param height the gridheight value
     * @param weightx the weightx value
     * @param weighty the weighty value
     */
    public void addComponent( Container cont, GridBagLayout gbl,
                               Component c, int x, int y, int width, int height,
                               double weightx, double weighty ) {

        addComponent( cont, gbl, c, x, y, width, height,
                      GridBagConstraints.WEST, GridBagConstraints.BOTH,
                      new Insets( 1, 1, 1, 1), 0, 0, weightx, weighty );
    }

    /**
     * Adds a component with the specified GridBagConstraint values to the specified container.
     *
     * @param cont the container
     * @param gbl the layout object
     * @param c the component to set the constraint
     * @param x the gridx value
     * @param y the gridy value
     * @param width the gridwidth value
     * @param height the gridheight value
     * @param a the align of the grid
     * @param f the fill type of the grid
     * @param i the insets of the grid
     * @param ipadx
     * @param ipady
     * @param weightx the weightx value
     * @param weighty the weighty value
     */
    public void addComponent( Container cont, GridBagLayout gbl,
                              Component c, int x, int y, int width,
                              int height, int a, int f, Insets i,
                              int ipadx, int ipady, double weightx, double weighty ) {


        GridBagConstraints gbc = new GridBagConstraints( x, y, width, height,
                                                         weightx, weighty,
                                                         a, f, i, ipadx, ipady );

        gbl.setConstraints( c, gbc );
        cont.add( c );
}

}
