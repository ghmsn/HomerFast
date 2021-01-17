package cn.homeron.homerfast.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @Author guhm
 * @Date 2020/12/17 00:06
 * @Version 1.0
 **/
@EnableRedisHttpSession
@Configuration
public class HomerFastSessionConfig {

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("HOMERFASTSESSION");
        serializer.setDomainName("homerfast.cn");
        return serializer;
    }

}
