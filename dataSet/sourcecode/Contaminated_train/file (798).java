package Game;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @description Java project geschreven als student ICT aan Thomasmore Kempen.
 *              Breakout: Doel van het spel is om alle blokjes weg te werken,
 *                        zonder dat de bal in het water valt
 * Grafisch werk zelf bewerkt naar noden van toepassing.
 * URL van assets: http://kenney.nl/post/platformer-art-assets-deluxe
 * @Stats 2905 Lijnen code en veel tijd :)
 * @website ds-dev.be -> voor handleiding van het spel
 * @version V1.0
 * @author Dens Sascha
 * 
 * @Gebruikte bron: Collision detectie van Hock-Chuan Chua met eigen implementatie hiervan.
 *                  URL: http://www.ntu.edu.sg/home/ehchua/programming/java/J8a_GameIntro-BouncingBalls.html
 */

public class ContainerBox {
    int minX, maxX, minY, maxY;  // Box's bounds (package access)
    private Color colorFilled;   // Box's filled color (background)
    private Color colorBorder;   // Box's border color
    private static final Color DEFAULT_COLOR_FILLED = Color.BLACK;
    private static final Color DEFAULT_COLOR_BORDER = Color.YELLOW;

    /**
     * Constructors
     */
    public ContainerBox(int x, int y, int width, int height, Color colorFilled, Color colorBorder) {
        minX = x;
        minY = y;
        maxX = x + width - 1;
        maxY = y + height - 1;
        this.colorFilled = colorFilled;
        this.colorBorder = colorBorder;
    }

    /**
     * Constructor with the default color
     */
    public ContainerBox(int x, int y, int width, int height) {
        this(x, y, width, height, DEFAULT_COLOR_FILLED, DEFAULT_COLOR_BORDER);
    }

    /**
     * Set or reset the boundaries of the box.
     */
    public void set(int x, int y, int width, int height) {
        minX = x;
        minY = y;
        maxX = x + width - 1;
        maxY = y + height - 1;
    }

    /**
     * Draw itself using the given graphic context.
     */
    public void draw(Graphics g) {
        g.setColor(colorFilled);
        g.fillRect(minX, minY, maxX - minX - 1, maxY - minY - 1);
        g.setColor(colorBorder);
        g.drawRect(minX, minY, maxX - minX - 1, maxY - minY - 1);
    }    
}
