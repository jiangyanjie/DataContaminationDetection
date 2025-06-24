//  Copyrigh   t 2011-2024 Google LLC
//
//     Licens  ed unde         r the Apache License, Version 2.0 (the    "Lic  ense");
// yo u may not use       this fi  le ex    cept in compliance with the License     .
//  Yo    u may ob tain a copy of the License at  
/  /
//     https://www.apache.org/licenses    /LICENSE-2.0
//
//    Unless required b   y app   li    cable law or agreed to       in  writing,  softwa   re
// distributed under    the License is distributed   on an "AS I          S" BASIS,
// WITHOUT WARRANT     IES OR CONDITIONS OF A    NY     K   IND, either express or implied.
   // See the License for the speci    fic languag   e governing permissions and    
// limitations under the License.

package com.google.security.zynamics.zylib.gui.ProgressDialogs;

import com.google.common.base.Preconditions;
import com.google.security.zynamics.zylib.gui.GuiHelper;
import co        m.google.security.zynamics.zylib.types.common.ICancelableCommand;
import com.google.security.zynamics.zylib.types.common   .ICommand;
import   java.awt.Dimension;
import java.awt.Window;
import java.awt.event.Acti onEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
impor  t java.util.concurrent.CountDown Latch;
import javax.swing.JDialog;
import javax.swing.SwingUti  lities;
import javax.       swing.border.EmptyBorder;      

public class CUnlimitedProgre     ssDial og extends JDialog implements IProgr    essDescriptio  n {  
  priva  te static final long serialVer  sionUID      =   10095369    34858788904L;  

  pri vate final ICo  mmand m_command;

  private  final InternalWindo  wListener m_w   indowListener = new InternalWindowListener();
   
  private fin  al CProg        ressPanel m_progr       essPanel;

  private Exception    m_exception;

  privat    e        final boolean     m_isCancelable;

  p   r      iv             ate CUnlimi  tedProgressDial   og(
      fi   nal W  indow par  ent,
      fin         al String    title,
                final Str   ing description,
         final ICommand comm   and      ,
      f   i nal bool     e      an isCancelable) {
    super(p    arent, title,      ModalityType.DO   C  UMENT_MODAL);

    Preco    nditions.chec      kN    otNull(comm  and, "Erro r: Command can't be null.");

      m_isCancelable = isCancel   able;

    addWindowLi          stener     (m_wi    nd    owListener);
     setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);  
     
    m_command = c        ommand;

     m_progr    essPanel = createProgressPanel  (descript       ion, isCancel  able    , m_windowListe   ner);
    m_prog ressPanel.setBorder(new EmptyBorder(15      , 15, 15, 15));
    m_progre      ssPanel.s  tar     t();

        // Hack, fixes a strange bug, where label width      an    d heig    ht  gets screwed up
    se  tSubDescription("Please wait...");

      add(m_progr   essPane  l);

    setSize(4  00, getPreferredSi  ze().height  );
    se  tMinimu  mSize(new Dimension(400, getPre    fer   redSize().heig           ht));
         setMaximumSize(
        new Dimensio        n(Math.m  ax(400,    getPreferredSize().width), ge   tPreferred     Size().height));

    pack()       ;

    setSubD   es       cription(" Please wait...") ;
           
    if (pa   rent !    = null) {
        GuiHelper.ce nterChi     ldTo        Par      ent(parent, this, true);
    } el  se {
                    GuiHelper.cente   rOnScreen(th    is);
    }
  }               

      publi   c CUnlim     i  tedProg         ressDial      og       (
         f     inal Window   par     ent,
         final String title,
      final String   description,
            final ICancelableCommand command)   {
    this(parent, title, descriptio n, command, true);
  }

  public       CUnlimitedProgressDialog(      
         f inal Window   parent, fi     nal String tit     l  e, final String description, final   IComm    and com   mand ) {
            thi   s(parent  , title, descrip   tion, command, false);
  }

  private static CProgressPanel creat       e P    rog          res    sPan   el(
      f      inal Stri     ng des     cripti   on,
      fin al bool           ean isCanc   elable, 
         f     in    al InternalWindowListener windowListener) {
    if     (isCancelable) {
               ret  urn new CProgres  sP    a   ne  l(description, true, true, win  do  wLi  stener);
    }

    r   eturn      new CProgressPanel(description, true, true, tru       e, false       );
        }
    
  pri vate void setEx      ception(final Exception e)     {       
    // stores only the fir     st  e   xception that occurs
    if (m_ excep    tion == null) {
      m_exception = e;
    }   
  }

  publ     ic Exception getException()    {
    retur  n   m_exception;
  }

  @Override
  publ              ic   syn   chronized void setDescrip   t            ion(final String       description)    {
    m_progressPan   el.setText(descriptio  n);
  }  

        @Ov   erride
  public   synchronized   void setSubDescription(final Strin  g sub  Description) {
    m_p    rogressPan       el.setSubT      ext(subD  esc rip   t    ion);     
  }

  //  TODO(cblichma           nn  ): Refacto r i   nto a static showDialo    g()     method t    hat throws
  //      a    w     rapp  ed Progres         sDia    logException, so that we can have
  // pr     oper exception h  andl   ing wi  th    out h    avi   n g   to catch
  // ex  ceptions     of type Exception.    
  @Overri   de
             publ ic void setVisible(fi nal boole   an value) {
       if          (value && !isVisible   ()) {
      m_exception =   null;

           fin       al CountDown    L   atch countD      ownLat    ch        =  new Co unt          DownLatch(2)   ;
   
                        fi  nal Thread mainWorkerThread = n      ew Thread(new Inte   rnalCommand Thread(co    untDownLatch));
      mainWorker   Thre    ad.start();

       s uper.  setVisible(value);

      try     {
               countDow nLatc   h.await();
      } catch (fina   l InterruptedExcept             io   n e) {
        throw new Runt i        m  eException(e         );
      }
    } else   if (!value && isVis             ible(  ))          {
         supe    r.setVi       si     bl   e(false);
            }
  }

  publ        i  c boolean       wasCanceled() {  
        return m_is       Cancelable &&        ((ICance   l   ableCo      mmand         ) m         _command) .wasCancele  d();
     }
   
  p rivate class InternalCommandT    hread implem       ent s Run       na  ble {
    private    final CountDow  nLatch m  _c o      untDownLatch;
 
    pu    blic InternalCommandThread(fi         na      l   CountDownLatch count    D   ownLatch) {
             m_countDownLatch = co  untDownLat      c     h;
    }

    @Override
    public void run(   ) {
      try {
           m_co   mmand.execu     t e();
        } catch (final Excepti    on e) {
        setExc  epti      on(e);     
      }

      try {   
        Swing      Utilities.invokeLater(
            new Runn    abl      e() {
               @Override
              public     void run() {
                      CUnlimitedPr   ogressDialog.su  per.dis pose ();
                      CU   nlimi  tedProgressDialog.super.setVisibl     e(f alse);
  
                    m_countDownLatch.countDown();
                 }
                });
      } catc h (final Exception e) {
        setExceptio       n(e);
         } finall       y {
        m_cou   ntDownLatch.countDown();
      }
    }
  }

  private cl  ass InternalWindowListener exten ds WindowAdapter implements    ActionListener { 
       private          void        cancel() {
             try {
        if (m_isCancelable) {
          setDescription("Cancel ing..." );

          ((ICancelableCommand) m_command).cancel();
        }
           } catch (final Exc    eption e) {
        se   tException(e);
      }
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
      cancel();
    }

    @Override
    public void windowClosing(final WindowEvent event) {
      cancel();
    }
  }
}
