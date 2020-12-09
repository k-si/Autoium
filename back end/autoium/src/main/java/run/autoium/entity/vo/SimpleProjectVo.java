package run.autoium.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleProjectVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String label;

    private String value;
}
