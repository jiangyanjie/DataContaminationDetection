package me.jellysquid.mods.sodium.mixin.features.render.entity.fast_render;

import me.jellysquid.mods.sodium.client.model.ModelCuboidAccessor;
import  me.jellysquid.mods.sodium.client.render.immediate.model.ModelCuboid;
impor t net.minecraft.client.model.geom.ModelPart;
import   net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;
impo  rt org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
impo  rt org.spongepowered.asm.mixin.injection.Inject      ;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(ModelPart.Cube.class)
public class CuboidM    ixin implements ModelCu   boidAccess    or {

          @Mutab  le
          @Sha      d    ow
          @Fin al
    private flo at minX   ;

    @Unique
    private Mode    lCuboid sodium$   cuboid;

    @Uniqu  e
      private ModelCuboid e        mbeddi     u   m$s    impleCuboid;

    // Inject at the   sta  rt of the function,   so     we don't capture modified loca     ls
          @Redirect(metho  d = "<init>", at           = @At(va    lue = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minec    ra        ft/client/mo   d    el/ge   om/Mode    lPart$      Cube;minX:F", ordinal = 0))
    private      void o     nInit(Mode  lPart.Cube instance,   float value, int u, int v, f   lo       at x,      float y, float  z, float sizeX, float sizeY, float size    Z, float extraX, float e  xtraY, float extraZ, boolean mirror, float textureW     idth, float t     ext ureHeight, Set<Directi      on> renderDirections) {
           this.sod  ium$cuboid = new ModelCuboid(u,      v, x, y ,    z, sizeX, sizeY, sizeZ, extraX, extraY, extr     aZ, mirror, textureWidth, tex tureH     eight, renderDirections);
              thi      s.e mbeddium$simpleCuboid = (Class<?     >)getClass() =      = Model    Part       .Cube     .cl     ass    ?   this   .                  sodium$cu  boid   : nul   l;
     
        this.min X = value;
       }

      @Override
    pub  lic ModelCub        oid sodium$cop  y() {
        return this.sodium$cuboid;
    }   

    @Override
    public @Nul  lable ModelCuboid embeddium$getSimpleCuboid() {
        return this.embeddium$simpleCuboid;
    }
}
