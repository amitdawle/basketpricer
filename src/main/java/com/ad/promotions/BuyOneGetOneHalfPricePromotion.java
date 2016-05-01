package com.ad.promotions;

import com.ad.Discount;
import com.ad.Item;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.google.common.collect.Lists.newArrayList;

public class BuyOneGetOneHalfPricePromotion extends MultiBuyPromotion {

    public BuyOneGetOneHalfPricePromotion(@Nonnull Item eligibleItem) {
        super(newArrayList(eligibleItem));
    }


    @Override
    protected Discount applyDiscount(List<Item> eligibleForDiscount) {
        Objects.requireNonNull(eligibleForDiscount, "Items eligible for discount must not be null");
        if( eligibleForDiscount.size() < 2 ) {
            return ZERO_DISCOUNT;
        } else{
            List<Item> discountedItems = eligibleForDiscount.subList(0, 2);
            BigDecimal discount = discountedItems.get(0).getPrice().multiply(BigDecimal.valueOf(0.5));
            return new Discount(discount, discountedItems);
        }
    }
}
