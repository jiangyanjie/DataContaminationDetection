/*
      * This     file is   part of   F  inally  L   ord.
 *
 *         Finally  Lord i s    free softwa   re:       yo    u can r     edistribut  e    it     and/or modify
 *      it under   th     e te                    rms of the GNU General         Public Li  ce  nse as     pu     blis   hed by  
 *       the Free So ftware Foun    dati       on, either versi    on 3 of   the L             icense,     or
 *        (    at     your option ) any la   ter version.
   *
 *      Finally Lord    is distributed in          the hope that it will be useful,
 *            but WITHOUT ANY WA RRA   NTY; without even    the implied        warr anty of     
 *      MERCHANTABIL          ITY or    FITNES  S  FOR A PA RTICULAR    PUR      P      OSE.  Se    e the
 *      GNU General  Public Licens     e for  more detail   s.
 *
 *             You should have received        a cop  y of the GNU General Public License
 *      along with Finally Lord.  If not, see <http://www.gnu.org/licenses/>.
 *   /

package logic;

import     ac   tor.Acto r;
  import actor.Player;
import utili  ty.Log;
import utility.P oint;
import world.Map;  
i     mp    ort world   .tile  .Ti  le;

import    java.util.HashM    ap;
     
/**
 *       Cre ated with    In    telliJ IDE  A.
 * User: hankbr     obe  ck
 * D    ate: 10         /15 /12
    * Time: 1:12 PM
     * To change this template use File |      Sett   ings | Fil  e  Templates   .             
 */
publi  c class Act i  onHandler {
    static Player                     player;      
                  sta    tic Ma      p activemap    ;
         st      atic DamageSy       stem damageSystem;
  
    public static void in   it(Pl     ayer p) {
          pla     y          e   r = p;
                damageSystem =   n      ew DamageSystem(player)   ;
         }
    

      public static void setA      cti   veM  ap(Map amap) {
        activemap =     am       ap;
    }
 
    public stati c boolean    a  ttemptPla    yerMove(Poin    t dir) {
         Ha shMa  p<Integer, T     ile  > tiles    = activema  p.getT   ileMap();
        HashMap<Integer, Actor>   actors = activemap .ge     tActorHash();
          Po  int newl oc = player.getPos().copy();
             new        lo   c.p     ush(dir);  
              int       newkey = a     ctivemap.ge              nKey(newloc.getX(),    newlo  c.     getY());
        if (tiles.c    o   ntainsKey(new     key)) {  
                         if      (t ile      s.get( newkey).isPass able(             ))   {
                     i f (!actors.c on    tainsK   e   y(n  ewke  y))         {      
                         player.          move(d      ir)   ;   
                    return true  ;
                                          }
                         }
        }
        if (actors.containsKey(newkey)) {
            Log.prin  t(    "player attacked!");
            damageSystem.player      Att         a       ck(actors.get(newkey));
                return true;
        }
        return false;
    }
   
                public stat      ic boolean attackPlaye      r(Actor     attacker) {
        return damageSystem.attackPlayer(attacker);
    }

    public      static boolean acto rAttack(Actor attacker, Actor defender) {
        return false;//TODO To be implemented
    }


}
