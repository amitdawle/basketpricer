package com.ad.promotions;

import com.ad.Basket;
import com.ad.Discount;
import com.ad.Item;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

abstract class MultiBuyPromotion implements Promotion{

    private List<Item> eligibleItems = null;
    private List<Item> discountedItems = null;
    private int requiredNumberOfEligibleItems;
    private int requiredNumberOfDiscountedItems;

    MultiBuyPromotion(@Nonnull List<Item> eligibleItems, int requiredNumberOfEligibleItems,
                      @Nonnull List<Item> discountedItems, int requiredNumberOfDiscountedItems){
        Objects.requireNonNull(eligibleItems, "Items under offer should not be null");
        this.eligibleItems = eligibleItems;
        this.discountedItems = discountedItems;
        this.requiredNumberOfEligibleItems = requiredNumberOfEligibleItems;
        this.requiredNumberOfDiscountedItems = requiredNumberOfDiscountedItems;
    }

    @Nonnull
   public Discount applyOnce(Basket b) {
        requireNonNull(b, "basket should not be null");
        List<Item> items = new ArrayList<>(b.getItems());
        List<Item> c = items
                .stream()
                .filter(x -> eligibleItems.contains(x))
                .collect(Collectors.toList());

        if( c.size() < requiredNumberOfEligibleItems) {
            return Discount.ZERO_DISCOUNT;
        }

        c = c.subList(0, requiredNumberOfEligibleItems);
        c.forEach(items::remove);

        List<Item> d = items
                .stream()
                .filter(x -> discountedItems.contains(x))
                .collect(Collectors.toList());

        if( d.size() < requiredNumberOfDiscountedItems ) {
            return Discount.ZERO_DISCOUNT;
        }
        d = d.subList(0 , requiredNumberOfDiscountedItems);
        return applyDiscount(c, d);
    }

    protected abstract Discount applyDiscount(List<Item> chargeableItems, List<Item> discountedItems);

}
