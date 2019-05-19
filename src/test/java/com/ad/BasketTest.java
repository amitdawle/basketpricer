package com.ad;

import org.junit.Test;

import static com.ad.Item.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;


public class BasketTest {

    @Test
    public void basketCanBeCorrectlySetup(){
        Basket b = new Basket();
        b.addItem(Apple);
        b.addItem(Apple);
        b.addItem(Banana);

        assertEquals(3, (b.getItems().size()));
        assertEquals(2L, (b.getItems().stream().filter(x -> x == Apple ).count()));
        assertEquals(1L, (b.getItems().stream().filter(x -> x == Banana ).count()));
    }

    @Test(expected = NullPointerException.class)
    public void basketCannotContainInvalidItems(){
        Basket b = new Basket();
        b.addItem(Apple);
        b.addItem(Apple);
        b.addItem(null);

        assertEquals(3, (b.getItems().size()));
        assertEquals(2L, (b.getItems().stream().filter(x -> x == Apple ).count()));
        assertEquals(1L, (b.getItems().stream().filter(x -> x == Banana ).count()));
    }
}
