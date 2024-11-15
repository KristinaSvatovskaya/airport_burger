package com.javaacademy.burger;

import com.javaacademy.burger.dish.Dish;
import com.javaacademy.burger.dish.DishType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@DisplayName("Проверка из санэпидемстанции")
public class SteakhouseSanEpidemTest {
    Waitress waitress;
    Kitchen kitchen;
    PayTerminal payTerminalMock;
    Steakhouse steakhouse;

    @BeforeEach
    void setUp() {
        waitress = new Waitress();
        kitchen = new Kitchen();
        payTerminalMock = Mockito.mock(PayTerminal.class);
        steakhouse = new Steakhouse(waitress, kitchen, payTerminalMock);
    }

    @DisplayName("Проверка санэпидемстанцией на исправность работы заведения")
    @Test
    void sanEpidemInspection() {
        DishType dishType = DishType.RIBS;
        Currency currency = Currency.RUB;

        Paycheck fakePaycheck = new Paycheck(BigDecimal.valueOf(700), currency, dishType);
        when(payTerminalMock.pay(dishType, currency)).thenReturn(fakePaycheck);
        Paycheck paycheck = steakhouse.makeOrder(dishType, currency);

        assertEquals(700, paycheck.getTotalAmount().intValue());
        assertEquals(DishType.RIBS, paycheck.getDishType());
        assertEquals(Currency.RUB, paycheck.getCurrency());

        assertFalse(kitchen.getCompletedDishes().get(DishType.RIBS).isEmpty());

        Dish dish = steakhouse.takeOrder(paycheck);
        assertEquals(DishType.RIBS, dish.getDishType());
    }
}
