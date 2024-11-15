package com.javaacademy.burger;

import com.javaacademy.burger.dish.DishType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Проверка из налоговой")
public class SteakhouseTaxInspectionTest {
    Waitress waitressMock;
    Kitchen kitchenMock;
    PayTerminal payTerminalSpy;
    Steakhouse steakhouse;

    @BeforeEach
    void setUp() {
        waitressMock = Mockito.mock(Waitress.class);
        kitchenMock = Mockito.mock(Kitchen.class);
        payTerminalSpy = spy(new PayTerminal());

        setUpPayTerminal(payTerminalSpy, DishType.RIBS, Currency.RUB, 700);
        setUpPayTerminal(payTerminalSpy, DishType.FRIED_POTATO, Currency.USD, 1);
        setUpPayTerminal(payTerminalSpy, DishType.BURGER, Currency.MOZAMBICAN_DOLLARS, 1);

        steakhouse = new Steakhouse(waitressMock, kitchenMock, payTerminalSpy);
        when(waitressMock.giveOrderToKitchen(any(), any())).thenReturn(true);
    }

    @DisplayName("Проверка заказа ребер за рубли")
    @Test
    void taxInspectionRibsInRublesSuccess() {
        checkOrderDetails(DishType.RIBS, Currency.RUB, BigDecimal.valueOf(700));
    }

    @DisplayName("Проверка заказа картошки за доллары")
    @Test
    void taxInspectionPotatoesInDollarsSuccess() {
        checkOrderDetails(DishType.FRIED_POTATO, Currency.USD, BigDecimal.valueOf(1));
    }

    @DisplayName("Проверка заказа бургера за мозамбикские доллары")
    @Test
    void taxInspectionBurgerInMozambicanDollars() {
        checkOrderDetails(DishType.BURGER, Currency.MOZAMBICAN_DOLLARS, BigDecimal.valueOf(1));
    }

    private void setUpPayTerminal(PayTerminal payTerminal, DishType dishType, Currency currency, int amount) {
        doReturn(new Paycheck(BigDecimal.valueOf(amount), currency, dishType))
                .when(payTerminal).pay(dishType, currency);
    }

    private void checkOrderDetails(DishType dishType, Currency currency, BigDecimal expectedAmount){
        Paycheck paycheck = steakhouse.makeOrder(dishType, currency);

        assertEquals(expectedAmount, paycheck.getTotalAmount());
        assertEquals(dishType, paycheck.getDishType());
        assertEquals(currency, paycheck.getCurrency());
    }
}
