package guru.bonacci._1985.tregress;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import guru.bonacci._1985.cassandra.CTrans;
import guru.bonacci._1985.cassandra.CTransKey;
import guru.bonacci._1985.kafka.KTrans;

@Component
public class TransFormer {

	CTrans toLatter(KTrans kTrans, long when) {
		
		var cTrans = CTrans.builder()
				.transferId(kTrans.getTransferId())
				.poolId(kTrans.getPoolId())
				.from(kTrans.getFrom())
				.to(kTrans.getTo())
				.amount(kTrans.getAmount().multiply(new BigDecimal(100)).intValue())
				.build();

		cTrans.setKey(new CTransKey(cTrans.poolAccountId(), when));
		return cTrans;
	}
}
