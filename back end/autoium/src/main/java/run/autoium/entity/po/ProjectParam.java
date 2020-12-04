package run.autoium.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 参数化
 * </p>
 *
 * @author Ks1r
 * @since 2020-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ProjectParam对象", description="参数化")
public class ProjectParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "参数ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "所属项目ID")
    private String projectId;

    @ApiModelProperty(value = "参数名称")
    private String name;

    @ApiModelProperty(value = "参数类型：0:字符串;1:数值;2:数据库;3:布尔;4:数")
    private Integer type;

    @ApiModelProperty(value = "参数键")
    private String key;

    @ApiModelProperty(value = "参数值")
    private String value;

    @ApiModelProperty(value = "显示排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
