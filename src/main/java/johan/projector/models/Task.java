package johan.projector.models;

/**
 * This class represents a Task, that should be displayed with its corresponding Project.
 *
 * @author Johan Hernandez
 * @version 1.0.2
 */
public class Task {
    /**
     * Stores the status of this Task
     */
    private TaskStatus myTaskStatus;
    /**
     * Stores the task as a String
     */
    private final String myTitle;

    /**
     * Constructor accepts a String to be assigned to this Task.
     *
     * @param theTitle is the title of the task being created
     */
    public Task(final String theTitle) {
        this(theTitle, TaskStatus.UNFINISHED);
    }

    /**
     * Constructor that allows you to preset the Task Status
     *
     * @param theTitle the title of the task
     * @param theStatus the status of the task
     */
    public Task(final String theTitle, final TaskStatus theStatus) {
        myTitle = theTitle;
        myTaskStatus = theStatus;
    }

    /**
     * Creates a new Task with the same fields as the given Task
     *
     * @param otherTask the Task being copied
     */
    public Task(final Task otherTask) {
        myTitle = otherTask.getTitle();
        myTaskStatus = otherTask.getStatus();
    }

    /**
     * Gets the status of this Task, whether it is unfinished, in progress, or finished.
     *
     * @return the task status of this Task
     */
    public TaskStatus getStatus() {
        return myTaskStatus;
    }

    /**
     * Gets the title associated with this Task
     *
     * @return this tasks title as a String
     */
    public String getTitle() {
        return myTitle;
    }

    /**
     * Sets the status of this Task to "In Progress"
     */
    public void setInProgress() {
        myTaskStatus = TaskStatus.IN_PROGRESS;
    }

    /**
     * Sets the status of this Task to "Finished".
     */
    public void setFinished() {
        myTaskStatus = TaskStatus.FINISHED;
    }

    /**
     * A string containing the title of the Task, along with its Status
     *
     * @return a String representation of a Task
     */
    @Override
    public String toString() {
        return "Task: " + myTitle + " (" + myTaskStatus + ")\n";
    }
}
