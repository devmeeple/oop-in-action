package reservation;

import generic.Money;

public class Movie {

    private Money fee;
    private DiscountPolicy discountPolicy;

    public Money calculateFee(Screening screening) {
        return fee.minus(discountPolicy.calculateDiscount(screening));
    }

    public Money getFee() {
        return fee;
    }
}
