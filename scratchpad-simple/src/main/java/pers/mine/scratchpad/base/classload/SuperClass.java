package pers.mine.scratchpad.base.classload;

public class SuperClass {
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
