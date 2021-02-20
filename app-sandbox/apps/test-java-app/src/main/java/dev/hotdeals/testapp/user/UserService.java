package dev.hotdeals.testapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
  @Autowired
  UserRepo userRepo;
  public User getUser(long id) throws Exception
  {
    User user = userRepo.findById(id).orElse(null);
    if (user == null) throw new Exception("Bitch not found");
    else return user;
  }
}
