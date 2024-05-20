package com.agsft.shield.util;

import java.util.Calendar;
import java.util.Date;

public class ShieldUtil {

    public static Date getTokenExpiration(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }
}
