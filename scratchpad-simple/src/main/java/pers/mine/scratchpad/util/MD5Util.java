package pers.mine.scratchpad.util;

import java.security.MessageDigest;

/**
 * @author yebukong
 * @description MD5相关
 * @since 2019-01-16 19:28
 */
public class MD5Util {

    /**
     * 加盐MD5
     *
     * @param sources        待hash字符串
     * @param salt           盐
     * @param hashIterations 重复次数
     * @return 参考自shiro加盐算法 org.apache.shiro.crypto.hash.SimpleHash.hash(byte[], byte[], int)
     */
    public static String entcryptMD5HexWithSalt(String sources, String salt, int hashIterations) {
        if (StringX.isEmpty(sources)) {
            throw new IllegalArgumentException("Arg sources is null!");
        }
        if (StringX.isEmpty(salt)) {
            throw new IllegalArgumentException("Arg salt is null!");
        }
        if (hashIterations < 1) {
            throw new IllegalArgumentException("Arg hashIterations is null!");
        }
        byte[] hashed;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(salt.getBytes());
            hashed = digest.digest(sources.getBytes());
            int iterations = hashIterations - 1;
            for (int i = 0; i < iterations; i++) {
                digest.reset();
                hashed = digest.digest(hashed);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("计算加盐MD5过程发生异常!", e);//将异常转换为运行异常
        }
        return byteToHex(hashed);
    }
//shiro 的写法
//    protected byte[] hash(byte[] bytes, byte[] salt, int hashIterations) throws  NoSuchAlgorithmException {
//        MessageDigest digest = MessageDigest.getInstance("MD5");
//        if (salt != null) {
//            digest.reset();
//            digest.update(salt);
//        }
//        byte[] hashed = digest.digest(bytes);
//        int iterations = hashIterations - 1; //already hashed once above
//        //iterate remaining number:
//        for (int i = 0; i < iterations; i++) {
//            digest.reset();
//            hashed = digest.digest(hashed);
//        }
//        return hashed;
//    }

    /**
     * 计算指定字符串MD5值
     */
    public static String entcryptMD5Hex(String sources) {
        if (StringX.isEmpty(sources)) {
            throw new IllegalArgumentException("Arg sources is null!");
        }
        byte[] digest;
        byte[] sBytes = sources.getBytes();
        try {
            MessageDigest sha = MessageDigest.getInstance("MD5");
            sha.update(sBytes);
            digest = sha.digest();
        } catch (Exception e) {
            throw new IllegalArgumentException("计算MD5过程发生异常!", e);//将异常转换为运行异常
        }
        return byteToHex(digest);
    }

    public static String byteToHex(byte[] bs) {
        if (bs == null || bs.length < 1) {
            throw new IllegalArgumentException("Arg bs is null!");
        }
        StringBuffer sbu = new StringBuffer();
        String stmp = "";
        for (byte b : bs) {
            stmp = Integer.toHexString(b & 0xff);
            sbu.append((stmp.length() == 1 ? "0" + stmp : stmp));
        }
        return sbu.toString().toUpperCase();
    }

    /**
     * 来源于百度api文档
     * http://lbsyun.baidu.com/index.php?title=lbscloud/api/appendix
     */
    public static String baiduApiMD5(String md5) {
        try {
            MessageDigest md = MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.entcryptMD5HexWithSalt("NookBlog12580.", "minenookxiaxixi", 128));
    }

}
