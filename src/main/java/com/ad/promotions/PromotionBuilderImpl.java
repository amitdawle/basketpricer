package com.ad.promotions;

import com.ad.Item;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;


class PromotionBuilderImpl implements PromotionBuilder {

    @Nonnull
    public OfferBuilder on(int quantity, @Nonnull Item item) {
        requireNonNull(item, "Item should not be null");
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity should be non negative");
        }
        return new OfferBuilderImpl(quantity, item);
    }

}
