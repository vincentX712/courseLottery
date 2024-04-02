package com.officerschool.courselottery.common.Utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/21
 */
public class TimeUtil {

    public static String getCurrentDate() {
        //获取当前系统时间
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(currentTime);
    }
    public static String getBefore7Day(){
        LocalDate yesterday = LocalDate.now().minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 使用formatter格式化昨日的日期
        String formattedYesterday = yesterday.format(formatter);
        return formattedYesterday;
    }
}
