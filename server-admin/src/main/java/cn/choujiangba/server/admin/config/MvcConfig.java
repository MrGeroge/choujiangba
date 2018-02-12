package cn.choujiangba.server.admin.config;

import cn.choujiangba.server.admin.interceptor.CROSInterceptor;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.List;

/**
 *
 *
 * Author:zhangyu
 * create on 15/10/19.
 */
@Configuration
@ImportResource("classpath:services-test-mysql.xml")
@ComponentScan(basePackages = "cn.choujiangba.server.admin.controller")
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter{

    @Bean(name = "multipartResolver")
    public StandardServletMultipartResolver initMultipartResolver(){
        StandardServletMultipartResolver resolver=new StandardServletMultipartResolver();
        //resolver.setDefaultEncoding("UTF-8");
        //resolver.setMaxUploadSize(512000);

        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CROSInterceptor());
    }


    @Override
    public void addArgumentResolvers(
            List<HandlerMethodArgumentResolver> argumentResolvers) {
        //用于从session中取值
        //argumentResolvers.add(new SessionScopeArgumentResolver());
    }

    /**
     * 定义静态资源访问路径
     * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
        registry.addResourceHandler("/views/**").addResourceLocations("/views/");
    }

    /**same as <mvc:default-servlet-handler/>*/
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        //对返回JSON支持
        registry.enableContentNegotiation(new MappingJackson2JsonView());

        //配置thymeleaf为模版引擎
        ServletContextTemplateResolver tplResolver=new ServletContextTemplateResolver();
        tplResolver.setPrefix("/WEB-INF/template/");
        tplResolver.setSuffix(".html");
        tplResolver.setCharacterEncoding("UTF-8");
        tplResolver.setTemplateMode("HTML5");
        //TODO:发布时候需要修改缓存时间
        tplResolver.setCacheTTLMs(1L);

        SpringTemplateEngine engine=new SpringTemplateEngine();
        engine.setTemplateResolver(tplResolver);

        ThymeleafViewResolver viewResolver=new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(engine);
        viewResolver.setCharacterEncoding("UTF-8");

        registry.viewResolver(viewResolver);
    }
}
