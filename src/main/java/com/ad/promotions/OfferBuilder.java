package com.ad.promotions;

import com.ad.Item;


interface OfferBuilder {
    DiscountBuilder offer(int quantity, Item item);
}
