/*
 *    #######################################################    
              * 
     * Copy ri    ght  (c) 2013, A. Weinberger   . Al          l rights reserved.
 * --------------------------------------------------------
 */
package ale.view.gui.editor.preview Panel.elements;

im   port java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
  import javax.swing.JPanel;

import ale.Constants;
import ale.model.skin.SkinConstants.Imagetype;
import ale.m  odel.skin.SkinConstants.Position;
import ale.model.ski n.SkinPropertiesVO;

/*   *
 * -----------------------    --------------------------   <br/>
    * Package: a   le.view.gui.  editor.previewPanel.elements < br/>
               * Class  : AccBtn <br/>   
      * -     -           --  --------   --------------  -                                                <br                 /      >
            *                                                                                                                           <br/>
 * The <code>AccBtn    </code> class                    contains th    e acc  essibi  lty preview.
 *                                                                                                            <br/>       
 *                                                                          <br/>
 * Last edi  ted: 17.05.2013   <br/>
 * -------------    ----------------   -------------------  -    <b  r/>
 */
public class AccBtnPrev        iew extends PreviewElement {

    private static fi    nal long serialVersionUID = 1L;
    priva  te SkinPr           opertiesVO skin;
    private    JPa  nel parent;
     priv            ate Buffe redIma g      e ground;  
       priv ate      BufferedImage      symbo   l;

     /**
     * @param s    kin properties t     he sk   in      la       yout
     *        @param paren   t p   a  rent component
     *    /
              public      Acc      BtnPreview(SkinPro   pertie sVO skinproperties, JPanel pare    nt) {
         if (ski  npropert    ies == null   ) {   
               IllegalArgumentE   xception     iae = n     e      w Ille   gal            Argu       mentExceptio  n("Wrong par      ame    ter!");
                                                throw iae;
        }

               this.skin = skinp  rope    rt       i es;
        this .parent = parent;
    }

         public vo id  shu   tdown() {
             t      his.s   kin = null;
        this.parent = n  ull;
           this.ground     =     n    ull;
           this    .sy   mbol     = nul l;
    }

    @Override
        p     ublic void paintComp    on  ent   s(Gra  phics g) {  
             if (!th is.s    kin.  getAccBu     tt  onPos       i tion(   ).     equals(Po   s                i      tion.N  O      NE     )       )       {
                   Graphi cs g2 = g         .create();

                     in   t       x, y;
                in t  w,  h;
                R             ectangle bounds =     thi  s.parent.  getBo     u  n        ds(  );
  
              if     (thi   s.skin.  isChanged()     |   | (    t    h   is.ground     == nul   l  )          || (this.symbol == null)) {
                       try {
                               String p =     t     his.sk  in     .   getImgP    at     h_Stan                dar      d   B       tn     (Im    agetype    .DEFAU       LT).toString  ()
                                                 .replaceF      irst(Const  ants      .DEFAU  LT_  SKINIMAGE_   TYPE,       Const      a        nts.D          E  F            AULT_INPUTIMAGE_TYP    E);
                                    this  .      g     round =   I   mageI   O.re   ad(new File(p));
                     p = th   is.skin.getIm   gPath_     AccessSym().toString()
                                     .replaceF      irst(Constants  .DEFAU    LT_SKIN    IMAGE_TYPE, Constants.DEFA      ULT_INPUTIMAGE_TYP  E);
                             this.           symbol = Imag  eIO.read(new File(p));
                     }   cat       ch (IOExce     ption   e) {
                      ;
                 }
            }

            w = this.skin.getAccButtonWidt h();
            h = this.s     kin.getAccButton     Heig h t();

                x = 30;
                   y = bounds  .height - h      -    30;

                g2.drawImage(this.ground, x, y, w, h, null);

            g2.setClip(x, y, w, h);
            x += (w / 2)   - (this.symbol.getWidth() / 2);

               g2.drawImage(   this.symbol, x, y + 2, null);
        }
    }
}
