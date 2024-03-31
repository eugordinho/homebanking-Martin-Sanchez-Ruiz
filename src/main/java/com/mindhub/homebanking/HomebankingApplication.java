package com.mindhub.homebanking;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.enums.TransactionType;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

 	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountrepository,
									  TransactionRepository transactionrepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return args -> {
			Client client1 = new Client("Melba", "Morel","melba@mindhub.com", passwordEncoder.encode("Melba1234"));

			Client client2 = new Client("Martin", "Sanchez","martinsr@mindhub.com", passwordEncoder.encode("Tin1234") );


			clientRepository.save( client1 );
			clientRepository.save( client2 );

			Account account1 = new Account( "VIN09379001", 8000.0, LocalDate.now());
			Account account2 = new Account( "VIN09379002", 7000.5, LocalDate.now().plusDays(1));

			client1.addAccount( account1 );
			client1.addAccount( account2 );

			accountrepository.save(account1);
			accountrepository.save(account2);


			Transaction transaction1 = new Transaction(-500.4, "transferencia", LocalDateTime.now(), TransactionType.DEBIT);
			Transaction transaction2 = new Transaction(300, "deposito", LocalDateTime.now(),TransactionType.CREDIT);

			account1.addTransaction( transaction1 );
			account1.addTransaction( transaction2 );

			transactionrepository.save( transaction1 );
			transactionrepository.save( transaction2 );

			Loan loan1 = new Loan("Mortage", 500000, Set.of(12, 24, 36,48,60));
			Loan loan2 = new Loan("Personal", 100000, Set.of(6, 12, 24));
			Loan loan3 = new Loan("Automotive", 300000, Set.of(6, 12, 24, 36));


			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan( 400000, 60 );
			ClientLoan clientLoan2 = new ClientLoan( 50000, 12 );

			clientLoan1.setLoan(loan1);
			clientLoan2.setLoan(loan2);

			client1.addClientLoan( clientLoan1 );
			client1.addClientLoan( clientLoan2 );

			clientLoanRepository.save( clientLoan1 );
			clientLoanRepository.save( clientLoan2 );

			Card card1 = new Card("1234-1234-1234-1234", "007", CardColor.GOLD, CardType.CREDIT, LocalDate.now());
			Card card2 = new Card("4567-4567-4567-4567","777", CardColor.TITANIUM, CardType.DEBIT, LocalDate.now());

			client1.addCards( card1 );
			client1.addCards( card2 );

			cardRepository.save( card1 );
			cardRepository.save( card2 );

		};
	}
}
