package guru.bonacci._1985.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci._1985.accountinit.PoolTypeBasedInitializer;
import guru.bonacci._1985.cassandra.CAccount;
import guru.bonacci._1985.cassandra.CAccountKey;
import guru.bonacci._1985.domain.AccountDetails;
import guru.bonacci._1985.repository.AccountRepository;
import guru.bonacci._1985.repository.CAccountRepository;
import guru.bonacci._1985.repository.PoolRepository;
import guru.bonacci._1985.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepo;
  private final CAccountRepository caccountRepo;
  private final PoolRepository poolRepo;
  private final UserRepository userRepo;
  private final ApplicationContext appContext;
  
  
  public Optional<AccountDetails> getAccount(Long id) {
    return accountRepo.findById(id);
  }

  public List<AccountDetails> getAccountsByPoolId(Long id) {
    return accountRepo.findByPoolId(id);
  }

  @Transactional
  public Optional<AccountDetails> createAccount(Long poolId, Long userId, AccountDetails account) {
    var pool = poolRepo.findById(poolId)
        .orElseThrow(() -> new EntityNotFoundException("Cannot find pool with id " + poolId));
    account.setPool(pool);

    var user = userRepo.findById(userId)
      .orElseThrow(() -> new EntityNotFoundException("Cannot find user with id " + userId));
    account.setUser(user);

    var poolType = pool.getType().toString().toLowerCase(); // maps uppercase enum on lowercase bean name
    var initializer = appContext.getBean(poolType, PoolTypeBasedInitializer.class);
    account.setStartAmount(initializer.determineStartAmount(pool));
    
    var created = accountRepo.saveAndFlush(account);
    //FIXME for transactional behaviour use Kafka as a hub
    caccountRepo.save(new CAccount(new CAccountKey(account.getPool().getName(), account.getName())));

    return Optional.of(created);
  }

  public List<AccountDetails> searchAccounts(Long poolId, String accountName) {
    return accountRepo.findByPoolIdAndNameLike(poolId, "%"+accountName+"%");
  }

  @Transactional
  public void deactivate(Long id) {
    getAccount(id).ifPresent(account -> {
      account.setActive(false);
      
      accountRepo.saveAndFlush(account);
      caccountRepo.deleteById(new CAccountKey(account.getPool().getName(), account.getName())); 
    });
  }
}
