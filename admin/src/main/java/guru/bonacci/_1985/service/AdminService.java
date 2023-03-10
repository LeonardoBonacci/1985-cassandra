package guru.bonacci._1985.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci._1985.domain.AdminUser;
import guru.bonacci._1985.domain.Pool;
import guru.bonacci._1985.repository.AdminRepository;
import guru.bonacci._1985.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

  private final AdminRepository adminRepo;
  private final UserRepository userRepo;


  @Transactional
  public Optional<AdminUser> createAdmin(Long userId, String bankDetails) {
    var user = userRepo.findById(userId)
      .orElseThrow(() -> new EntityNotFoundException("Cannot find user with id " + userId));

    return Optional.of(doCreateAdmin(AdminUser.builder().user(user).bankDetails(bankDetails).build()));
  }

  private AdminUser doCreateAdmin(AdminUser admin) {
    return adminRepo.saveAndFlush(admin);
  }

  public Optional<AdminUser> getAdminByPoolId(Long poolId) {
    return adminRepo.findByPools(Pool.builder().id(poolId).build());
  }

  @Transactional
  public void delete(Long id) { 
    log.info("about to delete admin {}", id);
    var admin = adminRepo.findById(id)
       .orElseThrow(() -> new EntityNotFoundException("Cannot find admin with id " + id));
    
    if (!admin.getPools().isEmpty()) {
      throw new IllegalStateException("Cannot delete while linked to pool");
    }

    log.info("deleting admin {}", id);
    adminRepo.deleteById(id);
    adminRepo.flush();
  }
}
