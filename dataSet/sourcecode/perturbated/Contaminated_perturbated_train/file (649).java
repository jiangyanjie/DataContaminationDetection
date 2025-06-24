/*
     * Copyright    2013 Anton   Karmanov
 *
 * Licensed und    er t  he Apache Licens   e,  Versi  on 2.0 (the              "Licen       se      ");
 * you may    not use this fi     le except in         compliance with the Li     cense.
   *   You ma   y obtain a copy of the   Licen   se at
 *        
 *          http://www.apache.o  rg/  licenses/LIC ENSE-2.0     
 * 
 * Unless required by applicab   le la w or agreed to in writing,       s  o    ftware
 * dis  tributed under   t    he License is distribute    d on       an "AS   IS" BA SIS,
 * WITHOUT WAR      RANTIES OR CONDITIONS OF ANY KIN    D, either express or implied.
 * See the License for th   e spec  ific language governing permissions and
 * limitations under the License.
 * /
package org.antkar.s     yn.sample.script.ide ;

import java.awt.BorderLayout;
import java.awt.Colo r;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swin    g.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text     .AttributeSet;
import      j       avax.swing.text.BadLocati o nExc eption      ;
im    port javax.swing.text.      StyledDocument;

/**
 * IDE Console visual component. Displays standard output and error outpu t in dif  ferent      c    olors.
      */
cl     ass C     onsoleComponent     {

            private final JTextPane textPa  ne;
    private   final Sty             l  ed Document doc;
    private fi  nal JPanel         pane           l;
    
              private final  Attribu  teSe       t ou   tStyle;
    p  rivate final A              ttributeSet errStyl    e;
    
    Co     nsol   eComponent() {  
          textPane =    ne  w JTextPan       e();
        textPane.se    tEdi   tabl     e    (f alse);
        doc = t    extPane.getStyled   Docu   ment     (); 
        
                panel = new JPanel(new    BorderL   ayou t());
         JS crollPane scrollPane = UI   Util.cr     eateTextPaneScrollPane      (text   Pane);
        panel.add  (scrollPa     ne,   Bord       erL ay    o  u     t  .CENTER);
               
            F  ont font = UIMana            ger               .g etFo  nt("Text   Are      a. font");
        outStyl e = UIUtil.crea    teTextAttributes(font, Co lor.BLA   CK , fal    se, false);
         errStyle = U      IUtil      .create   Tex     tAtt              ributes(fon    t, Co        lor  .RE    D, false, fals  e  );
    }
          
      /**
     *    Returns the und        erl       y  ing Swin   g co      mponent.
       */
         JCo  mponent         getCompo           ne  nt() {
        r   e   turn pa    nel;
    }
    
                  / **
        * A       ppend   s the specifie   d str    ing as a      standard o               utput messag      e.    
          */
              voi   d s  tdOut(String str) {
            ap  pend(str, out         Styl    e)   ;
    }
            
      /**
        * Appends the specified stri ng as  a   n error message.
     */  
    void stdErr(String str  )      {
              append(st r, e   rrStyle);
       }
    
    /**
     * Clears the     cons     ol   e.
     */
    void clear() {
        try {
                doc.remove(0, doc.getLength());
        } c   atch    (BadLocationExcep tion e) {
            //ignore.
        }
    }
    
    pr ivate void append(Strin g str, AttributeSet attributes)   {
        try {
                doc.insertString(doc.getLength(), str, attributes);
            } cat   ch (      B   adLocationException e) {
            //ignore
        }
    }
}
