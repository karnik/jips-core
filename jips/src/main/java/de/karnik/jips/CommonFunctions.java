/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */
package de.karnik.jips;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * The CommonFunctions class contains usefull methods.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.1
 * @since v.0.0.5
 */
public class CommonFunctions {

    private static final char kHexChars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static double objects = 0;
    private static Random random = new Random();

    /**
     * This class is uninstantiable.
     */
    private CommonFunctions() {}
	
    public static Point getMiddleOfScreen( int width, int height ) {
        Dimension mainScreen = Toolkit.getDefaultToolkit().getScreenSize();
        return new Point( mainScreen.width / 2 - width / 2, mainScreen.height / 2 - height / 2 );
    }

  public static String getUUID() {
    return UUID.randomUUID().toString();
    }

    private static String bufferToHex( byte buffer[] ) {
        return CommonFunctions.bufferToHex( buffer, 0, buffer.length );
    }

    private static String bufferToHex( byte buffer[], int startOffset, int length ) {
        StringBuffer hexString = new StringBuffer(2 * length);
        int endOffset = startOffset + length;

        for (int i = startOffset; i < endOffset; i++)
            CommonFunctions.appendHexPair(buffer[i], hexString);

        return hexString.toString();
    }

    private static void appendHexPair( byte b, StringBuffer hexString ) {
        char highNibble = kHexChars[(b & 0xF0) >> 4];
        char lowNibble = kHexChars[b & 0x0F];

        hexString.append(highNibble);
        hexString.append(lowNibble);
    }
    
    public static int getSmallest( int first, int second ) {
    	if( first >= second )
    		return second;
    	
    	return first;
    }
    
    public static int getBiggest( int first, int second ) {
    	if( first <= second )
    		return second;
    	
    	return first;
    }
    
    public static int compare( int first, int second ) {
    	if( first > second )
    		return 1;
    	
    	if( first == second )
    		return 0;
    	
    	return -1;
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

  public static String[] splitSeparatedString(String separatedString, String separator) {

    if (null == separatedString || null == separator)
      return null;

    if (separatedString.equals(""))
      return new String[0];

    return separatedString.split(separator);
  }

}
