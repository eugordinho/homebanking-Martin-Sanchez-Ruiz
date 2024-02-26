package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName, lastName, mail, password;
    @OneToMany ( mappedBy = "clientOwner", fetch = FetchType.EAGER )
    private Set<Account> accountSet = new HashSet<>();
    @OneToMany( mappedBy = "client")
    private Set<ClientLoan> clientloans = new HashSet<>();
    @OneToMany( mappedBy = "cardHolder", fetch = FetchType.EAGER )
    private Set<Card> cards = new HashSet<>();

    public Client(  ) {
    }

    public Client( String firstName, String lastName, String mail, String password ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Long getId() {
        return id;
    }

    public Set<Account> getAccountSet(){
        return accountSet;
    }

    public void addAccount(Account account ){
        account.setClient( this );
        accountSet.add( account );
    }

    public Set<ClientLoan> getClientloans() {
        return clientloans;
    }

    public void setClientloans(Set<ClientLoan> clientloans) {
        this.clientloans = clientloans;
    }

    public void addClientLoan( ClientLoan clientloan ) {
        clientloan.setClient( this );
        clientloans.add( clientloan );
    }

    public Set<Loan> getClientloans( ClientLoan clientloan ){
        return clientloans.stream().map( loan -> loan.getLoan()).collect( Collectors.toSet());
    }

    public void addCards( Card card ){
        card.setCardHolder( this );
        cards.add( card );
    }
    public Set<Card> getCards ( ) {
        return cards;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
