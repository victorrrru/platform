package com.fww;

import java.util.UUID;

/**
 * @author 范文武
 * @date 2018/04/27 15:31
 */
public class UUIDUtil {
    public UUIDUtil() {
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
