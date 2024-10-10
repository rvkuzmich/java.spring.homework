package kuzmich.hw6;

import kuzmich.hw6.model.Employee;
import kuzmich.hw6.model.Project;
import kuzmich.hw6.model.Timesheet;
import kuzmich.hw6.repositories.EmployeeRepository;
import kuzmich.hw6.repositories.ProjectRepository;
import kuzmich.hw6.repositories.TimesheetRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class Hw6Application {

	private static final String[] firstNames = {"Roman", "Alexey", "Nikolay", "Oleg", "Sergei"};
	private static final String[] lastNames = {"Kuzmich", "Bobylev", "Artyomov", "Posazhenkov", "Mikhatov"};

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Hw6Application.class, args);
		ProjectRepository projectRepository = ctx.getBean(ProjectRepository.class);

		for (int i = 1; i <= 5; i++) {
			Project project = new Project();
			project.setProjectName("Project #" + i);
			projectRepository.save(project);
		}

		EmployeeRepository employeeRepository = ctx.getBean(EmployeeRepository.class);
		for (int i = 1; i <= 5; i++) {
			Employee employee = new Employee();
			employee.setFirstName(firstNames[i-1]);
			employee.setLastName(lastNames[i-1]);
			employeeRepository.save(employee);
		}

		TimesheetRepository timesheetRepository = ctx.getBean(TimesheetRepository.class);

		LocalDate createdAt = LocalDate.now();

		for (int i = 1; i <= 10; i++) {
			createdAt = createdAt.plusDays(1);

			Timesheet timesheet = new Timesheet();
			timesheet.setProjectId(ThreadLocalRandom.current().nextLong(1, 6));
			timesheet.setCreatedAt(createdAt);
			timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));
			timesheet.setEmployeeId(ThreadLocalRandom.current().nextLong(1, 6));

			timesheetRepository.save(timesheet);
		}
	}
}
