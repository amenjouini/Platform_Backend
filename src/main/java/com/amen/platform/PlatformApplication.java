package com.amen.platform;

import com.amen.platform.auth.AuthenticationService;
import com.amen.platform.auth.RegisterRequest;
import com.amen.platform.user.EmailService;
import com.amen.platform.user.Role;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import static com.amen.platform.user.Role.ADMIN;
import static com.amen.platform.user.Role.MANAGER;

@SpringBootApplication
public class PlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlatformApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(AuthenticationService service){
//		return args -> {
//			var admin = RegisterRequest.builder()
//					.firstName("Admin")
//					.lastName("Admin")
//					.email("admin@gmail.com")
//					.password("password")
//					.role(ADMIN)
//					.build();
//			System.out.println("Admin token: " + service.register(admin).getAccessToken());
//
//			var manager = RegisterRequest.builder()
//					.firstName("Manager")
//					.lastName("Manager")
//					.email("manager@gmail.com")
//					.password("password")
//					.role(MANAGER)
//					.build();
//			System.out.println("Manager token: " + service.register(manager).getAccessToken());
//		};
//	}



}
