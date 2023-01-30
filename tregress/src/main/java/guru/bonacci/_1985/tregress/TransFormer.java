package guru.bonacci._1985.tregress;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import guru.bonacci._1985.tregress.in.KafTrans;
import guru.bonacci._1985.tregress.out.CassTrans;
import guru.bonacci._1985.tregress.out.CassTransKey;

@Component
public class TransFormer {

	CassTrans toLatter(KafTrans kTrans, long when) {
		
		var cTrans = CassTrans.builder()
				.transferId(kTrans.getTransferId())
				.poolId(kTrans.getPoolId())
				.from(kTrans.getFrom())
				.to(kTrans.getTo())
				.amount(kTrans.getAmount().multiply(new BigDecimal(100)).intValue())
				.build();

		cTrans.setKey(new CassTransKey(CassTrans.poolAccountId(cTrans), when));
		return cTrans;
	}
}
