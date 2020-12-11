package run.autoium.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleApiCaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String suiteId;

    private String label;

    private Boolean buttonable;

    private Integer method;

}
