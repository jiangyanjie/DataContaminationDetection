/*
          * This file is      pa    rt of   Fin    ally L   o   rd.
 *
 *     Fi    nally Lo  rd is free software: you           c    an     redistrib ute it and     /or mo      di      fy
 *     it under the t   erms of the GN  U      General Public Li  cense a  s p ublis hed by
     *     the Fr   ee Software    Found         ation, eith     er version 3 of th       e     License, or
 *     (at y  our option) any later versio     n.
 *
                     *     F       in   al   ly Lord is    dist   ribute d in the hop    e t       h          at it will be useful,
 *     but WITHOUT ANY WARRANTY; with    out even the implied warranty of
 *     ME    RCHANTAB    I  LIT  Y or FITNESS      FOR A PART     ICULAR PURPOSE.  See th           e    
 *         GNU General Pub lic      License      for m   ore      details.
 *
    *     You s hould have received  a copy of the       G    NU General Public License
 *      along with Finally L    ord.  If not, see <http   ://www.gnu.org/     licenses/>.
 */

package ren    der;

public class    Ac torSpri   te {
    //Ma le "       Heroes"
    public stat              ic SpriteID ranger =           new SpriteID(0, 0);
    p      ublic stat      ic SpriteID warrior = new Spri     teID (1,    0);
    public static    SpriteID      ma ge = new Sprit eID(2, 0);
    publi  c static Sp   riteI     D rogu        e =    new                Sp riteI       D(3, 0);
      public static SpriteI          D     technom  an   cer     = new Spri   teID(4, 0);
    public stati    c SpriteID p aladin = new Sprite   ID(5     , 0);
    publ   ic static  Sprite    I              D cleric = new SpriteID(6, 0);
         public static  SpriteI    D adventur        er = new SpriteID(7          ,  0);
    public st           atic     Spr iteID guard = new SpriteID(8, 0);
    publi         c st    atic Spri      teID r   ed_   guard = new SpriteID(9, 0);
         publ   ic sta tic Sprite    ID holy_guard = new Sp     riteID(10, 0);
          p ublic    s    tatic SpriteID pirate = new SpriteI    D(11, 0);
    public    static Sp   rite   I           D  dark_mage = new     Sp    riteID(12,      0);
         p  ublic static SpriteID druid = new    SpriteID(13, 0);
    public static Sprite  ID forester    =     new S    priteID(   14, 0);
        public static        SpriteID bar       b        arian = new SpriteID(15, 0);
    //Female "Heroes"
    pu     b     lic st       atic SpriteID ranger_female = new Spri      teID(0, 1);
            publi   c stat   ic S  p rit    eID w a  rrior_female = new SpriteID(1, 1);
    publi   c s    ta  tic SpriteID ma    ge     _female =         new SpriteID(2, 1);
    public        static     Spri   te ID rogue_    f   emale = new SpriteID(3, 1);
        public    st   atic SpriteID t    echno    mancer_female = new SpriteID(4, 1);
    p ublic st    atic Sp    riteID paladin_        female = new SpriteID(5, 1);
         publi       c static SpriteID cleri c_fema    le =   new S     p   riteID(6, 1  );
       public   static SpriteID advent      urer_female = new      SpriteID(7, 1);
       public static SpriteID guard_fem    ale = n     ew SpriteID(8, 1);
      public stat        ic SpriteID red_guard_female = new Sp       riteID(9, 1);
    publ  i  c static SpriteID holy_guard_female = ne     w SpriteID   (1         0   , 1);
    public     stati  c Spri    teID pirat   e_fe   male  = new S   p r  iteI          D(11, 1);
    pub   lic sta    tic S prit     eID d    ark_mage   _female   = new SpriteID(12, 1)   ;
    pu   blic s  tatic SpriteID        druid_fema      le = new SpriteID(13, 1);
    publ     ic     static     SpriteID forester_female = new    SpriteID(14, 1);
    public static SpriteID barbarian      _fe   male =    new Spr  it                e   ID(15, 1);
    //Humanoids
    public  static SpriteID elder       _mage = new SpriteID(        0, 2);
    publ     ic static SpriteID smith = new SpriteI      D(1, 2); 
    public static S    p  riteID cook = new S        p  riteID(2, 2);
    public static SpriteID bandit = new SpriteID(3, 2);
    publi    c static SpriteID     cit   y_gua    rd = new        SpriteID(4, 2);
    public s      tatic SpriteID priest   _blue = new       Spr     iteID(5, 2 );
    pu    blic static SpriteID priest_white = ne    w SpriteID(6, 2);
    public s   tatic   Sprit  eID priest_red =    n   ew    S    pr  iteID(7, 2);
       pu  blic static Spri     teID priest_red_leader  = new SpriteI D(8, 2);
    public sta    tic    SpriteID nun = ne  w    Sprit   eID(9, 2);
    publ   i  c    st  at     ic Spri    teID evi   l_mage_purple = new SpriteID(   10, 2);
        public static SpriteID evil_mage_white = new Sprit     eID(11,      2);
    public stat  ic     SpriteID evil  _mage_red = new S   p   riteID(12, 2);
    publ   ic static SpriteID dark_ ma  n_gray = new Sp         riteID   (1   3, 2);
    pub lic static S   prit  eID dark_  man_purple = n ew Spr  iteID(14,   2);
    pu b   lic static       Spr    i teID elder_mage_f  emale = new S      priteID(15,   2);
      //El     ves
    p   ublic static S     priteID        elf_ranger = new SpriteID(   0, 3);         
    p    u        blic static SpriteID         elf_w arrior = new SpriteID(1 , 3);
    publi      c static Spr     iteID elf_mage =   new     SpriteI  D(2, 3);
    public static Sp    riteID elf_ranger_     elder = new SpriteID(3, 3);
    public    static Spri     teID  elf_mage_elder = new SpriteID(4, 3)  ;        
    publi     c static SpriteID elf_ranger_blue =   new SpriteID(5, 3); 
               pu  blic static  SpriteID elf_    warrior _blue = new Sprit    eID(6, 3);
    publi   c static SpriteID elf_mage_blue =  new SpriteID(7, 3);
    public static Sprit     e   ID elf_rang   er_e   lder_blue     = new      Spr      iteID(8, 3);
       pub    lic static Spr  i   teID  elf_mage_ elder_blu      e = new Spr    iteID(9, 3);
          public static Spr  iteID e   l    f_rang   er_dark = new Spri     teID(10, 3);
       public static  SpriteID elf_warrior_dark = new    Spr    it     eID   (11, 3)  ;
          pu        blic static Sp   ri     teID elf_mage_dark = new Spri      teID(12,   3);
      publ     ic sta      tic Sp   riteID elf_ranger_elder_dark        = new SpriteID(    13, 3);
     public static SpriteID elf_ma ge_elder   _dark = new Spr    it eID(14, 3);
    public static Spr  iteID elf_rogue_dark =     new SpriteID         (15, 3);
    //Short   ies
    public static SpriteID k   obold_wa   rrior = new SpriteID(14, 4);   
    pub    lic static SpriteID kob          old_ranger = new SpriteID(15, 4);

    //Gob   lins
    public s          tatic SpriteID go  blin = n ew SpriteI       D(0, 5);
    public static SpriteID goblin2 = new SpriteID(1,      5);       
            pu         blic static SpriteI  D   goblin     _wa   rrior = new SpriteID(2, 5);    
    pu    bl  ic stati   c SpriteID          goblin_  mage = new Sprit      e  ID(3, 5); 
      public static S       priteID orc = new     SpriteID(4, 5);
    public     static SpriteID orc_  warrior      =  new SpriteID(5, 5);
       public static     Sp  riteID orc  _red =   new SpriteI   D(6, 5);
             publi   c static Sprit      eID     orc_red           _warrior = n   ew SpriteID(7, 5);
    //TOD O      this line incom  plete


               /   /Undead
        public static SpriteI   D skeleton = new SpriteID(0,      6);
    public s      tati       c      Spri     teID skeleton_warr  ior     = new S   p       riteID(1, 6); 
    public static Sp        riteID skeleton_warrior_2 = new      SpriteID(2, 6)   ;
         public static Sp          r    iteID skele      ton_ma ge = new SpriteID (3, 6);
    public stati              c Sprite ID sk     eleto n_king = new Sprite       ID(4, 6);
    pu blic static Sprite   ID floating   _  skull_   red = new Spri      teID   (5, 6);
    public static Spri  teID    f     loat  ing_sku     ll_blue = n        ew Spri    teID(6, 6);
     p         ublic sta  t ic Sp    riteID apparitio   n = new SpriteID(7,       6);
       p                  ubl   i c stat  ic Sp    riteID   shado  w = new Sp     riteID(8, 6);
    public static    SpriteID shadow_ro  bes = new SpriteID   (9,     6);
    pu    bl     ic st      atic S               priteID mummy = n ew SpriteID(10, 6);
    pu     blic static SpriteID mummy_king = new SpriteID(11, 6);
    public static    S priteID mum    my_q u    ee        n = new       Spri       te   ID(12, 6)  ;       
    public stati    c SpriteID grim_reaper      =   ne          w SpriteID(     13, 6);
    public  static SpriteI     D vampire = new SpriteID(14,      6);
        public static     SpriteID    vampire     _k  ing = new SpriteID(16, 6);
    //Spirits
    p        ublic static SpriteID spirit_of_flame = new Sprite      ID(0, 7);
    public s  tatic     SpriteID spirit_o      f_   water = new SpriteID(1   , 7);  
    public static SpriteID spir  it = new Sprit   eID(2, 7    );  
         public static SpriteID b   lu   e_app   ar ition = new SpriteID(3, 7);
      public s    tatic Spri            teID    red_apparition = new Sprit       eID(   4, 7);
     public sta            tic SpriteID dark_ap   parition =    new SpriteID(  5,       7  );      
    public s   tatic SpriteI     D ghost_       gray    = new Spr      iteID(6   , 7);
    public stat    ic S priteID         ghost_blue = new  Spr    iteID(7, 7);
    public       static SpriteID    ghost_   white = new SpriteID(8, 7);
    public static SpriteI   D      ghost      _bat = new Sp riteID(9, 7);
       public static SpriteID    ghost_ranger = new SpriteID(10, 7);
    public static Spr iteID gho st_w   arrior = new  SpriteID(1    1, 7);
    p   u         blic sta tic Spr   i  teID ghost     _mage      = new SpriteID(   12, 7)   ;
       public static  Spr             i    teID ghost_r       ogue = new S  pri    teID(13, 7);
      public       static Spri       teID ghost_p            aladin = new SpriteID(14, 7);
    public static SpriteID ghost_guard = n     ew Sp     rite      ID(15, 7);
       //Og  res
    pu    b       lic        sta  tic S    priteID ogre = new S   priteID(0, 8 );
    public st  atic SpriteID ogre_f    emale = new S    priteID(1, 8);
    public static SpriteID o     gre_2 = new SpriteID(2,      8);
    public    s   tatic SpriteID ogre_r   obes   =     new   S    priteI    D(3,     8);
    public stat      i  c SpriteID ogre_    king = new SpriteID(4, 8);
      pub lic static S    priteID       cycl  ops = new SpriteID(5   , 8);
    pub   lic static Spr      iteID cyclops_blue = ne  w       SpriteID    (6, 8);
     public static Sprite ID cyclo   ps_helmet = new Sprit  eID(7, 8);
    pu     blic static      SpriteID     cycl     ops_ helmet_blue = new SpriteID(8, 8);
    public    static SpriteID cyclops_king = new SpriteID(9, 8);
    public stat         ic       SpriteID hobgoblin = n       ew Spri   teID(1 0, 8);
    public static SpriteID hob    goblin_warri              or = new Spr iteID(11, 8);
    public static SpriteID    hobgoblin_warr   ior_   2 = ne  w    Sp rit  eID(12, 8);
      pub     lic static SpriteID hobgobl in_mage =    new Sprite  ID (13, 8);    
             pu  blic static SpriteID      hobgoblin_priest = new SpriteID(14, 8       );
       p     ubli          c static Spri  teID hobgoblin_king = new SpriteID(1 5, 8);
    // Magical
          public    sta    tic SpriteID demon = ne          w Spri    teI   D(0, 9);
    public stati  c  Spr   i teID de  mon_white =      new  SpriteID(1, 9);
             publi  c static Sprit   e  ID demon_2 = n     ew SpriteID(2,   9);
    public sta    tic SpriteID demon_warrio       r = new SpriteID(3, 9);
    p ublic static Sprit eID demon_ma     ge = n         ew SpriteI     D(4, 9);
    p  ublic st     atic SpriteID        mind_fla  yer_blue = new SpriteID(5, 9);
    pub  lic     st atic SpriteID mind_flayer_yel     low         = new SpriteID(6, 9) ; 
       publ      ic static SpriteID angel = n   ew SpriteID(      7, 9);     
    public static    SpriteID angel_female = new       SpriteID(8, 9);
       public st     atic Sp     riteI     D       demon_blu     e = new SpriteID(9, 9 );
    public static SpriteID dark_warr       ior = new SpriteID(10, 9);
      pub    lic s tatic SpriteID whisp_1 = new SpriteID(11, 9);
        public static SpriteID whisp_2 = new SpriteID(     12, 9);
    public static SpriteID behe  a    ded = new Spri  teID(13, 9);
    pu    bl     ic      static Sprit      eID beheaded_female = new SpriteID(14, 9);
    public static SpriteID beheaded_sku    ll =    n   ew   SpriteID(15, 9);
       //Golems
    public static Spri   teID gol    em_ stone =  new SpriteID(0, 10);    
    pub  lic static SpriteID golem_stone_2 = new Spr   iteID(1, 10);
    public static SpriteID golem_s    and = new SpriteID(2, 10);
    pu    blic static SpriteID g     ole   m_nature  = new SpriteID(3, 10);
    public static   SpriteID go   lem_dirt    = new Sprite  ID(4, 10);
    public   static Spr  iteID golem_    ice =      new S priteID(5, 10);
    p   ublic static SpriteID golem_green = new Sprite  ID(6, 1   0);
    public s      tatic SpriteID golem_purple = new Sprit    eID(7, 10);
    public static SpriteID golem_death = n     ew SpriteID(8, 10);
    public static S    p     rit       eID golem_sand_2        = new S     priteID(9, 10);
        public static SpriteID golem_fire = new SpriteID(10, 10);
    public static SpriteID golem_sha   dow = new SpriteID(11, 10);
    pu blic static SpriteID gole     m_water = new SpriteID(12, 10);
    public static SpriteID golem_snow = new SpriteID(13, 10);
    public static SpriteID golem_sand  _3 = new    SpriteID(14, 10);
    publi  c static       SpriteID golem_red = new SpriteID(15, 10);

       //Animals

    public   static SpriteID dog_dark =   new Sp riteID(0, 13);  
      public static SpriteID dog_brown = new SpriteID(1, 13  );
    public static SpriteID sheep = new Spri teID(2, 13);
    public static SpriteID tiger = new        SpriteID(3, 13);
         publi c static Sprit    eID chicken_ultra = new SpriteID(4, 13);
    publi  c stat ic SpriteID be      ar = n    ew Spri  teID(5, 13);
    public static SpriteID gorilla = new SpriteID(6, 13);
    public static SpriteID monk   ey = new SpriteID(7, 13);
    public   static SpriteID fox = new SpriteID(8, 13);
    public static SpriteID ram = new SpriteID(9, 13);
    public static SpriteID deer = new SpriteID(10, 13);
         public static SpriteID bear_standing = new SpriteID(11, 13);
    public static SpriteID horse_sadle = new   SpriteID(12, 13);
    public static SpriteID horse_wild = new SpriteID(13, 13);
    public static SpriteID pegasus = new SpriteID(14, 13);
    public static Sprit  eID lion = new SpriteID(15, 13);

    //Test Sprites
    public static SpriteID player = new SpriteID(4, 0);
    public static SpriteID chicken = new SpriteID(13, 12);
    public static SpriteID blood = new SpriteID(5, 24);

}
