/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.common.lang;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The abstract translator class contains class fields and methods to
 * to handle translation files.
 *
 * @author <a href="mailto:markus.karnik@gmail.com">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.1
 */
public abstract class AbstractTranslator implements LocaleChangeListener {

  private Locale[] supportedLocales = {
          Locale.GERMAN,
          Locale.ENGLISH
  };

  /**
   * The available languages.
   */
  private String[][] availableLanguage;

  /*
   * The lang resource bundle.
   */
  private ResourceBundle langBundle;

  /**
   * Constructs a new AbstractTranslator object with the specified parameters.
   */
  public AbstractTranslator() {
    loadLocale();
  }


  /**
   * Load current locale values.
   */
  public void loadLocale() {
    langBundle = ResourceBundle.getBundle(getBundleName(), Locale.getDefault());
    generateLanguageMap();
  }

  /**
   * Returns the resource bundle name of this translator object.
   *
   * @return The resource bundle name.
   */
  public abstract String getBundleName();

  /**
   * Returns the value for the searched index.
   *
   * @param what the index to search for
   * @return the value for the wanted index, or null
   */
  public String getTranslation(String what) {
    return langBundle.getString(what);
  }

  /**
   * Creates a String array with the available languages.
   *
   * @return the String array with the available languages
   */
  private String[][] generateLanguageMap() {

    availableLanguage = new String[getAvailableLangCount()][2];

    for (int i = 0; i < supportedLocales.length; i++) {
      availableLanguage[i][0] = supportedLocales[i].getLanguage();
      availableLanguage[i][1] = supportedLocales[i].getDisplayLanguage();
    }

    return availableLanguage;
  }

  /**
   * Returns a count of the available languages
   *
   * @return the count of the available languages
   */
  public int getAvailableLangCount() {
    return supportedLocales.length;
  }

  /**
   * Returns a String array with the available languages.
   *
   * @return the String array with the available languages
   */
  public String[][] getAvailableLanguages() {
    return availableLanguage;
  }
}
