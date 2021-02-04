package pers.mine.scratchpad.base.classload;

public class SubClass extends SuperClass {
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
