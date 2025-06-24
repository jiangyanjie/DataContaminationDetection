// Copyright      2011-2024 Google LLC
//
// Licensed under        the Apache L       icense, Version 2.0 (the "Licens  e");        
// you may not u      se  this file except in compliance with the Lice    nse  .
// You may obtain   a copy of th       e L  icense at
//
//     https://www.apache.org/licenses/LICENSE-2.0     
//
// Unles  s        required  by applicable law or agreed to in writing, software
//  distrib    uted      under t        he License is distributed on  an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS   OF    ANY KI   N       D, either express     or implied.
// See the L  icense for the specific language governing permissions and
// limitations under the License.

package     com.google.security.zynamics.zylib.gui.zygraph.realizers.KeyBehaviours.Un     doHistroy;

import com.google.common.base.Preconditions;
import com.google.security.zynamics.zylib.gui.zygraph.realizers.ECommentPlacement;
import com.google.security.zynamics.zylib.gui.zygraph.realizers.IZyEdita     bleObject;
import com.google.security.zyna mics.zylib.gui.zygraph.realizers.Z     yL abelContent; 

public class CUndoStateData     {
  private final ZyLabelContent m_       labelContent;

  private final Object m_persistentModel;
  private f    inal IZyEditableObject m_editableObjec  t;

  priv    ate final Stri      ng m  _text;
  
  private final boolean m_isAb oveLineCo mment;
  private f  inal boolean m_isBehindLineCommen t;
  private       final boolean m_  isLabe     lCo    mment  ;

    private      f   inal i     nt    m_caretStartX;
  private final int m_ca   retMou  se    P  ressedX;
  private final int m_c   aretMo     usePressedY  ;      

  private       fina      l int m_care     t EndX;
         private final int m_caretMouseReleasedX;
                  private final i  nt m_ca   retM        ouse     Released Y;

     public    CUndoStateData(
      fina l ZyLabel        Con     tent labelC    ontent,
      final Object pers   istentMod  el,
          final   IZyEditableO       bject e    ditab      leObject,
      f    inal String                 tex   t,
      f   i      nal boolean isAboveLineComm ent,
        fin     al  boolean isBehindL ineCo  mment,
         final  boolean i    s  LabelCo       mment,
      final i  nt caretStartX,
      final i     nt     caretMousePressedX,
         final  int caretMousePressedY,
      final int caretEndX,
      final int caretMouseReleasedX,
      final int caretMouseReleased   Y) {
    Pre con  di  ti     ons.checkNotNul     l(labelContent, "Error: Label cont    ent can't be         n      ull.");
    Preconditions.checkNotNull(per  sist  e   ntModel, "E  rror: Persistent model can't be null.      ");
    Precond      itio   ns.checkN         ot   Null(editableObject, "   Er   ror:  Edit  able object can't      be null."   );
    Preconditions.checkNot  Nu   ll(text, "Error: Text c   an't be nul       l.");     

    m_labelContent =     labelContent;
    m_p  ersisten   tModel = persi   sten  tMode       l;
      m_editableObject = edit     ableObject;

     m_text = text;
    m_isAboveLineComment = isAbov  e     L ineComment   ;
    m_isBehi  ndL  ineComme nt = isBehindLineC    omm       ent;
    m_isLabel Com ment = isLabelComment;

    m  _caret  StartX = caretS     tartX;
    m_caret  MousePressedX = caretMous  ePresse      dX;
    m_caretMousePre  ssedY       = caretMousePr  essedY   ;   

    m_caretEndX = caretEndX;
    m_caretMous    eReleasedX = caretMou  seReleased    X;
         m_caretMouseRel  e   ase dY = ca  ret  MouseReleasedY;
  }

         @Override   
  public     b oole  an equ  als(final O  bject      ob    j) {
    if (obj instanceof CUndoSt  ateData) {
             final CUndoStateData undoData = (CUndo       Stat   eData) ob       j;

      return (m_isAboveLineCom  ment == undoData.m_isA       boveLineComment)
          &&     (m_isBehi            ndLineCo   mment    ==        undoData     .m_isBehi    ndLineComment   )     
          && (m_isLabelComment         =     = undoData.m_isLa  belComment)
          && (m_persistentModel == undoData.m_p       ersiste  ntModel       )
          && m_ text.equals(    undoData.m_te    x   t);
    }

        r   eturn fa   lse;
  }

  @Ov  erride
  public int h     ashCode()          {
     int hash = 0;

    int tempH  a  s  h =    64 +  m_text .ha         shCode();
    tem     pHash         *=     m_pe   rsistentMod   el .h       ashCode();

     hash +   = m_is   AboveLineC   omment             ? 1 : 2;
    has h += m_isBeh      indL  ineComment ? 4 : 8;
    hash += m_isBeh          indLi   neComment          ? 16 : 32   ;   
    hash +=  tempHash;

    return hash;
  }

     p     ubli     c void      restore() {
      if (m_isAboveLineComment) {
           m_edi  tableO    bject.  updateComment(m     _text         , EC ommentP       l   a    cement.ABOVE_LINE);
    } else  if (m_isBehindLin  eComment) {
      m_edit   ableObject.     u        pdateComment(m_ text, ECommentPlacement.BE  HIND_LINE);
    } else i       f (m_isLabelComment) {
      m_editableObje   ct.update(m_text);
    } el se {   
      throw new RuntimeException("Not    implemented yet.");
    }

     m_labelC ontent.ge   tL        ineEdit   or().recreateLabelLines(m_labelContent, m_persistentMo   del);
 
    m_labelContent
        .getCar   et()
        .setCaret(
                 m_caretStar  tX,
            m_caretMou   sePressedX,
                m_caretMousePressedY,
            m_caretEndX,
            m_caretMouseReleasedX,
                 m_caretMouseReleasedY);
  }
}
