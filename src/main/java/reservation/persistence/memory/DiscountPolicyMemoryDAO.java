package reservation.persistence.memory;

import reservation.domain.DiscountCondition;
import reservation.domain.DiscountPolicy;
import reservation.persistence.DiscountConditionDAO;
import reservation.persistence.DiscountPolicyDAO;

import java.util.List;

public class DiscountPolicyMemoryDAO extends InMemoryDAO<DiscountPolicy> implements DiscountPolicyDAO {

    private DiscountConditionDAO discountConditionDAO;

    public DiscountPolicyMemoryDAO(DiscountConditionDAO discountConditionDAO) {
        this.discountConditionDAO = discountConditionDAO;
    }

    @Override
    public DiscountPolicy selectDiscountPolicy(Long movieId) {
        DiscountPolicy result = findOne(policy -> policy.getMovieId().equals(movieId));
        if (result == null) {
            return null;
        }

        List<DiscountCondition> conditions = discountConditionDAO.selectDiscountConditions(result.getId());
        result.setDiscountConditions(conditions);

        return result;
    }
}
