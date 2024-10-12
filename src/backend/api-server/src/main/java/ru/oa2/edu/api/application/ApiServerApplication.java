package ru.oa2.edu.api.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages={"ru.oa2.edu.api.*"})
@EnableJpaRepositories(basePackages={
		"ru.oa2.edu.api.application.database",
		"ru.oa2.edu.api.domain.job"
})
@EnableTransactionManagement
@EntityScan(basePackages="ru.oa2.edu.api.application.entities")
public class ApiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiServerApplication.class, args);
	}

}
