package com.eazybytes.loans.repository;

import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.service.ILoansService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class LoansRepositoryTests {
    @Mock
    private LoansRepository loanRepository;  // Assuming LoanRepository is a dependency of LoanService

    @Autowired
    private ILoansService loanService;  // The class under test

    @Test
    public void testGetLoanByMobileNumber() {
        // Arrange
        String mobileNumber = "1234567890";
        Loans expectedLoan = new Loans();
        // Assuming Loan is your model class

        // Mock the behavior of the loanRepository when getLoanByMobileNumber is called
        when(loanRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.of(expectedLoan));

        // Act
        Optional<Loans> actualLoan = loanRepository.findByMobileNumber(mobileNumber);
        // Extract the Loans object from the Optional and then compare
        Loans actualLoans = actualLoan.orElse(null);
        assertEquals(expectedLoan, actualLoans);
    }
    @Test
    public void testFindByLoanNumber() {
        // Given
        String loanNumber = "ABC123";
        Loans expectedLoan = new Loans();
        // Assuming Loan is your model class

        // Mock the behavior of the loansRepository when findByLoanNumber is called
        when(loanRepository.findByLoanNumber(loanNumber)).thenReturn(Optional.of(expectedLoan));

        // Act
        Optional<Loans> actualLoan = loanRepository.findByLoanNumber(loanNumber);

        // Assert
        assertEquals(expectedLoan, actualLoan.orElse(null));
    }
}
