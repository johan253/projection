package johan.projector.models;

/**
 * This class represents a Task, that should be displayed with its corresponding Project.
 *
 * @author Johan Hernandez
 * @version 1.0.0
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
        myTitle = theTitle;
        myTaskStatus = TaskStatus.UNFINISHED;
    }
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
}
