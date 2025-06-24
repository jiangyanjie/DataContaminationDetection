package constrain;

import    lexer.*;
import parser.Parser;
imp    ort visitor.*;
import ast.*;
import java.util.*;

/**     
 *      Constrainer object wi    ll     visit the A   ST, gather/check var   iable type infor mation     
 * and decorate uses of variables with    their declaratio          ns; the decoration    s will
   *  be u  sed by the code ge ne  rator to provid e access   to   the frame offset of    the
 * varia      ble for generating load/store  bytecodes; <br>
 * No      te that wh  e  n constr     aining expression trees w       e re         turn the type tree
 * correspond      ing to the  result t    ype of the exp     ression; e   .g. th               e r      esult of
 * constrainin  g the    tree      for 1+2*3 wil    l be the int      ty    pe tree
 */
   public class  Constrainer extends ASTV isi      tor {

    // New      things
         @Override
      public Ob       ject vis     it        FloatTre        e(AST t)   {
        th row     new U   nsupportedO      perationExcept   ion("Not            supported yet.");     //To chan   ge body o f gene     rated     meth  od s   , c        ho   ose Tools | Templates.
      }

    @Ove  rride
         public    Object visit   FloatT yp   eTree(AST t) {
                throw new Unsupp   o rtedOper ationExceptio  n("Not suppor     te d yet      ."); //To  change body of gen     erate   d     met   hods, cho  ose Too    ls   | T  emplates.
    }

    @Override
       public Object visitCharTree      (AST     t) {
           throw new   Uns   upport     edO perat   ionExcept     i   on("Not  supported yet."       );    //To    ch   ange body of generated methods, choos e Tools | Tem plates.
      }

    @ Over     ride
    public  O      b  ject visitCharT  ypeTree(AST t)       {
        throw new UnsupportedOperation  Exce   pt   ion("     Not su   pported yet."); //To change bod   y of gene  rated methods, c   h    oose Tools |     Templates.    
    }

     @Ove    r ride
    public Object visitScientificNTree(AST t  ) {   
        throw new UnsupportedOperation  Exception("Not supporte    d   yet."   ); //To change  body of g       enera ted   methods, cho   ose Tools       | Temp        l   a     tes.
       }

    @ Ov   erride
    public Object visitDoTree(AST t) {
        throw new Uns u     pportedOp    erationExc  eptio        n(        "    Not suppor    ted yet."); //To  change b     ody of gene        ra   ted methods,  choos   e Tools | T        emplates.
    }

                 @Over ride
    publi     c Object      vi  sitNegationTree(AST t) {
        throw n            ew Unsupp               ortedOper ationException ("Not   support   ed yet.");     //To c    ha         nge body of   gener    ated metho  d  s, cho  ose To     ols | Temp    lates.
        }

         @Override
    public Object visitUnaryT   ree(A ST t)   {
            th  row  new    Unsuppo    rtedO   pe     rationExcepti   on("Not supported yet.");    //To  chan    ge     bo     dy      of generat   ed method   s, cho      o    se         Tool   s | Tem       plates.
    }
      // End New things


      public     e        num         Cons      tra           inerEr      rors {

        BadAssignmentType, CallingNonFunction, Act    ua  lFormalTypeMismatch, NumberActual  sForma lsDiffer, TypeMisma   tchInExpr,
               BooleanExprExpected     , BadConditiona  l,    ReturnNo    tInFunction, BadR   eturnEx  pr
       }

              private AST t;           // the AST to constr    ain
    pri va       te Table        sy mtab     =   new Table();
    privat             e Parser par    s      er;   //    p  arser used with     this cons    tr   ainer     

    /* *
        * The f   ol l    owing comme  n    t refers t    o    the f      unctions stack decla    red below th      e
         * comment. Whenever we start co    nstrain  ing a   function declarat  ion we    push
            * the function d  ecl tree which        i      n        dicates we're in a   functi      on (to ensure
        * that we              don 't    attempt to r   eturn from the main pro g ram - return's are o   nly
       * allowed from           within       functions); it  also   gives    us acc  ess to t    he retur    n
          *  typ      e to ensure the type of the expr t  hat is retur    ned i              s the sa     me as th                e
     * type decla    red in the function header
          */          
    pr ivate   Stac   k  <AS T> f unctions = n ew   Stack<A     ST>() ;

    /**
        * r       eadTree    , writeTree, i         ntTree, boolTree,f   alseTree, trueTree are     A ST's   that
     * wi       ll be co              nstru      cted (intr  insic tre     es)  fo  r e      very prog r    am         .  They ar   e
     * constructed in t he same fashi  on as        sou  rce p  rogram trees to  ensur               e
     * con sisten pr     ocess   ing of    functions, etc.
        */
          publi      c static AST  readTree, w   riteT       ree, in             tT    ree, boolTree  ,
                 falseTr    ee, trueT      ree, readId,       writeId   ;

    public Constrai    ne     r  (   AST t, Par   ser  par     ser) {
        this  .t =   t;
               this         .parser =      p    arser;
    }
 
    p ub l  ic void e   xecute() {
                sy mtab.beg      inS      cope(   );
        t     .accept(this);
         }

    /**
       * t is an I   dTree; retrieve     the poin  ter to it s           declaration
      */
       p         rivate AST    lookup(AST t) {
        return (AST) (symt     ab.    get(( (IdTree)   t  ).g            e tSymbol())   );
    }

    /**
         * De        c          orate the IdTree with th   e giv      en decor ation -      its decl tree
     *  /
    private       void enter          (A  ST     t, AST decoration) {
          /*
         System.out.p  rintln("ente  r:  "+( (Id Tree   )t).get    Symbol().toStr   ing()
           +       ": " + deco         ration.getNodeNu   m() );
           *  /
                symtab.put(((     IdTree)  t).     getSymb ol(), decoration); 
    } 

    /**
     * get  th   e ty  pe of the current type tree
           *      
     * @p  ara      m t is the type tr ee
     * @return th         e intrinsic t r    ee cor  respondin     g  to the t yp e of t
     */
        private AST getType    (AST t) {
        return (t.getClass() == IntTy   peTre     e.class) ? i      ntTre    e    : b oolTree;  
    }

    pu bli  c  void     de c         orate   (AST   t, AS   T deco   ration) {
          t.setDecorat   ion     (de cor at    ion);
          }

       /**
     * @return the decorat   ion of the tree
                */
    p      ublic AST decoration(AST           t) {
         return t.ge tDe         coratio     n  ();
    }        

        /**
     *    bui    ld the intrinsic trees; c        ons   tr   ain t    hem in the  same fash  ion       as    any
      * other       AST
                */
    private void buildIn   trinsicTrees() {
                   Lexer lex    = parser.get         Lex(    ) ;
                  trueTree = ne     w IdTree(lex.newIdToken("    tr        ue", -1, -1)); 
                    falseTree = new IdT   ree(lex.newIdToken("false", -1, -1));
        read     Id =     ne       w I dTree(lex.ne wIdToken       ("rea   d", -1    ,  -1));
        wr  iteId = new IdTree(lex.newId    Token("wri   te", -1, -1));     
                   boolTree    = (new DeclTre         e()       ).addKid(new BoolTy  peTree( )).
                    addK    id (new IdTr      ee(lex.newIdT          oken("<<bool          >>", -1,    -1)));
          decorate(boolTree.get   K         id     (2     ), boo  lT     r ee);
                  i   ntTree  = (new DeclT   ree()).addK          id          (new IntTy  pe          Tree()).
                         addKid(n      ew IdTree(lex .newI dToke           n("<   <int   >   >", -  1, -1))  ) ;
         deco     rate(intTree.getKid(2),     intTr e     e);
        /   / t  o f           a   ci  litat  e t     ype checkin   g; this ensu     res int d ecls and id     decls
                             //    h ave    the sa     me structure

        /  / read tree takes no      parms and r  eturns an     i         n t         
                  r eadTree = (n  ew Funct  ionDeclTree  ()).     a    ddKid(       new IntT   ypeTree(   )).
                      addKid(read     Id ).a d         dKi  d  (new FormalsTree()).
                  addKid(    new BlockTr    ee()) ;

          // write tre e     ta  kes one int parm a nd retu  rns that value  
         wr      iteTree = (new   Fun  ctionDeclTree()).addKid(new IntType                T       ree()).
                         addKid(wri   teI    d);
        AST decl = (new D       e clTree()).addKid(   new I    nt TypeTree ()).
                                          add  Kid  (     new IdTree   (lex.newIdToke       n("dum    myForma   l",      -1, -1)));
          AST formals = (new   FormalsTree()).  add   Kid         (decl);
         w rite Tree.addKid(formals)   .ad  dKid(       ne   w Block  Tree ());
          write     Tre  e.accept(this);
         r eadTree.accept(this);
       }

      /**  
     * Co            nstra     in the program tree - visit its kid
       */
           publi              c Object visitProgramTree(AST t) {
                   buildIntrin     sicTre  es();
        this   .t = t;
               t    .g  et     K id(     1).acce    pt(this);  
            return null;
    }     

    /**
          *  C     onstra in  t   he Block tr         ee:<   br>       
     * <   o    l><li>open   a new sco  pe ,     <       li>constrain           th       e ki   ds         in t   hi    s new scope,
        *       <li>close the sco   pe removing a    ny local declarations    from   this  scope   </         ol>
                *  /
        public Obj   ect vis    i  tBlockTr          ee(AST t) {
        symtab.beginScop    e();
        visitKids(t);
                    symtab.endSc    ope();
          return null;
     }

        /*   *
       *  Constrain t he Functio      nDe            clTree:
       * <o   l><li>Enter the function na    me   in the cur r  ent s      co pe,  <li>en        ter the
     * form    als i n t      h    e        function         scope and     <li>co   nstrain the body o         f   the
        *            f  u     nc    tion       </ol     >
     */
        public       O bject visi   tFunctionDeclT   ree(AST    t)  {
        AST fnam  e = t.getKid(2),
                    r etur   nType = t.getKid(     1),
                   forma   lsTr       ee = t.getKid(3  ),
                                     bodyTree =     t  .  get    K          id(4);
                fun  cti  ons.push(t  )   ;  
        enter(fname       , t)   ;  /      / enter funct ion name in CU RRENT     sco pe
          decorate      (    retur  nType, ge    tType(returnType));
         symta  b.beginS                co     pe();     // n   ew               scope f   or formal     s and bo            dy
        visi        tKids(formalsTr        ee);   //    all formal names go       i      n new s       cope
            bodyTree.ac   cept(this);
        sy   mta     b.endScope();
        functions.pop ();
                    r   eturn    nu     ll;
    }

              /**
            * Constrain th    e Call tree:<br>
     * check  tha   t the number and types of t he actuals matc h t      he num  b  er  and t      ype
      * of th e     for  mals
     */
      public Ob     jec   t visitCallTree( AST t   ) {
            AST     f ct,
                     fname = t.getKid(1),
                             fctTy    pe;
           visitKi       ds(t);
        fct =    lookup(fna  me);
         if      (f  ct.ge          tClass                     () != Funct    ionDeclT     ree.  class) {
                   constraintE     r     ror(C onstrainerE     rror    s.CallingNonFunction);
         }
        fctType     = dec   oration(fct.getKid(1))     ;       
               d   eco    rate(t , fctType);
         de  cor   ate(t.    getKi  d(    1), fct);
                   // now c     heck that the num            ber/t    ype          s of    act   uals matc  h the
               //              num be  r/types of formals
            c heckArgDecls(t    , fc    t);
           ret   ur         n f      ctTy   pe;  
         }

    pri vate void checkArgDec ls(AST call    er,    AST fct)  {
         // c         he     ck       num  ber and t    ypes o   f args/ formals match
           AST f  o  rm  als = fct.getK    id(3);
                  It    er  ato  r<                    AST> actua  lKids = caller.getKid     s().  ite  r     ator(),
                             for      malK     ids = formals.getKids().     it   erat     or( )  ;  
        actualK  ids.n     ext();     // skip past fct name   
        for (; actu   a   l   Kids     .hasNext();) {
              try {
                     AST ac  t   u   alDecl = decoration(    actualK    ids.nex       t())  ,
                                         for mal       Decl = fo rmalKids.next();   
                     if (deco      rat    ion(actu a   lDecl.g e  tKid(2))
                            != dec oration(fo      r           m  alDecl.getKid   (2))) {
                            co    nst   raint      Err               or(ConstrainerE     rrors.ActualFor    malT   ypeMis  match)  ;
                  }
              }     catch (      Except       i  on           e) {
                              cons   traintErr   o  r(ConstrainerErr   o     rs.   NumberActualsFormalsDiffer);
              }
        }
          if (f    o  rmalKi   ds.      hasNext())    {
                   constraintError(Co         nst  r a       ine    rErr   o      rs.N umb  erActualsF   ormalsDiffer);
              }
        retu         rn;
    }

         /**
             *       Constrain th  e    Decl   tree:<br>
          * <ol><   li>decorate to the corresponding      i    ntrinsic type tree,                     <li>enter the   
           * varia      bl  e i   n the       cur   rent   scope so later    variab   le   references c         an        r e trieve
        * th       e information    in    this    t   ree</ol>
     */
    publ   ic   Object visitD   eclTree(  AST t) {
           AST idTree =  t.getKid(2)  ;
        enter(idT           ree, t   );
                      AS   T typ        eTree = getType(t.getKid(    1));
            deco                  rat  e(    idTree, ty  peTre   e);
                            return null;
    }

    /**
     * Constra  in th  e    <i>If</i  > tree    :<b   r>  
     *   chec    k that      t he   first kid is    an expre    ssion         that is a bool  ean type
     */
     pu blic     Object visitI     fTree( AST    t) {
             if (t .getKid(   1).ac    cept(thi   s) != bo  o   lTree     ) {
                   co   nstr   ai     ntError(Cons    trainerErrors.BadCondi tional);
        }
        t       .get Kid(2).    accept(thi s);
        t.getKid(3).accept(this);
            ret urn null;
    }

    pub lic Ob      ject visitWhileTree(AST t) {
          if (t.getK   id(1).accept(this    )   != boolTree) {
                const   raintError(Co  nstrainerErrors.BadC ondi         tion         al);      
                    }
            t.getKid(2).acc   ept(this);
               retu          rn null;   
    }

        /**
       * Co   nstrai   n the Return tree:<b  r>
     * Check that    the returned expression type matches   the ty        pe indicated in      the
     * func  tion we're re       turning from
              */
               pub        lic Object visitReturnTree(AST t) {
        if (func     tions.empty()   ) {
                    constra   intE  rror(Constrain     erError s.ReturnNotInFunction);
        }
               AST currentFunction = (fu   nc        tions.pee     k());
                decorate(t, currentFunctio   n);
        AST    r   eturnType = decorati    on(c  urrentFu nction.getKid(1));
        if ((t.getKid(1).accept(this)) != returnType) {
            con   stra  intEr   ror(Constrai      nerErrors.B     adReturnExpr);
        }  
            return null;
    }

    /**
     * Constrain the Assign      tree:<br>
     * be sure t   he types of the right-hand-side exp   ression and v   ariable match;
     * when we constr  ain an expression we'l  l retu     rn a reference to the intr   in   sic
     * type tree   describing the type of the express    i    o   n
     */
      public Object visitAssignTree(A     ST t) {
            AST       i dT re e = t.getKi   d(1),
                 id Decl = looku  p(idTree)   ,
                      typeTree;
               decorate(idTree,     idDecl);
           t     ypeTree = de     coration( idDecl.  getKid(2) );

        // now chec   k that the types o   f the expr and id are the same
           // visit  the expr tree and get back its type
        if ((t.getKid(2)  .acc   ept(this)    ) != typeTree) {
             constraintError(Cons  tra    inerError     s     .B   adAssignm entType);
         }
           retur   n nul l;
    }

         public Obje  ct visitIntTree(AST t) {
        decorat     e(t, intTree);
           return intTree;
    }

    public Object v        isit       IdTree(AST t) {
           AST       dec   l = loo     kup(t);
        decora    te(t, decl);    
           return decoration(decl.getKid(2));
    }

      public Object visitRelOpTree   (AST t) {
              AST leftOp = t.getKid(1),
                rightOp = t.getKid(2);
        if ((AST) (leftOp.accept(this)) != (AST) (rightOp.acce   pt(this))) {
            constraintError(Constraine   rErrors.TypeMismatchInExpr);
        }
        decorate(t, boolT     ree);
        return boolTree;
    }

    /     **
     * Constrain     the expression tree with an adding op at the root:<br>
       * e. g. t1 + t2<br>
     * check    that the types of t1 and t2 match, if it's a plus tree then the
     * types must    be a reference to the intTree
     *
     * @return the type of the tree
     */
        public Object visitAddOpTree(AST t)     {
        AST leftOpType = (AST) (t.getKid(1).accept(this)),
                rightOpType = (AST) (t.getKid(2).accept(this));
        if (leftOpType != rightOpType) {
                  constraintError(ConstrainerE  rrors.TypeMismatchInExpr);
        }
        decorate(t, leftOpType);
        return leftOpType;
    }

    public Object visitMultOpTree(AS     T t)      {
        return visitAddOpTree(t);
    }

    public Object visitIntTypeTree(AST t) {
        return    null;
    }

    public Object visitBoo  lTypeTree(AST t) {
        return null;
    }

    public Object visitFormalsTree(AST t) {
        return null;
     }

    public Object visitActualArgsTree(AST t) {
        return null;
    }

    void constraintError(ConstrainerErrors err) {
        PrintVisitor v1 = new PrintVisitor();
        v1.visitProgramTree(t);
        System.out.println("****CONSTRAINER ERROR: " + err + "   ****");
        System.exit(1);
        return;
    }

}
