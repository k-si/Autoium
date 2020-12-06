package run.autoium.utils;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.DocumentHelper;
import org.jsoup.Jsoup;
import run.autoium.common.DataCode.response.BodyType;

public class StringUtils {

    /**
     * 利用异常检查字符串类型
     *
     * @param str 要被校验的字符串
     * @return 请求体类型
     */
    public static Integer checkStringFormat(String str) {
        try {
            JSONObject.parseObject(str);
            return BodyType.JSON;
        } catch (Exception e) {
            try {
                JSONObject.parseArray(str);
                return BodyType.JSON;
            } catch (Exception e1) {
                try {
                    Jsoup.parse(str);
                    return BodyType.HTML;
                } catch (Exception e2) {
                    try {
                        DocumentHelper.parseText(str);
                        return BodyType.XML;
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return BodyType.TEXT;
    }

    /**
     * 判断字符串是否为空串
     *
     * @param str 要被校验的字符串
     * @return true / false
     */
    public static Boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }
}
