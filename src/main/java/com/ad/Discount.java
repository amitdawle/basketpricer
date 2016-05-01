package com.ad;


import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Discount {

    private BigDecimal amount;
    private List<Item> discountedItems;


    public Discount(@Nonnull BigDecimal amount, @Nonnull List<Item> items) {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(items);
        this.amount = amount;
        this.discountedItems = Collections.unmodifiableList(items);
    }


    @Nonnull
    public BigDecimal getAmount() {
        return amount;
    }

    @Nonnull
    public List<Item>  getDiscountedItems(){
        return discountedItems;
    }


}
