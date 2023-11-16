package com.eazybytes.cards.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.exception.CardAlreadyExistsException;

import com.eazybytes.cards.repository.CardsRepository;
import com.eazybytes.cards.service.impl.CardsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CardsServiceImplTest {

    @Mock
    private CardsRepository cardsRepository;

    @InjectMocks
    private CardsServiceImpl cardsService;

    @BeforeEach
    void setUp() {
        // You can initialize or set up things before each test if needed
    }

    @Test
    void testCreateCard() {
        // Arrange
        String mobileNumber = "1234567890";
        when(cardsRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.empty());

        // Act
        assertDoesNotThrow(() -> cardsService.createCard(mobileNumber));

        // Verify that the repository method was called
        verify(cardsRepository, times(1)).save(any());
    }

    @Test
    void testCreateCardWhenCardAlreadyExists() {
        // Arrange
        String mobileNumber = "1234567890";
        when(cardsRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.of(new Cards()));

        // Act
        assertThrows(CardAlreadyExistsException.class, () -> cardsService.createCard(mobileNumber));

        // Verify that the repository method was not called
        verify(cardsRepository, never()).save(any());
    }

    @Test
    void testFetchCard() {
        // Arrange
        String mobileNumber = "1234567890";
        Cards card = new Cards();
        when(cardsRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.of(card));

        // Act
        CardsDto result = cardsService.fetchCard(mobileNumber);

        // Assert
        assertNotNull(result);
        // Add more assertions based on the expected behavior

        // Verify that the repository method was called
        verify(cardsRepository, times(1)).findByMobileNumber(mobileNumber);
    }

    // Add more test cases for updateCard and deleteCard methods

}
