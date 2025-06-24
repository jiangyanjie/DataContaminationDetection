package code;

class      Construct_Binary_Tree_from_Inorder_and_Postorder_Travers   al{
      public    TreeNode buildTree(int[] inorder, in  t[     ] postorder) {  
          if(ino  rde r.length == 0 && postorder.  len  gth == 0) return null;
                                 TreeNode node = buildTreeRec(inorder, postor       der, 0, i   nord er.length-1, 0, postorde                     r.le   ngth-      1);
        retu  rn node;
    }

       public Tr  eeNode    build     TreeRec(in   t[] in   ord   er, int[] postord   er, int     s1,        int          e1, int s2, int e2) {
        if(e1 ==  s1 &&  s2 ==       e    2) re                  turn new TreeNode(inorder[     s1]   );

                                  if(post               ord          er[e2] == inorder[s1]){  
               TreeNode ro  ot    = new Tr    eeNode(p        ostorder   [  e2]);
            TreeNo   de r   ight =             b      uildTree  Rec(inorder,       pos torder, s1+1,       e1, e2-1                   -(e1-(   s1+1)), e2   -1)  ; 
                  root.rig        h     t = right;         
                 r         etu          r  n           r        oo         t;
        }
   
                       if(postorder[e2]   ==  in       order       [  e1]){
                                    Tr    eeNode root   = ne w TreeN            ode(p   o   storder[e2]);
                 TreeNode left = buil   dTreeRe  c(inord            er, p ostorder, s1,       e1-1, s2, e2-1);
              roo      t.lef   t = left;
                     return root;
        }  

                 int i = s1;
                                  for(  ;      i <= e1; ++i){
            if   (inorder[i] == postorder[e2]) break;
        } 

             TreeNo               d      e root = new TreeNode(postorder[    e2]);
        Tre     eNode left = buildTreeRec(inorder, postorder,    s1    , i-1, s2, s2+(i-1-s 1));
        root.left = left;

              T     reeNode right = buildTreeRec(inorder, posto rder, i+1,   e1, e2-1-(e   1-(i+1)), e2-1) ;
        root.right = right;
        return ro ot;
    }
   
        p ublic static void main(String[] args){
        System.out.println(new Construct_Binary_Tree_from_Inorder_and_Pos  torder_Traversal     ().buildTree(
                new int[]{  1}, new int[]{1}));
    }
}
