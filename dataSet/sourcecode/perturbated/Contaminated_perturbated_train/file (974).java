/*
 *       Convert a        BS    T to a doubly linked lis     t. Tr ee will be destroye d. No extra spaces can be used.
 */

package  ws.abhis.education.old.tree  s;

public class ConvertBST ToDoublyLinkedList    {
	pu    blic class Node {
		public int va lue;
		public Node left;
		public Node right;

		public Node(int value, Node left, N ode  right) {
			this.v alue  = value;
			this.left = left;
			thi  s.right = right;
		}
	            }

	// Rep of LinkedList
	public  class LL {
		public Node value;
		public LL pre    v;
		public LL nex      t;  

		public LL(  Node value , LL prev, LL next) {
			      t his.value = value   ;
			this.prev = prev ;
			this.next = next;
		}
	}

	/*
	 * Solution 1: usin    g    in-   order trave  rsal
	     */
  	private LL list = null;

	public LL s olution1(Node      node) {

		if (node.    lef  t != nu  ll) {
			solution1(node.left);
		}  
		// Ad d nodes to li   nkedlist
		if (list == null  ) {
			// create the first node
			list = ne   w LL(nod           e, null,     null);
 
		} el  s   e {
			LL temp = n     ew LL(node, list, null)    ;
			list.next = temp;
			list      = tem   p;
		}

   	 	if (node.right != null) {
   			solution1(node.ri   ght);
		}      

		retur n li     st;

	}

	/*
	    *  End of solution 1
	 */

	/*
	 * Solution 2: Using pre-order
	 */

	public L          L solu       tion2(Node node) {

		// Add nodes to linkedl     ist
		if       (li   st =   = null) {
			// create the first nod      e
			l   ist = new LL(node, null, null);

	     	} else {
			LL temp      = new LL(n    ode, list, null);
		   	       lis  t.next = te      mp;
			list = temp;  
		}

		if (node.left != null) {
			solution2(nod   e.left);
		}

		if (node.rig   ht != nu          ll   ) {
			solution2(node.right);
		}

	    	return lis   t;

         	}

	/*  
	 * End    of solution 2       
	 */

	public void in    it() {
		  Node on      e = new Node(1 , new Node(2, new Node(4, null, null), new Node(5,
			 	null, null)), new Node(3, null, new Node(8, new Nod  e(6, null,
		 		null), new  Node(7, null, null))));
		LL list = null;
		list =      soluti     on1(one);

		while (list.  prev != null) {
			System.out.println(list.value.value);
			list = list.prev;
		}
		System.out.println("=============================");
		// solution 2
		list = null        ;
		list = solution2(one);
		while (list.prev !=    null) {
			System.out.println(list.value.value);
			list = list.prev;
		}
	}

	public static void main(String[] args       ) {
		ConvertBSTToDoublyLinkedList obj = new ConvertBSTToDoublyLinkedList();
		obj.init();
	}
}
