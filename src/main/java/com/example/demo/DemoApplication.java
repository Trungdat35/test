package com.example.demo;

import com.example.demo.model.Account;
import com.example.demo.model.Decentralization;
import com.example.demo.repository.AccountRepo;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "API Product",
				version = "1.0.0",
				description = "This project is only learning !",
				contact = @Contact(
						name = "Đỗ Trung Đạt",
						email = "dotrungdat.mda@gmail.com"
				)
		),
		servers = {
				@Server(
						description = "Local ENV",
						url = "http://localhost:8088"
				),
		},
		security = {
				@SecurityRequirement(
						name = "bearerAuth"
				)
		}
)
@SecurityScheme(
		name = "bearerAuth",
		description = "JWT auth description",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
)
public class DemoApplication implements CommandLineRunner {
	@Autowired
	private AccountRepo accountRepo;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Account adminAccount =  accountRepo.findByAccountByDeID(1);
       if(adminAccount==null){
		   Account account = new Account();
		   account.setUserName("admin");
		   account.setStatus(1);
		   account.setPassword(new BCryptPasswordEncoder().encode("admin"));
		   accountRepo.save(account);
	   }
	}
}
