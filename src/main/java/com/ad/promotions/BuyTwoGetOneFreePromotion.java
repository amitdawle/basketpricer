package com.ad.promotions;

import com.ad.Discount;
import com.ad.Item;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.google.common.collect.Lists.newArrayList;

public class BuyTwoGetOneFreePromotion extends MultiBuyPromotion {

    public BuyTwoGetOneFreePromotion(@Nonnull Item eligibleItem) {
        super(newArrayList(eligibleItem));
    }


    @Override
    protected Discount applyDiscount(List<Item> eligibleForDiscount) {
        Objects.requireNonNull(eligibleForDiscount, "Items eligible for discount must not be null");
        if( eligibleForDiscount.size() < 3 ) {
            return ZERO_DISCOUNT;
        } else{
            List<Item> discountedItems = eligibleForDiscount.subList(0, 3);
            BigDecimal discount = discountedItems.get(0).getPrice();
            return new Discount(discount, discountedItems);
        }
    }
}
