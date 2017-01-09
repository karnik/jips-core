/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.objects;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.gui.objects.inputtypes.*;
import de.karnik.jips.processing.BaseProcess;
import de.karnik.xml.XMLControl;
import org.jdom.DataConversionException;
import org.jdom.Element;

import javax.swing.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * The InputMaskReader class contains basic class fields and methods to work with the input mask layouts.<br />
 * This class reads and builds dynamic input masks.
 *
 * <strong>Possible Input Types are:</strong><br/>
 * - textfield<br />
 * - filechooser<br />
 * - combobox<br />
 * - checkbox<br />
 * - radiobutton<br />
 * - label<br />
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.5
 */
public class ConfigMaskReader extends XMLControl {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -50939281671143105L;

    private Vector<InputType> it = new Vector<InputType>();
    private BaseProcess baseProcess = null;
    private HashMap<String,ButtonGroup> buttonGroups = new HashMap<String,ButtonGroup>();

    /**
     * Constructs a new InputMaskReader object with the specified parameters.
     *
     * @param xmlfile XML-File to read
     */
    public ConfigMaskReader( String xmlfile, BaseProcess baseProcess ) throws JIPSException {
        super(xmlfile, true, false);
        setBaseProcess( baseProcess );
        checkDefaults();
        readContent();
    }


    /**
     * Checks for default values.
     */
    public void checkDefaults() {
        Iterator types = getIteratorListOfSecondChild( "defaults" );
        while( types.hasNext() ) {
            Element type = ( Element )types.next();
            String id    = type.getAttributeValue( "id" );
            String value = type.getAttributeValue( "value" );

            if ( baseProcess.getConfiguration( id ) == null ) {
                baseProcess.setConfiguration( id, value );
            }

        }
    }

    /**
     * <strong>Possible Input Types are:</strong><br/>
     * - textfield<br />
     * - filechooser<br />
     * - combobox<br />
     * - checkbox<br />
     * - radiobutton<br />
     * - label<br />
     */
    public void readContent() throws JIPSException {

        String objectType = "";
        String strBounds[] = null;
        int intBounds[] = new int[ 4 ];
        boolean translate = false;
        InputType tempIt = null;
        ButtonGroup bg = null;

        Iterator types = getIteratorListOfSecondChild( "inputmask" );

        while( types.hasNext() ) {
            Element type = ( Element )types.next();

            String id = type.getAttributeValue( "id" );
            if( id.equals( "" ) );
                // warning

            objectType = type.getAttributeValue( "type" );

            try {
                translate  = type.getAttribute( "translate" ).getBooleanValue();
                // get bounds
                strBounds     = type.getAttributeValue( "bounds" ).split( "," );

                if( strBounds.length == 4 ) {
                    for( int i = 0; i < strBounds.length; i++ ) {
                        intBounds[ i ] = Integer.parseInt( strBounds[ i ] );
                    }
                } else {
                    String element = generateReadableElement( type, true );
                    String path = getXmlFilePath() + " : " + element;
                    String[] loc = { "de.karnik.jips.gui.object.ConfigMaskReader", "readContent() ", "-" };
                    throw new JIPSException( "0021", false, path, loc );
                }

            // exception handling
            } catch( DataConversionException dce ) {
                throw new JIPSException( "001F", dce, false, getXmlFilePath() );
            } catch( NumberFormatException nfe ) {
                throw new JIPSException( "0020", nfe, false, getXmlFilePath() );
            }

            if( objectType.equals( "textfield" ) || objectType.equals( "filechooser" ) ) {

                // length
                int length = 0;
                try {
                    length = Integer.parseInt( type.getChildText( "length" ) );
                } catch( NumberFormatException nfe ) {
                    throw new JIPSException( "0022", nfe, false );
                }

                // inputType
                String inputType = type.getChildText( "inputType" );
                int intInputType = InputType.STRING;
                if( inputType.equals( "number" ) ) intInputType = InputType.NUMBER;


                // text
                String text = type.getChildText( "text" );

                if( text.equals( "default" ) ) text = baseProcess.getConfiguration( id );

                if( ( text == null || text.equals( "" ) ) && intInputType == InputType.NUMBER ) {
                    text = "0";
                }

                if( objectType.equals( "filechooser" ) ) {
                    tempIt =  new InputTypeFileChooser( text, id, length, translate );
                } else {
                    tempIt =  new InputTypeTextField( text, id, length, translate );
                }


            } else if ( objectType.equals( "combobox" ) ) {
            } else if ( objectType.equals( "checkbox" ) ) {

            // radiobutton
            } else if ( objectType.equals( "radiobutton" ) ) {

                // group
                String group = "";
                group = type.getChildText( "group" );

                bg = buttonGroups.get( group );

                if( bg == null ) {
                    bg = new ButtonGroup();
                    buttonGroups.put( group, bg );
                }

                // text and selection
                String text = type.getChildText( "text" );
                boolean booleanSelected = false;

                   String selected = baseProcess.getConfiguration( id );
                if( selected != null && selected.equals( "true" ) ) booleanSelected = true;

                tempIt = new InputTypeRadioButton( text, id, group, booleanSelected, translate );

            // add a label
            } else if ( objectType.equals( "label" ) ) {
                String text = type.getChildText( "text" );
                tempIt =  new InputTypeLabel( text, id, translate );

            } else {
            }

            // set bounds and add InputType
            tempIt.addComponent();
            tempIt.setComponentBounds( intBounds[ 0 ],
                    intBounds[ 1 ],
                    intBounds[ 2 ],
                    intBounds[ 3 ] );

            if( tempIt.getComp() instanceof JRadioButton )
                bg.add( ( JRadioButton )tempIt.getComp() );

            it.add( tempIt );
        }
    }

    /**
     * @return Returns the it.
     */
    public Vector<InputType> getIt() {
        return it;
    }

    /**
     * @return Returns the baseProcess.
     */
    public BaseProcess getBaseProcess() {
        return baseProcess;
    }

    /**
     * @param baseProcess The baseProcess to set.
     */
    public void setBaseProcess( BaseProcess baseProcess ) {
        this.baseProcess = baseProcess;
    }

}
