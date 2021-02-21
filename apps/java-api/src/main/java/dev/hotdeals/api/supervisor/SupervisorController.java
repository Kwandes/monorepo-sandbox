package dev.hotdeals.api.supervisor;

import dev.hotdeals.api.exceptionhandling.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("supervisor")
public class SupervisorController
{
    @Autowired
    private SupervisorRepo supervisorRepo;

    @GetMapping("")
    public List<Supervisor> findAll()
    {
        return supervisorRepo.findAll();
    }

    @GetMapping("/{id}")
    public Supervisor findOne(@PathVariable Long id)
    {
        return supervisorRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Failed to find an supervisor with an ID of " + id));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Supervisor create(@RequestBody Supervisor newSupervisor)
    {
        return supervisorRepo.save(newSupervisor);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Supervisor update(@RequestBody Supervisor newSupervisor, @PathVariable Long id)
    {
        return supervisorRepo.findById(id)
                .map(supervisor -> {
                    supervisor.setName(newSupervisor.getName());
                    supervisor.setEmail(newSupervisor.getEmail());
                    return supervisorRepo.save(supervisor);
                })
                .orElseGet(() -> {
                    newSupervisor.setId(id);
                    return supervisorRepo.save(newSupervisor);
                });
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id)
    {
        if (!supervisorRepo.existsById(id))
        {
            throw new ResourceNotFoundException("Failed to find an supervisor with an ID of " + id);
        }
        supervisorRepo.deleteById(id);
    }

    @GetMapping("/uml")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String uml()
    {
        return new SupervisorUml().toString();
    }
}

