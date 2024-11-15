package com.javaacademy.burger.util;

import com.javaacademy.burger.Kitchen;
import com.javaacademy.burger.Waitress;
import com.javaacademy.burger.dish.DishType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование официанта")
public class WaitressTest {
    private Kitchen kitchenMock;
    private Waitress waitress;

    @BeforeEach
    void setUp(){
        kitchenMock = Mockito.mock(Kitchen.class);
        waitress = new Waitress();
    }

    @DisplayName("Официант принимает заказ на бургер")
    @Test
    void giveOrderToKitchenSuccess() {
        boolean result = waitress.giveOrderToKitchen(DishType.BURGER, kitchenMock);
        assertTrue(result);
    }

    @DisplayName("Официант не принимает заказ на фуагра")
    @Test
    void giveOrderToKitchenFailure() {
        boolean result = waitress.giveOrderToKitchen(DishType.FUAGRA, kitchenMock);
        assertFalse(result);
    }
}
