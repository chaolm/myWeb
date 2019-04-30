package com.dripop;

import com.dripop.core.config.WarApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

/**
 * dripop application
 */
@SpringBootApplication(scanBasePackages = {"com.dripop"})
@EnableScheduling
public class AppStarter extends WarApplicationConfiguration
{
    public static void main(String[] args) throws Exception {


        SpringApplication.run(AppStarter.class, args);

    }
}
