package johan.projector.models;

import java.util.*;

/**
 * This Class represents a singular Project with Tasks attached to it.
 *
 * @author Johan Hernandez
 * @version 1.0.0
 */
public class Project {
    private String myTitle;
    private String myDescription;
    private Map<TaskStatus, List<Task>> myTasks;

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
    }

    /**
     * Gets the title of this Project.
     *
     * @return the title
     */
    public String getTitle() {
        return myTitle;
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
        if (myTasks.get(TaskStatus.IN_PROGRESS) == null) {
            return null;
        }
        return new ArrayList<>(myTasks.get(TaskStatus.IN_PROGRESS));
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
}
