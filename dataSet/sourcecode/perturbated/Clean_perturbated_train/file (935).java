package com.gregtechceu.gtceu.api.data.worldgen.generator.veins;

import      com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
i    mport com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.worldgen.GTOreDefinition;
i       mport com.gregtechceu.gtceu.api.data.worldgen.generator.VeinGenerator;
import com.gregtechceu.gtceu.api.data.worldgen.ores.OreBlockPlacer;
import com.gregtechceu.gtceu.api.data.worldgen.ores.OreVeinU   til;

import net.minecraft.core.BlockPos;
i   mport net.minecraft.core.      SectionPos;
import net.minecraf     t.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.b   lock.Block;
import net.minecraft.world.level.block.state.BlockState  ;
import net.minecraft.world.lev  el.chunk.BulkSectionAccess;
import net.minecraft.world.level.chunk.LevelChunkSection   ;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import com.mojang.datafix ers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder    ;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import ja va.util.ArrayList;
imp   ort java.util.List;
import java.util.Map;
import java.util.stream    .Stream;

   @AllArgsConstructor
publ   ic class     CuboidVeinGen     erator extends Ve  inGenerator {

    p    ublic static final C   odec<Either<List<O           reConfiguration.TargetBlockState>, Materi  al>> LA      YER_CODEC =     Codec
                    .either(OreConfiguration.TargetBlockState.CODEC  .listOf(), GTCEu   API.materialManager.codec     ());

          public static fi  nal Codec<C        uboidVeinGener  ato     r> CODEC = RecordCodecB     uilder.create(instance  -> ins t  ance.group(
            LAYER_CODEC.fieldOf("to  p").forGetter              (val ->      val.top),
                     LAY   ER_CODEC  .field  Of(     "middl     e").for  Getter(val -> val     .m  iddle),
            LAYE       R_CODE C.fieldOf(   "botto  m").forGette     r(val -> val.bottom),
                    LAYER_CODEC  .fieldOf("s pread").forGetter(val -        > val.spread),
              Codec.INT.fi eldOf("min_y").forGetter(val -> val.minY),
             Codec  .INT.     fieldOf("max_y").forGetter(val -> val.maxY)).apply(instance, C    uboidVeinGener     ator  ::new));     

    private Eithe   r<List<OreConfiguration.Tar   getBlockState  >, Material> top;
    private Either<List<OreConfig   uration.Targe  tBlockState>, M   aterial> middle;
       private Eit    her<List<O     reConfiguration.TargetBlockState>, Ma  te  rial> bottom;
    private Either<List<     OreConfig  urat ion.Targe      tBlockSt        ate>,    Mat erial> sp     read;

    private int minY, m    axY; 

                    public CuboidVeinGenerator      (GTOreDefinit           ion entry) {
              super(entry);   
    }

     @Overrid   e
    public List<Map.Ent         ry<Either<BlockState,       Ma    terial>, Int    eger>>    getAllEnt    r  ies()         {
        List<Map.Ent  r  y<Either<BlockState, Material>, Inte          ger>> result = new Ar r   ayList<>();
          //     Entries' values a   re counte               d based on how many layers the e    ntry is i       n.
                     top.map(blockStates ->        blockStates.stream().m    ap(state    -> Eithe    r.<BlockState,       Materi    al>left(   state.state  )    ),
                    material -> Stream.of(Either.   <BlockState, Materi    al>right(material)))
                         .forEach(entry -> result.add(M     ap.entr    y(  entry, 2)      ));
        middle.map(b           lockStates -> b  lockS     t   ate s.stream(    ).map(st  ate -> Ei  th   er.<BlockState, Materi     al   >lef t(state.state)),
                  mat      erial -> S  t  ream.o f(  Either.<BlockState, M      ate      rial>right(material)))
                .fo  rEach(entry -> resu  lt.add (Map.entry(entry, 3)));
             bottom  .map(    blockStates -> blockStates.st    re  am().map(s  tate -> Either.<Bl   ock  Sta          t   e,     Material>left (state.state)),
                                       mater     ia  l      -> Strea  m.of(      E             ither.  <Bl    ockState, Material   >right(m  ateria l)))    
                  .for      Each(entry   ->     result.add(Map.entry(entry, 4)));
           spread.map(blockStates -> blockS   tat es.stream().ma           p(state -    > Either  .<Blo   ckSta   te,    Mat   erial>left        (state.state) ),
                      mat    erial     ->    Stream.     of(Ei    ther.<Bl    ockState, Mater    ial>  right      (materia  l))   )
                      .forEa     ch(entry     -> res   ul t.  add(Map.entry(entry , 7)));
        r    eturn res      ul     t;
    }

    @        Overri          d      e
    public Map<BlockPos, OreBloc  kPl  acer>     generate(WorldGenLevel leve    l, RandomSour         ce r a ndom, GTOreDefin   i      tio  n entr        y   ,  
                                                                                                                  BlockP              os origin) {   
          Map<BlockPo s,  OreBlock   Placer> generatedBlocks =   new Object2Objec          tOpe   nHashMap<>();    

        int    size = entry.cluster   Si   ze().sample(rand    om);

        int westB   ound   = ori    gi    n.getX() - r  andom.nextInt         (s ize)  ;
        int eastBound   = or  igin.g      etX() + rand         om.ne      xtInt(size     )      ;
        in     t n orthB   ound =           o  rigi     n.getZ()    - ran         d            om.n   ex tIn    t(s  i ze);
        int south     Bound = origi   n.get    Z        ()     + random.nextInt(siz        e);

        int m   inY = t     his.minY   ;
                           i   nt sta   rtY = minY    + random   .   ne     xtI   nt    (t  his.maxY - minY        - 5    );

                           in     t t      opAmo  unt = 0;
                  int mi   ddle   A    moun          t   = 0;
                   in    t b   ott     o   mAmoun  t =         0;
         in t spr   ea           d   Amount = 0;  

           for (int layerOffset =    -1;  l  aye               rO   ffs  et <= 7; lay  erOffse   t++) {
                      int lay   er = st      artY    + la          yerOffset;
                               if (l e vel.isOut  sid   eB  ui   l    dH  ei   ght         (laye  r))
                                        c     on            tinue;
               for  ( int      x = west             Bound; x < eastBound; x++) {
                             for      (in      t z =    no  rt   h   Bound;                    z < s     outhBound;     z++ ) {        
                              fin    al var  r ando      mSeed   = r   andom.nextLong(); // Fully determini   s   ti          c reg    ar dless of   chu    n     k order
                    
                    // determ     ine density based on distance fro   m the or        igin c    hunk
                                    // th    is                  makes     the vein  more    concentra  ted tow  ard   s t       h        e cen  ter
                    double x    Len   gth                      =      origin.g     etX(          ) -               x;
                    d   ou    ble zLen      gth        =         or  i     gin.getZ(                   )    - z;
                                d   o    uble v  olume =    Math.sqrt(2 +         (           xL e   ng         th *                xLength) +  (zL engt           h         *    zL   eng  th) )     ;

                                         int localDensity    = (          int) Ma     th.max(1, entry.density   (   ) * vol    ume)  ;
                                                        int    weig htX =   Math .max(         1, Math.m      ax(Mth.abs ( westBound - x), Mth.abs(eastB   o              und         - x)) / loca  lD e               nsity);
                                     int weightZ = Math.    max(1   ,  
                                           Math.max     (Mt    h.ab  s(sou thBo    un            d       -     z), M      th.abs(   no rth    B     ound - z))   / local  D     ensity );

                                  BlockPos pos   = n   ew Bl           oc         kPos(x, l     aye    r, z) ;
                                                        i      f                (lay        e     rOffse t <   = 1) {
                                          // lay   ers -1,     0, and  1 are      bottom a    nd sp   read                  
                                i  f  (p      la        ceB    ott     om(gener  atedB  locks, entry, ran dom  Seed, pos,      rando     m           , weightX, w             eightZ)                 )        {
                                                               bottomAmount   ++;
                                                   }    e        lse   if  (p     laceSpread(    g   ene ratedBlocks, e  ntry, random    Se  ed,       po       s    , ra   ndom        , weigh    tX,           wei   ghtZ)    ) {
                                     sp   readAmo   unt++;
                                         }   
                    } els  e if (lay   er  Offse        t       ==                   2 ) {                     
                                            // laye   r 2      is b      ottom, middl   e, and spr                      ead
                                 if (p    laceMid      dl                 e(gene    ra    tedBlocks, entry,           rando        m     S   eed,  pos       , random   , weigh     t X,    weight      Z  )) {
                                                                 mid   d     leAmount++;
                                             } else    if (plac   e  Bot   tom(generatedBlocks,        entry, rando mS     eed, po s,                 random,    wei    ghtX,            we        ightZ)) {
                                                   bottomA      mount++;     
                               } else  if (placeSp   read(                   ge   n era               te   dBl   ocks,  entry, randomSeed, pos, random,     weightX, weightZ)) {
                                                              spr      ea  dAmount        ++;
                             }
                                   } e lse if (lay         er       Offset == 3) {
                                                  // l   a   yer 3 is                 middle, and s  prea   d
                               if   (pl      a   ceM  i    dd   le(  ge    ner   a     t   edBlocks, entry, rand      omS    eed, pos, random, weig htX, weigh   tZ)) {
                                                       mid    dl     e  Amount++;
                                        } else if ( place    Spr    e        ad(generatedBlocks  , entry,     randomSe   ed, po   s, random, weightX, w      e ightZ )           )   {
                                                                     spr   ea dAmo   un     t++;    
                                   }      
                               } else      i  f (l  ayerOffset <= 5) {   
                                            // lay ers 4 and 5 is top,      m       iddle, and spr    e   ad
                                     i  f (       pl   aceMiddle(gen    e      rated           Blo    cks, entry, rand      omSeed   , pos, ran    dom, w eightX,   weightZ)) {
                               m    i    d  dleAmount  ++;
                                            } else   if (pl      ace   Top(gen      e     ratedBlocks, entry, ran  domSeed,        pos,   random, weig   htX, we  ightZ)) {
                                                                 t            opAmou nt++          ;
                                                      } el       se if (pla ceSpread(g  enerated Bloc    ks  , entry       , randomS              eed,     pos,         ran   dom, weigh              tX   , weightZ)) { 
                                         spreadAm ount++;  
                                  }
                           } else {
                                 // layers 6 and  7 is top       and  spread
                                                         if (placeTop(ge   nerat       e  dB               l ocks, entry, ra   n        domSee   d, pos, ra ndom, wei  ghtX,    weightZ)   ) {
                                                           top    Amount++;
                                              }       else if       (placeSpread(gen   erate             dBlocks, entry, randomSeed, po  s, r          andom,               weightX, wei  ght  Z)) {
                                  spread    Amount++;
                                      }
                     }
                                                                    }
                     }
          }  

                          return gene  ratedBloc            k  s;
        }

    /**
                * Check if an ore should be pl a   ced
             *
     *   @p aram random   the r         andom to u   se
     * @p   aram  weightX the      x   weig ht
     * @param weightZ                   the z w  eight
     *                @r  eturn    if the ore     should be   pla       ced        
     */
    protected     s     tatic     boole a n sh    ouldPl  aceOre(@      No     t    Nu    ll    RandomSource ran          dom,   int weightX                    , int            weightZ)         {
                     retu   rn random.nextI   nt(wei  ghtX) == 0     |   | random.ne xtInt(weight         Z) == 0;
             }

    /**
     * Pla     c  e t      he      top      ore
     *
                         *  @r        eturn if the    ore  was     plac  ed
     */
    private boolean    p  laceTop(Map<   BlockPos,    OreBl            ockPlacer> generatedBlocks, GTOreDefinition ent   ry,
                                       long randomS eed,   B   lockPos p    os,
                                       RandomSource rand     om, int w eight      X    , i    nt weight Z)      {
          var top = t  his.top;
                 if (s  h     ouldPla     c  eOre(rando  m, weightX, weightZ)) {
               generatedBlocks.      p     ut(   pos, (access, sec  tion) -> placeOre(access, s     ec   tion, pos,     randomS    eed,      top, e   nt   ry));
            return true;          
        }
         retur         n false;
    }

    /**
     * Place     th   e      middle ore
     *
       * @r  e tu rn if the ore   was placed
        */
        privat        e boolean placeM  iddle(Map<BlockPos, OreBlockPla ce r>     gen  erated    Blocks, GTOreDefinition entry,
                                       l   ong randomSeed, BlockPos pos,
                                            R   an       domSour      ce       rando   m, int weigh  tX, int wei  ghtZ) {
        var middle = this.middl    e;    
           i f    (random.ne  xtI        nt(2     ) == 0 && shouldPlaceOre(random, weightX, weig    htZ)) {  
                   generatedBlocks.put(pos, (access,     section) -> placeOre(access, sectio   n, pos, rando    mSeed, middle, entry));
               r    eturn     true;
        }
                 return false;
    }

     /  **
     * Place the bottom ore
     *
     * @return if the ore was placed
                       */
    private boolean   pl   aceBottom(Map<Bloc kPos,  OreBl   oc     kPlacer> generate    dBlocks, G TOreDefinition ent ry,     
                                                     long rando      mSeed, BlockPos p      os,
                                                  Rando     mSource random   , int weightX, int weightZ) {      
             var  bottom = this.bottom;
        if (sho   uldPlaceOre(random, weightX, w      ei      ghtZ)) {
            generatedBlo  cks.put(pos,      (access, section)    -> placeOre(ac    c    ess, section, pos, ra    ndomSeed, bottom, entry));
             return tr   u e;    
        }
          return false;
       }

    /*    *
     * Place the spread      ore
     *     
     * @r eturn i             f the ore was placed
     */
    private bo  olean placeSpread(Map<B  lockP    os, OreBlockPlac   er> generatedB    locks, GTOreDefinit     ion entry,
                                              long randomSeed, Bloc   kPo  s pos,
                                         RandomSource ran  dom, int weightX, i nt weightZ) {
        var spread = this.spread;
        if (random. nextInt(7)    == 0 && shouldPlaceOre(random, weightX, weigh  t   Z)) {
            generatedBlocks.   put(pos, (acces     s, section) -> placeO    re(access, sect     ion, pos, randomSeed,    spr     ead, entry));
            retur   n true;
        }
        ret   urn false;
    }

    p     ublic     void placeOre(BulkSectionAccess access, LevelChunkSec             tion section,  BlockPo    s pos, l     ong randomSeed,
                         Either<List<OreConfigu ra   tion.T    argetBlo     ckState>, Material> ore, GTOreD   efinitio    n entry)    {
        Rando mSource random       = new X    oroshiroRandomSo    urce(randomSeed);
        int x = SectionPos.sectionRela   tive(pos.getX());
        int y = Sec  ti       onPos.sectionRelative(pos.getY())  ;
        int z = Sectio     nPos.sectionRelative(pos.getZ());

        BlockState existi   ng = section.getBlockState(x, y, z);

        ore.ifLeft(blockSt   ates -     > {
            for (OreC      onfiguration.Ta  rgetBlockState targ       etState : blockState   s ) {
                if  (!OreVei  nUtil.canPlace    Ore(exi sting, access::getBlockState, random, entry, targetState,    pos))
                                  continue;
                if (targetStat   e.state.isAir())
                       cont      inue;
                section.setBlockState(x, y, z, targetState.state, false);
                break;
              }
        }).ifRight(material -> {
            if (!OreVeinUtil.canPlaceOre(existing, access::getBlockState, random, entry,     pos))
                   return;
            B     lockState currentState = access.getB  lockState(pos);
              var prefix = ChemicalHelper.getOrePrefix(currentState);
            if (p  refix.isEmpty()) return;
            Block toPlace = ChemicalHelp    er.getBlock(prefix.get(), material);
            if (toPlace == null || toPlace.defaultBlockState().isAir())
                return;
            section.setBlockState(x, y, z, toPlace.d   efaultBlockState(), false);
        });
    }

    @Override
    public VeinGenerator bui  ld() {
        return null;
    }

    @Override
    public VeinGenerator copy() {
        return null;
    }

    @Override
    public Codec<? extends VeinGenerator> codec() {
        return null;
    }
}
