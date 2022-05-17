package itmo.mpi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"itmo.mpi.service", "itmo.mpi.entity",
        "itmo.mpi.controller", "itmo.mpi.impl",
        "itmo.mpi.config", "itmo.mpi.exception"})
@EntityScan("itmo.mpi.entity")
@EnableJpaRepositories("itmo.mpi.repository")
public class MpiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MpiApplication.class, args);
    }
}

