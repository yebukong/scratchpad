package pers.mine.scratchpad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangxiaoqiang
 * @description TODO
 * @create 2022-06-13 19:47
 */
public class Test {
    private static final Logger LOG = LoggerFactory.getLogger(Test.class);

    private static final String a = "aaa";

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        String s = new String("1");
        String s2 = "1";
        s.intern();
        System.out.println(s == s2);
        //
        String s3 = new String("1") + new String("1");
        s3.intern();
        String s4 = "11";
        System.out.println(s3 == s4);
        Field a1 = Test.class.getDeclaredField("a");
        Object o = a1.get(new Test());
        System.out.println(o);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true); //Field 的 modifiers 是私有的
        modifiersField.setInt(a1, a1.getModifiers() & ~Modifier.FINAL);
        a1.setAccessible(true);
        a1.set(null,"setVale");
        System.out.println(a1.get(null));
//        String name = "/warehouse/test.db/fancy_v2_bidding_log/thisdate=2022-06-10/hour=15/fancy_v2_bidding_log_2022-06-10_15-f-248-flume-1654847539921-29-orc";
//        System.out.println(getIdFromFilename(name, FILE_NAME_PREFIXED_TASK_ID_REGEX));
    }

    private static final Pattern FILE_NAME_PREFIXED_TASK_ID_REGEX =
            Pattern.compile("^.*?((\\(.*\\))?[0-9]+)(_[0-9]{1,6})?(\\..*)?$");

    private static String getIdFromFilename(String filename, Pattern pattern) {
        String taskId = filename;
        int dirEnd = filename.lastIndexOf("/");
        if (dirEnd != -1) {
            taskId = filename.substring(dirEnd + 1);
        }

        Matcher m = pattern.matcher(taskId);
        if (!m.matches()) {
            LOG.warn("Unable to get task id from file name: " + filename + ". Using last component"
                    + taskId + " as task id.");
        } else {
            taskId = m.group(1);
        }
        LOG.debug("TaskId for " + filename + " = " + taskId);
        return taskId;
    }
}
