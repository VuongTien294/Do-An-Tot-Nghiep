package com.doantotnghiep.demo.config;
import java.util.Properties;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.thymeleaf.templatemode.TemplateMode.HTML;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;


@Configuration
public class MailConfiguration {
//    @Autowired
//    private Environment env;

//    @Bean
//    public JavaMailSender getMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//
//        mailSender.setHost(env.getProperty("spring.mail.host"));
//        mailSender.setPort(Integer.valueOf(env.getProperty("spring.mail.port")));
//        mailSender.setUsername(env.getProperty("spring.mail.username"));
//        mailSender.setPassword(env.getProperty("spring.mail.password"));
//
//        Properties javaMailProperties = new Properties();
//        javaMailProperties.put("mail.smtp.starttls.enable", "true");
//        javaMailProperties.put("mail.smtp.auth", "true");
//        javaMailProperties.put("mail.transport.protocol", "smtp");
//        javaMailProperties.put("mail.debug", "true");
//
//        mailSender.setJavaMailProperties(javaMailProperties);
//        return mailSender;
//    }

    @Bean
    @Primary
    public SpringResourceTemplateResolver htmlTemplateResolver() {
        SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
        springResourceTemplateResolver.setPrefix("classpath:/templates/");
        springResourceTemplateResolver.setSuffix(".html");
        springResourceTemplateResolver.setTemplateMode(HTML);
        springResourceTemplateResolver.setCharacterEncoding("UTF-8");
        return springResourceTemplateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(MessageSource messageSource,
                                               SpringResourceTemplateResolver templateResolver) {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setMessageSource(messageSource);
        springTemplateEngine.setTemplateResolver(templateResolver);
        return springTemplateEngine;
    }

}
