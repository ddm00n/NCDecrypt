package com.m00n.me.utils;

import com.m00n.me.algorithm.DES;

public class DESUtil {
    private static final long key = 1231234234L;
    public static String decrypt(String s) {
        return decrypt(s, key);
    }

    public static String decrypt(String s, long k) {
        if (s == null) {
            return null;
        } else {
            DES des = new DES(k);
            byte[] sBytes = s.getBytes();
            int bytesLength = sBytes.length / 16;
            byte[] byteList = new byte[bytesLength * 8];

            for(int i = 0; i < bytesLength; ++i) {
                byte[] theBytes = new byte[8];

                for(int j = 0; j <= 7; ++j) {
                    byte byte1 = (byte)(sBytes[16 * i + 2 * j] - 97);
                    byte byte2 = (byte)(sBytes[16 * i + 2 * j + 1] - 97);
                    theBytes[j] = (byte)(byte1 * 16 + byte2);
                }

                long x = des.bytes2long(theBytes);
                byte[] result = new byte[8];
                des.long2bytes(des.decrypt(x), result);
                System.arraycopy(result, 0, byteList, i * 8, 8);
            }

            String rtn = new String(subArr(byteList));
            return rtn;
        }
    }

    public static byte[] subArr(byte[] a) {
        int al = a.length;
        int end = checkEnd(a);
        if (end == 0) {
            return a;
        } else {
            byte[] rtn = new byte[al - end];
            System.arraycopy(a, 0, rtn, 0, al - end);
            return rtn;
        }
    }

    public static int checkEnd(byte[] arr) {
        int rtn = 0;

        for(int i = arr.length - 1; i > 0 && arr[i] == 32; --i) {
            ++rtn;
        }

        return rtn;
    }
}
