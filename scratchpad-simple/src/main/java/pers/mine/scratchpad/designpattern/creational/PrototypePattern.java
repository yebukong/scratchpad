package pers.mine.scratchpad.designpattern.creational;

/**
 * 原型模式
 */
public class PrototypePattern {

    public static void main(String[] args) {
        ConcretePrototype a = new ConcretePrototype("a");
        System.out.println(a);
        System.out.println(a.copy());
    }

    static interface Prototype {
        Prototype copy();
    }

    static class ConcretePrototype implements Prototype, Cloneable {
        String name;

        public ConcretePrototype(String name) {
            this.name = name;
        }

        @Override
        public Prototype copy() {
            try {
                return (Prototype) this.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String toString() {
            return "ConcretePrototype{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
