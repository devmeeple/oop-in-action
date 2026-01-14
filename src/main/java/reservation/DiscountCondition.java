package reservation;

public interface DiscountCondition {

    boolean isSatisfiedBy(Screening screening);
}
