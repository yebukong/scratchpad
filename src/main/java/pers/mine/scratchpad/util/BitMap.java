package pers.mine.scratchpad.util;

import java.text.MessageFormat;

/**
 * redis布隆过滤器 实现原理：有一个512MB(约 1024*1024*512*8【1KB =
 * 8bit】)的bit数组，总共为40亿个bit位，可以代表约40亿个数字 通过多次hash映射 判断某一个值是否存在 待测试 TODO
 */
public class BitMap {
	/**
	 * 使用int数组承载bit
	 */
	private int[] bitArr = null;
	/**
	 * 总位数
	 */
	private long bitLen = 0;

	/**
	 * 基础位宽度
	 */
	private int BASEWIDTH = Integer.SIZE;

	public BitMap(long bitLen) {
		if (bitLen < 1) {
			throw new IllegalArgumentException(MessageFormat.format("错误的bit位长度 : {0}", bitLen));
		}
		int arrSize = (int) ((bitLen - 1) / BASEWIDTH + 1);
		bitArr = new int[arrSize];
		this.setBitLen(bitLen);
	}

	/**
	 * 设置指定位置为1
	 */
	public void put(long index) {
		int aIndex = (int) (index / BASEWIDTH);
		bitArr[aIndex] = setIntegerBitValue(bitArr[aIndex], (int) (index % BASEWIDTH), 1);
	}

	/**
	 * 获取指定位置是否指定值
	 */
	public boolean get(long index) {
		int tmp = bitArr[(int) (index / BASEWIDTH)];
		return getIntegerBitValue(tmp, (int) (index % BASEWIDTH)) == 1;
	}

	/**
	 * 设置指定位置为0
	 */
	public void clean(long index) {
		int aIndex = (int) (index / BASEWIDTH);
		bitArr[aIndex] = setIntegerBitValue(bitArr[aIndex], (int) (index % BASEWIDTH), 0);
	}

	/**
	 * 重置各bit位为0
	 */
	public void cleanAll() {
		for (int i = 0; i < bitArr.length; i++) {
			bitArr[i] = 0;
		}
	}

	/**
	 * 置oldValue指定bit位的值为指定值
	 * 
	 * @param oldValue 旧int值
	 * @param bitIndex 目标bit位
	 * @return 返回新int值
	 */
	public static int setIntegerBitValue(int oldValue, int bitIndex, int bitvalue) {
		int result = oldValue;
		if (bitvalue == 0) {
			if (getIntegerBitValue(oldValue, bitIndex) == 1) {
				// 1 -> 0 使用 '&'
				result = oldValue & (-1 - (1 << bitIndex));
				// result = oldValue & (~(1 << bitIndex));
			}
		}
		if (bitvalue == 1) {
			if (getIntegerBitValue(oldValue, bitIndex) == 0) {
				// 0 -> 1 使用 '|'
				result = oldValue | (1 << bitIndex);
			}
		}
		return result;
	}

	/**
	 * 获取intVal指定bit位的值【0/1】
	 * 
	 * @param intVal
	 * @param bitIndex 从低位到高位bit位索引
	 * @return 指定bit位值：0 或 1
	 */
	public static int getIntegerBitValue(int intVal, int bitIndex) {
		return (intVal >>> bitIndex) & 1;
	}

	public long getBitLen() {
		return bitLen;
	}

	private void setBitLen(long bitLen2) {
		this.bitLen = bitLen2;
	}

	/**
	 * 测试用
	 */
	public String toBinaryString() {
		StringBuffer sbu = new StringBuffer();
		if (bitArr == null) {
			sbu.append("null");
		} else {
			StringBuffer tmpSbu = null;
			String tmp = "";
			for (int j : bitArr) {
				tmpSbu = new StringBuffer();
				tmp = Integer.toBinaryString(j);
				if (tmp.length() < 32) {
					tmpSbu.append(StringX.dup("0", 32 - tmp.length()));
				}
				tmpSbu.append(tmp);
				sbu.append(tmpSbu.reverse().toString());
			}
		}
		return sbu.toString();
	}
}
