/*
 * Copyright (c) 2005    (Mike) Maurice Kienenberger (mkienenb@gmail.com)
          *
 * Perm   ission       is he   reby granted, free        of charge, to any pe   rson obtai    ning a copy
 * of this software   and associated document           ation     files (t  he "Software"), to deal
 * in the Software with         out restriction, inclu  ding     without limi  tation the righ   ts
 * to us  e, copy, modify, merge, publish, distri  bute, sublic         en se, a       nd/or se ll
 * copies of the     Software, and t   o permit pe    rsons to    whom th     e  Software is
     *   furnished t  o do so, subject to the follow  ing con  diti  o   ns:
 *     
 * The above  c o     pyright notice and    this permission notice sh    all be included in
 * all copies   o r substantial portions of the  Softwar   e.
     *
       *    THE SOFTWA    RE IS PROVIDED "A     S IS", WITHOUT    WARRANTY OF ANY   KIND, EXPRESS OR
   * IMP           LIED, INCLUDING BUT NOT LIMITED TO THE WARRAN TIES OF MERCHANTABI   LITY,    
 * FITNESS FOR A PARTICU   L   AR PURPOSE AND NO    NINF  RI    NGEMENT. IN NO EVENT SHALL THE
 * AUTH     O     RS OR COPYRIGHT HOLDER    S B  E LIABLE FOR ANY CLAIM, DAM        AGES OR OTHER
     * LIA   BILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OT HERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


package org.gamenet.application.mm8l  eveleditor.handler;

import java.awt.BorderLayout;
import java.awt.Componen  t;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;

import javax.swing.JPanel;
i  m    port javax.swing.JTextArea;

import org.gamenet.application.mm8leveleditor.control.DeltaOutdoorDataMapControl;
import org.gamenet.application.mm8leveleditor.data.GameVersion;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.D3Object;
import org.gamenet.application      .mm8leve   leditor.data.mm6.outdoor.DeltaOutdoorDataMap;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.OutdoorDataMap;
import org.gamenet.       application.mm8leveleditor.lod.LodEntry;
import org.gamenet.application.mm8leveleditor.lod.LodResource;
import org.gamenet.application.mm8leveleditor.lod.RawDataTemplateLod  Resource;
     import org.gamenet.ut         il.Ta   skObserver;
impo rt org.gamenet.util.UnsupportedFileFormatException;

   import com.mmbr eak     fast.unlod.lod      .LodFile;
i   mport com.mmbre      akfast.unlod.lod.NoSuchEntryE xcept     ion;

public class DdmHandler extends Abstr     actBaseHandler imp   lements LodResource   Handler
{
    private LodResource sourceL odResource = null; 
       private byte sourc    eDataCache[  ] = null;
    private DeltaOutdoor    DataMap ddm           = null;
      
        public Component getComponentFor(Lod  Resource lo            d     Resource,  TaskObserver     taskObse  rv          er) throws Interrup    tedException
    {
         this.sourceLodRes   ource =    lodResource;
          
               taskObserver. taskP  rogress(lodResource    .get    Nam   e(), 1f / (float    )task  Observer.getRange  ());

                 JPanel      pa    n   el = new JPane l(new        Bor  derLayout());
          JTextA   rea d         escr        iption = new JT    e  x    t  Area(l    odResource.g et  Te  xtD     escription(     ));     
             panel.add( description, BorderL  a    yout.PAGE_START);
 
        taskObserver.tas  kProgress(  lodResource.getNa  me     (), 2f / (  floa t)     tas kObserver.getRang  e(      ));

           this.so   urc     e     Dat     aCache =          null   ;
              try
         {
              this.sourceD    ataCache   =     lodResource    .g         etDa     ta();
            }
             catch (IOExc  ept  ion anIOE  xce       ption)     
         {     
                    Thr      owable throwable = new Thro   wable("Unab  l       e to extrac         t data.", anIOExce        ption);
                  throwab   le.p rin    tStac    kTrac   e();
                     StringWriter      string  Writer = n ew StringWri     ter();
                        Print  Writer pr  intWriter         =  new Pri   ntWr    iter(stringWr    iter,                   true);
                    throwable.printS      tackTrace(printW       rit     er);
               
                    Component    compo     nent = new JTextArea(strin     gWrite  r.t oSt  ring    ());

               panel.add(  component  , BorderLayout.CENT   ER);           
              return pan   el;
        }
              
        task    Observer.taskPr             ogr                    ess(       lodResourc e   .getNam    e(), 0.7f);
             if (Thread.currentThrea   d().isI  nterrupted())
                                    throw new Inte   rrupt  edExcept   ion("DdmHandler.get       Co mpone  n          tFo      r() was   interrupte       d.") ;

                 if (Del     taOutdoorDataMap.checkD  at aInte    g      rity(GameVersion.M M6, sou     r   ceDa  taC    ache, 0, sou         rceD   ataCache.length)               )
           {      
                   ddm           =   new DeltaO           utdoorDataMap (Gam   eVersio      n.MM6, this.   source DataCache)     ;
             }            
                  else   
         {
                                      int totalSpri     teCount = 0;
            int     totalD       3Obje   ctCount = 0;
                             int tota    lD3ObjectFa  c     e ts  C      ount = 0 ;

                         //     R            eall     y ug   l        y hack to read to          tal  n    um   be   r      of da    ta from      odm fi   le
                   Lo   d             Entry ddmLo           d       Entry = (LodEntry)lodRe            sou rce;
                  LodFile lod        File = d dmL   odEntry     .getLodFile(      );   
                         try
                            {
                                   Lo      dEntry odmLodE                      nt  ry =    lo dFi      le  .f   indLodEn     tryByFile  Nam      e(    lodReso ur   c        e.ge     tName().subs               tring       (0, lodResource.getName().leng  th() - 3)               + " odm           ");
                        
                                    byte      odmSou     rceDataCa        ch     e[   ];
                             try
                {
                                           od    mSourc        eDat aCache =     od  mLodE    nt  ry.getD  at       a(  );
                            }
                            catch (I     OExcep    tion anIOE   xcept        i   on)
                             {
                        T       h    rowable    throwable = new Th  r  owable  (         "    Unab    le t  o extr  act data from       odm.", anIOE  xcep      tion  )       ;
                              thro   wable.printStackTrace   ();
                                              St     ring        Writer stringWr  iter =     ne w        Strin           gW  rit   er    ();
                             PrintWrit   er pr  i         nt   Write   r     = new  Prin           tWrite            r(stringWriter,  tr      ue);
                                                      throw  able.p   rintStackTrace(      printWr    iter);
                     
                                                               Com   ponent component    =       new JTextAr   ea      (str             ingWriter     .to      String  ());

                              pa    nel.add (component, B orderLayo    ut.   CE    N   TER);
                         re   t       u        r      n panel;
                  }
   
                                 Out       d   oo   r      Da    ta     Map odm;
                    if (OutdoorDataMap.       checkD    ataIntegrit   y( G    ame            Version.MM7, odm   SourceDataCache, 0, odmSourceDa   ta    Cache    .     length))
                             {
                                    odm = n   ew Out    doo     r   Data     Map(GameV ersion     .MM7,    o    d  mSour       ceData Cach         e  );
                    }
                                  el se       if      (Ou tdoorDa    taMap.che  ckDataI nteg                                     rity(GameVersion.MM8     ,  od mSou rceDataCa   che, 0,    odmSourceDat   aCache.length))
                          {
                                       odm = ne w O ut     doo      rDataMap(GameVers     ion          .MM8,    odmSour ceDataCa  che);
                 }
                         else
                     {
                         throw ne     w UnsupportedFileFormat     Exceptio     n     ("Not an expected mm7 or mm8 odm format.");
                    }
                               
                    totalS  priteCount    = odm       .getSpri teList().         size();
                   t    otalD3O   bj ectCount = o    dm    .getD3Obje                ctList().size();
                    total  D3O  bjectFacetsCount    = 0;
                           Iterato  r    d3ObjectIterator           =      od  m         .ge       tD3Objec       tList().  it      erator();
                      while (d3ObjectItera   tor.hasNext())
                                          {
                         D3Object d3Object = ( D3Object) d3Obje   ctIterator.    next();
                     totalD3 ObjectFacetsC  ount += d3O             bj  ec      t.   getFacetList().size();
                    }
                }
                catch (NoSuchEntryExceptio n exception)
            {
                totalSpriteCount =    -1;
                totalD3O    bjectCount = -1    ;
                    totalD3ObjectFacetsCou  nt    = -1;
            }
     
               if (DeltaOutdoorDataMap.checkDataIntegrity(Ga       meVersion.MM7, sourc  eDataC ache,    0,    sourceD at           aC  ache.length, totalSpriteCount, totalD3ObjectFa    cetsCount))
                 {
                      ddm =      new DeltaOutdoorDataMap(GameVersion.MM7, this.sourceDataCache, totalSprit  eCount , totalD3ObjectFacetsCount);   
              }
              else if (DeltaOutdoorDataMap.c    he   ckDataIntegrity(GameVersi    on.MM8, sour   ce DataCache, 0, sourceDataCache.length, totalSpriteCount, totalD3ObjectFacet  sCount))
            {
                   ddm    = new Delta      OutdoorDataMap(      GameVersion.MM8, this.sourceDataCache, totalSpriteCount, totalD3ObjectFac     etsCount);
              }
                   else
            {
                      throw new Unsuppo    rtedFileFormatException("Not an expected mm6, mm7, or mm8 ddm format.");
            }
        }
            
            
           
        taskObserver.taskProgress(lodResource.getName(), 0.75f);
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException("DdmHandler.getComponentFor() was interrupted.");

        panel.add(new DeltaOutdoorDataMapControl(ddm), BorderLayout.CENTER);

		return panel;
    }		
    
    public LodResource getUpdatedLodResour   ce()
    {
        byte newData[] = this.ddm.updateData(this.sourceDataCache);
        return new RawDataTemplateLodResource(sourceLodResource, newData);
    }
}
