package dev.hotdeals.api.student;

import dev.hotdeals.api.exceptionhandling.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("student")
public class StudentController
{
    @Autowired
    private StudentRepo studentRepo;

    @GetMapping("")
    public List<Student> findAll()
    {
        return studentRepo.findAll();
    }

    @GetMapping("/{id}")
    public Student findOne(@PathVariable Long id)
    {
        return studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Failed to find an student with an ID of " + id));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@RequestBody Student newStudent)
    {
        return studentRepo.save(newStudent);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Student update(@RequestBody Student newStudent, @PathVariable Long id)
    {
        return studentRepo.findById(id)
                .map(student -> {
                    student.setName(newStudent.getName());
                    student.setEmail(newStudent.getEmail());
                    student.setSupervisor(newStudent.getSupervisor());
                    return studentRepo.save(student);
                })
                .orElseGet(() -> {
                    newStudent.setId(id);
                    return studentRepo.save(newStudent);
                });
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id)
    {
        if (!studentRepo.existsById(id))
        {
            throw new ResourceNotFoundException("Failed to find an student with an ID of " + id);
        }
        studentRepo.deleteById(id);
    }

    @GetMapping("/uml")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String uml()
    {
        return new StudentUml().toString();
    }
}
