package kuzmich.hw7;

import kuzmich.hw7.model.Employee;
import kuzmich.hw7.model.Project;
import kuzmich.hw7.model.Timesheet;
import kuzmich.hw7.model.User;
import kuzmich.hw7.repositories.EmployeeRepository;
import kuzmich.hw7.repositories.ProjectRepository;
import kuzmich.hw7.repositories.TimesheetRepository;
import kuzmich.hw7.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class Hw7Application {

	private static final String[] firstNames = {"Roman", "Alexey", "Nikolay", "Oleg", "Sergei"};
	private static final String[] lastNames = {"Kuzmich", "Bobylev", "Artyomov", "Posazhenkov", "Mikhatov"};

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Hw7Application.class, args);

		UserRepository userRepository = ctx.getBean(UserRepository.class);
		User admin = new User();
		admin.setLogin("admin");
		admin.setPassword("$2a$12$ShivOAYWDNuUqL3ax6HzE.w8DOUBzMp2hrNF39oJge6h/TYq0ijKq");

		User user = new User();
		user.setLogin("user");
		user.setPassword("$2a$12$4E/cAOYf1YOsbSb9O1mtIeLWBHFAaNrwGVrF7iyz3Xg2hJtODiDgO");

		admin = userRepository.save(admin);
		user = userRepository.save(user);

		UserRoleRepository  userRoleRepository = ctx.getBean(UserRoleRepository.class);
		UserRole adminAdminRole = new UserRole();
		adminAdminRole.setId(admin.getId());
		adminAdminRole.setRoleName(Role.ADMIN.getName());
		userRoleRepository.save(adminAdminRole);

		UserRole adminUserRole = new UserRole();
		adminUserRole.setId(admin.getId());
		adminUserRole.setRoleName(Role.USER.getName());
		userRoleRepository.save(adminUserRole);

		UserRole userUserRole = new UserRole();
		userUserRole.setId(user.getId());
		userUserRole.setRoleName(Role.USER.getName());
		userRoleRepository.save(userUserRole);

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
