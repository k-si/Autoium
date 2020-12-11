package run.autoium.common.DataCode.request;

/**
 * 期望值和实际值的关系
 */
public interface ExpectRelationshipType {

    /**
     * 等于
     */
    int equalTo = 0;

    /**
     * 大于
     */
    int greaterThan = 1;

    /**
     * 大于等于
     */
    int greaterThanOrEqualTo = 2;

    /**
     * 小于
     */
    int lessThan = 3;

    /**
     * 小于等于
     */
    int lessThanOrEqualTo = 4;

    /**
     * 包含
     */
    int contain = 5;

    /**
     * 被包含
     */
    int contained = 6;

    /**
     * 不包含
     */
    int doNotContain = 7;

    /**
     * 开始于
     */
    int startWith = 8;

    /**
     * 结束于
     */
    int endWith = 9;
}
