package com.eazybytes.accounts.repository;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        // Ajoutez ici du code pour initialiser la base de données avec des données de test si nécessaire.
    }

  /*  @Test
    public void testFindByMobileNumber() {
        // Créez un objet Customer pour la recherche
        Customer customer = new Customer();
        customer.setName("Hassan");
        customer.setEmail("hassan@gmail.com");
        customer.setMobileNumber("1234567890");
        customer.setCreatedBy("Youssef");
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedBy("Youssef");
        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.save(customer);

        // Recherchez le customer par le numéro de mobile
        Optional<Customer> foundCustomer = customerRepository.findByMobileNumber("1234567890");

        // Vérifiez si le customer a été trouvé
        assertTrue(foundCustomer.isPresent());

        // Vérifiez si le customer trouvé a le bon numéro de mobile
        assertEquals("1234567890", foundCustomer.get().getMobileNumber());
    }
*/
    // Vous pouvez ajouter d'autres tests ici selon vos besoins.
}
