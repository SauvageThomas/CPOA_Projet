package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import command.Command;

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
			if (input.equals(Command.quit)) {
				break;
			}
			try {
				Command.valueOf(input.split(" ")[0]);
				execute(Command.valueOf(input));
			} catch (java.lang.IllegalArgumentException e) {
				error(input);
			}
		}
	}

	private void execute(Command commandLine) {
		String[] args = commandLine.toString().split(" ", 2);
		System.out.println(args);
		Command command = Command.valueOf(args[0]);
		switch (command) {
		case show:
			show();
			break;
		case add:
			add(args[1]);
			break;
		case check:
			check(args[1]);
			break;
		case uncheck:
			uncheck(args[1]);
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

	private void add(String commandLine) {
		String[] subcommandRest = commandLine.split(" ", 2);
		String subcommand = subcommandRest[0];
		if (subcommand.equals("project")) {
			addProject(subcommandRest[1]);
		} else if (subcommand.equals("task")) {
			String[] projectTask = subcommandRest[1].split(" ", 2);
			addTask(projectTask[0], projectTask[1]);
		}
	}

	private void addProject(String name) {
		tasks.put(name, new ArrayList<Task>());
	}

	private void addTask(String project, String description) {
		List<Task> projectTasks = tasks.get(project);
		if (projectTasks == null) {
			out.printf("Could not find a project with the name \"%s\".",
					project);
			out.println();
			return;
		}
		projectTasks.add(new Task(nextId(), description, false));
	}

	private void check(String idString) {
		setDone(idString, true);
	}

	private void uncheck(String idString) {
		setDone(idString, false);
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
