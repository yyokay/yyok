package com.yyok.share.framework.utils;

import cn.hutool.crypto.digest.MD5;

import java.util.UUID;

public class CodeUtil {

    public static synchronized String md5dh16CtmUuid() {
        return MD5.create().digestHex16(String.valueOf(System.currentTimeMillis()) + UUID.randomUUID());
    }

    public static synchronized String uuid() {
        return String.valueOf(UUID.randomUUID()).replace("-", "").toUpperCase();
    }

    public static void main(String[] args) {
        for (int a = 0; a < 170; a++) {
            System.out.println(md5dh16CtmUuid());
        }
    }
}
