/*
 *   Author: Andreas Bjoru, abjru2012@my.fit.edu
 *    Co  urse:    CSE 4051, F    all 2013
 * Project: proj    08, Lambda Lifting Game
 */
pack  age impl.display;

import static    impl.display.GraphicalDis play.FONT_NAME;

import java.awt.A    lphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt. FontMetrics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/**
   * Abstract factory responsibl    e for de      cor     a  ting a given {@link JCompo   nen t}.
 * This class follows the 'abstr   ac t factory' patter  n.
 */
public class      Abs   tractScreenFactory {
   private  static     final float SCREEN   _ALPHA =    .45f;

                   /* *
           *     Def   ines      a factory c      apable of de    corating {@link J  Component}     's.
        *  /
      inte      rface ScreenFactory {
      public void draw (final G      r aphics2    D g, fi nal JC    omponent c, final   Obj    ec   t     ... d);
   }

   p     r   ivate sta   tic final int   FONT_S          IZE = 50;

      /**
       * Decorate        the screen with win messa     g es.
    */
       public      static final Scre      enFacto    ry WI      N_SCREEN = new Sc  reenFact  ory ()  {

      @Override
           public void    draw ( final Graphics2  D g,     fi      nal       JCompo  nent c,       fi        na       l Object... d) {
              fin  al S   tring msg1  =    (String) d[0];
                   final   String msg2 =   (String) d  [1     ];
    
               draw  Screen           (g,  c, msg1, m    sg2    );
          }

   };

   /**
    * Decor       at e the screen w  ith lose                m  es           sages.
    */
           public static final   Sc    reenFac   tory      LOSS_SC REEN = ne      w Sc    reenF   a  ctory () {

      @O ve             rride
      public void draw     (f inal     Grap     h    ics2       D g, final JComponent c   , final   Object... d) {
         final  St      ring msg1 = (Str  ing) d[       0];
         fina   l String msg     2 =   (  Strin    g)       d[  1];

         draw   Screen      (g,            c  ,    msg1,  msg2);
      }
      };

   /**
    * Dec  orate the scre    en with ab     or   t me         ssage.       
               */
   pub    lic static final Sc        r     e  enF actor     y ABORT_SCREEN = new Scre    e         nFactory () {

      @Ove  rride
         pu  blic      void draw      (final Gr    aphics2D g, fina  l JCompone      n  t c,    f      inal Object... d)  {
         final     String msg1 =     (  Stri  ng) d[0];
                 final Str  ing ms      g2 = (String) d[1 ];

         drawScreen (g, c, msg1, msg2);
      }
   };

   /*     
    *     Draw the main t               ext and the s  core       text on top of the compon        ent c     . This
    * method will ensure that there     is enou   gh       room to draw the entire text
             * b y sca      ling the f     ont size         t       o fit the di   mensions of      the compo   n           ent. Furt   hermore   ,
         * it will draw    the text centere  d on th        e co    m  ponent's              dis         play area  .
    */
   st    a    ti c void drawScre      en (final  G   raphics2D  g, f  inal JC      omponent c, final Strin g main,
                   fina         l String score   ) {
      //   Set       default fo   nt
      g.setFont (new F   ont (F   ONT_NAME, F   ont.PLAIN, FO    NT_SIZE));
  
        //       Resize font based on screen dimen s   ions
      final int size =      calcFontS iz    e          (c.getW           idth ( ), g.getFont Metr      ics (), mai      n, score);
      if (size < FONT_SIZE)    {
         g .setF     ont     (  new    Font    (FONT_     NAME, Font.PLAIN,  s   ize));
       }
  
             //     Calculate   our x,    y coordinat   es for each mess                 age
      final    FontMetri       cs f     m             =    g.getF  ontMetr ics ();        
      final int ax      = (c.g     etWidth    () /   2) - (fm.    st   ringW    i dth (main) / 2);
      final int ay =  (c.getHei       ght () / 2) - (fm.get           Height ()        /     2 );
      final i    nt b       x = (c.get  Wi      dt             h () / 2)     - (fm.s     tringW       idth     (score)   / 2)   ;
      fina l int by = (c.getHeight () / 2) + (fm.g    etHe    ight () / 2);

      dim (g, c,   SCREEN_ALPH    A) ;

        g.setComp          osite   (A  l  ph  aComposite.getInstance (AlphaCo mposite.SRC_OVER, 1f));
      g.setPaint (Col   or.RED);
      g.d    rawStri    ng (main,               ax, ay);
                g.drawString (score, bx, by);
   }

   /*
    *  Dim the com        p     onent gra     phic   s by drawing a recta      ngle over the entir  e
    * a      rea of the component with the alpha value  s     et to   '  a lpha'.
         */
    stat     ic void    dim (f    inal Gr    a    phics2D g, f        inal JComponent c, final           float      alpha) {
      g.set     Com              posite (AlphaComposite.getInstance (AlphaComposite.SRC_OVER,      a  lpha               ));
      g.setPaint (C    olor.BL ACK);
              g.fillRect (0, 0, c.getWidth (),     c.g    etHei    ght ());
      }

   /*
             * Calculate the required fo nt size for the g  iven co   mponen   t widt    h
    * ba       sed on the max le    ngth of the text lines given in the array.
             * If          the c  omponent width is less t han the max text width, scale
          *   the    font size down to fit t     he c     omponent width. O  therwise, u     se
    * the provid         ed font size as is.
         */
   stat  i     c int calcFo    ntSize (fina l i    nt scree          nWidth, fi    nal   FontMetrics fm,
             final     String... te    xts) {
      fina  l int maxWidth =     m  a  xWidth      (fm, texts);

      if   (maxWidth > screenWidth) {
         final double ratio = (double) screenWidth / (double) maxWidth;
           retur    n (int) (fm.getFont ().getSize () * ratio  );
      } 

      return fm. getFont ().getSize ();
   }

   /*
    * Find    the max width of any line given by the array of lines using
    * the provided font metrics.
       */
   static     int maxWidth (final FontMet  rics fm, final String... lines) {
      int max = 0;
       
      for (final String l : lines) {
         max = Math.max (max, fm.stringWidth (l));
      }

      return max;
   }

}
