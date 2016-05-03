package com.ad.pricing;

import com.ad.Basket;
import com.ad.Item;
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
import static org.mockito.Mockito.when;


public class DiscountMaximizingBasketPricerTest {


    @Test(expected = NullPointerException.class)
    public void priceNullBasket(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(Collections.<Item>emptyList());

        BasketPricer pricer = new DiscountMaximizingBasketPricer();
        BigDecimal result  = pricer.price(null);

        fail("Should not come here. Null element must throw exception");

    }

    @Test
    public void priceEmptyBasket(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(Collections.<Item>emptyList());

        BasketPricer pricer = new DiscountMaximizingBasketPricer();
        BigDecimal result  = pricer.price(b);

        assertThat(BigDecimal.ZERO, is(result));
     }

    @Test
    public void basketPricedCorrectlyWithOneItem(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple));

        BasketPricer pricer = new DiscountMaximizingBasketPricer();
        BigDecimal result  = pricer.price(b);

        assertThat(BigDecimal.valueOf(0.60), is(result));
    }

    @Test
    public void basketPricedCorrectlyWithSomeItems(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple,Item.Apple,Item.Banana, Item.Apple));

        BasketPricer pricer = new DiscountMaximizingBasketPricer();
        BigDecimal result  = pricer.price(b);

        assertThat(Item.Apple.getPrice().multiply(BigDecimal.valueOf(3)).add(Item.Banana.getPrice()), is(result));
    }

    @Test
    public void bOGOFChosenOverBuyOneGetOneHalfPriceOnSameItemSinglePair(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple, Item.Apple));

        BasketPricer pricer = new DiscountMaximizingBasketPricer();
        BigDecimal result  = pricer.price(b, new BuyOneGetOneFreePromotion(Item.Apple, BigDecimal.valueOf(0.6)), new BuyOneGetOneHalfPricePromotion(Item.Apple, BigDecimal.valueOf(0.6)));

        assertThat(Item.Apple.getPrice() , is(result));
    }


    @Test
    public void bOGOFChosenOverBuyOneGetOneHalfPriceOnSameItemMultiplePairs(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple, Item.Apple, Item.Apple, Item.Apple));

        BasketPricer pricer = new DiscountMaximizingBasketPricer();
        BigDecimal result  = pricer.price(b, new BuyOneGetOneFreePromotion(Item.Apple, BigDecimal.valueOf(0.6)), new BuyOneGetOneHalfPricePromotion(Item.Apple, BigDecimal.valueOf(0.6)));

        // BOGOF should be applied twice in preference to Buy One get One half price
        assertThat(Item.Apple.getPrice().multiply(BigDecimal.valueOf(2)) , is(result));
    }


    @Test
    public void bOGOFChosenOverBuyTwoGetOneFree(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple, Item.Apple, Item.Apple, Item.Apple));

        BasketPricer pricer = new DiscountMaximizingBasketPricer();
        BigDecimal result  = pricer.price(b, new BuyTwoGetOneFreePromotion(Item.Apple, BigDecimal.valueOf(0.6)), new BuyOneGetOneFreePromotion(Item.Apple, BigDecimal.valueOf(0.6)));

        // BOGOF should be applied twice in preference to Buy Two get One half price
        assertThat(Item.Apple.getPrice().multiply(BigDecimal.valueOf(2)) , is(result));
    }


    @Test
    public void bOGOFChosenOverAllOtherPromotions(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple, Item.Apple, Item.Apple, Item.Apple));

        BasketPricer pricer = new DiscountMaximizingBasketPricer();
        BigDecimal result  = pricer.price(b, new BuyTwoGetOneFreePromotion(Item.Apple, BigDecimal.valueOf(0.6)), new BuyOneGetOneHalfPricePromotion(Item.Apple, BigDecimal.valueOf(0.6)),  new BuyOneGetOneFreePromotion(Item.Apple, BigDecimal.valueOf(0.6)));

        // BOGOF should be applied twice in preference to Buy Two get One half price
        assertThat(Item.Apple.getPrice().multiply(BigDecimal.valueOf(2)) , is(result));
    }



    @Test
    public void basketPricedCorrectlyWithDifferentOffersOnDifferentItems(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple, Item.Apple, Item.Banana, Item.Banana,Item.Banana));

        BasketPricer pricer = new DiscountMaximizingBasketPricer();
        BigDecimal result  = pricer.price(b, new BuyTwoGetOneFreePromotion(Item.Banana, BigDecimal.valueOf(0.3)), // Applied
                                             new BuyOneGetOneHalfPricePromotion(Item.Apple, BigDecimal.valueOf(0.6)), // Discarded
                                             new BuyOneGetOneFreePromotion(Item.Apple, BigDecimal.valueOf(0.6))); // Applied

        // BOGOF should be applied to Apples and 50% discount on second item for Banana
        BigDecimal expected = Item.Apple.getPrice();
        expected = expected.add( Item.Banana.getPrice().multiply(BigDecimal.valueOf(2)) );

        assertThat(expected, is(result));
    }

}
