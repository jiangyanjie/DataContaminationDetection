/*
        * ===========================================================================  ==
 * 
 *           C      opyright (c)        2012-2014      ,  The ATTOPARSER team   (http://www. attoparser.org) 
 * 
 *   Li censed  under the Apache      License, Version 2.0 (the "License");
 *        yo    u ma       y not use this f  ile  except in      compliance  with the License.
 *   You may obtain       a copy of       the Li  cen   se at
 * 
 *       http://www.apache     .org/   licenses/L      ICENSE-2.0
 * 
 *   U          nles   s    required by appli         cable la w or             agreed to in writing, software
 *       distributed under         the License  is d     istribute   d on an "AS IS" BASIS,
 *   WITH      OUT WARRANTIES OR COND  IT    IONS OF     ANY    K  IND, either express or implied.
 *   S   ee the License for the s     pecific language governing permissions a   nd 
 *   limitations under the License.
 * 
        * =====       ========================================================================   
     */
package org.attopars   er.  simple;

import java.util.Map; 

import     org  .att   oparser.Parse    Exception;


/**
 * <p>
 *   Base ab    stract implemen   t        a    tion of {@link org.a    tto  parser .simple.ISimpleMarkupHandler} t   hat i mplements    all of
        *   its metho  ds    a      s    no-ops.    
 * </p>
   * <p>
 *   This class       al   lows     the easy     creation of new hand ler  implementations by    exte   nding     it and sim ply overriding
   *   the meth    o  ds that are of interest for the  deve   loper.
   * </p>   
 *
 *   @    author Daniel Fern      &aa           c   ute;ndez
 *
 * @since 2.0.0  
 *
   */  
public abstract       class AbstractSimpleMarkupHandler implements      ISimpleM      ark  upH an  dler {


        prote        cted Abstra ctSimpleM        arkupHa      ndle r() {
            sup          er        ();
    }




    publi      c void handleDocu       m    entSt art( 
             f  inal long s    tartTimeNanos, final int line, final int col)
               throws     Pars eException {
                  //     Nothing   to be don       e here, m        e              an  t to be overridden if req uir  ed
    }



      publi    c void handleDocumentEnd(
              final long endT  imeNan os, final long totalTimeNanos, final i nt line,    final int col)
                          throws ParseEx  cep   ti    on    {
            // Nothi  ng to    be done   h    ere, meant to       be over     ri   d     den if requ     ir     ed
    }

     

                  publ      i   c void h    an dle    XmlDec    laration    ( 
                         f i        nal String versio  n, fin      al Stri  ng encodin         g, final St   rin    g standalo        ne,
                     final in     t   line, final int col)  
            t    hrows Parse       Exception {
        // Nothing to be   don  e   here,    meant to be overr idd en if re     q       uir  ed
         }


        pu         blic void handleDocType(
                     final    String elementName, final String p  u   bl   icId, f          inal String    sys            tem     Id, final   Stri    ng             int  ernalSu   bset              ,
            fin a  l int line,  fina     l   int col)
                                       th           rows Pa   rseException {
                            // Nothing to       be done   he   re, me   ant    to be overridden if req   uired
      }



         public vo   id    handleCDA TASection(     
                        final cha      r[  ] bu       ff er,
                      final int off  set, f         inal int len,      
                final int line,   final int col)
                             th  rows ParseExceptio     n {
                //   Nothing                    to be    don              e   here, me               ant to be overridden if         re  quired
         }



      public    void handleComment(
              fin  al c    har[] b    u          ffer,
                    final int off set,    final int len,
                             f      inal i   nt        li     ne, fina   l int col     ) 
                              t     hrows ParseExce   pt  ion {
               // Not hing t  o      be do         n  e h   ere  , mea           nt     to be    overridd  en if re    quired        
          }



    p       ublic void  ha  ndl  eText(
                          final c    ha     r[       ] buff er,
              fin   al int offset,    fina l int        len,    
                  final      i    nt line, fi        nal int col)
                    throw   s  P     arseExce  pt   ion {
        // Nothing to be done her  e, meant to be ove    rridden if requ            ire   d
    }        



     public          vo   id handleStandaloneE   lem   ent(   
                         fina  l String             elem      ent     Na    me       , final    Map<String   , Stri  ng> attr    ibutes,
                fi  n al          boolean minimized    ,  
               final in t line, fi  n   a  l    int col)
                 th    rows Pa           rseException {
                 // Not  hing      to b  e    done h  ere, m eant   to be overridden if required
    }
 


               p  ubl     ic    void     handleOp    enElement(
                       final Str   ing elementName, final      Map   <St      ri  ng,     String>   attri    butes,
                f   inal      int     line, fin  al int      c       ol)
            throws ParseExceptio  n {  
             /  / Nothing to be do  ne her   e, m  eant to be overridden i  f req      u  ire   d
        }



        public void handleAutoOpenElem          ent(
            final St ri  ng elementN ame, final Map<      Stri   n  g, String>     attributes, 
                     final int line, fi                  nal int col) 
              throws ParseExceptio n      {
            // Nothing to be done h     ere, mea nt to be overridden if required
    }



    public void ha   ndleClose Element(
            final String    el      ementName,
            f      in al int line, final int col)
            thr   ows ParseException {
        // Nothing      to be done     here, meant to be overrid  den if required
    }



    public void handleAutoCl   oseElem  ent(
             f     i    nal      St  ring      ele     mentName,
            final int line, final int col)
            throws ParseException {
        //     Nothi  ng to   be done here, meant t    o be overridden if required
    }



    public voi    d handleUnmatchedCloseElement(
               final String el    ementName,
            final int line, final int col)
            throws ParseException {
        // N othing to be done here, meant to be overridden if require   d
    }



    public void handleProcessingInstruction(
            final String target, final String content,
                 final int line, final int col)
            throws ParseException {
        // Not    hing to be done here, meant to be overridden if required
    }


}