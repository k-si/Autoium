package run.autoium.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MyParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;

    private String value;
}
