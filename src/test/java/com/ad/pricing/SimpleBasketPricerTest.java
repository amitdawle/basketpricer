package com.ad.pricing;

import com.ad.Basket;
import com.ad.Item;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static com.ad.Item.*;
import static com.ad.promotions.Promotions.buyOneGetOneFree;
import static com.ad.promotions.Promotions.buyOneGetOneHalfPrice;
import static com.ad.promotions.Promotions.buyTwoGetOneFree;
import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class SimpleBasketPricerTest {


    @Test(expected = NullPointerException.class)
    public void priceNullBasket() {
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(Collections.<Item>emptyList());

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result = pricer.price(null);

        fail("Should not come here. Null element must throw exception");
    }

    @Test
    public void priceEmptyBasket() {
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(Collections.<Item>emptyList());

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result = pricer.price(b);

        assertThat(result, is(BigDecimal.ZERO));
    }

    @Test
    public void basketPricedCorrectlyWithOneItem() {
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Apple));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result = pricer.price(b);

        assertThat(result, is(BigDecimal.valueOf(0.60)));
    }

    @Test
    public void basketPricedCorrectlyWithSomeItems() {
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Apple, Apple, Banana, Apple));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result = pricer.price(b);

        assertThat(result, is(Apple.getPrice().multiply(BigDecimal.valueOf(3)).add(Banana.getPrice())));
    }

    @Test
    public void basketPricedCorrectlyWithSomeItemsAndBuyTwoGetOneFreeDiscount() {
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Apple, Apple, Banana, Apple));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result = pricer.price(b, buyTwoGetOneFree(Apple));

        assertThat(result, is(Apple.getPrice().multiply(BigDecimal.valueOf(2)).add(Banana.getPrice())));
    }


    @Test
    public void basketPricedCorrectlyWithSomeItemsAndBuyOneGetOneFreeDiscount() {
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Apple, Apple, Apple, Apple));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result = pricer.price(b, buyOneGetOneFree(Apple));

        assertThat(result, is(Apple.getPrice().multiply(BigDecimal.valueOf(2))));
    }

    @Test
    public void basketPricedCorrectlyWithSomeItemsAndBuyOneGetOneFreeDiscountOnMultipleItems() {
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList(Banana, Apple, Apple, Apple, Apple, Banana));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result = pricer.price(b, buyOneGetOneFree(Apple), buyOneGetOneFree(Banana));

        assertThat(result, is(Apple.getPrice().multiply(BigDecimal.valueOf(2)).add(Banana.getPrice())));
    }


    @Test
    public void basketPricedCorrectlyWithSomeItemsAndBuyTwoGetOneFreeDiscountOnMultipleItems() {
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList(Banana, Apple, Apple, Apple, Apple, Banana, Banana));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result = pricer.price(b, buyTwoGetOneFree(Apple), buyTwoGetOneFree(Banana));

        BigDecimal toPay = BigDecimal.ZERO;
        // 3 for 2 Apple
        toPay = toPay.add(Apple.getPrice());
        toPay = toPay.add(Apple.getPrice());
        // No offer Apple
        toPay = toPay.add(Apple.getPrice());
        // 3 for 2 Banana
        toPay = toPay.add(Banana.getPrice());
        toPay = toPay.add(Banana.getPrice());

        assertThat(result, is(toPay));
    }


    @Test
    public void orderOfDiscountsDoesNotAffectFinalReduction() {
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList(Banana, Apple, Apple, Apple, Apple, Banana, Banana));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result1 = pricer.price(b, buyTwoGetOneFree(Apple), buyTwoGetOneFree(Banana));

        BigDecimal result2 = pricer.price(b, buyTwoGetOneFree(Banana), buyTwoGetOneFree(Apple));

        assertEquals(result1, result2);
    }

    @Test
    public void priceCalculatedCorrectlyDifferentPromotionsOnDifferentItems() {
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList(Banana, Apple, Apple, Apple, Banana));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result1 = pricer.price(b, buyTwoGetOneFree(Apple), buyTwoGetOneFree(Banana));

        BigDecimal result2 = pricer.price(b, buyTwoGetOneFree(Banana), buyTwoGetOneFree(Apple));

        assertEquals(result1, result2);
    }


    @Test
    public void priceCalculatedCorrectlyWhenNoEligibleItemsForDiscount() {
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList(Apple, Apple));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result1 = pricer.price(b, buyOneGetOneFree(Banana));

        assertThat(result1, is(Apple.getPrice().add(Apple.getPrice())));
    }

    @Test
    public void priceCalculatedCorrectlyWithBuyOneGetOneHalfPriceDiscount() {
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList(Banana, Banana));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result = pricer.price(b, buyOneGetOneHalfPrice(Banana));

        assertThat(result, is(Banana.getPrice().multiply(BigDecimal.valueOf(1.5))));
    }

    @Test
    public void priceCalculatedCorrectlyForBOGOFAndBuyOneGetOneHalfPrice() {
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList(Banana, Banana, Apple, Apple));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result = pricer.price(b, buyOneGetOneFree(Apple), buyOneGetOneHalfPrice(Banana));

        BigDecimal expected = Banana.getPrice().multiply(BigDecimal.valueOf(1.5));
        expected = expected.add(Apple.getPrice());
        assertThat(result, is(expected));
    }


}
