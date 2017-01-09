/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.common;

import lombok.Getter;
import lombok.Setter;

/**
 * The JIPSMessage class contains methods and files for jips warning an error messages.
 * It cannot be instantiated.
 *
 * @author <a href="mailto:markus.karnik@gmail.com">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.6
 */
public class JIPSMessage {

  public static final int INFORMATION = 0;
  public static final int ERROR = 1;
  public static final int WARNING = 2;

  @Getter
  @Setter
  private int type = INFORMATION;
  @Getter
  private String msg = null;
  @Getter
  private String hint = null;

  @SuppressWarnings("unused")
  private JIPSMessage() {
    // nothing
  }

  public JIPSMessage(String msg) {
    this.msg = msg;
  }

  public JIPSMessage(String msg, String hint) {
    this(msg);
    this.hint = hint;
  }

  public JIPSMessage(int type, String msg, String hint) {
    this(msg, hint);
    this.type = type;
  }
}
