package run.autoium.entity.vo;

import lombok.Data;

/**
 * 最近七天的用例执行情况
 *
 * @author lbj
 */
@Data
public class SevenDaysDataVo {

    //日期
    private String date;

    //当日用例总数
    private int total;

    //当日成功用例数
    private int successCount;

    //当日失败用例数
    private int failCount;

    //当日未执行用例数
    private int notPerformed;

    public SevenDaysDataVo() {
        this.date = "";
        this.failCount = 0;
        this.total = 0;
        this.successCount = 0;
        this.notPerformed = 0;
    }

    public void addSuccessCount() {
        this.successCount += 1;
    }

    public void addTotal() {
        this.total += 1;
    }

    public void addFailCount() {
        this.failCount += 1;
    }

    public void addNotPerformed() {
        this.notPerformed += 1;
    }
}
