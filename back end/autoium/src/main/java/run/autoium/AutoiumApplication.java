package run.autoium;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("run.autoium.mapper")
public class AutoiumApplication {
    public static void main(String[] args) {

        SpringApplication.run(AutoiumApplication.class, args);
    }
}
