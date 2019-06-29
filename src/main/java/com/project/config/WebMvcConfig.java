package com.project.config;


import com.project.config.interceptor.LoginInterceptor;
import com.project.config.resolve.SessionAttrArgumentResolve;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

/**
 * web mvc 扩展
 * 不继承WebMvcConfigurerAdapter 抽象类，因为在jdk8中，接口提供了默认的实现
 *
 * 执行机制：
 * @see org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration
 * 会自动注入WebMvcConfigurer ，然后进行遍历添加
 *
 * @Autowired(required = false)
 * 	public void setConfigurers(List<WebMvcConfigurer> configurers) {
 * 		if (!CollectionUtils.isEmpty(configurers)) {
 * 			this.configurers.addWebMvcConfigurers(configurers);
 * 		}
 * 	}
 * @author iscys
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * @see WebMvcAutoConfiguration.EnableWebMvcConfiguration
     * 添加参数解析器
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add( new SessionAttrArgumentResolve());

    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns("api/**,/wx/**","zyupload/**","/js/**","/kindeditor/**","/layui/**","/css/**","/bootstrap/**");
    }


}
