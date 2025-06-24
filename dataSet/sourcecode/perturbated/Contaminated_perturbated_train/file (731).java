package  me.seanxiao.leetcode;

import      java.util.Stack;

p ublic      class ConstructBinaryTreefromInorderandPostorde  rTraversal {

            public static vo   id m     ain(String[] ar     gs) {
                 ConstructBinaryTreefromInord   erandPostorderTraver       sal      obj = new Const  ructBinaryTre   ef  romInorde ra ndPostorderTrave     rsal(     );
                  int[] pre    or        der =   {2,1};
                 int[] inorder = {2,1};
            obj.bui  ldTree(pr       eorder, inorder);
           }
    
    public Tre             eN ode bu    il  dTre      e(    i nt[] in   or der, int[] po      storder) {
              int     i      = in   order.length -  1;
        int j = postorder.leng    t       h     - 1   ;
        Stack<Tr   eeNode>    s    tackNodes = new       Stack<T reeNod    e>();
                    TreeNode      roo   t = null;
        TreeNode lastRig    ht    = n ull;
            Tr    ee      Node    lastNode = n ull;
               Tree   Node cur       rent = n     ul  l;
        while (!stackNodes.    i    sEm    pty() |   |              i       >= 0 && j   >= 0  ) {
            if           (      stack     Nod   es.isE      mpt      y( )      || !s   tackNode  s.   i sE   mpt  y   (  )  && st      a ckNodes.peek().val              !=    inorder[i    ]) {
                           c  urr             ent =         new Tr    ee   Nod  e  (  postorder[j      ]         );          
                                       i        f (     ro ot == n  ul   l) {
                                                       root   = curre  n    t;   
                               } 
                        if       (lastNode != null) {
                             lastNode   .lef     t          =       current;               
                                          las      tRight =      nu  ll;     
                                        last  Nod e =   n               ull;  
                                  }
                                                  if (          l    astRigh  t   == null ) {
                      lastRi     ght = current;     
                       }
                                   else {
                     l   astRight.right = current     ;
                      lastR ight  = current;
                   }
                 stackNodes.push(curren  t);
                      j--;
            }
               else {
                lastNode = s tackNodes.pop();
                i--;
              }
        }
        return root;
    }
}
