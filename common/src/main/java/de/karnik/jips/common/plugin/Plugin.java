/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */
package de.karnik.jips.common.plugin;

import lombok.Getter;
import lombok.Setter;

/**
 * A Class to define the plugin datatype.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 0.3
 * @since v.0.0.6
 */
@Getter
@Setter
public class Plugin {

  public static final int TYPE_IO = 0;
  public static final int TYPE_POINT_OPERATION = 1;
  public static final int TYPE_FILTER_OPERATION = 2;
  public static final int TYPE_EDGE_OPERATION = 3;
  public static final int TYPE_FOURIER_OPERATION = 4;
  public static final int TYPE_UNDEFINED = 5;

  private int pluginType = TYPE_UNDEFINED;

  private int pluginInputCount = 0;
  private int pluginOutputCount = 0;

  private String[] pluginInputNames;
  private String[] pluginOutputNames;

  private String pluginPackage = "";
  private String pluginName = "";
  private String pluginVersion = "";
  private String pluginProviderName = "";
  private String pluginJar = "";
  private String pluginMainClassName = "";
  private String pluginIcon = "";
  private String pluginDir = "";
  private String pluginUrl = "";
  private String pluginContact = "";
  private Class<?> pluginMainClass = null;

  public Plugin(String pluginTyp, String[] pluginInputNames, String[] pluginOutputNames,
                String pluginPackage, String pluginName, String pluginVersion,
                String pluginProviderName, String pluginJar, String pluginMainClassName,
                String pluginIcon, String pluginDir, String pluginUrl, String pluginContact) {

    try {
      int temp = Integer.parseInt(pluginTyp);
      setPluginType(temp);
    } catch (NumberFormatException nfe) {
      setPluginType(TYPE_UNDEFINED);
    }

    if (null == pluginInputNames) {
      setPluginInputCount(0);
    } else {
      setPluginInputCount(pluginInputNames.length);
    }

    if (null == pluginOutputNames) {
      setPluginOutputCount(0);
    } else {
      setPluginOutputCount(pluginOutputNames.length);
    }

    setPluginInputNames(pluginInputNames);
    setPluginOutputNames(pluginOutputNames);
    setPluginPackage(pluginPackage);
    setPluginName(pluginName);
    setPluginVersion(pluginVersion);
    setPluginProviderName(pluginProviderName);
    setPluginJar(pluginJar);
    setPluginMainClassName(pluginMainClassName);
    setPluginIcon(pluginIcon);
    setPluginDir(pluginDir);
    setPluginUrl(pluginUrl);
    setPluginContact(pluginContact);
  }

  public void setPluginType(int pluginType) {

    switch (pluginType) {
      case TYPE_IO:
      case TYPE_POINT_OPERATION:
      case TYPE_FILTER_OPERATION:
      case TYPE_EDGE_OPERATION:
      case TYPE_FOURIER_OPERATION:
      case TYPE_UNDEFINED:
        this.pluginType = pluginType;
        break;
      default:
        this.pluginType = TYPE_UNDEFINED;
    }
  }

}
