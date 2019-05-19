package com.ad.promotions;

import com.ad.Basket;
import com.ad.Discount;
import com.ad.Item;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static com.ad.promotions.Promotions.buyTwoGetOneFree;
import static com.google.common.collect.Lists.newArrayList;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BuyTwoGetOneFreePromotionTest {

    @Test
    public void noDiscountOnEmptyBasket(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(Collections.<Item>emptyList());
        Promotion promotion = buyTwoGetOneFree(Item.Apple);

        Discount d = promotion.applyOnce(b);

        assertEquals(BigDecimal.ZERO, (d.getAmount()));
        assertEquals(Collections.EMPTY_LIST, (d.getDiscountedItems()));
    }

    @Test
    public void discountAppliedOnceForBasketWithEligibleItems(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple, Item.Apple, Item.Apple));
        Promotion promotion = buyTwoGetOneFree(Item.Apple);

        Discount d = promotion.applyOnce(b);

        assertEquals(Item.Apple.getPrice(), (d.getAmount()));
        assertTrue( d.getDiscountedItems().contains(Item.Apple));
        assertEquals( 3, (d.getDiscountedItems().size()) );
    }

    @Test
    public void noDiscountAppliedOnceForBasketWithoutEligibleItems(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple, Item.Banana));
        Promotion promotion = buyTwoGetOneFree(Item.Apple);

        Discount d = promotion.applyOnce(b);

        assertEquals(BigDecimal.ZERO, (d.getAmount()));
        assertEquals( 0, ( d.getDiscountedItems().size()) );

    }


    @Test
    public void discountAppliedForBasketWithMultipleItems(){
        Basket b = mock(Basket.class);
        when(b.getItems()).thenReturn(newArrayList(Item.Apple, Item.Banana, Item.Apple, Item.Apple));
        Promotion promotion = buyTwoGetOneFree(Item.Apple);

        Discount d = promotion.applyOnce(b);

        assertEquals(Item.Apple.getPrice(), (d.getAmount()));
        assertTrue(d.getDiscountedItems().contains(Item.Apple));
        assertEquals(3, (d.getDiscountedItems().size()) );
    }
}
