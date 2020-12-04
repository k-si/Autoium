package run.autoium.entity.vo;

import lombok.Data;
import run.autoium.entity.MyAssert;
import run.autoium.entity.MyHeader;

import java.io.Serializable;
import java.util.List;

@Data
public class ApiCaseResultVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<MyHeader> headers;

    private String body;

    private Integer bodyType;

    private Integer statusCode;

    private Integer respBodySize;

    private List<MyAssert> assertResult;

    private String exception;
}
