package pers.mine.scratchpad.other.hotdeploy;

public class MineClassLoader  extends ClassLoader {
	 public Class<?> findClass(byte[] b) throws ClassNotFoundException {
	        return defineClass(null, b, 0, b.length);
	 }
}
