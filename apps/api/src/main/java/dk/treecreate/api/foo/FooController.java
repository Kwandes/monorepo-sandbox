package dk.treecreate.api.foo;

import dk.treecreate.api.foo.dto.FooDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/api/foos")
public class FooController {

  private FooService fooService;

  public FooController(FooService fooService) {
    this.fooService = fooService;
  }

  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping(value = "/{id}")
  public FooDto findOne(@PathVariable Long id) {
    Foo entity = fooService.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    return convertToDto(entity);
  }

  @GetMapping
  public Collection<FooDto> findAll() {
    Iterable<Foo> foos = this.fooService.findAll();
    List<FooDto> fooDtos = new ArrayList<>();
    foos.forEach(p -> fooDtos.add(convertToDto(p)));
    return fooDtos;
  }

  protected FooDto convertToDto(Foo entity) {
    return new FooDto(entity.getId(), entity.getName());
  }
}
