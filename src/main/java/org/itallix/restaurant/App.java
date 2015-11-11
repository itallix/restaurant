package org.itallix.restaurant;

import org.itallix.restaurant.security.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "org.itallix.restaurant.models.persistence")
@EnableJpaRepositories(basePackages = "org.itallix.restaurant.dao.repository")
@Import({
        WebSecurityConfig.class
})
public class App {


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
