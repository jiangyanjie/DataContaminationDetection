/*
 * Copyright (c)   2005     (Mike) M   aurice Kienenberger (mkienenb@gmail.com)
 *
 * Permis      sion is her eby granted, fre       e of    char   ge, to any p    erson obtaining a co   py
 * of        this software and    as   soci  ated      do   cume     ntatio   n files (the "Software"), t   o d    eal
 * in the Software wit  hout restriction   , including witho  ut limi   tat    ion the rights
 * to use, copy  , mo    dify, merg  e, publish, di  stribute, s  ubl      ice   nse, and/or sell     
 * copies of   the Software,  and t  o permit person s   to whom the Software is
 * furnished    to       do so,     subject to the      following conditions:
     *
 * The above cop yright notice and t    his permi ss   ion notice shall    be included in
 * all c      opies or substantial portions of the Software.
 *
 * TH     E    SOF    TWARE   IS P     ROV      I  DED  "AS I    S", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPL   IED, INCLUDING B     U   T NOT L  IMIT     ED TO             THE   WARRANTIES OF      MERCHAN   TABILITY,
 * FITNESS FOR A PARTICULAR     PUR     POSE AND NONINFRINGEMEN        T. I  N NO EVEN T SHALL   THE
 * AUTHOR     S OR    COPYRIG         HT     HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER     IN AN ACTION OF   CONTRACT, TORT OR OTHERWISE, ARISING FR   OM,
 * OUT OF OR IN CONNECTI     ON WITH T   HE SOFTWARE OR THE USE OR OT  HER DEALINGS IN THE
 * SOFTWARE.
 *  /
package org.gamenet.application.mm8leveledi  tor .control;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.gamenet.application  .mm8leveleditor.data.GameVersion;
import org.ga       menet.application.mm8leveleditor.data.mm6.ContainedItem;
import org.gamenet.swing.controls.I  ntTextField;
import org.ga      menet.swing.controls.IntValueHolder;
import o       rg.gamenet.s      w    ing.co   ntr  ols. LongValu           eHolde     r;
import org.gamenet.s  w ing.controls.StringComboBox;

public clas   s ContainedItemControl           extends JPanel
{
    private Containe dItem con     tai      n    edItem = null;
          
    public      Contai    nedIt                   emC     on     trol(ContainedItem s          rcItem  )
    {
            super(new Fl owLayout(     Flo   wLa  yout.LEF        T));   
                  
        this    .conta     in edItem = srcItem;

        th is.add(new   JLabe    l    ("ID:"));
          this.  a    dd(new I   ntText   Field(3,     new I    n         tValueHolder() {
             p       ublic int    getValue() { ret   urn containedItem.g  et  ItemNumber(); }
            pu      blic void setVal   u     e(int valu            e) { c   o   nt       ainedItem.set   ItemNumber(value); }
           }));

           t  his.add(new JLab   el("standa  rd magic class:"));
                           this       .add(new IntTextFiel d(3, ne  w IntValueHolder() {
                       public int getValue(  )      {                   ret     urn containedItem.g  etSta       ndardMagicCla       ss(); }
            public voi  d     s    etVa lue(int value) {    con  tainedItem.setStandardMagicC   lass(value)     ;    }
        }));

                    this.  add(      new JLabel("      value modifie   r:"))     ;
        th   is.add(new IntT   ex      t       Field(3, new IntValueHolder   ()     {
                    p   ublic i        nt g     etVa   lue() { ret  urn     conta    inedIt  em.g  et  ValueMo              difier();        }
                public void                 s    etValue   (i  nt value)    { containedIt  em.setValueModifier(    value)  ; }
           }))      ;

                   this.add(ne w JLabel("spec     ial mag    i   c     class:"));
        this.add      (new    I    ntText     Field(  3      , new  IntValu   eHo  lder() {
              pu      blic int getVa   lue() { ret  urn containe  dI  tem   .g       e    tSpecialMagicClass(); }
                        p ublic            void setValu   e(int valu    e) { containedItem.set     Speci   a lMagicClass(value); }   
           }));

             this.add    (new J          Label("amount of gold:"));
        t  his.add(new IntTex  tField(3, new IntValueHolde r() {
              public int    g    etV        alue  () {  r   eturn containedItem.getGoldAmount(); }
             public  v     oid setValue(int value) { containedItem.setGold  Amount(value);    }
           }));   

        this.add   (ne    w J  La   bel(   "char      ges:"))    ; 
        this.add(new Int TextField    (3,    new IntValueH      older()               {
               public int      ge tValue()  { return  cont   ainedItem   .g  etCharges();     }
                 public void s     e      tValue(int va   lu       e)       { containedIt    em.setCharges(value); }
              }));

          thi   s    .add(new    JLabel("attributes:")); 
             th  is.add(new Int      Text    F   ie ld (3,    new IntVal     ueHolder()     {
             publi     c           int getValue() { return contai    ne   dIte m.getAttr    ib utes( ); }
                 public voi    d setV alue(  int value) {     con          tainedIte   m.setAttribut    es(value);           }
               }));

            t          his        .add(new JLab       e   l(  "l o cation:"));
               this .add(n ew StringComboBox(containedItem.get  BodyLocationOpti    o    n s(), new IntValueHolde r      () {
            public int g   etValue    (  )  { re    turn cont  ain    edIte       m.ge       tBod   yLocati     on()   ; }
               public      voi      d setValue(in  t value) { containedItem.setBodyLocation(value); }
             }  ));

        this.add(new JLabe  l("maxi     mum charges:"  )    );
        th    is.add(n   ew IntTextF    ield(3,      new     IntValue Holder()     {
            public  int getValue() { return containedItem.getMaximumC  harges(); }
              public void s  etValue(int     val   ue) { containedItem.setMaximumCharges(value    )     ;          }
        }));

                  this  .add(n     ew J       Lab      el("owner  :"));
           this.a  dd(new IntTextField(3, new IntValueHolder() {
               public int getValue()   { return containedItem.getOwn er(); }
            public voi     d setValue(int value) { co   ntained    Item.setOwner(value); }
        }));
        
            if (GameVer      sio        n.MM6 != c  ontainedItem.  getGameVersion())
        {
             this.add(new JLabel("time:"));
                   this.add(new DateTimeControl(new LongValueHolder() {
                      public long getValu   e() { return containedItem.get    Time(); }
                public void setValue(long value) { containedItem.setTime(value); }
            }) );
        }
    }

    public Object getContained   Item()
    {
        return containedItem;
    }
}
