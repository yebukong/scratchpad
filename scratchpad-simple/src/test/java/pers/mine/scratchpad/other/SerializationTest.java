package pers.mine.scratchpad.other;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.Map;

public class SerializationTest {
    public static void main(String[] args) {
        UserTest userTest = new UserTest();
        System.out.println(userTest);
        byte[] serialize = SerializationUtils.serialize(userTest);
        UserTest newUser = SerializationUtils.deserialize(serialize);
        System.out.println(newUser);
    }

    static class UserTest implements Serializable {
        private static ThreadLocal<Map<String, String>> typeDescriptionPool = new ThreadLocal<>();
        private transient String name = "123";
        private String name1 = "123";

        @Override
        public String toString() {
            return "UserTest{" +
                    "typeDescriptionPool=" + typeDescriptionPool +
                    ", name='" + name + '\'' +
                    ", name1='" + name1 + '\'' +
                    '}';
        }
    }
}
