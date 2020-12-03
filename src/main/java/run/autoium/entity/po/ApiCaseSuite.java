package run.autoium.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 包含多个测试用例的文件夹
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ApiCaseSuite对象", description="用例模块")
public class ApiCaseSuite implements Serializable {

    /**
     * 对象序列化版本
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用例模块ID
     */
    @ApiModelProperty(value = "用例模块ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 用例模块名称
     */
    @ApiModelProperty(value = "用例模块名称")
    private String name;

    /**
     * 所属项目ID
     */
    @ApiModelProperty(value = "所属项目ID")
    private String projectId;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    /**
     * 用例模块简介
     */
    @ApiModelProperty(value = "用例模块简介")
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
}
