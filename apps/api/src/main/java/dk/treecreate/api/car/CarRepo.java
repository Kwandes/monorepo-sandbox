package dk.treecreate.api.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public
interface CarRepo extends JpaRepository<Car, Long>
{
}
