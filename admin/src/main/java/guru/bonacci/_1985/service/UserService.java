package guru.bonacci._1985.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import guru.bonacci._1985.domain.UserInfo;
import guru.bonacci._1985.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repo;

  
  public Optional<UserInfo> getUser(Long id) {
    return repo.findById(id);
  }

  public UserInfo createUser(UserInfo user) {
    return repo.saveAndFlush(user);
  }

  public UserInfo updateUser(UserInfo user) {
    return repo.saveAndFlush(user);
  }
}
