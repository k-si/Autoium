package run.autoium.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SimpleApiSuiteVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String label;

    private String value;

    private Boolean buttonable;

    private List<SimpleApiCaseVo> children = new ArrayList<>();
}