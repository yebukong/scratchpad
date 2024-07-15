package pers.mine.scratchpad;

public class RecordTest {
    public  record Person(String name, int age) {
        public static String address;

        public String getName() {
            return name;
        }
    }
    
    public static void main(String[] args) {
        Person mine = new Person("mine", 18);

        System.out.println(mine.age());
        System.out.println(mine.name());
    }
}
