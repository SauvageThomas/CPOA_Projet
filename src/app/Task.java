package app;

import java.util.Date;


/**
 * The class Task is the model of Tasks created in the application
 *
 */
public final class Task {
	private final long id;
	private final String description;
	private boolean done;
	private Date deadline;

	/**
	 *  The Task constructor simply permits to create a Task with an id, a description and
	 *  a boolean done indicating if the task has already been done or not.
	 * @param id
	 * @param description
	 * @param done
	 */
	public Task(long id, String description, boolean done) {
		this.id = id;
		this.description = description;
		this.done = done;
		this.setDeadline(null);
	}

	
	/**
	 *  This method returns the id of the task
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 *  This method returns the description of the task
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 *  This method indicates if the task is done or not
	 * @return done
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * This method modify the value of the boolean to indicate if
	 * the task has already been done or not.
	 * @param done
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	/**
	 *  This method returns the deadline of the task
	 * @return deadline
	 */
	public Date getDeadline() {
		return deadline;
	}
	
	/**
	 * This method modify the deadline of the task.
	 * @param deadline
	 */
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
}
