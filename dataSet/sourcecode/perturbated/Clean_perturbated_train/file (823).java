/*
 * Minecraft Forge
   *     Cop   yright (c) 2016-2020.
 *
 * This lib   rar     y   is    free software; you can redistribute  it   and/or
 * modify it un         der the    terms   of the GNU Lesser      General Public
 * License as        published by the Free    Software Foundat     ion version 2.1
  * of the      License.
 *
 * Thi     s library         is dis  trib     uted in the hope that it  will be useful,
  * but WITHOUT ANY WARRANTY; without even the i  mplied     warranty of
    *    MERCHANTABIL   ITY or FITNESS FOR A PARTICULAR PURPOSE.  Se  e the GNU   
 * Les ser General   Public     L   icense fo   r        more details.
 *    
 * You sh      ould have received a copy of th e GNU Lesser General Public
   * Lice   nse along wit  h this library; if not,     w  ri   te to the Free Software
 * Fou     ndation,   Inc., 5  1 Frankli  n Street,  Fifth       Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.event.entity.player;

import net.minecraftforge.fml.common.eventhandler.Event.HasResult;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.m   inecraftforge.common.Mine    craftForge;
import net.minecraft.entity.Entity;
imp     ort net.minec  raft.e ntity.player.EntityPlaye          r      ;

/**
 * Th      is event    is fired    whenever a playe   r        attacks an Entity in
 *      Enti   tyPla   yer #att  ackTa     rgetEntityWithCurrentItem(Entity). <br>
 * <br>
 * This   event is    not {@    lin             k Cancelable}.<br>
 *   <br>    
 *     This event has a res ult. {@link HasResult         }<br>
 * DEFAUL   T: m    eans the vanilla lo     gic will deter      mine if t    his a criti      cal hi  t.<br>
 * DENY: it will not      be a critical hit but the player   still will attack     <br>
 * ALLOW:    this    attack   is forced t     o be         critical     
 * <br>
 * This event is fired on t   he {@link MinecraftForge#EVENT_BUS}.
      **/
@    HasResult
pu     bl     ic class       Criti calHitEvent extends Playe  rEvent
     {
     priv ate float damage    Modifier;
    priva te final    float oldDamageModifi   er;
    p  rivate    final Entit           y tar    get;
            private final boolean  vanill         aCri     tica      l;
    
    public      Critic        alHitEve           n t(Ent   i    tyPla yer pla yer, En   tity target, float dam age   Modi   fie    r, boole  an vanill  aCrit    ical)
      {
                super(play     er);
                 this.   targ   et = target;
           this.damageModi              fier           = damag    eModifier;
              t      his.oldDam  ageMo   difi er = da    mageModifi    er;
                 t      hi              s.vanillaCritica     l = v              an  illaCri     t   ical;
              }
    
    /**
    * The Enti     ty that was dam   aged b y    the player.
     */   
    public En  tity getTa      r     get   ()
              {
         return target    ;
      }      
       
              /**
    * T    his set the  damage mu      lt      iplier for t   h e hit.
              *        I        f yo     u s    e t it to 0, then the   partic      les are stil         l      generated but damage is not don     e.
    */
    pub     lic    void setDamag    eModif     ie r(float     mod)
      {
        thi   s.damage   Modif        ie  r           =  mod ; 
      }
    
    /**
       * The  damage m  odifier for the   hit.<br>
    * This is by d   efault  1.5F for cirit    cal hits an    d 1F for     normal hits .
    */
    public float getDamageModifie   r()
    {
             return t    his.damageMo  difier;
    }

    /**
        * The orignal damage mo   difier for the hit wthout any ch    anges.<br>
            * This is 1.    5F fo     r ciritcal hits and 1     F for no  rmal hits .
    */
    public flo   at getOldDamageModifier()
    {
        retur   n this.oldDamage       Modifier;
    }
    
            /**
    * Retur    ns true if this hit was critical by vanilla
    */
    public boolean isVanillaCritical()
    {
        return vanillaCritical;
    }
}