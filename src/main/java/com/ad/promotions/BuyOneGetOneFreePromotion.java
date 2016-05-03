package com.ad.promotions;

import com.ad.Item;

import javax.annotation.Nonnull;
import java.math.BigDecimal;

import static com.google.common.collect.Lists.newArrayList;

public class BuyOneGetOneFreePromotion extends MultiBuyPromotion {


    public BuyOneGetOneFreePromotion(@Nonnull Item eligibleItem, BigDecimal amount) {
       super(newArrayList(eligibleItem) , amount);
    }

    @Override
    protected int getItemQuantityNeededToApplyDiscount() {
        return 2;
    }
}
