/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */
package de.karnik.jips.common.processing;

import de.karnik.jips.common.ImageStorage;
import de.karnik.jips.common.JIPSException;

/**
 * ...
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.7
 */
public abstract class JIPSProcess {

  private JIPSProcessListener jipsProcessListener = null;

  public final JIPSProcessListener getJipsProcessListener() {
    return jipsProcessListener;
  }

  public final void setJipsProcessListener(JIPSProcessListener jipsProcessListener) {
    this.jipsProcessListener = jipsProcessListener;
  }

  public abstract void run() throws JIPSException;

  public final ImageStorage getInput(String inputName) throws JIPSException {

    if (jipsProcessListener != null)
      return jipsProcessListener.getInput(inputName);

    return null;
  }

  public final void setOutput(String outputName, ImageStorage imageStorage) throws JIPSException {
    if (jipsProcessListener != null)
      jipsProcessListener.setOutput(outputName, imageStorage);
  }

  public final String getConfiguration(String key) {
    StringBuffer value = new StringBuffer();

    if (jipsProcessListener != null)
      jipsProcessListener.getConfiguration(key, value);

    return value.toString();
  }

  public final void setConfiguration(String key, String value) {
    if (jipsProcessListener != null)
      jipsProcessListener.setConfiguration(key, value);
  }

  public void initProgressBar(int start, int end) throws JIPSException {
    if (jipsProcessListener != null)
      jipsProcessListener.initProgressBar(start, end);
  }

  public void setProgressBar(int value) throws JIPSException {
    if (jipsProcessListener != null)
      jipsProcessListener.setProgressBar(value);
  }

}
