package kuzmich;

import kuzmich.aspect.logging.LoggingAutoConfiguration;
import kuzmich.model.*;
import kuzmich.repositories.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

//@EnableDiscoveryClient
@SpringBootApplication
//@Import(LoggingAutoConfiguration.class)
public class TimesheetRestApplication {

	private static final String[] firstNames = {"Roman", "Alexey", "Nikolay", "Oleg", "Sergei"};
	private static final String[] lastNames = {"Kuzmich", "Bobylev", "Artyomov", "Posazhenkov", "Mikhatov"};

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(TimesheetRestApplication.class, args);

		UserRepository userRepository = ctx.getBean(UserRepository.class);
		User admin = new User();
		admin.setLogin("admin");
		admin.setPassword("$2a$12$ShivOAYWDNuUqL3ax6HzE.w8DOUBzMp2hrNF39oJge6h/TYq0ijKq");

		User user = new User();
		user.setLogin("user");
		user.setPassword("$2a$12$4E/cAOYf1YOsbSb9O1mtIeLWBHFAaNrwGVrF7iyz3Xg2hJtODiDgO");

		admin = userRepository.save(admin);
		user = userRepository.save(user);

		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);


		UserRole adminAdminRole = new UserRole();
		adminAdminRole.setUserId(admin.getUserId());
		adminAdminRole.setRoleName("admin");
		userRoleRepository.save(adminAdminRole);

		UserRole adminUserRole = new UserRole();
		adminUserRole.setUserId(admin.getUserId());
		adminUserRole.setRoleName("user");
		userRoleRepository.save(adminUserRole);

		UserRole userUserRole = new UserRole();
		userUserRole.setUserId(user.getUserId());
		userUserRole.setRoleName("user");
		userRoleRepository.save(userUserRole);

		User anonymous = new User();
		anonymous.setLogin("anon");
		anonymous.setPassword("$2a$12$DHItOyBqhzLdf7Y3ZbkUU.cqJtirHnrinQPP7RC31mPPZ/WpqDo6C");
		anonymous = userRepository.save(anonymous);

		User rest = new User();
		rest.setLogin("rest");
		rest.setPassword("$2a$12$h.TahnUtuUSc178WGcsYsuwNZ4WnofOfCEulD02H8vuONt25tq7Cu");
		rest = userRepository.save(rest);

		UserRole restRestRole = new UserRole();
		restRestRole.setUserId(rest.getUserId());
		restRestRole.setRoleName("rest");
		userRoleRepository.save(restRestRole);

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
