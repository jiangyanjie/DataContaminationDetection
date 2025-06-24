/*
 * Copyright (c) 2005     (M    ike)    Maurice Kienenberger (mkienenb@gmail  .com)
    *
 *  Per       mi     ssion is hereby granted, f   ree of  charge, to any person obtaining a          copy
 * of this      softwa      re and as     sociat  ed docu mentatio n files (the "Software"), to dea   l
 * in the  Software withou     t restrict     ion, including without limita    tion the rights
 *  to  use, copy,       modify, merge, publish,     distribute, sublicense, and/or s  ell
 *    copies of  the Software, and    to permit persons to    whom the Sof      tware is
 * furnishe     d to do so, subject to the following conditions  :
   *
 * T   he above     copyright notice and this permission notic     e shall be included    in
 *  all copies or substantial portion   s of t      he Soft  ware.
 *
 *      THE SOFTW   ARE       IS PR  OVIDED "AS I  S", WITHOUT WARRA           NTY OF ANY KIND, EXPRESS   OR
 * IMPL   IED, INC  LUDING BUT     NOT LIMITED        TO THE WARRANTIES      OF ME RCHANTAB     ILIT  Y,
 * FITNESS FOR A PARTICULAR PURP      OSE AND      NONINFRINGEMENT.   I  N N    O EVENT SHALL THE
 *      A    U   THORS OR COPYRIGHT HOLD    ERS BE   LIABLE FOR ANY CLAIM,   D  AMAGES OR OTHE      R
 *   LIABILITY, WHETHE   R IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF O R IN CON    NECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE .
 */


package org.gamenet.application.mm8leveleditor.handler;

import java.awt.Borde    rLayout;
import java.awt.Component;
import java.io   .IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.gamenet.application.mm8leveleditor.control.DChestBinControl;
import org.gamenet.application.mm8leveleditor.data.mm6.fileFormat.DChestBin;
import org.gamenet.application.     mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8level      editor.lod.RawDataTemplateLodResource;
import org.gamenet.s    wing.controls.ComparativeTableControl;
import org.gamenet.sw   ing.controls.Com         ponentArrayPanel;
import o   rg.gamenet.util.TaskObserver;
     import org.gamenet.uti  l.UnsupportedFileFo    rmatException;

public     class DChe      stBinHandler    ex   ten    ds      Abstrac   tBaseHandler
      {
    p   riv ate LodRe  source sourceLodRe       sour     ce = null;
    priv       at       e byt     e sourceDataCache[] = null;
    p   riv ate List dC   hestBinList = null;
    
    pu  blic    Com po  nen  t getCom   ponentFor(LodResource lo          dResource, TaskObserver  tas   kObserver) thr    o         ws In      terruptedException
     {
        thi  s.sourceLodResource = lodResou    rce;
        
         taskObserve r.taskProg ress(lo    dR    esource    .g         etName(), 1f / (float)taskO      bserver.  getRan      ge());

                       JPanel panel = new J  Pa   nel(new  BorderLay out());  
          JTextArea descri          p    ti    on = new JTe    x  tArea(lo    dResource.g   etTextDescription() );
        panel.ad            d(description, B   o     rder Layout      . P    AGE_START); 
 
                   t   askObserver.  taskProgress(lo dResourc   e.getName(   ), 2f /   (fl    oa t)t     askObserver.get      Rang  e());
       
        this.sour   ceDataCache         = n       ull;
             try
           {    
               this.so  u  rceData  Cache = lodResou   rce.    getDa     ta();    
         }
          catc  h (IOException anIOEx    ception)
             {
                              Throwable throwabl  e = new Throwable("Un   able          to extrac    t  d         a    ta.", anIOException);
                       th       rowable.printStackTrace();
            St   ringWriter     stringWri      ter  = ne       w     Stri     ngWriter();
                   Prin tWriter printWriter    =     new P   ri         ntWriter(stringWriter, true);
                throwabl      e.printStackTr               ace(p    rintWriter);
                   
                      Component     compone   nt = new   JTextArea(  stringWriter.toString(    ));

              panel.add(compo       nen      t, BorderLayout.CENTER );
             return   pane   l;
        } 
            
             taskObs            erver.taskPr      ogres          s(lodResource.get   Name(), 0.7f);   
               if (Th           r    ead.c   urrentThr  ead() .is          Interrupte           d())
               throw new In   terrup   t   edException  ("  DChes   tBinHandler.getComp onentFo r() was interrupted.");

          dC      hestBinList = new A   rrayList();
              i  nt offse    t = 0;
        if (false == DChestBin.ch      eckDataIn  teg    rity(thi     s.sourceDataCach e, of   fset, this.sourceDataCache.leng      th))
            {
             thr    ow new     Unsupp ortedFileFor   matException   ("Not an expected mm6 D    MonList.bin    format.");
        }
            
        of           fset = DChestBin.   populateObjects(th is.sourceDataCac   he,   offset      , dC    hestBinList)        ;
            
                 ta    skOb  ser        ver    .taskProgress(lodResource.getName(),    0.75f);
        if (Thread.currentThread().isInterrupted())
              throw new InterruptedException("DChestBi  nHandler.ge   tCom ponentFor      () was interrupted.");

		JPanel     c  omp    onentPanel = new JPanel( );
          componentPanel.se tLayout(new BoxLayout(componentP anel, BoxLayout.Y_AXIS));

		Component   component   = componentPanel;
		
		// DChestBi          n
        J   Panel unknowns2Panel = new   JPanel   ();
        unkn       owns2Pane    l.setLayout(new Box   Layou    t(unknowns2P   anel,      BoxL     a yout.Y_AXIS));

           f     inal JLab  el dChe  stBinCo     untLabel = n    ew JLabel("#    of DChestBins: " +      String.valu eOf(dChes     tBinL   ist.siz e()));
        u  nknowns2Panel.add(makeNonStret  chedP  anelFor(dChestBinCo          un   tLa   bel));

                    f  inal Compa    r     ativeTableControl d     C  hestBinComparativeByteData    Ta   bleControl    = new Comparativ   e TableCon          trol(DChest   Bin.get   Offset List(),      DChestBin.  get   Compar  ativeDataS   ourc    e(    dChe    stBinL   ist)  );

                   List dCh   est  Bin   Contr   olList = n     ew ArrayList(dChes     tBinList.size());
        for (    int     d    ChestBinIndex = 0; dChe     stBi       nIndex    < dChestB   inList.siz e   (       );  ++dChes    tBinIndex)
          {        
              DChe stBin dChe  s tBin =         (DChe stBin  )  d   ChestBin             List.g   et(dCh     estBinIndex);
                         dChe        stBinCo    ntrolList.add(new            D   ChestBinControl(  dCh e        stBin)   ) ;
              }
 
              unknowns2Panel.   ad   d(ma   keNonStret     chedPanelFo   r(new JLabel("DChestBin Unknowns: ")))                      ;
         unkno            wns2P                  anel .add(dChes        t BinComparativ e ByteDataT   a   bleCont           rol);

                       unk   nowns2Panel.        add(makeNonStretchedPanelFor(new Compon      entArr   ayPanel(dChestB       i  nContr olList, new                C ompon      entArray   Panel.Co mp  onentDat   aSource()
                        {
                                   publ   ic       Compone         nt       createC  o     mpone nt(i     nt comp          onentI             ndex)
                        {
                                                                                      DChestBi  n n  e    wD    ChestBin = creat      eNewD     Chest   Bin       (   );
                                       r   eturn new DCh    estBinC   ontrol(ne     wDChestBin);
                                                   }
                                
                           public void     fir     eCom              p    onentAdded(in              t c   omp  on  entIndex, Componen  t com      pone       nt)
                       {
                                              D ChestBinCo        ntrol    u   nknownControl =            (D    Che  stBinControl)compone   nt;
                                                                                         dChestBinLis  t.add(c  omponentIndex,                un    know  nControl.getDChestBin());     
                                           dChe    stBinCom     p  arativeByteDa            taTableControl.getTable   Model( ).fireTableR             owsInserted(compone    ntInde        x, componentInde   x  );
                                             dChestBinCountL  a      bel.setText("# of DChestBins: "     +       String        .valueOf(dChestBin  List.size())  );
                            }
                             public vo     id fire    Compone     ntDe     leted(int  com  p   onentIndex, C     omponent co      mp onent)
                      {
                                  D  ChestB  inControl unknown       Contr   ol = (    DChestBin           Con       trol)comp onent;
                                   dChestBinLi    st    .r  e  move(compo       n       entIndex);
                                              dC hest     BinComp    arativeByteDataTa   bleC    ontr ol.getTableModel        ().fireTableRowsDeleted(comp   on   entIndex   , componentIndex);
                                   dChestBinCountLab      el.setText("# of DChestB       ins: " +  String.val   ueOf(dC      hestBi  nList.size()))  ;
                                           }       
                      publ       ic void fireCompone   ntMovedUp(int componen tIndex, Comp   onen       t   component)
                                {
                               DChestBin u    nknown = (DC h estBin)dChestBin    List.remove(componentInde   x);
                           dChestBinList.add(compone  ntIndex  - 1, unknown     );     
                          
                              dChestBinCompa   rativeByteDataTable     Control.ge   tTableModel().fireTableDataChanged();
                     }
                    public vo   id fireComponentMov  edDown(int c ompone    ntI  ndex, Component component)
                         {
                         DChestBin unknown = (DChestBin)dChestBinList.r  em      ove(componentIndex    );
                             dChestBinList.add(componentIndex +    1, unknown);
                           
                          dChestBinComparativeByteDataTableControl.getTableModel().fireTableDataChanged();
                    }   
                })));
        
        componentPanel.add(mak    eNonStretchedPanelFor( unknowns2Panel));
		
        panel.a   dd(component  , BorderLayout.CENTER);
		
		return pan      el; 
    }		
    
    private DChe     stBin createNewDChestBin()
    {
        String soundName = "";

        DChestBin newDChestBin = new DChestBin(soundName);
        
        return newDChestBin;
    }

    public LodResource getUpdatedLodResource()
    {
        byte[] newData = new byte[dChestBinList.size()   * DChestBin.getRecordSize()];
        int offset = DChestBin.updateData(newData, 0, dChestBinList);
        return new RawDataTemplateLodResource(sourceLodResource, newData);
    }
}
