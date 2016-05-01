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

import static java.util.Objects.requireNonNull;

abstract class MultiBuyPromotion implements Promotion{

    protected  static final Discount ZERO_DISCOUNT = new Discount(BigDecimal.ZERO, Collections.<Item>emptyList());
    protected List<Item> itemsUnderOffer;

    MultiBuyPromotion(@Nonnull List<Item> itemsUnderOffer){
        Objects.requireNonNull(itemsUnderOffer , "Items under offer should not be null");
        this.itemsUnderOffer = itemsUnderOffer;
    }

    @Nonnull
   public Discount applyOnce(Basket b) {
        requireNonNull(b, "basket should not be null");
        List<Item> eligibleItems = b.getItems()
                .stream()
                .filter(x -> itemsUnderOffer.contains(x))
                .collect(Collectors.toList());

        if(eligibleItems.isEmpty() ) {
            return ZERO_DISCOUNT;
        }
        return applyDiscount(eligibleItems);
    }


    protected abstract Discount applyDiscount(List<Item> eligibleForDiscount);
}
