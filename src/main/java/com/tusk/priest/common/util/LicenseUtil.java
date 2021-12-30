package com.tusk.priest.common.util;

import com.tusk.priest.common.util.encrypt.AESEncrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 授权工具类
 *
 * @author alvin
 * @date 2019/3/26
 */
public class LicenseUtil {

    private static final Logger log = LoggerFactory.getLogger(LicenseUtil.class);

    public static String SALT_PREFIX = "GOGOEIO_";
    public static String SPLITE = "_";




    /**
     * 解析成功需满足：
     * 1. mac有一个相同
     * 2. 时间戳验证
     */
    public static boolean parseLicense(String token) {
        try {
            String magic = AESEncrypt.decrypt(token, SALT_PREFIX + DateUtil.formatYear());
            String[] magicSplite = magic.split(SPLITE);
            String macIdsParse = magicSplite[0];
            long timestampParse = Long.parseLong(magicSplite[1]);
            int dayLast = Integer.parseInt(magicSplite[2]);


        } catch (Exception e) {
            log.debug(e.getMessage(), e);
            return false;
        }


        return true;
    }


}
