package dev.hotdeals.testapp.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController
{
  @GetMapping("/")
  public ResponseEntity<String> index()
  {
    return new ResponseEntity<>("yeet", HttpStatus.I_AM_A_TEAPOT);
  }
}
