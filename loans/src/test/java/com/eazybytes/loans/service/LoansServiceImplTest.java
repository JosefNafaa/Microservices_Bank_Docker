package com.eazybytes.loans.service;
import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.exception.LoanAlreadyExistsException;
import com.eazybytes.loans.exception.ResourceNotFoundException;
import com.eazybytes.loans.mapper.LoansMapper;
import com.eazybytes.loans.repository.LoansRepository;
import com.eazybytes.loans.service.impl.LoansServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoansServiceImplTest {

    @Mock
    private LoansRepository loansRepository;

    @InjectMocks
    private LoansServiceImpl loansService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createLoan_NewLoan_Success() {
        // Arrange
        String mobileNumber = "1234567890";
        when(loansRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.empty());
        when(loansRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        assertDoesNotThrow(() -> loansService.createLoan(mobileNumber));

        // Assert
        verify(loansRepository, times(1)).save(any());
    }

    @Test
    void createLoan_LoanAlreadyExists_ExceptionThrown() {
        // Arrange
        String mobileNumber = "1234567890";
        when(loansRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.of(new Loans()));

        // Act and Assert
        assertThrows(LoanAlreadyExistsException.class, () -> loansService.createLoan(mobileNumber));
        verify(loansRepository, never()).save(any());
    }

    @Test
    void fetchLoan_ExistingLoan_Success() {
        // Arrange
        String mobileNumber = "1234567890";
        Loans existingLoan = new Loans();
        when(loansRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.of(existingLoan));

        // Act
        LoansDto result = loansService.fetchLoan(mobileNumber);
        LoansDto existingLoanDto=new LoansDto();
        LoansMapper.mapToLoans(existingLoanDto,existingLoan);

        // Assert
        assertNotNull(result);
        assertEquals(existingLoanDto.getMobileNumber(), result.getMobileNumber());
        // Add more assertions based on your implementation
    }

    @Test
    void fetchLoan_NonExistingLoan_ExceptionThrown() {
        // Arrange
        String mobileNumber = "1234567890";
        when(loansRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> loansService.fetchLoan(mobileNumber));
    }

    @Test
    void updateLoan_ExistingLoan_Success() {
        // Arrange
        LoansDto updatedLoanDto = new LoansDto(); // Set fields as needed
        updatedLoanDto.setLoanNumber("ABC123");
        Loans existingLoan = new Loans();
        when(loansRepository.findByLoanNumber(updatedLoanDto.getLoanNumber())).thenReturn(Optional.of(existingLoan));
        when(loansRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        boolean result = loansService.updateLoan(updatedLoanDto);

        // Assert
        assertTrue(result);
        verify(loansRepository, times(1)).save(any());
    }

    @Test
    void updateLoan_NonExistingLoan_ExceptionThrown() {
        // Arrange
        LoansDto nonExistingLoanDto = new LoansDto(); // Set fields as needed
        nonExistingLoanDto.setLoanNumber("NonExistingLoan");

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> loansService.updateLoan(nonExistingLoanDto));
        verify(loansRepository, never()).save(any());
    }

    @Test
    void deleteLoan_ExistingLoan_Success() {
        // Arrange
        String mobileNumber = "1234567890";
        Loans existingLoan = new Loans();
        when(loansRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.of(existingLoan));

        // Act
        boolean result = loansService.deleteLoan(mobileNumber);

        // Assert
        assertTrue(result);
        verify(loansRepository, times(1)).deleteById(existingLoan.getLoanId());
    }

    @Test
    void deleteLoan_NonExistingLoan_ExceptionThrown() {
        // Arrange
        String mobileNumber = "NonExistingMobileNumber";
        when(loansRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> loansService.deleteLoan(mobileNumber));
        verify(loansRepository, never()).deleteById(anyLong());
    }
}
