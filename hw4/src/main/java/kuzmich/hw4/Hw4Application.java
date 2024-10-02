package kuzmich.hw4;

import kuzmich.hw4.model.Project;
import kuzmich.hw4.model.Timesheet;
import kuzmich.hw4.repositories.ProjectRepository;
import kuzmich.hw4.repositories.TimesheetRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class Hw4Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Hw4Application.class, args);
		ProjectRepository projectRepository = ctx.getBean(ProjectRepository.class);

		for (int i = 1; i <= 5; i++) {
			Project project = new Project();
			project.setId((long) i);
			project.setProjectName("Project #" + i);
			projectRepository.createProject(project);
		}


		TimesheetRepository timesheetRepository = ctx.getBean(TimesheetRepository.class);

		LocalDate createdAt = LocalDate.now();

		for (int i = 1; i <= 10; i++) {
			createdAt = createdAt.plusDays(1);

			Timesheet timesheet = new Timesheet();
			timesheet.setId((long) i);
			timesheet.setProjectId(ThreadLocalRandom.current().nextLong(1, 6));
			timesheet.setCreatedAt(createdAt);
			timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));

			timesheetRepository.create(timesheet);
		}
	}
}