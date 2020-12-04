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
 * 接口测试用例的实体类，数据存于数据库内
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ApiCase对象", description="接口用例")
public class ApiCase implements Serializable {

    /**
     * 对象序列化版本
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用例id
     */
    @ApiModelProperty(value = "用例ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
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
     * 所属模块ID
     */
    @ApiModelProperty(value = "所属模块ID")
    private String apiCaseSuiteId;

    /**
     * 协议和域名
     */
    @ApiModelProperty(value = "协议和域名")
    private String host;

    /**
     * 路径
     */
    @ApiModelProperty(value = "路径")
    private String path;

    /**
     * 请求类型 0 GET、1 POST、2 PUT、3 DELETE
     */
    @ApiModelProperty(value = "请求类型;0:GET;1:PUT;2:PUT;3:DELETE")
    private Integer reqMethod;

    /**
     * 用例的请求头
     */
    @ApiModelProperty(value = "用例的请求头")
    private String reqHeader;

    /**
     * 用例的请求参数
     */
    @ApiModelProperty(value = "用例的请求参数")
    private String reqParams;

    /**
     * 请求body的类型 0 json、1 form、2 file
     */
    @ApiModelProperty(value = "请求body的类型; -1:body，0:json，1:form，2:file")
    private Integer reqBodyType;

    /**
     * 用例的json类型请求体
     */
    @ApiModelProperty(value = "用例的json类型请求体")
    private String reqBodyJson;

    /**
     * 用例的form类型请求体
     */
    @ApiModelProperty(value = "用例的form类型请求体")
    private String reqBodyForm;

    /**
     * 用例的文件类型请求体,用阿里OSS存储
     */
    @ApiModelProperty(value = "用例的文件类型请求体,用阿里OSS存储")
    private String reqBodyFile;

    /**
     * 请求的断言
     */
    @ApiModelProperty(value = "请求的断言")
    private String examine;

    /**
     * 前置操作例如登录,是所有接口用例中的一个
     */
    @ApiModelProperty(value = "前置操作例如登录,是所有接口用例中的一个")
    private String preAction;

    /**
     * 用例的描述
     */
    @ApiModelProperty(value = "用例的描述")
    private String description;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private Integer sort;

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
