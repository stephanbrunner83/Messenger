package com.runtastic.fhooe.messanger.config;

import com.runtastic.fhooe.messanger.aop.logging.LoggingAspect;

import io.github.jhipster.config.JHipsterConstants;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}
