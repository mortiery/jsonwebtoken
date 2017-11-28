package de.yannickmortier.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class AuthServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}
}
