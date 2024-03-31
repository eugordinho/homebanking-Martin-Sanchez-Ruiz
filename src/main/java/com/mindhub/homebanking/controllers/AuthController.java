package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.LoginDTO;
import com.mindhub.homebanking.dtos.RegisterDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.securityServices.JwtUtilService;
import com.mindhub.homebanking.securityServices.UserDetailsServiceImpl;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.RandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RandomNumber randomNumber;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginDTO loginDTO){

        try {
            Client client = clientService.getClientByEmail(loginDTO.email());

            if(client == null){
                return new ResponseEntity<>("The entered email is not valid", HttpStatus.FORBIDDEN);
            }

            if(!passwordEncoder.matches(loginDTO.password(), client.getPassword())) {
                return new ResponseEntity<>("The password entered is not valid", HttpStatus.FORBIDDEN);
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.email());
            final String jwt = jwtUtilService.generateToken(userDetails);
            return ResponseEntity.ok(jwt);
        }catch (Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody RegisterDTO registerDTO){
        try {
            if(registerDTO.firstName().isBlank()){
                return new ResponseEntity<>("The first name field cannot be empty", HttpStatus.FORBIDDEN);
            }

            if(registerDTO.lastName().isBlank()){
                return new ResponseEntity<>("The last name field cannot be empty", HttpStatus.FORBIDDEN);
            }

            if(registerDTO.email().isBlank()){
                return new ResponseEntity<>("The email field cannot be empty", HttpStatus.FORBIDDEN);
            }

            if(registerDTO.password().isBlank()){
                return new ResponseEntity<>("The password field cannot be empty", HttpStatus.FORBIDDEN);
            }

            if(clientService.getClientByEmail(registerDTO.email()) != null){
                return new ResponseEntity<>("The email entered already exists in the database", HttpStatus.FORBIDDEN);
            }

            if(registerDTO.password().length() < 8){
                return new ResponseEntity<>("Password must be at least 8 characters", HttpStatus.FORBIDDEN);
            }

            if(registerDTO.password().contains("@")){
                return new ResponseEntity<>("The email entered is not valid", HttpStatus.FORBIDDEN);
            }

            Client client = new Client(
                    registerDTO.firstName(),
                    registerDTO.lastName(),
                    registerDTO.email(),
                    passwordEncoder.encode(registerDTO.password()));

            String accountNumber;
            do {
                accountNumber = "VIN" + RandomNumber.getRandomNumber(0, 100000000);
            } while (accountService.getAccountByNumber(accountNumber) != null);

            Account account = new Account(accountNumber, 0, LocalDate.now());

            client.addAccount(account);
            clientService.saveClient(client);
            accountService.saveAccount(account);

            return new ResponseEntity<> ("Client created", HttpStatus.CREATED);

        } catch (Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok("Hello " + mail);

    }

/*    @GetMapping("/current")
    public ResponseEntity<?> getClient(){
        String userMail = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.getClientByEmail(userMail);
        return ResponseEntity.ok(new ClientDTO(client));
    }*/
}