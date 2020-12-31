package dev.hotdeals.api.supervisor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SupervisorRepo extends JpaRepository<Supervisor, Long>
{
}
