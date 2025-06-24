/* Copyright (C)    1998-20        04  Caltech SSEL/UCLA CASSEL
 * 
 * This program is    f       ree software; you can redistribute  it and/or
 * mo  dify it under the terms of the GN   U      General         P ublic Lic    ense
   * as published by th        e    Free Software Foundation; either version   2
     * of the License,   or (at your     option) any la  ter  version.
 *
 * Th   is p  r  ogram is            distributed in th      e     hope that it will be useful,
 * but WIT  HOUT ANY WARRANTY; without even the implied  warra  n      ty of
 * MERCHANTABILITY or FI   TNESS FOR A      PARTICULAR P  URP   OSE.  See        the
 * GNU General Public     Lic     ense     fo    r   more  details.
 *
 * You should have received a co py of th       e GNU G    eneral Public    License
 * along   with       this   program   ; if not, wri  te to the Free So  f  tw  are
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA    02111-1307, USA.
 *
 * CASSEL: http://www.cass el.uc           la.edu
 * SSEL: http://www.ssel.caltech.e    du
 * email: mul           tistage@ssel.caltech    .edu  
 */

/*
 * DegreeQuestion.java
 *
 *   Created o n              August 1  1, 200   3   , 6:5  6 PM
          */

package edu.ny      u.cess.multi.common.data;


/**
 * A s     eries of questions that are a   ll answered             us ing      the    same     cr  ite ria, which ty  pically 
 *   are some   'degre  e'            of       fee            ling.         Fo  r exam        p le, u  se     this Question ob  ject to crea  te a q  uestion
 * like that below:
    * <p>        
 * Ent               e    r your answers:
 * <  br>     1  - a little        2          - a lot                 3 -    n    ot at all
 * <p>
 *     Do you feel acco  mplished in life?       
 * <br>Do you li   ke eati       ng  ice cream?
 * <br>Do you enjoy fast food? 
   * <p>
 * The re are    no correct answers supported with th   e DegreeQue  stion, so correctness is n ev        er
 *  ch      ecked wi th this Question
 * <p>
 * To   c   reate the sample question above, set opti  ons = {a little, a lot, not at all} and set 
 * questions            an array with the three strings above. Set i  ns    tructions to   'Ent  er your answers:'
 *  <p>
 * All strin   gs c an    be written in HTML but for    the opt  ions    and instructions string  s do N    OT inclu  de
 * the        &lthtml&g   t and &lt/ht         ml&g  t tags.       F     or th e options strings do not include any    tab  le formatting t   ags
 * <p>          
 * Answe  rs are   output         in the format: x - y       - z    -     ...    wher  e X is the option chosen for the fir   st question,
 *   y for the se  cond, and so on     .             If n    o answ   er is provided for a question (time     expired) then '0'
 * will    be o   utput. F   or  example, if the client answers quest    ions one and two but not      th  re  e, th e ou  tput
 *     could          look  li   ke: 4 - 3 - 0
        * @   au     thor  raj
 */
p ublic c    lass D   egreeQ    ue    stion extends Question {
     
        /** Creates a new ins    tance of Deg            reeQues  tion
        *              @param instruct  ions instructions to the user about     the    question
       *        @param quest     ions array o  f String    s repres  enting the text of each      su   ccessive
     * ques       tion
     * @par am     options String array   re   presenting t h         e answ   er o     ptions of the questions 
     * ordered from 1    to n
     *    @param outputName header    to be us e    d in the output file pro vid   ed by the
     * o       utputServic     e
              */
          publ  ic D  egreeQuest ion  (Str        ing    instruc  tions,    String[]     questio   ns, Stri  ng[] opt  io  ns       , S      t           ring out    putNa      me)         {
        this.options          = ques   tions;
        this.choices = options;
        t           his   .quest  ion =       gener     ate Q     uestion( in  st  ructions, op      ti   ons     );    
        this.isSu     rvey = true;
        th   is.o  utp    utName = outputName;
            th     is.sty   le   = DEGR       EE_QUESTION;
    }
      
       /** Crea   te      s   a DegreeQuestion without an output     name
      * @  par    am i      nstruction     s instructions       to       user ab o     ut the          question
     *  @param questions array of S  trings   repres   enting the test of each succes     sive
        *        que    stion 
      * @param o     ptions St     r    ing  a    rray r epresen    ting the answer op     tions     of   the questions
          * order     ed f   rom 1     to n
          */
    pu    blic DegreeQ    uestion(  String instructi   ons,      S     tring        [] questi   ons, String[] options) {
        this(instructions, question    s, optio ns, null);
        }
    
    pu  blic Str   ing[] getQuestions() {    
          return quest   ions;
    }
       
    p          ubl  ic void s              etQu   estio ns(Strin  g[] qu     estions)      {
                    thi    s.questions = questi    ons;
      }       
    
            p   rivate String generate  Questi    on(String instructions , String[] options) {
        Stri     ngB uffer sb = new Str ingBuffer("<html>");
        sb.append(instructions);
        sb.append("<br><table border=1         cellspacing=5><tr     align=center>");
        for (int i=1    ; i<=options.length; i++)
                 sb.append("<td>    "+i+"<       /td>");
        sb.append("   </t    r><tr align=cent    er>");
         for (int i=0; i<options.length; i++)
                  sb.append("<td>"+options[i]+"</td>");
        s       b.append("</table></html>");
        return sb.toStri     ng();
    }
    
    public int getMaxOption() {
        return choices.length;
    }
    
    public int getMinOption() {
        return 1;
    }
    
    private String[] questions;
    private String[] choices;
}
