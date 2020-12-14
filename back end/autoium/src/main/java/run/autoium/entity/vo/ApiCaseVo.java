package run.autoium.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import run.autoium.entity.MyAssert;
import run.autoium.entity.MyHeader;
import run.autoium.entity.MyParams;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ApiCaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ExcelProperty(value = "用例名称" , index = 0)
    private String name;

    private String projectId;

    @ExcelProperty(value = "项目名称" , index = 1)
    private String projectName;

    private String apiCaseSuiteId;

    @ExcelProperty(value = "文件夹名称" , index = 2)
    private String apiCaseSuiteName;

    @ExcelProperty(value = "主机地址" , index = 3)
    private String host;

    @ExcelProperty(value = "路径" , index = 4)
    private String path;

    @ExcelProperty(value = "请求类型" , index = 5)
    private String stringReqMethod;

    /**
     * 请求类型 0 GET、1 POST、2 PUT、3 DELETE
     */
    private Integer reqMethod;

    @ExcelProperty(value = "请求头" , index = 6)
    private String stringReqHeader;

    private List<MyHeader> reqHeader;

    @ExcelProperty(value = "请求参数" , index = 7)
    private String stringReqParams;

    private List<MyParams> reqParams;

    @ExcelProperty(value = "请求体类型" , index = 8)
    private String stringReqBodyType;

    /**
     * 请求body的类型 0 json、1 form、2 file
     */
    private Integer reqBodyType;

    @ExcelProperty(value = "json请求体" , index = 9)
    private String reqBodyJson;

    @ExcelProperty(value = "form请求体" , index = 10)
    private  String stringReqBodyForm;

    private List<MyParams> reqBodyForm;

    private String stringReqBodyFile;

//    private File reqBodyFile;

    @ExcelProperty(value = "断言" , index = 11)
    private String stringExamine;

    private List<MyAssert> examine;

    @ExcelProperty(value = "前置条件" , index = 12)
    private String preAction;

    private Integer finish;

    @ExcelProperty(value = "描述" , index = 13)
    private String description;

    @ExcelProperty(value = "排序等级" , index = 14)
    private Integer sort;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer status;

    //创建的起始时间和结束时间
    private String gmtCreateStart;

    private String gmtCreateEnd;

    private String value;

    private String label;
}
