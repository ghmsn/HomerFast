package cn.bdc.weChatService.common.config;

import cn.bdc.weChatService.common.interceptor.GlobalInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 继承WebMvcConfigureAdapter继承并重写addInterceptor方法用于添加配置拦截器
 * 
 * @author guhm
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 添加拦截器
		registry.addInterceptor(new GlobalInterceptor()).addPathPatterns("/**");
		
	}
}
