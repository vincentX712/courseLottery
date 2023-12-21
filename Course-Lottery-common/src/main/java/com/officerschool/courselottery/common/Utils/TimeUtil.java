package com.officerschool.courselottery.common.Utils;

import java.text.SimpleDateFormat;
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
}
