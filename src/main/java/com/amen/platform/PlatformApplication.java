package com.amen.platform;

import com.amen.platform.auth.AuthenticationService;
import com.amen.platform.auth.RegisterRequest;
import com.amen.platform.user.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.amen.platform.user.Role.ADMIN;

@SpringBootApplication
public class PlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlatformApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AuthenticationService service){
		return args -> {
			var admin = RegisterRequest.builder()
					.firstName("Admin")
					.lastName("Admin")
					.email("admin@gmail.com")
					.password("password")
					.role(ADMIN)
					.build();
			System.out.println("Admin token: " + service.register(admin).get);
		};
	}

}
