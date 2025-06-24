package ws.abhis.education.old;

import java.util.LinkedList;

/**
 * http://jira.abhis.ws/browse/AP-54
 */
public class AP_54 {

    //Reverse for alternate levels, so say, reverse for even levels
    public void rearrangeAlternateLevels(Node root) {


        LinkedList<Node> current = new LinkedList<Node>();

        if (root != null)
            current.add(root);

        int level = 1;

        while (current.size() > 0) {


            LinkedList<Node> parents = current; //Create new level
            current = new LinkedList<Node>();

            for (Node parent : parents) {

                if (parent != null) {

                    if (parent.left != null) {
                        current.add(parent.left);
                    }
                    if (parent.right != null) {
                        current.add(parent.left);
                    }
                }
            }

            //At this point current contains all the children of parents
            if (level % 2 == 0) {
                //even level, yeah!
                current = reverseArr(current);

                for (int i=0; i<parents.size(); i=i+2) {
                    int k = i+1;

                    if (i < parents.size() ) {
                        if (i >= current.size()) {

                            parents.set(i, parents.get(i).left = null);
                        } else {
                            parents.set(i, parents.get(i).left = current.get(i));
                        }
                    }

                    if (k < parents.size()) {
                        if (k >= current.size()) {
                            parents.set(i, parents.get(i).right = null);
                        } else {
                            parents.set(i, parents.get(i).right = current.get(k));
                        }
                    }

                }
            }

            level++;

        }


    }

    private LinkedList<Node> reverseArr(LinkedList<Node> current) {
        LinkedList<Node> result = new LinkedList<Node>();
        for (int i=current.size()-1; i>=0; i--) {
            result.add(current.get(i));
        }

        return result;
    }

    public void printInOrder(Node root) {
        if (root == null)
            return;

        printInOrder(root.left);
        System.out.println(root.value);
        printInOrder(root.right);
    }

    public static void main(String... args) {
        /**
         *              20
         *          8       22
         *       4     12
         *          10   14
         */

        Node four = new Node(4, null, null);
        Node ten = new Node(10, null, null);

        Node root = new Node(20, new Node(8, four, new Node(12, ten, new Node(14, null, null))), new Node(22, null, null));

        AP_54 ap_54 = new AP_54();
        ap_54.rearrangeAlternateLevels(root);
        ap_54.printInOrder(root);
    }
}
