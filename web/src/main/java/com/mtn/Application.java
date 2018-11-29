package com.mtn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Allen on 4/20/2017.
 */
@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        SpringApplication.run(Application.class, args);
    }

}
