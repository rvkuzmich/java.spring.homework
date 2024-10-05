package kuzmich.hw5.services;

import kuzmich.hw5.model.Employee;
import kuzmich.hw5.model.Project;
import kuzmich.hw5.model.Timesheet;
import kuzmich.hw5.repositories.EmployeeRepository;
import kuzmich.hw5.repositories.ProjectRepository;
import kuzmich.hw5.repositories.TimesheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    @Autowired
    private final EmployeeRepository employeeRepository;
    @Autowired
    private final TimesheetRepository timesheetRepository;
    @Autowired
    private final ProjectRepository projectRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Timesheet> getTimesheets(Long id) {
        if (employeeRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Employee id " + id + " doesn't exist");
        }
        return timesheetRepository.findByEmployeeId(id);
    }

    public void addProject(Long id, Long projectId) {
        if (employeeRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Employee id " + id + " doesn't exist");
        }
        if (projectRepository.findById(projectId).isEmpty()) {
            throw new NoSuchElementException("Project id " + projectId + " doesn't exist");
        }
        employeeRepository.findById(id).get().getProjects().add(projectRepository.findById(projectId).get());
        projectRepository.findById(projectId).get().getEmployees().add(employeeRepository.findById(id).get());
    }
}
