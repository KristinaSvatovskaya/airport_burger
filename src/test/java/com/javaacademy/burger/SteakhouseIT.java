package com.javaacademy.burger;

import com.javaacademy.burger.dish.Dish;
import com.javaacademy.burger.dish.DishType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Проверка работы всего заведения владельцем бизнеса")
public class SteakhouseIT {
    private Steakhouse steakhouse;

    @BeforeEach
    void setUp() {
        Waitress waitress = new Waitress();
        Kitchen kitchen = new Kitchen();
        PayTerminal payTerminal = new PayTerminal();
        steakhouse = new Steakhouse(waitress, kitchen, payTerminal);
    }

    @DisplayName("Проверка полного заказа с оплатой и получением блюда")
    @Test
    void makeAndTakeOrder() {
        DishType burger = DishType.BURGER;
        Currency currency = Currency.RUB;
        Paycheck paycheck = steakhouse.makeOrder(burger, currency);

        assertEquals(300, paycheck.getTotalAmount().intValue());
        assertEquals(DishType.BURGER, paycheck.getDishType());
        assertEquals(Currency.RUB, paycheck.getCurrency());

        Dish dish = steakhouse.takeOrder(paycheck);
        assertEquals(DishType.BURGER, dish.getDishType());
    }
}
