package com.ad.promotions;

import com.ad.Basket;
import com.ad.Discount;
import com.ad.Item;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static com.ad.promotions.Promotions.buyTwoGetOneFree;
import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BuyTwoGetOneFreePromotionTest {

    @Test
    public void noDiscountOnEmptyBasket(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(Collections.<Item>emptyList());
        Promotion promotion = buyTwoGetOneFree(Item.Apple);

        Discount d = promotion.applyOnce(b);

        assertThat(BigDecimal.ZERO, is(d.getAmount()));
        assertThat(Collections.EMPTY_LIST, is(d.getDiscountedItems()));
    }

    @Test
    public void discountAppliedOnceForBasketWithEligibleItems(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple, Item.Apple, Item.Apple));
        Promotion promotion = buyTwoGetOneFree(Item.Apple);

        Discount d = promotion.applyOnce(b);

        assertThat(Item.Apple.getPrice(), is(d.getAmount()));
        assertThat( d.getDiscountedItems(), hasItem(Item.Apple));
        assertThat( 3, is(d.getDiscountedItems().size()) );
    }

    @Test
    public void noDiscountAppliedOnceForBasketWithoutEligibleItems(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple, Item.Banana));
        Promotion promotion = buyTwoGetOneFree(Item.Apple);

        Discount d = promotion.applyOnce(b);

        assertThat(BigDecimal.ZERO, is(d.getAmount()));
        assertThat( 0, is( d.getDiscountedItems().size()) );

    }


    @Test
    public void discountAppliedForBasketWithMultipleItems(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple, Item.Banana, Item.Apple, Item.Apple));
        Promotion promotion = buyTwoGetOneFree(Item.Apple);

        Discount d = promotion.applyOnce(b);

        assertThat(Item.Apple.getPrice(), is(d.getAmount()));
        assertThat(d.getDiscountedItems(), hasItem(Item.Apple));
        assertThat(3, is(d.getDiscountedItems().size()) );
    }
}
