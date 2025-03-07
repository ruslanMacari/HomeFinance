package homefinance.common.config;

import java.util.List;
import java.util.Locale;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Bean
  @Override
  public LocalValidatorFactoryBean getValidator() {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(messageSource());
    return bean;
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
    source.setBasenames("classpath:locales/messages", "classpath:locales/errors");
    source.setDefaultEncoding("UTF-8");
    return source;
  }

  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver localeResolver = new SessionLocaleResolver();
    localeResolver.setDefaultLocale(getDefaultLocale());
    return localeResolver;
  }

  private Locale getDefaultLocale() {
    Locale defaultLocale = Locale.getDefault();
    if (defaultLocale.getLanguage().equalsIgnoreCase("ru")) {
      return defaultLocale;
    }
    return Locale.ENGLISH;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
    interceptor.setParamName("lang");
    registry.addInterceptor(interceptor);
  }

  @Bean
  public FilterRegistrationBean<HiddenHttpMethodFilter> hiddenHttpMethodFilter() {
    FilterRegistrationBean<HiddenHttpMethodFilter> filterBean = new FilterRegistrationBean<>(new HiddenHttpMethodFilter());
    filterBean.setUrlPatterns(List.of("/*"));
    filterBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
    return filterBean;
  }

}
