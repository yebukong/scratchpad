package pers.mine.scratchpad.base.classload;

public class HumanTest {
    static abstract class Human {
    }

    static class Man extends Human {
    }

    static class WuMan extends Human {
    }

    public void say(Human x) {
        System.out.println("Human");
    }

    public void say(Man x) {
        System.out.println("Man");
    }

    public void say(WuMan x) {
        System.out.println("WuMan");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human wuMan = new WuMan();
        HumanTest humanTest = new HumanTest();
        humanTest.say(man);
        humanTest.say(wuMan);

    }
}
