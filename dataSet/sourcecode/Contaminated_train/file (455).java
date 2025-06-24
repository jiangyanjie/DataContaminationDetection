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

import main.Game;
import map.Point;

import java.util.ArrayList;
import java.util.Random;

//import xml.XMLReader;

/**
 * Created with IntelliJ IDEA.
 * User: HANK
 * Date: 5/10/12
 * Time: 1:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActorHandler {
    private ArrayList<Actor> active_actors;
    private ArrayList<Actor> inactive_actors;
    //private ActorList actorList;
    Random random;
    Game game;
    public boolean run_turn;
    int current_id;


    public ActorHandler(Game game) {
        this.game = game;
        active_actors = new ArrayList<Actor>();
        inactive_actors = new ArrayList<Actor>();
        random = new Random();
        //actorList = XMLReader.loadActors();

        init();
    }

    private void init() {
        for (int x = 0; x < 10; x++) {
            int randx = random.nextInt(10);
            int randy = random.nextInt(10);
            addActor(ActorType.chicken,
                    new Point(25000 + randx, 25000 + randy));//THis should add chickens
        }
//        for (int x = 0; x < 10; x++) {
//            int randx = random.nextInt(10);
//            int randy = random.nextInt(10);
//            addActor(ActorType.fox,
//                    new Point(25000 + randx, 25000 + randy));
//        }


    }

    public void addActor(ActorType type, Point world_position) {//This creates the actor in the actorhandler and also adds it to the worldhash
        Actor actor = new Actor(type, world_position, current_id);
        active_actors.add(actor);
        game.worldHash.addActor(actor);
        current_id++;
    }

    public Actor getActor(int id) {
        return active_actors.get(id);
    }

    public void actorDeactivated(Actor actor) {
        active_actors.remove(actor);
        inactive_actors.add(actor);
    }

    public void update(boolean performed_action, double ap_from_action) {
        //TODO Fix/Create actor turn system
        if (performed_action) {
            if (run_turn) {
                for (int c = 0; c < active_actors.size(); c++) {
                    Actor a = active_actors.get(c);
                    if (a.active) {
                        a.giveAP(ap_from_action);
                        a.update();

                    }
                }
                run_turn = false;
            }

        }


    }

    public boolean moveActor(Actor actor, Point dir) {

        boolean tile_passable = game.worldHash.getPass(Point.add(actor.world_position, dir));


        if (tile_passable) {
            actor.move(dir);

            return true;

        }
        return false;
    }

    public ArrayList<Actor> getActive_actors() {
        return active_actors;
    }

    public ArrayList<Actor> getInactive_actors() {
        return inactive_actors;
    }

    public void createActor(ActorType type) {

    }

    public void giveAP(double amount) {
        for (int c = 0; c < active_actors.size(); c++) {
            Actor a = active_actors.get(c);
            a.giveAP(amount);
        }
    }

}
