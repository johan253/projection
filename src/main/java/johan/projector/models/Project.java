package johan.projector.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * This Class represents a singular Project with Tasks attached to it.
 *
 * @author Johan Hernandez
 * @version 1.1.1
 */
public class Project {
    /**
     * The title of this Project
     */
    private String myTitle;
    /**
     * The Description of this project
     */
    private String myDescription;
    /**
     * The Tasks associated with this project
     */
    private final Map<TaskStatus, List<Task>> myTasks;
    /**
     * The property change support object to send signals to listeners when certain properties change.
     */
    private final PropertyChangeSupport myPcs;

    /**
     * Instantiates a new Project.
     *
     * @param theTitle the title of the Project
     */
    public Project(final String theTitle) {
        this(theTitle, "");
    }

    /**
     * Instantiates a new Project.
     *
     * @param theTitle       the title of the Project
     * @param theDescription the description of the Project
     */
    public Project(final String theTitle, final String theDescription) {
        myTitle = theTitle;
        String d = theDescription.isBlank() ? "N/A" : theDescription;
        myDescription = d;
        myTasks = new HashMap<>();
        myTasks.put(TaskStatus.UNFINISHED, new ArrayList<>());
        myTasks.put(TaskStatus.INPROGRESS, new ArrayList<>());
        myTasks.put(TaskStatus.FINISHED, new ArrayList<>());
        myPcs = new PropertyChangeSupport(this);
    }

    /**
     * Adds a listener to receive property change events from this object
     *
     * @param theListener the listener object to be added
     */
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }
    /**
     * Removes a listener from receiving property change events from this object
     *
     * @param theListener the listener object to be removed
     */
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(theListener);
    }

    /**
     * Gets the title of this Project.
     *
     * @return the title
     */
    public String getTitle() {
        return myTitle;
    }
    public void setTitle(final String theTitle) {
        myPcs.firePropertyChange(PropertyChanges.PROJECT_TITLE_CHANGE, myTitle, theTitle);
        myTitle = theTitle;
    }

    /**
     * Gets the description of this Project.
     *
     * @return the description
     */
    public String getDescription() {
        return myDescription;
    }

    /**
     * Sets the description of this Project
     *
     * @param theDescription the new description
     */
    public void setDescription(final String theDescription) {
        myPcs.firePropertyChange(PropertyChanges.PROJECT_DESCRIPTION_CHANGE, myDescription, theDescription);
        myDescription = theDescription;
    }

    /**
     * Gets all the Tasks associated with this Project
     *
     * @return all the Tasks
     */
    public List<Task> getAllTasks() {
        List<Task> out = new ArrayList<>();
        for (final TaskStatus s : myTasks.keySet()) {
            out.addAll(myTasks.get(s));
        }
        return out;
    }

    /**
     * Add a task to this project.
     *
     * @param theTask the task
     */
    public void addTask(final Task theTask) {
        myTasks.get(theTask.getStatus()).add(theTask);
    }

    /**
     * Includes the Project name, description, and a list of tasks associated with it.
     *
     * @return a String representation of this Project
     */
    @Override
    public String toString() {
        return getTitle();
        //return "Project: " + myTitle + " - " + myDescription + '\'' + " - Tasks:\n" + myTasks + "\n";
    }
}
