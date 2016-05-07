package com.ad.promotions;

import com.ad.Item;

import javax.annotation.Nonnull;
import java.util.Objects;

import static java.util.Objects.requireNonNull;


class OfferBuilderImpl implements OfferBuilder {
    private int quantityThisItem;
    private Item thisItem;

    @Nonnull
    public OfferBuilderImpl(int quantity, @Nonnull Item item) {
        requireNonNull(item, "item should not be null");
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity should be non negative");
        }
        this.thisItem = item;
        this.quantityThisItem = quantity;
    }

    @Override
    @Nonnull
    public DiscountBuilder offer(int quantityDiscountedItem, @Nonnull Item discountedItem) {
        if (quantityDiscountedItem <= 0) {
            throw new IllegalArgumentException("quantity should be non negative");
        }
        requireNonNull(discountedItem, "discountedItem should not be null");
        return new DiscountBuilderImpl(quantityThisItem, thisItem, quantityDiscountedItem, discountedItem);
    }
}
