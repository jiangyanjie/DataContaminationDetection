/*
 * This file is part of Finally Lord.
 *
 *     Finally Lord is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Finally Lord is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Finally Lord.  If not, see <http://www.gnu.org/licenses/>.
 */

package actor;

import entities.Faction;
import render.ActorSprite;
import render.SpriteID;

/**
 * Created with IntelliJ IDEA.
 * User: HANK
 * Date: 6/20/12
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */
public enum ActorType {
    chicken("Chicken", ActorSprite.chicken, 1, 1, 1, 1, 1, 1, Faction.ANIMAL_BENIGN, "An extremely rare semi-flightless\n" +
            "bird. Its blood is used in health\n" +
            "potions."),
    fox("Fox", ActorSprite.fox, 4, 14, 4, 1, 1, 1, Faction.ANIMAL_PREDATORY, "A small animal that hunts \nsmall game"),
    goblin("Goblin", ActorSprite.goblin, 6, 14, 6, 8, 6, 6, Faction.GOBLIN, "A green, small, evil \ncreature");

    private String name;
    private String description;
    private int[] stats;
    private SpriteID spriteID;
    private SpriteID portaitID;
    private Faction faction;

    ActorType(String name, SpriteID spriteID, int STR, int DEX, int CON, int INT, int WIS, int CHA, Faction faction,
              String description) {
        this.name = name;
        this.description = description;
        stats = new int[]{STR, DEX, CON, INT, WIS, CHA};
        this.spriteID = spriteID;
        portaitID = spriteID;
        this.faction = faction;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;

    }

    public int[] getStats() {
        return stats;
    }

    public SpriteID getSpriteID() {

        return spriteID;
    }

    public SpriteID getPortaitID() {
        return portaitID;
    }

    public Faction getFaction() {
        return faction;
    }


}
