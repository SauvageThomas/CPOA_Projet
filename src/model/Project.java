package model;

import java.util.*;

/**
 * The class Project is the model of projects created in the application
 *
 */
public class Project {

	private final String name;
	private ArrayList<Task> taskList;

	/**
	 * 
	 * The 	Project constructor simply permits to create a Project with an id and a name.
	 * @param name
	 */
	public Project(String name) {
		this.name = name;
		this.taskList = new ArrayList<Task>();
	}



	/**
	 *  This method returns the list of tasks include in the project
	 * @return taskList
	 */
	public List<Task> getList() {
		return taskList;
	}
	
	/**
	 *  This method add an object Task to the project
	  */
	public void addTask(Task tache) {
		this.taskList.add(tache);
	}

	/**
	 *  This method return the name of the project
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 *  This method remove a Task from the project
	 */
	public void removeTask(Task tache) {
		this.taskList.remove(tache);
	}

}
