package pers.mine.scratchpad.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 文件复制切割
 * @author Mine
 * @date 2019/09/03 16:53:46
 */
public class SplitTest {
	public static void main(String[] args) throws IOException {
		String file = "D:/split/stg_log.out";
		String newfile = "D:/split/stg_log_split";
		File f = new File(file);
		File ftemp = new File(newfile);

		if (!f.exists()) {
			System.out.println("文件不存在!");
			return;
		}
		if (!ftemp.exists()) {
			ftemp.createNewFile();
		}
		long mLen = 1024 * 1024;
		long len = f.length();

		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			fis = new FileInputStream(f);
			inChannel = fis.getChannel();
			fos = new FileOutputStream(ftemp);
			outChannel = fos.getChannel();
			long result = inChannel.transferTo(len - 100 * mLen, 100 * mLen, outChannel);
			outChannel.force(true);
			System.out.println(String.format("复制大小：%s byte", result));
			System.out.println(String.format("test.out大小：%s byte", f.length()));
			System.out.println(String.format("temp.out大小：%s byte", ftemp.length()));
			System.out.println(String.format("inChannel大小：%s byte", inChannel.size()));
			System.out.println(String.format("outChannel大小：%s byte", outChannel.size()));

		} finally {
			fis.close();
			fos.close();
			inChannel.close();
			outChannel.close();
		}

	}
}
