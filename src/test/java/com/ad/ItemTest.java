package com.ad;

import org.junit.Test;

import java.math.BigDecimal;

import static com.ad.Item.Apple;
import static com.ad.Item.Banana;
import static org.junit.Assert.assertEquals;

public class ItemTest {

    @Test
    public void itemsHaveCorrectPrice(){
        assertEquals(Apple.getPrice(), BigDecimal.valueOf(0.60));
        assertEquals(Banana.getPrice(), BigDecimal.valueOf(0.30));
    }



}
