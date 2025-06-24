/*
 * This file is part of Finally Lord.
 *
 *      Finally Lord is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      Finally Lord is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with Finally Lord.  If not, see <http://www.gnu.org/licenses/>.
 */

package logic;

import actor.Actor;
import actor.Player;
import utility.Log;
import utility.Point;
import world.Map;
import world.tile.Tile;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: hankbrobeck
 * Date: 10/15/12
 * Time: 1:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActionHandler {
    static Player player;
    static Map activemap;
    static DamageSystem damageSystem;

    public static void init(Player p) {
        player = p;
        damageSystem = new DamageSystem(player);
    }


    public static void setActiveMap(Map amap) {
        activemap = amap;
    }

    public static boolean attemptPlayerMove(Point dir) {
        HashMap<Integer, Tile> tiles = activemap.getTileMap();
        HashMap<Integer, Actor> actors = activemap.getActorHash();
        Point newloc = player.getPos().copy();
        newloc.push(dir);
        int newkey = activemap.genKey(newloc.getX(), newloc.getY());
        if (tiles.containsKey(newkey)) {
            if (tiles.get(newkey).isPassable()) {
                if (!actors.containsKey(newkey)) {
                    player.move(dir);
                    return true;
                }
            }
        }
        if (actors.containsKey(newkey)) {
            Log.print("player attacked!");
            damageSystem.playerAttack(actors.get(newkey));
            return true;
        }
        return false;
    }

    public static boolean attackPlayer(Actor attacker) {
        return damageSystem.attackPlayer(attacker);
    }

    public static boolean actorAttack(Actor attacker, Actor defender) {
        return false;//TODO To be implemented
    }


}
