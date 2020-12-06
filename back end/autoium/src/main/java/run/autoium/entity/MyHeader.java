package run.autoium.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;

    private String value;
}
