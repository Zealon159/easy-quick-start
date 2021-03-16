package cn.zealon.quick.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zealon
 * @since: 2021/3/16
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient config(){
        Config config = new Config();
        //config.setTransportMode(TransportMode.EPOLL);
        config.useSingleServer().setAddress("redis://localhost:6379")
                .setKeepAlive(true);
        return Redisson.create(config);
    }
}
