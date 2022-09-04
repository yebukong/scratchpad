package pers.mine.scratchpad.designpattern.creational;

/**
 * 简单工厂
 */
public class SimpleFactoryPattern {

    public static void main(String[] args) {
        Product a = Factory.createProduct("A");
        System.out.println(a.name());
    }

    static interface Product {
        String name();
    }

    static class ProductA implements Product {

        @Override
        public String name() {
            return "A";
        }
    }

    static class ProductB implements Product {

        @Override
        public String name() {
            return "B";
        }
    }

    static class Factory {
        public static Product createProduct(String type) {
            if ("A".equalsIgnoreCase(type)) {
                return new ProductA();
            } else if ("B".equalsIgnoreCase(type)) {
                return new ProductB();
            } else {
                throw new IllegalArgumentException("UNKOWN TYPE :" + type);
            }
        }
    }
}
