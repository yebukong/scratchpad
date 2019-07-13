package pers.mine.scratchpad.other;

/**
 * https://www.zhihu.com/question/329007850
 * @author Mine
 * @date 2019/07/13 23:25:32
 */
public class Outer {
    private String s = "outer.s"; 
    
    public void method(){
        String s = "outer.method.s";
        final String ss = "outer.method.ss";
        final String s1 =s;
        class Inner{
            private String s = "inner.s"; 
            
            public void method() {
                String s = "inner.method.s";
                System.out.println(s);
                System.out.println(this.s);
                //System.out.println(s);//无法直接访问外部类中局部变量
                System.out.println(s1);
                System.out.println(ss);
                System.out.println(Outer.this.s);
            }
        }
        new Inner().method();
    };
    
    public static void main(String[] args) throws InterruptedException {
        new Outer().method();
        
        Thread.sleep(Integer.MAX_VALUE);
    }
}