package com.ad.promotions;

interface DiscountBuilder {

    Promotion free();
    /* percentage value => 50 is 50% */
    Promotion discountedBy(double percentage);
}
