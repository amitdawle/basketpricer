package com.ad.pricing;

import com.ad.Basket;
import com.ad.Item;


import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.Objects;

public class SimpleBasketPricer implements BasketPricer {

    @Override
    @Nonnull
    public BigDecimal price(@Nonnull Basket b) {
        Objects.requireNonNull(b , "Basket should not be null");

        if( b.getItems() == null ){
            return BigDecimal.ZERO;
        }

        BigDecimal result = BigDecimal.ZERO;
        for(Item i: b.getItems()){
            result = result.add(i.getPrice());
        }
        return result;
    }
}
