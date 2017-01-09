/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */

package de.karnik.jips.gui.pluginsview;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class PluginTransferable implements Transferable {

  private String pluginName = null;

  public PluginTransferable(String pluginName ) {
    this.pluginName = pluginName;
  }

  @Override
  public Object getTransferData(DataFlavor flavor )
          throws UnsupportedFlavorException, IOException {

    if( flavor.equals( DataFlavor.stringFlavor ) )
      return this.pluginName;
    else
      throw new UnsupportedFlavorException( flavor );

  }

  @Override
  public DataFlavor[] getTransferDataFlavors() {

    DataFlavor[] df = new DataFlavor[ 1 ];
    df[ 0 ] = DataFlavor.stringFlavor;

    return df;
  }

  @Override
  public boolean isDataFlavorSupported( DataFlavor flavor ) {
    if( flavor.equals( DataFlavor.stringFlavor ) )
      return true;

    return false;
  }

}
