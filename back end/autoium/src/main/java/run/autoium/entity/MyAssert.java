package run.autoium.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyAssert implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 要验证的内容：响应码，响应头，响应体（json、text、xml）
     */
    private Integer dataSource;

    /**
     * dataSource的文字描述
     */
    private String data;

    /**
     * 从响应体中提取参数的表达式
     */
    private String express;

    /**
     * 实际值和期望值的关系
     */
    private Integer expectRelation;

    /**
     * expectRelation的文字描述
     */
    private String relation;

    /**
     * 期望类型
     */
    private Integer expectType;

    /**
     * expectType的文字描述
     */
    private String type;

    /**
     * 期望值
     */
    private String expectValue;

    /**
     * 实际类型
     */
    private Integer realType;

    /**
     * 实际类型的文字
     */
    private String realTypeText;

    /**
     * 实际值
     */
    private Object realValue;

    /**
     * 断言结果
     */
    private String result;
}
