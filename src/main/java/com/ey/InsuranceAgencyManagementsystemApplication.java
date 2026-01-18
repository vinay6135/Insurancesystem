package com.ey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class InsuranceAgencyManagementsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsuranceAgencyManagementsystemApplication.class, args);
	}

}
