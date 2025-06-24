/*
 * Copyright   (  c) 2005 (Mik  e) Maurice Kienenberger   (mkienenb@gmail .com)
 * 
 *        Perm    ission is hereb      y granted,        free of    charge, to any person obtaining a copy
  *    of this softwa     re and asso  ciated documentat  ion fil    es     (the "Software"),      to deal
  * in the Software without r  est     rictio n, including without limitation the rig hts
 * to use , copy,      modify, m     er     ge  , publish, distribute, sublicense,          and/   or sell
 * copies of the Software, a      nd to per     mit       persons to whom the     Softwa      re is
 * fur       nished to do      so, subje    ct to the f ollowing condition    s:
 *                
 * The above copyright notice an    d this permissio       n notice shall        be includ           e    d in
 * all copies or s ubstantial     portions of the Software.
 * 
 * THE SOFTWAR   E IS PROVIDED   "A          S IS", WITHO       UT WARRA     NT   Y OF AN  Y KIND, EXP         RESS       OR
    * IMPL  IED, INCLUDING BUT NOT LIMITED   TO THE WARRANT   IES       O      F MERCHAN  TABILITY,
 * FITNESS FOR A PARTICULAR P  URPOSE AND NONINFRINGEM ENT. IN NO EVENT SHALL            THE  
 * AUTH  ORS OR COPYR  IGH   T HOLDERS B  E LIABL  E FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABI    LITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * O      UT OF OR IN CONNECTION WIT    H THE SOFTWARE OR THE USE OR O     THER DEALINGS IN THE
 * S  OFTWARE.
 */
package org.gamenet.swing.controls;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.even   t.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import j    ava.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.C      onstructor;
import java.lang.reflect .InvocationTargetExce ption;
import java.util.ArrayList;
import   java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent   ;
import javax.swing.event.Tr  eeSelectionListener;
import javax.swing.tr ee.DefaultTreeCellRenderer  ;
import javax.swing.tree.TreeModel;
import javax   .swing.tree.TreePath;

import org.gamenet.application.mm8leveleditor.BinaryFileCompare;
import  or        g   .gamenet.application.mm8levele     ditor.lod.Pr ogressDisplayer;
import     org.gamenet.util.MonitoredTaskThread;
import org.gamenet.u  til.S  equenceNumber  Iterator;
i    mport org.gamenet.util.TaskObserver;   


publ         ic c   lass DataSect       i   onTr     ee extends JTree
{
    private boolean    multithrea   ded =          true;
    private P        rogressDis         pl       ayer progressDisplayer;
                 
       private DataSectionab le roo    t;
      private Strin    g roo  tName;

    private     JP  opupMenu po                pup = null         ;
       private JMenuI         te     m com   pareWithExternalFi   le  Me     nuItem =       null;
         priva   te JMenuItem   c     o        mpareWithEachOth erMenu    Item =      null;

             clas    s DataSection   Renderer extends    De   fa  ultTreeCellRe       ndere r
       {
        public      DataSectionRenderer()    
             {
                 }  

                   publi   c Co   mponent getTreeCe llRendererCompo  nent(JTree tree, O    b               je    ct    value,        
                  bool     ean   se       l, b     oole an expan      d     ed, bool  ean       l eaf, int  row,
                            boo  lean has       Focus )
           {
                     String la   be      l    N   ame = ((Na  me      dDataSecti       onabl  e)value).getLabel ()     ;
                          
                                       Compon             ent render   er = super.getTreeCel  lRend ererCompone         n    t(tr    ee,
                                 value,      sel, ex  panded  , leaf,  row,    hasFocus);
                 ((JLabel) r  ender er).set T    ext(labelName);

                      return        renderer;
            
        }
    }
    
    pri   va               te s       tatic final  int INDEX        _STEP_     COUN   T = 10;

       int  erface NamedDat  aSectiona  ble     ext  ends D   ataSec        tio   nable
     {
        public Stri        n g getL  a             be l();
    }
    
    static     c lass NamedDataSecti     ona bleImpl imp  le   ments Named DataSectionable
    {
                priv      ate DataSecti         onable dataSection     able;
                              privat  e String    la   bel;    
               
                                 publ    ic    boo  le an equals(Obj      e c   t da       t aS   ectio    nableObject)
        {
                       if (fal  se == dataSectio     nableObj        ect   i nstanceof NamedDataSec          ti   onableImpl)  re tu   rn fals e;
        
                        NamedDataS  ectionable     Impl named    D     ataSec           tionab   leImpl =    (NamedDa     ta   Sec  tionab leImpl)d       ataSe     ctionableObject;       
                                 if (false    == nam edDataSectio    na   bl  eImpl.get  Da    taSectionable(    ).equals(data    Sectionable))  return f  alse;
                 if (fal    se == nam    edDataSec  tionableIm pl.getLabel().equals        (label))      return false  ;
              return tr       ue ;
        }   
            
        publ       ic Named DataS   ect    ionab              l   eImpl(   Dat   aSecti onable dataSecti    onable, Strin   g       label)
                {
                                        this.   da  taSecti   ona    bl     e = dat    aSectio    na    ble;
             this   .label =     la         be     l;
        }
                   
           public St  ring toString()
          {
                            StringBuffe    r str    ingBuffer   = new Stri  ngBuf    fe    r();
                stri    n g   Buffer    .appe   nd(h     a    shCod   e());
             stringBuf  fer  .a   ppen     d(":dataS ectionable  =" + dataSection        able);
                strin    gBuffer.append  (",label="    + l  abe  l);
            return s     tri    ngBuffer.toStrin   g();
               }
          public D  ataSectionable getDat a      Sect      io nabl      e()
                   {
             retur             n      this.dataSectionabl      e;
            }
                p  ublic  String g etLabel()
        {
                            re    turn this.label;   
                        }

        pu   blic DataSec     tion[] getStat               ic    DataSec    ti  ons()
        {
                ret   urn da     ta  Sectionable.getS  tat        icD  ataSe  ctions();
          }
   
                   public Ob    ject         get Da   ta  ()
                 {
                    retu                    rn dataSectionable.get Data();
           }

             pub   l           ic Ob    ject ge    tDat    aForDat aSection(DataSection   dataSection)
         {
                  re        turn da    taSect    ion   ab   le          .g   e  t             DataFor DataS   e  cti  on(dataSection);
          }

             p     ublic    Compo  nent getC        om p     onen  tForDataS   ection(TaskOb ser  ver tas kO  b   s erver, Stri  ng dataSect  io   nName)     throws   Inte      r     ruptedEx    ception
                             {
                 retur   n   dat   aSectionable.getCompo      nentF or  DataSection    (taskObser  ver, dat aSectionNa me);
                      }

            publi    c    C     omponen          t ge       t    ListCompone  ntForDataS    ection(TaskObserver taskO    bserver, String dataS       ectionNam   e,       List list, I   te   rat   or in    dexItera tor)            thr ows Inte   rruptedExce  pt ion
         {
                 ret    urn  dat         aSectio  nabl    e   .getL  istCompon     entForDat aSec    tio    n(taskObse        rver, dataSectio n   Name, list,      inde      x     Iterat                  or);
                  }
       }
         
       static class Le  afDataSectiona  b  le im pl  e    men   ts  Named   D  a    t                a   Se   ctiona     bl   e
    {
                publ    i c Object data;
               public String l   abel;
          
                publi         c b       oolean equal s(Ob    jec          t dataSect io nab  le)
                                  {
                                 if (fals    e == dat   aSectionable   instanceof Lea        fDa    taS     ectionable   )  return false    ;
                  
            Lea           fDat  a S       e    ctionable       leafDataS  ec    tion     able        = (Leaf   DataSectionable)d  ataSect   ion   able;  
                 
                        i     f      (     nu   ll == leafD  ataSecti  onabl e.get   Data())
                            {
                if    (nu                ll !=   data)  return   false;
                }
             els    e
                          {            
                  if (false ==         leafDa   taS         ectionable. getData().equals (d     ata)     )  r   eturn false;
                        }
                
                             if (false       == leafDataSect    iona     b    le.getLabel().equals(label))                          return fal          se;
                 return tr          u   e;
            }
             
                pub  l    ic LeafDa     taSecti      onab   le(    Obj     ect d     ata, String  labe   l)
             {
                    s              u   per();
            this.d  ata = dat   a;   
                     this   .label = label  ;
          }
                       
           public    St   ring getLabel(   )
           {
                       re    turn this .label;
           }

          public Dat   a  Section[] g   etStaticDa    taSections(  )
        {
                                return nu           l   l;
              }

         public                      Object     getD  at  a    ()  
           {
                return  da  ta;
                }

               p  ubli        c O    bject g           et     Da   taFor     Da    t       aSection(DataSe       c     tion           dataSecti  o     n)
        {
                   r           etur     n nu      l l;
        }

                      p   ublic Compo  nent getCom      p onentFor    DataSecti    on(TaskObs     erv   er taskObserver, S  tring dataSectionN    ame) t  hrows In terruptedExcep   t      ion
        {
                     return nul     l;
              }     

        publi      c C  om    ponent ge     tListCompone ntForDa    ta   Se   cti      on(TaskObser  ve  r     tas kObserver,          String dataSectionName, List list, Iterator        indexI        te       rator) throws In  terr  uptedEx   cepti    on
            {
            retu rn null;
                  }  
    }
    
        static    c         lass Li   stItemD  ataSectionable implements N    amedDataS      ectionable
        {
         p  r ivate     DataSect      ionable parentDataSectionable;      
            p   rivate Str    ing    listID;
             priv  ate i   nt inde  x;
         
                                 public boolean   eq       u   als(Object dataSectionableObject        )
               {
                     if (fa   lse          =  =  dataSectionableOb  je   c       t       insta     nceof List  ItemDataSec      tionable)       return false;
              
                 ListItemDa      t          aSectionable lis     tItemD        ataSec tiona           ble =  (Lis            tIte     mDataSe  c     t  io  nable)dataSectionableObj  ect;   
              if (fal  s   e =   = listItemDa   ta  Sec    tio  nable.get    Pare        ntDataSec           tionable().equal     s (parentDat    aSectionable))  retur        n false;
            if           (false       == listItemD  a  taSe   ctionable.getLi      stID      ( ).equa  ls(lis   t                   ID) )      ret        urn false;
                    if (listItemDataSecti           onable.getIn   dex() !=         inde  x)  return fals         e;
                          return true;
                }
           
        public ListItemDataSect    ionable(DataSe       ctio      nable parentDataSectionab   le, String listID,                            int index)
        {
            this.p    arentDataSectionab   le = pa r     e ntDataSection   able;
                  t   his.listID = listI            D;
               this.ind    ex = in  dex;
             }
          
                            p     ubl    ic String toString    ()
                  {
                   StringBuffer  stringBuffer    = ne w StringBu   ffer();
             st   ringBuffer.a    ppend(hashCode  ());
               s              t   ringBuffer.a  ppend  (":dataSectionable="  + pare     ntD             ataSectionable);
                   s  tringB  uff e   r.append(",  li    stID=" + listID);
                    strin   gBuffer.append(" ,inde x=" +      index);
                ret   ur   n stringBu   f  fer.           t  oString();
               }
            public Data         Secti  onab       le ge        tP            arentDataSec    tionable()
                 {
                     r              eturn this.parentDat  a          Sectionab  le;
                       }
                       public             int g etIndex()
            {
                  re         turn this.index; 
        }
          publi c String getLabel(  )
        {
            return listID +  "["   +  String.valueOf(index  )      +       "]"   ;
        }

         public  St    ri  ng       getList    ID()
                {
                             retu   rn t     his.listI       D;
        }
                      /*      * 
                      * @r  etur      n l      ist 
         */
             public List g   et        List()
               {     
              DataSect     ion c hildDataSection =    get   Data  Sec  tionForParentAndName(      parentDataSectionable, listID     );
              retu    rn (L i  st        )par entDataSectionable.getDataFo        rDataSect       ion(childDa    t  aSect    ion)     ;
        } 
        
          publi c     NamedDa    ta  Section  able getDataSectionab       le()
        {
            return g       etDataS ec  tionable     ForParentNameA    nd    Index(parentDat   aSectiona ble, listID, index);
                       }

                          publi    c Data    Section[] getStaticDataSection     s()
                       {
                    ret    urn    getDataS   ectionable().ge    tS    taticDat   aS      ections();
                         }

        public Object getData()      
           {
                    return get          D      ataSec                    tiona            ble  ().  g  etData();
        }

             public Object getDataForDa            ta       Section    (DataSectio     n      dataSection  )
        {
                return get    DataSec   tionable().ge tDat  aForDataSectio              n(    data     Section);
        }

        public     Component getComponentForDataSection  (Tas        kObserver taskObse  rver, S   tring dataSectionName)    thr  ows  Interrupte    dE   xception
          {
              retur    n get    DataSectionable()         .getCom   ponentForDataSection(ta  skOb     serv   er, dataSect   ionNam e);
        }

           pu blic   Component    getListCompo  nent    ForDa  t   aSe       ction(TaskObs   er    ver taskOb  server,          String dataSectionName, Li   st li    st         , Iterator indexIterator) throws Inter         ruptedExce    ption
         {
                             retur  n      g   e tD ataSectionable().g   etList      Component ForData  Section(  ta   skObserver,                      da  taSectionN  ame,  list, inde       xIterator)    ;   
           }          
    }
    
    static class ListDataSectionable imple     ments NamedDa taSectio  nable
          {
           p  rivate DataSectionable    parentData          Sec      tion ab   le;
        pri     vate String lis  tI    D;
                 privat  e int star       tIndex;
               priva     te   int endI ndex;
         private int      in   dexS tep      ;
          
        public bo olean  equals(Ob           ject    dataSectionabl   e)
              {
            if (fal  se == dataSecti     onable inst ance                  o   f ListDa    t  aSect  io  nab  le)  retu  r            n f   alse;
         
               List  DataSecti   onable   listDataSectionable = (List D    ata   Sectiona     ble) dat     aSe        ctionabl       e;
                              if (fals   e =   = l   istDa taSection    able   .ge          tParentDataSect          iona       ble().equals(p        arentDataSectio nable))  return false;        
                            i    f (listData   Se  c  t i         o     n   ab    l   e    .getStartIndex() !=    startInd   ex)  retur  n fa lse;      
                       if   (listDat     aSec    tionabl e.getEndIn          dex() != endIn     dex)  return false;                 
                if (      list D                           ataSe     ctiona   b    le.getIndexStep() != indexStep)        r     eturn  false;  
            retu    rn tru  e;
            }
                  
            publi  c String g etLabel()
                {
                                if (-1 == e  ndInd     ex)
                   retu rn l  is  tID + "[no elements]";  

            i  f (s   tartIndex == en   dIndex)
                        retur      n list        ID                  +          "[" + String.valueOf(end   In  d      e     x)  + "]"  ;
                        return     listID   + "[               " + String.v         alueOf(sta       rtIndex) + ". ." + String.val ueO   f    (end    Index) + "] "; 
              }

        privat  e ListDataS         ecti     on    ab  le(DataSectionable parentDataSec   tion        able,     String listID,   in t      st      artIn  dex, i    nt     endIndex   ,       int indexStep )
                  {
                       this.pa  r  entDa taS  ect    ion     able = paren   tData  Sectionable;
                   t   his.listID =   lis   tID;
                        th   is.start        Index = startIn de    x  ;
                t hi  s.endInde   x   = end   Index;
                     this.indexStep = indexStep;   
                            if (   startIndex == e   ndInd  ex)
                                   {
                                 throw   new    Runt    imeE   xception("Sh    ould never reach h       ere:   startIndex=" + start Index       +        ", endI     ndex=" + e ndIn        dex);
                 }    
        }

           pu  blic i   n   t getEndIndex()
              { 
            return thi   s.endIndex;
               }
                 pub   lic int      getIn  dexStep(   )
          {
            r    etu              r  n     this.in     d  exStep;
            }
                      public String getListID()
        {
            return t      his.lis  tID;
        }
            publ   ic int getStart In  de       x(  )
             {
                  return t         his.startIndex;
             }

                   public int size()
          {
                   r  eturn ((endIndex - startIn       dex) / in     dexStep)  + 1;  
        }

                     public NamedDat  aSectio  nab      le ge     tC hil  d(int index) //          ind = 2, s    tart=100, end=199, step =    10  => [1   2                  0..129, 1]     
            {     
                                  if (1 == indexStep    )   
            { 
                            return n     ew     Lis   tItemDataSectionable(    parent       Da    taSection   able,  listID, sta     rtIndex + index);
            }
              else
                {
                                            int chi    ldStartIndex = (index  * indexStep ) + startIndex;
                     int    ch   ildE        ndIndex =    Math.min(endI   nd    e x, childStart  Inde    x +   (indexSte   p - 1    ));
                int childIn     d e    xStep =    Math.max(1, indexStep   /    I  N  DEX_     STEP     _COUN   T);
                  
                        i      f  (childStar         tIndex =   = childEndIndex)
                     {
                           return new L     istI   temDataSectionable(parentDataSec  tionable, l    i  st   ID, childEndIndex  );
                     }

                         r  eturn    new ListDataSe  ct   ionable  (parentDataSectionable, listID, childStartIndex, childEndIndex, childIndexStep);
            }
        }
        
          pub     lic  String to   String()
               {
                     Str   ingBuffer stringBu    ffer = new    Strin g B    uf     fer();
                string     Buffer .app   e     nd   ( has   hCode     ());
                             st      ringBu        ffe    r.a  ppend("  :listID="           + lis tID);
             stringBuffer   .ap    pend(     ",s ta rt  Index=" + String.va  lu   e Of(startIndex));      
                  str in gBuffer.a   ppend(",endIndex="        +   S tring.valueOf(endIndex));              
               stri     n  gBuffer.ap          p    e   nd(",ind   exSt   e  p=" + S   tring.     valu       eOf( indexStep));
                    return                stringBuffer.       toString(   );
        }
         
           public DataSect  ion[]               getSta                   tic      Da   taSections()
               {
              return parentDataSect  ionable.getStaticD    ataSecti     ons();
           }
           
               /** 
                  * @ret  u   rn      l               ist
                 */ 
        pu  blic List         getList(    )
           {   
                 DataS ection c     hildDataSe   ction          = getDataSec        tionForP  arentAndName(parent  DataS  ect ionab             le, listI     D )       ;
               return                   (List     )p   aren  tDataSect  ionable.g      etDa    taFo          rDataSection(            childDat    aSection)    ;
         }
             
                   public Obje       c  t getData() 
          {
            r  etu             rn   parent Dat   aSectio nabl e.ge  tData();
              } 
        public Object getDat aF  orDataSection(DataSection dataSec    tio        n)                  
          {
            return parentDataSectionable   .getDataFo   rDat    aSe    ct     ion (dat   aS  ection);
        }
               public    Com       pone    nt   getCompo  nent       F         or         Da  taSe ct      ion    (Tas      kObserver      taskObs    erver, String dataSectionN   ame) throws      InterruptedExcep  tion      
              {
                retu   rn paren t Da taSectionab    l  e.getCom   p onentF         orD   ataSecti     on(taskObs     erver, dat              aSec   tionName   );
             }
        public Component get List Comp     one     ntForDataSec      t io            n(TaskO     bserver taskObserver, S tring data SectionName, List list , Iter    ator indexI    tera  tor) thr  ows Interrupted  Excepti on
                           {       
                 /    / I   MPLE  MENT: probably ne   e  d           own lis  t index ite  rator
              return  parentDataS      ectionable.    getLi  stCom     ponen  tFo  rDataSec    tion(taskObserver, dataSe     ction  Name, list, ind    exIterator);
        }
            public  Dat aSectionabl  e g etP  arentD          ataSectionabl e( )
                {  
                                ret        urn this.parentDa t   aSecti   ona        ble;   
        }

          public Named DataSe     ctionable               get  Data   Section         a     bleF     orIndex(int index)
              {
                  return       getDat                   aSe     ctionableForParentNameAndIndex(parent          DataSec    tionabl    e, listID, index);
           }    
    }  
       
           public st  atic      Na  medDataSectionable getDataSectionableFor    ParentNameAndIndex(Data Section       able par  entData S    ection   able, String childSectionName, int ind   ex)
      {
          DataSectio  n     chi   ldDa  t   aSectio n = getDataSectionForPa   rentAndName(parentDataSectiona         b     le, c   hildSectionName);
          
                 Class ch  ildDataS   ect       ionableClass =     ch  ildDataSection.getDa  taSectiona    bleClass();
             List chi  ldObj   ect = (List)pare    nt  Da       taSec   tion able    .get         DataForDataSect          ion(childDataSectio  n);
                  Obj   ect ob          j         ect = childObject.g        et(index);
        
               String sectionName =    childSe           ctionName + "[" + St  ri   ng   .value  Of(index) +  "]";
         if (n  ull                == childData         Se   ctio   nable    Class)    
                   retu      rn new LeafDataSectionab   le(ob    ject,  se ctionName);
               else return    new   NamedD       ataSec     tionabl  eImpl(  constructDataSectionableForNonListObjec                t    (c  hil       dDataSectionableClass, objec        t), sectionName );
    }     

    public s   tat     ic D   ataSection     getDa   taSecti        onFor         ParentAndName               (Da taSecti     onable parent  Da  taSect              ionable, String s    ect   i     onNam      e)
            {        
                      DataSection dat  a          Section  Array[]       = parentDataSe      ctio   nab  le.ge    tStati     c       Dat aS      e ctions();
                           for (int ind      ex = 0;    index    < d   a     taSectionAr   ray.l ength; index++)
              {
              DataS        ection se              ction = dat     aSectionAr     ray[index] ;
               i  f (section.  getDataSe  ctionName() == sec   tionN     a  m e)      r   etur n section;
        }
                 
            return null;
        }   
     
    publ    i    c static D ataSec   ti ona     ble c    o  ns  tructD       ata    Sectionabl  eForNonL istObject(Class d        ata  Sectionabl      eC   lass, O  bject object  )     
    {
           if (null == d      at a Sec    tiona     bleClas                  s)  throw n e     w       Runtime      E       xcep       tion("da       taSectionabl       eClas      s is null.    ")    ;
           if (object instanceof    List)        throw n    ew Runtim     eEx    cep    tion             ("obje     ct is Lis   t.");
        
                 Constructor[] constructorArray    =    d      ataSec          tiona   bl       eClass.getConstruc  tors();
                  if (1      !=       co      nstr  uctorArray.l          ength)           
                throw new R    untim   eExcepti on(Strin  g.valueOf(co   n  struc torArra    y.length) + " cons               tr     ucto  rs found for class " + dataSec           tionableClass.g    etNa    me   ());
        
                  Object[]  paramet er    Array = new    Obje  ct[]     { o     bject };
        try
                     {
            r     etu rn   (DataSe  c    tion       able)c onstr uct        o    rAr      ra        y    [0]      .    newInst        ance(par     ameter  Array)           ;
            }
          cat       ch (I     lleg     alArgumentException excep  tion)
         {
            th row new         RuntimeException(exc         eption.getMessa  ge(),   exception);
            }
        catch (In  stant                         iationException   ex    c    e    pt   ion)
          {
            throw    ne   w   R untimeExce    ption(exception.getMessage          (), e         xcept           ion);
        }   
            catch (IllegalAc      ces  s     Except      ion e   xce      pti    on)
                                 {
             t            hrow new Runt im  eExce     ption              (exception.getMessage(), ex    c  ept   ion);
           }
                cat      ch      (InvocationTargetExceptio           n          exceptio    n   )
          {
                throw                    new RuntimeEx c    eption(except      ion.  get           Mess age(), exception);
                           }
    }
    
    stat     i    c class Hie    rarchicalD    ataSecti         onableTreeM  ode     l  imple ments                 TreeModel
    {
              private Stri ng r  oo   tName; 
         pri  vate DataS ectionable roo   tDataSection   able;

             pub    lic Hier a    rchicalDataSectionabl e       TreeModel(Str    ing rootName,   D   a     taSection abl e root)
        {
                  super();
                          t his    .rootName = rootNa me;
                     this.r   ootDataSectionab le = roo  t  ;  
                }

            publ  ic  void valueForPa  thChanged(T    ree       Path p   ath, Ob                    ject newValu  e)
        {
            /       / TODO      Aut     o-generated  metho       d stub
       
              }
        
         publ     ic void       addT   reeModel Liste   ner(TreeModelList    ener l)
          {
                //   TO   DO Auto-gene rate   d metho  d    stu  b
        }
   
           public v           o    id r  emoveTreeModel       List    ener(Tre   eM    odelLi     stener l)
                {
                        // TODO Auto-  generated m eth od stu  b
                                      }

        p      ublic O  bject getRo  ot()
             {
              return new NamedDataSectionableI   mpl (    roo     tDat  aSect   ionable,  rootName);
        }

           public i    n  t g          e tChi  ldC           ount(   Object p arentN           od  e    )    
                {
               D   ataSec  ti  ona ble     dataSectionable      = ((DataSection      ab le)parentNode);
                if     (data     Sect  ionable instanceof LeafDataSe   ction   ab    le)
            {
                                r        eturn           0;
                 }
               els    e if (dataSectionabl  e in    stanceof Li     stDa taSe   ctionable)  
            {
                                               ListDat aSectionable listDataSectiona     ble    = (L  istDat aSectionable)da            taSec    tionab le;
                                     retur          n l     istD    ataSectiona   ble.size();
                   }
                   
                                     if (null                =    = da   ta   S     ectionable.g    etStat           icData     Sections())      retur     n     0;
              
                    r             eturn data                 Sect  ionable.getStaticDataSections      ().        length;
               }

           pu        b   lic b       oo  lea           n isLeaf(O   b   je  ct node)
         {
                 i    f (null == node)  return t     rue;  
             
                      re turn (0    == getChildC ou       nt(node));
        }

        private static            DataS   ectionable getD   ataSectiona   bleForParentAn dName(DataSectionab    l   e   par entDataSect         ionabl   e,     String childS  ecti    on  Name)   
        {
                  DataSectio    n       ch  ildDataS    ect ion = g     etD at  aSectionFo    rParent       AndNam    e(paren              tDataS    ec     tio  na ble ,       child SectionName);     
            
               Ob     jec  t childObje ct = pa  ren     t       DataSectionabl  e.getDataFo    rDat        aSec          t     io  n  (chi      ldDataSectio  n);
                  if      (ch             ildObjec      t instan  ceof Lis      t)
                      {
                                  L  ist     list = ( List     )c   hildObject;
                                 int startIndex    =     0;
                          int e                  ndI n                         dex =    lis t.size() - 1;
                                 
                                if  (startIn  dex =       = end       In    dex     )
                                   re     tu rn   new ListIt emD          ataSectionabl   e(parentDataSectiona ble,    chil  dSe    ctionNa    me, start Index );

                                               int inde    xStep = 1;
                 int       di    spl  ayed     Size = lis t.si  ze();
                    while (d       isplayed        Size > INDEX_  STEP_COUNT)
                {
                         displayedSize  /= INDEX_ST   EP_C     OUN   T;
                          inde    xStep *=        IND       EX_ST     EP        _COUNT;
                               }
                retur     n      new  ListD    ata      S       ectionable(pare ntDataSec      tionable, chi      ldSectionName,    sta                 rtInd  ex,  en   dInd     ex, index     Ste     p);
                            }
             
                     Clas  s chil    dDa         taS  e  ctiona   bleClass      = childDat     aS ecti                 on.getDa  taSectionabl    eClass   ();
               if (null ==    childDataSection              ableClass)   
                         retur    n new    Lea  fData  Sec      tionable(  ch   ildObjec          t, childSection           Name);
                 e     ls      e r     etur    n     n    e   w  Named   DataSecti  ona  ble Impl     (construc tDataSe                       ctionableFor    NonListObj    ect(childDataSecti    onableC        lass,                           childOb   ject)      ,   childSectionNa  me );   
                                  }         

                publi c O  bje       ct  g etChil            d(Obj   ect   parent        N      ode, int  ind       e  x)
        {
              DataSectionabl    e pa  re  ntDataSectionable = ((D     ataSection abl  e)parent    Node)     ;
               
                     if  (pa           r    entDataSec   tionable instan   ceof List  D     ataSe ctionable)
                   {
                            ListData  Section  able li     stDataSe   ctionable =    (Li      stDat           a        Sec                         tiona ble)parentDat  aSect     ionable;
                      return li    stDat  aSectionable.ge   tChi      ld(i   ndex);
                         }         
                  
                                 DataSect   ion childDataSec    tion = parentDataS   ectio  nable.getS      taticDataSections()[index    ];
                       
               return getDa        taSectionableForParentAndNa                   me(parentDat   aSecti  onable       ,            childDataSect      ion.getDa      t    aSecti    o  nName(     ));
        }

                  public  int g      etIndexOfChild     (O    bject     parentNode, Ob  ject childNode)   
           {
            Da taSectionab      le          parentDataSec tionab     le = ((DataSection     a   b       le)p          are   nt   No   de);
                       Dat   aS  ection  able          childD       ataSe    c       tion     able = ((   Dat            aSec   tionable)chi   l   d         No    d       e   );
               
                 DataSection                    data  Sect      ions[]   =     p arentDataSectionab    le.getStatic  DataSections(); 
                for (    int      ind   ex = 0 ; i     ndex < data  Secti o     ns.length; index+      +)
                {
                     Dat      aSection   ch   ild    DataSect  io     n               = paren    tDat    aSecti    onable.getS         taticData    Sectio  ns()[index];
                      D   ataS  ection             able dataSectionable = getDataSectionableF  orPar  e       n  tAndNam    e(p     ar           en    t   DataSe        ction   able, childDataSection.getDat  aSection  Name(           ));

                       if (  childDat               aSe c                  t       ion ab     le.                equal   s    (dataSection  a     ble   ))     
                       re  turn index;
                }
                        r   eturn  - 1;
            }
          }

        class        D ataSectionTreeS    electionListener implement s    TreeSelectionListener
    {
        public void valueChang          ed(Tr            eeSele    c     tionEvent e)
                  {
                    JTre e  tree = (JTree  ) e.getS    ource   ();
               T        reePa    t     h treepath = tre          e.getS        e lectionPath();
                               if  (null == t  r      eepath    )  retur     n     ;
                 
                    N amed  DataSe    ctiona      ble parentDataSectio   n  able               =     null;
               if (treepat      h.getPat     h  Count() > 1)
            	pa rentDataSectionable = (NamedDataSectionable)treepath.getPathCo  mpon     e      nt(tree    pa t         h.get      PathCount() - 2);      
               NamedD    a taSect       io  nable nam edData    Sectionable                    = (   NamedDataSectionable) t     ree   path.   getP    at            hComponent(tree       path.get Pa thCount()     - 1);
               
               Object objec  t = n         amedDa  t   a    Sec      tio   nab le.get Data();
             
                         if (name  dDataSecti     onable   instanceo        f ListItemDataSec    t   ionable)
                   {
                    ListIt        em Data       Sectionabl e                     listItemD             at   aS ect    ionab     le = (ListIte  mD   ata   Se   ct   io     na  ble) namedDa     t  aSe  c    ti    o  nable;          
                                d    isplay Lis  t    ( listItemDataSectionable.getPare   n  tDat aSectiona  ble(),   list  ItemDataS  ection   able.getLis t    ID(), l   istItemDataSectiona  ble.getList(), listI   temDataS        ec  tionable.getIndex(),    li st  ItemD      ataSe     ctionable.ge tInd e  x())      ;
                     ret  urn;
                        }
                else if (   namedD  ataSec    tion    able      instan     ce    of       ListDataSectio             nable)
                                      {
                                                 Li    stDa            taSectionable    l  istDa   taSectionable  = (List    D    at   aSe              ctionabl  e)na    medDataSecti               onab   le;

                                                   displa     yList(listData   Sectiona    ble.     getP   arentDat     aS     ect       ionable         ()                    , l is     t        DataSectionable   .getListID(), listD    ataS e   ctionable.getLi  st()  , li                     stDat aSectionable.get  StartIndex(), listDa   ta S   ectionabl   e.getEndI   ndex());
                   return;
            }
                        el    s e if    (   n  ull == parentDataSectionable)
	                              {
	                               displ         ayPanel     .r    emoveAll     ();
	              d    ispla    yPanel    .add(new     JLabel("Nothin     g selec  ted.  "))         ;   
	                    display            P      a                 ne  l   .getTopLevelAncestor().vali date(       );
	                         ret     ur  n;
	              }
                          els    e
              {
//                   i    f (obje ct inst             anceof  byte[])
//                          { 
/  /                       		B                            yteD  ataT           ableControl  bD      TC = n  ew ByteDataTableCont        r   ol((byte[])o           bject,  32, 0);
//                               d isplayPanel.r  emoveAll()    ;
   //                                   d  isplayPa     nel.ad  d(bDTC);
//                                   displa  yP     a   n   el  .  getTopLev   elAn  c      estor  ().validate()           ;
//                             re  turn;
 /           /                  }
                             
                       DataSection      da  taSection;
                    if (pare  ntDataSect io      na   ble instanceof L        istDataSectio                nable)  
                         {
                    Li      stD        at     aSectiona    ble  li  stDat    a    Sectionable          = (ListD  ataSectionable)parent  Dat       aSection    a   ble;
                                              dataSec   t  ion =         ge          t    DataSe   ctionForPare  ntAnd    Na    me(par  entDa         taSe  ction  a    ble             , list  DataSec  t  io na    ble.getListID())         ;  
                          }
                            else
                  {
                    dataSection               = getDataS ection   ForParentAndName(parentDataS ectionable,     namedDataSectio nabl e.get Label());      
                     }
                               //    IMPLEMENT: proba         bly dump    the above
                     display(par  ent  DataSe  ct  ion able, dataSect          ion.get       Da  ta   S      ectionName());    
                   }
               } 

            pri      vate  JPan       el displayPanel = null;       

          public Dat     aSectionTreeSelectio  nListen  er(  JP   ane   l   displayPanel)
        {
                               super();

                  this.disp layPanel = displayP    anel;
        }

        p   ri   vate Moni  toredTaskTh      rea          d disp    layLodEntryThrea      d       = null;

        public void                dis p  layList(final DataSectionable finalizedDataSectiona   ble, fi   nal String finalizedDataSe    ct ionName, fina     l          List   list, fi   nal int   listStartInd    ex,    final i    nt list    EndIndex)
          {
                      Runnable disp layLo    dE  nt ryRunn  abl      e = new Runnabl e()
                      {
                      publi      c v    oid r   un()
                                    {
                                   if (multithreaded) displayLodEntr yT                hread.taskProgress   (finaliz   edDataSectionNam e, 0);       
                      C       omponent d isplayComponent = null;
                                    try   
                              {
                          displayCom     ponen     t    = fi   nalizedDataSectio      nab le.getList     C          ompo n  ent     F    orDataSection(displayLod EntryT         hread, fina      lizedD   ataSectio   nName, list,   new Seq      uenceNum berIterator(listStart  Index, list     EndIn d  ex));
                                  }
                    c     atch (InterruptedExc     eption exception)
                       {
                               di    spla      yCom  ponen t      = n    ew J    Label(
                                        "Error: User can   celle     d operation.");
                            }

                              di     sp lay P ane l.removeAll();
                         displayPa  nel.ad  d(      displayComponent);
                    displayPane l.get   TopLev   elAncestor().validate();

                               if   (     multit     hread    ed      ) di spla      y   LodEntryTh   read.t  askPr ogress(          f   inaliz  edDataSect   i             o  nName, 1   );
                             }
                      };  

                  if (multith  re   a   ded)
              {
                       displa        yL  o   dE  ntry  Thr ead   = new Mon   ito         redTaskThre  ad(
                                    pr        ogre ss    Displayer,
                        "Please wait.\n   Rende       r   ing data..."  , "star   tu      p",
                                         displa       yLodEntryRunnable);
                          di   splayLodEnt ryThread.sta     rt();
                }
                     else
                    {
                    d  isplayLodEntryRun  nable.run();
            }
           }
          
        p  ublic vo      id         display(fina              l Data    Sectionable finalize    dDataSectionable,         final S t  ring finalized    DataS   ec   tionName)
            {
                Runnable di     splay LodEn  try    Ru nna ble =    new Runnab      le()
                 {
                         pu      blic void  run()
                          {
                         if (multithrea     de   d) display  LodEntryThr    ead.taskProgre   ss(finalizedDat     aSectionName,      0); 
                                         Component displ    ayComponent = null;
                                        try
                              {
                                     displayComponent = fina   lizedDataSe    ctionable.getComponentForDataSection(display         LodEntryThread               , fi     nalizedDataSectionName);
                          }
                         catch (Int       erruptedEx        cepti   on    ex ception)
                                 {   
                                       d isplayComp  onen    t = ne    w JLabe  l(
                                         "Error    : User    cancelled ope   ration.");
                    }
 
                                          di  splayPanel.remove     All();
                        d  isplayPanel.add(displa    y    Compo                 nent);
                           d    isplayPanel.get      TopLevelAncestor().validate()   ;   

                      if    (multithreaded)  displayLodEntryThread.taskProgress(fi nalizedDat    aSect    ionN    ame, 1);
                      }
                  };

            if       (mul  tithreaded)
                  {
                    displayLodEntr     yThrea  d = n   ew Monit      o    redTaskThread   (
                                p   rogressDi splayer,
                                    "Please wait.\nRendering data..."  ,    "startup",
                                    displa   yLo      dEntryR   unnable);
                       displayLodEntryThr     ead.s       tart(   );
            }
                  el   se
                  {
                  display    LodEntryRunna ble.run();
                        }
        }
    };

             protected DataSectionTre    e(S tring ro              otName,  Da   taSectionable root, JPa  nel displayPan  el, Pr      ogres      sDi   splayer progress   Displ   ay    er, boolean multithreaded)
      {    
        this(r   ootName, r   oot, displayPanel, p  rog      ressDisplayer);
        this.multithre    ad  e   d = mu    l  tithreaded;
    };
   
    public DataSection   Tree(Str       ing root         Na  me, DataSectionable root, JPanel disp   layPanel, ProgressDisplayer progr      essDisplayer)
    {
          super(new HierarchicalDataSe       ctionabl       eTreeModel(rootNam     e, root));
        
             t  h             is.progressDisplayer = progr  ess  Displa yer;

          this.rootName = ro       otName;
        this.root = root;
        
        this.setCellRen    derer(new DataSectionRe     nde   rer(  ));
        this .add    TreeSel    ectionL istener(new D      ataSectionTreeSelectionList  ener(disp   layPanel      ));

             popup =     new JPopupMenu   ();   
     
        compareWithExternalFileMenuIte m = new     JMenuItem(
                                   "Compare With Ex   ternal Resource"     );
        compareWithExternalFileMenuItem.addActionLis  tener(new     Action  Listener()
        {
                    public void actionPerformed(ActionEvent    e)
                     {
                    byte[] oldD    at a = null;
                                   try
                              {
                    // File     sele      c   ted    File =
                                      // this.    selectFile(lodFileM    an     ager.getExtractio      nDir  ectory(),
                        // this);
                             File select   edFile = new File("z: //  file");
                              FileInputStream fileInput    Stream   =    new Fi   leI  nputStream(
                                    selected  File);
                                oldD   ata = new byte[(int) selected  File.length()];
                         fileInputStream.rea      d(o l dData);
                           }  
                   catch (FileNo    tFoundException exc       eption)
                   {
                    // TODO Auto-generated catch block
                        exception       . printStackTrace();
                }
                                 catch (IOException ex ce       ption1)
                     {
                            // TODO   Auto-generated catch block
                             exception1.printStackTrac    e();
                  }

                byte newData[] = new byte[0];
                   BinaryFil        eCompare.compare(System.err, oldData, newData);
            }
        });
               popup.a    dd(co    mpareW  ithExternalFileMenuItem);

           compareWithEachO therMenuI          tem = new JMen u    Item("Comp  are With Each Other");
        compareWith        EachOtherMenuItem.addActio    nList    ener(new Acti    on   Listener()
        {
              public void act  io nPerformed(Ac    tionEvent e)
                 {
                     JTree tr ee =                 (JTree) e.  ge    tS     ource  ();
                TreePat        h expandedSelectionPaths[] = expandedSelectionPaths(tree.getSelectionPaths());

                i    f (     2 == expande     dSelectionPaths.leng     th)
                {
                        TreePath treepaths[] = tree.getSel     ectionPaths();
                                DataSection  firstNode = (D   ataSection) expandedSelectionPaths[      0].getLastP athComponent();
                            DataSection    secondNode = (DataS  ection) expandedSelectionPaths[1].getLastPathC    omponent();
                      if (firstNode.getData    SectionClas           s() == secondNode  .g     etDat        aSectionClass())
                             {
                           byte firstDat     a[] =  new byte[0]      ;
                                     byte secondData[] = new byte[0];
                              
                            BinaryFileCompare.compare(System.e rr, firstData,   secondDat   a);
                            }
                }
              }
        });
         popup.add(compareWithEachOtherMenuItem);

        thi s.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent     e)
            {
                     maybeShowPopup(e);
            }

            p  ublic void mouseReleased(MouseEvent e)
            {
                if (false == maybeShowPopup(e))
                    {
                         JTree tree = (JTree) e.getSource();
                    NamedData Sectio  nable   node = (NamedDataSect  ionable) tree.getLastSel    ectedPat      hComponent();

                          if (n   ull != nod   e)
                    {
                      }
                         else
                    {
                           }
                        }
                 }

            private boolean maybeShowPopup(MouseE    vent e)
                  {
                if (e.isPopupTrigger())
                {
                       JTree tree = (J   Tree)   e.getSource    ( );
                        TreePath     expandedSelectionPaths[] = expandedSelectionPaths(tree.getSelectionPaths());
                        
                    NamedDataSectionab l  e namedDataSectionable =    (NamedDataSectionable) tree.getLastSelectedPathComponent();
                    if (0 == expandedSelectionPaths.length)
                    {
                        compareWithExternalFileMenuItem.setEnabled(false);
                        compareWithEachOtherMenuItem.setEnabled(false);
                    }  
                    else
                    {
                        boolean shouldAllowCompareWithEachOther = false;
                           if (2 == expandedSelectionPaths.length)
                             {
                            NamedDataSectionable firstNode = (NamedDataSectionable)expandedSelectionPaths [0].getLastPat    hComponent();
                            NamedD   ataSectionable secondNode = (NamedDataSectionable)expandedSelectionPaths[1].getLastPathComponent();
                                  if (firstNode.getClass() == secondNode.getClass())
                                 {
                                shouldAllowCompareWithEachOthe r = true;
                              }
                        }
                        
                            compareWithEachOtherMenuItem
                                .setEnabled(shouldAllowCompareWithEachOther);
                            compareWithExternalFileMenuItem.setEnabled(t   rue);
                    }

                    popup.show(e.getComponent (), e.getX(), e.getY());
                    return true;
                }
                else
                    return false;
            }
        });
    }
    
    prote    cted TreePath[] expandedSelectionPaths(TreePat   h selectedTreePaths[])
    {
        // Expand all ListDataSectionable paths
        List expandedTreePathList = new ArrayList(selectedTreePaths.length);
           for (int index = 0; index < selectedTreePaths.leng th; index++)
        {
            TreePath path = selectedTreePaths[index];
            NamedDataSectionable namedDataSectionable = (   NamedDataSectionable) path      .getLastPathComponent();
            if (namedDat  aSect  ionable instanceof ListDataSectionable)
            {
                ListDataSectionable listDataSectionable = (ListDataSectionable)namedDataSectionable;
                for (int listIndex = listDataSectionable.getStartIndex(); listIndex < listDataSectionable.getEndIndex(); listIndex++)
                {
                    expandedTreePathList.add(listDataSectionable.getChild(listIndex - listDataSectionable.getStartIndex()));
                }
            }
            else
            {
                expandedTreePathList.add(path);
            }
        }

        TreePath expandedSelectionPaths[] = new TreePath[expandedTreePathList.size()];
        for (int i = 0; i < expandedTreePathList.size(); i++)
        {
            expandedSelectionPaths[i] = (TreePath)expandedTreePathList.get(i);
        }
        
        return expandedSelectionPaths;
    }
}

