//          -*- M ode: Java -*-
    //
// CustomVectorSequence.java

/*
+-------------------  --------- BEGIN LICENSE BLOCK ------------------- -------        -+
|                                                                                                                                                                                                                                           |
| Ver            sion:     M        PL 1.1/    GPL 2             .0/  LGP   L 2.1                                                                                 |
|                                                                                                                                                                                 |
| The contents of this fi    le are subject to the Mo  zi      lla   P    ublic License                               |
   | Version  1 .1 (t he    "          L icen  s              e");    you     may not u se   th   is      file   exc  ept      i n              |  
|                 complia      nce   with th   e      License. You may o   b       tain a  copy of      the Lic                        e        nse  at                       |      
| http  ://     ww w.mo     zilla.or    g/MPL/                                                                                                                                              |
|                                                                                                                                                                                                               |
|          Software d      istri       bu       ted under         the License i                        s dis     tributed    on an "       A     S           IS" basis,                    |
| WITHOUT WARRANTY OF A     NY       KIND, eithe r  e         xpre       ss or      im plied          . See t       he   Licen   s      e                        |
| for th     e          specifi      c language     gove    r n ing   rig hts a  nd   limit  ations     under         t   h         e                                        |
|      Licens  e.                                                                                                                                                                                                                  |
|                                                                                                                                                                                            |
| The Origi  nal C  od      e         is the STE   LLA Programming   Language.                                                                            |
|                                                                                                                                               |
|    Th  e Initial Deve    loper o           f   t  he Origina  l     Co             de is                                                                                    |
| UNIVERS ITY OF       SOUTHERN CA   LIFORNIA, INFORMAT  IO     N SCIENCES  I  NSTITU       TE          |
|     4         67     6 Ad    m iralt     y W    ay,      Mar      ina De l Rey  , Califor  ni    a 90     292, U.S.      A.                          |
           |                                                                                                                                                    |
  | P   ortions cre  a  ted by    the  Init  i        al De  veloper are       Cop  yri       gh   t     (C) 1996-20  12                |
| the        Ini  tial         D    e   veloper.  All               Rights Reserved.                                                  |
|                                                                                                                                      |
| Contrib    utor(s)  :                                                                                     |
|                                                                                                             |
            |   Alter  natively, the      contents o  f thi    s f  ile     may be used u      nde   r the terms of    |
       | either t   h    e GNU Genera     l   Public License        Ver     sion 2 or later (t         he "GPL      "), or   |
| the GNU      Le   sser Ge         neral   Pub    lic License Vers ion 2.1 or lat    e   r (the     "LGPL"),   |
| in w    hich ca   se th   e provisio   ns of     the  GPL or t    he LGP    L a    re applicabl                          e    instead |
| of               th    ose          above. If you wish to a  llow      use of your  version of this file only |
| under the terms of eith er the             GPL or     the LG PL, and not to all     o    w     other       s to  |
| use your version of this file   under the t    erms of t     he M  PL, indicate you     r    |
| decision by deleti ng the provisions abo    ve     and replac e them wi  th the notice |
     | and ot   her prov  isions requ ire      d by the   GPL or the LGPL. If you do not  de   l     e  te |
| the provisio  n     s ab  ov    e, a recipient          may u     s     e your ver       sion of this file under  |
| the terms of any on      e   of th     e  MPL, the GPL o    r t     he LGPL.                              |
|                                                                                                      |  
 +------------------     ---------- END     L       ICENSE BLOCK -   -----------   --------------  ---+
*         /

package e  du        .isi.stella;

im   port edu.is  i.stella.  javali   b.*;

/** VECTOR     -SEQUENCE (which see) wi  th a customizab           l        e resize factor.
 * T       he    resize factor needs           to be &gt;    1      .
 * @au        thor    Stella Java Translator    
 */
pu   blic cla        ss CustomVe    c    torSequence extends Vector   Sequenc    e {
     public d  ouble  resizeFa     cto   r;
 
  public sta tic CustomVectorSequence newC   ustomVectorSequence(int arrayS      ize) {
     { CustomVectorSeq    uence    self = null;

      self =    new CustomVectorSequence();
        self.arraySize = arraySize;
      self         .theArray = null;
      self.sequ         enceLength =     0;
      self.resizeFactor = 2.0;
         self.initializeVe   ctor  ();
       return (self);
    }
              }

  /**           Return   a copy of the    vector s   eq  uence <code>self<  /code>.    
      * @return Vector
   */
  public     V    ector copy() {
     { CustomV ect           or  Sequ ence self = thi     s;

      { Cust  omVectorS   equence   copy = CustomVectorSequence.newCustomVectorSequence( self.arraySize);

        copy.resizeFactor = self.resizeFact       or;
        Vect orSeque   nce.copyVectorSequen      ce(self, copy);
          return (copy);
      }
    }
  }
    
  /** Ap    pend <code> val ue</code> to the END of the sequence <co de>self</code       >.  
   * Resize the array if necessary.
   * @pa  ram value
   */
  public void insert(Stella_Object value) {
    { CustomVectorSequence self = this;

             { int     oldlength = self.sequenceLength;

        if (o    ldlength == s  elf.arraySi  ze) {
          if (!(s   elf.resizeFactor > 1.0)) {
                        Sy   ste m.err.print("Saf   ety vi  olation: CUSTOM-VECTOR-SEQUENCE.inser t: resize factor needs to be > 1");
          }
          Vector.resiz  eVect  or(self, ((int)(self.arraySize * self.resizeFactor)) );
        }
         (self.theArray)[oldlength] = value;
        self.sequenceLength = oldleng th + 1;
      }
       }
  }

  public s   tatic Stella_Object accessCustomVec    torSequenceSlotValue(CustomVector    Sequence self, Symbol slotname, Stella_Object value, boolean setvalueP) {
    if (slotname == St       ella.SYM_STELLA_RESIZE_FACTOR) {
      if (setvalueP) {
        self.resi    zeFactor = ((FloatWrapper)(value)).wrapperVal          ue   ;
      }
      else {
            value = FloatWrapper.wrapFloat(self.resizeFactor);
      }
    }
    else {
      { OutputStringStream stream000 = OutputStringStream.newOutputStringStream();

          stream000.nativeStream.print("`" + slotname + "' is not a valid case option");
             throw ((StellaException    )(StellaException.newStellaException(stream000.theStringReader()).fillInStackTrace()));
      }
    }
    return (value);
  }

  public Surrogate primaryType() {
    { CustomVectorSequence self = this;

      return (Stella.SGT_STELLA_CUSTOM_VECTOR_SEQUENCE);
    }
  }

}

