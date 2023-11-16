package com.eazybytes.accounts.repository;

import com.eazybytes.accounts.entity.Accounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class AccountsRepositoryTest {

    @Autowired
    private AccountsRepository accountsRepository;

    @BeforeEach
    public void setUp() {
        // Ajoutez ici du code pour initialiser la base de données avec des données de test si nécessaire.
    }

   /* @Test
    public void testFindByCustomerId() {
        // Créez un objet Account pour la recherche
        Accounts account = new Accounts();
        account.setCustomerId(1L); // Remplacez cette valeur par l'ID de votre client réel
        account.setAccountNumber(1234L);
        account.setAccountType("Primary account");
        account.setBranchAddress("Casablanca Morocco");
        account.setCreatedBy("Youssef");
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedBy("Youssef");
        account.setUpdatedAt(LocalDateTime.now());
        accountsRepository.save(account);

        // Recherchez le compte par l'ID du client
        Optional<Accounts> foundAccount = accountsRepository.findByCustomerId(1L); // Remplacez par l'ID du client réel

        // Vérifiez si le compte a été trouvé
        assertTrue(foundAccount.isPresent());

        // Vérifiez si le compte trouvé a le bon ID de client
        assertEquals(1L, foundAccount.get().getCustomerId()); // Remplacez par l'ID du client réel
    }

    @Test
    public void testDeleteByCustomerId() {
        // Créez un objet Account à supprimer
        Accounts account = new Accounts();
        account.setCustomerId(2L); // Remplacez cette valeur par l'ID de votre client réel
        account.setAccountNumber(1234L);
        account.setAccountType("Secondary account");
        account.setBranchAddress("Casablanca Morocco");
        account.setCreatedBy("Youssef");
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedBy("Youssef");
        account.setUpdatedAt(LocalDateTime.now());
        accountsRepository.save(account);

        // Supprimez le compte par l'ID du client
        accountsRepository.deleteByCustomerId(2L); // Remplacez par l'ID du client réel

        // Vérifiez si le compte a été correctement supprimé
        Optional<Accounts> deletedAccount = accountsRepository.findByCustomerId(2L); // Remplacez par l'ID du client réel
        assertTrue(deletedAccount.isEmpty());
    }*/

    // Vous pouvez ajouter d'autres tests ici selon vos besoins.

}
