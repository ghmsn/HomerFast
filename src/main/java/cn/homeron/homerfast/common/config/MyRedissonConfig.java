package cn.homeron.homerfast.common.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Author chenjie
 * @Date 2021/1/17 11:12
 * @Version 1.0
 **/
@Configuration
public class MyRedissonConfig {

    @Value(value = "${spring.redis.host}")
    private String redisHost;

    @Value(value = "${spring.redis.port}")
    private String redisPort;

    @Value(value = "${spring.redis.password}")
    private String redisPassword;

    /**
     * 对Redisson的使用都是通过RedissonClient
     * @return
     * @throws IOException
     */
    @Bean(destroyMethod="shutdown")
    public RedissonClient redisson() throws IOException {
        // 单Redis节点模式
        // 1、创建配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://".concat(redisHost).concat(":").concat(redisPort));
        if(StringUtils.isNotBlank(redisPassword)){
            config.useSingleServer().setPassword(redisPassword);
        }
        // 2、根据config创建RedissonClient示例
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
