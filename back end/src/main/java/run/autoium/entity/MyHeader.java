package run.autoium.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MyHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    public MyHeader() {

    }

//    public MyHeader(String key, String value) {
//        this.key = key;
//        this.value = value;
//    }

    private String key;

    private String value;
}
