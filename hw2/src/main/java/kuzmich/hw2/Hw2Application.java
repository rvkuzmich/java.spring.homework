package kuzmich.hw2;

import kuzmich.hw2.view.Board;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Hw2Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Hw2Application.class, args);

		Board board = context.getBean(Board.class);

		board.newTicket("Roman", "Kuzmich");
		board.newTicket("Nadezhda", "Grishina");g
	}

}
