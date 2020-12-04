package run.autoium.entity.vo;

import lombok.Data;

@Data
public class ApiCaseSuiteVo {
    //名称
    private String name;

    //描述
    private String description;

    //创建时间-开始
    private String gmtCreateStart;

    //创建时间-结束
    private String gmtCreateEnd;
}
