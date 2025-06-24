package code;

class Construct_Binary_Tree_from_Preorder_and_Inorder_Traversal{
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder.length == 0 && inorder.length == 0) return null;
        TreeNode node = buildTreeRec(preorder, inorder, 0, preorder.length-1, 0, inorder.length-1);
        return node;
    }

    public TreeNode buildTreeRec(int[] preorder, int[] inorder, int s1, int e1, int s2, int e2) {
        if(e1 == s1 && s2 == e2) return new TreeNode(inorder[s2]);
        if(preorder[s1] == inorder[s2]){
            TreeNode root = new TreeNode(preorder[s1]);
            TreeNode right = buildTreeRec(preorder, inorder, s1+1, e1, s2+1, e2);
            root.right = right;
            return root;
        }
        if(preorder[s1] == inorder[e2]){
            TreeNode root = new TreeNode(preorder[s1]);
            TreeNode left = buildTreeRec(preorder, inorder, s1+1, e1, s2, e2-1);
            root.left = left;
            return root;
        }

        int i = s2;
        for(; i <= e2; ++i){
            if(inorder[i] == preorder[s1]) break;
        }
        TreeNode root = new TreeNode(preorder[s1]);
        TreeNode left = buildTreeRec(preorder, inorder, s1+1, s1+1+(i-1-s2), s2, i-1);
        TreeNode right = buildTreeRec(preorder, inorder, s1+1+(i-1-s2)+1, e1, i+1, e2);
        root.left = left;
        root.right = right;
        return root;
    }

    public static void main(String[] args){
        System.out.println(new Construct_Binary_Tree_from_Preorder_and_Inorder_Traversal().buildTree(
                new int[]{}, new int[]{}));
    }
}
