package com.mindhub.homebanking;

import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Set;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountrepository, TransactionRepository transactionrepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository){
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

			Loan loan1 = new Loan("Mortage", 500000, Set.of(12, 24, 36,48,60));
			Loan loan2 = new Loan("Personal", 100000, Set.of(6, 12, 24));
			Loan loan3 = new Loan("Automotive", 300000, Set.of(6, 12, 24, 36));

			ClientLoan clientLoan1 = new ClientLoan( 400000, 60 );
			ClientLoan clientLoan2 = new ClientLoan( 50000, 12 );

			client1.addClientLoan( clientLoan1 );
			client1.addClientLoan( clientLoan2 );

			loan1.addClientLoan( clientLoan1 );
			loan2.addClientLoan( clientLoan2 );

			clientRepository.save( client1 );
			clientRepository.save( client2 );

			accountrepository.save(account1);
			accountrepository.save(account2);

			transactionrepository.save( transaction1 );
			transactionrepository.save( transaction2 );

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			clientLoanRepository.save( clientLoan1 );
			clientLoanRepository.save( clientLoan2 );


		};
	}
}
