package com.ad.promotions;

import com.ad.Basket;
import com.ad.Discount;

public interface Promotion {
   Discount applyOnce(Basket b);
}
