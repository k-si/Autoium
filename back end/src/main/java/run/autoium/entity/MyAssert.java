package run.autoium.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MyAssert implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 要验证的内容：响应码，响应头，响应体
     */
    private Integer dataSource;

    /**
     * 从响应体中提取参数的表达式
     */
    private String express;

    /**
     * 实际值和期望值的关系
     */
    private Integer expectRelation;

    /**
     * 期望类型
     */
    private Integer expectType;

    /**
     * 期望值
     */
    private String expectValue;

    /**
     * 实际类型
     */
    private Integer realType;

    /**
     * 实际值
     */
    private String realValue;

    /**
     * 断言结果
     */
    private Boolean result;
}
