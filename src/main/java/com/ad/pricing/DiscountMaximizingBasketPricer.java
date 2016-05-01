package com.ad.pricing;

import com.ad.Basket;
import com.ad.Discount;
import com.ad.Item;
import com.ad.promotions.Promotion;


import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ad.Discount.ZERO_DISCOUNT;

public class DiscountMaximizingBasketPricer extends SimpleBasketPricer {

    @Override
    public BigDecimal price(Basket basket, Promotion... promotions) {
        BigDecimal totalDiscount = BigDecimal.ZERO;
        if( promotions != null ) {
            Discount optimalDiscount = optimalDiscount(basket, promotions);
            totalDiscount = optimalDiscount.getAmount();
        }
        BigDecimal amount =  price(basket);
        return amount.subtract(totalDiscount);
    }

    @Nonnull
    private Discount optimalDiscount(@Nonnull Basket basket, @Nonnull Promotion ... promotions) {
        Objects.requireNonNull(basket, "basket should not be empty");
        if(basket.getItems().isEmpty()){
            return ZERO_DISCOUNT;
        }

        List<Discount> discounts = new ArrayList<>();
        for(Promotion promotion: promotions){
            discounts.add(promotion.applyOnce(basket));
        }

        List<Discount> appliedDiscounts = discounts.stream().filter(d -> d.getAmount() != BigDecimal.ZERO).collect(Collectors.toList());

        List<Discount> candidates = new ArrayList<>();
        for(Discount discountSoFar: appliedDiscounts) {
          BigDecimal currentAmount = discountSoFar.getAmount();
          Basket itemsNotYetDiscounted = this.basketWithoutDiscountedItems(basket, discountSoFar.getDiscountedItems());

          Discount optimalDiscountOtherItems = optimalDiscount(itemsNotYetDiscounted , promotions);

          List<Item> discountedItems = new ArrayList<>();
          discountedItems.addAll(discountSoFar.getDiscountedItems());
          discountedItems.addAll(optimalDiscountOtherItems.getDiscountedItems());

          candidates.add( new Discount(currentAmount.add( optimalDiscountOtherItems.getAmount()), discountedItems) );
        }
        Optional<Discount> optimal = candidates.stream().max((a, b) -> a.getAmount().compareTo(b.getAmount()));

        if( optimal.isPresent() ) {
           return optimal.get();
        }
        return ZERO_DISCOUNT;
    }



}
