package com.dev.saurabh.leetcode;

/**
 * Copyright (c)
 * User: saurabh
 * Date: 10/18/12
 * Time: 11:51 AM
 * This file is created and owned by Saurabh Kr Singh (saurabh.nsit@gmail.com).
 * The code written here is being released under Apache 2.0 License
 */


public class ConstructBinaryTreeInorderPreorder {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public static void main(String [] args)
    {
        ConstructBinaryTreeInorderPreorder instance = new ConstructBinaryTreeInorderPreorder();
        TreeNode res = instance.buildTree(new int[]{1,3,2,4}, new int[]{1,2,3,4});
        display(res);
    }

    private static void display(TreeNode res) {
        if(res == null)
        {
            return;
        }


        display(res.left);
        System.out.print(res.val);
        display(res.right);
    }

    private int preIndex  = 0;



    public TreeNode buildTree(int[] preorder, int[] inorder) {
        preIndex = 0;
        if(inorder == null || inorder.length == 0)
        {
            return null;
        }

        return getBinaryTree(preorder, inorder, 0, inorder.length-1);
    }

    private TreeNode getBinaryTree(int[] preorder, int[] inorder, int start, int end) {

       if(preIndex < preorder.length)
       {
           if(start > end)
           {
               return null;
           }

           TreeNode node = new TreeNode(preorder[preIndex++]);

           if(start == end)
           {
               return node;
           }

           int index = -1;
           for(index = start; index <= end; index++)
           {
               if(inorder[index] == node.val)
               {
                   break;
               }
           }

           if(index == -1)
           {
               return null;
           }

           node.left = getBinaryTree(preorder, inorder, start, index-1);
           node.right = getBinaryTree(preorder, inorder, index+1, end);

           return node;
       }

        return null;
    }
}
