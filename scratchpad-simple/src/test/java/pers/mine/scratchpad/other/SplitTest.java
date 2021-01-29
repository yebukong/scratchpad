package pers.mine.scratchpad.other;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Date;

/**
 * 文件复制切割
 *
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

    @Test
    public void test1() {
        String s = "1,2,3,4,5,,,";
        System.out.println(Arrays.toString(s.split(",", -1)));
        System.out.println(Arrays.toString(s.split(",", 0)));
        System.out.println(Arrays.toString(s.split(",", 1)));
        System.out.println(Arrays.toString(s.split(",", 2)));
    }

    @Test
    public void test2() {
        Date startDate = DateUtil.parseDate("2020-08-15");
        Date endDate = DateUtil.parseDate("2020-09-30");

        long startTime = startDate.getTime();
        String fmt = "PARTITION p{} VALUES LESS THAN ('{}') ENGINE = InnoDB,";
        while (startDate.getTime() <= endDate.getTime()) {
            System.out.println(StrUtil.format(fmt, DateUtil.format(DateUtil.offsetDay(startDate, -1).toJdkDate(), "yyyyMMdd"), DateUtil.format(startDate, "yyyy-MM-dd")));
            startDate = DateUtil.offsetDay(startDate, 1).toJdkDate();
        }
    }
}
