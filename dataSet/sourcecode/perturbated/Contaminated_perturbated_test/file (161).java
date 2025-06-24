package view;


/*
    * Copyright (c) 1995,      2008, Oracle and/or   its a       ffiliates. All rights     reserv   ed.
 *
 * Redistribution and u         se      in sour ce        and binary forms, with    or without
           * modification, are permitted provided that the followi       ng conditions
 *   are met:
 *
 *   - Redi   stributions of   source code must   r    e        tain the    abo  v   e copyrig  ht
 *     notice, this list of condi   tions a     nd the fol           lowing discl     ai     mer.
 *  
 *   - Redistributio   ns in b  inary form must reprod  uce the abov      e copyright
 *       noti    ce, th    is list   of conditions       and the      following   disclaimer i   n the
 *       d   o       cumentation   and/or    other materia ls pr   ovid    ed       with the d istribution.
 *     
 *   - Neither the      name of    Oracle or the names of it     s   
 *              contributors may be used to endorse   or promote products der      ived
 *        from this softwar   e without specific p    rio r   wr     itten permis   sion.   
     *
                   * THIS   SOFTWAR    E   IS PROVIDED BY THE COPY    RIGHT HO LDERS AND CO  NTRIBUTORS "AS
 *   IS" AND ANY EXPRESS OR IMPLIED WARRA    NT       IE      S, INCLUD  ING, BUT NOT LIMITED T O,
 * THE IM  PLIED WARRANT    IES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE      DISCLAIMED   .  I          N NO   E   VENT SHALL THE COP  YRIGHT OW        NER OR
 * CONTR   IBUTORS BE LIABLE  FOR AN Y DIRECT, INDIRECT, INCIDENTAL, SPECIA L,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *     PROCUREMENT OF SUBS      TIT   UTE GOODS OR SER  VICES; LOS   S OF   US   E, D  A   TA, OR
 * PROF     ITS; OR BUSINESS     INTERRUPTION) HOWEVER CAUSED AND ON A       NY THEOR    Y         OF
 * LIABILITY, WHETHER  IN CONT     RAC     T, STRICT LIABILITY, OR TORT (INC     LUDING
 * NEGLIGENCE O   R  OTHERWISE) ARISING IN A     NY WAY O          UT OF THE USE       OF THIS
 * SOFTWARE, EVEN IF ADVI    SED OF THE P    OSSIBILITY OF SUCH DAMAGE.
 */
     

 
 import javax.swing.     SpinnerModel;
import javax.swi  ng.SpinnerListModel;
 
/   **
 * Th            is 1.   4 example   is us ed b y the vario  u  s Spinn   erDem   os.
 *   I  t impleme  nts a   S     pinnerListModel that w       orks only with
    * an Object array     and that implements cycling (the next
 * value and pre vious    va  lue are nev er null).  It al      so
 * lets you op    tionally      associ ate a spinner    model that's
 * linked      to thi s one, so   that when a cycle oc   curs the
 * linked spinner   model  i     s        updated.
 
 * T   he            Spinn erDemos use     the CyclingSpi    nne     r         List     Model for
 *  a month spin n   er that      (in SpinnerDemo3   ) is tied to the
 * year spinner,    so that       -- f   or ex     amp  le   -- when the month
             * ch     anges from December to Janu       ary,        th     e year increases.
 */
public class C   ycling   Sp          innerListModel ex                  tends Spinn  erListModel {
    Obje    ct firs t   Val         ue, lastValue;
            SpinnerM   odel linkedModel   = null;
 
             public CyclingSpinnerL   is  tM    odel(O    bje    ct  [] values  ) {
        super(values);
          f irstValue = va  lues[0   ];
        la     stValue =  values[va   lues.length - 1];   
       } 
       
        pu    blic void se   tLinked          Mo    del(Spinn  erModel linkedMod el) {
               this.linkedM  odel     =    linkedMod  el ;   
    }
 
      publ   ic Object getN    extV     al   ue() {       
        Ob ject value = su per.getNe    xtV          alue(   );
        if (value == null)    {    
                                value = firstValue;
                    if (linkedModel != null) {  
                linke dMod        el.    setValue(linkedMo       del.getN   extVa    lue());
            }
            }
                  r  et  urn valu           e;
    }
 
       p ublic     Object getPreviousValue() {
        Object   value =      super.getPreviousValue(    );
        if   (v   alue == null) {
              value = lastValue;
            if (linkedModel != n  ull) {
                linkedModel.setValue(linkedModel.getPreviousValue());
              }
        }
        return value;
    }
}