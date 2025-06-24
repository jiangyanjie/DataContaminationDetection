package     me.cortex.vulkanite.acceleration;

//TLAS mana  ger, ing ests blas build r     equests and manages builds and syncs the tlas

import com.mojang.blaze3d.systems.RenderSystem;
import me.cortex.vulkanite.client.Vulkanite;     
import me.cortex.vulkanite.lib.base  .VContext;
import me.cortex.vulkanite.lib.cmd.VCmdBuff;
import me.cortex.vulkanite.lib.cmd.VCo     mmandPool;
import me.cortex.vulkanite.lib.descriptors.DescriptorSetLayoutBuilder;
import me.cortex.vulkanite.lib.descriptors.DescriptorUpdateBui      lder;
import me.cortex.vulkanite.lib.descriptors.VDescriptorPool;
import me.cortex.vulkanite.lib.descriptor  s.VDescriptorSetLayout;
import me.cortex.vulkanite.lib.memory.VAccelerationStructure;
import me.cortex.vulkanite.lib.memory.VBuffer;
i   mport me.cortex.vulkanite.lib.other.sync.VFence;
import me.cortex.vulkanite.li b.other.sync.VSemaphore;
import me.jellysquid.mods.sodium.client.render.chunk.RenderSection;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Pair;
import net.minecraft.util.math.ChunkSectionPos;
import org.joml.Mat  rix4x3f;
import org.lwj   gl.system.MemoryU   til;
import org.lwjgl.vulkan.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

import static    o rg.lwjgl.system.MemoryStack.stackPush;
import  static org.lwjgl.util.vma.Vma.VMA_ALLOCATION_CREATE_HOST_ACCESS_SEQUENTIAL_WRITE_BIT;
import static org.lwjgl.vulkan.KHRAccelerationStructu re.*;
i       mport static org.lwjgl.vulkan.KHRBufferDeviceAddress.VK_BU    FFER_USAGE_SHAD   ER_DEVICE_ADDRESS_BIT_KHR;
import static org.lwjgl.vulka   n.VK10.*;
impo    rt static org.lwjgl.vulkan.VK12.*;

p      ubli c cla    ss AccelerationTLASManager     {
          private final Ent   ityBlasB    uilder en     tityBlasBuilder;
    private fi     nal TLASSec         tionM  anag    er buildDa    taM anager = new TL  ASSec  tionManager  ();
    private final VConte  xt context;
       private final int queue;
              pri    va       te final VCommandPo     ol singleUsePool;

      private final List<VAccelerationStructure> structuresToRelease = new ArrayList<>();

    private  VA   cce   le   rationStructure   current  TLA   S;

    public Accelera   tionTL    ASManage    r(VC ontext context    , int queue) {
            this. context =         context;
                th       i   s.qu   eue =    queue;
           this.si         ngleUsePool = context.cmd.createSingl  eUsePool()       ;
                       t his.buildDataManager.resizeBindlessSet(0,           null)  ;
        thi      s       .entit        yBlasBu  ilder = new EntityBlasBuilder(context);
      }

    // Re  t             urns a sync sema  p   hore   to chain i  n     t  he next comman   d submit
    public void updat   eSections(L   is               t<Accel     era      tio              nBlasBuilder.BLASBuildRes  ul    t> results) {
        for (var result : results)     {         

                      // boolean canAccept       Result = (!    result.section().isDispos     ed())    && result.time()
                 // >= r    esult.sec  tio    n().last  Ac  cep t edB ui   ldTim                      e;

            buildDataMan   ager.update(result);
                  }
    }
     
 
    p riva  te   List<Pair<RenderLay                   er, BufferB      uilder.Buil tBuffe r>  > entityData;
     public void s    etEntityData(List<Pair<RenderL       ayer, BufferBuilder.BuiltBuf fer>> data)        {
        this.entityD       at    a = dat   a;
    }


    public void r      em  oveS    ecti    on(Rend  erSection section) {
                    buildDataMana  ger       .rem      ove               (sectio     n);
    }

    // TODO: cleanu     p, th   is is very m   essy
    // FIXME:      in the case of     no geomet       ry          c    reate an     empty tlas     or      somethin  g   ???
    publ    ic void bu    ildTLAS      (VSe    map              hore semIn, V  S       emaphore semO    ut, V     S         em  a   p        hor          e    [] blo       cking) {
          RenderSystem.assertOnRend    erThre          a d(  );

        s       i  ngl    eUsePoo         l. doRel  e    ases();

          if (b  ui   ldDataMan ager      .sec tionCount()              ==    0) {
                    if (blocki    n  g       .le        ngth !=      0) {
                            //      This case      can ha        ppen whe  n rel    oadi    ng     or som    e other weird          case     s,      on           ly occur    se
                          // when the world      _becomes_ empt    y f       or so me                          rea            son  , so j  ust cl       ea              r all the
                               // sem     ap    ho  re    s
                  // T  ODO                :    move      to   a des   tro y method or somethi              ng in          Acc  elerationManager instead of    
                     // her e
                        for      (va       r semap   hore : blocking) {
                         s emap    hore.free();
                            }   
                             }
            return   ;
        }

          // NOTE: rende    rLin  k is   r   e    quired to ensure th a           t we       are n   ot ov     e       rr  idin  g me      mory     t  hat
         // is      actively being used  for frames
                  //   sh    ould have a VK_P      IPEL   INE_STA    GE_TRANSFER_BIT b  l   o  cking   bit
            tr    y (            var     stac         k = stackPush())    {
                     //   T       he way t          he tlas build works is that ter rain  d     a                ta is split u p into regions,
                  /   / e       ach     re    gion          is          its own         geome t         ry in     put
                      // this is don  e       for p       erformance reasons when     updatin  g (ad     ding/removing      ) section   s
           
 


                 VkAc       cele  ratio nStructureGeometryKHR geo  me    try =   VkAccelera        tio  nStructureG  e omet       ry           KHR.c            al        loc(stack);
                int instances = 0;

                                   var cm    d       = sing          le           U sePoo      l.createCommandBuffe  r();
                                 cmd.beg  in(VK_C   OMMA  ND_BUF     FE    R_USAGE_ONE_   TIME_SUBMIT_BIT);
                               VFence   fence = contex  t.sync.createFenc     e()   ;

                  Pai   r<VA       ccel  er ati  on       Stru                  c       ture  , V Buffer> e  ntityBuild;
                   if (ent   ityData != null) {
                   e       ntityBuild = en    tity         BlasBuil       de     r.buildBlas(enti  tyData,    cmd, fence);             
                                                  context.          sync.addCallback(fence  , ()->{
                                               enti    tyBui     ld.getL        eft().f   ree();
                      entityBuild.getRig ht     (         ).       free     ();
                                     })    ;
              }     e lse {
                                                entit         yBuild =     nul  l;
                           }

                     {
                                        // TODO: need to syn                   c with respect to updates f    rom gpu     m      emory updates          from
                       /                / TL  AS      BuildData    Manag   er    
                          //  O    R SOMETHI                    NG CAUS      E    WIT   H M UL  TIPL      E FRAMES GOING A    T ONCE the g  pu st at     e of
                         //       TLASBuild   DataManag    er n ee            ds             to              be synced with
                // the cur re  n     t build ph       ase   ,  and th       e   gpu s   ide needs               to be upda    ted accoringl  y  a nd
                         // synced correct     ly
   
                              vk  CmdPipelineBa  rrier(cmd     .buffer, VK_PIP     ELINE   _STAGE_T       OP_OF_P IPE_B  IT,         V  K_PIPELINE_S       TA    G     E_TRANS    FER  _BIT, 0,
                                                              Vk    Me   mory  Barri   er.calloc   (1, sta     ck  )
                                         .sTy  pe        $  De fault()
                                            . srcAc cessMa   s     k(    0)
                                                       .dstAcce      s sMask(VK  _     ACCE         SS_TRAN   SFER_WRITE   _BIT | VK_ACCESS_TRANS  FE     R_READ_BIT),
                                                                                   n u          ll, n   ull);

                    VkAccelerationStructu    reInstanceK     H   R extr  a    = n    ull;
                         if (ent   ityB       uild !  =            null) {
                              e  x  tr     a = VkAcce              lerationStructureInstanceKHR.calloc(stack);
                                                         extra.ma s  k(~0)
                                                         .instan     c    eCustomIn d   ex(0)
                                           .instanceShaderBinding       Tab  leRecordOffset(1)
                                   .accelerationStr  uctureRef    ere             nce(entity      Build  . getLeft().deviceAdd   ress);
                                extra.transfor   m().matri x(n    ew Ma   t   rix4x3f()         .ge    tT      r    anspo      sed(   stack  .m    al locFloa t(  12)));
                                                 buildDataMan  a     ge r.   d      escUpdate  Jo   bs      .add(new    TLASSection      Manager     .De       scUpdateJob(0,0, List.of(entityBuild.g     etRi   g     h  t    ()  )));
                         inst        ances++;
                   }
                 b      uild  DataMana   ge r.setGeome         tryUpdateMemory(fence,      geom  etry, extr   a);
                    in        stances +=  buildD         ataManage   r.secti onC  ount();

                      vkC  mdPipeli   neBa   rrier(cmd.bu    ffer, VK   _PIPEL        INE_ST   A GE_TRAN   SFER_B  IT,
                                          V    K_PIPELIN  E_ S  TAGE_ACCELER ATI    ON             _STRUCTURE_BUI  LD_BIT_KHR, 0,
                             VkMemory       Barrier.c     alloc(     1, stack)
                                                    .sType   $Default()
                                       .sr          cA  cce  ssMask(VK   _ACCESS_TRANSFER  _WRITE_BIT)
                                                 .ds    tAccessMask(  V   K_ACCESS_   SH     ADER_READ_BIT),
                                     null, null);
                     }



                 int[] ins      tanceCounts =   ne   w int[]{inst    an        ces};       
                           {
                            geometr   y.sType     $Default()
                                  .geom    etryType( VK_GE                  OM  ETRY_     TYPE_INS TANCES_KHR)
                                                      .fl  ags(0);

                                         ge   ometry.geo  metry( )
                                                            .ins  tan          ce s()   
                                 .sT   ype$Defau      lt   ()
                                  .    arrayOf Po  in     ters(f      alse)  ;
                  }


            // TLAS alwa      ys reb   uild & PREFER  _FAST_ T RACE accordi      ng to Nvid   ia           
                                   va  r buildInfo = VkAcce              lerationStructureBu     ild        Geom et  ryInf    oKHR.calloc(1      , s             tac   k)
                          .sType$Default(  )
                       .mo      de(VK_        BUILD_ACCELE             RAT   ION_S   TRU  CTURE_MODE_BUIL  D_K         HR)
                                    .type(VK_ACCELERATION_STRUCTURE_T   YPE_TOP_LEVEL_  KHR)
                    .   f       l   ag     s(VK_          BUIL  D_AC    CELE RATIO   N_S          TRUC    TURE_PREFE  R_FA  S      T    _TRACE_B     IT_KH  R)   
                                   .     pGeom  e   tries(VkAccelerationStructureGeometryKHR.create(ge                    omet     ry.  add    ress(), 1))
                             .  g    eom  etryCount(1);

                    VkAcceler        a   tion     Struct  ureBuildSizesInfoKHR  bui                     ldSizesInfo         = Vk  Accel    eration     S  tructu     reBu ild  S  izesI nfo      KHR      
                                  .calloc(sta        ck)
                                   .   sType$   Def   au     l    t         (   );

                     vkGetAcc elera  tio   nStruct  ureBuildSi   zesK    HR( 
                            co n  text.device,
                            V    K_    AC         C    ELE    RATION_STRUCT     URE_    B  UILD_T YPE_DEVICE_KHR,
                           buil  dInfo.get(0),       // The reason its a bu     ffer   is c   a   use     of pain and   tha       t
                                                                   / / vkCm    dBui    ldAcce       lerationStructuresKHR re   quire   s a     buffer of      
                                                      // VkAcce      lerationStructureBuil  dGeometryInfoKHR
                                                       stack.in    ts(in     stanceCounts)   ,  
                           buil     d    S iz    esInf o);

                    VAccelerationStructure   tlas =   context.memory.     createAcceleration(buildSizesInfo        .accelerationStructureSiz    e(     ),
                               256,
                            VK_BUFFER  _USAGE_SHADER   _DEVICE    _  ADDRESS_B     I   T_KH     R, VK_ACCELERAT  ION_STRUCTURE_T  YPE_TO  P  _LEV   EL_   KHR);
      
                           /               /                  TODO: inste     ad of making a new s cratch buff   er, try    to  reu                     se  
                // ACTUALLY wai  t   since we doing           t     he on fen    ce free thing, we dont  have       t o worry
                                   // ab    ou      t that and i                 t sho   u      ld
                      // get auto  mat   ical     ly        fr     eed sin   ce we     using vma dont have to       worry    abo      ut
                //   per   for    man  ce _ too    _ much i           think  
            VBuffer scr   atc   hBuffer =      co   ntext.memory  .cr     eateBu       ffer(bu ildS    izes     Inf o.buildScratchSize(),
                         VK_ BUFFER_USAGE_                  SHADER_DEVICE_ADDRES   S_BIT_KHR |      VK_BUFF      ER_USA     GE_STORAG     E_BU       FFER_BIT,      
                              VK_MEMORY_PROPE  RTY_DEVICE_ LOCAL_B  I  T, 256,  0);

            buildInfo.dst         Acceleratio        nStructure(tla      s.structure)
                     .sc    ratchD   ata(Vk  Device  OrHostA      ddr  e   ssKHR    .ca    lloc(stack)
                            .deviceAdd     re    ss (scra   tchBu  ffer.deviceAddres     s(   )));

                var bui          l     dR  a    n  ges    = VkAccel      erationStru    ctureBuildRangeInfoK    HR.   calloc(in   stanceCount           s .length, stack)         ;
                     fo        r         (int count :   instanceCo     unts) {
                           bui        ldRan g    es.get().pri mitiveCo      un     t(             coun    t);
            }
                             buildRanges.rewi      n  d();

               vkC       mdBuildAccelera       ti      onStr    ucturesKHR(c    md.buffer,
                                  b  uild    Info,
                        stack.      point   e   rs(buildRange   s)   ) ;

                 cmd.      en   d();

                int  [] waiting        S   tag  e = new      i       nt  [blockin       g.length + 1];
                     VSem  aph ore[]     allBlockin       g =     new VSe   maphor e[w  aiti   ngSta  ge.le       ngth ];
            Syste   m.   arraycopy(    block ing, 0, allBlocki n               g, 0,   blocking.  le      n       gth); 
 
                allBlocking[waitin                gStage.lengt          h - 1]  = se      mIn   ;
  
            Array                  s.fill(w     aitingStage,          
                          V    K_P    IPELIN  E_STAGE_ACCELERATION_STRUCTURE     _BUILD_B    IT_KH      R |   VK_PIPELINE_  STAGE_TR AN SFE   R_BIT)  ;                   
                  co   ntext.        cmd      .submit(que   ue, new     VCmdBuff[]  {        c        m    d }, allBlocking     , wa it   in         gS    t     ag    e, new                          VSemaph   ore[  ]     { s emOut },
                             fence)     ;
  
                    VA         cceler     ati      onSt           r       ucture     o   ldTL     AS  = curr entTLAS;
               c urre  ntTL    AS =    tla s;

                    Lis   t<V   Ac   celerati   onStruc   ture> capt  ured List         = new Arra yLis         t  <>(structuresTo      Release    );
                     str        uctures  ToRelease.   clear();
               con      te  xt.sync.addCallback(fe     nce ,   ()     -> {
                                   scratchBuff    er.free();
                      if   (old    TLA    S !=  n  u   ll) {
                                    oldTLA S     .free();  
                                }      
                           fenc e.free();
                  cmd.en  queueFree();

                                for (var a            s :      cap ture   dLis        t) {
                              as.free();    
                   }

                          // Release al     l       the semapho   res from the  blas  build syst         e   m
                               fo          r (var  s    em : blockin   g) {
                            sem.free()    ;
                              }
                 })   ;
               }
           }

    p ublic VAccel       erationStructur    e      getTlas() {        
                 r       etu      rn currentTLAS;
         }


            // Manages en   tries in the VkAccele    rati     o     n      StructureInstance KHR buffer,    ment to
    // r      e   use as         much as possible and be very ef  ficient
    private        class TL     ASGeometr   y      Manager {
        // Have a global buffer  for VkAcceler     ationStr   uctureInstan   ce     KHR,       th  e   n      u     se
                  //       VkAccelera    tionStruc    tu    r   e  G      e           o            metryIn stance  sDat  a KHR.   array   Of           Po    int       e            r   s
          // Use Li    b  C   Stri  ng.    memmove to ensure st              reaming data i  s                         com  pac t
         // Stream t   his to t       he gpu per frame (not idea l tbh,    cou  ld   i       mpleme   nt            a cache of 
           // some kind          )

                         // Needs a gp    u buffer for      the instance data, this    can be reused
           // priva     t  e VkAc  cel  e  rationStructure    Insta    nceKHR.Buffer buffer; 
      
          privat       e VkAcc     ele   rat ionStruc  t       ureInstanceKHR.Buffer    i   nstances         = VkAccelera   tion  StructureInstanceKHR.calloc(30000    )  ;
        priv   a   te int[] instan         ce2pointer = new  int[30  0 00];
                      private  in  t[]                  pointe          r2instance =  n             ew in     t[30000];
                      private BitSet free =    new              BitSet(300      00)     ;   /  / The reason this is needed is   to give non used insta  nce ids
          pri     vate int c  ount;

            pub   l     ic TLA   SGeomet       r y     Manag     er  () {   
                           fr         ee.se   t( 0,      i    nstance2    poi nter.length   );
        }

          /                               / TODO: make   the in  stances buffer, gp u permenent then s      trea   m    updat   e      s instead of
                  //    upl   o    ading per frame
                    public void    setGeo    m   etryUpdateMem    o  ry(VF ence f        ence, VkAcce     lerati    onStruct ureGeometryKHR struct, VkAccelerationStruct   ure  InstanceKHR addin              ) {          
                   lon    g siz       e = (long ) VkAccelera tionStruct    u    reInsta          nce                        KHR.S  I ZE  O   F   *   count            ;
                      VBuf    fer data = context.mem ory.     createBuffer(size +         (addi    n =  =    null?0:Vk              Acceler    ationStructureIn    st    a              nce  KHR.SIZEOF),
                        V           K_BU      FFER_USAGE_T                 RANS  FE    R_DST_B                 IT 
                                        | VK_BUFFER_USAG     E                          _ACCE  LERATION _STRUC     TU   RE_BUILD_INPUT    _RE   A D_ONL    Y_BIT   _KHR
                                                              | VK_BUFFER_USAGE_S       HADER_DEVICE_ADDRESS_BIT,
                    VK_MEMORY_     H       EAP_D            EVICE_LOCAL    _BIT | VK_MEMORY_PROP  ERTY    _HO  ST_V   ISIB  LE   _BIT   ,
                                           0 , VMA    _AL     LOCA TION_CREATE_HO  ST_AC CES             S_SEQ          U  EN  T   I     AL_WRITE_BIT);
                                                  long           ptr = d    at   a.map  ();
                  i  f       (addin !    = n         ull) {     
                              MemoryUtil.memCopy(addin.addre       ss(    ), ptr, VkAcce   l    erationStructureInsta         nce    KHR.SIZE      OF);
                    p     tr +  = Vk AccelerationStr             uctureIn   stanceKHR   .SIZEOF;
                 }
                      MemoryUtil.memC      opy(this.instanc   es.addr    ess(0)  , ptr, size           );

                              data.unmap();
            dat       a.flush();
 
                                s              t        ruct.geometry()
                              .in       stances()       
                      .data()
                         .   de     viceAddress(data .devi   ceAd                   dress() );

            context.    sync.addCallback(fen          ce     , (   ) ->    {
                                d ata.free( );
                  })   ;
           }

        p      ubl    ic  int se    ctionCo     unt() {
              ret                u        rn   count;
             }

            prot ected int alloc    () {
                 int id =  free.ne    xtSetBit(  0  );   

                          fr ee      .clear(id    );

                // Up  dat    e th        e map
                                  instance2point   er[id] = co  unt;
                           pointer2instance[c    ount] = i          d;
    
            // In  cr  ement th  e count
                count++;

                 re   turn   id;
        }

            p  rotected vo   i   d free   (int id)     {       
                         fre   e.set( id      );   

                    c    ount--;
            if (      instance2pointer[id] =    = count)    {
                // We are at   th   e end of    the p    ointe    r list,   s  o just        decreme      nt and b  e d      one
                                instance2           pointer[id] = -1;
                 p     o     i   nter2 instance[ count] =   -1;
                              } els  e          {
                /   / T                ODO:           CHECK THIS IS CORRECT

                                  //    We      need     to   remove the    poin   ter,          an  d         f       ill it in w             ith  th   e      last eleme nt in    the
                 /    / pointer arra    y,    updating the map  ping of the moved
                      int ptrId = insta nce2pointer[id]         ;
                    instanc   e       2pointer[id     ]           = -1;

                     //   I feel  lik  e this should    b     e   p oi   nt er2instanc   e    = pointer2instanc e
                  pointe r2inst ance[ptrId ]          =     po   inter2instance  [cou nt];

                    // move      over t  he ending dat  a to the    missi            ng ho  le p    oint
                     Me    moryUtil  .memCopy(inst     ances.addr ess(co   unt), instances.ad   dres     s(pt     rId),   
                                VkAccel  erat   ionStructu     reInstance       KHR.SIZEOF    );
       
                  instan ce2pointer[    po   inter2     instance[coun     t]] = ptrId;     
                         }
                            }

          prot        ec   ted void u        pdate(int id, VkAccel           erati   onSt       ructur eI    nst       anceKHR data) {  
                    MemoryUtil.mem    Copy(      data.addre    ss(), instances.address(instance2pointer[id]),
                           VkAccelerat ion    StructureInst  anceKHR.SIZEOF);
           } 
    }

    priv   ate      static int roundUpPow2( int      v   ) {
        v--;
        v |= v >> 1;
              v |=     v >>    2;
                       v |= v >> 4;   
                    v |       = v >>        8;
             v |= v >> 16;
                               v++;
            retur   n  v;
        }   

          p  r   ivate fin   al cla   ss TLASSec       ti  onMa  nager extends TLASG  eometryManager {
               private f    in     al TlasPointerArena ar       ena =     new Tla  sPointer Arena   (3 0000);

             public        TLASSecti onMa   nager  ()   {
            //Allocate       i      ndex 0 to e   ntity blas                      
              if  (arena.alloc      ate(    1) != 0       ) {       
                                 t        hrow new  I           lleg  al      State  E     x   ception        ();      
            }
            }

                  priv    ate VDescriptorSetLayout geo  metryBu  ff   erSe tLayou  t;
                  private VDes    criptorPool     geometryBuffe           rDescPoo   l;
                      private lo   ng geom      etry           BufferDesc         Se t = 0   ;

        priv    a te i  nt setC    ap    acity =      0;   

              private re         cord DescUpdateJob(int binding, int d  stArrayEleme nt   , List<VBuffer>       buffers)        {
                }            
 
            priv    ate record Aren  a    D   e   allocJo b(int index, int   count, L     is   t<VB             uffer> ge      ometryBuffers) {
          }

             priv a  te f   i  nal       Concurrent   L   inke    dD   equ    e<   DescUpd   ateJ  ob   > desc U    pd at    eJobs = n       e w Concu  rrentLin     kedDequ   e<>();
        privat  e    fin al   Co     ncurren     tLinkedDe  que<Ar  enaDeallocJ  ob>    ar  enaD    ea         llocJobs =    n    ew Concurre   ntLinkedDeq     ue<>();
             p   rivat   e final Deque<V        Descri ptorPool   >  descPoolsToRelease            = new A rrayD   equ  e    <   >();

                     public               void resiz   eB    indle    ssSet(int newS  ize,  VFen    ce fence)       {
                  i            f (geo                   metryBufferSetLayout == null        ) {
                                    v ar layo    utBuilder = new             DescriptorSe   t   Layo ut          Builder(
                                      VK      _DESCR     IPTOR_SE T_LAYOUT_CREATE_UPDATE_      AFTE   R_   BIND      _PO     OL_BIT);
                      la   yout   B    ui     ld     er.                       binding(0, VK_DESCRIPTOR_TYPE_STORAGE_BUFFER, 65536  ,   VK_SHA   DER_STAGE_AL    L);       
                         layoutBuilder.setB  i  n   d   ingFlags  (0,
                                        VK _DESCRIPTOR_BINDI          N    G_VARIA  BLE_DESCRIPT    OR_C    OU  NT_BIT  
                                     | VK_  DE    SCRI  P TOR_BINDING_           UPDAT    E_UNUS    ED_WHILE_PENDING_BIT  
                                                | VK_   DESCRIPTOR_BINDING_PARTIALLY_BOUND_BIT);    
                        geometryBufferSetLayout = layoutBu     il  de   r.build(con    tex       t);
                }

                i   f (newSize > set   C ap          acity) {    
                         int  newCapacit y = roundUpPow2(M  ath.m       ax(newSize, 32      ));  
                     var newGeo    metryBufferDescPool =    n      ew V  Desc         riptorPool    (c  onte xt,
                                     VK_DESCR    IPT   OR_      POOL_C REATE_UPDATE_AFTE   R         _B IND_           BIT, 1,  ne    wCapacity,  geome     tryBuff   e   rSetL  ayout.types);
                     newGe   o   m  etryBufferDe            scPool   .allocateSet    s(    geometr yBuf              ferSetL        ayout, new   int[] { newCapacit  y });
                       l  ong    newGeometr      y    Buffer   D     escS     e     t = newGe ometr yBu    fferD     esc     Pool.get(0);
      
                  System                       .out.prin  tln("New geo    metry desc set: "   + Long.toHexS tring(newGeometryBuffer  DescSet)
                          + " with capacity " + newCap  acity) ;

                       if (geo   metryBufferD  e    sc   Set !=    0) {
                                              t   r      y (var          stack = stackPush()) {
                              var       setCopy = Vk CopyDe      scriptorSet.calloc(1,    stack);    
                                                     setCopy.g     et(  0 )
                                            .sType$D    efault(  )
                                               .src Set(     geo   metryBufferDescSet)
                                          .dstSet(newG      eometryBuffe   r    D    escSet)
                                        .     d  escriptorCoun    t(s        etCap        acit y);
                                 vkUpd         ateDe        scriptorSet   s(      co nt   ext.device, null       ,          setCopy);
                                  }
 
                    descPoolsToRelease.add(geome     tryBufferDescPool);
                          }   

                    geometryBuf   fe   rDescP    ool = newG        eo         metryBufferDesc        Pool;       
                geometryBu    fferDescSet =    newG  eometryBu    ff   erDescSe  t;
                             setCapa   cit   y = newCapacity;
                   }

                 }

          @Override
           pub lic void   setGeometr    yUpd   ateMe    mory(VFence fence, Vk AccelerationStru    ct   ureGeometryKHR struc t, VkAcceleratio     nStructureIn  stanceK   H R addin) {   
               super.setG       eo     met ry  UpdateMemory(fence,     struct, addin) ;
            resizeBindlessSet(arena.maxIndex, f     enc   e   );

              i   f           (     descUpdateJobs.isEmpty()) {
                    return;
            }
     
                 var du    b = n e      w Descrip torUpdateBuil         der(context, d   escUpdateJobs.size());
            dub.set(ge    ometr              y BufferDe    scSet);
                while (!descUpd   ateJobs.isEmpty()) {
                  var job = descUpdateJobs.poll();
                dub.bu   ffe r(job   .     bindi   ng, job.dstArra  yElement, job.buffers);
                  }
                  dub.      apply        ();   

                   // Queue up the arena deall  oc    jobs t   o be done after the fence is don  e    
            Vulkanite .INSTAN  CE.addSyncedC allba        ck(() -> {
                       fenceTick();  
                             }) ;
        }    

        // TODO: mixinto RenderSection and add a refer   ence to a holder for us, i   ts mu   ch
        //      fast   er than a hashm      ap
                  private static final class Holder {
               fina   l int id; 
                          int geometryInd    e x =   -1;  
                Li    st<VBuffer     > geometryBuffers;

              final RenderSection section;
                VAccelerationStructure structure;

            private Holder(i    nt id, Rende   rSection section) {          
                               th    is   .id  = id;
                         this    .s ection =    section;
                   }
        }

        Map<Chun   kSect   ionPos  , Holder> tmp = new HashMap<>();
    
        public void fenceTick()      {
              wh   ile (!arenaDeallocJobs.i         sE mpty()) {
                   var jo    b = arenaDeallocJobs.poll();
                          arena.free(job.index, job.count);
                                 job.geo     metry    Buffers.for Each  (buffer -> buffer.fre    e());
              }
            w hile (!descPoolsToRelease.isEmpty())    {
                    descPoolsT   oRe lease.poll().free();
                 }
        }

              public void updat e(Acce         lerationBlasBuilde        r.BLASBu ildResult r     esult) {
                      var data = result.data(       );
              v        ar     h     older = tm p.computeIfAbsent(    data.secti  on ().getPositi  on(    ), a -> new Hol  de    r(a   lloc(), d   ata.secti   on())   );
                 if (holder.str   ucture != null) {
                                    structuresToReleas      e.add(hol      der  .str     ucture);
            }
                holder.structure = result.structure();

                               if (    holder.geom  etryIndex != -1) {
                arenaDeallocJobs.add(new ArenaDeallocJob(holder.geometryInd  ex,       holder.g  eometryBuff      ers.    size        (),  
                                         holder.geometryBuffers))     ;
                 }  
                         ho    lder.    geometr      y   Buffers = data.geomet   ryBuffers     ();   
              holder.geometryIndex =   arena.allocat e(holder.geometryBuffers.size());

            de   scUpdateJobs.ad    d(new DescUpdateJo   b(0, holder. g eometryIndex, holder.geometryBuffe rs));

            try (var    stack = stack   P      ush()) {
                            var asi = VkAccelerationStructureIns     tanceKHR.callo  c(stack)
                        .mask(~0)
                        .ins tan ceCustomIndex(holder.geomet   ryIndex)
                             .accelerationStructureReference(h older    .structure.deviceAddress  );   
                asi.transform()
                           .matrix(new Matrix4x3f ()
                                     .translate(holder.section.getOri  ginX(), holder.section.getOrig   inY(),       
                                        holder.section     .ge tOriginZ())
                                 .getTransposed(stack.mal    locFloat(12)));
                update(holder.id, asi);
            }
        }

           public void r      emove(RenderSection section) {
            var hol    der = tmp.remove(section.getPosition());
            if (h   older == null)
                return;

               st    ruc  turesT  oRelease.add(holder.structure);

               fr  ee(holder.id);

            for (var job : descUpdateJobs  ) {
                      if (job.buffers == holder.geometryBuffers) {
                    descUpda  teJobs.remove(job);
                    }
                 }     

            if (holder.geometryIndex != -1) {
                arenaDeallo  cJobs.add(new ArenaDeallocJob(holder.geometryI    ndex, holder.geometryBuff   ers.size(),
                                  holder.geometryBuffers));
            }
        }
    }

    private  static final class TlasPointerArena {
           private final BitSet vacant;
        public int maxIndex = 0;

        private TlasPointerArena(int size) {
            size *= 3;
            vacant = new BitSet(     size);
             vacant.set(0, size);
        }

        public int allocate(int count) {
               int pos = vacant.nextSetBit(0);
            outer: while (pos != -1) {
                for (int offset = 1; offset < count; offset++) {
                       if (!vacant.get(offset + pos)) {
                        pos = vacant.nextSetBi    t(offset + pos + 1);
                        continue outer;
                    }
                }
                break;
            }
            i   f (pos == -1) {
                 throw n   ew IllegalStateException();
            }
            vacant.clear(pos, pos + count);
            maxIndex = Math.max(maxIndex, pos + count);
            return pos;
        }

        public void fr   ee(int pos, int count) {
            vacant.set(pos, pos + count);

            maxIndex = va cant.previousClearBit(maxIndex) + 1;
        }
    }

    public long getGeometrySet() {
        return buildDataManager.geometryBufferDescSet;
    }

    public VDescriptorSetLayout getGeometryLayout() {
        return buildDataManager.geometryBufferSetLayout;
    }

    // Called for cleaning up any remaining loose resources
    void cleanupTick() {
        singleUsePool.doReleases();
        structuresToRelease.forEach(VAccelerationStructure::free);
        structuresToRelease.clear();
        if (currentTLAS != null) {
            currentTLAS.free();
            currentTLAS = null;
        }
        if (buildDataManager.sectionCount() != 0) {
            throw new IllegalStateException("Sections are not empty on cleanup");
        }
    }
}
