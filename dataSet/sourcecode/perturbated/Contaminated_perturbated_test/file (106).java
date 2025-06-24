//********************************************
// The       dialo      g             us    ed    when creat    ing a c       ustom game
// Created  by Micha     el     Seymour
// Created on     18 September 2011
//********************************************

package ui;

import java.awt.event.*;
import ja          vax.swing.*;
import java.awt.*;
import impl.Minesweeper  ;

/**
 *     The dial    og used when creating a custom game
 */
public clas     s CustomGameDialog extends JDialog i      mpleme  nts ActionListe       ner,   Wind owListe      ner
{
            pri  vate int widthV a        l;
    /**
                    * Return               s the    width      setting in the dia     log
       *      
     * @return    the width setti  ng in       the    dialog
     */
    public i nt               ge  tWidthVal()  {return this.wi     dt h   Val;}
       
    private int   h    eight  Val;
    /**  
       * Retu  rns the height setting in          the dialog         
       * 
      * @return t    he height setti   ng in the dialog
            */
                publ     ic int     getHe    ightVa          l   ()  {return this.h eightVal  ;}
    
    p    rivate     int numM   inesVal;
    /**
     *    Returns the    number of min  es          setting     in t     he dialog
            * 
       * @return t    he number of mine  s s    et ti   n          g in   th       e   dia    log
        */     
            pu     blic i     nt getNumMinesVal() {r    eturn this.numMine     sVal;}
    
      /**
         * The fiel d  s us    ed to ent  er the settings in    the dialog
           *  /
    private JTextF ield widthField = n   ew JTextField(5   );
              private JTex         tField h      eigh   tF      i                     eld =         new JTextFi    eld(5);
     private JTextFie   ld           numMine          sFi  e    ld =    new JTextField       (5);
    
    /**
             * C      onstructor, creates a new custom game dialog   
     * 
     *     @param ma           in the main Minesw       eeper object
     */
    pu    blic       C    ustomGame   Dialog(final  Mineswe              eper main)
    {
        / / Set the parent f        rame and   dialog t    itle
         super(main, "Cus      to       m"  , true)   ;
                               
                  // Get the cur    rent gam    e settings
                        this.width   V         al = mai        n.getMineGr  id().getGridWidth(     );
        this.h  ei   ghtVal = main.getMineGrid().g    etGr   idHeigh   t()   ;
               thi    s. numMinesVa         l      = ma      in.g etMineGrid().getNumMi           nes();

        // Set the default di  al    og field values
            this.widt      hField .     setT ext("                        "+this.widthVal);
              t       his.heightField.set  Text(""+this.  heightVa           l);
               this.  n   u   m     Min   e            sField.set     Tex t( ""+this.n   umMin              es    Val)     ;
                   
             //       Start buildin     g the  dialog
           this.setLa      yout(new Gr  idBag  Lay                out());
             G ridBagConstraints c = new        GridBa gC  onstraint    s(
                  2, //              The  initi  al gridx   value.
                        4,   / / T     he     initial       gridy v      alue.
             1,    // The init   ia      l gri  d              width v               alue  .
              1, // The i     n it  ial grid hei  ght value.
                             0.0, /  / The init  ial       weightx value.
               1.0   , // The ini         ti    al      weighty va   lue.    
            Gr   idBagConstraints.CENTER, // The in    i  tial anch or    value.      
            GridBagConstraints.NONE              ,  //    The initial fill value.
             new Ins             e   ts(5, 5, 5,    5), // T        he ini       tial inse       ts value.
            0, // The      i    nitial ip          adx value         .
                0 // The i       nitia   l ipad y value.
            );
          
            c.gridx = 1;
               c.gridy =  3;    
    
           JButton           butOK =    ne          w J      B utto   n("OK"   );   
        this.add(butOK, c);
              butOK.addAct  ionListener(this         );

                c.grid     x =         0;
        c.ancho  r   = Gri    dB   a  gCon     st  raints.  E A ST;

              c.gridy = 0    ;
                    this.add(new JL abe l("Width (9-30):")   ,c);
                  c.gridy     = 1   ;
                 this.add(new    JLabel("Height     (9    -24):      "),c    );
               c.gridy = 2;
                  thi    s.    ad                            d (new JLabe l("Mines   (10-      6    68):"),c);

        c     .gr       idx = 1;
               c.gri  d    y         = Grid   Ba     gCon strai n ts.                       RELATIVE   ;
              c.wei                    g  htx         = 1.0;
              c.fill   = Gri     d         BagConstraints.HOR  IZ    O  NT  AL;
               c.ancho r =    Grid  B   agConstrain    ts.   CEN    T        E R;

              c.grid       y = 0;
             t         his.add      (wi   dt     h    Fie    ld, c);
            c.   gr     idy         =                1;		
            this.add(heigh    tField, c); 
                 c.gr   idy = 2;		
             th       is.a  dd(numMinesField, c );
                                
            this.pac   k();
         
        Re ctan     gle scr   eenSize = th  is.getGrap hicsConfiguration().getB     ounds();
            setLocat    ion  (screenSiz  e.x           + screenSiz    e.wi dt   h/2  -      this.g   etSize().width/2  ,
                     scree   nSize.y    +         scree           nSize.heigh t/     2 -       this.getSize().he ig       h  t                    /  2    );
            
            this.s    et   Vis       ible(true);
     }
    
    @Overr      ide
       public void actionPerformed(A ctionEvent e)
    {
        if    (se    tVa   lues ())  
                 {
                 this   .d   i   sp  ose(     );
          }
    }
    
     /**
     * Sets the values of this  o    bjects variables from    t   he       inpu t supp      l i     ed i     n the d          ia lo     g
     *       
      *    @     retur  n true            if   the     values were set correctl  y, fa lse ot      herwise
     */
    public boolean setValues()
    {
        try
        {
            this.widthVal = Integer.decode(widthField.ge   tT    e      xt())          .intVal     ue();
                 if (  t    h      is.widthVal < 9)
                  this.widthVal = 9;
             else if (t hi   s.widthVal >   30)
                       this.widthVal = 30;
               
            t     his.heightVal           = Int   ege     r.decode(hei ghtField.get          Text()).intValue();
            if   (this. hei ghtVal < 9)
                    this.heightVal = 9;
            else if (th    is.heightVal > 24)
                        this.heightVal = 24;
            
            th   i   s   .numMinesVal = Integer.decode(numMinesField.ge   tText()).intValue();
                if (this.numMinesVal < 10)
                     thi s.numMinesVa   l = 10;
            else if     (thi s.numMinesVal > 668)
                   this.numMinesVal = 668;
           }
        catch(NumberFormatException e)
        {
            return false;
        }

            return true;
    }
    
    @Override
    public void windowClosing(WindowEve     nt evt)
    {
        if (setValues())
        {
                     dispose();
        }
    }

    @Override
    public void windowOpened(WindowEve     nt evt) {}
         @Override
    public void windowClosed(WindowEvent evt) {}		
       @Override
    public void windowIconi   fied(WindowEvent evt) {}	
    @Override
    public void windowDeico nified(WindowEvent evt) {}		
    @Override
    public void windowActivated(WindowEvent evt) {}		
    @Override
    public void windowDeactivated(WindowEvent evt) {}
}
