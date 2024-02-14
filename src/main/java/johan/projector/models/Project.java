package johan.projector.models;

import java.util.*;

public class Project {
    private String myTitle;
    private String myDescription;
    private Map<TaskStatus, List<Task>> myTasks;
    public Project(final String theTitle) {
        this(theTitle, "");
    }
    public Project(final String theTitle, final String theDescription) {
        myTitle = theTitle;
        myDescription = theDescription;
        myTasks = new HashMap<>();
    }
}
