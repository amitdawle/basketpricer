package com.ad.promotions;

import com.ad.Discount;
import com.ad.Item;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;


class DiscountBuilderImpl implements DiscountBuilder {

    private int quantityThisItem;
    private Item thisItem;
    private int quantityDiscountedItem;
    private Item discountedItem;

    DiscountBuilderImpl(int quantityThisItem, Item thisItem, int quantityDiscountedItem, Item discountedItem) {
        this.quantityThisItem = quantityThisItem;
        this.thisItem = thisItem;
        this.quantityDiscountedItem = quantityDiscountedItem;
        this.discountedItem = discountedItem;
    }


    @Override
    public Promotion free() {
        return new MultiBuyPromotion(newArrayList(thisItem), quantityThisItem, newArrayList(discountedItem), quantityDiscountedItem) {
            @Override
            protected Discount applyDiscount(List<Item> chargeableItems, List<Item> discountedItems) {
                if (chargeableItems.size() < quantityThisItem) {
                    return Discount.ZERO_DISCOUNT;
                }
                if (discountedItems.size() < quantityDiscountedItem) {
                    return Discount.ZERO_DISCOUNT;
                }
                List<Item> items = itemsOfferAppliedTo(chargeableItems, discountedItems);

                return new Discount(discountedItem.getPrice(), items);
            }
        };
    }

    @Override
    public Promotion discountedBy(double percentage) {
        return new MultiBuyPromotion(newArrayList(thisItem), quantityThisItem, newArrayList(discountedItem), quantityDiscountedItem) {
            @Override
            protected Discount applyDiscount(List<Item> chargeableItems, List<Item> discountedItems) {
                if (chargeableItems.size() < quantityThisItem) {
                    return Discount.ZERO_DISCOUNT;
                }
                if (discountedItems.size() < quantityDiscountedItem) {
                    return Discount.ZERO_DISCOUNT;
                }
                List<Item> items = itemsOfferAppliedTo(chargeableItems, discountedItems);
                return new Discount(discountedItem.getPrice()
                        .multiply(BigDecimal.valueOf(discountedItems.size()))
                        .multiply(BigDecimal.valueOf(percentage / 100)),
                        items);
            }
        };
    }

    private List<Item> itemsOfferAppliedTo(List<Item> chargeableItems, List<Item> discountedItems) {
        List<Item> itemsProcessed = newArrayList();
        itemsProcessed.addAll(chargeableItems);
        itemsProcessed.addAll(discountedItems);
        return itemsProcessed;
    }
}
