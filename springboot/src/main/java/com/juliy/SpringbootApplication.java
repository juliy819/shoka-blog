package com.juliy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author juliy
 * @date 2023/3/1 13:23
 */
@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(SpringbootApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");

        System.out.println("----------------------------------------------------------\n\t" +
                "Application  is running! Access URLs:\n\t" +
                "Local访问网址: \t\thttp://localhost:" + port + "\n\t" +
                "External访问网址: \thttp://" + ip + ":" + port + "\n\t" +
                "文档地址: \t\t\thttp://localhost:" + port + "/doc.html\n" +
                "----------------------------------------------------------");
    }

}
