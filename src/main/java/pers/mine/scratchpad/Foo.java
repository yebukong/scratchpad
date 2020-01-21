package pers.mine.scratchpad;

/**
 * debug 对程序本身的干扰
 * @author Mine
 */
public class Foo {
    public static void main(String[] args) {
        Person person = new Person();
        person.setName("张三");
        person.setAge(30);
        System.out.println(person.toString());
    }
}

class Person {
    private String name;
    private int age;
    private StringBuilder nameDisPlay;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        if (this.nameDisPlay == null) {
            this.nameDisPlay = new StringBuilder();
            this.nameDisPlay
                    .append("我的名字叫：")
                    .append(this.name)
                    .append("，")
                    .append("我今年")
                    .append(this.age)
                    .append("岁");
        }
        return this.nameDisPlay.toString();
    }
}
