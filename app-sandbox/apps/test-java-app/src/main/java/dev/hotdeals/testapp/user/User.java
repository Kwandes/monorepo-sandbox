package dev.hotdeals.testapp.user;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @Basic
  @Column(name = "name", length = 160)
  private String name;

  public User(long id, String name)
  {
    this.id = id;
    this.name = name;
  }

  public User()
  {
  }

  public long getId()
  {
    return id;
  }

  public void setId(long id)
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
    User user = (User) o;
    return id == user.id && Objects.equals(name, user.name);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(id, name);
  }
}
