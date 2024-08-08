package com.weil.document.utils;

import com.weil.document.exception.ResolvingException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Name: InputStreamConvert
 * @Description: 输入流转换
 * @Author: weil
 * @Date: 2024-07-31 11:06
 * @Version: 1.0
 */
public class InputStreamConvert {

    /**
     * 缓冲区大小
     */
    public static final int bufferLength = 1024;

    /**
     * 不可重复读取的流转成可重复读取的字节数组流
     */
    public static InputStream convert(InputStream in) {
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            // 转字节输入流实现重复读取
            byte[] buffer = new byte[bufferLength];
            int len;
            while ((len =in.read(buffer, 0, buffer.length) ) != -1) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            throw new ResolvingException(e);
        }
        return new ByteArrayInputStream(bos.toByteArray());
    }
}
