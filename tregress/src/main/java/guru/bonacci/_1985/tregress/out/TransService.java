package guru.bonacci._1985.tregress.out;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransService {	

	private final TransRepository cTransRepo;
	
	public void save(Trans cTrans) {
		log.info("saving {}", cTrans);
		
		var cTransNeg = cTrans.negativeClone();
		log.info("and {}", cTransNeg);

		cTransRepo.saveAll(List.of(cTrans, cTransNeg));
	}
}