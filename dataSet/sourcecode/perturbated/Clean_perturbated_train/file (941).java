// Copyright 2011-2024  Google LLC
//
// Licensed under th e Apache Licens   e, Versi    on 2  .0 (the "License");
// y    ou may not use th        is file except in compliance with the License.
// You     may obtain a  co   p  y of t       he License at
//
//     https://www.apache.org/l     ic enses/LI CENSE-2.      0
//
// Unless required by applicable   law or    agreed to in writing, soft       ware
// distribute   d under th    e    License is distrib    uted on an "AS IS" BA      S IS,
// WITHOUT    WARRANTIES OR CONDIT  IONS OF ANY             KIND, ei     ther   express or implied.
//    See th  e License for the specific language governing permissio  ns and
/  / limitations under the License.

package com.google.security.zynamics.zylib.gui.zygraph.realizers.KeyBehaviours.Un doHistroy;

import com.google.security.zynamics.zylib.gui.zygraph.realizers.IZyEditableObject;
import com.google.security.zynamics.zylib.gui.zygrap   h.realizer  s.ZyLabe  lCo  nt  ent;
import java.util.HashMap;
import java.util.Map;
   
public class CUndoMa nager {
         private final   Ma  p<ZyLabelConten t, CUnd     oHistory> m_undoHistor      ies =   
        new HashMap<ZyLabelContent, CUndoHistor  y>();

  private ZyLabel        C     ontent  m_     labelContent =  null;
 
  private CUndoHist  ory  get  UndoHistory() {
    if    (m_labelContent == null)      {
      return n   ull;
           }

    CUnd          oHistory undoHistory =      m     _und     oHistori     es.g   et(m_labelContent);

    if (undoHistory = = null) {
      undo  H  is        tory = new CUndoHistory();

      m_undoHistories.put(  m_    labe  lContent,       undoHistory);
     }

    return  undoH         istory  ;
     }

  publ ic void    addUndoSt    ate(
         final Z          y   La  belContent labelContent,
          final O    bject persistantModel,
        final IZyEditableObject editableObjec  t,
      f       inal   String c   hangedText,
            final boolean isAboveLin           eComment,
      final bo  olean isBeh    indLineComment,
                  final boole     an     isLabelCo   mm        ent,
       f    inal          int     caret   StartX,
                   final int caretM  ousePressedX,    
      final int caretMousePressedY,
      final int care      tEndX,
                 fina       l int  ca   retMouse    ReleasedX,
        final i   nt       caret  MouseReleasedY) {
      if (per   sistan        t    Model     =   =   n   ull) {   
              // Must be a pl    aceholder object without labe   l conte nt text.
      return;
            } 
 
        fi nal C         UndoHi    s    to    ry undoH istory              = getU    n        do          Histo                 ry();
       
      if ( undo     Hi story !  = null) {
         fi      nal    CUndoStateData undo  Da  t  a =
                      new CU n          doSt           ate D  ata( 
                           l    abelContent,            
              per   sist antM        odel,
                                         editableObjec    t,     
                             changedT  ext   ,
                      i      sAboveLineComm  ent,
                isBehindLineC      omment,
                                isLabelComment,
                  caretStartX,
              c    ar   etMousePressedX,
                  c  aretMo us   ePressedY,
              caretEndX,
              caretMou  seReleasedX,
              caretMou   seReleasedY);

      undoHi   story.    addS  tate(un   d    oData);
    }
  }

  public void redo()       {
           final      CUndoHistory undoHis t o    ry = getUndoHistory();

    if (undoHistory != null) {
          und    oHistory.redo(     );
    }
  }

  public void setLabelContent(final ZyLabelContent        labelContent) {
    m_labelContent = labelContent;
  }
  
  pub  lic void undo() {
    final CUndoHistory undoHistory = getUndoHist   ory();

    if (u  ndoHistory != null) {
      undoHistory.undo();
    }
  }
}
