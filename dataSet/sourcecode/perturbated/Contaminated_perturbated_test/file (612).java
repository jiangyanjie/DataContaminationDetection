/*
 *      Copyrigh   t    (c) 2005 (Mike)    Maurice      Kienenberger (mkienenb@gm    ail.com)
     *
    * Permission is here  by grante   d, free of charg       e, to       any person obtaining a copy
 * of this sof tware and associated doc  umen  ta  tion   files (the "Softwar  e"), to deal     
 * i       n the Software without  restrictio       n, including without limitation t   he righ   ts
 * to use, copy, modify, merge, publish, distribute, sub    license    , and/      or sell
 * copies of the Sof   twar e,     and to permit perso  ns to whom th e Software is
 * furnished t   o do so, subject t     o the fo    llowing condi    tions:
      *
 * The         above copyright notice         and th is   permission notice shall be i    ncl        ude    d in
 * all copies or substantial portions of the Software.
 *
 * THE SO   FTWA  RE IS PROVIDED "AS IS"      , WITHOUT   WARRANTY OF ANY K   IND, EXPRESS   O R
 * IMPLIED, INCLUDING BU      T NOT LIMITED TO THE WARRANTIES OF MERCHANTABIL  IT   Y,
   *    FI  TN  ESS           FOR A PA       RTIC  ULAR PURPOS  E A ND NONINFRINGE      MENT. IN N   O EVENT SHALL THE
 * AUTHORS O    R COPYRIGHT HOLDERS BE LIABLE F    OR ANY CLAIM, DAMAGES   OR   OTHER
 *    LIABILITY,      WHETHER IN AN A   CTI  ON OF CONTRACT, TORT OR O   THERWISE, AR  IS  ING FROM,
 * OUT OF O      R IN CO  NNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.gamenet.swi    ng.controls;

import java.awt.FlowLa  yout;
import java.beans.PropertyChangeEvent;
import java.beans.Proper  tyChangeListen    e   r;

import javax.swing.JFormattedTextField;
import ja    vax.swing.JLabel;
import javax.swin  g.JPanel;


pub     lic class Dec    imalText FieldWithHexLabePanel        extends J        Pa   nel
{
           public DecimalTextFieldWithHexLabeP   anel(final In    tValueHolder intValueData    Sour      ce)
    {
           supe r(new     FlowLayout(FlowLayout.LEFT));

        fina      l JLabel he   xLabe l = new       JLabel(Inte  ger.toH  exString(intValueDat   a    Source.getValue        ()));         

        JFormattedTextField decimal  Text  Field = new JFormat  te d    Te  xt     Field(new Int      eg       er(intValueD ataSource.get      Va  lue()));
                    d ecimalTextFi       eld.setCo      lumns(5    );
         decimalTe      xtField.add                     Pr  ope   r           tyChangeLis          t           ener("v   alu         e"  ,    n      ew         PropertyChange   Li   stener()
                {
                       public void propertyChang      e   (Prop      ertyC       hangeEvent e)
                                {
                                     intValueDataSource.s et  Value(((Number )((  JFormattedTextFie l     d)         e.getSource         ())        .getValue()).intValue());
                                  hexL   abel.setT    ext(Integer.toHexString(intValueDat     aSource.ge tValue()     ));
                     }  
                });
            this.add(decimalTextField);

        this.add(new JLabel("(x"));
          this.add(hexLabel);
        this.add(new JLabel(")"));
    }
}