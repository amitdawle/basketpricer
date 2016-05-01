package com.ad.promotions;

import com.ad.Basket;
import com.ad.Discount;
import com.ad.Item;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Objects.requireNonNull;

public class BuyOneGetOneFreePromotion extends MultiBuyPromotion {

    public BuyOneGetOneFreePromotion(@Nonnull Item eligibleItem) {
        super(newArrayList(eligibleItem));
    }


    @Override
    protected Discount applyDiscount(List<Item> eligibleForDiscount) {
        Objects.requireNonNull(eligibleForDiscount , "Items eligible for discount must not be null");
        if( eligibleForDiscount.size() < 2 ) {
            return ZERO_DISCOUNT;
        } else{
            List<Item> discountedItems = eligibleForDiscount.subList(0, 2);
            BigDecimal discount = discountedItems.get(0).getPrice();
            return new Discount(discount, discountedItems);
        }
    }
}
