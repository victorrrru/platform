package com.fww;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("贷款流程接口")
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.fww"))
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build()
                .apiInfo(userInfo());
    }



    private ApiInfo userInfo() {
        ApiInfo apiInfo = new ApiInfo("贷款流程相关接口",//大标题
                "包括贷前、贷后相关接口",//小标题
                "v1.0.0",//版本
                "深圳.车飞贷",
                new Contact("杨培海", "", "472579211@qq.com"),// 作者
                "",//链接显示文字
                ""//网站链接
        );
        return apiInfo;
    }

}
