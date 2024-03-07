package com.mindhub.homebanking.services.implService;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    @Override
    public List<Loan> getAllLoans() {
        return null;
    }

    @Override
    public List<LoanDTO> getAllLoansDTO() {
        return null;
    }

    @Override
    public Loan getLoanById(Long id) {
        return null;
    }

    @Override
    public void saveLoan(Loan loan) {

    }
}
