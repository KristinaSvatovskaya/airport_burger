package com.javaacademy.burger.util;

import com.javaacademy.burger.Currency;
import com.javaacademy.burger.PayTerminal;
import com.javaacademy.burger.dish.DishType;
import com.javaacademy.burger.exception.NotAcceptedCurrencyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тестирование работы терминала оплаты")
public class PayTerminalTest {
    private PayTerminal payTerminal;

    @BeforeEach
    void setUp() {
        payTerminal = new PayTerminal();
    }

    @DisplayName("Проверка оплаты бургера в рублях")
    @Test
    void paySuccess() {
        DishType burger = DishType.BURGER;
        var paycheck = payTerminal.pay(burger, Currency.RUB);

        assertEquals(300, paycheck.getTotalAmount().intValue());
        assertEquals(Currency.RUB, paycheck.getCurrency());
        assertEquals(burger, paycheck.getDishType());
    }

    @DisplayName("Ошибка при оплате мозамбикскими долларами")
    @Test
    void payFailure() {
        DishType burger = DishType.BURGER;
        assertThrows(NotAcceptedCurrencyException.class, () -> payTerminal.pay(burger, Currency.MOZAMBICAN_DOLLARS));
    }
}
