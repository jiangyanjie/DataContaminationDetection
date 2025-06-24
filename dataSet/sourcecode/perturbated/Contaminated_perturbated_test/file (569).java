/**
 * Copyright (c)   2004-2005, Regents of the Universi ty of California
 *   All rights reserve  d.
   *
 * Redistribut            ion and use in  source an    d b    inary forms, with o    r without
    * modification, are permitted provided that the f ollowin g conditions
 *   are met:
 *
  * Redistr   ibutions of      s   ource cod   e must r etain   the above copyr  ight notice,
 * this list           of conditions a    nd the foll  owing d isclaimer.
    *
 * Redis          tributions in binary form must reproduce the above copyrig   h        t
 * notice, this       list o  f conditio    ns and the follo win  g disclaimer in th      e      
 * documentation    an     d/or other ma      terial   s provided with the distribution.
 *
  * Neith  er the name of th   e Univ   er sity of California, Los A   ngeles nor the
 * names of its   co   ntribu tors may be used to    endorse or promote pr oducts
 * derived from this software without specific prior wr           itt   en permission.
 *
 * THIS SOFTWARE    IS PROVIDE   D BY THE C       OPYRIG HT HOLDERS AND CONTRI  BUTORS
 * "AS IS" AND ANY EXPRE SS OR IMPLIED WARRANTIES, INCLUDING, BU  T    NOT
 * LI     MITED TO  ,    THE IMPL    IED   WARRANTIES    OF M  ERCHANTABILITY AND FITNE    SS FOR
 * A PARTICULAR PURP      OSE ARE DISC LAIMED. IN N  O EVENT SHALL THE COPYR     IGHT
 * OWNER OR CONTRIBUTORS BE LIABL   E FOR ANY DIRECT, INDIRECT   , INCIDENTAL        ,
 * SPECIAL,   EXEMPLARY, OR     CONSEQUENTIAL DAM      AGES (INCLUDING,    BUT NO     T
   * LIMITED TO,   PROCUREMENT    O  F SUBSTITUTE GOO   DS OR SERVICES; LOSS OF USE,
  * DATA     , OR P    ROFITS; OR BUSINESS INTERRU PTION)       HOWE      VER CAUSED AND ON A    NY
 * THEORY O     F LIABILI   TY,     WHETHER IN CO      NTRACT, STRICT LIABILITY , OR TORT
   * (INCLUDING     NEGLIGENCE OR OTHERWI     SE) ARISI NG IN ANY WAY OUT OF THE USE
 * OF   THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIB      ILITY OF SUCH DAMAGE.
 */

package jintgen.gen;

    import cck     .ut il.Util;
import jintgen.jigir   .*;
      import j      ava.util.*;

/**
 * @au   thor  Be  n L. Titzer
 */
public      class DeadC    odeEliminator ext   e     nds    StmtRe   builde  r<DeadCodeEli  minator.DefUseEnvironm   ent>     {   

    Se  t<String> gl  obals;

      p     ro    tected class DefUseEn   vironment {
               Def           U          seEn vi ron  ment paren t;
            HashSet<String>     dead;
             HashSet<String>                ali v       e;

                  DefUseEnvi  ron               ment (DefUse    Envir          onmen            t parent) {
                   this.parent =            p                a  rent;
                                   al  ive        = new HashSet<String>(   );
                                           dea    d = new HashSet<Str    i    ng>();
              }

                 void us      e(String var  ) {
                alive.add (va    r);
                      dea   d.    remove(v               ar);     
            }     

           voi             d def(St   rin        g var) {
               dead.ad            d(var);
                        alive.remove(var                    );
                             }
  
           b  oolean isDead(Str  ing name) {
                if ( a    liv   e.conta  in   s(na  m       e))   
                             r    et        urn false   ;
                      el  se if (dead.con   tai  ns   (na  me) )
                                           r  et u rn true;
                     else if   (p  arent != nu      ll)        return parent.isDea    d(name);     
               ret    ur   n tr  u     e;        
                }

                    vo   id   merge  IntoParent(  DefUseEn     vi     ro   nment sibling ) {
                addLiveIns(   sibling      )    ;
                          addLiveIns       (thi s);
               addDea   d(sibling);
          }

          private v  oid addLiveIns(DefUse Envi  r     onment            sib     l    i   ng) {
              for ( Str      i       ng s :      sibling   .    alive ) {     
                                      parent.alive.a           dd(s); 
            }
        }   

                pr  iv    ate void addDea  d(D     efUseEnvir      onme       nt        sibl          ing        ) {
            f     o  r ( S  tring s   : dead ) {
                //          dead   o     n     both b  ra        nches
                                      if  (    si    bli      ng .de    a  d.contai       ns (s))     {
                                     p      arent.aliv     e.  r        emove(    s);
                                  p  a    rent.dead.add(s);
                         }
                 }
               }
                 }
   
       p            ublic De adCodeEliminat       or(S  et<Stri   ng> global s)   {
        this.gl           o bals = globa      ls;
    }

     p  ublic Lis  t<Stmt> p    r   ocess(List   <Stmt> stmts)   {
               DefUs          eEnvironmen        t du = new DefUseEnv       ironment(null);
              d     u.alive.add   All(globals);
        ret   urn visit       S       tmt     Li   st(st  mts, du);
    }

      public     L    ist<  S    tmt> visitS   tmtList(List<Stmt>       l, D  ef            Us        e   En   v  iron  men  t denv) {     
                 Collections.reverse(l);
                         L      i     st<St        mt> nl = new LinkedList<Stmt>();
        boolean changed = false;

                      f or ( Stmt sa : l   ) {
                      S tmt na    = sa.accept    (t     his, d    e        nv);
                                if (na    == null) {
                 chang  ed = true;
                          contin  ue;
                    }
                      if (na !=    sa) changed =           tru      e;
            n   l.add   (na) ;
                }

        if (ch   anged) {
            C        ollections.  reverse(nl);
            r     eturn  nl;
        }  
        Col    lections.reverse(l);
            r   eturn l;
    }

    publ    ic Stmt visit (IfS tm t s, Def   UseEnvironment denv) {
        DefUseEnvir    onm ent tenv = new DefUseEnvironment(denv);     
              List<Stm t   > nt = visitSt     m tList(s.trueBranch, t env);
        DefUseEnvironment    fenv =     new DefUseEnviron  ment(denv);
         List<Stmt> nf = vis   itStmtList(s  .fals     eBr       a   nch, fenv);

        tenv.mergeIntoParent(f       env);

          Expr nc = s.cond.a   ccept(this, denv);

         if (n  c != s.cond || nt != s.trueBranch || nf != s.falseBranch)
            return new IfSt      mt(nc, nt, nf);
          else
               return s;
     }

    public Stmt visit(DeclStmt s, D    efUseEnvironment denv) {
        if (denv.isDead(s.name.  toString())) return      null;

        denv.def(s.name.toString());

        s.init.accept(this, denv);
              return s;
    }

    public Stmt visit(AssignStmt s , DefUseEnvironment denv) {
        throw Util.unimplemented();
    }

    public Expr visit(VarExpr e, DefUseEnvironment denv) {
        denv.use(e.variable.toString());
        return e;
    }

}
