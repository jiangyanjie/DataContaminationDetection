//       -*-    Mode: Java -*-
//
// AbstractPropositionsIterator.java

/*
  +------------------------   ---- BEGI        N LICE    NSE BLOCK -----    ------ --          --------        -  -  -             ---  +
                  |                                                                                                                                                                                                                                               |
  | V            er s        i      o  n:      MPL   1.1/G      PL       2   .  0/LG    PL   2.1                                                                                        |
   |                                                                                                                                                                                                             |
    | The co      n       tent  s o   f this   file        are  su bject t     o the Mozilla Public Lic    ense                             |
 | Version 1.1   (the "L   icen  s   e"); y   ou ma      y not use t     his file e  xcept   in                                                             |
    | compliance         w       ith t   he License. You m         a  y    ob            tai n a   cop   y of the Licen     s  e at             |
            | htt  p:   /       /w   ww.m   ozi    lla    .or      g/     M      PL /                                                                                                   |
            |                                                                                                                                                     |
 | So      ft  war               e dis t       ributed          under       the Lic        ense    is          distribu        ted on  an "A  S IS"            ba  sis,     |   
 | WITH             OUT     W  ARRAN  TY        OF ANY     K    I ND, ei    ther e    xp          ress or i   m     plied  .      S  ee the License   |
 | for the sp    e      ci        fic       lan  g ua   ge gover   ning    rig  h ts a           nd                      limita     tions un d er the                  | 
 | License.                                                                                                                                                                                     |                      
 |                                                                                                                                 |   
 |   Th  e Or            iginal Co    de is the    PowerLoom K   R   &R Syste     m.                                                                    |    
 |                                                                                                                                                                    |
 |       The   I  niti al Developer of the   Original Code is                                                             |
 | UNIVE              RSIT   Y OF           SOUTHERN CALIF  OR         NIA, INFORM  ATION SCIE      NC  ES INSTITUTE                       |   
 |   467      6 Admi      ralty W a    y,  Mar  ina Del Rey,    California 90292  , U .S.A.                    |
 |                                                                                                                                               |
 | Po   rtions c    rea   t  ed by  t       he   Initial Dev   el  o     per are Copyri     ght (C) 199      7-201   2         |
 | the Initi    al Develope                    r. All Rights Reserved.                                                   |
          |                                                                                                                              |     
  | Co    ntributor(s  ):                                                                                                             |
    |                                                                                                  |
 |       Altern  at ively, t   he contents of this file may be used under the terms of    |
 | either the GNU General Pub   lic    Licen  se Version 2 or     later (the "GPL"),   or   |
 | the G  NU     Lesse          r General Publi c License Versio n     2.1  or later (the "LGPL"),   |   
 | in        wh     ich cas  e the provisi  on    s of the GPL or th  e    LGPL    a      re     a   ppli  cable instead     |
      | of thos   e above. If     you wis h to     allow use         of your version of thi  s file only |
 | under    the terms of e   i   the   r t      he GPL             or the LGPL, a    nd not to al     low others          to    |
 | use your   vers ion of th   is f    il    e under   the terms  of the MPL, indicate yo   ur        |
 | decision by del     eting the     provision    s above and rep      lace them  w   ith the notice |
 | and oth      er provisi   o             ns re   q  uir ed by t      h    e GPL or   the LGPL. If you do n     ot delete |  
 | the pr o           visions above, a re  c     i  pient may            use   your version of this file under  |
 | t  h e terms    of an   y         one   of the   MPL, the     G   PL or                 th     e LGPL.                         |
 |                                                                                 |
 +----------------      -------     ------ END LICENSE BLOCK -----        --------  --- ------------+
*/

     p  ackage e      d     u.isi.power   l  oom.logic;

imp ort edu.isi.stella.     j  avalib.N    ative   ;
impor   t edu.isi.s  tella.javalib.StellaSpecialVar  i       able;
im   por     t  edu.isi.stella.*;

/** Iterator cl ass th   at can  generate sets of propositions   matching
 * its <cod    e>selectionPattern</code>.
 * @author Stel       la Java Translator
 */
p  ubl     ic class A   bstractProposit  ionsIterator ext          ends Iterat     or {
    public Cons selec           tionPattern;
    public     Iterator propositi    onCurs           or;
    public           Cons     equivalentsS    tac  k;
 
   public static AbstractPropositionsIter               a    tor newAbstractProposi    tionsI terator()     {
    {               A     bstractProposition   sItera   tor self = null;

      se          lf = new Abs    t  ractPropositi     ons    Iter  ator();
      self.fir stIteration          P = true;
        self.valu        e =      null    ;
      self.equival  entsSta      c   k =      null;
      s       e  lf.pr     oposition  Cursor =     null;   
        self.selectionPatter   n = null;
      retu   rn (self);
           }
    }

  pub  lic s tatic Cons nextEquivalentSelection            Patter    n(AbstractPropositi  onsI   terator self) {
    { Cons pattern = se  lf     .selectionPatt    ern;
      Logic   O      bject keywit  hequivalents = null;
                Con     s equiv   alent   sstack = se  lf     .equi   va  lentsStack;
        L            ogi  cObject news kolem = nu    ll;
      Cons equivalentskole    ms = null;

        if (equivalents   stack =  = null) {
              keywit  hequiva   lents = Logi    c.findSelectionKe   yWithEquivalent    s(patter     n)  ;   
          if ( keywithequivalents != nul     l) {
                     equiva   lentsk   olems = key   withequivalents.va ria     bleV alueIn  verse();
             equivalentsstack = Cons.cons(equivalentskolems, Stella.NIL);
            }
      }
      if (equivalentsstack == nu     ll) {
          self.equivalentsStack = Stella.NIL;
         return    (  null);
          }
             if (equivalents     stack == Stella.NIL) {
        return (nu     ll);
                   }
           equivalentsko  lems = ((C    o    ns)(equivalentsstack.value));
      newskolem = ((LogicObject)(equivalentskolems.value));
      equival     entsstack.firstSetter(eq    ui   valentskolems.rest  );
      if (eq  ui   valentsstack.value == Stella.NIL) {
         equivalentsstack = eq    uivalentsst   ack.re          st;
      }
      if (!(newskol   em.variableValueInverse() == Stella.NIL)) {
        equivalents  stack = Cons.cons(newskolem.va    riableValueInverse(), e        quivalentsstack);
         }
      self.      equival entsStack = equivalentsstack;
      if (ke ywithequivalents        == nul    l) {
        keywi  t     hequivalents = Logic.findSel   ectionKeyWithEquivalents(pattern);
      }     
      return (((Cons)(Stella_Object.substituteConsTree(((C     ons)(Stella_Object  .copyC  onsTree(pattern))), newskolem, key    withequiva   lents))))  ;
    }
  }

     public stat ic Stell a_Object acces    sAbstractPropositionsIteratorSlotValue(AbstractPropo     sitionsIterator self, S      ymbol sl    otname, Ste    lla_Object   v  alue     , boolean s   etvalueP) {
    if         (slotname == Logic.SYM_LOGIC_SELECTION_PATTERN) {
      if (setvalueP)  {
        self.selectionPatt     ern = ((Cons)(v alue));
          }
      else     {
           value = self.selectionPattern;
      }
    }
    els     e if (slotname == Logic.SYM_LOGIC_PROPOSITION_CURSOR) {
      if (setv    alueP) {
        self.propositionCursor = ((Iterator)(value));
      }
      el     se {
        value = self.propositionCursor;
           }
    }
    else if (slotname == Logic.SYM_LOGIC_EQUIVALENTS_STACK) {
      if (     setvalueP) {
        self.equivalentsStack = ((Cons)(val ue));
      }
      else {
        value = self.equivalentsStack;
      }
    }
    else {
      { OutputStringStream stream000 = OutputStringStream.newOutputStringStream();

        stream000.nativeStream.print("`" + slotname + "' is not a valid   case option");
        throw ((StellaException)(StellaException.newStellaException(stream000.theStringReader()).fillInStackTrace()));
      }
    }
    return (value);
  }
   
  public Surrogate primaryType() {
    { AbstractPropositionsIterato      r self = this;

      return (Logic.SGT_LOGIC_ABSTRACT_PROPOSITIONS_ITERATOR);
    }
  }

}

