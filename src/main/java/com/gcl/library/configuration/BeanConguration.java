package com.gcl.library.configuration;

import com.gcl.library.filter.CheckLoginFilter;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.servlet.Filter;

/**
 * Created by gcl on 2016/12/15.
 * BeanConguration
 */

@Configuration
public class BeanConguration {
    @Bean
    @Scope("session")
    public HttpClient httpClient() {
        HttpClientParams params = new HttpClientParams();
        params.setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        return new HttpClient(params);
    }
}
