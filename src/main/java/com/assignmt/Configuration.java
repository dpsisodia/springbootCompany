package com.assignmt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
      ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
      messageBundle.setBasename("classpath:messages/messages");
      messageBundle.setDefaultEncoding("UTF-8");
      return messageBundle;
    }
}
