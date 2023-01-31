package guru.bonacci._1985.trans;


import java.util.UUID;

import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci._1985.kafka.KTrans;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("transfers")
@RequiredArgsConstructor
public class TransController {

	private final TransService service;
	
	// TODO Callable<> 
	@PostMapping
  public Mono<KTrans> transfer(@RequestBody @Valid TransDto dto) {
    var watch = new StopWatch();
    watch.start();

    var trContext = toTrans(dto);
    var result = service.transfer(trContext);

    watch.stop();
    log.info("Processing Time : {}", watch.getTotalTimeMillis()); 
  
    return result;
  }
	
  static KTrans toTrans(TransDto dto) {
    return KTrans.builder()
    				.poolId(dto.getPoolId())
            .transferId(UUID.randomUUID().toString())
            .from(dto.getFrom())
            .to(dto.getTo())
            .amount(dto.getAmount())
            .build();
  }
}
