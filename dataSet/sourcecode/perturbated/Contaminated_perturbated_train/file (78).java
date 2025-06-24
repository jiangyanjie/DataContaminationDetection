/*
     * Copyright (c) 1997, 2     013, Or   acle       and/or       its affil   iates. All r      ights  reserved  .
 * DO NOT ALTER OR REMOVE COPY    RIGHT NOTICES OR                THIS FILE H   EADER.
 *
 * This code is free software; you  can    redistribute it   and/or modify it
          * u   nder the     ter  ms     of the GN     U    General Pub  lic License version 2 o      nly, as
 * published by  the Free Software Fo    undation.                   Oracle designates this
 *      par   ticular fil    e as subject to the "Classpath" exc  ep    ti     on       a s provided
 * by Oracle in the    LI   CENSE fil    e that accompanied this c   ode.
 *
 * This    code is distribut     ed in t      he h  ope that it will be useful, but WIT   HOUT
 *     ANY WARRANTY; without              even the i   mplied warranty of MERCHANTA     BILITY or
 *                FI  TNESS FOR A PARTICULAR PURPOSE.  See       the GNU General    Public License 
     * version 2       for more d   etails (a copy is       incl     uded in the LIC   ENSE file that
 * accompanied this code). 
 *
 * You should have r ec   eived a copy of t    he GNU General Pu  blic License version
 * 2 along with this work; if not, write to the Fre       e Software Foundation,
    * Inc., 51 Franklin S   t, Fif       th Floor,        Bos      ton, MA 02110-1 301 USA.
 *
 * Please contac   t     Oracle,   500     Oracle Parkway, R  edwood Shores  ,       CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.outerthoughts.javadoc.iframed.formats.html;

import co m.sun.j     avadoc.*;
import com.out   erthou    ghts.javad        oc.iframed.formats.html.markup.*;
import com.ou terthoughts.jav   a doc.ifr  amed.internal.too    lkit.*;
impo rt com.outerthoug hts.javad      oc      .iframed.internal.too  lk   it.util   .*    ;

/**
   * Print method and con struct    or info.
 *
 *         <p><b>This is NOT part of    any supporte   d API.
 *  If you write code t hat d  epends on t       his, you do so at yo        ur ow      n ri    s    k.
 *  This  code      and its     internal interfaces are subj  ec         t to ch    ange or
 *  deletion without notice.<     / b>
 *
 * @author Robert Field
   * @aut  hor Atul M Dambalkar
 * @author Bhave  sh     P   atel (Modified   )
 */
public abstract cla      ss AbstractExecutableMemberWri    ter       exten    ds        AbstractMe     mberWriter {

       public   Ab  st       rac    tE   xe  c     utableM      emberWriter(SubWriterHo l     derWriter   writer,    
                              ClassDoc classd        oc) {
                     super(writer, classdoc);
    }

               public Abst ractE  xe   cutableMemb   erW       riter(SubWriterHolderW riter         writer) {
              super(writ    er);
      }

    /**
              *           Ad d the type paramete  rs for the executable member.     
     *
         * @p       aram me   mb     er the  mem   b   er to writ  e     type paramet   ers for.
     * @p aram htmltree    the content tree to   which the parameters wil  l       be added     .
     * @return    the di splay     length required to write this inf ormat      ion.
         */
       protect   e     d void addTypeParameters(ExecutableMemberDoc member, C onte  nt htmltree) {
        Content               ty        pe   Par         ameters    =        getTypeParameters(mem        ber);
        if (! typePara  meters.isEm   pty  ()) {
                htmltree.a  ddCont   ent(typeParam     e   ters);
              htmltree.addCo     n     tent(wr   iter.g      etSpace());
                }
    }   

    /*    *
                *    Get the     typ    e           parameters f  or     the executable     member.
     *
     * @param member the  mem    ber for    which to ge  t    th                 e type pa  rameter   s.
             * @re     turn     the    type parameters.
           */
      pro  tecte     d     Conten  t ge   t  Ty        p        eParameters(ExecutableMemberDoc m   ember)   {
             LinkInfo I mpl   linkIn   f      o = new Li  nkInf    oImpl(configu  rat  io         n,
              Li n        kInfoImpl.Kind.MEMBER_TYPE _PARAMS, mem    ber);
           return writer  .getTypePar    ameterLin   ks(linkInfo);
    }

    /**
     * {@inheritDoc            }
     * /
    pr         otected Content getDeprecatedLink(Pr   ogramElementDoc me mber)   {
                     Executa  b  leMemberDoc emd = (    E       xecuta  bleMember Doc   )membe               r;
          return write    r  .getDo  cLink(LinkInfoImpl.Kind.MEMBER, (MemberDoc) emd  ,
                           em  d.qu  alifiedName    () +        emd.f         l  at  Signat     ure()           );
        }

    /**
     * Add the summary link for the me     mber.
          *   
                  * @  param cont  ext the        id of th      e     context where t   he l   ink will     be p rinted
     *    @   pa     ram cd t   he classDoc that we should lin       k to
             * @par       am membe         r  the    member  be         ing linked to
       * @param tdSumm   ary the   conten      t tree to wh       ic    h the li nk will be added
     */
    prote     cted           voi  d addS    ummaryLink( LinkInfo  Impl.Ki        nd context, Clas    sDoc cd, Pro     gra          m  Elemen          tDoc member,
                  Content tdSumma ry)   {
        E   xecutable      MemberDo   c emd   = (E xecutableMemberDo   c      )member;
        String na   me =   emd           .name();
        Content me   mberLink = HtmlTree  .SP     AN(      HtmlStyle.m   ember  NameLi   nk   ,
                       writer.ge tDoc    L  ink(context, c      d, (MemberDoc) emd,
                       name,    fa    lse));
                      Cont  ent co de = HtmlT       ree.CODE( memberLink);
        addParame  ter    s( emd, false, code, name.length() - 1);
               tdSummary.ad     dContent(    code);
    }

      /**
     * Add the inh   e           rited summary link for the member.
          *    
     * @param cd the       classDoc that we sho       uld link t          o
     * @param m          embe    r th   e m ember being   linked to
     * @param linksTre   e     t   he con   t  en   t tre          e to which t          he li    n    k will       be a dded
     */
    pro  tected void addInheritedS       ummaryLink(Cla   ssDoc  cd,
            Pr  ogra     mElementDoc m    ember, Content link  sTree         )    {
                linksTree.addC      ontent(
                              writer.getDocL     ink(LinkInfoImpl.Kind.MEMBER,     c  d, (MemberD  oc) member,
                        member.name(), fal     s    e))   ;
    }

    /**
     * Add the parameter for    t h             e       e  xecutable member.
            *
     *        @param me          mber the member to write param    eter for.
     * @par   am param the pa      rameter th at nee ds to be writ     ten.
         * @   param isVarArg true if t     his is a lin       k  t   o var arg.
     * @param tree th     e        content tre e to w      hich the   parameter informati             o    n will      be adde       d.
       */
    pro        tected void addP          ara      m(Executable        MemberDoc member,            P          arameter param                     ,
                 boolea         n isVarA rg    , Con  tent t    ree ) {
          if (param.type() != null) {
            C     ontent link =     writer        .getLink(          new  Lin  kInfoI       mpl(
                       configuration, L   inkInf     oIm pl.              Kind.EXEC   UT   ABLE_MEMBER_PARA   M,      
                                             param.       type()).varargs   ( isV       arArg ));   
                tree.addCont   ent(link);
          }
            if(param.   name().       leng th() > 0)   {
                 tree.addContent(writer.getSpace());
                    tree.  addContent       (param.name());
                       }
    }
     
             /*       *
     * Ad     d the     r   eceive r an    notations informat  ion.
            *
       * @pa      ram        m    e     mber   the member to write r eceiv    er annot     ations for.
                 *   @param rc vrTyp  e the r  ece   iver type.
     * @param descList list  of an    no      tation desc     riptio   n  .
              *   @param tree t h    e c          onten    t tr    ee to which  the  information will be added.
                         */
    prot  ected      void addReceiverAnnotations(Execut        abl    eMem      berDoc member, Type rcvrType,
                      Annota  tio    nDesc[  ]           d     esc     Li   st, Conte    n    t tree)       {     
        write    r  .ad   dRecei    v       er                Ann   otat     ionInfo(m    emb            er, descL  ist, tr   ee);
           tre   e.addCo     ntent(writ     e           r    .getSpace( )); 
              tree.addConte                      nt(rcvrType.typeNam e()  );
        LinkInfoI        mpl   linkInfo    = n    ew LinkInfoImpl(co   nfiguration, 
                L inkInfoImpl.        Kind.     CLASS_S   IGNATURE, rc   vrType);
        tree.a   ddCo   n     tent( writer.getTypeParame    terLinks(link Inf        o));
           tree  .   addContent(   writer .ge  tSpace());
        tree.ad    dContent  ("t  h      is")    ;
    }


     /*  *
          * Ad  d all the parameters for  t       he exe   cutable memb           er.
                          *
     * @param        member th  e memb  e   r    to wri      t e    par     amete      r  s for    .
                 *     @param htmltree      the  con tent     tree to whi            c      h the p   arameters   in     formation will be added.
       */
          pro tecte  d void   addPar    ameters(ExecutableMemberDoc member, Co    n    tent htmltree, i   nt i       ndentSize) {
         add              Pa  r   amet          ers(m   ember, tru          e, htmltree, indentSize);
       }

          /*                 *      
     *   Add all the       parameters for     t  h      e exec  u           table       member.    
       *
     * @     p        aram member      the member   to                 wr       ite parameters fo r.
     *           @param i   nc         lud  eAnn  ota    tions t   rue if annotation in          forma tion ne   eds      to be add   ed       .
         *              @par   am htmltree the conten   t tr   ee to which t               he p  a   r    amet ers       informat ion will   b         e added.
     */
      protec   ted voi  d         addParame      ters   (Execu    table  Mem      berD   o      c member,
                            bo     olea n    inclu  deAnnot   ation   s, Co          nte  nt h tml   t ree     , int indentSi           ze) {
         html tree.  addContent   ("(");
                S  t     ri    ng     sep = ""   ;
                   Para  meter[] params  = me        m       be    r.par         ameters();
        String inden         t = ma       ke    Space(indentSize + 1);
        Type rc   vrTyp  e =      member.recei      verT              y     pe(      );
           if (i     nc   ludeAnno   tations    &      & rcv    rT   y      pe ins  tance        of Annota   ted  Type) {
                   Annotatio   nDesc[] descLi     st =         rc vrT      ype.  asAnnota    tedType()      .ann      otat   ions()  ;
              if (     desc     L    ist.length    > 0) {
                                addReceiverAnnotations(m          ember , rcvrType, desc List, h   t      mltree);
                            s   ep        = ",      " + Doclet Constant    s.NL + inden  t;       
                              }
        }
                 int p  aramsta      rt     ;
            for (paramstart =                0; paramstart                   < param      s.  le    ngth; pa          ramsta        rt++) {
                     htmltree.addCo       n tent(sep);
            Paramet er par am        =     params[par   am        start];
             if (!param.name().startsWith("this$")) {
                           i       f (includeAnnotat   ions) {
                                   boole   an   foundAnn    otations =
                                        writ   er.ad  dAnno   tationI     n fo(ind  ent.length(),
                                         me   mber, param, htmltre   e);
                           if (  found   An    not       ations) {
                                                     ht                        mltr   ee.a  d    dCo     n    tent(D        ocletC   onsta      nts.NL);
                                                   htmlt    ree.addCon     te    nt(indent);
                                }
                                                  }
                      ad   d        Param(member, pa        ram,
                      (paramstart == p     ara    ms.length -    1)   && mem ber.i   sVarArgs(), htmltree);
                    break;
              }
                  }

        for (in t i = p    arams   tart + 1;    i < params.length; i ++) {
              htm  ltr  ee.addC  o  ntent(",");
               htm ltr       ee    .addConte   nt(Do   cletConstant  s.NL);
                    htmlt   ree.    addContent(indent  );
                    if (includeAnnotat  io ns)     {
                               boolean found Annotatio ns     =
                                    write  r.addA  nnotationInfo(  in dent.le       ngth()    , member, params[       i],
                             htmltree);
                     if (fou   ndAn    no    tations) {
                                 h    t mltree.addCo        ntent(Docle     tConstants .NL  ) ;
                     html   t   ree. ad        dC ont ent(ind   ent);  
                        }
                 }
            addParam(mem  ber, p    arams[i], (i == par    ams.length - 1) &     &              membe    r.i  s     VarA            rgs(),
                      h   tmltree);
             }
                  htmltr         ee.addCon  tent(")");
       }

     /**
        * Add ex    ce     ptions for the executable member.
      *
     * @param member the me  mber t  o write e       xceptions f  or.
             * @ param htmltree the c          on t   ent tree to whic h    the e   xceptions inf     ormation w  ill be added.  
     */      
    protect           ed void addExcepti   ons  (Executabl  eMem berDoc member, Content    htmltre e, int indentSiz    e) {
           Typ  e[] exceptions = member.thrownEx   cepti  onTypes      ();           
                      if (ex   cep          tions.length > 0) {
              LinkInfoImpl m     embe   rTypeP     aram        = new Link            InfoIm  pl(config   uration,
                       Li  nkInfoI    mpl.Kind   .      MEMBE   R, member );
            String inde   nt = make    Space(indentSize + 1 - 7);
               htmltree.addContent(DocletConstants.NL)   ;
              htmltree.addCon   te       nt(indent);
              html  tree.add   Content    ("throws ");
            indent = make Space(indent   Size + 1);
              C     ontent    link = writer.getLink(new L      in    kInfoImpl(c   onf   iguration,     
                      LinkIn   foImpl.K ind.MEMBER,   excepti    ons[0]));
                htmltree.a      ddCo   nte   nt(lin    k);
            for(int i =   1; i < ex c    eptions.len     gth; i++) {
                htmltree.addCon   tent(",");
                htm     ltre   e.addContent(DocletConstants.NL);
                        htmltree.ad     dContent(indent  );
                        Content    except     ion Link = wri       ter.  getLink(new        LinkInfoI  mpl(
                            configuratio n, LinkInfoIm  pl.    K  ind.MEMBER, exceptions[i]));
                          h  tmltree.addContent(exceptionLink);
            }
        }
    }

           protected ClassDoc implementsMet  hodInIntfac(MethodDoc meth od,
                                                            ClassDoc[] intfacs) {
        for (int i = 0; i <        intfa          cs.length; i++   ) {
             MethodDoc[]  methods = intfacs[i].   methods();
                if (methods.le  ngth > 0) {
                for (        int j = 0; j < me thods.length;   j     ++) {
                    if   (metho   ds[j].name().equals(method.name()) &&
                                      methods[j].signature().equals(method.si   gnature())) {
                        return intfacs[i];
                    }
                }
            }
        }
        return null ;
    }

    /**
     * For backward compatibility, include an anchor using the erasures of the
     * parameters.  NOTE:  We   won't need this method anymore a   fter we fix
        * se  e tags so that they use the type instead of    the erasure.
       *
     * @param emd the ExecutableM emberDoc     to anchor to.
     * @return the 1.4.x style anchor for the Exec   utableMemberDoc.
     */
    protected String getErasureAnchor(ExecutableMemberDoc emd) {
        StringBuilder buf = new StringBuilder(emd.name() + "(");
        Parameter[] params =     emd.parameters();
        boolean foundTypeVar  iable = false;
        for (int i = 0; i < params.length; i++) {
            if (i > 0) {
                buf.append(",");
            }
            Type t = params[i].type();
            foundTypeVariable = foundTypeVariable || t.asTypeVariable() != null;
            buf.append(t.isPrimitive() ?
                t.typeName() : t.asClassDoc().qualifiedName());
            buf.append(t.dimension());
        }
        buf.append(")");
           return foundTypeVariable ? writer.getName(buf.toString()) : null;
    }
}
