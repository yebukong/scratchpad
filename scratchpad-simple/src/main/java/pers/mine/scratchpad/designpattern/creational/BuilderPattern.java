package pers.mine.scratchpad.designpattern.creational;

/**
 * 建造者模式
 */
public class BuilderPattern {
    public static void main(String[] args) {
        Builder builder = new ProductBuilder();
        builder.setName("X");
        builder.setSize(100);
        builder.setPrice(99);
        Product product = builder.build();
        System.out.println(product);
    }

    static interface Builder {
        void setName(String name);

        void setSize(int size);

        void setPrice(int price);

        Product build();

        public default void test(){
            System.out.println("test");
        }
    }

    static class ProductBuilder   extends BuilderPattern implements Builder,Runnable,Cloneable{
        private Product product;

        public ProductBuilder() {
            product = new Product();
        }

        @Override
        public void setName(String name) {
            product.setName(name);
        }

        @Override
        public void setSize(int size) {
            product.setSize(size);
        }

        @Override
        public void setPrice(int price) {
            product.setPrice(price);
        }

        @Override
        public Product build() {
            return product;
        }

        @Override
        public void run() {

        }
    }

    static class Product {
        String name;
        int size;
        int price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "name='" + name + '\'' +
                    ", size=" + size +
                    ", price=" + price +
                    '}';
        }
    }
}
