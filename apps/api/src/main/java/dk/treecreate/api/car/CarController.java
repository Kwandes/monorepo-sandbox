package dk.treecreate.api.car;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
class CarController
{
  private CarRepo carRepo;

  public CarController(CarRepo carRepo)
  {
    this.carRepo = carRepo;
  }

  @GetMapping("/cars")
  public Collection<Car> coolCars()
  {
    return carRepo.findAll().stream()
      .filter(this::isCool)
      .collect(Collectors.toList());
  }

  private boolean isCool(Car car)
  {
    return !car.getName().equals("AMC Gremlin") &&
      !car.getName().equals("Triumph Stag") &&
      !car.getName().equals("Ford Pinto") &&
      !car.getName().equals("Yugo GV");
  }
}
