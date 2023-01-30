package guru.bonacci._1985.admin.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci._1985.admin.domain.Pool;
import guru.bonacci._1985.admin.query.IdAndName;
import guru.bonacci._1985.admin.repository.AccountRepository;
import guru.bonacci._1985.admin.repository.AdminRepository;
import guru.bonacci._1985.admin.repository.CPoolRepository;
import guru.bonacci._1985.admin.repository.PoolRepository;
import guru.bonacci._1985.cassandra.CPool;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PoolService {

	private final PoolRepository poolRepo;
	private final CPoolRepository cpoolRepo;
  private final AdminRepository adminRepo;
  private final AccountRepository accountRepo;
  
  
  public Optional<Pool> getPool(Long id) {
    return poolRepo.findById(id);
  }

  public Integer getPoolSize(Long id) {
    return accountRepo.findByPoolId(id).size();
  }

  public List<String> allPoolNames() {
    return poolRepo.findAllProjectedBy().stream()
                  .map(IdAndName::getName)
                  .collect(Collectors.toList());
  }
  
  @Transactional
  public Pool createPool(Long adminId, Pool pool) {
    var admin = adminRepo.findById(adminId)
         .orElseThrow(() -> new EntityNotFoundException("Cannot find admin with id " + adminId));
    pool.setAdmin(admin);

    var created = poolRepo.saveAndFlush(pool);
    
    //FIXME for transactional behaviour use Kafka as a hub
    cpoolRepo.save(new CPool(pool.getName(), pool.getType())); 
    return created;
  }
  
  @Transactional
  public void deactivate(Long id) {
    getPool(id).ifPresent(pool -> {
      pool.setActive(false);
      pool.setAdmin(null);
      
      poolRepo.saveAndFlush(pool);
      //FIXME for transactional behaviour use Kafka as a hub
      cpoolRepo.deleteById(pool.getName());
    });
  }
}
