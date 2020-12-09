package run.autoium.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("api_case")
public class ApiCaseManage {

    /**
     * 对象序列化版本
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用例ID")
    @TableId(value = "id" , type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 用例名称
     */
    @ApiModelProperty(value = "用例名称")
    private String name;

    /**
     * 所属模块ID
     */
    @ApiModelProperty(value = "所属模块ID")
    private String projectId;

    /**
     * 所属模块名称
     */
    @ApiModelProperty(value = "所属模块名称")
    @TableField(exist = false)
    private String projectName;

    /**
     * 所属模块ID
     */
    @ApiModelProperty(value = "所属模块ID")
    private String apiCaseSuiteId;

    /**
     * 所属模块名称
     */
    @ApiModelProperty(value = "所属模块名称")
    @TableField(exist = false)
    private String apiCaseSuiteName;


    /**
     * 用例的描述
     */
    @ApiModelProperty(value = "用例的描述")
    private String description;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private int status;

    /**
     * 创建的起始时间
     */
    @ApiModelProperty(value = "创建的起始时间")
    @TableField(exist = false)
    private String gmtCreateStart;

    /**
     * 创建的结束时间
     */
    @ApiModelProperty(value = "创建的结束时间")
    @TableField(exist = false)
    private String gmtCreateEnd;


    @TableField(exist = false)
    private String value;

    @TableField(exist = false)
    private String label;
}
