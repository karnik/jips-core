/*
 * JIPS - JIPS Image Processing Software
 * Copyright (C) 2006 - 2017  Markus Karnik (markus.karnik@gmail.com)
 */

package de.karnik.jips.gui.objects;

import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;

/**
 * This class is for graphical classes which need borders to layout components.
 *
 * @author <a href="mailto:markus.karnik@my-design.org">Markus Karnik</a>
 * @version 1.0
 */
public abstract class Borders {

    /**
     * A EmptyBorder with a width of 0 pixels.
     */
    public static final EmptyBorder E0_BORDER = new EmptyBorder( 0, 0, 0, 0 );
    /**
     * A EmptyBorder with a width of 1 pixels.
     */
    public static final EmptyBorder E1_BORDER = new EmptyBorder( 1, 1, 1, 1 );
    /**
     * A EmptyBorder with a width of 2 pixels.
     */
    public static final EmptyBorder E2_BORDER = new EmptyBorder( 2, 2, 2, 2 );
    /**
     * A EmptyBorder with a width of 3 pixels.
     */
    public static final EmptyBorder E3_BORDER = new EmptyBorder( 3, 3, 3, 3 );
    /**
     * A EmptyBorder with a width of 4 pixels.
     */
    public static final EmptyBorder E4_BORDER = new EmptyBorder( 4, 4, 4, 4 );
    /**
     * A EmptyBorder with a width of 5 pixels.
     */
    public static final EmptyBorder E5_BORDER = new EmptyBorder( 5, 5, 5, 5 );

    /**
     * A lowered BevelBorder.
     */
    public static final BevelBorder BL_BORDER = new BevelBorder( BevelBorder.LOWERED );
    /**
     * A raised BevelBorder.
     */
    public static final BevelBorder BR_BORDER = new BevelBorder( BevelBorder.RAISED );

    /**
     * A lowered SoftBevelBorder.
     */
    public static final SoftBevelBorder SBL_BORDER = new SoftBevelBorder( SoftBevelBorder.LOWERED );
    /**
     * A raised SoftBevelBorder.
     */
    public static final SoftBevelBorder SBR_BORDER = new SoftBevelBorder( SoftBevelBorder.RAISED );

    /**
     * A raised BevelBorder.
     */
    public static final MyBevelBorder MBR_BORDER = new MyBevelBorder( MyBevelBorder.RAISED );

    /**
     * A lowered BevelBorder.
     */
    public static final MyBevelBorder MBL_BORDER = new MyBevelBorder( MyBevelBorder.LOWERED );

    /**
     * A white LineBorder with a width of 1 pixel.
     */
    public static final LineBorder L1W_BORDER = new LineBorder( Color.white ,1 );
    /**
     * A white LineBorder with a width of 1 pixel.
     */
    public static final LineBorder L1R_BORDER = new LineBorder( Color.red ,1 );
    /**
     * A black LineBorder with a width of 1 pixel.
     */
    public static final LineBorder L1B_BORDER = new LineBorder( Color.black ,1 );
    /**
     * A dark gray LineBorder with a width of 1 pixel.
     */
    public static final LineBorder L1DG_BORDER = new LineBorder( Color.darkGray ,1 );
    /**
     * A light gray LineBorder with a width of 1 pixel.
     */
    public static final LineBorder L1LG_BORDER = new LineBorder( Color.lightGray ,1 );
    /**
     * A dark gray LineBorder with a width of 4 pixels.
     */
    public static final LineBorder L4G_BORDER = new LineBorder(Color.darkGray ,4);
    /**
     * A dark gray LineBorder with a width of 1 pixels placed on the bottom.
     */
    public static final EmptyBorder L1DGB_BORDER = new JIPSLineBorder( Color.darkGray, 0, 0, 1, 0 );
    /**
     * A dark gray LineBorder with a width of 1 pixels placed on the top.
     */
    public static final EmptyBorder L1DGT_BORDER = new JIPSLineBorder( Color.darkGray, 1, 0, 0, 0 );
    
    /**
     * This class is uninstantiable.
     */
    private Borders() {}
}