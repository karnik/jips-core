# JIPS Image Processing Software - Core

## About JIPS
TBD

## PARAMETERS
```
-debug            Enable debug modus
-graphicalDebug   Enable graphical debug modus
-help             Show Help
```

## REQUIREMENTS
* Windows, Linux or Solaris
* Java SE 6 or later

## TROUBLESHOOTING

### A.1 Java 2D
The OpenGL pipeline was first made available in the J2SE 5.0 release on Solaris OS, Linux, and Windows. This alternate pipeline uses the hardware-accelerated, cross-platform OpenGL API when rendering to VolatileImages, to backbuffers created with BufferStrategy API, and to the screen.

To enable the OpenGL Pipeline ( Windows, Solaris, Linux )
```
-Dsun.java2d.opengl=true
```

Starting with the Java SE 6 release, the Direct3D pipeline uses the Direct3D API for rendering. Some older video boards/drivers combinations are known to cause issues (both rendering, and performance) with the Direct3D pipeline. To disable the pipeline in such case, pass the parameter 
```
-Dsun.java2d.d3d=false
``` 
to the Java VM, or set the J2D_D3D environment variable to false.

Disabling the Use of Pixmaps by the X11 Pipeline is also a good method to boost 2D performance. To disable the use of pixmaps by Java2D, pass the following property to the Java VM:
```
-Dsun.java2d.pmoffscreen=false
```

For more information about Java 2D Troubleshooting, please see
* http://java.sun.com/javase/6/webnotes/trouble/TSG-Desktop/html/java2d.html


### A.2 Xgl, aiglx, Beryl and Compiz

Using Java VM 1.6 with Beryl or Compiz causes blank/empty windows. This bug is
well known and hopefully will be fixed in the nearest future.

A small workaround is the usage of MToolkit. You have to set environment
variable AWT_TOOLKIT to MToolkit.
```
#> export AWT_TOOLKIT=MToolkit
#> ./jips.sh
```

**Bugreports:**
* http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6429775
* http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6509038

**More information:**
* http://wiki.archlinux.org/index.php/Xgl_Troubleshooting#Incompatible_software.

## License
The JIPS Image Processing Software is open-sourced software licensed under the [MIT license](http://opensource.org/licenses/MIT).
