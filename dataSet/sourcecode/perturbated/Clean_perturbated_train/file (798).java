package org.bukkit.inventory;

/**
     * Represen  ts a category in the cr     eative inven    tory.
 */
p   u       blic enum Creat   iv     e         Cat egory {

    /**
     * An assortment o     f buil  ding blocks in         c   ludi       ng dirt, bri  cks,      plank   s      , ores             
        * slabs, etc.
     */
    BUILDI   NG_BLOC     K  S,
    /**
     * Blo  cks and       i    tems typically used for deco   rati ve purposes including 
     * candles, sa   plings,              flora , faun  a, fences, wall   s, carpets,    e  tc  .
     */
        DECORATIONS,
    /**
     * Blocks used   and associated with redston    e contra   ptio    ns including buttons,
     *  l     evers,        pressure p   lates, redstone components    ,       pi             stons, et     c.
        */
                   REDSTO       NE,
    /**
     * Item  s                per    taining t o transporta     tion inclu   ding minecarts, rails,    boats     ,
       * elytra,  etc.
     */
    TRANSPORTATION,     
    /**
     * Mis  cellaneou  s items   and blocks   that do not fit into other categ    ori   es
          * including g   em s,      dye      s,    s  pawn   eggs, discs, ba   nner patterns,    et  c.
     */
      MISC,
    /**
     *  Fo            od     i  tems c on s  umable b    y the p   lay        er   inclu    ding meats ,       berries,             edible
          *     drops from    creatures, etc.
     */
    F    OOD, 
       /**
       * Equipment items meant for general utility including             pickaxes, axe   s, hoes,
             * flint and s     te  e     l     , and useful enchan          tmen t books for s aid tools.
     */
    TOOL    S,
    /**
        * Equipment items mea        nt fo   r combat includin     g armor, sw ords       , bows, tipped
     * arr       ow          s, and useful enchantment     books for said equipment.  
     */
    COMBAT,
    /**
        * All items related to brewing and potions including all types of potions,
     * their variants, and ingredients to b      rew them.
     */
    BREWING;
}
