package com.mgy.util;


import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * @author MGY
 * @data 2019/12/24 20:17
 */
public class CheckUtil {
    private static final String token = "WEIXINTOKEN";

    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{token, timestamp, nonce};

        //排序
        Arrays.sort(arr);

        //生成字符串
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }

        //sha1加密
        String sha1 = getSha1(content.toString());
        return sha1.equals(signature);
    }

    //sha1加密
    public static String getSha1(String str) {
        if(StringUtils.isEmpty(str)){
            return null;
        }else{
            return DigestUtils.sha1Hex(str);
        }
    }
}
