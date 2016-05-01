import com.ad.Basket;
import com.ad.Item;
import com.ad.pricing.SimpleBasketPricer;
import com.ad.promotions.BuyOneGetOneFreePromotion;
import com.ad.promotions.BuyTwoGetOneFreePromotion;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class SimpleBasketPricerTest {


    @Test(expected = NullPointerException.class)
    public void priceNullBasket(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(Collections.<Item>emptyList());

        SimpleBasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(null);

        assertThat(BigDecimal.ZERO, is(result));
    }

    @Test
    public void priceEmptyBasket(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(Collections.<Item>emptyList());

        SimpleBasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b);

        assertThat(BigDecimal.ZERO, is(result));
     }

    @Test
    public void priceBasketWithOneItem(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple));

        SimpleBasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b);

        assertThat(BigDecimal.valueOf(0.60), is(result));
    }

    @Test
    public void priceBasketWithSomeItems(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple,Item.Apple,Item.Banana, Item.Apple));

        SimpleBasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b);

        assertThat(Item.Apple.getPrice().multiply(BigDecimal.valueOf(3)).add(Item.Banana.getPrice()), is(result));
    }

    @Test
    public void priceBasketWithSomeItemsAndBuyTwoGetOneFreeDiscount(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple,Item.Apple,Item.Banana, Item.Apple));

        SimpleBasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b, new BuyTwoGetOneFreePromotion(Item.Apple));

        assertThat(Item.Apple.getPrice().multiply(BigDecimal.valueOf(2)).add(Item.Banana.getPrice()), is(result));
    }


    @Test
    public void priceBasketWithSomeItemsAndBuyOneGetOneFreeDiscount(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple,Item.Apple,Item.Apple, Item.Apple));

        SimpleBasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b, new BuyOneGetOneFreePromotion(Item.Apple));

        assertThat(Item.Apple.getPrice().multiply(BigDecimal.valueOf(2)), is(result));
    }

    @Test
    public void priceBasketWithSomeItemsAndBuyOneGetOneFreeDiscountOnMultipleItems(){
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList(Item.Banana,Item.Apple,Item.Apple,Item.Apple, Item.Apple, Item.Banana));

        SimpleBasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b, new BuyOneGetOneFreePromotion(Item.Apple), new BuyOneGetOneFreePromotion(Item.Banana));

        assertThat(Item.Apple.getPrice().multiply(BigDecimal.valueOf(2)).add(Item.Banana.getPrice()), is(result));
    }



    @Test
    public void priceBasketWithSomeItemsAndBuyTwoGetOneFreeDiscountOnMultipleItems(){
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList(Item.Banana,Item.Apple,Item.Apple,Item.Apple, Item.Apple, Item.Banana, Item.Banana));

        SimpleBasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b, new BuyTwoGetOneFreePromotion(Item.Apple), new BuyTwoGetOneFreePromotion(Item.Banana));

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

        SimpleBasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result1  = pricer.price(b, new BuyTwoGetOneFreePromotion(Item.Apple), new BuyTwoGetOneFreePromotion(Item.Banana));

        BigDecimal result2  = pricer.price(b, new BuyTwoGetOneFreePromotion(Item.Banana), new BuyTwoGetOneFreePromotion(Item.Apple) );

        assertEquals( result1, result2);
    }

    @Test
    public void priceCalculatedCorrectlyDifferentPromotionsOnDifferentItems(){
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList(Item.Banana, Item.Apple, Item.Apple, Item.Apple, Item.Banana ));

        SimpleBasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result1  = pricer.price(b, new BuyTwoGetOneFreePromotion(Item.Apple), new BuyTwoGetOneFreePromotion(Item.Banana));

        BigDecimal result2  = pricer.price(b, new BuyTwoGetOneFreePromotion(Item.Banana),new BuyTwoGetOneFreePromotion(Item.Apple) );

        assertEquals( result1, result2);
    }


    @Test
    public void priceCalculatedCorrectlyWhenNoEligibleItemsForDiscount(){
        Basket b = mock(Basket.class);
        when(b.getItems())
                .thenReturn(newArrayList( Item.Apple, Item.Apple));

        SimpleBasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result1  = pricer.price(b, new BuyOneGetOneFreePromotion(Item.Banana));

        assertThat( Item.Apple.getPrice().add(Item.Apple.getPrice()), is(result1));
    }



}
