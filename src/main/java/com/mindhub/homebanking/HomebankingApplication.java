package com.mindhub.homebanking;

import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
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
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountrepository, TransactionRepository transactionrepository){
		return args -> {
			Client client1 = new Client("Melba", "Morel","melba@mindhub.com");
			Client client2 = new Client("Martin", "Sanchez","martinsr@mindhub.com");


			Account account1 = new Account( "1", 8000.0, LocalDate.now());
			Account account2 = new Account( "2", 7000.5, LocalDate.now().plusDays(1));

			Transaction transaction1 = new Transaction(-500.4, "transferencia", TransactionType.DEBIT);
			Transaction transaction2 = new Transaction(300, "deposito", TransactionType.CREDIT);

			client1.addAccount( account1 );
			client1.addAccount( account2 );

			account1.addTransaction( transaction1 );
			account1.addTransaction( transaction2 );

			clientRepository.save( client1 );
			clientRepository.save( client2 );

			accountrepository.save(account1);
			accountrepository.save(account2);

			transactionrepository.save( transaction1 );
			transactionrepository.save( transaction2 );


		};
	}
}
