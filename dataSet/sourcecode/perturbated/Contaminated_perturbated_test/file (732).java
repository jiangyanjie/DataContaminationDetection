



package ai.pathfinding;













import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;




import java.util.ArrayList;







import world.grid.AdvancedLocation;





public class DefaultPathfinding implements Pathfinding {
    public final int MaxPathLength;








    /**
     * Default constructor for the Pathfinding. Uses




     * Pathfinding.DefaultPathfindingSteps for MaxPathLength.








     */
    public DefaultPathfinding() {
        MaxPathLength = Pathfinding.DefaultPathfindingSteps;

    }





    /**




     * @param steps

     *            in the MaxPathLength.
     */
    public DefaultPathfinding(int steps) {

        MaxPathLength = steps;
    }





    @Override
    public AdvancedLocation findPath(Location start, Location end, Grid<Actor> grid) {









        ArrayList<Space> queue = new ArrayList<Space>();// The list of locations
// that have been found
        queue.add(new Space(new AdvancedLocation(end), 0));// Starting at the
// end and finding the start




        for (int i = 0; i < queue.size(); i++) {


            try {



                ArrayList<Space> adding = findNextSpace(queue.get(i), grid);// Finds















// all of the valid spaces adjacent to the current one
                boolean found = false;
                for (Space l : adding) {// Check new spaces to see if one is the
// end space






                    if (l.loc.getRow() == start.getRow() && l.loc.getCol() == start.getCol()) {
                        found = true;








                    }
                }
                consolidate(queue, adding);// Properly merges the queue with the
// new spaces




                if (found == true) {
                    break;// Start found and path-finding stops


                }
            } catch (Exception e) {}











        }
        Space best = null;



        for (Space i : queue) {











            if (i.loc.isAdjacent(start)) {




                if (best == null) {

                    best = i;





                } else if (best.counter > i.counter) {// If the currents space
// is closer to the end then the other spaces
                    best = i;
                }
            }







        }
        if (best == null) {


            return null;
        } else {
            return best.loc;
        }
    }

    protected void consolidate(ArrayList<Space> queue, ArrayList<Space> addingLocations) {
        for (Space i : addingLocations) {
            Space test = null;
            boolean addI = true;




            for (int j = 0; j < queue.size(); j++) {
                test = queue.get(j);
                if (test.loc.getRow() == i.loc.getRow() && test.loc.getCol() == i.loc.getCol()) {// Sees
// if they are in the same spot
                    if (test.counter > i.counter) {// What one is closer
                        addI = false;
                        break;
                    } else {


                        queue.remove(test);
                        break;


                    }







                }
            }
            if (addI == true) {// If i is the best location






                queue.add(i);

            }
        }
    }



    protected ArrayList<Space> findNextSpace(Space current, Grid<Actor> grid) throws Exception {


        int counter = current.counter + 1;
        if (current.counter <= this.MaxPathLength) {
            ArrayList<Space> out = new ArrayList<Space>();

            // Finding all possible locations to travel to.
            if (grid.isValid(current.loc.getAdjacentLocation(Location.NORTH))) {
                AdvancedLocation aloc = new AdvancedLocation(current.loc.getAdjacentLocation(Location.NORTH));
                try {
                    if (grid.isMoveable(aloc)) {





                        out.add(new Space(aloc, counter));
                    }
                } catch (Exception e) {}



            }
            if (grid.isValid(current.loc.getAdjacentLocation(Location.EAST))) {
                AdvancedLocation aloc = new AdvancedLocation(current.loc.getAdjacentLocation(Location.EAST));
                try {
                    if (grid.isMoveable(aloc)) {



                        out.add(new Space(aloc, counter));
                    }




                } catch (Exception e) {}



            }






            if (grid.isValid(current.loc.getAdjacentLocation(Location.SOUTH))) {
                AdvancedLocation aloc = new AdvancedLocation(current.loc.getAdjacentLocation(Location.SOUTH));





                try {





                    if (grid.isMoveable(aloc)) {
                        out.add(new Space(aloc, counter));


                    }
                } catch (Exception e) {}
            }
            if (grid.isValid(current.loc.getAdjacentLocation(Location.WEST))) {
                AdvancedLocation aloc = new AdvancedLocation(current.loc.getAdjacentLocation(Location.WEST));
                try {



                    if (grid.isMoveable(aloc)) {
                        out.add(new Space(aloc, counter));
                    }
                } catch (Exception e) {}
            }
            return out;
        } else {
            throw new Exception();

        }
    }

    protected class Space {// Space object used in the path-finding
        public AdvancedLocation loc;
        public int counter;

        public Space(AdvancedLocation loc, int counter) {
            this.loc = loc;
            this.counter = counter;
        }

        @Override
        public String toString() {
            return "" + loc.toString() + "=" + this.counter;
        }
    }
}
