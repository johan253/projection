package johan.projector.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * This Class represents a singular Project with Tasks attached to it.
 *
 * @author Johan Hernandez
 * @version 1.1.0
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
    private Map<TaskStatus, List<Task>> myTasks;
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
        myDescription = theDescription;
        myTasks = new HashMap<>();
        myTasks.put(TaskStatus.UNFINISHED, new ArrayList<>());
        myTasks.put(TaskStatus.INPROGRESS, new ArrayList<>());
        myTasks.put(TaskStatus.FINISHED, new ArrayList<>());
        myPcs = new PropertyChangeSupport(this);
    }
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }
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
    public void setDescription(final String theDescription) {
        myPcs.firePropertyChange(PropertyChanges.PROJECT_DESCRIPTION_CHANGE, myDescription, theDescription);
        myDescription = theDescription;
    }
    public List<Task> getAllTasks() {
        List<Task> out = new ArrayList<>();
        for (final TaskStatus s : myTasks.keySet()) {
            out.addAll(myTasks.get(s));
        }
        return out;
    }
//
//    /**
//     * Gets the total number of Tasks this project has
//     *
//     * @return the number of tasks this Project has
//     */
//    public int getTaskCount() {
//        int temp = 0;
//        for (final TaskStatus s : myTasks.keySet()) {
//            temp += myTasks.get(s).size();
//        }
//        return temp;
//    }
    /**
     * Gets the unfinished tasks associated with this Project.
     *
     * @return the unfinished tasks
     */
    public List<Task> getUnfinishedTasks() {
        if (myTasks.get(TaskStatus.UNFINISHED) == null) {
            return null;
        }
        return new ArrayList<>(myTasks.get(TaskStatus.UNFINISHED));
    }

    /**
     * Gets the in progress tasks associated with this Project.
     *
     * @return the in progress tasks
     */
    public List<Task> getInProgressTasks() {
        if (myTasks.get(TaskStatus.INPROGRESS) == null) {
            return null;
        }
        return new ArrayList<>(myTasks.get(TaskStatus.INPROGRESS));
    }

    /**
     * Gets the finished tasks associated with this Project.
     *
     * @return the finished tasks
     */
    public List<Task> getFinishedTasks() {
        if (myTasks.get(TaskStatus.FINISHED) == null) {
            return null;
        }
        return new ArrayList<>(myTasks.get(TaskStatus.FINISHED));
    }

    /**
     * Add a task to this project.
     *
     * @param theTask the task
     */
    public void addTask(final Task theTask) {
        myTasks.get(theTask.getStatus()).add(new Task(theTask));
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
