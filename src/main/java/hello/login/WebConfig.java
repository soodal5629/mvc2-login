package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {

    @Bean
    // 필터 등록
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LogFilter());
        filterFilterRegistrationBean.setOrder(1); // 필터 체인 중 순서 정하기
        filterFilterRegistrationBean.addUrlPatterns("/*"); // 적용할 url 패턴

        return filterFilterRegistrationBean;
    }

    @Bean
    // 필터 등록
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LoginCheckFilter());
        filterFilterRegistrationBean.setOrder(2); // 필터 체인 중 순서 정하기
        filterFilterRegistrationBean.addUrlPatterns("/*"); // 적용할 url 패턴

        return filterFilterRegistrationBean;
    }
}

