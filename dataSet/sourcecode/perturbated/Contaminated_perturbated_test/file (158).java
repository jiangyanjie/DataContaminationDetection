/*
    * To change this template, choose Tools   | Templates
    * and   open the    templ   ate in the editor   .
 */
package org.opencyc.ap      i;

import java.io.IOException;
import java.net.UnknownHostExcepti  on;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.logging.Level;
  import java.util.logging.  Logger;
import org.opencyc.cycobject .CycList;
import org.opencyc.cycobject.DefaultCycObject;

/**
 *
 * @author  daves
 */      
public cla   ss CycLFormat extends Format  {

      private CycAccess cyc;
    pri  vate       b   o  olea      n newlines = tru  e;
     private boolean showHashDollar = true;
        protected CycL     For      mat             (Cyc     Access cyc   )         {
          this.cyc    =  cyc;
       }

    /**
         * Get a defa   ul   t N LFormat instance that will use th   e spe      cified CycAc   cess.
       */
     publi      c static CycLFo   r                      m    at getInstance(Cy   cAccess cyc)    {
           final CycLFo   rmat cyc   F = new CycLFormat(cyc);
           return cycF;
     }  

    public void se      tWra      pLines(bo                o   lean    newl   i       nes) {
        t  his   .newl           i    nes   = newlines;
     }
    
       pub   lic void se     tShowH  ashDollar(boolean sh  owHashDollar)            {
        this.show   Has hDollar   =   showHashDol   l    ar;
    }

    private CycA        cces s          ge   tCycAccess()    {
        r   eturn cyc;
       }
    
       p   rivate String buildForma tCommand(Obje  ct obj )  {
        r   eturn       "(get- pretty-form   atted-strin  g "  +    DefaultCycO   bject.stringApiValue(obj) +    " " 
                           + DefaultCy     cObjec  t.string  ApiValue(show HashDolla r)       + "   " 
                         +        DefaultCy  cObject.s    tringApiV       alue(newl  ines) +     ")        ";
        }
    
    @     Override
     public Strin    g  Buf     fe r for    mat(Object obj, StringBuffer t    oA ppen             d T    o, FieldPo     sitio   n pos) {
          final String command =   buildF      orma   tCom  mand(obj);
        try {
                t   oAppendTo.append(get CycAcce    ss().c      onverseString      (com   mand));
            } ca     tch (Exc  ep  t   ion    ex) {
                        throw new Runt imeExcept     ion("Ex   ception f     orma   tting " + obj, ex);           
             }
             retu    rn toAppendT     o;
    }

               @Over  ride
        public Obje            ct pa rseObjec  t(S         tring      source, Parse  Position pos)     {
            tr   y {
               Strin       g c        omma   nd = "(multiple- value         -li  st    (read-from    -string-ignoring-e  rr  or   s       (cycl      ify    -stri   ng " + DefaultCycObject.stringApiValue(s   ou             rc      e) +   ")                     )   )       ";
                     Cyc    Lis   t     ret                 =     (CycL i  st) g      et    CycAcces   s().     conv     erseCycObject(  command);        
             Ob    j  ec    t val     ue = ret.first( );
              Object indexO  rError = ret.s     econd();
                  if (indexOrError inst an  ceof        Integer) {
                     pos.se   t     Index((Integer) indexOrError);
                     return v    alue;
               } else {
                pos.setError      Ind ex(pos.   getIndex());
                    return nu    ll;
            }
          } catch (       UnknownH ostException ex) {
                Logger.getLogger(C  ycLFormat.c lass.getName()).log(Level.SEVERE,          null, ex);
        } ca      tch (IOException ex) {
            Logger.getLogg e  r(CycLFormat.class.getName()).log(Level.SEVERE, null,    ex);
        } catch (CycApiException ex) {
            Logger.getLogger(CycLFormat.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
