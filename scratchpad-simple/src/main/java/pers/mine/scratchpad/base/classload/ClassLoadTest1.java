package pers.mine.scratchpad.base.classload;

public class ClassLoadTest1 {
    static {
        System.out.println("ClassLoadTest1 static exec");
    }

    public static void main(String[] args) {
        new SubClass();
    }

    static class SuperClass {
        public static final String SUPER_STATIC_FIELD = "SUPER_STATIC_FIELD";

        static {
            System.out.println(SUPER_STATIC_FIELD);
        }

        public SuperClass() {
            System.out.println("super init");
        }

        public String superField = "superField";

        {
            System.out.println(superField);
        }

    }

    static class SubClass extends SuperClass {
        public static final String SUB_STATIC_FIELD = "SUB_STATIC_FIELD";

        static {
            System.out.println(SUB_STATIC_FIELD);
        }

        public SubClass() {
            System.out.println("sub init");
        }

        public SubClass(int i) {
            System.out.println("sub init xxx");
            System.out.println("sub init");
        }

        public String subField = "subField";

        {
            System.out.println(subField);
        }
    }
}
