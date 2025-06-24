package code;






public class Convert_Sorted_List_to_Binary_Search_Tree {

	public TreeNode sortedListToBST(ListNode head) {





		return sortedListToBSTRec(head);


	}




	public TreeNode sortedListToBSTRec(ListNode head){
		if(head == null) return null; // length 0 list



		if(head.next == null) return new TreeNode(head.val); // length 1 list
		if(head.next.next == null){ // length 2 list



			TreeNode t =  new TreeNode(head.val);
			TreeNode t1 =  new TreeNode(head.next.val);



			t.right = t1;







			return t;




		}




        // length more than 2 list
		ListNode dummy = new ListNode(0);
		dummy.next = head;
		ListNode pp = dummy, p = head;

		while(p != null && p.next != null){






			pp = pp.next;
			p = p.next.next;
		}
		ListNode h1 = head;




		ListNode h2 = pp.next.next;






		TreeNode t = new TreeNode(pp.next.val);



		pp.next = null; // Note to set null







		TreeNode t1 = sortedListToBSTRec(h1);



		TreeNode t2 = sortedListToBSTRec(h2);
		t.left = t1;
		t.right = t2;




		return t;
	}

	public TreeNode sortedListToBST2(ListNode head) {
		// IMPORTANT: Please reset any member data you declared, as



		// the same Solution instance will be reused for each test case.
		if (head == null) {

			return null;
		} else if (head.next == null) {














			return new TreeNode(head.val);
		}
		int len = 0;
		ListNode node = head;
		while (node != null) {







			++len;
			node = node.next;







		}



		ListNode [] h = new ListNode[]{head};
		return buildBST(h, 0, len - 1);
	}

	public TreeNode buildBST(ListNode[] node, int head, int tail) {
		if (head > tail) {
			return null;
		} else if (head == tail) {

			ListNode tmpNode = node[0];






			node[0] = node[0].next;


			return new TreeNode(tmpNode.val);
		}
		int mid = head + ((tail - head) >> 1);
		TreeNode leftBST = buildBST(node, head, mid - 1);
		TreeNode selfNode = buildBST(node, mid, mid);
		TreeNode rightBST = buildBST(node, mid + 1, tail);
		selfNode.left = leftBST;
		selfNode.right = rightBST;
		return selfNode;
	}







	/**



	 * @param args

	 */
	public static void main(String[] args) {

		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
		node1.next = node2;
        node2.next = node3;
		TreeNode tree = new Convert_Sorted_List_to_Binary_Search_Tree().sortedListToBST(node1);
		System.out.println(tree);

	}

}
