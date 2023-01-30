package guru.bonacci._1985.tregress;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import guru.bonacci._1985.tregress.in.KafTrans;
import guru.bonacci._1985.tregress.out.Trans;
import guru.bonacci._1985.tregress.out.TransKey;

@Component
public class TransFormer {

	Trans toLatter(KafTrans kafka, long when) {
		
		var cTrans = Trans.builder()
				.transferId(kafka.getTransferId())
				.poolId(kafka.getPoolId())
				.from(kafka.getFrom())
				.to(kafka.getTo())
				.amount(kafka.getAmount().multiply(new BigDecimal(100)).intValue())
				.build();

		cTrans.setKey(new TransKey(Trans.poolAccountId(cTrans), when));
		return cTrans;
	}
}
