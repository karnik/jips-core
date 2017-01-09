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
public interface JIPSProcessListener {

  ImageStorage getInput(String id) throws JIPSException;

  void setOutput(String id, ImageStorage imageStorage) throws JIPSException;

    void setConfiguration(String key, String value);

    void getConfiguration(String key, StringBuffer value);

    void initProgressBar(int start, int end) throws JIPSException;

    void setProgressBar(int value) throws JIPSException;

}
