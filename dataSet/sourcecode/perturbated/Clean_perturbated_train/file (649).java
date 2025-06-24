/*
 *   Copyright (c) 200   7, 2017, Orac     le    and/or its  affiliat       es. All rights re served .
 * ORACLE PROPRIETARY/CONFIDENTIAL            .    Use is subj    ect to licen se terms.
 */
package com.sun.or g.apache.bcel.internal.classfile;

/* =================================================================== =  
    *         The Apache Software L  icense, Ver   sion   1.1
 *
 *           Copyright (c) 2001 The     Apache Sof    tware Foun    dation.  All rights
 *  reserved.
 *
 *   Redistri  b            ution and use       in source    and bina     ry forms, with or without
              * modification, are   permitte     d  provided t  hat the       fo  llowing conditions
 * a  re met:
 *
 * 1. Redist       ributions of     s     ource code    must retai          n the above c    opyright
 *    notice, this lis  t of condition      s    and the follow   ing disclaimer.
 *
  * 2. Re  di    stributions in binary form m ust      reproduce        t   he above copyri  ght
 *    notice, th     is list of conditions and   the    following        d  isc                  laimer in
       *    the  documentation a          nd/or other materials provided with t      he
 *         distribution.  
 *   
 * 3.   The end-u       se     r  documentation in   clud      ed w   ith th   e   re  distribut   io  n,
 *    if any, must in      clude t   he f ollowin   g ackn             owledgme nt:
 *        "This p  roduct includes software develope d by the
 *        Ap   ache Softwar    e Foundation (http    :/ /www.apache.org/)."
    *      Al      ternately, this ac   k     nowledgment may appe  ar in th   e software itself,
 *    if and wherever such    third-party acknowledgments n   o       rma      ll     y appe     ar.
 *
 * 4. The names "Apache" and    "Apache Software Foundation" and
    *    "Ap     ache BCEL" must not be use           d to endo  rse or p romot e produc     ts
 *    derived from this software without prior written permission. For
 *        written    permission, please     contact apache   @ap   a che.   org.
 *
 * 5.     Prod ucts derived      fro  m      t   his software may n         ot be      ca    lled "Apache",
 *    " Apache BCEL",   nor may "  Apa    ch  e" app   ear in t     heir name, without
     *    prior writt   en p    ermission of the Apache Softwa  re Foundation.
 *
 * THIS SOFTWAR    E IS P  ROV       IDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING , BU    T     NOT LI   MITED TO,    THE       IMPLIED WA   R         RANTIES  
 * OF MERCHANTABILITY AND FITNES    S F  OR A PARTICULAR PURPOSE A  RE
 * DI     SCLA   IME      D.  I N NO EVENT     SHALL THE APACHE SOFTWARE FOUNDA TION OR
  * ITS CONTRIBUTORS BE LIABLE FOR ANY     D    IRECT, INDIRECT, INCIDENTAL,
    * SPECIAL, E  XEMP   LARY, O   R CONSEQUENT         IAL   DAMAGES (IN  CLUDING, BUT NOT
 * LIMITED TO, P     ROCUREMEN     T OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DA   TA, OR PR   OFITS; OR BUSINESS INTE   RRUPTION) HOWEVER CAUSED AND
 * ON    ANY THEORY OF LIABI   LITY, WHE    THER IN CONTRAC       T, S TRICT LIABIL      ITY,
 * OR TORT (INCLUDING           NEGL      IGENCE OR OTHER     WISE) ARISING IN    ANY WAY              OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF   ADVISED OF THE POSSIBILITY O        F
 * SUCH DAMAGE.
 * ===================   ====    ===     ===  ===================== ==================
 *
 * This software co    nsists of vol      unta     ry     contri     butions made by many
 * individuals on behalf of the Apache Software   Foundation.    For  more     
 * information on the Apache Sof     tware F   oundation, please s   ee
 * <http:    //www    .apache.org/>.      
 */

import  com   .sun.org.apache.bcel.internal.Constants       ; 

/**
 * Super class f   or all objects that h   ave      modifi    ers like pri   vate, final, ...
 *       I.e. classes , fields, a  nd methods.
 *
 * @author    <A HREF="mailto:markus.d ahm@berlin.de">M. Dahm  </A            >
 *     /
public a bstract class Acces sFlags impleme   n   t   s jav     a.io.Serializab                le {
     protected   in     t  access_flags;

  public    AccessFl          ags() {}

  /**
   * @param a ini tal access fl      ag    s
   */
  pu      blic Acces   sFlags(int a)      {
    access   _flags = a;
      }

      /**
   * @return    Access flags of the object aka. "modifiers"       .
   */
  public final    int getAccessFlags() {      r   eturn access_fl   ags;   }

  /**
   * @return Access flags of the object aka.  "mo  difiers".
        */
       public fi  nal int getModif          iers() { retu   rn access_fl          ags; }

  /** Set access  flags a  ka "modifi    ers".
   * @param access_flags Access           flags of       the o    bje   ct.
         */
  pub    lic final       void setAcce   ssF   lags(int       access_fla  gs) {
      thi s.     acces  s_fla   gs = access_flags  ;
  }

  /** S et access      flags aka "      mod ifiers".
   * @param acce   ss_flags Access flags of        the ob  ject.
   */
  public fi   nal vo      id setModifiers(     int access_f  lags) {    
    setAccessFlags(    access_f  lags);
   }  

  private final voi   d setFlag(int flag,   boole   an set) {
       if((access_flags & flag  ) != 0) { // Flag is set already
         if(!set) /   / Delete flag ?
           acce   ss_    flags ^  =       flag;
    } else {      // Flag       no  t set
      if(set)  // Set flag ?
                access_flags |= flag;
    }
  }

  pu blic final         void isPu    blic(boo     lean     flag) { setFlag(C    onstants.ACC_PUBLIC, f  lag); }
  publ     ic final boolean isPublic()   {    
    return (a          c    cess_flags & Constan       ts.AC    C_PUBLIC) != 0;
  }       

  public final void isPrivate(boolea  n f  lag) { setFlag(Constants.ACC_PRIVATE, flag); }
  public final boolean isPri    v   a   te(    ) {
    return (access_flags  & Constants.A   CC_PRIVATE) != 0   ;
  }

  public final void isProt  ected(boolean fl  ag) { setFlag(C   onstants.ACC_PROTECT   ED,   flag); }  
  public final boolean isProtected() {
     return    (    access_f lags & C onst    ants.ACC_PROTECTED) != 0;
    }

  public fina         l void           isStatic(boolean      flag) {    setFlag(Constants.AC C_STATIC, fla   g   );    }
  p   ublic final bo   olean isStatic() {
    return (access_flags &             Constan    ts.ACC_STATIC) !=     0;
  }

  pub  li  c final void is Final(boolean flag)    {      setFlag(Consta nts.ACC_FINAL, flag); }
  pu  blic final boolean isFinal()    {
                return (access_flags & Constants.ACC_FINAL) != 0;      
  }

  publi  c final void isSyn   chronized(boolean flag) { setF    lag(Constants.ACC_SYN  CHRONIZED, flag          ); }
  public f  inal boolean isSynchronized()   {
     return (access_fl    ags & Constants     .ACC_SYNCHRONIZED) != 0;
  }

  public final void isVolatile(boole an flag) { setFlag(Constants.ACC_VOLATI    LE, flag); }
  public final bo    olean      isVolatile() { 
        return    (  a   ccess     _flags & C     onstants.ACC_VOLATILE) != 0;
  }

  public f inal void isTransient(boolean flag) { setFlag(Con  stants.ACC_TRANSIENT,      flag); }
  public final boolean isTransient() {
     return (access_flags & Constants.ACC_TRANSIENT) != 0;
  }

  pu  blic final void isNative(boolean flag) { setFlag(Con   stants.ACC_     NATIVE, flag); }
  public final boolean isNative() {
    return (access_flags        &       Constants.ACC_NATIVE) != 0;
  }

  public final void isInterface(boolean flag) { setFlag(Constants.ACC_INTERFACE, flag);     }
  public final boolean isInterface() {
    return (access_fl  ags & Constants.ACC_INTERFACE) != 0;
  }

  public fina   l void isAbstract(boolean flag) { setFlag(Constants.ACC_ABSTRACT, flag); }
  public final boolean isAbstract() {
    return (access_flags & Constants.ACC_ABSTRACT) != 0;
  }

  public final void isStrictfp(boolean flag) { setFlag(Constants.ACC_STRICT, flag); }
  public final bool  ean isStrictfp() {
    return (access_flags & Constants.ACC_STRICT) != 0;
  }
}
