package pers.mine.scratchpad.base.string;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * 郝夫曼树
 */
public class HuffmanTree {
    public Node root;
    public String[] codeArr = new String[256];

    public static void main(String[] args) {
        HuffmanTree tree = new HuffmanTree();
        int[] weightArr = new int[256];
        weightArr['A'] = 27;
        weightArr['B'] = 8;
        weightArr['C'] = 15;
        weightArr['D'] = 15;
        weightArr['E'] = 30;
        weightArr['F'] = 5;
        tree.buildTree(weightArr);
        String[] codes = tree.codeArr;
        for (int i = 0; i < codes.length; i++) {
            if (codes[i] != null) {
                System.out.println(StrUtil.format("{} : {}", (char) i, codes[i]));
            }
        }
        //验证
        String encode = tree.encode("BADCADFEED".toCharArray());
        System.out.println(encode);
        char[] decode = tree.decode(encode);
        System.out.println(Arrays.toString(decode));
    }

    public static int[] genWeightArr(char[] cs) {
        int[] weightArr = new int[256];
        for (char c : cs) {
            Assert.isTrue(((int) c) <= 256, "不支持的字符{}", c);
            weightArr[c]++;
        }
        return weightArr;
    }

    public void buildTree(int[] weightArr) {
        //基于优先队列 构造树
        Queue<Node> q = new PriorityQueue<>();
        for (int i = 0; i < weightArr.length; i++) {
            if (weightArr[i] > 0) {
                Node node = new Node((char) i, weightArr[i], null, null);
                q.offer(node);
            }
        }
        while (q.size() > 1) {
            Node min1 = q.poll();
            Node min2 = q.poll();
            Node n = new Node(null, min1.weight + min2.weight, min1, min2);
            //将新形成的节点插入到队列中
            q.offer(n);
        }
        //最后一个节点为根节点
        root = q.poll();
        //解析编码
        buildCode(new Stack<>(), root);
    }

    void buildCode(Stack<Character> stack, Node node) {
        if (node.isLeaf()) {
            StringBuilder code = new StringBuilder();
            for (Character character : stack) {
                code.append(character);
            }
            codeArr[node.c] = code.toString();
        } else {
            stack.push('0');
            buildCode(stack, node.left);
            stack.pop();
            stack.push('1');
            buildCode(stack, node.right);
            stack.pop();
        }
    }

    public String encode(char[] cs) {
        StringBuilder bitString = new StringBuilder();
        for (char c : cs) {
            bitString.append(codeArr[c]);
        }
        return bitString.toString();
    }

    public char[] decode(String bitString) {
        StringBuilder cs = new StringBuilder();
        Node curr = root;
        char[] chars = bitString.toCharArray();
        for (char c : chars) {
            curr = (c == '0') ? curr.left : curr.right;
            if (curr.isLeaf()) {
                cs.append(curr.c);
                curr = root;
            }
        }
        return cs.toString().toCharArray();
    }

    public class Node implements Comparable<Node> {
        public final Character c;
        public final int weight;
        public final Node left;
        public final Node right;
        public final String name;

        public Node(Character c, int weight, Node left, Node right) {
            this.weight = weight;
            this.left = left;
            this.right = right;
            this.c = c;
            if (c == null) {
                name = StrUtil.format("({})({})", left.name, right.name);
            } else {
                name = c + "";
            }
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(Node node) {
            if (this.weight == node.weight) {
                return this.name.compareTo(node.name);
            } else {
                return this.weight - node.weight;
            }
        }

        @Override
        public String toString() {
            return StrUtil.format("{}<{}>", name, weight);
        }
    }
}
