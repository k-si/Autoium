package run.autoium.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import run.autoium.common.DataCode.ObjType;
import run.autoium.common.DataCode.request.DataSourceType;
import run.autoium.entity.MyAssert;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求断言的工具类
 */
public class RequestAssert {

    /**
     * 执行所有断言
     *
     * @param response
     * @param asserts
     * @return
     */
    public static List<MyAssert> executeAsserts(CloseableHttpResponse response, List<MyAssert> asserts) {
        List<MyAssert> result = new ArrayList<>();

        for (MyAssert myAssert : asserts) {

            // todo...
        }
        return result;
    }

    /**
     * 执行单个断言
     *
     * @param response
     * @param myAssert
     * @return
     */
    public static MyAssert executeAssert(CloseableHttpResponse response, MyAssert myAssert) {
        MyAssert result = new MyAssert();

        // 设置断言结果的期望值
        // todo...

        Integer dataSource = myAssert.getDataSource();
        switch (dataSource) {

            // 数据源为响应码
            case DataSourceType.respCode:

                // 期望的类型和数值
                Integer expectType = myAssert.getExpectType();
                String expectValue = myAssert.getExpectValue();

                // 期望值和实际值的关系
                Integer expectRelation = myAssert.getExpectRelation();

                // 实际值的类型和数值
                int realValue = response.getStatusLine().getStatusCode();
                int realType = getObjRealType(realValue);

                // 设置断言结果
                result.setDataSource(dataSource);
                result.setExpectType(expectType);
                break;

            // 数据源为响应头
            case DataSourceType.respHeader:
                break;

            // 数据源为json响应体
            case DataSourceType.respJson:
                break;

            // 数据源为text响应体
            case DataSourceType.respText:
                break;

            // 数据源为xml响应体
            case DataSourceType.respXml:
                break;
        }

        return result;
    }

    /**
     * 获取对象类型
     *
     * @param object
     * @return
     */
    public static int getObjRealType(Object object) {
        if (null == object) {
            return ObjType.empty;
        } else {
            if (object instanceof Boolean) {
                return ObjType.bool;
            } else if (object instanceof Byte || object instanceof Short || object instanceof Integer
                    || object instanceof Long || object instanceof Float || object instanceof Double) {
                return ObjType.number;
            } else {
                return ObjType.string;
            }
        }
    }
}
