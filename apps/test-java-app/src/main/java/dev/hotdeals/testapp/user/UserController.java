package dev.hotdeals.testapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController
{
  @Autowired
  UserService userService;

  @GetMapping("/user")
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
  public User getUser() throws Exception
  {
    return userService.getUser(1);
  }
}
