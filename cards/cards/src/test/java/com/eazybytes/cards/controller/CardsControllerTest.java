package com.eazybytes.cards.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.dto.ResponseDto;

import com.eazybytes.cards.service.ICardsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class CardsControllerTest {

    @Mock
    private ICardsService cardsService;

    @InjectMocks
    private CardsController cardsController;

    @BeforeEach
    void setUp() {
        // You can initialize or set up things before each test if needed
    }

    @Test
    void testCreateCard() {
        // Arrange
        String mobileNumber = "1234567890";
        doNothing().when(cardsService).createCard(mobileNumber);

        // Act
        ResponseEntity<ResponseDto> responseEntity = cardsController.createCard(mobileNumber);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(CardsConstants.STATUS_201, responseEntity.getBody().getStatusCode());
        assertEquals(CardsConstants.MESSAGE_201, responseEntity.getBody().getStatusMsg());

        // Verify that the service method was called
        verify(cardsService, times(1)).createCard(mobileNumber);
    }

    @Test
    void testFetchCardDetails() {
        // Arrange
        String mobileNumber = "1234567890";
        CardsDto cardsDto = new CardsDto(); // Assume a CardsDto object is returned by the service
        when(cardsService.fetchCard(mobileNumber)).thenReturn(cardsDto);

        // Act
        ResponseEntity<CardsDto> responseEntity = cardsController.fetchCardDetails(mobileNumber);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        // Verify that the service method was called
        verify(cardsService, times(1)).fetchCard(mobileNumber);
    }

    @Test
    void testUpdateCardDetails() {
        // Arrange
        CardsDto cardsDto = new CardsDto(); // Assume a CardsDto object is provided in the request
        when(cardsService.updateCard(cardsDto)).thenReturn(true);

        // Act
        ResponseEntity<ResponseDto> responseEntity = cardsController.updateCardDetails(cardsDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(CardsConstants.STATUS_200, responseEntity.getBody().getStatusCode());
        assertEquals(CardsConstants.MESSAGE_200, responseEntity.getBody().getStatusMsg());

        // Verify that the service method was called
        verify(cardsService, times(1)).updateCard(cardsDto);
    }

    @Test
    void testDeleteCardDetails() {
        // Arrange
        String mobileNumber = "1234567890";
        when(cardsService.deleteCard(mobileNumber)).thenReturn(true);

        // Act
        ResponseEntity<ResponseDto> responseEntity = cardsController.deleteCardDetails(mobileNumber);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(CardsConstants.STATUS_200, responseEntity.getBody().getStatusCode());
        assertEquals(CardsConstants.MESSAGE_200, responseEntity.getBody().getStatusMsg());

        // Verify that the service method was called
        verify(cardsService, times(1)).deleteCard(mobileNumber);
    }

    // Add more test cases as needed

}
