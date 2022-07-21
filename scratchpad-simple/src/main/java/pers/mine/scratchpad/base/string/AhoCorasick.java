package pers.mine.scratchpad.base.string;

import java.util.LinkedList;
import java.util.Queue;

/**
 * AC自动机
 */
public class AhoCorasick {
    private final AcNode root = new AcNode('/'); // 根结点无数据

    /**
     * Fail指针填充
     */
    public void buildFailurePointer() {
        Queue<AcNode> queue = new LinkedList<>();
        root.fail = null;
        queue.add(root);
        //广度优先，遍历每一层节点
        while (!queue.isEmpty()) {
            AcNode p = queue.remove();
            for (int i = 0; i < 26; ++i) {
                AcNode pc = p.children[i];
                //跳过空节点
                if (pc == null)
                    continue;
                ///根结点的所有孩子结点的fail都指向根结点
                if (p == root) {
                    pc.fail = root;
                } else {
                    //获取父节点的fail指针
                    AcNode q = p.fail;
                    while (q != null) {
                        // q.children[pc.data - 'a'] 表示父节点fail指针的和当前字符子节点
                        // 不为空，说明存在，即pc.data=qc.data，说明找到了fail指针
                        // 若没有找到，则递归查找fail指针的fail指针(q = q.fail)，直到为空
                        AcNode qc = q.children[pc.data - 'a'];
                        if (qc != null) {
                            pc.fail = qc;
                            break;
                        }
                        q = q.fail;
                    }
                    //未找到，则pc节点fail指向root
                    if (q == null) {
                        pc.fail = root;
                    }
                }
                queue.add(pc);
            }
        }
    }

    public void insert(char[] text) {
        AcNode p = root;
        for (int i = 0; i < text.length; ++i) {
            int index = text[i] - 'a';
            if (p.children[index] == null) {
                AcNode newNode = new AcNode(text[i]);
                p.children[index] = newNode;
            }
            p = p.children[index];
        }
        p.length = text.length;
        p.isEndingChar = true;
    }

    /**
     * @param target 主串
     */
    public void match(char[] target) {
        int n = target.length;
        AcNode p = root;
        for (int i = 0; i < n; ++i) {
            int idx = target[i] - 'a';
            // p.children[idx] 表示模式串p节点的子节点中是否存在和当前字符相等的字符
            // 如果有（!=null)，说明匹配到了，后续会验证是否为完整的匹配串
            // 如果没有(==null)，尝试寻找失败指针指向位置的节点
            while (p.children[idx] == null && p != root) {
                p = p.fail; // 失败指针发挥作用的地方
            }
            p = p.children[idx];
            if (p == null)
                p = root; // 如果没有匹配的，从root开始重新匹配

            /**打印匹配到的字符**/
            AcNode tmp = p;
            while (tmp != root) { // 打印出可以匹配的模式串
                if (tmp.isEndingChar) {
                    int pos = i - tmp.length + 1;
                    System.out.println("匹配起始下标" + pos + "; 长度" + tmp.length);
                }
                tmp = tmp.fail;
            }
        }
    }

    public void match0(char[] target) {
        AcNode p = root;
        int i = 0;
        while (i < target.length) {
            int idx = target[i] - 'a';
            //匹配到则进行下一位字符的比较
            if (p.children[idx] != null) {
                i++;
                p = p.children[idx];
            } else {
                AcNode nextNode = p.fail;
                //没有匹配，且fail指针为空，整体p重置，且主串起始索引放到下一位
                if (nextNode.fail == null) {
                    p = root;
                    i++;
                } else {
                    p = nextNode;
                }
            }
            AcNode tmp = p;
            while (tmp != root) { // 打印出可以匹配的模式串
                if (tmp.isEndingChar) {
                    int pos = i - tmp.length;
                    System.out.println("匹配起始下标" + pos + "; 长度" + tmp.length);
                }
                tmp = tmp.fail;
            }
        }
    }

    public static void main(String[] args) {
        AhoCorasick ahoCorasick = new AhoCorasick();
        ahoCorasick.insert("ab".toCharArray());
        ahoCorasick.insert("bcd".toCharArray());
        ahoCorasick.buildFailurePointer();
        ahoCorasick.match("abcde".toCharArray());
        ahoCorasick.match0("abcde".toCharArray());
    }

    static class AcNode {
        public char data;
        public AcNode[] children = new AcNode[26]; // 字符集只包含a~z这26个字符
        public boolean isEndingChar = false; // 结尾字符为true
        public int length = -1; // 当isEndingChar=true时，记录模式串长度
        public AcNode fail; // 失败指针

        public AcNode(char data) {
            this.data = data;
        }
    }
}
