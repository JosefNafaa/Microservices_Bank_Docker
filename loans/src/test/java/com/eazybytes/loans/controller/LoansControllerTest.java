package com.eazybytes.loans.controller;
import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.dto.ResponseDto;
import com.eazybytes.loans.service.ILoansService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoansControllerTest {

    @Mock
    private ILoansService loansService;

    @InjectMocks
    private LoansController loansController;

    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    void testCreateLoan() {
        // Arrange
        String mobileNumber = "1234567890";

        // Act
        ResponseEntity<ResponseDto> responseEntity = loansController.createLoan(mobileNumber);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(LoansConstants.STATUS_201, responseEntity.getBody().getStatusCode());
        assertEquals(LoansConstants.MESSAGE_201, responseEntity.getBody().getStatusMsg());

        // Verify
        verify(loansService, times(1)).createLoan(mobileNumber);
    }

    @Test
    void testFetchLoanDetails() {
        // Arrange
        String mobileNumber = "1234567890";
        LoansDto loansDto = new LoansDto();
        when(loansService.fetchLoan(mobileNumber)).thenReturn(loansDto);

        // Act
        ResponseEntity<LoansDto> responseEntity = loansController.fetchLoanDetails(mobileNumber);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        // Verify
        verify(loansService, times(1)).fetchLoan(mobileNumber);
    }

    @Test
    void testUpdateLoanDetails() {
        // Arrange
        LoansDto loansDto = new LoansDto();
        when(loansService.updateLoan(any())).thenReturn(true);

        // Act
        ResponseEntity<ResponseDto> responseEntity = loansController.updateLoanDetails(loansDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(LoansConstants.STATUS_200, responseEntity.getBody().getStatusCode());
        assertEquals(LoansConstants.MESSAGE_200, responseEntity.getBody().getStatusMsg());

        // Verify
        verify(loansService, times(1)).updateLoan(loansDto);
    }

    @Test
    void testDeleteLoanDetails() {
        // Arrange
        String mobileNumber = "1234567890";
        when(loansService.deleteLoan(mobileNumber)).thenReturn(true);

        // Act
        ResponseEntity<ResponseDto> responseEntity = loansController.deleteLoanDetails(mobileNumber);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(LoansConstants.STATUS_200, responseEntity.getBody().getStatusCode());
        assertEquals(LoansConstants.MESSAGE_200, responseEntity.getBody().getStatusMsg());

        // Verify
        verify(loansService, times(1)).deleteLoan(mobileNumber);
    }
}
