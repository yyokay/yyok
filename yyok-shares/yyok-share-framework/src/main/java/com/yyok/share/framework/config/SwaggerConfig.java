package com.yyok.share.framework.config;

import com.fasterxml.classmate.TypeResolver;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.yyok.share.framework.annotation.AnonymousAccess;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRuleConvention;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * api页面 /doc.html
 * @Author yyok
 * @Date 2022/05/9
 **/
@Configuration
@EnableSwagger2
//@EnableSwaggerBootstrapUI
@ConditionalOnWebApplication
public class SwaggerConfig {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.token-start-with}")
    private String tokenStartWith;

    @Value("${swagger.enabled}")
    private boolean enabled;

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.version}")
    private String version;

    @Value("${swagger.serverUrl}")
    private String serverUrl;

/*    @Bean
    @SuppressWarnings("all")
    public Docket createRestApi() {
        RequestParameterBuilder ticketPar = new RequestParameterBuilder();
        List<RequestParameter> pars = new ArrayList<>();
        ticketPar.description("token").name(tokenHeader)
                .in(ParameterType.HEADER).required(true)
                .query(param -> param.model(model -> model.scalarModel(ScalarType.STRING)));
        pars.add(ticketPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enabled)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yyok"))
                .paths(PathSelectors.regex("/error.*").negate())
                .build()
                .globalRequestParameters(pars);
    }*/

    @Bean
    @SuppressWarnings("all")
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        ticketPar
                .name(tokenHeader)
                .description("token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue(tokenStartWith + " ")
                .required(true)
                .build();
        pars.add(ticketPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enabled)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("^(?!auth).*$"))
                .apis(RequestHandlerSelectors.basePackage("com.yyok"))
                //.paths(Predicates.not(PathSelectors.regex("\/error.*")))
                //.paths(PathSelectors.regex("/error.*").negate())
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .termsOfServiceUrl(serverUrl)
                .description(title)
                .version(version)
                .contact(new Contact("yyok", "https://www.yyokay.com", "YYOK@163.com"))
                .build();
    }

    /**
     * bearer 授权上下文
     * @return List<SecurityContext>
     */
    private List<SecurityContext> securityContext() {
        SecurityContext context = new SecurityContext(
                defaultAuth(),
                // 配置需要访问授权的请求，效果是对应页面上的请求有没有小锁图标
                PathSelectors.regex("/sys/auth/*").negate(),
                // 配置需要访问授权的请求，效果是对应页面上的请求有没有小锁图标
                each -> true,
                // operationSelector优先级高于上面两个，配置需要访问授权的请求，效果是对应页面上的请求有没有小锁图标
                // 将auth开头的请求和类、方法上有指定注解的请求在swagger页面上放行，不使用jwt bearer token 授权方案
                operationContext -> !operationContext.requestMappingPattern().matches("/sys/auth/*")
                        && !operationContext.findControllerAnnotation(AnonymousAccess.class).isPresent() &&
                        !operationContext.findAnnotation(AnonymousAccess.class).isPresent()
        );
        return Collections.singletonList(context);
    }

    private List<SecurityScheme> securitySchemes() {

        return new ArrayList(
                Collections.singleton(new ApiKey("Authorization", "Authorization", "header")));
    }

    private List<SecurityContext> securityContexts() {
        return new ArrayList(
                Collections.singleton(SecurityContext.builder()

                                // 配置需要访问授权的请求，效果是对应页面上的请求有没有小锁图标
                        //.PathSelectors.regex("/sys/auth/*").negate()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build())
        );
    }

    /**
     * bearer 授权范围
     */
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new ArrayList(
                Collections.singleton(new SecurityReference("Authorization", authorizationScopes)));
    }

}

/**
 *  将Pageable转换展示在swagger中
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
class SwaggerDataConfig {

    @Bean
    public AlternateTypeRuleConvention pageableConvention(final TypeResolver resolver) {
        return new AlternateTypeRuleConvention() {
            @Override
            public int getOrder() {
                return HIGHEST_PRECEDENCE;
            }

            @Override
            public List<AlternateTypeRule> rules() {
                return newArrayList(newRule(resolver.resolve(Pageable.class), resolver.resolve(Page.class)));
            }
        };
    }

    @ApiModel
    @Data
    private static class Page {
        @ApiModelProperty("页码 (0..N)")
        private Integer page;

        @ApiModelProperty("每页显示的数目")
        private Integer size;

        @ApiModelProperty("以下列格式排序标准：property[,asc | desc]。 默认排序顺序为升序。 支持多种排序条件：如：id,asc")
        private List<String> sorter;
    }
}
