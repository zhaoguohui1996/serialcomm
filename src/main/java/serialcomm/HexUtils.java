package serialcomm;

import java.util.Collections;
import java.util.LinkedList;

/**
 * @program: serialcomm
 * @description
 * @author: zgh
 * @create: 2020-05-11 15:35
 **/
public class HexUtils {

    /**
     * 十六进制字符串转字节数组
     *
     * @param hexString:
     * @return byte[]:
     * @author : cgl
     * @version : 1.0
     * @since 2020/4/15 14:52
     **/
    public static byte[] hexStringToByte(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.replace(" ", "").toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 字符转为字节
     *
     * @param c:
     * @return byte:
     * @author : cgl
     * @version : 1.0
     * @since 2020/4/15 14:53
     **/
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /***
     * 高低位互换
     * @Author zgh
     * @Descripton changeHighAndLow
     * @Date 5/11/2020 3:37 PM
     * @param crc:
     *@return   {@link String}
     */
    public static String changeHighAndLow(String crc) {
        LinkedList linkedList = new LinkedList();
        for (int i = 0; i < crc.length(); i += 2) {
            String s1 = crc.substring(i, i + 2);
            linkedList.add(s1);
        }
        // 反转lists
        Collections.reverse(linkedList);
        String string = linkedList.toString();
        return string.substring(1, string.length() - 1).replace(",", "");
    }


}
