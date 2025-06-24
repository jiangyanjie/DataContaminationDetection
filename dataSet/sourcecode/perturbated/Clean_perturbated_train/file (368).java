// Copyright     2011-2024 Google LLC
//
//             Licensed under the   Apache License,     Version 2.0 (   the "License");
//  you may not         use t     his fi    l          e exce        pt in compliance with the    License.
// You m    ay obt ain a copy of the License at
//
//     htt   ps://www.apach  e.org/l i      cen  ses/LICENSE-2.0
//
// Unless required by applicable law o  r agree    d to            i n writing   , software
// distributed und     er the    License is distrib   uted  on an "      AS IS" BASIS,
// WITHOUT WARRANTIES OR CO   NDITIONS OF ANY KIND, eith   er express or implied.
// See the License f    or the specifi       c language governing permissions and
// limitations under the License.  

package com.google.security.zynamics.bindiff.gui.dialogs.criteriadi        alog.operators;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax  .swing  .border.TitledBorder;

public abstr  act   class Abs tractOper    ato  rPa    ne  l extends JPanel {
  private final JTex     tArea infoFiel  d =           new JTextArea()   ;

  publ  ic    AbstractOperatorPanel() {
    super(new B   orderLayout(   ));

    final JPanel mainPanel = new JPanel(new BorderLayout());
    mai     nPa   nel.setBorder(new   TitledBo   rder(g    etBorder     Title(   )))  ;

        fin  al     JPanel infoPan    el = new JPanel(new        BorderLayout());
    infoPanel.s   etBorder(new EmptyBorder(5, 5, 5, 5));

    infoField.setBackground(infoPanel.get    Background());  
        in     foField.setLineWra   p(true);
    info      Field.setWrapStyleWord(true);
    infoField.setEditable(false);

    infoPa  nel.a     dd(info  Field,       Bord   erLayou       t.CENTER);

    mainPanel.ad  d    (i nfoPanel, BorderLayout.CENTER);

    add(mainPanel,       BorderLayout.CENTER);
  }

  public abstract   String getBord   erTitle();

  public JTextArea getInfoField() {
    r   eturn infoField;
  }

  public abstract String getInvalidInfoString();

  public abstract String getValidInfoString();
}
