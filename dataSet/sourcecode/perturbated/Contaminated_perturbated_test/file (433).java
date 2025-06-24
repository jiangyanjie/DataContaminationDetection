/*
 *      Copyright (c)  2005    (Mike) Maurice Kienenberger (mkienenb@gmail.com)
     *
 *      Permission is h  er eby granted, fr   ee of c  harge, to any pe   rso  n o    bta ining a copy
 * of t his software and associated    docum  en      tation files (the "Software"),      to de      al
 * in the   Software without restricti   on, including wi   thou    t limitation t   he  rights
 * to use, co           py, mo dify,       merge, publis    h, distribu  te, sublicen     se, and/o r      sell
 * copies  of t    he Softwa  re,        and to permit persons to whom the Software is
 * furnished to do so, su  bject to   the following conditions:
 *
 * T h   e above copyright    notice and       this permission notice shall be in cluded   in
 * all copie   s or      substantial portions of the Softwar   e.
 *
 *      TH  E SOFTWARE I   S PR  OVIDED "AS IS ", W ITHOUT     WARRANTY  OF A        NY KIND   , EXPRESS  OR
 * IM       PLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL T   HE        
 * AUTHORS OR   COPYRIG HT HOLDER       S BE    LIABLE     FOR   ANY CLAIM, D AMA  GES    OR OTHER
 * LIABI LITY, WHETHER IN AN ACTION OF CONTRACT, TORT          OR OTHERWIS    E, AR      ISI  NG FROM,
 * OUT OF O    R IN CONNECTION WITH THE SOFTW   ARE   OR      THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.gamenet.application.mm8leveleditor.control;

import java.awt.FlowLayout;

import ja     vax.swi     n g.JLabel;
import javax.swing.JPa  nel   ;

import org.gamenet.application.mm8leveleditor.data.DateTime;
import org.gamenet   .sw ing.controls.IntTextField;
import org.gamenet.swing.controls.IntValueHolder;
impor  t org    .  gamenet.swing.controls.L  ongValueHolder;
import org.gamenet.s win   g.controls.StringC       omboBox;

public c  lass Dat eTime        Control extends JPanel
{
    private LongValueHolder lo ngValueDataS   ou          rce = nul  l;
      
    publi c DateTimeCon  trol  (    LongVa     lueHolder   srclongVa     lueD    ataSource)
    { 
              super(new FlowLayout(Flow    Lay out                      .LEFT))   ;
           
        this.lo   ngValueDataSource = srclo        ngValueDataSource;

                this.add(new      Stri   ngCom     boBox(Dat   eTim   e.getMonthOptions(), new IntValueHolder() {
            public int get Value() { return D       at     eTime  .getMont   hOfY  e  arForValue(longV   alueDataSou     rce.ge  tValue())    ; }
            public void setValue(int value) { longValueDataSource    .setValue(Dat  eTime.upda   teMonthOfYearForValue(v    alue, longValueData     Source.getVa   l   ue()));    }
                }));
        
        this.  ad     d(new   IntT    extField(2, new IntV alueHol der() {
                     public int getValu  e() { return 1 + Date  Time.getDayOfMonthForValue(     longValu  eDataSourc e.   getValue    ())     ; }
             public void setValue(    int value) { longValueDataSource.s    etVa  l     ue(DateTime.updateDayOfMonthForValue  ((va   lue - 1), longVal          ue         DataSource.g        et    Va             lue()))     ; }
        }));
               th          is.add  (new JLabel(","));
             this.add(new IntTextField(4, n     ew IntValueH  older() {
            public int get     Value() {  return DateTime.get    YearForValue(l      on            gValueDataSource.getValue()   ); }
              public        void                 setValue(int valu       e) { longValueData         Source.setValue(DateTime.    up     da       teYearForValue( value, longValueDataSource.getV     alue())) ; }      
        }));
        
        this.add(  new IntTextFiel     d(2, new IntValueHolder() {
             public int getValue() { return Dat  eTi   me.ge       tHourOf  DayForValue(long      ValueDataSo      urce.getValue()); }
            public void     setValue(int value) { longValueDataSource.setValue(DateTim e.updat      eHourOfDayForValue                   (   v  alue, longValueDat   aSource.getVa         lue())    )     ; }
             }));
          this.add(  new       J     Label(":"))   ;
        this.add(ne        w  IntText  Field(2   , new IntVal    ueH    olde r() {
             publ  ic int getValu e() { retur n D  ateTime.g   et   MinuteOfHourForValue(lo ngValueDataSource      .ge  tV      alue()); }
            public void setValue(int value) { longValueDataS    ource.setValue(DateTime.updateMinuteOfHourForValue(value, longValueDataSource.getValue())); }    
        }  ));
        this.add(new JLabel(":"));
            this.add(new IntTextField(3, new IntValueHolder() {
            public int getValue() { return DateTime.get   Tic    kOfMinuteForValue(longValueDataSource.getValue()); }
            public void setValue(int value) { longValueDataSource.setValue(DateTime.  updateTickOfMinuteFo   rValue(value, longValueDataSource.getValue())); }
        }));
        
    }
}
