package pers.mine.scratchpad.other;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * @description gc log解析
 * @create 2020-10-12 14:52
 */
public class GcLogResolveTest {
    public static void main(String[] args) {
        List<String> logs = FileUtil.readLines("D:\\Mine\\Desktop\\log\\easyreport_gc.log.20201009195540", StandardCharsets.UTF_8);
        for (String log : logs) {
            if (log.startsWith("2020-10")) {
                System.out.println(log);
            }
        }
    }

    static JSONObject resolveLog(String log, JSONObject lastLogInfo) {
        long app = 0;
        long real = 0;
        Date startDate = null;
        Date endDate = null;
        double timeRatio = 0;
        double memoryRatio = 0;
        String type = "";

        Date lastEndDate = null;
        if (lastLogInfo != null) {
            lastEndDate = lastLogInfo.getDate("end");
        }
        String dat = log.substring(0,28);

        JSONObject result = new JSONObject(true);
        result.put("app", app);
        result.put("real", real);
        result.put("start", startDate);
        result.put("end", endDate);
        result.put("timeRatio", timeRatio);
        result.put("memoryRatio", memoryRatio);
        result.put("type", type);
        result.put("rawLog", log);
        return result;
    }
}
