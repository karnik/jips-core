/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.plugin.viewer.locale;

import de.karnik.jips.common.lang.AbstractTranslator;
import de.karnik.jips.common.lang.Translator;

/**
 * Translator class for the viewer plugin.
 *
 * @author <a href="mailto:markus.karnik@gmail.com">Markus Karnik</a>
 * @version 1.0
 */
public class ViewerTranslator extends AbstractTranslator {
  private static final String LANG_BUNDLE_NAME = "de/karnik/jips/plugin/viewer/locale/viewer";

  /**
   * Static translation instance for the singleton.
   */
  private static ViewerTranslator instance = null;

  /**
   * Constructs a new Translator object with the specified parameters.
   */
  private ViewerTranslator() {
    super();
    // register to get informed on locale changes.
    Translator.getInstance().addLocaleChangeListener(this);
  }

  /**
   * Returns the viewer translation instance.
   *
   * @return the translation object
   */
  public static ViewerTranslator getInstance() {
    if (instance == null) {
      instance = new ViewerTranslator();
    }
    return instance;
  }

  @Override
  public String getBundleName() {
    return LANG_BUNDLE_NAME;
  }
}
