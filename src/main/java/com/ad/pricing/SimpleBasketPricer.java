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

public class SimpleBasketPricer implements BasketPricer {

    @Nonnull
    private BigDecimal price(@Nonnull Basket b) {
        Objects.requireNonNull(b , "Basket should not be null");

        if( b.getItems() == null ){
            return BigDecimal.ZERO;
        }

        BigDecimal result = BigDecimal.ZERO;
        for(Item i: b.getItems()){
            result = result.add(i.getPrice());
        }
        return result;
    }

    @Override
    public BigDecimal price(Basket basket, Promotion... promotions) {
        BigDecimal totalDiscount = BigDecimal.ZERO;
        if( promotions != null ) {
            for(Promotion promotion : promotions) {
                Basket currentBasket = basket;
                Discount discount = promotion.applyOnce(currentBasket);
                while (discount.getAmount() != BigDecimal.ZERO) {
                    totalDiscount = totalDiscount.add(discount.getAmount());
                    currentBasket = basketWithoutDiscountedItems(currentBasket , discount.getDiscountedItems());
                    discount = promotion.applyOnce(currentBasket);
                }
            }
        }
        BigDecimal amount =  price(basket);
        return amount.subtract(totalDiscount);
    }

    private Basket basketWithoutDiscountedItems(Basket b , List<Item> discountedItems){
        Basket basketWithoutDiscountedItems = new Basket();
        List<Item> itemsInBasket =  new ArrayList<>(b.getItems());

        discountedItems.forEach(itemsInBasket::remove);
        itemsInBasket.forEach(basketWithoutDiscountedItems::addItem);

        return  basketWithoutDiscountedItems;
    }
}
