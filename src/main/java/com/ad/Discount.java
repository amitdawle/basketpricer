package com.ad;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Discount {

    private BigDecimal amount;
    private List<Item> discountedItems;


    public Discount(@NotNull BigDecimal amount, @NotNull List<Item> items) {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(items);
        this.amount = amount;
        this.discountedItems = Collections.unmodifiableList(items);
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public List<Item>  getDiscountedItems(){
        return discountedItems;
    }


}
