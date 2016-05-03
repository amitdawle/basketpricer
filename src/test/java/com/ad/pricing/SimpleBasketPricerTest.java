package com.ad.pricing;

import com.ad.Basket;
import com.ad.Item;
import com.ad.pricing.SimpleBasketPricer;
import com.ad.promotions.BuyOneGetOneFreePromotion;
import com.ad.promotions.BuyOneGetOneHalfPricePromotion;
import com.ad.promotions.BuyTwoGetOneFreePromotion;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

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
    public void priceNullBasket(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(Collections.<Item>emptyList());

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(null);

        fail("Should not come here. Null element must throw exception");
    }

    @Test
    public void priceEmptyBasket(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(Collections.<Item>emptyList());

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b);

        assertThat(BigDecimal.ZERO, is(result));
     }

    @Test
    public void basketPricedCorrectlyWithOneItem(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b);

        assertThat(BigDecimal.valueOf(0.60), is(result));
    }

    @Test
    public void basketPricedCorrectlyWithSomeItems(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple,Item.Apple,Item.Banana, Item.Apple));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b);

        assertThat(Item.Apple.getPrice().multiply(BigDecimal.valueOf(3)).add(Item.Banana.getPrice()), is(result));
    }

    @Test
    public void basketPricedCorrectlyWithSomeItemsAndBuyTwoGetOneFreeDiscount(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple,Item.Apple,Item.Banana, Item.Apple));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b, new BuyTwoGetOneFreePromotion(Item.Apple, BigDecimal.valueOf(0.6)));

        assertThat(Item.Apple.getPrice().multiply(BigDecimal.valueOf(2)).add(Item.Banana.getPrice()), is(result));
    }


    @Test
    public void basketPricedCorrectlyWithSomeItemsAndBuyOneGetOneFreeDiscount(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple,Item.Apple,Item.Apple, Item.Apple));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b, new BuyOneGetOneFreePromotion(Item.Apple, BigDecimal.valueOf(0.6)));

        assertThat(Item.Apple.getPrice().multiply(BigDecimal.valueOf(2)), is(result));
    }

    @Test
    public void basketPricedCorrectlyWithSomeItemsAndBuyOneGetOneFreeDiscountOnMultipleItems(){
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList(Item.Banana,Item.Apple,Item.Apple,Item.Apple, Item.Apple, Item.Banana));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b, new BuyOneGetOneFreePromotion(Item.Apple, BigDecimal.valueOf(0.6)), new BuyOneGetOneFreePromotion(Item.Banana, BigDecimal.valueOf(0.3)));

        assertThat(Item.Apple.getPrice().multiply(BigDecimal.valueOf(2)).add(Item.Banana.getPrice()), is(result));
    }



    @Test
    public void basketPricedCorrectlyWithSomeItemsAndBuyTwoGetOneFreeDiscountOnMultipleItems(){
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList(Item.Banana,Item.Apple,Item.Apple,Item.Apple, Item.Apple, Item.Banana, Item.Banana));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b, new BuyTwoGetOneFreePromotion(Item.Apple, BigDecimal.valueOf(0.6)), new BuyTwoGetOneFreePromotion(Item.Banana, BigDecimal.valueOf(0.3)));

        BigDecimal toPay = BigDecimal.ZERO;
        // 3 for 2 Apple
        toPay = toPay.add(Item.Apple.getPrice());
        toPay = toPay.add(Item.Apple.getPrice());
        // No offer Apple
        toPay = toPay.add(Item.Apple.getPrice());
        // 3 for 2 Banana
        toPay = toPay.add(Item.Banana.getPrice());
        toPay = toPay.add(Item.Banana.getPrice());

        assertThat(toPay, is(result));
    }


    @Test
    public void orderOfDiscountsDoesNotAffectFinalReduction(){
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList(Item.Banana, Item.Apple, Item.Apple, Item.Apple, Item.Apple, Item.Banana, Item.Banana));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result1  = pricer.price(b, new BuyTwoGetOneFreePromotion(Item.Apple, BigDecimal.valueOf(0.6)), new BuyTwoGetOneFreePromotion(Item.Banana, BigDecimal.valueOf(0.6)));

        BigDecimal result2  = pricer.price(b, new BuyTwoGetOneFreePromotion(Item.Banana, BigDecimal.valueOf(0.6)), new BuyTwoGetOneFreePromotion(Item.Apple, BigDecimal.valueOf(0.6)) );

        assertEquals( result1, result2);
    }

    @Test
    public void priceCalculatedCorrectlyDifferentPromotionsOnDifferentItems(){
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList(Item.Banana, Item.Apple, Item.Apple, Item.Apple, Item.Banana ));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result1  = pricer.price(b, new BuyTwoGetOneFreePromotion(Item.Apple, BigDecimal.valueOf(0.6)), new BuyTwoGetOneFreePromotion(Item.Banana, BigDecimal.valueOf(0.6)));

        BigDecimal result2  = pricer.price(b, new BuyTwoGetOneFreePromotion(Item.Banana, BigDecimal.valueOf(0.6)),new BuyTwoGetOneFreePromotion(Item.Apple, BigDecimal.valueOf(0.6)) );

        assertEquals( result1, result2);
    }


    @Test
    public void priceCalculatedCorrectlyWhenNoEligibleItemsForDiscount(){
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList( Item.Apple, Item.Apple));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result1  = pricer.price(b, new BuyOneGetOneFreePromotion(Item.Banana, BigDecimal.valueOf(0.3)));

        assertThat( Item.Apple.getPrice().add(Item.Apple.getPrice()), is(result1));
    }

    @Test
    public void priceCalculatedCorrectlyWithBuyOneGetOneHalfPriceDiscount(){
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList(Item.Banana, Item.Banana));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b, new BuyOneGetOneHalfPricePromotion(Item.Banana, BigDecimal.valueOf(0.15)));

        assertThat( Item.Banana.getPrice().multiply(BigDecimal.valueOf(1.5)), is(result));
    }

    @Test
    public void priceCalculatedCorrectlyForBOGOFAndBuyOneGetOneHalfPrice(){
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList(Item.Banana, Item.Banana, Item.Apple, Item.Apple));

        BasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b, new BuyOneGetOneFreePromotion(Item.Apple, BigDecimal.valueOf(0.6)) , new BuyOneGetOneHalfPricePromotion(Item.Banana, BigDecimal.valueOf(0.15)));

        BigDecimal expected = Item.Banana.getPrice().multiply(BigDecimal.valueOf(1.5));
        expected = expected.add(Item.Apple.getPrice());
        assertThat(expected, is(result));
    }




}
