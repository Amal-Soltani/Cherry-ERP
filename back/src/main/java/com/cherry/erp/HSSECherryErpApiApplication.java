package com.cherry.erp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Properties;

@Slf4j
@SpringBootApplication
@EnableAsync
public class HSSECherryErpApiApplication {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    private static final String SERVER_PORT = "server.port";
    private static final String SERVER_SERVLET_CONTEXT = "server.servlet.context-path";


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(HSSECherryErpApiApplication.class);
        Environment env = app.run(args).getEnvironment();

        log.info("\n\n************************* CHERRY ERP *****************************\n\n\t" +
                        "{} is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}{}\n\t" +
                        "Swagger: \t\thttp://localhost:{}{}/swagger-ui.html\n\t" +
                        "Monitoring : \t\thttp://localhost:{}{}/actuator\n\t" +
                 "\n********************************************************************\n\n",
                env.getProperty("spring.application.name"),
                env.getProperty(SERVER_PORT),
                env.getProperty(SERVER_SERVLET_CONTEXT),
                env.getProperty(SERVER_PORT),
                env.getProperty(SERVER_SERVLET_CONTEXT),
                env.getProperty(SERVER_PORT),
                env.getProperty(SERVER_SERVLET_CONTEXT));

    }

    @Bean(name = "emailSenderBean")
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(587);


        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }

}
