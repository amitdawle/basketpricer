package com.ad.promotions;

import com.ad.Basket;
import com.ad.Discount;
import com.ad.Item;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static com.ad.Item.Apple;
import static com.ad.Item.Banana;
import static com.ad.promotions.Promotions.buyOneGetOneFree;
import static com.google.common.collect.Lists.newArrayList;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BuyOneGetOneFreePromotionTest {

    @Test
    public void noDiscountOnEmptyBasket(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(Collections.<Item>emptyList());
        Promotion promotion = buyOneGetOneFree(Apple);

        Discount d = promotion.applyOnce(b);

        assertEquals(d.getAmount(), (BigDecimal.ZERO));
        assertEquals(d.getDiscountedItems(), (Collections.EMPTY_LIST));

    }

    @Test
    public void discountAppliedOnceForBasketWithEligibleItems(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Apple, Apple));
        Promotion promotion = buyOneGetOneFree(Apple);

        Discount d = promotion.applyOnce(b);


        assertEquals(d.getAmount(), (Apple.getPrice()));
        assertTrue( d.getDiscountedItems().contains(Apple));
        assertEquals( 2, (d.getDiscountedItems().size()) );
    }

    @Test
    public void noDiscountAppliedOnceForBasketWithoutEligibleItems(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Apple, Banana));
        Promotion promotion = buyOneGetOneFree(Apple);

        Discount d = promotion.applyOnce(b);

        assertEquals(BigDecimal.ZERO, (d.getAmount()));
        assertEquals( 0, ( d.getDiscountedItems().size()) );
    }


    @Test
    public void discountAppliedForBasketWithMultipleItems(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Apple, Banana, Apple, Apple));
        Promotion promotion = buyOneGetOneFree(Apple);

        Discount d = promotion.applyOnce(b);

        assertEquals(d.getAmount(), (Apple.getPrice()));
        assertTrue(d.getDiscountedItems().contains(Apple));
        assertEquals(2, (d.getDiscountedItems().size()) );
    }



}
