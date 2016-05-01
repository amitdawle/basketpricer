package com.ad.pricing;

import com.ad.Basket;
import com.ad.Item;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class SimpleBasketPricer implements BasketPricer {

    @Override
    public BigDecimal price(@NotNull Basket b) {
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
