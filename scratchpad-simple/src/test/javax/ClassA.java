
public class ClassA {
    static {
        System.out.println("ClassA");
    }

    static Inner inner = new Inner();

    public static void main(String[] args) {
        System.out.println(ClassA.inner);
    }

    static class Inner {
        static {
            System.out.println("inner");
        }
    }
}
