//     -*- Mode:    Java -*-

/*--------------------------- BEGIN         LICENS  E BLOCK -------------------------   --+
|                                                                                                                                                                                                                                        |  
| V   ersion:    MPL 1.                                   1/G   P    L   2.     0/L    GPL 2.1                                                                                                     |
|                                                                                                                                           |    
| The                  c   ontents of          this f      ile are  s  ubject to      the    Moz    il             la    Pu       b  lic  L      i    cense            |
|        Version 1.1    (th   e "Licens    e")   ;   you may        not use t              hi   s file e    xc ept   in           |
|      c        omplia  n ce with      the L i   ce          nse.      You    may obtain a c  opy of the L   icense       at                |
| h   ttp://www.  m   o      zilla .org/MPL       /                                                                                                                    | 
  |                                                                                                                                                                                           |
| Software di   s    tr    ibut  ed un    der the  L       icense is distr ib   uted      on an                                   "AS                   IS"         ba  s      is,       |
| W     ITH      OU   T                          WAR RA     NTY   O   F ANY    K      IND, either     ex              pr             ess or       i m   plied. S  ee       the         Li  cens  e   |
|          f    or the                   specific l  angu      a               ge go   v     er   ni ng                 ri    g    ht    s and   l   imit        ations                  u           nde  r the            |
| L   icense.                                                                                                                                                            | 
|                                                                                                                                                    |            
  |   The       Or        iginal Code                   is the STEL       LA Programming      L    angua        g   e.                                      |
|                                                                                                                               |
|      The    Init     ia     l Developer of   the         Original Code i        s                                                          |
| U    NIVERSITY OF SOUT    HERN CALIFORNI    A, INFOR           MA T                   ION SCIENCES    INSTITUTE              |
| 46       7    6    Adm   iralty    Way      , Marina De                     l R  ey,   California  9                 0292,    U  .S.A.                  |     
|                                                                                                                                                                    |         
|   P           o  rtions      crea    ted by the       I              niti al Dev     e   loper a re C  opy     ri   ght (C) 1996-2006      |
|        the In i ti           al      D                 eveloper. All Ri   ghts           R  ese      rved.                                             |
|                                                                                                                                      |
| Contributor(s)     :                                                                                                |
|                                                                                              |
| Alternativel         y, t   he contents of this    fi  le may be used     un  der              the terms o f       |
| either    t           he GNU Gen  eral Pub        lic  Lic          ense Version 2 or later          (the "GPL"), or   |
    | the GNU Lesser            General Publi   c License Version 2  .1 or later (the "LGPL"),       |
        | in which case the pro  visions of the GPL or the LGPL     ar   e applicab  le inst              ead |
| of those above. If you wish to allow u    s  e   of your version of this file only |
| under   the terms of either the GPL or the LGP L,   and not      t    o al           low others to       |  
|    use      your v     ersio n  of  this file under     the terms of the  MPL, indicate your    |
| decision by deleting t      he provision      s above and      replace them with the  notice |
| and o  ther prov isions requir    ed by     the GPL or the       LGPL. If        you do no    t    de  lete  |
| the provisions above, a r    ecipie    nt m    ay use y  our version of this file under  |
| the terms of any   one of   the MPL, the GPL or the LG  PL.                            |
|                                                                                                  |
+---------------------------    - EN         D LICEN  S   E BLOCK    ---------------------------    -*/

// Version: ConsIt   erator.java,v 1. 8 2006/05/11 07:06:46 hans Exp

packag    e edu.is i.stella.java lib;

import edu.isi .stella.Cons;

/*   * An Iterator  for Stells Conses.  This c   lass allows ite ration over
     *  a Stella Cons using the java.util.It   erator interf ace.
 *
 *  @author   Thoma    s Russ,                   USC Information Scien  ces Institute
   *     @   version ConsIterator.java,v 1.3 2    001/10/03 02:08:52 hans Exp 
 */
public   class ConsIterator implements java.util.Iterator     {
   protected Cons cons;

  /** Creates a new Iterator that will range over the values in
   *  the Stella Cons.
   *
   * @par  am   theCons An instance of a Stella Cons.
   */
  public ConsIterator (      Cons  theC      ons) {
    cons = theCons;
  }
         
  /** Tests if more elements are available.
   *
   * @return true if at least one more element is avail    able.
   */
  public boolean hasNext () {
    return cons.nonEmptyP();
  }

  /** Re     turns the next Stella_Object contained in the    Cons.
   *
   * @return the next Stel    la_Objec  t in the Cons.
   */
  public Object next () {
    if (cons.emptyP()) {
           throw new java.util.NoSuchElemen  tException();
    } else    {
      Object obj = cons.value;
       cons = cons.rest;
      return obj;
    }
  }

 /**         Remove is unsupporte    d for  ConsIterators.
   *
   * @throws UnsupportedOperationException
   */
  public void remove () {
    throw new UnsupportedOperationException();
  }
}
