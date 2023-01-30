package guru.bonacci._1985.accountinit;

import java.math.BigDecimal;

import guru.bonacci._1985.domain.Pool;

public interface PoolTypeBasedInitializer {

  BigDecimal determineStartAmount(Pool pool);
}
