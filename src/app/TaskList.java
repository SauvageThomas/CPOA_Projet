package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import command.Argument;
import command.Command;
import command.CommandFactory;
import command.KeyWord;

public final class TaskList implements Runnable {
	private static final String QUIT = "quit";

	private final Map<String, List<Task>> tasks = new LinkedHashMap<>();
	private final BufferedReader in;
	private final PrintWriter out;
	private final PrintWriter err;

	private long lastId = 0;

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		PrintWriter err = new PrintWriter(System.err);
		new TaskList(in, out, err).run();
	}

	public TaskList(BufferedReader reader, PrintWriter writer, PrintWriter err) {
		this.in = reader;
		this.out = writer;
		this.err = err;
	}

	public void run() {
		while (true) {
			out.print("> ");
			out.flush();
			String input;
			try {
				input = in.readLine();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if (input.equals(KeyWord.quit)) {
				break;
			}
			CommandFactory factory = new CommandFactory();

			Command command = factory.getCommandFromString(input);

			if (command == null) {
				error(input);
			} else {
				execute(command);
			}

		}
	}

	private void execute(Command command) {
		switch (command.getKeyWord()) {
		case show:
			show();
			break;
		case add:
			add(command);
			break;
		case check:
			check(command.getParameter());
			break;
		case uncheck:
			uncheck(command.getParameter());
			break;
		case help:
			help();
			break;
		default:
			error(command.toString());
			break;
		}
	}

	private void show() {
		for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
			out.println(project.getKey());
			for (Task task : project.getValue()) {
				out.printf("    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '),
						task.getId(), task.getDescription());
			}
			out.println();
		}
	}

	private void add(Command command) {
		switch (command.getArg()) {
		case project:
			addProject(command.getParameter());
			break;
		case task:
			addTask(command.getParameter());
			break;
		default:
			err.println("This should not be seen");
			break;
		}
	}

	private void addProject(String name) {
		tasks.put(name, new ArrayList<Task>());
	}

	private void addTask(String parameter) {
		String[] args = parameter.split(" ", 2);
		List<Task> projectTasks = tasks.get(args[0]);
		if (projectTasks == null) {
			out.printf("Could not find a project with the name \"%s\".",
					args[0]);
			out.println();
			return;
		}
		projectTasks.add(new Task(nextId(), args[1], false));
	}

	private void check(String parameter) {
		System.out.println(parameter);
		setDone(parameter, true);
	}

	private void uncheck(String parameter) {
		setDone(parameter, false);
	}

	private void setDone(String idString, boolean done) {
		int id = Integer.parseInt(idString);
		for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
			for (Task task : project.getValue()) {
				if (task.getId() == id) {
					task.setDone(done);
					return;
				}
			}
		}
		out.printf("Could not find a task with an ID of %d.", id);
		out.println();
	}

	private void help() {
		out.println("Commands:");
		out.println("  show");
		out.println("  add project <project name>");
		out.println("  add task <project name> <task description>");
		out.println("  check <task ID>");
		out.println("  uncheck <task ID>");
		out.println();
	}

	private void error(String command) {
		out.println("I don't know what the command \"" + command + "\" is.");
		out.flush();
	}

	private long nextId() {
		return ++lastId;
	}
}
