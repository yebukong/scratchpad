package pers.mine.scratchpad.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 参考《java编程思想》生产者-消费者 demo
 * 简单使用 wait()和notify()
 */
/**
 * 餐厅类
 */
public class Restaurant {
	Meal meal = null;
	ExecutorService exec = Executors.newCachedThreadPool();
	WaitPerson waitPerson = new WaitPerson(this);
	Chef chef = new Chef(this);

	public Restaurant() {
		exec.execute(waitPerson);
		exec.execute(chef);
	}

	public static void main(String[] args) {
		new Restaurant();
	}
}

/**
 * 菜品
 */
class Meal {
	private final int orderNum;

	public Meal(int orderNum) {
		this.orderNum = orderNum;
	}

	public String toString() {
		return "meal:" + orderNum;
	}
}

/**
 * 顾客
 */
class WaitPerson implements Runnable {
	private Restaurant restaurant;

	public WaitPerson(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (restaurant.meal == null) {// 餐厅菜品为空则等待
						// [之所以用while包围是防止唤醒后条件依旧不满足-之所以可能不满足，是唤醒使用了notifyAll]
						this.wait();
					}
				}

				System.out.println(System.currentTimeMillis() + " 顾客拿到了菜品:" + restaurant.meal);
				Thread.sleep((int) (Math.random() * 2000));// 吃饭时间

				synchronized (restaurant.chef) {// 消耗了菜品，则唤醒厨师
					restaurant.meal = null;
					restaurant.chef.notifyAll();
				}
			}
		} catch (InterruptedException e) {
			System.out.println(System.currentTimeMillis() + " 顾客线程中断");
		}
	}
}

/**
 * 厨师
 */
class Chef implements Runnable {
	private Restaurant restaurant;
	private int count;

	public Chef(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (restaurant.meal != null) {// 餐厅还有菜品则等待
						this.wait();
					}
				}
				Thread.sleep((int) (Math.random() * 2000));// 做饭时间
				count++;
				System.out.println(System.currentTimeMillis() + " 厨师：饭好了！");
				if (count >= 10) {
					System.out.println(System.currentTimeMillis() + " 厨师:今天菜做的够多了，下班!");
					restaurant.exec.shutdownNow();
				}
				synchronized (restaurant.waitPerson) {// 提醒顾客取餐
					restaurant.meal = new Meal(1);
					restaurant.waitPerson.notifyAll();
				}
			}
		} catch (InterruptedException e) {
			System.out.println(System.currentTimeMillis() + " 厨师线程中断");
		}
	}
}