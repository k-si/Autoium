package run.autoium.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 本质还是测试用例vo，但是会包含项目名称和文件夹名称
 */
@Data
public class ApiCaseManageVo extends ApiCaseVo {

    private static final long serialVersionUID = 1L;


    private String projectName;

    private String apiCaseSuiteName;

    private String gmtCreate;

    private String gmtModified;

    @TableField(exist=false)
    private String value;

    @TableField(exist=false)
    private String label;
}
