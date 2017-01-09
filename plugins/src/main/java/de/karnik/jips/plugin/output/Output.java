/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.plugin.output;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.processing.JIPSProcess;

public class Output extends JIPSProcess {

  public static final String OUTPUT_0 = "out0";

  public Output() {
    super();
  }

  public void run() throws JIPSException {
    System.out.println(this.getInput(OUTPUT_0));
  }

}
