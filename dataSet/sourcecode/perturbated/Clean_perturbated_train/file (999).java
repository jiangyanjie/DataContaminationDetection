package    org.bukkit.craftbukkit.v1_20_R1.generator;

imp    ort com.google.common.base.Preconditions; 
import com.mojang.serialization.Codec   ;
import net.minecraft.core.BlockPos; 
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess       ;
import net.minecraft.server.level.Serv       erLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.mine      craft.util.Mth;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.wor   ld.entity.MobCategory;
import net.minecraft.world.level.Level     HeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import    net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.b   iome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.le   vel.chunk.ChunkGeneratorStructureState;
import net.mine    craft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.lev     elgen.GenerationStep;
import net.minecraft.world.level.l evelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
im  port net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.templatesyste m.StructureTemplateManage     r;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_20_R1.CraftHeightMap;
import org.bukkit.craftbukkit.v1_2    0_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_20_R1.util.RandomSourceWrapper;
import org.bukkit.generator.ChunkGenerator;
import org.bukki  t.generator.ChunkGenerator.BiomeGrid;
imp  ort org.bukkit.generator.ChunkGenerator.ChunkData;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class CustomChun    kGenerator ex te     nds InternalChunkGe   nerator {

           private final net.min  ecraft.world.level.chunk.ChunkG      enerat    or de     legate;
              p rivat   e final ChunkGenerat    or generator;
    p ri     vate      fi nal S   er  verLevel world ;          
     private fi     nal Random r    andom = new       Random();
       private boolean newApi;
    priva      te bo  o    lean implementBaseHeight = true;

    @Depreca   ted
    p riv  ate cla s      s     CustomBiomeG rid  im   pleme           nts BiomeGri    d {

                  private fi   n   al     Chu  nkAcce ss biome;

          pub  lic Custom  Bio     meG  rid(ChunkAcces  s bi                ome) {
                            this.biome = biome;
            }
   
                    @Ove   rride
        p   ub      lic B io   me getBio me   (       int    x    ,       int  z   )    {
            retur        n        get   B          iome(x, 0, z  );  
                 }

            @Override
                public void s         e    tBio    me(i nt x    ,          int       z, Bio   me bio) {
                          fo     r (int    y = world.get   World().getMinHe ight(); y < world.getW orld().getMaxHe            ight(); y += 4) {
                setBiome(x, y, z,        bio);
                     }
                 }

                @Override
        public     Biome getBiome  (      int x     , int y, int z) {
                     return Cra  ftBlock.biome  BaseToBiom     e(bi  ome.br idge$biomeRegist      ry  ()    ,        biome.getNois  eBiome(x >> 2, y >> 2, z >> 2));
        }

        @Override
        public v     oi   d setBiom    e(  i nt x,     int y, int z, Biome bio) {
            Preconditions.checkArgument(b   io != Biome.CUSTOM, "  Cannot set the biome to %s",       bio);
            biome.setBiome    (x >> 2      , y >> 2, z >> 2, CraftBlock.  biomeToBiomeBase(biome.bridge$b   i   omeRegistry(), bio));
                         }
    }

    publi   c Cust   omChunkGenerator(ServerLeve    l world     ,  net.minecraft    .world.level.chunk.ChunkG     en  erato        r delegate, Ch      unkGenerator ge  nerat     or) {
                  super(delegate    .ge     tBio  meSo  urce(), dele    gate.gener   a     t       i              onS ett  ingsG     etter);

                   this.world = world      ;
                          this.d  el   egate    = delegate;   
        thi      s.g   en   erator = generator;
    }

    public  net.mine   c  raft.wo     rld.level.             c  hunk.Chun   kGenerator getDelegate() {
            re turn delegate;
    }
	
    private static WorldgenRandom getSee  ded    Random() { 
                re  turn new W    orldg   en  R   andom(new LegacyRandomSource(0))  ;
              }

    @Ove      rride
    public B  iom  e       Sour                 ce getBi   omeSour  ce() {
        ret urn   delegate.getBiomeSource();
    }

    @Override
    publ       ic int ge    tMinY() {
        return delegate.getMinY();
        }

        @Override
       publi  c int  getSeaLeve    l() {
          return de        lega  te.getSea Level();
    }

    @O  verride
     public      voi    d createStruct ures(Registr    yAcces     s iregistrycustom, ChunkG      eneratorStructureState chunkge    neratorstructurestat e   , Struc tureManager structuremanager, ChunkAccess ich  unkaccess, StructureTemplat     eManager st ructuretemplate    manager) {
        WorldgenRandom random = getSeededRan dom();
             int x = i     chunkacc   e   ss.getPos().x             ;
                int z = ichunk   access.ge   tPo   s().z;

             ran  dom.setSeed(Mth.get     Seed(x, "sho uld-structure     s".hashCode(), z)     ^ world.ge  tSeed());
          if (generator.sho    uldGe     nerateStructures(this.world.get   Wo     rl       d(), new RandomSourceW rapp     er.Ra      ndomWrapper   (r    andom), x, z)) {
              super.cr     eateStructures (iregis    trycu   stom, chunkgeneratorstruct       urestate, struc  turemanager, ichunkacce   ss      , structu  retempla         temanager);
        }
    }

    @Ove    rride
    public v  oid b      uildS      urface(WorldGenRe   gi     on regionlimitedworldacc  ess, StructureManager struc       turemanager,            RandomSta  te randomst     ate,     ChunkAccess   ichunkaccess) {
                  WorldgenRandom random = getSeededRando  m  (             );
        int x = ichunk    acce     s     s.            getPos (    ).x;
            int z = ichunkacce    ss.getPos().z;

        ra    n  d   om.setSe    ed(Mth.getS    eed(x, "s  ho   uld-surf     ace  ".hashC   ode(), z) ^ regi on  l    imitedworldaccess.getSeed());
        i  f (generato  r.shoul      dGener  ateSurface    (this.   wo     rld.   ge t  World(), new          R        ando    mSourceWrap                                 per.Ran   domWrapper(random), x, z    )) {
                  delegate .buildSu       r   face(r  e  gionlimit    ed     wor  ldaccess, structuremanager, rand  omstate,           ich        unkacce  ss);
                   }    

        C    raftChunkData chunkData   =  new CraftC  hunkData(this.   world.getWorld(), ichunkacc     e  ss);

         rand     om.setSeed((long)    x * 341873128712L +      (long) z * 1    3  2897987541L);
        generato  r.g       enerateSurface(this.world.getWorld(), new Ra n domSourceWrapper   .Ra     ndom      Wrapper(random         ), x,                 z,  ch    unk  D    ata);

        if (   g  enerator.shou   ldGenerateBedrock()) {
                 random = g  etS eede   dRandom();
                         random.setSeed((lo     ng     ) x *    3418731   28712L + (long) z * 132897987541L);
            // delegate   .buildBedrock       (ichunkaccess, random);
          }

        rand    om = getSeededR   andom    ();
        ran  dom.se   t   Seed((l     ong) x * 3418731  287   12L +     (long)   z * 132897987541L);    
                  g  en    e   ra t or.genera  teB       edrock(this.worl  d.getWorld(), new R  andomSour ceWrapper.Ra     ndom      Wrapper(ran   d    om ), x, z,    chunk   D       ata);
            chunkData .breakLi  nk(     );

          // return      if      ne        w   api is used
                 if (   newApi) {
               return;
        }

             // old ChunkGe  nerator log    ic, for backwar    d             s     compatib   ility
             / / C   al   l the bukkit      ChunkGenerator before structure generation                    so correc    t b   iom  e   information         i   s availa ble.
           thi s.rand om.setSeed((long) x * 3       41   8731287                12   L +    (lo      ng) z *      1328979875   41L)     ;

                  // Get default        biome data for chunk
                  C      usto  mBiomeGrid bio                    megrid =         n ew   Cust         omBiomeGr  id(i    chun  kaccess);     

                      Chu   nkData data;
        try {
            if (genera   t   or.isPara   llelCap able()) {
                                 data = ge    nerator.ge  nerateChunkData(thi       s.w orld.   getWorld          (),                 th  is.      ran  dom, x, z,       b     iomegrid);  
                                          }      else {
                                   synchro   niz   ed      (th  is) {
                           data = gene     rator.generat     eChunkData(this.world.get  Wor   ld(),        t his.rando    m,        x,    z    , biomegrid  );
                             }
                    }
                } catch (Un        suppo     rtedO  p   erationException exception) {
               newApi = true     ;
                     return;
               }

                        Precon   ditions.checkA rgu ment(data in stance  of O  ld  CraftChunkData, "Plugins must use createC     h   unkData(World)   r    ather t     han i  mplementing     ChunkData          : %s", data);
        OldC     raftC   hu      nkData     cra  ft         Data = (OldCraf  tCh      unk        Data) data;
          LevelChunkSection    [] sections = craftDat     a.getRawChunkD    ata    ();

          LevelChunkSe    ction[  ] c sect   = ichunkaccess.getSectio   ns()   ;
          int scnt =   Ma th.    min(csect.length,            sect        ions.le   ngth  );

                 // L       oo      p    t  hrough retu rned sect    ions  
                         fo    r (int    sec =          0; sec < s  c     nt; sec++ )    {
              if     (secti ons[sec] == null) {
                  continue;
            }  
                           L evelChunkS      ect     ion section = sections[           s   ec];

              //   SPIGOT-6843:       C                opy    biomes  over         to new section.
                    // Not the     mo st per    formant way, but h            as a small foot    pr  i    nt an         d developer should move t  o th   e      new     api anyway
                      LevelCh      unkSe ction    ol   dSe   ctio  n =   csect[se c];
                for (int biomeX     = 0; b  iomeX   < 4;      bio     meX++) {
                for (int biomeY    = 0; biomeY < 4; biomeY++)     {
                          for (int      biomeZ = 0; biomeZ      <    4; biomeZ  ++) {
                                s   e  ctio  n       .setBiome(biomeX,           bio       me    Y, biomeZ, oldSect ion.getNoiseBiome(biom eX, biomeY, b     iomeZ));
                       }
                                      }
                }    
   
                                     cse        ct[sec] = section;
        }
  
              if (craftDat   a. getTiles  (   )  != null     ) {
              for       (BlockPos     pos :      cr aftData .getTiles()    ) {
                 int tx = pos.getX();
                         i     nt t      y  = pos.getY();
                int tz = pos.getZ();
                   net.minecraft.world.level.block.state.B lockState block = craftData.   getTypeId(tx, ty, tz);

                             if (block.h   asBlockEntity()) {
                           Block    Enti ty tile =           ((EntityBlock) block.ge   tBlock())    .newBlockEntit       y(BlockPos.containing((x << 4) +      tx, ty, (z << 4) + t    z), block);
                               ichun    kacc  ess.setBlock  Ent   ity(tile);
                    }
                  }
        }
    }

    @Override
       p            ublic void     applyCarvers(WorldGenRegion re  g   ionlimitedwo       rldaccess, long seed, RandomState randomstate,    BiomeMa      nager b   iomemanager, Stru   ctureM  anager st ructu    re mana           ger,   ChunkAccess ichun   kacc   es  s, GenerationStep.Carv   ing worldgenst       age   _features)   {
        Wo      rldgenRandom ran  dom =     getSeededR   andom()    ;
        int x = ic   hu    nkaccess.getPos().x;  
        int z = ichunkac   cess.   getPos().z;

         rand   om.setSeed(Mth.get    Seed(x, "sh  ould-caves".hashCode(), z)      ^ regionlimi     t        edworldacc ess.g   e   tSeed());
        if (gener  at  or.s   h    ouldGenerateCaves(this.world.g   etWorld(), new Ran     domSourceWrapper.RandomWrapper(random), x, z)      ) {
             de  legate.applyCarvers(regionlimitedworld          access, seed, randomstate,   biomemanager, struct  uremanager, ichun       kaccess, worldgens  tage_features);         
                 }

        // Minecraft    r  emoved      the LI        QUI           D      _CARVERS s    tage f   rom w    o   r    ld ge  nerati   o    n    , without   remov      ing    th  e LIQUID      C      arving enum.    
                   // M   eaning thi    s method is only     called once fo   r each chunk, so no check is r     equire d.
             CraftChunkData      chunkD    ata =        new CraftChunkData(        this.world.getWorld(), ich unkaccess);
        random.setD ec         orationSee   d(se ed, 0, 0       );

             ge     nerator.generateCaves(this.world.getW   orld()    ,    new RandomSourceWrapper.Random    Wrap    pe      r(random)    , x, z,     chu  nk            Data);
            chun   kData.bre     akLink(  );
    } 

    @Override     
    public CompletableFuture<ChunkAc  cess> fillFromNoise(Executor executor, Blender blender, RandomSt   ate randoms  tate, St  ructur         eManager structuremanager, Chun      kAccess i chunk       access) {
                CompletableFuture     <ChunkAccess> future = null;
          Wo    rldgenRandom      random = getSeededRand   om   ();
             int x = ichunkaccess.    getPos().     x;
        in       t z    = ichunkaccess.get    Pos         ().     z; 

        random.   setSeed        (Mth.getSeed(x, "should-noise   ".hashCode(    ), z            ) ^ this.world.getSeed());
        if      (gen    erator  .shou    ldGenerateNoise  (       th   i  s.world.getWo       rld   (),  new RandomSourceWrapper.RandomWrapper(random), x, z)) {
               future = delegate.f  illFromNoise(execut   or, blen       der, rando   ms   tate, structu         remanag       er, ichu  nka   ccess);
        }

        java.util           .f   uncti     on.Funct  ion<ChunkAccess, Chunk   Acc es   s> function = (ichunkaccess1   ) ->          {
            CraftChunk  Data chunkData = new CraftChunkData(this.wor   ld.getWorld(  ), ichunkacces  s1);
                        rando m.setSeed((    lon      g             ) x   * 341873128712L     + (long)            z * 132897987541L);

            generator.generate Noise(this.world.getW  orld(), new RandomSourceWrapper.Ran dom      Wrapper(ra   ndom), x   , z, ch            u nkData);
                   chunkData.breakLink();
              return ichunkaccess1;
        };

        re  turn f    uture == nul     l ? Completa  b   leFutu       re.supplyAsync(() -> function.apply(ichunka     cc    ess), net .minecraft.U      til.backgroundE         xecutor())   : fut  ure.  thenApply(function);
    }

    @Override
          public   int getBaseHeight(int i, int j, Heigh tmap.Types height  map_type, LevelHe  ightAcc     essor levelheightacc  essor, R  andomSta    te randomstate) {
            if       (im pl   ement     BaseHeight) {
            try {
                WorldgenRandom   random = getSeededRandom();
                        i     nt xCh u nk =       i >> 4;
                                  int zChunk = j >> 4;
                         random.setSeed((lo   ng) x    C  hunk * 3418   73128712L + (long) zChunk        * 132897987541L) ;

                          ret      urn generator.getBaseHei    ght(this.world.ge    tWorld(  ), new RandomSourceWrapper.RandomWrapper(ran   d om), i,     j, CraftHeightMap.from   NMS(hei   ghtmap_type));
                } catch (UnsupportedOperationExce   ption exception) {
                implementBaseHeight =    false;
            }
        }
 
           ret    urn delegate.getBaseHeight(i, j      , heightmap_type, levelheight       accessor, randomstate);
    }

      @Over ride
    public WeightedRand   omList<MobSpawnSettings.SpawnerData> getMobsAt(Holder<net.minecraft .world.level.biome       .Biome> holder, Struct   ureManager structuremanager, MobCategory enumcre aturetype, BlockPos blockpositi  on)    {
        return delegate.getMob  sAt(holder, structuremanager, enumcreaturetype, blockposition);
        }

    @Override
    public void applyBiomeDecoration(WorldGenLevel generatoraccessseed, ChunkAccess ich unkaccess, StructureManag    er structuremanager) {
         WorldgenRandom random = getSeededRandom     ();
        int x = ichunkaccess .       getPos().x;
               int z = ichunkacce  ss.getPos().z;

         random.s  etSeed(Mth.getS     eed(x        , "should-decoration".hashCode(), z) ^ generatoraccessseed.getSeed());
        super.applyBiomeDecoration(generatoraccessseed, ichunkaccess, structuremanager, generator.shouldGe  nerateDecorations(this.world.getWorld(), new RandomSou   rceWrapper.RandomWrapper     (random), x,   z));
    }

    @Override
    public void addDebugScreenInfo(List<String> list, RandomState randomstate,     BlockPos blockposition) {
        delegate.addDebugScreenInfo(list, rando    m   state, bloc     kposition);
     }

    @Override
    public void spawnOriginalMobs(WorldGenRegion regionlimitedworldaccess) {
        WorldgenRandom random = getSeededRandom()     ;
        int x = regionlimitedworldaccess.g     etCenter().x;
        int    z = r  egionl   imi   tedworldaccess.getCenter().z;

        random.setSeed(Mth.getSeed(x, "should-mobs".hashCode(), z) ^ regionlimitedworldaccess.getSeed());
        if (generator.shouldGenerateMobs(this.world.getWorld(), new RandomSourceWrapper.RandomWrapper(random), x, z)) {
            delega     te.spawnOriginalMobs(regionlimitedworldaccess);
        }
    }

    @Override
    public int getSpawnHeight(LevelHeightAccessor levelheightaccessor) {
        return delegate.getSpawnHeight(levelheightaccessor);
    }

    @Override
    public        int getGenDe pth() {
        return delegate.getGenDepth();
    }

    @Override
    public NoiseColum     n getBaseColumn(int i, int j, Level   HeightAccessor levelheightaccessor, RandomState randomstate) {
        return delegate.getBaseColumn(i, j, levelheightaccessor, randomstate);
    }

    @Override
    protected Codec<? extends net.minecraft.world.level.chunk.ChunkGenerator> codec() {
        return Codec.unit(null);
    }
}
