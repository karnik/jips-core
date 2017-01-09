/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C)  2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 *
 * This file is licensed to you under the MIT license.
 * See the LICENSE file in the project root for more information.
 *
 */


package de.karnik.loader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;


/**
 * The JarLoader class contains methods to load jar files
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 * @since v.0.0.6
 */
public class JarLoader {

    /**
     * @param args
     */

    public static void test() throws Exception{

    	URL jarURL = new File("ext_libs/PgsLookAndFeel.jar").toURL();

        //Entweder so
        //ClassLoader classLoader =    new URLClassLoader(new URL[]{jarURL});

        //Oder so
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if(classLoader != null && (classLoader instanceof URLClassLoader)){
            URLClassLoader urlClassLoader = (URLClassLoader)classLoader;
            Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            addURL.setAccessible(true);
            addURL.invoke(urlClassLoader, new Object[]{jarURL});
        }
      /*
      Class testRunnerClass = classLoader.loadClass("com.pagosoft.swing.ShadowBorder");
      testRunnerClass.getMethod("main", new Class[]{String[].class}).invoke(null, new Object[]{new String[0]});
      */
    }

}
