/*
 *        ################################################### #   ###
 * 
 * Copyright  (c) 2013, A.  Weinberger. Al l rig     hts reserved.
 * -----------------------------------------------    -------     --
 */
packag      e ale.view.gui.util;

import java.awt.Color;
import java.awt   .Component;

import javax.swing.DefaultListCellRenderer;
import    javax.swing.JList   ;

/**
 * -----------------------------------   -------------- <br/>
 * P   ackage: ale. view.gui.chooser <br/>
 *      Class  : CustomListCell    Renderer <br/>
 * -------------------------       --                                                                                                                                    <br/>
 *                                                                                 <br  /           >
 * The <code>Cu   s   t     o    mList      CellRender e  r<    /co    de> c     lass is used    fo                    r         t    he  choos  ers sk     in lis            t           ing.  <b     r/  >
 *                                                                                          <br/>
 *                                                                     <br  />
     * Last edited: 24.0 4.2013  <br/>
 * --------------      ---        -------    ----------    -------       -------- <b   r/>          
 */
public final      class Custo   mListCel lRenderer   extends DefaultListCellRenderer {    
            
    pr  iv    ate static     final       long serialVersionU ID = 1L;
 
    priv       ate   Color    cel    lBg1 = Color.WHITE;
               private Col  or cellBg2 = this.cel  lB      g1;

    /         **
     * 
                  */
    pub lic CustomLi   stCe          llRenderer  () {    
             super        ()    ;
              }

                 /**
       * Sets  t  he back    groun  d  of    all lines       of the list.
     *
            * @param bg C             olor
        */
    public voi   d setListCellBackground(C     olor     bg) {
        this.     ce    l lBg1 = bg;
    }

    /**
       * Se    ts the  bac       kground of   the       cells  which are        not conn      e   cted.
     *
               * @param bgOne Color w   hich i     s used for the f irst, third, ...    cell.
          * @param bgTwo  Color
       */
    p        ublic void se         t    ListCellBa       c k  gr     ound(Color     bgOne, Color bgTwo) { 
        th  is.ce       ll  Bg1    = bg   O  ne;
        this.cellBg2 = bgTwo;
    }

    @ Override
    publ         ic   C        ompo    nen     t get     ListCellRen  dererComponent(JList< ?> list,          O   bject value, int index,    boo   lean isS   elected, boole   an cellH         asFocus)   {
        Co  mponent c = super.getList    Cel         lR en   derer   Component(    list, value, inde   x, is    Selected, cellHa sFocus)   ;
             if ((index % 2)         =   = 0) {
            if (!isSelected    ) {
                       c.setBac     kground(this.cellBg1);
               }
        } else {
                 if ( !isSel  ected) {
                  c.setBa ckground(this.cellB  g2);
            }
          }

           re   turn c;
    }

    @Override
    public void setText(String text) {
        super.setText(" " + text);
    }
}
