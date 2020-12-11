package run.autoium.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import run.autoium.entity.MyAssert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将一个类型转为另一个类型
 */
public class AToBUtils {

    /**
     * json字符串转为list对象
     *
     * @param json  json字符串
     * @param clazz list存储的对象
     * @param <T>   泛型T，必须包含属性key和value
     * @return list对象
     */
    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        JSONArray array = JSONArray.parseArray(json);
        List<T> list = new ArrayList<>();
        try {
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = array.getJSONObject(i);
                Constructor<T> constructor = clazz.getConstructor(String.class, String.class);
                T t = constructor.newInstance(object.getString("key"), object.getString("value"));
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 断言的json字符串转为list
     *
     * @param examine 断言
     * @return
     */
    public static List<MyAssert> examineToList(String examine) {
        JSONArray array = JSONArray.parseArray(examine);
        List<MyAssert> myAsserts = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            MyAssert myAssert = new MyAssert();
            myAssert.setDataSource(object.getInteger("dataSource"));
            myAssert.setData(object.getString("data"));
            myAssert.setExpress(object.getString("express"));
            myAssert.setExpectRelation(object.getInteger("expectRelation"));
            myAssert.setRelation(object.getString("relation"));
            myAssert.setExpectType(object.getInteger("expectType"));
            myAssert.setType(object.getString("type"));
            myAssert.setExpectValue(object.getString("expectValue"));
            myAsserts.add(myAssert);
        }
        return myAsserts;
    }

    /**
     * list对象转为map
     *
     * @param list  list对象
     * @param clazz list存储的对象
     * @param <T>   泛型T，必须包含属性key和value
     * @return map对象
     */
    public static <T> Map<String, String> listToMap(List<T> list, Class<T> clazz) {
        Map<String, String> map = new HashMap<>();
        try {
            for (T t : list) {
                Method getKey = t.getClass().getMethod("getKey");
                getKey.setAccessible(true);
                String key = (String) getKey.invoke(t);
                Method getValue = t.getClass().getMethod("getValue");
                getValue.setAccessible(true);
                String value = (String) getValue.invoke(t);
                if (key.length() != 0 && value.length() != 0) {
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}

