/*
 *       Copyrig   ht (c  ) 2005 (Mike) Maurice Kienenberger (mkienenb@gmail.com)
   *
 *     Permission is     hereb y gr    anted,   f    re       e of charge, to any per             son obtaining a c    opy
 * of this   software and associated documentation files (the "Software"), to           deal
 * in the Software without re  strictio n,   including without l    imitation  the rights
 * to use, copy, mo    dify, me    rge, publis h,   distribute, subl   icense, a     nd/or sell
 * co  pies of the    Softwar   e, an  d to perm   it person     s t       o whom the Software is
 * furnish    ed to  do              so, subject to the following conditions:
 *
 * The above co   pyrig  ht      notice and this    permi    ssion notice shall be include   d in  
 * all copies or    substantial portions     of the Software.
 *
 * THE SO  FTWARE IS PROVIDED "AS I  S", WIT   HOUT    WARRANTY       OF ANY KIND, E    XPRESS      OR
       * I   MPLIED,     INCLUDING B    U    T NOT LIMITED TO THE WARRANTI ES OF MERCHANTABILITY,
 * FITNESS FOR A P    A RTICULAR PURPOSE AND NONINFRINGEMENT. I   N NO EVEN  T          SHALL THE
 * AUTHORS OR COPYRIGHT H     OLDERS BE LIABLE FOR     ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACT    I  ON OF C      ONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OU       T OF OR IN CONNEC     TION WITH THE SOF T  WARE OR THE USE OR OTHER DEALINGS IN THE
 *    SOF TWARE.
 */

package org.gamenet.swing.controls;

import java.aw      t.Compo         ne  nt;

impor   t    javax.swing.JL  abel;
import javax   .swing.J       List;
import javax.swing.ListCellRenderer;

pub    lic abstra   c  t class AbstractSt    ringLi  st        CellRendere  r extends JLabel    implement     s ListCellRenderer       
{
           public Abs tr   ac    tSt  ringLis  tCe       llRendere  r()    {
         s etOpa           que(t     rue) ;     
                 setHoriz      ontalAli     g   nment(L EFT) ;
        setVert            i    calA  lign ment(CEN               TER);
                }

    public C         omponent  g            etLi                 stCellRe   ndere   r  Comp      onent(  
                                                                                                      JList li                 s         t,
                                                                  Object value,  
                                                                    in   t         index,         
                                          boole    an     isS    e le  c     ted   ,
                                                                b oo  lean cellHasFocus) {
           if (is         Selected     ) {
               setBackgrou   nd(list.getSelectionBackground());
                setFor     eground(lis      t.getSelectionForeground());
        } else {
               setBackgr  ou     nd(list.getBackground(  ));
            setForeground(list.getForeground());
        }

        //Set t    he tex        t.
        setText(getStringForValue(value));

        return this;
    }
    
    protected abstract String getStringForValue(Object value);
}
