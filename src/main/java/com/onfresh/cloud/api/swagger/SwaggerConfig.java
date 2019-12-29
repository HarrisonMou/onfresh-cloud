package com.onfresh.cloud.api.swagger;

import com.onfresh.cloud.api.util.SpringContextUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final String prodBaseUrl = "www.onfresh.cn/v2/onfresh";

    private final String testBaseUrl = "localhost:9591";
    @Bean
    public Docket docket(){
        String activeProfile = SpringContextUtil.getActiveProfile("test");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .host(activeProfile != null ? testBaseUrl : prodBaseUrl)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.onfresh.cloud.api"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                //页面标题
                .title("onfresh RESTful API")
                //创建人
                .contact(new Contact("onfresh","http://www.onfresh.cn",""))
                //版本号
                .version("1.0")
                //描述
                .description("API 描述")
                .build();
    }
}
