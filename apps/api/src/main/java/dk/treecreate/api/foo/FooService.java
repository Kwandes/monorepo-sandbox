package dk.treecreate.api.foo;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FooService
{

  private FooRepo fooRepo;

  public FooService(FooRepo fooRepository)
  {
    this.fooRepo = fooRepository;
  }

  public Optional<Foo> findById(Long id)
  {
    return fooRepo.findById(id);
  }

  public Foo save(Foo foo)
  {
    return fooRepo.save(foo);
  }

  public Iterable<Foo> findAll()
  {
    return fooRepo.findAll();
  }
}
