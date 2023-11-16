package com.eazybytes.cards.repository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.service.ICardsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CardsRepositoryTest {

    @Mock
    private CardsRepository cardsRepository;

    @Autowired
    private ICardsService cardsService;

    @Test
    void testFindByMobileNumber() {
        // Arrange
        String mobileNumber = "1234567890";
        Cards card = new Cards(); // Create a Cards object with appropriate data
        when(cardsRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.of(card));

        // Act
        Optional<Cards> result = cardsRepository.findByMobileNumber(mobileNumber);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(card, result.get());

        // Verify that the repository method was called
        verify(cardsRepository, times(1)).findByMobileNumber(mobileNumber);
    }

    @Test
    void testFindByCardNumber() {
        // Arrange
        String cardNumber = "1234567890123456";
        Cards card = new Cards(); // Create a Cards object with appropriate data
        when(cardsRepository.findByCardNumber(cardNumber)).thenReturn(Optional.of(card));

        // Act
        Optional<Cards> result = cardsRepository.findByCardNumber(cardNumber);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(card, result.get());

        // Verify that the repository method was called
        verify(cardsRepository, times(1)).findByCardNumber(cardNumber);
    }

    // Add more test cases as needed
}
