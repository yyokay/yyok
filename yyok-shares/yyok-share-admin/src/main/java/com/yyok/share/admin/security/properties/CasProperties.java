package com.yyok.share.admin.security.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

/**
 * 创建了CAS Client相关的配置信息
 */
@Data
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "cas")
public class CasProperties {

    @Value("${cas.server.prefix}")
    private String casServerPrefix;

    @Value("${cas.server.login}")
    private String casServerLogin;

    @Value("${cas.server.logout}")
    private String casServerLogout;

    @Value("${cas.client.login}")
    private String casClientLogin;

    @Value("${cas.client.logout}")
    private String casClientLogout;

    @Value("${cas.client.relative}")
    private String casClientLogoutRelative;

    @Value("${cas.account.inmemory}")
    private String casAccountInMemory;

    /**
     * 配置CAS Client的属性
     *
     * @return
     */
/*    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        // 与CasAuthenticationFilter监视的URL一致
        serviceProperties.setService(casClientLogin);
        // 是否关闭单点登录，默认为false，所以也可以不设置。
        serviceProperties.setSendRenew(false);
        return serviceProperties;
    }*/

    /**
     * 配置CAS认证入口，提供用户浏览器重定向的地址
     *
     * @param sp
     * @return
     */
/*    @Bean
    @Primary
    public AuthenticationEntryPoint authenticationEntryPoint(ServiceProperties sp) {
        CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
        // CAS Server认证的登录地址
        entryPoint.setLoginUrl(casServerLogin);
        entryPoint.setServiceProperties(sp);
        return entryPoint;
    }*/

    /**
     * 配置ticket校验功能，需要提供CAS Server校验ticket的地址
     *
     * @return
     */
/*    @Bean
    public TicketValidator ticketValidator() {
        // 默认情况下使用Cas20ProxyTicketValidator，验证入口是${casServerPrefix}/proxyValidate
        return new Cas20ProxyTicketValidator(casServerPrefix);
    }*/

    /**
     * 在内存中创建一个用户并分配权限
     *
     * @return
     */
/*    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername(casUserInMemory).password("").roles("USER").build());
        return manager;
    }*/

    /**
     * 设置cas认证处理逻辑
     *
     * @param sp
     * @param ticketValidator
     * @param userDetailsService
     * @return
     */
/*    @Bean
    public CasAuthenticationProvider casAuthenticationProvider(ServiceProperties sp, TicketValidator ticketValidator, UserDetailsService userDetailsService) {
        CasAuthenticationProvider provider = new CasAuthenticationProvider();
        provider.setServiceProperties(sp);
        provider.setTicketValidator(ticketValidator);
        provider.setUserDetailsService(userDetailsService);
        provider.setKey("yyg");
        return provider;
    }*/

    /**
     * 提供CAS认证专用过滤器，过滤器的认证逻辑由CasAuthenticationProvider提供
     *
     * @param sp
     * @param ap
     * @return
     */
/*    @Bean
    public CasAuthenticationFilter casAuthenticationFilter(ServiceProperties sp, AuthenticationProvider ap) {
        CasAuthenticationFilter filter = new CasAuthenticationFilter();
        filter.setServiceProperties(sp);
        filter.setAuthenticationManager(new ProviderManager(Arrays.asList(ap)));
        return filter;
    }*/

    /**
     * 配置单点登录过滤器，接受cas服务端发出的注销请求
     *
     * @return
     */
  /*  @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        singleSignOutFilter.setCasServerUrlPrefix(casServerPrefix);
        singleSignOutFilter.setIgnoreInitConfiguration(true);
        return singleSignOutFilter;
    }*/

    /**
     * 将注销请求转发到cas server
     *
     * @return
     */
    @Bean
    public LogoutFilter logoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter(casServerLogout, new SecurityContextLogoutHandler());
        // 设置客户端注销请求的路径
        logoutFilter.setFilterProcessesUrl(casClientLogoutRelative);
        return logoutFilter;
    }

}
