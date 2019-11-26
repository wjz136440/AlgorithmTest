package Test;

import avro.shaded.com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @program: ad-flink
 * @description:
 * @author: joshua.Wang
 * @create: 2019-11-11 19:45
 **/
public class LinkList {

    @Data
    @Builder
    private static class LinkNode {
        private int value;
        private LinkNode next;
    }

    //链表反转
    public static List<Integer> reverse(LinkNode root) {

        List<Integer> result = Lists.newArrayList();
        root = reverse1(root);
        while (root != null) {
            result.add(root.getValue());
            root = root.getNext();
        }
        return result;
    }

    private static LinkNode reverse1(LinkNode root) {
        //参照节点
        LinkNode res = LinkNode.builder().next(root).build();
        LinkNode first = root;
        while (first.getNext() != null) {
            LinkNode temp = first.getNext();
            first.setNext(temp.getNext());
            temp.setNext(res.getNext());
            res.setNext(temp);
        }
        return res.getNext();
    }

    /**
     * 给定一个单链表的头节点 head,实现一个调整单链表的函数，使得每K个节点之间为一组进行逆序，并且从链表的尾部开始组起，头部剩余节点数量不够一组的不需要逆序
     */
    public static List<Integer> partReverse(LinkNode root, int k) {
        //先逆序
        root = reverse1(root);
        //部分逆序
        root = reverse2(root, k);
        //再全部逆序
        root = reverse1(root);
        List<Integer> result = Lists.newArrayList();
        while (root != null) {
            result.add(root.getValue());
            root = root.getNext();
        }
        return result;

    }

    private static LinkNode reverse2(LinkNode node, int k) {
        if (node == null) {
            return null;
        }
        LinkNode res = node;
        for (int i = 1; i < k; i++) {
            res = res.getNext();
        }
        if (res == null) {
            return node;
        }
        //定位截断k个后的第一个next
        LinkNode temp = res.getNext();
        //将前k个截断
        res.setNext(null);
        //新设置一个head当作反转之后的头，node结点为反转后的最后结点
        LinkNode head = reverse1(node);
        //递归截断后的链表
        LinkNode newTemp = reverse2(temp, k);
        //将尾部结点和结果链接
        node.setNext(newTemp);
        return head;
    }

    /**
     * 公共结点
     * 在公共结点之后，两个链表指针指向的地址是相同的。
     *
     * 我们也可以先让把长的链表的头砍掉，让两个链表长度相同，这样，同时遍历也能找到公共结点。此时，时间复杂度O(m+n)，空间复杂度为O(MAX(m,n))。
     */
    public static int getBothNode(LinkNode l1, LinkNode l2) {
        return 0;
    }

    /**
     * 复制一个复杂链表。在复杂链表中，每个结点除了有一个m_pNext指针指向下一个结点外，还有一个m_pSibling指向链表中的任意结点或者NULL。
     * 思路1：
     * 第一步仍然是复制原始链表上的每个结点N创建N'，然后把这些创建出来的结点用m_pNext链接起来。同时我们把<N, N'>的配对信息放到一个哈希表中。
     * 第二步还是设置复制链表上的每个结点的m_pSibling。
     *
     * 优化：
     * 第一步：让仍然是根据原始链表的每个结点N创建对应的N'。不过我们把N’链接在N的后面。
     * 第二步：设置复制出来的结点的m_pSibling。原始链表上的A的m_pSibling指向结点C，那么其对应复制出来的A’是A的m_pNext指向的结点，同样C’也是C的m_pNext指向的结点。
     * 第三步：将这个长链表拆分成两个链表：把奇数位置的结点用m_pNext链接起来就是原始链表，把偶数位置的结点用m_pNext链接起来就是复制出来的链表。
     */
    public LinkNode copy(){
        return null;
    }

    public static void main(String[] args) {
        LinkNode node7 = LinkNode.builder().value(8).next(null).build();
        LinkNode node6 = LinkNode.builder().value(7).next(node7).build();
        LinkNode node5 = LinkNode.builder().value(6).next(node6).build();
        LinkNode node4 = LinkNode.builder().value(5).next(node5).build();
        LinkNode node3 = LinkNode.builder().value(4).next(node4).build();
        LinkNode node2 = LinkNode.builder().value(3).next(node3).build();
        LinkNode node1 = LinkNode.builder().value(2).next(node2).build();
        LinkNode root = LinkNode.builder().value(1).next(node1).build();

        //System.out.println(reverse(root));
        System.out.println(partReverse(root, 3));
    }
}
