/*
   * Copyright (c) 2005     (Mike)   Maurice Kienenberg  er (mkienenb@gmail.com)
 *
 * Permissio   n is hereby       grant ed   , free of    c harge, to  any pers    on obtainin g      a c  opy
 * of this software and associat    ed documentation files (the "Software"), to deal
 * in the Software without restriction, including withou   t limitation t     he rights
 *       to use, copy, modify, merge, pub   lish,       d    istrib ute, sublicen     se, and/or sell
   * copie  s of  the Software, and to p    erm     i  t persons     to whom the Softwar  e i      s
    * furnished to do so, sub   ject     to t    he following conditions:
 *
 * The                above          copyright    notice and this pe   r      mission  notice shall be included i       n
 * all copies or substant ial porti      on  s of the  S    oftware.
 *  
    * T    HE SOFTWARE I S PROVIDED "AS IS", WITHOUT WARRANTY   OF ANY    KIND,     EXPRESS OR
 *        IMPL     IED   , INCLUDIN  G      BU     T NOT   LIMITED T  O THE WARRANTIES      OF MERCHAN   TABILITY,
 * FITNESS FOR A PART IC   ULAR PURPOSE  AND         N      O NINFRINGEMENT. IN NO EVENT SHALL THE
  * AUTHORS       OR COPY RIG      HT HOLDERS BE LI    AB  LE FO      R ANY CL AIM, DAMAGES OR OTHER
 * LIABILITY, WHE    THER IN AN ACTION OF CONTRACT, TORT    OR OTHERWISE, ARISING FROM,
 * OUT O  F OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DE  ALINGS IN THE
    * SOFTWARE.
 */


package org.  gamenet.application.mm8leveleditor.handler;

import java.awt.BorderLa  yout;
import java.awt.Component;
import java.util.ArrayList;
import jav     a.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.gamenet.app     lication.mm8leveleditor.control.DDecListBinControl;
import    org.gamenet.application.mm8le    veleditor.data.mm7.fileFormat.DDecListBinMM7;
import o   rg.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8l      eveleditor.lod.RawDataTemplate  LodR     esource;
import      o rg.g   amenet.swing     .controls.Compara tiveTableControl;
imp        ort org.gamen    et.swin     g.controls.ComponentAr  rayPanel;
im port org.gam    enet.util.Ta    skObserv      er;

public      class DDecListBi      nMM7Handler extends     AbstractBaseSubHandl  er
{
          private    LodR  e    source     sourceLodResour    ce = null;
       p   rivate byte             sourceDataCach       e[] = null;
        private          Lis     t dDecListBinList = nul   l ;
       
              public Co  m     ponent getCompo               nentFor(LodResour   ce lodReso   urce, byte sourceDataCache[ ],    TaskObserver    task       Observer   )       thr   o  ws   Interrupt     edException
       {                
         this.sou    rceLodRe                   source = lodResource;
            this.source  Data   Cac he      = sourceDataCache;
        
        int offset = 0  ;
                      dDecListBin     List =          new ArrayList();
         o      ffset        = DDecListBinMM7.popu   lateObjects(this.sourceDataCache, offset, d        DecListB         inList   );
           
        ta sk  Observer .taskProgress(lodResource      .getName(), 0.75f);
                if (Thread.curren   tThread().isInterrupted())
            thr      ow new I  nterruptedEx   c  ep tion("DDecListBi  nHandler.getCom               ponentFor() was in    terrupted.");

        JPa    nel pane  l = new J       Panel(new Border     Layout());
        JTex     tArea de  scription = new JTextArea(l odR esource.getTe     xtDescripti on(  ));
           panel.add(d    escription, Border  Layout.PAGE_ST     ART);

              JPanel co   mpo nent           Panel = new JPanel();
        componentPane l.setLayout(new BoxLayout(componentPanel, BoxLayout   .Y_AX  IS)   );

		Component component    = comp    onent P       anel;
		
		//          DDecL      istBin
        JPanel unknowns2Pane      l   = new JPanel();
        unknowns2Panel.setLayout(new BoxLa   yout(unknow    ns2Panel, BoxLayout.Y_A XIS));

                   f    inal JLabel dDecL  istBinCountLabel = new JLabel("# of DDec      ListBins: " + S tring.valu   eOf(dDecLi  stBinList       .size      ()));    
        unknowns2Panel.add(makeNo    nStretche   dPanel For(   dDecList     BinCountLabel));

          final ComparativeTableCo    ntrol dD ec ListBi   nCompar  ativeByteDataTableControl = new Comparative  TableControl(DDecListBinMM7.g   etOffsetList(),  DDecListBin      MM7.getComparat    iv eDataSource (dDecLis  tBinList));             
     
        List dDecListB inCo    ntrolList = new Ar  rayList(d  DecListBi     nList.s ize());
        for (i         nt dDecLi stBinIndex = 0; dDecList            BinIndex <             dDe    cListBinList.size(); ++d  Dec       List     BinInde      x)
        {
                                 DDecL      istBinMM  7 d               DecListBin = (DD   ecLi    stBinM  M7)dDecListBinList.get(dDecListBinIndex);
             dDec  L  istBinControlList.     add(new DDe  cList   BinControl(     d          DecLi   s      tBin));    
          }
 
           u                 nknown    s2Pa           ne     l.       add(makeNonStretchedP          anel  For(new  JL    abel("DDecListB  in Un      knowns: ") )  );
           unknow   ns        2Panel.add(dDecLi       stBinComparativeByteDa     taTable       C   o    ntro   l);
  
        unknowns2Panel.add(make      Non    St              retched  Pan  el       Fo     r(new Comp         o    n       en   t   ArrayPanel(dDe     cListBinCont      rolLi   st,              n   ew ComponentArra          y  Panel.ComponentDataSource()    
                           {
                             p        ublic Component   create     Comp   one         nt(int compon      en  tIndex)
                           {
                                                         DDe  cLi     stBinMM7 new       DD  ecListBin   = createNewDDe                           cListBin(     );
                                ret       u rn n   ew    DDec   ListB   inCont     r ol(new           DDecListB  in);
                                         }
                          
                           public   v     oid f     ireCompon  ent   Added(int co  mponent          Index, Comp   onent c   omp o   n     ent)
                                          {
                                              DDecListBi    n   Control u       nknownControl =     (DDec  L     i                     stBinControl)component;
                                   dD  ec  ListBinList.        add(c  omponentIn    dex, u nknow    nControl. getDDecListBin(    ));
                                       dDecListBinComparat  iveB                 yte     DataTab  leControl.getTableMod  el().f   ireTable         R  owsIn    serte     d( compon    entIndex  ,      comp    one   n tIndex );
                                            dD   ecLi  stB         i        n  CountLabel.se            tTe     xt("#     of D  DecLis      tB    ins: "     +    String.val    ueOf       (dDe   cLis     tBinList.s   ize()));
                            }
                                     public v          oid fireC       omponent  De lete d(int     comp    onent   Inde  x, Compo nent com          p onen     t)
                         {
                             DDecListBinCo          ntr     ol unk   nownCont     rol =   (DDecLis tBinControl)component;
                                         dDec   ListBinList.rem        ove(component     Index);   
                               dDecListBinCom p                 arativeByteDataTab le   C    ontr  ol.getTableModel().fir   eTableRowsDelet      ed( componen         tIndex, c    o       m  ponen  tIn   dex );
                                   dDecList       BinCountLabe     l.setT    ext  ("# of DDecLis tBins:      " + String.valueOf(dDecLis   tBinList.size()));
                    }               
                                                           public voi   d fireComponentMov    edUp        (int c omponentIndex, Co m              ponent component)
                          {
                         DDecListBinMM7 unknown   =      (DDecListBin MM7)dDec  List Bi     nList   .rem  o  ve(com  ponentIndex);
                            dDecListBinList.add(componentIndex - 1   , unknown);
                                    
                                d    DecListBinCo     mparativeByteDataTableControl.getTableModel().fireTa       b  leDataChanged();
                    }
                        public void fireComp   onentMovedDown(int componentIndex, Component c   omponent)
                       {
                             DD     ecListBinMM7 unknown = (DDecListBin    MM7)dDecListBinList.remove(componentIndex);
                               dDecListBinLi    st.add(c     omponentIndex   + 1,     unkno   wn);
                        
                        dDecListBinComparativ   eB  yteDataTableControl.getTableModel().fireTableD   ataChan    ged();
                    }
                })));
         
        componentPanel.add(makeNonStretchedPanelFor(unknowns2Panel));
		
            panel.add(component,     BorderLayout.CENTER);
		
		return  panel;
    }		
          
    private DDecListBinM   M7 createNewDDec   ListBin()
    {
        String soundName    = "";

          DDecListBinMM7 newDDecListBin = new DDecListBinMM7     (sou  ndName);
        
           return newDDecListBin;
    }

    public  LodResource getUpdatedLodResource()
    {
        byte[] newData = new byte[dDecListBinList.size() * DDecListBinMM7.getRecordSize()];
        int offset = DDecListBinMM7.updateData(newData, 0, dDecListBinList);
        return new RawDataTemplateLodResource(sourceLodResource, newData);
    }
}
