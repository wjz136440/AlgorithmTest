package Test;


import avro.shaded.com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @program: ad-flink
 * @description:
 * @author: joshua.Wang
 * @create: 2019-11-11 14:28
 **/
public class BTree {

    @Data
    @Builder
    private static class BTreeNode {

        private int value;
        private BTreeNode left;
        private BTreeNode right;
    }

    private static int max = Integer.MIN_VALUE;

    /**
     * 获取树深度
     */
    public static int getDepth(BTreeNode node) {

        if (node == null) {
            return 0;
        }
        int left = getDepth(node.getLeft());
        int right = getDepth(node.getRight());
        return Math.max(left, right) + 1;
    }

    /**
     * 获取最小深度 TODO多复习
     *
     * @return
     */
    public static int getMinDepth(BTreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.getLeft() == null) {
            return getMinDepth(root.getRight()) + 1;
        }
        if (root.getRight() == null) {
            return getMinDepth(root.getLeft()) + 1;
        }
        return Math.min(getMinDepth(root.getLeft()), getMinDepth(root.getRight())) + 1;

    }

    //先序遍历
    public static List<Integer> preOrder(BTreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Integer> list = Lists.newArrayList();
//        preOrder1(root,list);
        preOrder2(root, list);
        return list;
    }

    //递归
    private static void preOrder1(BTreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        list.add(node.getValue());
        preOrder1(node.getLeft(), list);
        preOrder1(node.getRight(), list);
    }

    //非递归
    private static void preOrder2(BTreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        Stack<BTreeNode> stack = new Stack<>();
        stack.push(node);
        while (!stack.empty()) {
            //将当前node替换为栈顶node
            BTreeNode temp = stack.pop();
            list.add(temp.getValue());
            //右节点先入栈
            if (temp.getRight() != null) {
                stack.push(temp.getRight());
            }
            if (temp.getLeft() != null) {
                stack.push(temp.getLeft());
            }
        }
    }

    //中序遍历
    public static List<Integer> midOrder(BTreeNode root) {

        if (root == null) {
            return Collections.emptyList();
        }
        List<Integer> list = Lists.newArrayList();
        //midOrder1(root,list);
        midOrder2(root, list);
        return list;
    }

    //递归
    private static void midOrder1(BTreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        midOrder1(node.getLeft(), list);
        list.add(node.getValue());
        midOrder1(node.getRight(), list);
    }

    //非递归
    private static void midOrder2(BTreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        Stack<BTreeNode> stack = new Stack<>();
        BTreeNode temp = node;
        while (temp != null || !stack.empty()) {
            while (temp != null) {
                stack.push(temp);
                temp = temp.getLeft();
            }
            temp = stack.pop();
            list.add(temp.getValue());
            temp = temp.getRight();
        }
    }

    //后序遍历
    public static List<Integer> postOrder(BTreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Integer> list = Lists.newArrayList();
        //postOrder1(root,list);
        postOrder2(root, list);
        return list;
    }

    //递归
    private static void postOrder1(BTreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        postOrder1(node.getLeft(), list);
        postOrder1(node.getRight(), list);
        list.add(node.value);
    }

    //非递归
    private static void postOrder2(BTreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        Stack<BTreeNode> stack = new Stack<>();
        stack.push(node);
        while (!stack.empty()) {
            BTreeNode temp = stack.pop();
            list.add(temp.value);
            if (temp.getLeft() != null) {
                stack.push(temp.getLeft());
            }
            if (temp.getRight() != null) {
                stack.push(temp.getRight());
            }
        }
        //TODO 需反转结果！！！
        Collections.reverse(list);
    }

    //二叉树的层次遍历
    public static List<List<Integer>> searchByRound(BTreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        Queue<BTreeNode> queue = new LinkedList<>();
        List<List<Integer>> resultList = Lists.newArrayList();
        List<Integer> roundList = Lists.newArrayList();
        int round = 0;
        int count = 1;
        //add()和remove()方法在失败的时候会抛出异常(不推荐)
        queue.offer(root);
        while (!queue.isEmpty()) {
            BTreeNode temp = queue.poll();
            roundList.add(temp.getValue());
            round++;
            if (temp.getLeft() != null) {
                queue.offer(temp.getLeft());
            }
            if (temp.getRight() != null) {
                queue.offer(temp.getRight());
            }
            if (round == count) {
                count = queue.size();
                round = 0;
                //此处必须new新list存值放入，否则会一直改变第一个的值
                resultList.add(new ArrayList<>(roundList));
                roundList.clear();
            }
        }
        return resultList;
    }

    /**
     * 求二叉树中节点的最大距离
     * 情况A: 路径经过左子树的最深节点，通过根节点，再到右子树的最深节点。
     * 情况B: 路径不穿过根节点，而是左子树或右子树的最大距离路径，取其大者
     */
    //TODO 需复习
    public static int MaxDistance(BTreeNode root) {
        if (root == null) {
            return 0;
        }
        getMaxDistance(root);
        return max;
    }

    private static int getMaxDistance(BTreeNode node) {
        //空节点的高度为-1
        if (node == null) {
            return -1;
        }
        int leftMax = getMaxDistance(node.getLeft()) + 1;
        int rightMax = getMaxDistance(node.getRight()) + 1;
        int maxDis = leftMax + rightMax;
        max = max > maxDis ? max : maxDis;
        return leftMax > rightMax ? leftMax : rightMax;
    }

    /**
     * 判断二叉树是不是二叉搜索树
     * 若它的左子树不空，则左子树上所有结点的值均小于它的根结点的值；
     * 若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值；
     * 它的左、右子树也分别为二叉排序树。
     *
     * @param root
     * @return
     */
    public static boolean checkBST(BTreeNode root) {
        if (root == null) {
            return true;
        }
        return checkBST1(root);

    }

    private static boolean checkBST1(BTreeNode node) {
        if (node == null) {
            return true;
        }
        if (node.getLeft() != null && node.getLeft().getValue() > node.getValue()) {
            return false;
        }
        if (node.getRight() != null && node.getRight().getValue() < node.getValue()) {
            return false;
        }
        return checkBST1(node.getLeft()) && checkBST1(node.getRight());
    }

    //TODO 二叉搜索树的第k小的节点（中序遍历解决)

    /**
     * 是否是平衡二叉树（Balanced Binary Tree）
     * 一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树
     *
     * @param root
     * @return
     */
    public static boolean checkBTree(BTreeNode root) {
        if (root == null) {
            return true;
        }
        int left = getDepth(root.getLeft());
        int right = getDepth(root.getRight());
        if (Math.abs(left - right) > 1) {
            return false;
        } else {
            return checkBTree(root.getLeft()) && checkBTree(root.getRight());
        }

    }

    /**
     * 判断二叉树是否是完全二叉树(Complete Binary Tree) NOT满二叉树（Perfect Binary Tree）
     * 若设二叉树的深度为k，除第k层外，其他各层（1～（k-1）层）的节点数都达到最大值，且第k层所有的节点都连续集中在最左边，这样的树就是完全二叉树
     * ●某节点没有左孩子，则一定无右孩子
     * ●若某节点缺左或右孩子，则其所有后继一定无孩子
     * 若不满足上述任何一条，均不为完全二叉树。
     */
    public static boolean checkCBT(BTreeNode root) {
        if(root == null){
            return true;
        }
        Queue<BTreeNode> queue = new LinkedList<>();
        queue.offer(root);
        //变量用来标记一个状态是否发生
        //只要当前节点的左孩子和右孩子都为空或者左孩子不为空，右孩子为空时，这个状态就发生，只要发生了这个状态，以后访问到的节点必须都是叶节点
        boolean hasNoChild = false;
        while(!queue.isEmpty()){
            BTreeNode temp = queue.poll();
            if(hasNoChild){
                if(temp.getRight() != null || temp.getLeft() != null){
                    return false;
                }
            }else{
                if(temp.getLeft() != null && temp.getRight() != null){
                    queue.offer(temp.getLeft());
                    queue.offer(temp.getRight());
                }
                else if(temp.getLeft() != null && temp.getRight() == null){
                    queue.offer(temp.getLeft());
                    hasNoChild = true;
                }
                else if(temp.getLeft() == null && temp.getRight() != null){
                    return false;
                }else {
                    hasNoChild = true;
                }
            }
        }
        return false;
    }

    /**
     * 最近公共子节点
     * 在二叉树根结点的左子树和右子树中分别找输入的两个结点，如果两个结点都在左子树，遍历当前结点的左子树，如果两个结点都在右子树，遍历当前结点的右子树，
     * 直到一个在当前结点的左子树，一个在当前结点的右子树，返回当前结点就是最低的公共祖先结点
     */
    private static Integer getNearestNode(BTreeNode root, BTreeNode node1, BTreeNode node2) {
        if (root == null || node1 == null || node2 == null) {
            return null;
        }
        if (node1 == node2) {
            return node1.getValue();
        }

        boolean firstInLeft = nodeInTree(root.getLeft(), node1);
        boolean firstInRight = nodeInTree(root.getRight(), node1);
        boolean secondInleft = nodeInTree(root.getLeft(), node2);
        boolean secondInRight = nodeInTree(root.getRight(), node2);

        //都在左子树
        if (firstInLeft && secondInleft) {
            //此处直接return
            return getNearestNode(root.getLeft(), node1, node2);
        }
        //都在右子树
        if (firstInRight && secondInRight) {
            return getNearestNode(root.getRight(), node1, node2);
        }
        //一左一右，返回root
        if ((firstInLeft && secondInRight) || (firstInRight && secondInleft)) {
            return root.getValue();
        }
        return null;
    }

    private static boolean nodeInTree(BTreeNode root, BTreeNode node) {
        if (root == null || node == null) {
            return false;
        }
        if (root == node) {
            return true;
        }
        return nodeInTree(root.getLeft(), node) || nodeInTree(root.getRight(), node);

    }

    /**
     * 输入一个二叉树和一个整数，打印出从二叉树根节点到叶子节点的路径上所有节点值之和值等于输入整数所有的路径
     */
    public static List<List<Integer>> getValidPath(BTreeNode root, int target) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> resultList = Lists.newArrayList();
        List<Integer> pathList = Lists.newArrayList();
        getValidPath1(root, resultList, pathList, target);
        return resultList;
    }

    private static void getValidPath1(BTreeNode root, List<List<Integer>> resultList, List<Integer> pathList, int target) {
        if (root == null) {
            return;
        }
        pathList.add(root.getValue());
        //为叶子节点，且等于目标值
        if (root.getLeft() == null && root.getRight() == null & target == root.getValue()) {
            resultList.add(new ArrayList<>(pathList));
            //此处不可以直接return!!!因为直接return会无法删去最后的值
            //return;
        }
        if (root.getLeft() != null) {
            getValidPath1(root.getLeft(), resultList, pathList, target - root.getValue());
        }
        if (root.getRight() != null) {
            getValidPath1(root.getRight(), resultList, pathList, target - root.getValue());
        }
        //TODO remove最后一个不满足条件的值！！
        pathList.remove(pathList.size() - 1);
    }


    public static void main(String[] args) {

        //构建Btree
        BTreeNode node6 = BTreeNode.builder().left(null).right(null).value(7).build();
        BTreeNode node5 = BTreeNode.builder().left(null).right(null).value(6).build();
        BTreeNode node4 = BTreeNode.builder().left(node6).right(null).value(5).build();
        BTreeNode node3 = BTreeNode.builder().left(null).right(null).value(4).build();
        BTreeNode node2 = BTreeNode.builder().left(null).right(node5).value(3).build();
        BTreeNode node1 = BTreeNode.builder().left(node3).right(node4).value(2).build();
        BTreeNode root = BTreeNode.builder().left(node1).right(node2).value(1).build();

        //System.out.println("获取最小深度为 " + getMinDepth(root));
        //System.out.println("先序遍历顺序为 " + preOrder(root));
        //System.out.println("中序遍历顺序为 " +midOrder(root));
        //System.out.println("后序遍历顺序为 " +postOrder(root));
        //System.out.println("是否为平衡二叉树 " +checkBTree(root));
        //System.out.println("层次遍历顺序为 " +searchByRound(root));
        //System.out.println("最大节点距离为 " +MaxDistance(root));
        //System.out.println("是否为二叉搜索树 " +checkBST(root));
        //System.out.println("结点node3和node6的最近的公共祖先节点为" + getNearestNode(root, node3, node6));
        //System.out.println("节点值之和值等于输入整数所有的路径为" + getValidPath(root, 10));
        System.out.println("是否为完全二叉树" + checkCBT(root));

    }
}
