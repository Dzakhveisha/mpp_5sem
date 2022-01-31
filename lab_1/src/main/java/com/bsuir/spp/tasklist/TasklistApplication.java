package com.bsuir.spp.tasklist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.bsuir.spp.tasklist**"})
@EntityScan("com.bsuir.spp.tasklist.dao.model")
@EnableJpaRepositories("com.bsuir.spp.tasklist.dao")
public class TasklistApplication {

    public static void main(String[] args) {
        SpringApplication.run(TasklistApplication.class, args);
    }

}
