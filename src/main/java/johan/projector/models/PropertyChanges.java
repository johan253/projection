package johan.projector.models;

/**
 * This class holds the string values for property changes that occur
 *
 * @author Johan Hernandez
 * @version 1.0.1
 */
public interface PropertyChanges {
    /**
     * String to use when implying a change to a project title
     */
    String PROJECT_TITLE_CHANGE = "project title change";
    /**
     * String to use when implying a change in project description
     */
    String PROJECT_DESCRIPTION_CHANGE = "project desc change";
    /**
     * String to use when implying a change in task title
     */
    String TASK_TITLE_CHANGE = "task title change";
    /**
     * String to use when implying a change in task status
     */
    String TASK_STATUS_CHANGE = "task status change";
}
