package com.ad.pricing;

import com.ad.Basket;
import com.ad.promotions.Promotion;

import java.math.BigDecimal;


public interface BasketPricer {

    BigDecimal price(Basket b , Promotion...promotions);
}
