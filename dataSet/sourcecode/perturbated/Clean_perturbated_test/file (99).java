package org.bukkit.craftbukkit.v1_20_R1.generator;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import   net.minecraft.core.Holder;
import net.minecraft.world.level.biome.BiomeSource;
import     net.minecraft.world.level.biome.Climate;
import   org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_20_R1.block.CraftBlock;
import org.bukkit.generator.BiomeProvider;
import   org.bukkit.generator.WorldInf o;

import java.util.ArrayList   ;
import java.util.List;
import java.util.stream.Stream;

public c   lass CustomWorldChunkManag  er extends BiomeSource     {   

    pri  vate       final Worl   dInfo worldInfo;
    private final BiomePr     ovider biomeProvider;
    private final net.minecraft.core.Registry<net.minecraft.world.level.bio     me   .Biome> registry;

        private static List<Holder<net.minecraft.world.level.biome.Biome>> biomeListToBiomeBaseList(List<Biome> biomes, net.minecraft.core.Registry<n      et.  m  inecraft.world.lev    el.b     iome.Biome     > registry) {
             List<Holde       r<net. minecra ft.world.level.biome.Biome>> biomeBas         es = new Arr   ayLi            st<>(    );

        fo  r (Biome biome :  b      i omes) {
                     Preconditions.checkArgument(b   iome             != Biom e.CUSTO  M,  "C   a     nnot use the biome %s",      biome);
             biomeBases.add(CraftBlock.biom eToBi omeBase(regi      st    ry, biome));
        }
       
        return biomeBases;
    }

    public     Cus    tomWorldC hunkManager(Worl   dIn fo worldInfo, B     iomeProvider bi  omePro v   ider, net.minecraft      .core.Registry<n  et.mine craft.    world.level.biome.Biome> regist  ry) {
        t      his.worl   dInf   o =    wo    rl dInfo;
        this.biom eProvi      der = biome  Provider;
                this.registry   = registry;
     }

         @Ove    rr    ide
      protected Codec<? ext     e    nds BiomeSource> codec() {
        t   hrow    new Uns          upportedOperationException("C   a   nnot ser     i   al  ize CustomWor       ldChunk     Man    ager");
    }

    @Override
         publi     c Holder<net.minecraft.world.level.biome.Biome   > getNoiseBiome(i nt x    , int y, int z, Climate.Sampler sam pler) {     
                      Biome bi ome = biomeProvider.getBiom   e(  worldInfo, x << 2, y << 2, z     << 2, CraftBiomeParameterPoint.createBiomeParameterP  oint(sample     r, sampler.sample(x, y, z   )));
        Precondit   ions.checkArgument(bi    ome != Biome.CU      STOM, "Cannot set the biome to %s", biome);

          return CraftB     lock.biomeToBiomeBase(registry, biome);
    }

    @Override
    protected Stream<Holder<net.minec  raft.world.level.biome.Biome>> collectPossibleBiomes() {
           return biomeListToBiomeBaseList(biomeProvider.getBiomes(worldInfo), registry).stream();
    }
}
