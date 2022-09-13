package pers.mine.scratchpad.designpattern.creational;

/**
 * 工厂方法
 */
public class FactoryMethodPattern {

    public static void main(String[] args) {
        // 这一步可以使用SPI及反射等技术，不然又引入了Factory的硬编码实现，属实多此一举了
        Factory factory = new ProductFactoryA();
        Product product = factory.createProduct();
        System.out.println(product.name());
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

    static interface Factory {
        Product createProduct();
    }

    static class ProductFactoryB implements Factory {
        @Override
        public Product createProduct() {
            return new ProductB();
        }
    }

    static class ProductFactoryA implements Factory {
        @Override
        public Product createProduct() {
            return new ProductA();
        }
    }
}
