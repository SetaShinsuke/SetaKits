package com.seta.setakits;

import java.util.Calendar;

/**
 * Created by SETA_WORK on 2017/3/16.
 */

public class DateUtils {

    public static String formatYYYYMMDD(long timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }
}
