package com.eazybytes.accounts.controller;
import com.eazybytes.accounts.constantes.AccountsConstants;
import com.eazybytes.accounts.constantes.controller.AccountsController;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.service.IAccountsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private AccountsController accountsController;

    @Mock
    private IAccountsService accountsService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        accountsController = new AccountsController(accountsService);
        mockMvc = MockMvcBuilders.standaloneSetup(accountsController).build();

    }

    @Test
    public void testCreateAccountSuccess() {
        // Créez un CustomerDto factice pour le test
        CustomerDto customerDto = new CustomerDto();

        // Configurez le comportement du service mock
        doNothing().when(accountsService).createAccount(customerDto);

        // Appelez la méthode du contrôleur
        ResponseEntity<ResponseDto> response = accountsController.createAccount(customerDto);

        // Vérifiez la réponse HTTP 201
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(AccountsConstants.STATUS_201, response.getBody().getStatusCode());
        assertEquals(AccountsConstants.MESSAGE_201, response.getBody().getStatusMsg());

        // Vérifiez que la méthode du service a été appelée
        verify(accountsService, times(1)).createAccount(customerDto);
    }

    @Test
    public void testCreateAccountCustomerAlreadyExists() {
        CustomerDto customerDto = new CustomerDto();

        // Configurez le comportement du service mock pour lancer une exception
        doThrow(CustomerAlreadyExistsException.class).when(accountsService).createAccount(customerDto);

        // Appelez la méthode du contrôleur et vérifiez que l'exception est bien lancée
        try {
            accountsController.createAccount(customerDto);
        } catch (CustomerAlreadyExistsException e) {
            // L'exception est attendue
        }

        // Vérifiez que la méthode du service a été appelée
        verify(accountsService, times(1)).createAccount(customerDto);
    }
    @Test
    public void testFetchAccountDetails() throws Exception {
        // Create a CustomerDto
        CustomerDto customerDto = new CustomerDto();
        customerDto.setMobileNumber("1234567890");

        // Mock the behavior of the service to return the CustomerDto
        when(accountsService.fetchAccount("1234567890")).thenReturn(customerDto);

        // Perform a simulated GET request with a valid mobileNumber
        mockMvc.perform(MockMvcRequestBuilders.get("/api/fetch")
                        .param("mobileNumber", "1234567890")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobileNumber").value("1234567890"));
    }


    /*@Test
    public void testFetchAccountDetailsWithInvalidMobileNumber() throws Exception {
        // Arrange
        String invalidMobileNumber = "12345"; // An invalid 5-digit mobile number

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/fetch")
                        .param("mobileNumber", invalidMobileNumber)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value())) // Expect a 400 Bad Request status
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorTime").exists()) // Check for the presence of the errorTime field
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("BAD_REQUEST")) // Check the error code
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value("Mobile number must be 10 digits")); // Check the error message
    }*/
    @Test
    public void testUpdateAccountDetailsSuccess() {
        // Arrange
        CustomerDto customerDto = new CustomerDto(); // Initialize with test data
        Mockito.when(accountsService.updateAccount(customerDto)).thenReturn(true);

        // Act
        ResponseEntity<ResponseDto> response = accountsController.updateAccountDetails(customerDto);

        // Assert
        assert(response.getStatusCode()).equals(HttpStatus.OK);
        assert(response.getBody().getStatusCode()).equals(AccountsConstants.STATUS_200);
        assert(response.getBody().getStatusMsg()).equals(AccountsConstants.MESSAGE_200);
    }

    @Test
    public void testUpdateAccountDetailsFailure() {
        // Arrange
        CustomerDto customerDto = new CustomerDto(); // Initialize with test data
        Mockito.when(accountsService.updateAccount(customerDto)).thenReturn(false);

        // Act
        ResponseEntity<ResponseDto> response = accountsController.updateAccountDetails(customerDto);

        // Assert
        assert(response.getStatusCode()).equals(HttpStatus.EXPECTATION_FAILED);
        assert(response.getBody().getStatusCode()).equals(AccountsConstants.STATUS_417);
        assert(response.getBody().getStatusMsg()).equals(AccountsConstants.MESSAGE_417_UPDATE);
    }
    @Test
    public void testDeleteAccountDetails_Success() {
        // Arrange
        String mobileNumber = "1234567890"; // Provide a valid mobile number
        Mockito.when(accountsService.deleteAccount(mobileNumber)).thenReturn(true);

        // Act
        ResponseEntity<ResponseDto> response = accountsController.deleteAccountDetails(mobileNumber);

        // Assert
        assert(response.getStatusCode()).equals(HttpStatus.OK);
        assert(response.getBody().getStatusCode()).equals(AccountsConstants.STATUS_200);
        assert(response.getBody().getStatusMsg()).equals(AccountsConstants.MESSAGE_200);
    }

    @Test
    public void testDeleteAccountDetails_Failure() {
        // Arrange
        String mobileNumber = "1234567890"; // Provide a valid mobile number
        Mockito.when(accountsService.deleteAccount(mobileNumber)).thenReturn(false);

        // Act
        ResponseEntity<ResponseDto> response = accountsController.deleteAccountDetails(mobileNumber);

        // Assert
        assert(response.getStatusCode()).equals(HttpStatus.EXPECTATION_FAILED);
        assert(response.getBody().getStatusCode()).equals(AccountsConstants.STATUS_417);
        assert(response.getBody().getStatusMsg()).equals(AccountsConstants.MESSAGE_417_DELETE);
    }

    /*@Test
    public void testDeleteAccountDetails_InvalidMobileNumber() {
        // Arrange
        String mobileNumber = "1234"; // An invalid mobile number (not 10 digits)

        // Act
        ResponseEntity<ResponseDto> response = accountsController.deleteAccountDetails(mobileNumber);

        // Assert
        assert(response.getStatusCode()).equals(HttpStatus.BAD_REQUEST); // Assuming you handle this case with 400 Bad Request
        // You may also check the response body for an error message.
    }*/
}
