package com.ad.promotions;

import com.ad.Item;


class OfferBuilderImpl implements OfferBuilder {
    private int quantityThisItem;
    private Item thisItem;

    public OfferBuilderImpl(int quantity, Item item) {
        this.thisItem = item;
        this.quantityThisItem = quantity;
    }

    @Override
    public DiscountBuilder offer(int quantityDiscountedItem, Item discountedItem) {
        return new DiscountBuilderImpl(quantityThisItem, thisItem, quantityDiscountedItem, discountedItem);
    }
}
