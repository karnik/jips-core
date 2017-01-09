/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.common;

import lombok.Getter;

/**
 * The JIPSException class contains the exception handling functions.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.6
 */
public class JIPSException extends Exception {

  private static final long serialVersionUID = -2860689831151612744L;

  @Getter
  private String code = null;
  @Getter
  private String filePath = null;
  @Getter
  private String msg = null;
  @Getter
  private String exceptionMessage = null;
  @Getter
  private String[] location = new String[3];
  @Getter
  private boolean critical = false;
  @Getter
  private Exception originalException = null;

  private JIPSException() {
    // nothing
  }

  public JIPSException(String c, Exception e, boolean crit) {
    this();
    code = c;
    critical = crit;
    if (e != null)
      getStackTraceString(e);
    originalException = e;
  }

  public JIPSException(String code, Exception e, boolean crit, String path) {
    this(code, e, crit);
    filePath = path;
  }

  public JIPSException(String code, boolean crit, String path, String[] location) {
    this(code, null, crit, path);

    this.location[0] = location[0];
    this.location[1] = location[1];
    this.location[2] = location[2];
  }

  public JIPSException(String code, boolean crit, String[] location) {
    this(code, null, crit, null);

    this.location[0] = location[0];
    this.location[1] = location[1];
    this.location[2] = location[2];
  }

  private void getStackTraceString(Exception e) {

    boolean first = true;

    if (e != null) {
      StringBuilder sb = new StringBuilder();
      StackTraceElement[] ste = e.getStackTrace();

      sb.append(e.getMessage()).append("\n");
      sb.append("Exception: ").append(ste[0].getClassName()).append("\n\n");

      for (StackTraceElement aSte : ste) {
        sb.append(aSte).append("\n");
        if (aSte.getClassName().startsWith("de.karnik.") && first) {

          location[0] = aSte.getClassName();
          location[1] = aSte.getMethodName();
          location[2] = Integer.toString(aSte.getLineNumber());

          first = false;
        }
      }

      exceptionMessage = sb.toString();
    }
  }
}
