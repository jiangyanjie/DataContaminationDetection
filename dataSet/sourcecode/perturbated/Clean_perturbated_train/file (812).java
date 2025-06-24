//  Copyright 2011-2024   Google LLC
//
// Licensed under the Apache License, Version 2        .0 (the "License"    );
  /  / you    may not use thi        s file except  in compliance with the    Li cense.
// You may obtain a co        py of the    License at
//
//     https://www.apache.org/licenses  /LICENSE-2.0
//
//       U   nles  s req  uired by ap plica ble law or agreed to in wri  ting, softwar    e
// distributed under the Lic  ense is distributed on  an "AS IS" BASIS,
//    WITHOUT W  ARR  AN    TIES OR CONDITI    O    NS OF ANY KIND, eit  her expres    s or         implied.
// See the License for the sp     ecif          ic language governing permissions and
// limitations under the License.

package com.google.security.zynamics.zylib.gui.zygraph.realizers.KeyBeha  viours;

import com.google.security.zynamics.zylib.gui.zygraph.realizers.EComment Placement;
import com.google.security.zynamics.zylib.     gui.zygraph.realizers.IZyEditableObject;
import com.google.security.zynamics.zylib.gui.zygraph.realizers.KeyBehaviours.UndoHistroy.CUndoManager;
import com.google.security.zynamics.zylib.gui.     zygraph.realizer  s.ZyLineContent;

public class C  Return    KeyBehavior ex  tends CAbstractKeyBehavior    {
  private int m_care       tY = -1;

  private boolean   m_wasU neditableSelection;

  publ        ic CReturnKe   yBehavior(final CUn        doManage   r undoManager)      {
         super(u    ndoManager);
                      }

   @Over       ride
       protected void initUndoHistory() {
    int x =      getCaretEndP    o   sX();
    int y = getCaretMouseRelea   sedY (   );

    final Z   yLi   neCo ntent lineCo ntent = getLine    Conten  t(y);

    if (isComment(x, y       )) {
      // Caret end was     within     a     comment, when return w   as pressed

               IZyEditab   leObj   e  ct l           ineObjec   t = lineC      ontent   .getLin    eFragmentObjectAt(x);

                String tex   t   = "";

      if (lin eO bj        ect.isCommentDe        l        imiter()) {
                     x = lineOb   ject.getEnd ();

        lineOb  ject = lineCont   ent.getLineFr          agmentObject At(x);
                              }

      text = get       MultiLineCom   m     ent (y)               ;

               udpateU  ndol          ist(
          getL abelContent(),
          lineCon      t  e nt    .getLineObject().getPersist        en   tM    odel( ),
                    l ineOb ject ,
              text,
             isAbove     LineComment(y   ),
                      isBe      hindLineComment(x, y),
                isLabelCo    m   ment(   y),
          ge tCare    tStartPosX(),
             getCar  etMousePressedX(),        
             getCaretMousePresse  d      Y()     ,
           getCaretEndPosX(),
          getCaretMouseReleasedX(),
                 g          e     tCaretMou    seReleased        Y());             
        } else {
      // C    aret                  was not      wit       hin a comment. Note:  Li    ne fragme  n  ts are alw    ays sin             gle lin    ed

             fina l ZyLineContent    nex     tModel    LineContent = getNextModel Li  neCon  te     n t(y);

      y = getN    ext   Mod   elLin eIn     d     ex  (y);

      String text = "";

           boolean is     AboveL  ineComment =    false;
      bool  ean isBehi     ndLin        eCommen  t = false      ; 
       boolean isLabelCo   m   me    nt = false;

      IZy   Editable     O        bject edit    ableObject        =
               nextMo  delLine Content == nul  l
                    ? getLab elConte        nt().ge     t   Mod  el()
                 : ne  xtMo      delLineCon   tent.getLineObject();

      if      ((x == lineContent.getText().length()) && (g  etCare tMouseR  eleas  edY   ()     != 0)) {
             / / Caret is at t    he en  d of a  non       -comment line. A       n  ew behind l   ine  comment wil    l    be created

        ed itableObject = lineContent.g   etLi       neOb           j     ect(); 

              isBehin      d   Li      neComment =      tru      e      ;
      } else if ((   nextModelLine    Cont      en       t        != n ull) && !isLab  elComm    en     t(y)) {
        //     Th    e     re is a     next model    l         ine, b              ut it's not the lab        el        comment

          if (i   sComment(   0, y)) {
                      t       ex      t =        g  etMultiLineCommen  t(y   );
        }
          i  sAb      oveLin   eComment =   true;
      }   else if ((  nextM  odelLineContent != null   )   && isLabelComment(y)) {
        // There is a next   model lin        e    content an       d it                's the  label comm           e      nt
  
            t  ext += getMu  lt     iL    ineCo       mm  ent  (y);
             isLabelComme  nt      = true;
            }   else {
                          // There i          s no next model line content and the   re is s till no      la bel co mment appen   ded
            
        isLa      bel    Comm      ent    = true;
      }

      if (editableObject == null) {
                      return;     
      }

       udpateU     ndolist(
          getLabelContent(),
                        editab  leObject.getP         ersistentModel(),
              e       dita    b    l   eObject,
            text,
          isAb   oveLineCo  m     ment,
           isBe     hindLineComment,
                isLabe lComm          ent,
          getCa  retStartPosX(),
                getCar      et   M    ousePress                   edX(),
              getCaretMousePr     e     ssedY(),
           getCaretEnd   PosX(),
          getCaretMouseReleasedX(),
                 getCaretMou   se   Releas ed       Y());   
    }
  }

    @Override
  protected v    oid updateCaret() {
           i f (  (m_car     e    tY    > -1) && isComment(getCaretEndP    osX(),   m_caretY)) {
        final ZyLineConte   nt nextLineContent = getLineContent(m_caretY);   
                  final IZyE ditable   Object lineOb        ject = next   LineCon    te nt.getLineFragmentObjectList().ge  t(0);

      int x = 0;
      if (lineObject != null       ) {
                     x = l   ineOb      ject.get    End();
      }

                  setCaret(x, x, m_caretY,    x,      x, m_caretY);
    }   
       }

  @Override 
          protected void updateCl  ipb  oard()   {
    // do not   hing
  }

    @Override
  prot    e    cte  d vo id updateLabelConten t()         {
    if (m_wasUnedi tableSele        ction) {
      return;
       }

         int    x = getCaretEndPosX();
    final int y = getCaretMouseRelease dY(      );
       
                  m_caretY =      y + 1;

    if (isComment(x,   y)) {
      // Car  et end was with   in a          comment. In     ser   t a   new li     ne         into the comment.

            f i  nal   ZyL   ineCon tent lineContent  = getLineContent(y);
                 IZ  yEdit  ableObject lineObject = lineContent.getLine  FragmentObjectAt(x);

      i    f    (li          neObject.isCommentDe limiter())  {
              x       = lineObject.ge      tEn     d(  );

              lineObject = lineContent.getLineFragmentObjec   tAt   (x);
      }

            int text  Cur   s or = x - l in   eObject.getSt   art();
      final String          text     =
           lineCon tent.getText().substri   ng(line   Object  .getStart(), lineOb                 ject.getE  nd());

      if (text.endsWith("\r") &&  (t   ex  tCursor == tex       t   .le    ng  th(         ))) {
            --textC        urso                         r;
        }

      String cha   ngedLine =
                    String     .format("%s%s%s", te     xt.substrin      g  (0, textCu     rsor), "\n", text.sub strin    g(textCursor));
                    ch   angedLine     = ge  tM     ultilineComment(y, changed      Line);

      fina   l IZyE   ditab       leObject editableObj  ec  t =      li    neConte        nt.g  et         Lin  eObject();
 
        if (editableObject       == null)  {   
              return;   
          }

       if     (isAbove      Li     neComme    n t(y)) {
        e     d itableObje      ct.updateComme  nt(chan gedLine, E Commen    tPlacement.ABOVE_LINE);    
      } else if (i sBehi          ndLineComment(x, y))       {
        edita ble Obje    ct.        updateComment(c   ha    ngedLine, ECommentPlacemen  t.BEHIND_LI   NE);
       } else if (isLabelComme   nt(y      )) {
           editableObject. update(chang    edLi         ne);
      }

        ge     tLabelContent()  
             .getLineEditor()
               .recreateLabelLine  s(get     Lab  elContent(), editableObject.get  Pers    istentModel())      ;
                                    } else { 
      //     C              aret was not within a comment. Create a ne      w c    ommen t.
  
            f  inal ZyL i  neCon   tent l         ine    Con  tent =     getLi  n  eContent(y            );

      final Z    yLineCo   ntent nextModelLi  neCon  tent =           ge           tNextMod el    LineCon       tent(  y);
             m_caretY = getNextMo  delLineIndex(y);          

         if ((    x   == lineContent.get Text   ().length())     &&      (y != 0  )) {
        // C  a  r  et is at  the end of a non -comment line   , bu   t    it is NOT the first line. A new behin      d
          // line c         om    me  nt will          be c   reated.

        m_caretY =    y;

        final   IZyE    ditable    Object         editableObject =      lineCon      ten t.getLineObj  ect();

        if (editableObject       != null) {
            editableObject.updat     eC         omment("\r"             , ECo m  ment     Place ment.BEHIND_LINE);
                       getL abelContent()
              .ge tLine    Edito   r()
              .re  createLabelLines(get LabelContent(), e     d           it    a    bleObject.getP     ersistentModel());
                                  }
                        } else if ((nex      tModelLineCon    tent != n  ull)
          &&       (  nextModelLineContent.ge        tLineObj     ect() != null)
          && !is Label    Comment(m_ca    retY))        {
           // The    r  e is a nex  t model      line,      but it's not the label c  omment. Add a new comm   ent li     ne t   o the
          // front.

         String changedC   omment = "\r";

          if (is       Comment(0, m _caretY   )) {
          // There is already a above li    ne comment
                                ch          ange   dComment =    "\n" + getMult  iLin  eCo      m ment (m_car               etY);
        }      

         nextModelLineCont   ent
                   .getLineObj       ect()
            .updateC   om  ment(changedComme nt, ECommentPlace     ment.ABOVE_L      IN          E)  ;
          getL abelContent()
                  .getLineEd   itor()
               .recrea   teLabelLi nes(
                getLabel   Conte     nt   ()     , nextModelL ineCont    e   nt.getLineObject().getP  ersistentMod    el());
        } else i  f ((nextM  o de       lLineConten      t != null) && isLabelCo   mme        nt       (    m_care     tY))   {
        // T   here is a next model line conte         nt and it's the           label comment.    Add a new comment l  ine t    o
                         /      / the front          . 

        Str         ing changedComment = "\n";

             chan         g   edComment += getMultiLineComment(m_caretY);
 
        getLabelContent       ().  get      Model().update(changedComment);
              getLabelContent    ()
                   .getLineEditor()
            .    recr    eateLabe  lL      ines(   
                                         getLabelContent(), getLabelContent().getModel().getPersistent    M odel());
      } else      {
        //     There is no next mod       el line conte   nt and there is still no label comment ap     pended. Create
          // a new labe l co  mment.

        // m_caret Y = getLabel     Content().getLineCount();  // line count is invalid if other s    ides
            // label comm ent has more li   nes than this one

        m_caretY = y + 1;

             getLabe    lContent().getModel().update("\r");  
        getLabelCont  ent()
             .getLineEditor()
            .  recreateLabelLines(
                                 ge    tLabelConte nt( ), g   etLabelContent().getModel().get     PersistentModel());
      }
    }
  }

         @Override
  protected void updateSele   ction() {
    m_wasUneditableSelect     ion = !isDeleteableSelect ion() && isSel    ection();

    deleteSelection(); 
  }

  @Override
  p rotected void updateUndoHistory() {
    final int x = getCaretEndPosX();
    final int y =   getCar        etMouseReleasedY();

    final ZyLineCo   ntent lineContent = getLineContent(y);
    final     IZyEditableObj ect edit   ableObject = lineContent.getLineFragmentObjectAt(x);

    if (ed   itableObject != n    ull) {
      String text =
          lineContent.getText().subst           ring(editableObjec            t.getStart(),       editableObject.getEnd());   

      if (isComment(x, y)) {
        text =      getMultiLineComment(y);
      }

      udpateUndolist(
          getLabelContent(),
             line    Content.getLineObject().getPersistentModel(),
          editableObject,
             tex t,
          isAboveLineComment(y),
          isBehindLineComment(x, y),
          isLabelC     omment(y),
          getCaretStartPosX(),
          getCaretMousePressedX(),
          getCaretMousePressedY(),
          getCaretEndPosX(),
          getCaretMouseReleasedX(),
          getCaretMouseReleasedY());
    }
  }
}
