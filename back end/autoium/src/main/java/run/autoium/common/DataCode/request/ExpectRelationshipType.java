package run.autoium.common.DataCode.request;

/**
 * 期望值和实际值的关系
 */
public interface ExpectRelationshipType {

    Integer equalTo = 0;

    Integer greaterThan = 1;

    Integer greaterThanOrEqualTo = 2;

    Integer lessThan = 3;

    Integer lessThanOrEqualTo = 4;

    Integer contain = 5;

    Integer contained = 6;

    Integer doNotContain = 7;

    Integer startWith = 8;

    Integer endWith = 9;
}
