package com.ad;

import org.junit.Test;

import static com.ad.Item.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class BasketTest {

    @Test
    public void basketCanBeCorrectlySetup(){
        Basket b = new Basket();
        b.addItem(Apple);
        b.addItem(Apple);
        b.addItem(Banana);

        assertThat(3, is(b.getItems().size()));
        assertThat(2L, is(b.getItems().stream().filter(x -> x == Apple ).count()));
        assertThat(1L, is(b.getItems().stream().filter(x -> x == Banana ).count()));
    }

    @Test(expected = NullPointerException.class)
    public void basketCannotContainInvalidItems(){
        Basket b = new Basket();
        b.addItem(Apple);
        b.addItem(Apple);
        b.addItem(null);

        assertThat(3, is(b.getItems().size()));
        assertThat(2L, is(b.getItems().stream().filter(x -> x == Apple ).count()));
        assertThat(1L, is(b.getItems().stream().filter(x -> x == Banana ).count()));
    }
}
