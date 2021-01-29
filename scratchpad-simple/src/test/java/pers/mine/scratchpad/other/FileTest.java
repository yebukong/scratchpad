package pers.mine.scratchpad.other;

import java.io.File;

import org.junit.Test;

public class FileTest {
	/**
	 * 统计指定文件夹下某类文件数量
	 * 
	 * @param dir
	 * @return
	 */
	public int totalTemplate(File dir) {
		int sum = 0;
		File[] listFiles = dir.listFiles();
		for (File file : listFiles) {
			if (file.isDirectory()) {
				sum += totalTemplate(file);
			} else {
				if (file.getName().endsWith(".摸板")) {
					sum++;
				}
			}
		}
		return sum;
	}
	
	
	@Test
	public void totalTemplate() {
		File dir = new File("D:\\Mine\\Desktop\\totalTemplate");
		System.out.println(totalTemplate(dir));
	}
}
