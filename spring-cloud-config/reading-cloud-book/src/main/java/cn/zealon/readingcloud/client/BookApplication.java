package cn.zealon.readingcloud.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图书资源中心
 * @author zealon
 */
@EnableEurekaClient
@SpringBootApplication
@RestController
@RefreshScope
public class BookApplication {

    @Value("${from}")
    private String from;

    @GetMapping("/from")
    public String heiHei(){
        return "from:"+from;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }

}
