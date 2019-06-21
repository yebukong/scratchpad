package pers.mine.scratchpad.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

/**
 * 字符串工具类[一个类的编写竟然跨年了]
 * 
 * @author yebukong
 * @since JDK1.8
 * @date 2017/11/29
 */
public class StringX {
	/** 0-9 **/
	public static final char[] BASE_NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	/** a-z **/
	public static final char[] LOWER_CASE_ALPHABET = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	/** A-Z **/
	public static final char[] UPPER_CASE_ALPHABET = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	private StringX() {
		throw new AssertionError("No StringX instances for you!");
	}

	public static boolean isNull(String str) {
		return Objects.isNull(str);
	}

	public static boolean isNotNull(String str) {
		return Objects.nonNull(str);
	}

	public static boolean isEmpty(String str) {
		return (str == null) || (str.length() == 0);
	}

	public static boolean isNotEmpty(String str) {
		return (str != null) && (str.length() > 0);
	}

	/**
	 * 空格串判断 [\u3000] 全角空格也识别
	 * 
	 * @see Character#isSpaceChar(char)
	 */
	public static boolean isSpace(String str) {
		if (isNotEmpty(str)) {
			int len = str.length();
			for (int i = 0; i < len; i++) {
				if (!Character.isSpaceChar(str.charAt(i))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 空白字符串判断 [\u3000] 全角空格也识别[也包括 \n \t \r 等字符 ]
	 * 
	 * @see Character#isWhitespace(char)
	 */
	public static boolean isBlank(String str) {
		if (isNotEmpty(str)) {
			int len = str.length();
			for (int i = 0; i < len; i++) {
				if (!Character.isWhitespace(str.charAt(i))) {
					return false;
				}
			}
		}
		return true;
	}

	/** @see #isStrictWhitespace(char) **/
	public static boolean isBlankPlus(String str) {
		if (isNotEmpty(str)) {
			int len = str.length();
			for (int i = 0; i < len; i++) {
				if (!isStrictWhitespace(str.charAt(i))) {
					return false;
				}
			}
		}
		return true;
	}

	/** @see #isSpace(String) */
	public static boolean isNotSpace(String str) {
		return !(isSpace(str));
	}

	/** @see #isBlank(String) */
	public static boolean isNotBlank(String str) {
		return !(isBlank(str));
	}

	/** 满足 isEmpty 返回 defaultStr */
	public static String nvl(String str, String defaultStr) {
		return isEmpty(str) ? defaultStr : str;
	}

	/** @see #isSpace(String) */
	public static String nvlForSpace(String str, String defaultStr) {
		return isSpace(str) ? defaultStr : str;
	}

	/** @see #isBlank(String) */
	public static String nvlForBlank(String str, String defaultStr) {
		return isBlank(str) ? defaultStr : str;
	}

	/** @see #isBlankPlus(String) */
	public static String nvlForBlankPlus(String str, String defaultStr) {
		return isBlankPlus(str) ? defaultStr : str;
	}

	public static boolean equals(String strA, String strB) {
		return isNull(strA) ? false : strA.equals(strB);
	}

	public static boolean equalsIgnoreCase(String strA, String strB) {
		return isNull(strA) ? false : strA.equalsIgnoreCase(strB);
	}

	/** 删除两端空白字符 */
	public static String trim(String str) {
		return isEmpty(str) ? str : str.trim();
	}

	/**
	 * 严格模式删除两端空白字符
	 * 
	 * @see #isStrictWhitespace(char)
	 */
	public static String strictTrim(String str) {
		return isEmpty(str) ? str : trimStart(trimEnd(str));
	}

	/** 删除开头空白字符 */
	public static String trimStart(String str) {
		if (isNotEmpty(str)) {
			int len = str.length();
			int index = 0;
			while ((index < len) && (isStrictWhitespace(str.charAt(index)))) {
				index++;
			}
			str = str.substring(index);
		}
		return str;
	}

	/**
	 * 删除结尾空白字符
	 * 
	 * @see #isStrictWhitespace(char)
	 */
	public static String trimEnd(String str) {
		if (isNotEmpty(str)) {
			int len = str.length();
			int index = len - 1;
			while ((index > 0) && (isStrictWhitespace(str.charAt(index)))) {
				index--;
			}
			str = str.substring(0, index + 1);
		}
		return str;
	}

	/**
	 * 删除所有空白字符
	 * 
	 * @see Character#isWhitespace(char)
	 */
	public static String delWhitespace(String str) {
		StringBuffer temp = null;
		if (isNotEmpty(str)) {
			int len = str.length();
			temp = new StringBuffer(len);
			for (int i = 0; i < len; ++i) {
				Character c = str.charAt(i);
				if (!Character.isWhitespace(c)) {
					temp.append(c);
				}
			}
			str = temp.toString();
		}
		return str;
	}

	/**
	 * 删除所有空白字符
	 * 
	 * @see #isStrictWhitespace(char)
	 */
	public static String delStrictWhitespace(String str) {
		StringBuffer temp = null;
		if (isNotEmpty(str)) {
			int len = str.length();
			temp = new StringBuffer(len);
			for (int i = 0; i < len; ++i) {
				Character c = str.charAt(i);
				if (!isStrictWhitespace(c)) {
					temp.append(c);
				}
			}
			str = temp.toString();
		}
		return str;
	}

	/**
	 * @param delimiter 定界符
	 * @param strs      源字符数组
	 * @see String#join(CharSequence, CharSequence...)
	 */
	public static String join(String delimiter, String... strs) {
		return String.join(delimiter, strs);
	}

	/** 使长度统一[长切短补 - 前填充] */
	public static String makeSameWidthPrefix(String str, int width, String keyWord) {
		return makeSameWidth(str, width, keyWord, true);
	}

	/** 使长度统一[长切短补 - 后填充] */
	public static String makeSameWidthSuffix(String str, int width, String keyWord) {
		return makeSameWidth(str, width, keyWord, false);
	}

	/** 拼接num个字符word并返回 */
	public static String dup(String word, int num) {
		if (isEmpty(word)) {
			throw new IllegalArgumentException("非法的 word:" + word);
		}
		if (num < 1) {
			throw new IllegalArgumentException("非法的 num:" + num);
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < num; i++) {
			sb.append(word);
		}
		return sb.toString();
	}

	/** 循环填充word到width宽度 */
	public static String dupByWidth(String word, int width) {
		if (isEmpty(word)) {
			throw new IllegalArgumentException("非法的 word:" + word);
		}
		if (width < 1) {
			throw new IllegalArgumentException("非法的 width:" + width);
		}
		StringBuilder sb = new StringBuilder();
		int num = ((width - 1) / word.length()) + 1;
		for (int i = 0; i < num; i++)
			sb.append(word);
		return sb.toString().substring(0, width);
	}

	/**
	 * 判断key是否存在于str中<br>
	 * key 或str 满足 isEmpty 亦返回 false
	 * 
	 * @see String#contains(CharSequence)
	 */
	public static boolean contains(String str, String key) {
		return isEmpty(str) || isEmpty(key) ? false : str.contains(key);
	}

	/**
	 * 循环验证 contains(str,key),存在一项则返回true
	 * 
	 * @see #contains(String, String)
	 */
	public static boolean containsKeys(String str, String... keys) {
		if (Objects.isNull(keys)) {
			return false;
		}
		for (String key : keys) {
			if (contains(str, key)) {
				return true;
			}
		}
		return false;
	}

	/** String To Unicode */
	public static String stringToUnicode(String str) {
		if (isEmpty(str))
			return str;
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			unicode.append("\\u" + Integer.toHexString(c)); // 转换为unicode
		}
		return unicode.toString();
	}

	/** Unicode To String */
	public static String unicodeToString(String unicode) {
		if (isEmpty(unicode))
			return unicode;
		StringBuffer str = new StringBuffer();
		String[] hex = unicode.split("\\\\u");
		for (int i = 1; i < hex.length; i++) {
			int data = Integer.parseInt(hex[i], 16);
			str.append((char) data);
		}
		return str.toString();
	}

	private static String makeSameWidth(String str, int width, String keyWord, boolean isPrefix) {
		if (isEmpty(keyWord)) {
			throw new IllegalArgumentException("非法的 keyWord:" + keyWord);
		}
		if (width < 1) {
			throw new IllegalArgumentException("非法的 width:" + width);
		}

		int len = 0;
		if (isEmpty(str) || (len = str.length()) < width) {
			return isPrefix ? (dupByWidth(keyWord, width - len) + str) : (str + dupByWidth(keyWord, width - len));
		} else if (len > width) {
			return str.substring(0, width - 1);
		} else {
			return str;
		}
	}

	/** 严格判断是否为空字符[包括non-breaking space {'\u00A0', '\u2007','\u202F'}] */
	private static boolean isStrictWhitespace(char c) {
		return Character.isSpaceChar(c) || Character.isWhitespace(c);
	}

	/**
	 * 随机生成指定长度的字符串，charPool为字符池,可指定多个
	 */
	public static String randomForMultiCharPool(int len, char[]... charPools) {
		if (len < 1) {
			throw new IllegalArgumentException("非法的 len参数:" + len);
		}
		if (charPools == null || charPools.length < 1) {
			throw new IllegalArgumentException("空的 charPools参数");
		}
		// 计算字符池字符总个数,及统计字符池边界
		int poolCharSum = 0;
		int charPoolsLen = charPools.length;
		int[] charPoolFlags = new int[charPoolsLen];
		char[] tempCharPool = null;
		for (int i = 0; i < charPoolsLen; i++) {
			tempCharPool = charPools[i];
			if (tempCharPool == null || tempCharPool.length < 1) {
				throw new IllegalArgumentException("charPools中包含空的字符池...");
			}
			charPoolFlags[i] = poolCharSum;
			poolCharSum += tempCharPool.length;
		}

		StringBuffer stb = new StringBuffer();
		Random rand = new Random();
		int randomNum = 0;
		for (int i = 0; i < len; i++) {
			randomNum = rand.nextInt(poolCharSum);
			// 定位随机数字所在字符池
			for (int j = 0; j < charPoolsLen; j++) {
				if (randomNum >= charPoolFlags[j] && ((j + 1) == charPoolsLen || randomNum < charPoolFlags[j + 1])) {
					tempCharPool = charPools[j];
					randomNum -= charPoolFlags[j];
				}
			}
			stb.append(tempCharPool[randomNum]);
		}
		return stb.toString();
	}

	/**
	 * 随机生成指定长度的字符串，范围限于字母,数字
	 */
	public static String random(int len) {
		return randomForMultiCharPool(len, StringX.BASE_NUMBERS, StringX.LOWER_CASE_ALPHABET,
				StringX.UPPER_CASE_ALPHABET);
	}

	/**
	 * 随机生成指定长度的字符串，范围限于字母,数字,指定特殊字符
	 */
	public static String random(int len, char[] otherChars) {
		return randomForMultiCharPool(len, StringX.BASE_NUMBERS, StringX.LOWER_CASE_ALPHABET,
				StringX.UPPER_CASE_ALPHABET, otherChars);
	}

	/**
	 * 时间字符串转时间
	 */
	public static Date parseDate(String date, String formatStr) {
		if (isEmpty(date)) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat(formatStr);
		Date retult = null;
		try {
			retult = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return retult;
	}

	/**
	 * 简单的时间转换方法:支持格式 yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss
	 */
	public static Date parseDate(String date) {
		if (isEmpty(date)) {
			return null;
		}
		String formatStr = "yyyy-MM-dd";
		if (date.length() > 10) {
			formatStr = "yyyy-MM-dd HH:mm:ss";
		}
		return parseDate(date, formatStr);
	}

	public static String toString(Object obj) {
		return obj == null ? null : obj.toString();
	}

	/**
	 * 时间转字符串
	 */
	public static String toString(Date date, String format) {
		format = nvl(format, "yyyy-MM-dd HH:mm:ss");
		String result = "";
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			result = sdf.format(date);
		}
		return result;
	}

	/**
	 * 时间转字符串: yyyy-MM-dd HH:mm:ss
	 */
	public static String toString(Date date) {
		return toString(date, "yyyy-MM-dd HH:mm:ss");
	}
}
