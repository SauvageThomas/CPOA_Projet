package app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ListIterator;

import model.Project;
import model.Task;

public class Function {

	public static void error(String input) {
		Application.out.println("I don't know what the command \"" + input
				+ "\" is.");
		Application.out.flush();
	}

	public static void show() {
		for (Project project : Application.projects) {
			Application.out.println(project.getName());
			for (Task task : project.getList()) {
				Application.out.printf("    [%c] %d: %s%n",
						(task.isDone() ? 'x' : ' '), task.getId(),
						task.getDescription());
			}
			Application.out.println();
		}
	}

	public static void addTask(String parameter) {
		String[] args = parameter.split(" ", 2);

		int pos = getPosOf(args[0]);
		if (pos == -1) {
			Application.out.printf(
					"Could not find a project with the name \"%s\".", args[0]);
			Application.out.println();
			return;
		}
		Project project = Application.projects.get(pos);
		project.addTask(new Task(nextId(), args[1], false));
	}

	private static long nextId() {
		return ++Application.lastId;
	}

	private static int getPosOf(String parameter) {
		int pos = -1;
		for (Project project : Application.projects) {
			if (project.getName().equals(parameter)) {
				pos = Application.projects.indexOf(project);
				break;
			}
		}
		return pos;
	}

	public static void addProject(String parameter) {
		Application.projects.add(new Project(parameter));
	}

	public static void today() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");

		String today = formatter.format(new Date());

		boolean found = false;
		for (Project p : Application.projects) {
			for (Task task : p.getList()) {
				if (task.getDeadline() == null) {
					continue;
				}
				if (formatter.format(task.getDeadline()).equals(today)) {

					Application.out.printf("    [%c] %d: %s%n",
							(task.isDone() ? 'x' : ' '), task.getId(),
							task.getDescription());

					found = true;
				}
			}
		}
		if (!found) {
			Application.out.printf("There is no task to do today.");
			Application.out.println();
		}
	}

	public static void removeTask(String parameter) {
		boolean found = false;
		for (Project p : Application.projects) {
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
			Application.out.printf("Could not find a task with the id \"%s\".",
					parameter);
			Application.out.println();
		}

	}

	public static void removeProject(String parameter) {
		int pos = getPosOf(parameter);
		if (pos == -1) {
			Application.out
					.printf("Could not find a project with the name \"%s\".",
							parameter);
			Application.out.println();
		} else {
			Application.projects.remove(pos);
		}

	}

	public static void check(String parameter) {
		setDone(parameter, true);
	}

	private static void setDone(String parameter, boolean b) {
		int id = -1;
		try {
			id = Integer.parseInt(parameter);
		} catch (Exception e) {
			Application.out.println("The id '" + parameter
					+ "' is not a correct id.");
			return;
		}

		id = Integer.parseInt(parameter);
		for (Project project : Application.projects) {
			for (Task task : project.getList()) {
				if (task.getId() == id) {
					task.setDone(b);
					return;
				}
			}
		}
		Application.out.printf("Could not find a task with an ID of %d.", id);
		Application.out.println();
	}

	public static void uncheck(String parameter) {
		setDone(parameter, false);
	}

	public static void quit() {
		Application.kill();

	}

	public static void help() {
		Application.out.println("Commands:");
		Application.out.println("  show");
		Application.out.println("  add project <project name>");
		Application.out.println("  remove project <project name>");
		Application.out.println("  remove task <task ID>");
		Application.out.println("  add task <project name> <task description>");
		Application.out.println("  check <task ID>");
		Application.out.println("  uncheck <task ID>");
		Application.out.println("  deadline <task ID> <date>");
		Application.out.println("  today");
		Application.out.println();

	}

	public static void deadline(String parameter) {
		String[] args = parameter.split(" ", 2);

		boolean found = false;
		for (Project p : Application.projects) {
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
								.println("Error, the date is not conform (Ex : 02-06-16).");
					}

					found = true;
				}
			}
		}
		if (!found) {
			Application.out.printf("Could not find a task with the id \"%s\".",
					args[0]);
			Application.out.println();
		}
	}

}
