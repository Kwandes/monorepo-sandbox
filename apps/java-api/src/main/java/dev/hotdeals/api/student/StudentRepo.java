package dev.hotdeals.api.student;

import dev.hotdeals.api.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long>
{
}
