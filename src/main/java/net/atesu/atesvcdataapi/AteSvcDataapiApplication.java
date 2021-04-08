package net.atesu.atesvcdataapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("net.atesu.atesvcdataapi.dao") // mybatis扫描包路径
@SpringBootApplication
public class AteSvcDataapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AteSvcDataapiApplication.class, args);
    }

}
