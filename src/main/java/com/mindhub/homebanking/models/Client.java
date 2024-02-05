package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName, lastName, mail;
    @OneToMany (mappedBy = "clientOwner", fetch = FetchType.EAGER)
    private Set<Account> accountSet = new HashSet<>();

    public Client() {
    }

    public Client( String firstName, String lastName, String mail ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
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
}
