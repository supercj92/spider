package com.cfysu.util;

import java.util.Random;

/**
 * Created by Administrator on 2019/11/9.
 */
public class RandomIPAdderssUtils {
    private static Random mRandom = new Random();

    /**
     * 获取随机ip地址
     *
     * @return
     */
    public static String getRandomIPAdderss() {

        return String.valueOf(mRandom.nextInt(255)) + "." + String.valueOf(mRandom.nextInt(255)) + "." + String.valueOf(mRandom.nextInt(255)) + "." + String.valueOf(mRandom.nextInt(255));
    }
}
