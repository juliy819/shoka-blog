package com.juliy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@Slf4j
public class SpringbootApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(SpringbootApplication.class, args);
        System.out.println("""
                                      _       _ _             _     _            \s
                                     (_)_   _| (_)_   _      | |__ | | ___   __ _\s
                                     | | | | | | | | | |_____| '_ \\| |/ _ \\ / _` |
                                     | | |_| | | | |_| |_____| |_) | | (_) | (_| |
                                    _/ |\\__,_|_|_|\\__, |     |_.__/|_|\\___/ \\__, |
                                   |__/           |___/                     |___/\s""".indent(1));

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
