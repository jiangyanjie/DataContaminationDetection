package code;

class Construct_Binary_Tree_from_Inorder_and_Postorder_Traversal{
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if(inorder.length == 0 && postorder.length == 0) return null;
        TreeNode node = buildTreeRec(inorder, postorder, 0, inorder.length-1, 0, postorder.length-1);
        return node;
    }

    public TreeNode buildTreeRec(int[] inorder, int[] postorder, int s1, int e1, int s2, int e2) {
        if(e1 == s1 && s2 == e2) return new TreeNode(inorder[s1]);

        if(postorder[e2] == inorder[s1]){
            TreeNode root = new TreeNode(postorder[e2]);
            TreeNode right = buildTreeRec(inorder, postorder, s1+1, e1, e2-1-(e1-(s1+1)), e2-1);
            root.right = right;
            return root;
        }

        if(postorder[e2] == inorder[e1]){
            TreeNode root = new TreeNode(postorder[e2]);
            TreeNode left = buildTreeRec(inorder, postorder, s1, e1-1, s2, e2-1);
            root.left = left;
            return root;
        }

        int i = s1;
        for(; i <= e1; ++i){
            if(inorder[i] == postorder[e2]) break;
        }

        TreeNode root = new TreeNode(postorder[e2]);
        TreeNode left = buildTreeRec(inorder, postorder, s1, i-1, s2, s2+(i-1-s1));
        root.left = left;

        TreeNode right = buildTreeRec(inorder, postorder, i+1, e1, e2-1-(e1-(i+1)), e2-1);
        root.right = right;
        return root;
    }

    public static void main(String[] args){
        System.out.println(new Construct_Binary_Tree_from_Inorder_and_Postorder_Traversal().buildTree(
                new int[]{1}, new int[]{1}));
    }
}
