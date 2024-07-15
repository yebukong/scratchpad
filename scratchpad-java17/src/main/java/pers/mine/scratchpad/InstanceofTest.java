package pers.mine.scratchpad;

import java.util.*;

public class InstanceofTest {
    public static void main(String[] args) {

    }

    void oldInstanceof(Collection<String> c) {
        if (c instanceof List<String>) {
            // 需要强转
            List<String> l = (List<String>) c;
            ListIterator<String> stringListIterator = l.listIterator();
        } else if (c instanceof Queue<String>) {
            Queue<String> q = (Queue<String>) c;
            Iterator<String> iterator = q.iterator();
        }
    }

    void newInstanceof(Collection<String> c) {
        if (c instanceof List<String> l) {
            // 直接使用声明的变量
            ListIterator<String> stringListIterator = l.listIterator();
        } else if (c instanceof Queue<String> q) {
            Iterator<String> iterator = q.iterator();
        }
    }
}
