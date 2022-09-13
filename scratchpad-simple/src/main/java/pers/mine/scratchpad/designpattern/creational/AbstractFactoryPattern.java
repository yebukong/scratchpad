package pers.mine.scratchpad.designpattern.creational;

public class AbstractFactoryPattern {

    public static void main(String[] args) {
        // 这一步可以使用SPI及反射等技术，不然又引入了Factory的硬编码实现，属实多此一举了
        Factory factory = new ProductFactory1();
        System.out.println(factory.createProductX().name());
        System.out.println(factory.createProductY().name());
    }

    static interface ProductX {
        String name();

    }

    static class ProductX1 implements ProductX {

        @Override
        public String name() {
            return "X1";
        }
    }

    static class ProductX2 implements ProductX {

        @Override
        public String name() {
            return "X2";
        }
    }

    static interface ProductY {
        String name();
    }

    static class ProductY1 implements ProductY {

        @Override
        public String name() {
            return "Y1";
        }
    }

    static class ProductY2 implements ProductY {

        @Override
        public String name() {
            return "Y2";
        }
    }

    static interface Factory {
        ProductX createProductX();

        ProductY createProductY();
    }

    static class ProductFactory1 implements Factory {
        @Override
        public ProductX createProductX() {
            return new ProductX1();
        }

        @Override
        public ProductY createProductY() {
            return new ProductY1();
        }
    }

    static class ProductFactory2 implements Factory {
        @Override
        public ProductX createProductX() {
            return new ProductX2();
        }

        @Override
        public ProductY createProductY() {
            return new ProductY2();
        }
    }
}
