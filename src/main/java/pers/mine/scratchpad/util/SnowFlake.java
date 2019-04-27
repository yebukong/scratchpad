package pers.mine.scratchpad.util;

/**
 * twitter 的 snowflake(雪花) 算法<br>
 * 64   = 1[符号位舍弃] + 46 + (2 + 3) + 12
 * 
 */
public class SnowFlake {
	private final static long START_STMP = 0L;// 起始时间戳
	// 位数分配
	private final static long SEQUENCE_BIT = 12L; // 毫秒内 序列号位数
	private final static long MAC_BIT = 3L; // 机器号位数
	private final static long DATACENTER_BIT = 2L; // 数据中心位数
	// 各部分最大值
	private final static long MAX_SEQUENCE_NUM = -1L ^ (-1L << SEQUENCE_BIT);// 每毫秒最大序列
	private final static long MAX_MAC_NUM = -1L ^ (-1L << MAC_BIT);// MAC最大值
	private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);// 机器号最大值
	// 各部分位移
	private final static long MAC_LEFT = SEQUENCE_BIT;
	private final static long DATACENTER_LEFT = SEQUENCE_BIT + MAC_BIT;
	private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

	private long datacenterID;
	private long macID;
	private long sequenceID;
	private long lastTimeStmp = -1L; // 上一次时间戳

	public SnowFlake(long datacenterID, long macID) {
		if (datacenterID > MAX_DATACENTER_NUM || datacenterID < 0) {
			throw new IllegalArgumentException("数据中心编号应在[" + 0 + "," + MAX_DATACENTER_NUM + ")区间内!");
		}
		if (macID > MAX_MAC_NUM || macID < 0) {
			throw new IllegalArgumentException("MAC编号应在[" + 0 + "," + MAX_MAC_NUM + ")区间内!");
		}
		this.datacenterID = datacenterID;
		this.macID = macID;
	}

	/**
	 * 获取下一个ID
	 */
	private synchronized long nextID() {
		long nowTime = getTimeStmp();
		if (nowTime < lastTimeStmp) {
			throw new RuntimeException("系统时钟异常,无法生成ID!");
		}
		if (nowTime == lastTimeStmp) {// 同一毫秒内
			/* 如果sequenceID为本毫秒最后一个ID，值将为0,便于判断 */
			sequenceID = (sequenceID + 1) & MAX_SEQUENCE_NUM;
			if (sequenceID == 0) {
				nowTime = getNextTimeStmp();
			}
		} else {
			sequenceID = 0L;
		}
		lastTimeStmp = nowTime;

		return (nowTime - START_STMP) << TIMESTMP_LEFT // 时间戳
				| datacenterID << DATACENTER_LEFT // 数据中心
				| macID << MAC_LEFT // 机器号
				| sequenceID;// 毫秒级序列号

	}

	/**
	 * 获取下一个指定长度字符串ID,不足高位补零,多余高位舍弃<br>
	 */
	public String nextID(int length) {
		String nextID = nextID() + "";
		int diffLen = length - nextID.length();
		if (diffLen > 0) {
			for (int i = 0; i < diffLen; i++) {
				nextID = "0" + nextID;
			}
		} else if (diffLen < 0) {
			nextID = nextID.substring(-diffLen);
		}
		return nextID;
	}

	private long getNextTimeStmp() {
		long nowTime = getTimeStmp();
		while (nowTime <= lastTimeStmp) {
			nowTime = getTimeStmp();
		}
		return nowTime;
	}

	private long getTimeStmp() {
		return System.currentTimeMillis();
	}
}
