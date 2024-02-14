package com.brodygaudel.accountservice;

import com.brodygaudel.accountservice.command.util.implementation.Counter;
import com.brodygaudel.accountservice.command.util.implementation.CounterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@SpringBootApplication
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CounterRepository counterRepository){
		return args -> {
			if(counterRepository.findAll().isEmpty()){
				Long id = 999999999999999L;
				counterRepository.save( new Counter(id));
			}
		};
	}

}
