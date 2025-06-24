package me.seanxiao.leetcode;

import java.util.Stack;

public class ConstructBinaryTreefromInorderandPostorderTraversal {

    public static void main(String[] args) {
        ConstructBinaryTreefromInorderandPostorderTraversal obj = new ConstructBinaryTreefromInorderandPostorderTraversal();
        int[] preorder = {2,1};
        int[] inorder = {2,1};
        obj.buildTree(preorder, inorder);
    }
    
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int i = inorder.length - 1;
        int j = postorder.length - 1;
        Stack<TreeNode> stackNodes = new Stack<TreeNode>();
        TreeNode root = null;
        TreeNode lastRight = null;
        TreeNode lastNode = null;
        TreeNode current = null;
        while (!stackNodes.isEmpty() || i >= 0 && j >= 0) {
            if (stackNodes.isEmpty() || !stackNodes.isEmpty() && stackNodes.peek().val != inorder[i]) {
                current = new TreeNode(postorder[j]);
                if (root == null) {
                    root = current;
                }
                if (lastNode != null) {
                    lastNode.left = current;
                    lastRight = null;
                    lastNode = null;
                }
                if (lastRight == null) {
                    lastRight = current;
                }
                else {
                    lastRight.right = current;
                    lastRight = current;
                }
                stackNodes.push(current);
                j--;
            }
            else {
                lastNode = stackNodes.pop();
                i--;
            }
        }
        return root;
    }
}
