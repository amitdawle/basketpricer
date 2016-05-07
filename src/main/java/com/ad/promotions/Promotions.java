package com.ad.promotions;


import com.ad.Item;

import javax.annotation.Nonnull;
import java.util.Objects;

public final class Promotions {

    private Promotions() {
    }

    // Popular discounts
    @Nonnull
    public static Promotion buyOneGetOneFree(@Nonnull Item item) {
        Objects.requireNonNull(item, "Item should not be null");
        return new PromotionBuilderImpl().on(1, item).offer(1, item).free();
    }

    @Nonnull
    public static Promotion buyOneGetOneHalfPrice(@Nonnull Item item) {
        Objects.requireNonNull(item, "Item should not be null");
        return new PromotionBuilderImpl().on(1, item).offer(1, item).discountedBy(50);
    }

    @Nonnull
    public static Promotion buyTwoGetOneFree(@Nonnull Item item) {
        Objects.requireNonNull(item, "Item should not be null");
        return new PromotionBuilderImpl().on(2, item).offer(1, item).free();
    }




}
