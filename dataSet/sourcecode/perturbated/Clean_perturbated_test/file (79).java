package com.gregtechceu.gtceu.data.recipe;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.tag.TagUtil;

import net.minecraft.core.registries.Registries;
import    net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
imp   ort net.minecraft.tags.TagKey;
imp    ort net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public  class CustomTags         {
  
        // Added    Va       nilla tags
       public   static final TagKey<  Item> TAG_PISTONS = TagUtil.createItemTag    ("pi    stons");
    public static final TagKey<Item> G     LASS_BLOCKS = TagUtil.createItem       Tag("glass");
    public     static final TagKey<Item>   GLASS_PANE     S = TagUtil.createItemTag(  "glass_panes");
    publi      c static f     inal T   a    gKey<Item> SEEDS = Ta    gUti   l.createItemTag("seeds") ;
    pub     lic static       final Ta    gKey<Item> CONCRETE_ITEM =     TagUtil.createItemTag("concrete");
    public static final TagK  ey<Item>    CONCRETE_POWDER_ITEM = TagUtil.createItemTag("concr        ete   _     powder");

       // Added      Gregtec   h tags
    public static final TagKe       y<Item> TRANSIS     TORS = Tag   Util.createModItem     Tag("transistors");
          public st   atic final TagKey<Item> RESISTORS = TagUtil.createMod    ItemTag("resistors");
    public  static         final TagKey<Item> CAPACITORS = TagUtil.createModItemTag("capacitors");
    public stati    c final TagKey<Item> DIODES =   TagUtil.createModIte  mTag("diode  s");
      publ ic static final TagKey<Item> INDUCTORS = TagUtil.createModItemTag("inductors");

    p  ublic sta   tic final TagKey<Ite  m> ULV_CIRCU  ITS = TagUti   l.createModItemTag("circuit       s/ulv");
          public static final TagKey    <Item> LV_CIRCUIT        S = TagUtil.createModItemTa  g("circui ts/l  v");
    public static final Tag   Key<Item> MV_CIRCUITS = TagUtil.createModItemTag("circuits  /mv");
     publi       c stati     c final TagKey<Item> HV_CIRCUITS =    TagUtil.createMod ItemTag(    "  circuits   /hv");
    public st   atic final Ta     gKey<It      em> EV_CIRC       UITS = TagUtil.createMod   ItemT    a    g("circuits/ev");
    public s tatic final  TagKey<Item> IV_CIRCUITS = TagUtil.createModItemTag("c        ircuits/iv");
    public static     final     TagKe     y<Item> LuV_CIRCUIT    S = TagUtil.createModItemTag("circuits/lu  v");
    publi c static final Tag     Key<  Item> ZPM_CIR     CUITS  = TagUtil.createModItemTag("circuits/zpm")  ;
    public s    tat   ic final TagKey< Item>  UV_CIRCUITS = TagUtil.creat eModItemTag("circuits/uv");
    public static final T   agKe   y<Item>   UHV_CIR  CUITS = TagU    til.createModItemTag("circuits/uh  v");
    public static final TagKe y<Item>    UEV_CIRCUITS = TagUtil.createModItemTag("circuits/uev");
    public static final TagKey<Item> UIV_CIR     C UITS = TagUtil.createModItemTag("circuits/uiv");
    public stati c final TagKe      y<Item> UXV_   CIRCUI  TS = TagUtil.createModItemT       ag("    circu      its/uxv");
    public static final TagKey<Item> O pV_CIRCUITS = TagUtil.createModItemTag("c   ircuits/opv");
    public static   final Ta   gKey<Item> MAX_CIRCUITS = TagUtil.creat   eMo      dIte   mTag("ci     rcuits/max");

    public static final TagKey<Item> ULV_BA  TTERIES = TagUtil.createModItemTa     g(" batteries/ulv");
    pub    lic static final TagKey<Item> LV_BA  TTERIES = TagUtil.createModIte      mTag("  bat    teries/lv");
    public stati        c final TagKey<Item> MV_BATTERIES = TagUti      l.createMod    ItemTag("batter     ie   s/mv");        
    public static final TagKe    y<  Item> HV_BAT  TERIES = TagUtil.createModItemTa      g("b   atteries/hv");
       pu  blic static final TagKey  <Item> EV_BATTERIE    S = TagUtil.createModItemTag("batteries/ev")   ;
           pu    blic stati      c final TagKey<Item> IV  _BATTERIES = Tag     Util.  cr  eateModI temT  ag("    batteries/iv");
    p   ublic static final TagKey<Item> L  uV_BATTERIES = TagUtil.createModItemTag("ba  tteries/luv");
      public static fina   l     TagKey<Item> ZPM_BATTERIES =  TagUtil.createModItemTag       ("batteries/zpm");
    public stati    c final TagKey<Item>  UV_BATTERIES = TagUtil.createMo   dItemTag("batteries/uv"   );
    public static final TagKey<Item> UHV_BATTERIES = Ta   gUtil.createModItemTag("batteries/uhv");

      pub  lic stati  c final   TagKey<Item> P     PE_ARMOR = T  agUtil.createModItemTag("ppe_armor");

          // Platform-dependent tags
    pub lic s    tatic f    inal TagKey<Item> RUBBE   R_LOGS_IT EM = TagUtil.createModItemTag("logs/rubber");
    public static final TagKey<Block>    NEEDS_WOOD_TOOL          = TagUtil.creat   eBlockTag("needs_wood_tool"      )  ;
       public static final TagK   ey<Block   > NEEDS_GOL  D_TOOL = TagUtil.createBlockTag     ("  needs_gold_tool");
    public     static final   TagKey     <Block>     NEEDS_NETHER    ITE_TOOL =       TagUtil.       createBlockT      ag("needs_netherite_tool"  );
    pub   lic stat  ic final TagKey<Blo ck>    NEEDS_DUR   ANIUM_TOOL =   TagUt il.createBlo   ckTag(  " needs_duranium      _tool");
        pub  lic       stat    ic final     Tag     Key<Bloc      k> NEEDS_NE     UTRONIUM_TOOL = TagUtil.cr      eateBlock      Tag("needs  _n   eutronium_tool");  

    @SuppressWarn   ings("unchecked")
    public st     atic                final         TagKey<Bloc  k>[] TOOL_   TIER S   = new TagKey[] {
                NEEDS_WOOD_TO        OL,  
              BlockTa       gs.NEEDS_S    TONE_TOOL,
              BlockTags.NEED S_  IRON_TOOL,
                Block Tags.NEEDS_DIAMOND_   TOO   L,
                 NEEDS_NET   HERITE_TOOL,      
            NEEDS_DURANIUM_TOOL,
                     NEEDS_NEU    TRONIUM_TOOL  ,
      };

           p  ublic static final Ta    gKey<Block> END   S  TONE_OR  E_R    EPLACEABLES = TagUtil.createB      lockTag("end_stone_ore_rep laceable s");
    public static fina    l TagKey<Block> CONCRETE_B       LOCK = TagUtil.cre   ateBlockTag("concrete");
    publ     ic s       tatic final T agKey<Bloc    k> CO    NCRETE_POWDER_BLOCK = Ta  gUtil.createBlockTag("conc    rete_powder");
        public static final TagKey<Block> GLASS_BLOCKS_BLOCK = TagUtil       .cre     at eBlockTag("glass");
       public s    tatic fina                 l TagKey<Block>    GLASS_PANES_BLOCK = TagUtil.crea teBlo  ckTag(   "glass_panes");
    public static      final  TagKey<Block> CREATE_SEATS     = Ta    gUtil.optionalTag(Regis  tries.BLOCK,
            new ResourceLoca       tion(GTValues.MODID_CREATE, "se   ats"));
    public static final TagKey<Block> ORE_BLOCKS  = TagUtil.createB  lockTag("    ores");

    public static final Tag    Key    <Block> RUBBE     R_LOGS_BLOCK = TagUtil.createModBlockTag("logs/rubbe     r");
    public static final TagKey<Item> WOODEN_CHESTS = TagUtil.createItemTag("che   sts/wooden");

    p        ublic static final TagKey<Biome> IS_SWAMP = TagUtil.createTag(Registries.BIOME, "is_swamp",      false);
       public static final Tag   Key<Biome> IS_S  ANDY = TagUt   il.createModTag(Registries.BIOME, "is_sandy");
    public static final TagKey<Biome> HAS_RUBBER_TREE = TagUtil.cr eateModTag(Registries.BIOME, "has_rubber_tree");

    public static final TagKey<EntityType<?>> HEAT_IMMUNE = TagUtil.createModTag(Registries.ENTITY_TYPE, "heat_im  mune");
    public static final TagKey<Enti   tyType<?>> CHEMICAL_IMMUNE = TagUtil.createModTag(Registries.ENTITY_TYPE,
            "chemical_immune");
}
