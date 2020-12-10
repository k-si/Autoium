package run.autoium.common.DataCode.request;

/**
 * 期望值和实际值的关系
 */
public interface ExpectRelationshipType {

    int equalTo = 0;

    int greaterThan = 1;

    int greaterThanOrEqualTo = 2;

    int lessThan = 3;

    int lessThanOrEqualTo = 4;

    int contain = 5;

    int contained = 6;

    int doNotContain = 7;

    int startWith = 8;

    int endWith = 9;
}
