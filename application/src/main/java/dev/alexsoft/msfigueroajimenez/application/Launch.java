package dev.alexsoft.msfigueroajimenez.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("dev.alexsoft.*")
@EnableJpaRepositories("dev.alexsoft")
@EntityScan("dev.alexsoft.*")
@EnableFeignClients("dev.alexsoft.*")
public class Launch {

    public static void main(String[] args) {
        SpringApplication.run(Launch.class, args);
    }

}
