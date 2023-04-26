package com.neuedu.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {
    @Autowired
    private V2Config v2Config;

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Contact contact = new Contact("杨杨编程", "www.xxxx.com", "1076072836@qq.com");
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("科技学院校园系统")
                        .description("科技学院校园系统项目前端接口文档")
                        //服务条款Url
                        .termsOfServiceUrl("www.xxxx111.com")
                        .contact(contact)
                        .version(v2Config.getVersion())
                        .build())
                //分组名称
                .groupName("f2")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.neuedu.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

}