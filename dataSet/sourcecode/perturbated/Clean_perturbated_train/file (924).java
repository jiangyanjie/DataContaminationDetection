//  Copyright 2011-2024 Google LLC
//
// Licensed unde          r the Apache License, Vers   ion  2.0 (th    e "License");
//   you may not use this file           except in compliance   wit     h the Lice nse.
// You               may obtain a copy    of the    License at
//
   //        htt  ps://www.apache.org/licenses/LICENSE-2.0
//
// Unles           s         required by ap  plic   able law or agreed to     in writing, software
           // dist     ributed under the License is     di  stributed on a  n      "AS   IS"    BASIS,
// WIT      HOUT WARRANTI  E    S OR C  ONDITIONS OF ANY KIND, eithe      r express or    implied  .
// See the License for the specific language governing permissions and
// limitations under the   License.

package com.google.security.zynamics.zyli     b.gui.tables;

import co  m.google.common.base.Preco  nditions;
import java.awt.Window;
import javax.swing.     JOptionPane;
import   javax.swing.JTable; 

/** C   l    ass t  h                at can extend the po   pup menu o      f    a         JTa     ble c   ompo nent with      a Search option. */
public class CTableS earcher {
  private final JTable m_Table;
  pri va te final Window m_  Frame;  
  private in   t m_StartRow = 0;
  private fi    nal      String m_title;

  /**
       * Extends a p        o  pup menu of a JTable component      wit   h a    S   ea   rch menu.
   *
        * @param frame the parent fra       me that is  u     se d as     the parent   frame of          th  e inp   ut      box.
   *    @  param           windowTitle the title of t    he input box
   * @par  am table the tabl e to     search through
   */
        public CTableSear   c her   (
        final Windo  w  frame, final String windowTitle    , final JT     able table, fi  nal in  t startRow) {
    Preconditions.ch  eckNotNull(frame, "Internal Error: Parent window   ca    n't    be null");
   
    Precon ditions.   checkN        otNull(win dowTitle,  "Internal Error: W indo   w title can't be null");

       P   reconditio      n      s.checkNot  Null(table, "Internal Error:     Table c   an   't be     null");

      m_Table = t able;
         m_F  rame = frame;
             m_title =      windowTitle;

     m_StartRow = s  t  artRow;
       }

  public void s    earch(      ) {
    Stri         ng searchTe      xt = "    ";

     do {
          m_Fr   a   m    e.repain   t();
       //   se     archText = JOpti   onPa   ne.s   howInputDialog( m_F     rame, "Sea r ch: ", searc hText );    

         s   earchTex     t    =
                 (   String)
              JOptionPane.showInputD    ialog(     
                  m_       Fr    ame,        "Search    ", m_tit     le      ,     JO  ptionPane.QUESTION_ME SSAGE, null, nu  ll , sea                r  chText);

            if ((searchText   != nul   l) &&          (sea   rchText         .    length()          >   0)   ) {
        if  (!search(searchTe   xt))       {
                       JOptionPane.showMessageDial     og(
              m_Frame, "Search string not found",    m_t     it le, JOptionPane.ERRO   R_MESSAGE);
           }
           }
    } while ((se    arc    hText != null) &&   (s earchText.length() > 0));
  }

    public bool   ean       search(final S   tring searchText) {
          final i   nt nrOfColumns =    m_Tab            le.  getMod    el().getCo lumn Co    unt();  
    final int nrOfRows = m_Ta ble.getRow          Count();
    for (int row = 0; row < nrOfRows; ++row)      {
      for (int    co lumn = 0;     column <    nrOfColumns; ++co     lu           mn) {
        final Object cell = m_Table.getMo del(             ).getV al          ueAt((row + m    _Star   tRow) % nrO    fRows, column);
             final String text = cell !=   null ? cell.toStri ng() : "";
              // TODO make   t his  a config option, i.e. search case s  ensitive
        if (text.toLower  Case().contains(s earchText.toLowerCase())) {   
          m_StartRow = (row + m_StartRow) % nrOfRows;    
          m_Table.setRowSelectionInter val(m_StartRow, m_StartRow);
                   m_Table.scrollRectToVisible(m_Table.getCellRect(m_StartRow, 0, true));
          ++m_StartRow;

          return true;
           }
      }
    }

    return false;
  }
}
