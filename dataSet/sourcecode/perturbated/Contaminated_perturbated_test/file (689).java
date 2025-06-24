/* -*-       Mode: java;     tab-width: 8; indent-tabs-m   ode:      nil; c-basic-offse    t:   4 -*-
      *
 * ***** BEGIN LIC     ENSE BLOC   K *****
 * Ve     rsion: MPL 1.1/GPL 2.0
  *
 * The conten  ts of thi s file are sub ject to        the Mozilla    Public Licen      se            Version
 * 1.1 (         the "License"); you ma    y  not use       this file except   in compliance with
 * the Licens e. You may obtai n a copy   of th      e L  icense at
 * http://www.mozilla.org/M     PL/
       *
 * Software di       strib       uted und    e  r the License is distributed on an "AS   IS" basis,
    * WIT   HOUT WAR  RANTY OF ANY KIND, either exp  ress or implied. See the L      icense
 * fo  r the    specific language   governin    g rights     and limitat ions under  t     he
  *    License.      
 *
 * The      Origina      l Code is Rhino code,     rel            eased
 * May      6, 19  99.
 *
 * The In   itial   Developer of the    Or   iginal Code is
   * Netscape Communications Corpora  tion.
 * Portions cre               ated by the Initi al Developer ar e Co   pyright (C) 19     97-1999
 * the Initial Develo p e     r. All Rights Rese        rved.
 *
 * Contr    ibutor(s):
 *    N  or ris Boyd
     *
 * Alter    natively, the    co    ntents   of this     file    may be used un    der the term    s o f
 * the GN   U    Gen     eral Pu b           lic License Version     2 or later (the "GPL"),       in w  hich
 * case th e provisi    ons of the GPL    are a  ppl  i    cable in stead        of thos       e abo  ve. If
    *       you wish to allow use of your  versio  n of this fi   le on      ly under the terms of
 * the GPL and   not to allo         w     othe    rs  to  u       se your version of this file  under th   e
    *   MPL, indicate y  our d  ecision    by deleting the provisions abov  e and re        placi    ng
 * them with the notice and other provisions required by      the GPL. If you do
 * not delete the p    r       ovisions ab   ove, a recipient may use your version of   this
 * file   under        either the MPL   or the GPL.
 *
 * * **** END L    IC   ENSE BLOCK ***** */

package or    g.mozill   a.javascri     pt;

/**
    * This is the default e     rror re  porter for JavaScript.     
    *
 * @author Norris Boyd
 */
cla   ss   DefaultErrorR  e           porter impleme  nts Er      rorReporter
{
             static fi nal DefaultEr   rorRe          p   orte  r in     sta   nce = new DefaultErr     orRepo   rter();

      private   boolean f   orEval;
    private ErrorRep      o  rt   er     chain  edR  e       porter;

    private DefaultError   Reporter() { }

    static E        rrorReporter forEval(ErrorRepor  ter repor  ter)
         {
                    DefaultErrorReporter         r =      new DefaultErr  o    rRe     porter()   ;                         
                      r    .forEval    = true   ;         
        r.chain      edR   ep orter =     re  port      er;  
                r    e  t  u rn    r;
    }

    public voi d warning(Str      in g mes   sage, String    sour ceURI         , int     line,
                                             String   line  Tex   t, int   l  ineOffset)
    {   
                   if    (ch     ai  n  edRe   porter    !=   null   ) {
                 chaine dReporte  r  .warning(
                  message ,      source    URI,   line,                       lineText     , lineOffs  et);
        } el          se {
             // Do nothing
                         }
    }
        
           public void erro   r(String   m   e            s       sage,   Stri ng sourceURI, int lin  e,
                                                               S  tring lineTex    t, i   nt       lin     eOff      set)
        {
              if (forEva   l        ) {
            // As sume error m     ess age str   ings                        that start          with      "TypeEr ror: "
                                   // sho   uld     becom     e TypeErr    or excep ti         ons. A             bi    t o       f a   hac   k   ,  but  we
                               // don't want to change the E       rro   rRepor       te     r        interfa    ce   .
             S  tring e      r  ror    =      "Sy         n  taxE   rror";
                              final String TYPE     _ERROR_    NAM     E   = "Typ      eError";
                                   final St  rin  g DELIMETE          R = ": ";
            final St   ring prefix       = TYPE_ERROR_NAME + DELIM ETER;
                if (me           ss  age . st artsWith(prefix  ))      {
                                      error = TYPE_E   RRO  R_N    AME;
                                 m    essage =         message.sub  string(prefix.   length());
                   }
                              throw ScriptRunt       ime.co   nstruct     Error(error, m       essage   , sourceURI, 
                                                               l      ine,       lin  eText, lineOffset);
        }   
           if (chain      edReporter    != nu     ll   ) {
            chainedRepor          ter.err   or(
                             message, s    ourceUR I, line, li neText,     lineOffset);
          } else {
                   throw r  unti    meE    rr   or(
                     mess age,  sourceURI, line, lineT  ex    t, lin         eOffset);
        }
       }

    public Eval     uatorExce   ption runtimeErro    r(String message, String sourceURI,
                                                              int line, Strin      g lineText,
                                           int lineOffset)
    {
        if (cha      inedReporter != null) {
            return chainedReporter.runtimeError(
                  message, sou   rceURI, line, lineText, lineOffset);
        } else {
            return new EvaluatorException(
                message, sour    ceURI, line, lineText, lineOffset);
        }
    }
}
