package com.eazybytes.accounts.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.impl.AccountsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class AccountsServiceImplTest {

    private AccountsServiceImpl accountsService;

    @Mock
    private AccountsRepository accountsRepository;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        accountsService = new AccountsServiceImpl(accountsRepository, customerRepository);
    }

    @Test
     void testCreateAccount() {
        // Créez un CustomerDto factice pour le test
        CustomerDto customerDto = new CustomerDto();
        customerDto.setMobileNumber("1234567890");

        // Créez un Customer factice pour le test
        Customer customer = new Customer();
        customer.setCustomerId(1L);

        // Configurez le comportement du repository mock
        when(customerRepository.findByMobileNumber(customerDto.getMobileNumber())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Exécutez la méthode à tester
        accountsService.createAccount(customerDto);

        // Vérifiez que le repository a été appelé pour sauvegarder le Customer
        verify(customerRepository, times(1)).save(any(Customer.class));

        // Vérifiez que le repository a été appelé pour sauvegarder le Account
        verify(accountsRepository, times(1)).save(any(Accounts.class));
    }

    @Test
     void testCreateAccountCustomerAlreadyExists() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setMobileNumber("1234567890");

        Customer existingCustomer = new Customer();

        when(customerRepository.findByMobileNumber(customerDto.getMobileNumber())).thenReturn(Optional.of(existingCustomer));

        assertThrows(CustomerAlreadyExistsException.class, () -> {
            accountsService.createAccount(customerDto);
        });

        verify(customerRepository, never()).save(any(Customer.class));
        verify(accountsRepository, never()).save(any(Accounts.class));
    }

    @Test
     void testFetchAccount() {
        // Créer un Customer fictif et un Accounts fictif
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setMobileNumber("1234567890");

        Accounts accounts = new Accounts();
        accounts.setCustomerId(1L);

        // Configurer les comportements attendus des mocks
        when(customerRepository.findByMobileNumber("1234567890")).thenReturn(Optional.of(customer));
        when(accountsRepository.findByCustomerId(1L)).thenReturn(Optional.of(accounts));

        // Appeler la méthode sous test
        CustomerDto customerDto = accountsService.fetchAccount("1234567890");

        // Vérifier que les méthodes des mocks ont été appelées avec les bons arguments
        verify(customerRepository).findByMobileNumber("1234567890");
        verify(accountsRepository).findByCustomerId(1L);

        // Vérifier que les valeurs renvoyées correspondent aux attentes
        assertEquals("1234567890", customerDto.getMobileNumber());
    }
  /*  @Test
    public void testUpdateAccount() {
        // Create a CustomerDto object for the test
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("John Doe");
        customerDto.setEmail("john.doe@example.com");
        customerDto.setMobileNumber("1234567890");

        // Create an AccountsDto object for the test
        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(1L);
        accountsDto.setAccountType("Savings");
        accountsDto.setBranchAddress("123 Main Street");
        customerDto.setAccountsDto(accountsDto);

        // Mock the behavior of your repository for findById
        Accounts mockAccounts = new Accounts();
        mockAccounts.setCustomerId(125L); // Set a dummy value for customerId
        when(accountsRepository.findById(accountsDto.getAccountNumber())).thenReturn(Optional.of(mockAccounts));

        // Execute the method you are testing
        boolean isUpdated = accountsService.updateAccount(customerDto);

        // Verify that the method returned true (update successful)
        assertTrue(isUpdated);

        // You can also perform other checks if needed to ensure
        // that the update operations were performed correctly.
    }
*/

}
