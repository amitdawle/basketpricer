package com.ad.promotions;

import com.ad.Basket;
import com.ad.Discount;
import com.ad.Item;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

abstract class MultiBuyPromotion implements Promotion{

    protected List<Item> itemsUnderOffer;
    @Nonnull private BigDecimal discountAmount;


    MultiBuyPromotion(@Nonnull List<Item> itemsUnderOffer, @Nonnull BigDecimal amount){
        Objects.requireNonNull(itemsUnderOffer, "Items under offer should not be null");
        Objects.requireNonNull(amount, "discount for offer should not be null");
        this.itemsUnderOffer = itemsUnderOffer;
        this.discountAmount = amount;
    }

    @Nonnull
   public Discount applyOnce(Basket b) {
        requireNonNull(b, "basket should not be null");
        List<Item> eligibleItems = b.getItems()
                .stream()
                .filter(x -> itemsUnderOffer.contains(x))
                .collect(Collectors.toList());

        if(eligibleItems.isEmpty() ) {
            return Discount.ZERO_DISCOUNT;
        }
        int quantityNeededToApplyDiscount =  getItemQuantityNeededToApplyDiscount();
        if( eligibleItems.size() < quantityNeededToApplyDiscount ) {
            return Discount.ZERO_DISCOUNT;
        } else{
            List<Item> discountedItems = eligibleItems.subList(0, quantityNeededToApplyDiscount);
            return new Discount(discountAmount, discountedItems);
        }
    }

    protected abstract int getItemQuantityNeededToApplyDiscount();

}
