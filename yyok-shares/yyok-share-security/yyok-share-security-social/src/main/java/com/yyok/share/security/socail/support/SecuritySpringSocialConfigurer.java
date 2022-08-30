package com.yyok.share.security.socail.support;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

@EqualsAndHashCode(callSuper = true)
@Data
public class SecuritySpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    public SecuritySpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        filter.setSignupUrl("/socialRegister");
        return (T) filter;
    }
}
