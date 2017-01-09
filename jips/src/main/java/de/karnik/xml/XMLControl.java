/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.xml;

import de.karnik.jips.IO;
import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.config.JIPSConstants;
import lombok.Getter;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * The XMLControl class contains basic class fields and methods to work with XML.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.1
 */
public abstract class XMLControl extends JComponent {

  /**
   * The Document with the XML data.
   */
  @Getter
  private Document myDoc = null;

  /**
   * The realtive path to the XML file.
   */
  @Getter
  private String xmlFilePath = null;

  /**
   * This class is not instantiable with the default constructor.
   */
  private XMLControl() {
    // nothing
  }

  /**
   * Constructs a new XMLControl object with the specified parameters.
   *
   * @param xmlFilePath    The path to the xml file to load.
   * @param isPathAbsolute Is this an absolute path?
   * @param critical       Does an error stop the execution.
   */
  public XMLControl(String xmlFilePath, boolean isPathAbsolute, boolean critical) throws JIPSException {
    this();
    this.xmlFilePath = xmlFilePath;

    InputStream is = IO.getFileInputStream(this.xmlFilePath, isPathAbsolute, critical);

    // try to read the XMl file
    try {
      SAXBuilder builder = new SAXBuilder();
      myDoc = builder.build(is);

    } catch (IOException ioe) {
      throw new JIPSException("0001", ioe, true);
    } catch (JDOMException jde) {
      throw new JIPSException("0002", jde, true);
    }

    // check xml-file version
    String xmlVersion = getMyDoc().getRootElement().getAttributeValue("jips-version");

    if (!xmlVersion.equals(JIPSConstants.JIPS_VERSION)) {
      String[] loc = {"de.karnik.xml.XMLControl", "XMLControl( String xmlFilePath, boolean critical )", "-"};
      String message = xmlFilePath + " - " + xmlVersion + " should be " + JIPSConstants.JIPS_VERSION;
      throw new JIPSException("0023", false, message, loc);
    }

  }

  public XMLControl(String xmlFilePath, boolean critical) throws JIPSException {
    this(xmlFilePath, false, critical);
  }

  /**
   * Returns a iterator list with the first child elements.
   *
   * @param search the name of the first children
   * @return a iterator list of the first child elements, or null
   */
  protected Iterator getIteratorListOfFirstChildren(String search) {
    try {
      return getMyDoc()
              .getRootElement()
              .getChildren(search)
              .iterator();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Returns a iterator list with the second child elements.
   *
   * @param search the name of the first child
   * @param id     id for the search string
   * @return a iterator list with the children of the first child, or null
   */
  protected Iterator getIteratorListOfSecondChild(String search, String id) {
    try {

      for (Object o : getMyDoc()
              .getRootElement()
              .getChildren(search)) {
        Element element = (Element) o;

        if (element.getAttribute("id").getValue().equals(id))
          return element.getChildren().iterator();
      }

      return null;


    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Returns a iterator list with the second child elements.
   *
   * @param search the name of the first child
   * @return a iterator list with the children of the first child, or null
   */
  protected Iterator getIteratorListOfSecondChild(String search) {
    try {
      return getMyDoc()
              .getRootElement()
              .getChild(search)
              .getChildren()
              .iterator();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Returns the value of the first child with the specified name that is found.
   *
   * @param search the name of the child to search for
   * @return the value of the child, or null
   */
  protected String getValueOfFirstChild(String search) {
    try {
      return getMyDoc()
              .getRootElement()
              .getChild(search)
              .getText();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Returns the value of the first child with the specified name.
   *
   * @param search the name of the child to search for
   * @return the value of the child, or null
   */
  protected String getValueOfFirstChild(String search, String searchattr) {
    try {
      return getMyDoc()
              .getRootElement()
              .getChild(search).getAttributeValue(searchattr);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Sets the value of the first child with the specified name that is found.
   *
   * @param search the name of the child to search for
   * @param value  the value to set
   */
  protected void setValueOfFirstChild(String search, String value) {
    getMyDoc()
            .getRootElement()
            .getChild(search).setText(value);
  }

  protected String generateReadableElement(Element e, boolean html) {
    StringBuilder retVal = new StringBuilder();
    String start = "<";
    String end = ">";

    if (html) {
      start = "&lt;";
      end = "&gt;";
    }

    retVal.append(start).append(e.getName()).append(" ");

    for (Object o : e.getAttributes()) {
      Attribute a = (Attribute) o;

      retVal.append(a.getName()).append("=\"").append(a.getValue()).append("\" ");
    }

    retVal.append(end);

    return retVal.toString();
  }

}
