package app;

import java.util.*;

public class Project {

	private final long id;
	private final String name;
	private ArrayList<Task> taskList;

	public Project(long id, String name) {
		this.id = id;
		this.name = name;
		this.taskList = new ArrayList<Task>();
	}

	public long getId() {
		return id;
	}

	public String getDescription() {
		return name;
	}

	public List<Task> getList() {
		return taskList;
	}

	public void addTask(Task tache) {
		this.taskList.add(tache);
	}

	public String getName() {
		return name;
	}

	public void removeTask(Task tache) {
		this.taskList.remove(tache);
	}

}
