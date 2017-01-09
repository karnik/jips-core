/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */
package de.karnik.jips;

import de.karnik.jips.common.JIPSException;
import de.karnik.jips.common.config.JIPSVariables;
import de.karnik.jips.common.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

public class PluginStorage {

  public static int TYPE_INPUT = 0;
  public static int TYPE_OUTPUT = 1;
  public static int TYPE_MASK = 1;
  /**
   * Static varibales instance for the singleton.
   */
  private static PluginStorage instance = null;
  private Properties props = new Properties();
  private Vector<Plugin> plugins = new Vector<>();

  // create JIPS variables
  private JIPSVariables vars = JIPSVariables.getInstance();

  /**
   * Constructs a new PluginStorage object.
   */
  private PluginStorage() throws JIPSException {
    loadFiles();
  }

  /**
   * Returns the jips variables instance.
   *
   * @return the jips varibales object.
   */
  public static PluginStorage getInstance() throws JIPSException {

    if (instance == null) {
      instance = new PluginStorage();
    }
    return instance;
  }

  private void addPluginsToClassPath() throws Exception {

    File pluginDir = IO.getFile(vars.getPluginsDir(), true, true);

    URI pluginDirUrl = pluginDir.toURI();
    URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
    Class<URLClassLoader> urlClass = URLClassLoader.class;
    Method method = urlClass.getDeclaredMethod("addURL", URL.class);
    method.setAccessible(true);
    method.invoke(urlClassLoader, pluginDirUrl.toURL());
  }

  private void loadFiles() throws JIPSException {

    Plugin plug = null;
    String filename;

    List<File> configFiles =
            IO.getFilesFromDir(IO.getFile(vars.getPluginsDir(), true, true), "plugin.properties");

    for (File file : configFiles) {

      try {
        props.load(new FileInputStream(file.getAbsolutePath()));
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }

      try {
        if (props.containsKey("typ") &&
                props.containsKey("class") &&
                props.containsKey("package")) {

          plug = new Plugin(
                  props.getProperty("typ"),
                  CommonFunctions.splitSeparatedString(props.getProperty("inputs"), ","),
                  CommonFunctions.splitSeparatedString(props.getProperty("outputs"), ","),
                  props.getProperty("package"),
                  props.getProperty("name"),
                  props.getProperty("version"),
                  props.getProperty("provider-name"),
                  props.getProperty("jar"),
                  props.getProperty("class"),
                  props.getProperty("icon"),
                  new File(file.getParent()).getName(),
                  props.getProperty("url"),
                  props.getProperty("contact")
          );

          plugins.add(plug);
        }
      } catch (NumberFormatException nfe) {
        throw new JIPSException("0029", nfe, true, file.getAbsolutePath());
      }

      filename = file.getAbsolutePath().replaceAll("plugin.properties", plug.getPluginJar());

      // check for correct package-path
      if (plug.getPluginPackage() == null || plug.getPluginPackage().length() == 0) {
        plug.setPluginPackage("");
      } else {
        if (!plug.getPluginPackage().endsWith("."))
          plug.setPluginPackage(plug.getPluginPackage() + ".");
      }


      plug.setPluginMainClass(loadJarFile(filename,
              plug.getPluginPackage()
                      + plug.getPluginMainClassName()));

    }
  }

  private Class<?> loadJarFile(String jarFile, String className) throws JIPSException {
    Class<?> c = null;

    try {

      File file = new File(jarFile);
      URLClassLoader ucl = new URLClassLoader(new URL[]{file.toURI().toURL()});

      URI pluginDirUrl = file.toURI();
      URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
      Class<URLClassLoader> urlClass = URLClassLoader.class;
      Method method = urlClass.getDeclaredMethod("addURL", URL.class);
      method.setAccessible(true);
      method.invoke(urlClassLoader, pluginDirUrl.toURL());

      c = urlClassLoader.loadClass(className);

    } catch (MalformedURLException mue) {
      throw new JIPSException("002A", mue, true, jarFile);
    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException cnfe) {
      throw new JIPSException("002B", cnfe, true, jarFile);
    }

    return c;
  }

  public Vector<Plugin> getPluginsByType(int typ) {
    Vector<Plugin> retPlugs = new Vector<>();

    for (Plugin tempPlugin : plugins) {
      if (tempPlugin.getPluginType() == typ) {
        retPlugs.add(tempPlugin);
      }
    }

    return retPlugs;
  }

  Plugin getPlugin(String id) {

    for (Plugin tempPlugin : plugins) {
      if (tempPlugin.getPluginName().equals(id)) {
        return tempPlugin;
      }
    }
    return null;
  }

}
