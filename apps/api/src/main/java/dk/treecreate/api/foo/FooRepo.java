package dk.treecreate.api.foo;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface FooRepo extends PagingAndSortingRepository<Foo, Long>
{
}
