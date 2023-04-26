package com.neuedu.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * ClassName:DateTools
 * Package:com.shop.vueshop.tools
 * Description:
 *
 * @Author: 贤哥编程
 * @Create: 2023-3-18 - 19:59
 */
public class DateTools {
    public static String dealDateFormat(String oldDateStr) {
        Date date1 = null;
        SimpleDateFormat df2 = null;
        try {
            oldDateStr = oldDateStr.replace("Z", " UTC");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            Date date = df.parse(oldDateStr);
            SimpleDateFormat sdf = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = sdf.parse(date.toString());
            df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df2.format(date1);
    }

}
