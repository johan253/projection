package johan.projector.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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
    private String myTitle;
    /**
     * Project change support alerts listeners of this object when a certain property changes
     */
    private final PropertyChangeSupport myPcs;

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
        myPcs = new PropertyChangeSupport(this);
    }

    /**
     * Creates a new Task with the same fields as the given Task
     *
     * @param otherTask the Task being copied
     */
    public Task(final Task otherTask) {
        this(otherTask.getTitle(), otherTask.getStatus());
    }

    /**
     * Adds a listener to receive property change events
     *
     * @param theListener the listener to be added
     */
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }

    /**
     * Removes a listener from receiving property change events
     *
     * @param theListener the listener to be removed
     */
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(theListener);
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
     * Sets the title of this Task
     *
     * @param theTitle the new title
     */
    public void setTitle(final String theTitle) {
        myPcs.firePropertyChange(PropertyChanges.TASK_TITLE_CHANGE, myTitle, theTitle);
        myTitle = theTitle;
    }

    /**
     * Sets the status of this Task to the given status
     *
     * @param theStatus is the new status of this Task
     */
    public void setStatus(final TaskStatus theStatus) {
        myPcs.firePropertyChange(PropertyChanges.TASK_STATUS_CHANGE, myTitle, theStatus);
        myTaskStatus = theStatus;
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
