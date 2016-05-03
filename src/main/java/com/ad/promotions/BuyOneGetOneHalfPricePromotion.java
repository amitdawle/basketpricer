package com.ad.promotions;

import com.ad.Item;

import javax.annotation.Nonnull;
import java.math.BigDecimal;

import static com.google.common.collect.Lists.newArrayList;

public class BuyOneGetOneHalfPricePromotion extends MultiBuyPromotion {

    public BuyOneGetOneHalfPricePromotion(@Nonnull Item eligibleItem, BigDecimal discountAmount) {
        super(newArrayList(eligibleItem), discountAmount);
    }


    @Override
    protected int getItemQuantityNeededToApplyDiscount() {
        return 2;
    }
}
