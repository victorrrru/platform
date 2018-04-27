package com.fww;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 范文武
 * @date 2018/04/27 15:42
 */
public class StringUtil {
    private static DecimalFormat formatDouble = new DecimalFormat("0.00");
    private static DecimalFormat dFormat2 = new DecimalFormat("0.00");
    private static DecimalFormat dFormat1 = new DecimalFormat("0.0");
    private static final String STR_FORMAT = "000000";

    public StringUtil() {
    }

    public static String cleanSpecialChar(String str) {
        try {
            str = StringUtils.trimToEmpty(str);
            str = str.replaceAll(" ", "");
            String regEx = "[`~!@#$%^&*()+|';'\\?~！@#￥%……&*（）——+|【】‘；：”“’。，、？]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            str = StringUtils.trimToEmpty(m.replaceAll(""));
            str = StringUtils.trimToEmpty(HtmlUtil.getTextFromHtml(str));
            return str;
        } catch (Exception var4) {
            throw new RuntimeException("清理特殊字符出错");
        }
    }

    public static String randomCode4() {
        int intCount = (new Random()).nextInt(9999);
        if(intCount < 1000) {
            intCount += 1000;
        }

        return intCount + "";
    }

    public static String randomCode6() {
        int intCount = (new Random()).nextInt(999999);
        if(intCount < 100000) {
            intCount += 100000;
        }

        return intCount + "";
    }

    public static String getRandomStr(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        return sb.toString();
    }

    public static String formatDouble(Object obj) {
        return obj != null?formatDouble.format(obj):"0.00";
    }

    public static boolean isEmail(String str) {
        return !StringUtils.isBlank(str) && str.matches(RegexpConstants.REGEXP_EMAIL[0]);
    }

    public static boolean isChinese(String str) {
        return !StringUtils.isBlank(str) && str.matches(RegexpConstants.REGEXP_CHINESE[0]);
    }

    public static boolean isNormalWord(String str) {
        return !StringUtils.isBlank(str) && str.matches(RegexpConstants.REGEXP_CHINESE_AND_WORD[0]);
    }

    public static String[] getArray(String fileName) {
        return !StringUtils.isBlank(fileName)?fileName.split(","):new String[0];
    }

    public static String dFormat1(Double val) {
        return val != null?dFormat1.format(val):"0.0";
    }

    public static String dFormat2(Double val) {
        return val != null?dFormat2.format(val):"0.00";
    }

    public static String byte2hex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        String tmp = "";

        for(int i = 0; i < b.length; ++i) {
            tmp = Integer.toHexString(b[i] & 255);
            if(tmp.length() == 1) {
                sb.append("0" + tmp);
            } else {
                sb.append(tmp);
            }
        }

        return sb.toString();
    }

    public static byte[] hex2byte(String str) {
        if(str == null) {
            return null;
        } else {
            str = str.trim();
            int len = str.length();
            if(len != 0 && len % 2 != 1) {
                byte[] b = new byte[len / 2];

                try {
                    for(int i = 0; i < str.length(); i += 2) {
                        b[i / 2] = (byte)Integer.decode("0X" + str.substring(i, i + 2)).intValue();
                    }

                    return b;
                } catch (Exception var4) {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    public static String unicodeToString(String utfString) {
        StringBuilder sb = new StringBuilder();
        int pos = 0;

        int i;
        while((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if(i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char)Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }

        return sb.toString();
    }

    public static synchronized String numberAddOne(String serialNumber) {
        Integer newSerialNumber = Integer.valueOf(Integer.parseInt(serialNumber));
        newSerialNumber = Integer.valueOf(newSerialNumber.intValue() + 1);
        DecimalFormat df = new DecimalFormat("000000");
        return df.format(newSerialNumber);
    }

    public static Long hash(String key) {
        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 305441741;
        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);
        long m = -4132994306676758123L;
        int r = 47;

        long h;
        for(h = (long)seed ^ (long)buf.remaining() * m; buf.remaining() >= 8; h *= m) {
            long k = buf.getLong();
            k *= m;
            k ^= k >>> r;
            k *= m;
            h ^= k;
        }

        if(buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;
        buf.order(byteOrder);
        return Long.valueOf(h);
    }

    public static String listToString(List<String> list) {
        if(list == null) {
            return null;
        } else {
            StringBuilder result = new StringBuilder();
            Iterator var2 = list.iterator();

            while(var2.hasNext()) {
                String str = (String)var2.next();
                result.append("'" + str.toString() + "',");
            }

            String str = result.toString();
            if(str.length() > 0) {
                str = str.substring(0, str.length() - 1);
            }

            return str;
        }
    }

    public static String encodeFileName(String fileName, String userAgent) {
        try {
            if(userAgent != null && fileName != null) {
                userAgent = userAgent.toLowerCase();
                if(userAgent.indexOf("firefox") != -1) {
                    fileName = new String(fileName.getBytes(), "iso8859-1");
                } else if(userAgent.indexOf("msie") != -1) {
                    fileName = URLEncoder.encode(fileName, "UTF-8");
                } else {
                    fileName = URLEncoder.encode(fileName, "UTF-8");
                }
            }

            return fileName;
        } catch (Exception var3) {
            return fileName;
        }
    }

    public static String getDecimalFormat(BigDecimal dec) {
        DecimalFormat fmt = new DecimalFormat("##,###,###,###,##0.00");
        return fmt.format(dec);
    }
}
