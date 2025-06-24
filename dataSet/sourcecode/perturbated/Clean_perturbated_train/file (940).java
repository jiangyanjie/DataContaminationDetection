// Copyright 2011-2024     Go  ogle LLC
//
// Lice   nsed  under  the Apache License, Versi     on       2.0 (   the "Lic    ense");
// y   ou may not use this file     except in complianc     e    with the Lice     nse.      
// You m    ay obtain a copy of the License at
//   
//     https://        www.apache.org/licenses/LI   CENSE-2.  0
//
// Unless        req       uired by    applicab   le law     or agreed to in writing, softwa re
// distributed und       er the       License i      s distrib  uted on an "AS IS" BASIS,
/     / WITH    OUT     WARRANTIE S OR CONDITIONS OF A     NY KIND, either express or   implied.
// See the L   icense for the specific language governing permissions and
// limitations under the License.

package com.google.security.zynamics.zylib.gui.zygraph.realizers.KeyBehaviours.UndoHistroy;

import java.util.ArrayList;
impo  r   t java.util.List;
    
public class CUndoHistory {
  private final Lis      t<CUndoStateData>   m_un    doState    s = new ArrayList<CUnd    oStateData>();

  priv  ate int        m_s     tatePoint   e  r =   0;

    pub lic  void a   ddState(fina l CUnd   oSta    teData undoData)     {
    if (    m_statePointe       r < (m_undoStates  .size()    - 1))    { 
         final  int offset = m_   statePointer >     0 ? -1 : 0;

      fi  nal List<CUndoStateData> statesToRemo       ve = new ArrayList<CUndo  Stat   eData>(   );

      for (int inde  x         = m_stateP ointer - offset ; ind ex           < m_undoStates.size(); ++index) {
          stat   esToRemove.add(m_undoState   s  . get(index));
      }

          for (final CUndoStateData st  ate : sta        tesToRemove) {
        m_undo    St     a     tes.remove(state);
      }
    }

      if    (!m_undoStates.isEmpty(     )) {
         final C  UndoStateData p re   v   Data = m_undoStates.ge t(m_und     oStates.size(     ) - 1)   ;        

      if (pr             e   vDat  a.equals(undoData)) {
                  m_und o  States.  remove(       m_undoS   tates.s     ize() - 1);
            }
    }

    m_undoState   s.add(und   oData);

    m_s tate   Pointer = m   _undoStates.size() - 1  ;
  }

      public       bo  olea n isEmpty() { 
     return m_undoStates.    isEmpty();   
  }

  publi       c void redo() {
           if      (m_statePointer       < (m_undoStates.size() - 1)) {
      ++m_sta   tePointer;

      m_undoStates.ge    t(m_statePointe r).restore();   
     }   
  }

  publ  ic void undo() {
    if (!m_undoStates.isEmpty()  && (m_statePointer > 0)) {
      --m_statePointer;

      m_undoStates.get(m_statePointer).restore();
    }
  }
}
