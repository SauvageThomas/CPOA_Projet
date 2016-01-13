package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import app.command.Command;
import app.command.CommandFactory;

public final class TaskList implements Runnable {

	private final List<Project> projects = new ArrayList<Project>();
	private final BufferedReader in;
	private final PrintWriter out;
	private final PrintWriter err;
	private boolean alive = true;

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
		case remove:
			remove(command);
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
		case quit:
			alive = false;
			break;
		case deadline:
			setDeadline(command.getParameter());
			break;
		case today:
			today();
			break;
		default:
			error(command.toString());
			break;
		}
	}

	private void today() {
		
		SimpleDateFormat formatter = new SimpleDateFormat(
				"dd-MM-yy");
		
		String today = formatter.format(new Date());
		
		
		boolean found = false;
		for (Project p : projects) {
			for (Task task : p.getList()) {
				if (formatter.format(task.getDeadline()).equals(today)) {
					
					out.printf("    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '),
							task.getId(), task.getDescription());
					
					found = true;
				}
			}
		}
		if (!found) {
			out.printf("There is no task to do today.");
			out.println();
		}
	}

	private void setDeadline(String parameter) {
		String[] args = parameter.split(" ", 2);

		boolean found = false;
		for (Project p : projects) {
			for (Task task : p.getList()) {
				if (String.valueOf(task.getId()).equals(args[0])) {

					SimpleDateFormat formatter = new SimpleDateFormat(
							"dd-MM-yy");
					Date date;
					try {
						date = formatter.parse(args[1]);
						task.setDeadline(date);
					} catch (ParseException e) {
						System.out
								.println("Error, the date is not conform (Ex : 02-06-16");
					}

					found = true;
				}
			}
		}
		if (!found) {
			out.printf("Could not find a task with the id \"%s\".", args[0]);
			out.println();
		}

	}

	private void show() {
		for (Project project : projects) {
			out.println(project.getName());
			for (Task task : project.getList()) {
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

	private void remove(Command command) {
		switch (command.getArg()) {
		case project:
			removeProject(command.getParameter());
			break;
		case task:
			removeTask(command.getParameter());
			break;
		default:
			err.println("This should not be seen");
			break;
		}
	}

	private void removeProject(String parameter) {
		int pos = getPosOf(parameter);
		if (pos == -1) {
			out.printf("Could not find a project with the name \"%s\".",
					parameter);
			out.println();
		} else {
			projects.remove(pos);
		}
	}

	private void removeTask(String parameter) {

		boolean found = false;
		for (Project p : projects) {
			ListIterator<Task> iterator = p.getList().listIterator();
			while (iterator.hasNext()) {
				Task task = iterator.next();
				if (String.valueOf(task.getId()).equals(parameter)) {
					iterator.remove();
					found = true;
				}
			}
		}
		if (!found) {
			out.printf("Could not find a task with the id \"%s\".", parameter);
			out.println();
		}

	}

	private void addProject(String name) {
		projects.add(new Project(name));
	}

	private int getPosOf(String name) {
		int pos = -1;
		for (Project project : projects) {
			if (project.getName().equals(name)) {
				pos = projects.indexOf(project);
				break;
			}
		}
		return pos;
	}

	private void addTask(String parameter) {
		String[] args = parameter.split(" ", 2);

		int pos = getPosOf(args[0]);
		if (pos == -1) {
			out.printf("Could not find a project with the name \"%s\".",
					args[0]);
			out.println();
			return;
		}
		Project project = projects.get(pos);
		project.addTask(new Task(nextId(), args[1], false));
	}

	private void check(String parameter) {
		setDone(parameter, true);
	}

	private void uncheck(String parameter) {
		setDone(parameter, false);
	}

	private void setDone(String idString, boolean done) {
		
		int id = -1;
		try {
			id = Integer.parseInt(idString);
		} catch (Exception e) {
			out.println("The id '" + idString + "' is not a correct id.");
			return;
		}
		
		id = Integer.parseInt(idString);
		for (Project project : projects) {
			for (Task task : project.getList()) {
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
		out.println("  remove project <project name>");
		out.println("  remove task <task ID>");
		out.println("  add task <project name> <task description>");
		out.println("  check <task ID>");
		out.println("  uncheck <task ID>");
		out.println("  deadline <ID> <date>");
		out.println("  today");
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
