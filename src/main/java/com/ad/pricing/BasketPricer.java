package com.ad.pricing;

import com.ad.Basket;

import java.math.BigDecimal;

public interface BasketPricer {

    BigDecimal price(Basket b);
}
