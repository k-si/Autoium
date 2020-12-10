package run.autoium.utils;

import com.jayway.jsonpath.JsonPath;
import run.autoium.common.DataCode.ObjType;
import run.autoium.common.DataCode.request.DataSourceType;
import run.autoium.common.DataCode.request.ExpectRelationshipType;
import run.autoium.entity.MyAssert;
import run.autoium.entity.MyHeader;
import run.autoium.entity.vo.ApiCaseResultVo;

import java.util.List;


/**
 * 请求断言的工具类
 */
public class AssertUtils {

    /**
     * 执行所有断言
     *
     * @param resultVo
     * @return
     */
    public static void executeAsserts(ApiCaseResultVo resultVo) {
        List<MyAssert> assertResult = resultVo.getAssertResult();
        if (assertResult != null) {
            for (MyAssert myAssert : assertResult) {
                executeAssert(resultVo, myAssert);
            }
        }
    }

    /**
     * 执行单个断言
     *
     * @param resultVo
     * @param myAssert
     * @return
     */
    public static void executeAssert(ApiCaseResultVo resultVo, MyAssert myAssert) {
//        MyAssert result = new MyAssert();

        // 设置断言结果的期望值
        Integer dataSource = myAssert.getDataSource();
        String express = myAssert.getExpress();
        Integer expectType = myAssert.getExpectType();
        String expectValue = myAssert.getExpectValue();
        Integer expectRelation = myAssert.getExpectRelation();
        Object realValue;

//        result.setDataSource(dataSource);
//        result.setExpress(express);
//        result.setExpectType(expectType);
//        result.setExpectValue(expectValue);
//        result.setExpectRelation(expectRelation);

        switch (dataSource) {

            // 数据源为响应码
            case DataSourceType.respCode:
                realValue = resultVo.getStatusCode();
                myAssert.setRealValue(realValue);
                if (realValue == null) {
                    myAssert.setRealType(ObjType.empty);
                } else {
                    myAssert.setRealType(ObjType.string);
                }
                break;

            // 数据源为响应头
            case DataSourceType.respHeader:
                MyHeader myHeader = resultVo.getHeaders()
                        .stream()
                        .filter(x -> x.getKey().equals(express))
                        .findFirst()
                        .orElse(null);
                if (myHeader == null) {
                    myAssert.setRealType(ObjType.empty);
                    myAssert.setRealValue("");
                } else {
                    myAssert.setRealType(ObjType.string);
                    myAssert.setRealValue(myHeader.getKey());
                }
                break;

            // 数据源为json响应体
            case DataSourceType.respJson:
                try {
                    realValue = JsonPath.read(resultVo.getBody(), express);
                } catch (Exception e) {
                    myAssert.setRealType(ObjType.empty);
                    myAssert.setRealValue("");
                    break;
                }
                myAssert.setRealType(ObjectUtils.getObjRealType(realValue));
                break;

            // 数据源为text响应体
            case DataSourceType.respText:

                // todo...
                break;

            // 数据源为xml响应体
            case DataSourceType.respXml:

                // todo...
                break;
        }

        myAssert.setResult(examine(myAssert.getExpectType(), myAssert.getExpectValue(), myAssert.getExpectRelation(),
                myAssert.getRealValue(), myAssert.getRealType()));
    }

    /**
     * 执行断言数据的判等
     *
     * @param expectType
     * @param expectValue
     * @param expectRelation
     * @param realValue
     * @param realType
     * @return
     */
    public static boolean examine(Integer expectType, String expectValue, Integer expectRelation,
                                  Object realValue, Integer realType) {

        // 如果类型不同直接判false
        if (!expectType.equals(realType)) {
            return false;
        }

        // 针对期望值和实际值的关系做进一步判断
        switch (expectRelation) {

            // 相等
            case ExpectRelationshipType.equalTo:
                return expectValue.equals(realValue);

            // 大于
            case ExpectRelationshipType.greaterThan:
                return expectValue.compareTo(String.valueOf(realValue)) > 0;

            // 大于等于
            case ExpectRelationshipType.greaterThanOrEqualTo:
                return expectValue.compareTo(String.valueOf(realValue)) >= 0;

            // 小于
            case ExpectRelationshipType.lessThan:
                return expectValue.compareTo(String.valueOf(realValue)) < 0;

            // 小于等于
            case ExpectRelationshipType.lessThanOrEqualTo:
                return expectValue.compareTo(String.valueOf(realValue)) <= 0;

            // 包含
            case ExpectRelationshipType.contain:
                return expectValue.contains(String.valueOf(realValue));

            // 被包含
            case ExpectRelationshipType.contained:
                return String.valueOf(realValue).contains(expectValue);

            // 不包含
            case ExpectRelationshipType.doNotContain:
                return !expectValue.contains(String.valueOf(realValue));

            // 开始于
            case ExpectRelationshipType.startWith:
                return expectValue.startsWith(String.valueOf(realValue));

            // 结束于
            case ExpectRelationshipType.endWith:
                return expectValue.endsWith(String.valueOf(realValue));
        }

        return false;
    }
}