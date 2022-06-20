public class ClassB {
    static {
        System.out.println("ClassB start");
        if ("s".equals("s")) {
            throw new RuntimeException("静态代码块执行异常");
        }
    }
}
