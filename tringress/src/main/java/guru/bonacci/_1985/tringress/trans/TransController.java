package guru.bonacci._1985.tringress.trans;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci._1985.kafka.KTrans;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("transfers")
@RequiredArgsConstructor
public class TransController {

	private final TransService service;
	
	
	@PostMapping
  public KTrans transfer(@RequestBody @Valid TransDto dto) {
    var trans = toTrans(dto);
    var result = service.transfer(trans);
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
