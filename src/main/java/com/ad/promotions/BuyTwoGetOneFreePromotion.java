package com.ad.promotions;

import com.ad.Basket;
import com.ad.Discount;
import com.ad.Item;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class BuyTwoGetOneFreePromotion implements Promotion {

    private Item itemUnderOffer;

    public BuyTwoGetOneFreePromotion(@NotNull  Item eligibleItem) {
        requireNonNull(eligibleItem, "Item under offer should not be null");
        this.itemUnderOffer = eligibleItem;
    }


    @Override
    public Discount applyOnce(@NotNull Basket b) {
        requireNonNull(b, "basket should not be null");
        List<Item> eligibleItems = b.getItems()
                                        .stream()
                                        .filter(x -> itemUnderOffer == x)
                                        .collect(Collectors.toList());

        if(eligibleItems.size() < 3 ) {
            return new Discount(BigDecimal.ZERO, Collections.<Item>emptyList());
        }
        List<Item> discountedItems = eligibleItems.subList(0,3);
        BigDecimal discount = discountedItems.get(0).getPrice();
        return new Discount(discount, discountedItems);
    }
}
