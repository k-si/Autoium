package run.autoium.entity.vo;

import lombok.Data;
import run.autoium.entity.MyHeader;
import run.autoium.entity.MyParams;

import java.io.File;
import java.io.Serializable;
import java.util.List;

@Data
public class ApiCaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String projectId;

    private String apiCaseSuiteId;

    private String host;

    private String path;

    /**
     * 请求类型 0 GET、1 POST、2 PUT、3 DELETE
     */
    private Integer reqMethod;

    private List<MyHeader> reqHeader;

    private List<MyParams> reqParams;

    /**
     * 请求body的类型 0 json、1 form、2 file
     */
    private Integer reqBodyType;

    private String reqBodyJson;

    private List<MyParams> reqBodyForm;

//    private File reqBodyFile;

    private String examine;

    private String preAction;

    private String description;
}
