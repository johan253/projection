package johan.projector.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Executable;
import java.sql.*;
import java.util.*;
import java.util.function.Consumer;


/**
 * The Driver used for connecting and executing queries to the local SQLite database.
 *
 * @author Johan Hernandez
 * @version 1.2.0
 */
public class DatabaseDriver implements PropertyChangeListener {
    /**
     * The constant PROJECT_TABLE.
     */
// TODO update visibility of static vars, or make static SQL Query vars
    static final String PROJECT_TABLE = "Projects";
    /**
     * The Project id column.
     */
    static final String PROJECT_ID_COLUMN = "project_id";
    /**
     * The Project name column.
     */
    static final String PROJECT_NAME_COLUMN = "name";
    /**
     * The Project description column.
     */
    static final String PROJECT_DESCRIPTION_COLUMN = "description";
    /**
     * The Tasks table.
     */
    static final String TASKS_TABLE = "Tasks";
    /**
     * The Task id column.
     */
    static final String TASK_ID_COLUMN = "task_id";
    /**
     * The Task name column.
     */
    static final String TASK_NAME_COLUMN = "name";
    /**
     * The Task status column.
     */
    static final String TASK_STATUS_COLUMN = "status";
    /**
     * The Task project column.
     */
    static final String TASK_PROJECT_COLUMN = "project_id";
    /**
     * The Singleton instance of this class
     */
    private static DatabaseDriver databaseDriver;
    /**
     * The connection to the local SQLite database
     */
    private Connection myConnection;
    /**
     * The current existing Projects
     */
    // TODO: may get rid of this and replace with methods to execute SQL queries
    private Map<Integer, Project> myProjectMap;
    private Map<String, Consumer<PropertyChangeEvent>> myMappings;

    /**
     * Gets the only instance of this class.
     *
     * @return the instance
     */
    public static DatabaseDriver getInstance() {
        if (databaseDriver == null) {
            databaseDriver = new DatabaseDriver();
        }
        return databaseDriver;
    }

    /**
     * Establishes connection upon instantiation and creates Map of Projects
     */
    private DatabaseDriver () {
        try {
            myConnection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database.db");
        } catch (SQLException e) {
            System.out.println(e);
            System.exit(-1);
        }
        myProjectMap = new HashMap<>();
        try {
            fetchAllData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        myMappings = new HashMap<>();
        setUpMappings();
    }
    private void setUpMappings() {
        myMappings.put(PropertyChanges.TASK_TITLE_CHANGE, this::updateTaskTitle);
        myMappings.put(PropertyChanges.TASK_STATUS_CHANGE, this::updateTaskStatus);
        myMappings.put(PropertyChanges.PROJECT_TITLE_CHANGE, this::updateProjectTitle);
        myMappings.put(PropertyChanges.PROJECT_DESCRIPTION_CHANGE, this::updateProjectDesc);
    }
    private void updateTaskTitle(PropertyChangeEvent theEvent) {
        String oldTitle = (String)theEvent.getOldValue();
        String newTitle = (String)theEvent.getNewValue();
        try {
            PreparedStatement ps = myConnection.prepareStatement("UPDATE Tasks SET name='"+newTitle+"' WHERE name='"+oldTitle+"'");
            ps.addBatch();
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void updateTaskStatus(PropertyChangeEvent theEvent) {

    }
    private void updateProjectTitle(PropertyChangeEvent theEvent) {

    }
    private void updateProjectDesc(PropertyChangeEvent theEvent) {

    }

    /**
     * Gets all the data from the local SQL database, and copies it to myProjectMap.
     *
     * @throws SQLException When database has Illegal data, or may be corrupted
     */
    private void fetchAllData() throws SQLException {
        Statement statement = myConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + PROJECT_TABLE);
        while (resultSet.next()) {
            String title = resultSet.getString(PROJECT_NAME_COLUMN);
            String description = resultSet.getString(PROJECT_DESCRIPTION_COLUMN);
            Integer id = resultSet.getInt(PROJECT_ID_COLUMN);
            myProjectMap.put(id, new Project(title, description));
        }
        resultSet = statement.executeQuery("SELECT * FROM " + TASKS_TABLE);
        while (resultSet.next()) {
            String title = resultSet.getString(TASK_NAME_COLUMN);
            String status = resultSet.getString(TASK_STATUS_COLUMN);
            Integer correspondingProjectID = resultSet.getInt(TASK_PROJECT_COLUMN);
            TaskStatus s;
            s = switch (status) {
                case "UNFINISHED" -> TaskStatus.UNFINISHED;
                case "INPROGRESS" -> TaskStatus.INPROGRESS;
                case "FINISHED" -> TaskStatus.FINISHED;
                default -> throw new SQLDataException("SQL database has illegal data, may be corrupted.");
            };
            myProjectMap.get(correspondingProjectID).addTask(new Task(title, s));
        }
    }

    /**
     * Gets all local Projects.
     *
     * @return all the Projects
     */
    public List<Project> getAllProjects() {
        List<Project> out = new ArrayList<>();
        for (final Integer key : myProjectMap.keySet()) {
            out.add(myProjectMap.get(key));
        }
        return out;
    }

    /**
     * Gets a project with a particular name.
     *
     * @param theName the name
     * @return the project with the given name
     */
    // TODO: may cause problems if there are projects with the same name
    public Project getProject(final String theName) {
        Project out = null;
        for (final Integer key : myProjectMap.keySet()) {
            if (myProjectMap.get(key).getTitle().equals(theName)) {
                out = myProjectMap.get(key);
            }
        }
        return out;
    }

    /**
     * Adds a project and its Tasks to the local SQLite database.
     *
     * @param theProject the project being added
     * @return true if this operation was successful, false otherwise
     */
    public boolean addProject(final Project theProject) {
        boolean out = true;
        List<Task> tasks = theProject.getAllTasks();
        try {
            PreparedStatement statement = myConnection.prepareStatement("INSERT INTO Projects(name, description) VALUES( ?, ?)");
            statement.setString(1, theProject.getTitle());
            statement.setString(2, theProject.getDescription());
            statement.addBatch();
            statement.executeBatch();
            Statement s = myConnection.createStatement();
            ResultSet result = s.executeQuery("SELECT project_id FROM Projects WHERE name='" + theProject.getTitle() + "'");
            int newProjectID = result.getInt(PROJECT_ID_COLUMN);
            myProjectMap.put(newProjectID, theProject);
            for (final Task t : tasks) {
                statement = myConnection.prepareStatement("INSERT INTO Tasks(name, status, project_id) VALUES( ?, ?, ?)");
                statement.setString(1, t.getTitle());
                statement.setString(2, t.getStatus().name());
                statement.setInt(3, newProjectID);
                statement.addBatch();
                statement.executeBatch();
            }
        } catch (SQLException e) {
            System.out.println(e);
            out = false;
        }
        return out;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        myMappings.get(evt.getPropertyName()).accept(evt);
    }
}
