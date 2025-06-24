/*
 * Author: Andreas Bjoru, abjru2012@my.fit.edu
 * Course: CSE 4051, Fall 2013
 * Project: proj08, Lambda Lifting Game
 */
package impl.display;

import static impl.display.GraphicalDisplay.FONT_NAME;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/**
 * Abstract factory responsible for decorating a given {@link JComponent}.
 * This class follows the 'abstract factory' pattern.
 */
public class AbstractScreenFactory {
   private static final float SCREEN_ALPHA = .45f;

   /**
    * Defines a factory capable of decorating {@link JComponent}'s.
    */
   interface ScreenFactory {
      public void draw (final Graphics2D g, final JComponent c, final Object... d);
   }

   private static final int FONT_SIZE = 50;

   /**
    * Decorate the screen with win messages.
    */
   public static final ScreenFactory WIN_SCREEN = new ScreenFactory () {

      @Override
      public void draw (final Graphics2D g, final JComponent c, final Object... d) {
         final String msg1 = (String) d[0];
         final String msg2 = (String) d[1];

         drawScreen (g, c, msg1, msg2);
      }

   };

   /**
    * Decorate the screen with lose messages.
    */
   public static final ScreenFactory LOSS_SCREEN = new ScreenFactory () {

      @Override
      public void draw (final Graphics2D g, final JComponent c, final Object... d) {
         final String msg1 = (String) d[0];
         final String msg2 = (String) d[1];

         drawScreen (g, c, msg1, msg2);
      }
   };

   /**
    * Decorate the screen with abort message.
    */
   public static final ScreenFactory ABORT_SCREEN = new ScreenFactory () {

      @Override
      public void draw (final Graphics2D g, final JComponent c, final Object... d) {
         final String msg1 = (String) d[0];
         final String msg2 = (String) d[1];

         drawScreen (g, c, msg1, msg2);
      }
   };

   /*
    * Draw the main text and the score text on top of the component c. This
    * method will ensure that there is enough room to draw the entire text
    * by scaling the font size to fit the dimensions of the component. Furthermore,
    * it will draw the text centered on the component's display area.
    */
   static void drawScreen (final Graphics2D g, final JComponent c, final String main,
         final String score) {
      // Set default font
      g.setFont (new Font (FONT_NAME, Font.PLAIN, FONT_SIZE));

      // Resize font based on screen dimensions
      final int size = calcFontSize (c.getWidth (), g.getFontMetrics (), main, score);
      if (size < FONT_SIZE) {
         g.setFont (new Font (FONT_NAME, Font.PLAIN, size));
      }

      // Calculate our x, y coordinates for each message
      final FontMetrics fm = g.getFontMetrics ();
      final int ax = (c.getWidth () / 2) - (fm.stringWidth (main) / 2);
      final int ay = (c.getHeight () / 2) - (fm.getHeight () / 2);
      final int bx = (c.getWidth () / 2) - (fm.stringWidth (score) / 2);
      final int by = (c.getHeight () / 2) + (fm.getHeight () / 2);

      dim (g, c, SCREEN_ALPHA);

      g.setComposite (AlphaComposite.getInstance (AlphaComposite.SRC_OVER, 1f));
      g.setPaint (Color.RED);
      g.drawString (main, ax, ay);
      g.drawString (score, bx, by);
   }

   /*
    * Dim the component graphics by drawing a rectangle over the entire
    * area of the component with the alpha value set to 'alpha'.
    */
   static void dim (final Graphics2D g, final JComponent c, final float alpha) {
      g.setComposite (AlphaComposite.getInstance (AlphaComposite.SRC_OVER, alpha));
      g.setPaint (Color.BLACK);
      g.fillRect (0, 0, c.getWidth (), c.getHeight ());
   }

   /*
    * Calculate the required font size for the given component width
    * based on the max length of the text lines given in the array.
    * If the component width is less than the max text width, scale
    * the font size down to fit the component width. Otherwise, use
    * the provided font size as is.
    */
   static int calcFontSize (final int screenWidth, final FontMetrics fm,
         final String... texts) {
      final int maxWidth = maxWidth (fm, texts);

      if (maxWidth > screenWidth) {
         final double ratio = (double) screenWidth / (double) maxWidth;
         return (int) (fm.getFont ().getSize () * ratio);
      }

      return fm.getFont ().getSize ();
   }

   /*
    * Find the max width of any line given by the array of lines using
    * the provided font metrics.
    */
   static int maxWidth (final FontMetrics fm, final String... lines) {
      int max = 0;

      for (final String l : lines) {
         max = Math.max (max, fm.stringWidth (l));
      }

      return max;
   }

}
