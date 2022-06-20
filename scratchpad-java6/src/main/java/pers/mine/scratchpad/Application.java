package pers.mine.scratchpad;

public class Application {
    public static void main(String[] args) {
        String str = new String("test") + new String("Intern");
        String internStr = str.intern();
        System.out.println(str == internStr);
        System.out.println("str hashCode : " + System.identityHashCode(str));
        System.out.println("internStr hashCode : " + System.identityHashCode(internStr));
    }
}
