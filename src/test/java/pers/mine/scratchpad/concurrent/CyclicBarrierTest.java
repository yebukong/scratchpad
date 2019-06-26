package pers.mine.scratchpad.concurrent;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * 模拟选举 10 个人选ABC中一个，当所有人投完票后打印票数,以及胜利者
 * 
 * @author Mine
 * @date 2019/06/26 20:33:52
 */
public class CyclicBarrierTest {
	static  final List<String> voteList = new CopyOnWriteArrayList<String>();// 票箱
	static String[] campaigners = { "A", "B", "C" };
	static String winner = "";
	public static void main(String[] args) throws InterruptedException {
		CyclicBarrier cb = new CyclicBarrier(10,()->{
			Multimap<String, String> voteMap = ArrayListMultimap.create();
			System.out.println(System.currentTimeMillis() + ":所有人投票结束,开始统计票数");
			for (String string : voteList) {
				voteMap.put(string, "0");
			}
			int max = 0;
			for (String key : voteMap.keySet()) {
				int size = voteMap.get(key).size();
				System.out.println(String.format("%s 的票为 %s", key,size));//得票为0的未打印
				if(size > max) {
					max = size;
					winner = key; //存在相等的，可能不准确
				}
			}
			System.out.println(String.format("%s 的票为 胜利者", winner));
		}) ;
		
		System.out.println("第一次投票：");
		for (int i = 0; i < 10; i++) {
			Thread th = new Thread(new CBRunnable(cb,""+i));
			th.start();
		}
		
		Thread.sleep(5000);
		System.out.println("\n第二次投票：");
		cb.reset();
		voteList.clear();
		for (int i = 0; i < 10; i++) {
			Thread th = new Thread(new CBRunnable(cb,""+i));
			th.start();
		}
		
	}

	/**
	 * 投票人
	 */
	static class CBRunnable implements Runnable {
		CyclicBarrier cb = null;
		String name = "";

		public CBRunnable(CyclicBarrier cb, String name) {
			this.cb = cb;
			this.name = name;
		}

		public void run() {
			try {
				long start = System.currentTimeMillis();
				int waitTime = (int) (Math.random() * 3000);
				System.out.println(start + ":" + name + " 开始思考选谁");
				Thread.sleep(waitTime);
				String target = campaigners[waitTime % 3];
				voteList.add(target);
				System.out.println(start + ":" + name + " 选择了 "+target+"，等待统计投票结果");
				cb.await();
				System.out.println(start + ":" + name + " 知道了 "+winner+" 是胜利者");
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

	}
}
