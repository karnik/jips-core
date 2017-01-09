##########################################################################
# JIPS - JIPS Image Processing Software                                  #
# Copyright (C) 2006 - 2007  Markus Karnik                               #
# File: README.TXT                                                       #
##########################################################################

Please send all questions, comments, concerns, and bug reports to:
    markus.karnik@my-design.org

DESCRIPTION


LICENSE

    Released under GNU General Public License ( GPL )
    Look at the license.txt file.


INDEX

    1 INSTALLATION
    2 PARAMETERS
    3 THEMES

    APPENDIX A - TROUBLESHOOTING
    APPENDIX B - REQUIREMENTS
    APPENDIX C - FULLY COMMENTED CLASSES

1 INSTALLATION

2 PARAMETERS

    -debug            Enable debug modus
    -graphicalDebug   Enable graphical debug modus
    -help             Show Help


3 THEMES

    JIPS is using the substance look and feel. For additional informations
    and vm flags please visit: https://substance.dev.java.net/ !

    The extra elements of substance are disabled by default.
        -Dsubstancelaf.noExtraElements

    Please use one of the four standard themes. The colors of JIPS have
    been optimised for this themes.

APPENDIX A - TROUBLESHOOTING

    A.1 Java 2D
    ===========

    The OpenGL pipeline was first made available in the J2SE 5.0 release
    on Solaris OS, Linux, and Windows. This alternate pipeline uses the
    hardware-accelerated, cross-platform OpenGL API when rendering to
    VolatileImages, to backbuffers created with BufferStrategy API, and
    to the screen.

    To enable the OpenGL Pipeline ( Windows, Solaris, Linux )
        "-Dsun.java2d.opengl=true"

    Starting with the Java SE 6 release, the Direct3D pipeline uses the
    Direct3D API for rendering. Some older video boards/drivers combinations
    are known to cause issues (both rendering, and performance) with the
    Direct3D pipeline. To disable the pipeline in such case, pass the
    parameter "-Dsun.java2d.d3d=false" to the Java VM, or set the J2D_D3D
    environment variable to false.

    Disabling the Use of Pixmaps by the X11 Pipeline is also a good method
    to boost 2D performance. To disable the use of pixmaps by Java2D, pass
    the following property to the Java VM:
        "-Dsun.java2d.pmoffscreen=false"

    For more information about Java 2D Troubleshooting, please see
        http://java.sun.com/javase/6/webnotes/trouble/TSG-Desktop/html/java2d.html


  A.2 Xgl, aiglx, Beryl and Compiz
  ================================

  Using Java VM 1.6 with Beryl or Compiz causes blank/empty windows. This bug is
  well known and hopefully will be fixed in the nearest future.

  A small workaround is the usage of MToolkit. You have to set environment
  variable AWT_TOOLKIT to MToolkit.

        ~$ export AWT_TOOLKIT=MToolkit
        ~$ ./jips.sh

  Bugreports:
        http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6429775
        http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6509038

  More information:
      http://wiki.archlinux.org/index.php/Xgl_Troubleshooting#Incompatible_software.


APPENDIX B - REQUIREMENTS

    * Windows, Linux or Solaris
    * Java SE 6 or later


APPENDIX C - FULLY COMMENTED CLASSES

    - de.karnik.common.IO
    - de.karnik.common.MsgHandler

    - de.karnik.fonts.DejaVuFont

    - de.karnik.jips.common.JIPSProject

    - de.karnik.jips.common.config.JIPSConstants
    - de.karnik.jips.common.config.JIPSStatus
    - de.karnik.jips.common.config.JIPSVariables
    - de.karnik.jips.common.lang.Translator;

    - de.karnik.jips.gui.controls.desk.SelectionListener

    - de.karnik.jips.gui.listeners.CloseableTabbedPaneListener
    - de.karnik.jips.gui.listeners.JIPSWindowAdapter

    - de.karnik.jips.gui.menu.BaseMenu
    - de.karnik.jips.gui.menu.BaseMenuBar
    - de.karnik.jips.gui.menu.MainMenu
    - de.karnik.jips.gui.menu.MenuActionListener
    - de.karnik.jips.gui.menu.MenuBarItem

    - de.karnik.jips.gui.object.Borders
    - de.karnik.jips.gui.object.CloseablePgsTabbedPaneUI
    - de.karnik.jips.gui.object.CloseTabIcon
    - de.karnik.jips.gui.object.ClosableTabbedPane
    - de.karnik.jips.gui.object.ImageCanvas
    - de.karnik.jips.gui.object.StatusBar
    - de.karnik.jips.gui.object.StatusField

    - de.karnik.jips.gui.splash.SplashPainter
    - de.karnik.jips.gui.splash.SplashScreen

    - de.karnik.jips.gui.subframe.AboutFrame
    - de.karnik.jips.gui.subframe.BaseFrame
    - de.karnik.jips.gui.subframe.PrefFrame
    - de.karnik.jips.gui.subframe.TextViewerFrame

    - de.karnik.jips.gui.GUIObjectHelper

    - de.karnik.jips.JIPS

    - de.karnik.xml.XMLControl