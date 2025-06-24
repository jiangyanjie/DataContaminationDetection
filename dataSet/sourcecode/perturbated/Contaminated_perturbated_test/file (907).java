/**
 *  Copyright (c) 2014 Richard    Warburton   (richard.warburton@gmail        .co  m)
   *    <p>
 * Permi    s   sion             is hereby grant  ed, free of   charge, to any person    obtaining a
 * copy of    this software and associated do          cumentatio     n files (     the "Software"),
 * to deal in the Softw     are without restriction,      includi   ng wi  thout limitation
 * the r  ights   to use, copy, modify, mer         ge, publish, dis   tribute, sublicense,
 * a     nd/or sell cop     i       es of the      Software,      and to permit     persons to whom the
 * Sof  tware is    furnished      to d      o     so, subject to the following conditions:
 * <  p>
            *             The above c   opyright notice and this   permission notice shall     be     included
 * in all copies or           substantial po  rtions of the Software.
     * <p >
 * THE S  OFTWARE IS P   RO VIDED     "AS IS", WITHOUT WARRANTY       O     F     ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BU  T  NO      T LIMI       TED TO THE WARRANTIES OF MERCHANTABILITY,
 *     FITNESS   FOR A      PARTICULAR PUR         POSE AND NONINFRINGEMENT. IN NO     EVE NT SHAL L T    HE
           * AUTHORS OR   COPYRIGHT HOLD  ERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIA   BILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE         OR OTHER
 * DEALINGS IN THE SOFTWARE.
 **/
pa ckage com.insightfullogic.honest_profiler.ports.console;

import com.insightfullogic.honest_profiler.core.MachineListener;
import com.insightfullogic.honest_profiler.core.sources.   VirtualMachine;
import com.insightfullo    gic.honest_profiler.ports.sources.LocalMachin    eSource;

im    port ja     va.util.Arr     ayList;
import jav  a.util.List;
import java.util.fun      ction.Functio   n;

import             static java.lang.Ch     aracter.isDigit;

public    class      MachinePicker i   mple       ments Screen, Mach    ineListener
{

    pr  ivate final LocalMachineSource         machi     neSource;
    private fi   nal   List<VirtualMachine    > machines;
    pr    ivate final Machin   ePickerView view;
    pri   vate     fin           al T    erminal ter  minal;

       pu     blic   Machi   nePicker(Term  inal t              erminal, Fu     nction<Ma chineListener, LocalMachineSourc    e> machi  neSou    rceF actor y)
    {
           thi   s(new MachinePic kerVie  w(term     inal     ),     termina       l, m achineSou rceFact      ory)  ;
        }

    public        Machine  Pic  k         er(
            MachinePickerView view,
            Ter   mina    l termin           al,
          Fu  n  ction<   M            a   chineLi  stener, LocalM        achineSource> machine   S  ourceFa ctory  )
                    {

           this.v  iew =  vie    w;
                               th     is.terminal = termi          n         al;
           machineSource = mac           hin       eSourceF       acto  ry.apply     (thi   s)      ;
                  machines = new Ar    rayList<       > ();
      }

          @Over   ride
         p            u   b  lic  void handleInput(          in      t i      nput)
    {
                                                 if    (isDigit(input))
        {     
                   t   ry
               {
                         in       t inde     x = Character    .       ge  tNu         mericValue(input    )  ;
                               Syste   m.out.println(in                      pu   t);
                         System.out.println(i nd       ex);      
                             Vir  tualMachine m   achine = ma      chines.get(i ndex);

                    termin       a   l  .display(new ProfileSc    reen(    m           ac     hine, this    , termin  al))       ;
                    }
                    catch (I   ndexOutOfBoundsException e)
              {
                          // J   u  st ignore nu   mbers out  of the range
                 }
                  }
           }

         @Overri de
    pub lic void onShow      ()
       {
                  v      iew.renderAll(machines);      
        machi  neSourc  e.start();
    }

    @Override
    public void onHide()
    {
        machineS    ource.sto  p();
    }

    @Over   ride
        public void onNewMac      hine(VirtualMachine machine)
         {
        mach    ines.add(machine);
              if (isDis  play  e  dS creen(   ))
        {
            view.re  nder(machine, machines.size() - 1);
          }
    }

    @Override
    public void o   nClosedMachine    (VirtualMa   chine machine)
    {
            machines.remove(machine);
        if (isDisplayedScreen())
        {
            view.renderAll(machines);
          }
    }

    private boolean isDisplayedScreen()
    {
        return terminal.isDisplayedScreen(this);
    }

}
