package pers.mine.scratchpad.other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 控制台输入
 * 
 * @author Mine
 * @date 2019/09/03 16:55:10
 */
public class ReadConsole {
	public static void main(String[] args) throws IOException {
		System.out.println((int) ((char) 65535));
		System.out.println((int) ((char) 65536));
		System.out.println((int) ((char) 65537));
		System.out.println((int) ((char) 65538));
		System.out.println((int) ((char) 0));
		System.out.println((int) ((char) 1));
		System.out.println((int) ((char) 2));
		System.out.println((int) ((char) 3));
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String readLine = "";
		do {
			System.out.print("输入:");
			readLine = br.readLine();
			System.out.println("输出:" + readLine);
		} while (!"quit".equalsIgnoreCase(readLine));
		System.out.println("检测到quit，已退出!");
		br.close();
		isr.close();
	}
}
