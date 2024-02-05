package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountrepository){
		return args -> {
			Client client1 = new Client("Melba", "Morel","melba@mindhub.com");
			Client client2 = new Client("Martin", "Sanchez","martinsr@mindhub.com");


			Account account1 = new Account( "1", 8000.0, LocalDate.now());
			Account account2 = new Account( "2", 7000.5, LocalDate.now().plusDays(1));

			client1.addAccount( account1 );
			client1.addAccount( account2 );

			clientRepository.save( client1 );
			clientRepository.save( client2 );

			accountrepository.save(account1);
			accountrepository.save(account2);


		};
	}
}
