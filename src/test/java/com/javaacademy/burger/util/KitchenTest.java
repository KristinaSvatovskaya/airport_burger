package com.javaacademy.burger.util;

import com.javaacademy.burger.Kitchen;
import com.javaacademy.burger.dish.DishType;
import com.javaacademy.burger.exception.KitchenHasNoGasException;
import com.javaacademy.burger.exception.UnsupportedDishTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тестирование кухни")
public class KitchenTest {
    private Kitchen kitchen;

    @BeforeEach
    void setUp(){
        kitchen = new Kitchen();
    }

    @DisplayName("Успешно приготовлен бургер")
    @Test
    void cookBurgerSuccess(){
        kitchen.cook(DishType.BURGER);
        assertEquals(DishType.BURGER, kitchen.getCompletedDishes().get(DishType.BURGER).peek().getDishType());
    }

    @DisplayName("Бургер не приготовлен, нет газа")
    @Test
    void cookBurgerFailure(){
        kitchen.cook(DishType.BURGER);
        kitchen.setHasGas(false);
        assertThrows(KitchenHasNoGasException.class, () -> kitchen.cook(DishType.BURGER));
    }

    @DisplayName("Невозможно приготовить рыбу фуагра")
    @Test
    void cookFuaGraFailure(){
        assertThrows(UnsupportedDishTypeException.class, () -> kitchen.cook(DishType.FUAGRA));
    }
}
