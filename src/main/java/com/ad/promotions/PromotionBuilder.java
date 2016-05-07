package com.ad.promotions;

import com.ad.Item;

import javax.annotation.Nonnull;

public interface PromotionBuilder {
    OfferBuilder on(int quantity, @Nonnull Item item);
}
