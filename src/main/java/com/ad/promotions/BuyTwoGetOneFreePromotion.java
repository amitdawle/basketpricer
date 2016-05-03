package com.ad.promotions;

import com.ad.Item;

import javax.annotation.Nonnull;
import java.math.BigDecimal;

import static com.google.common.collect.Lists.newArrayList;

public class BuyTwoGetOneFreePromotion extends MultiBuyPromotion {

    public BuyTwoGetOneFreePromotion(@Nonnull Item eligibleItem, @Nonnull BigDecimal discountAmount) {
        super(newArrayList(eligibleItem), discountAmount);
    }

    @Override
    protected int getItemQuantityNeededToApplyDiscount() {
        return 3;
    }
}
