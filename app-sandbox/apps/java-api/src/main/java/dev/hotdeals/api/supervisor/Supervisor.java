package dev.hotdeals.api.supervisor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "supervisor")
public class Supervisor
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "name", length = 160)
    private String name;

    @Basic
    @Column(name = "email", length = 255)
    private String email;

    public Supervisor(String name, String email)
    {
        this.name = name;
        this.email = email;
    }

    public Supervisor()
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

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supervisor that = (Supervisor) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name, email);
    }
}
