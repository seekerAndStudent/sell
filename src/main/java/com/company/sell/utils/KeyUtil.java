package com.company.sell.utils;

import java.util.Random;

/* 用于生成唯一主键
*  毫秒数+随机数
* */
public class KeyUtil {
    public static String genUniqueKey(){
        Random random = new Random();
        //用于生成0-900000之间的随机数
        Integer number = random.nextInt(900000) + 100000;
        //返回一个毫秒数+生成的随机数
        return System.currentTimeMillis()+String.valueOf(number);

    }
}
