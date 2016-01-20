package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import model.Function;
import model.Project;
import app.command.Command;
import app.command.CommandFactory;

public class Application implements Runnable {

	public static final Application instance = new Application();

	public static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));
	public static PrintWriter out = new PrintWriter(System.out);
	
	public static List<Project> projects = new ArrayList<Project>();
	
	private static boolean alive = true;

	public Application() {
	}

	public void run() {

		while (alive) {
			out.print("> ");
			out.flush();
			String input;
			try {
				input = in.readLine();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			
			CommandFactory factory = new CommandFactory();

			Command command = factory.getCommandFromString(input);

			if (command == null) {
				Function.error(input);
			} else {
				command.execute();
			}
		}

	}

	public static void kill() {
		alive = false;
	}

	public static void main(String[] args) {
		Application.instance.run();
	}

}
