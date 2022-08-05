package pers.mine.scratchpad.base.string;

/**
 * 字典树
 */
public class Trie {
    private TrieNode root = new TrieNode(); // 根结点无数据

    /**
     * 插入字符串
     */
    public void insert(char[] text) {
        TrieNode p = root;
        for (int i = 0; i < text.length; ++i) {
            int index = text[i] - 'a';
            if (p.children[index] == null) {
                TrieNode newNode = new TrieNode();
                p.children[index] = newNode;
            }
            p = p.children[index];
        }
        p.isEndingChar = true;
    }

    /**
     * 查找字符串
     */
    public boolean find(char[] pattern) {
        TrieNode p = root;
        for (int i = 0; i < pattern.length; ++i) {
            int index = pattern[i] - 'a';
            if (p.children[index] == null) {
                return false; // 不存在pattern
            }
            p = p.children[index];
        }
        if (p.isEndingChar == false)
            return false; // 不能完全匹配，只是前缀
        else
            return true; // 找到pattern
    }

    static class TrieNode {
        TrieNode children[] = new TrieNode[26];
        public boolean isEndingChar = false;

        public TrieNode() {
        }
    }
}
