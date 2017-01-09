/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.common.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * The Translator class contains class fields and methods to
 * to handle translation files.
 *
 * @author <a href="mailto:markus.karnik@gmail.com">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.15
 */
public class Translator extends AbstractTranslator {

  private static final String LANG_BUNDLE_NAME = "locale/lang";

  /**
   * Static translation instance for the singleton.
   */
  private static Translator instance = null;

  private static List<LocaleChangeListener> localeChangeListeners = new ArrayList<>();

  /**
   * Constructs a new Translator object with the specified parameters.
   */
  private Translator() {
    super();
  }

  /**
   * Returns the jips translation instance.
   *
   * @return the translation object
   */
  public static Translator getInstance() {
    if (instance == null) {
      instance = new Translator();
    }
    return instance;
  }

  @Override
  public void loadLocale() {
    super.loadLocale();

    // refrehs all registered locale change listeners.
    for (LocaleChangeListener lcl : localeChangeListeners) {
      lcl.loadLocale();
    }
  }

  @Override
  public String getBundleName() {
    return LANG_BUNDLE_NAME;
  }

  public void addLocaleChangeListener(LocaleChangeListener localeChangeListener) {
    localeChangeListeners.add(localeChangeListener);
  }

  public void removeLocaleChangeListener(LocaleChangeListener localeChangeListener) {
    localeChangeListeners.remove(localeChangeListener);
  }

}
