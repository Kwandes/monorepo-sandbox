package dk.treecreate.api.foo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Foo
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  public Foo()
  {

  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Foo foo = (Foo) o;
    return Objects.equals(id, foo.id) && Objects.equals(name, foo.name);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(id, name);
  }

  @Override
  public String toString()
  {
    return "Foo{" +
      "id=" + id +
      ", name='" + name + '\'' +
      '}';
  }
}
