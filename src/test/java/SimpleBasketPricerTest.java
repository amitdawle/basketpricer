import com.ad.Basket;
import com.ad.Item;
import com.ad.pricing.SimpleBasketPricer;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
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

        assertThat(BigDecimal.ZERO , is(result));
    }

    @Test
    public void priceEmptyBasket(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(Collections.<Item>emptyList());

        SimpleBasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b);

        assertThat(BigDecimal.ZERO , is(result));
     }

    @Test
    public void priceBasketWithOneItem(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple));

        SimpleBasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b);

        assertThat(BigDecimal.valueOf(0.60) , is(result));
    }

    @Test
    public void priceBasketWithSomeItems(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple,Item.Apple,Item.Banana, Item.Apple));

        SimpleBasketPricer pricer = new SimpleBasketPricer();
        BigDecimal result  = pricer.price(b);

        assertThat(Item.Apple.getPrice().multiply(BigDecimal.valueOf(3)).add(Item.Banana.getPrice()) , is(result));
    }

}
