import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.mycompany.userinputout.Task; // Ensure this matches your package name!

public class TaskTest {

    @Test
    public void testCheckTaskDescriptionSuccess() {
        // Tests a description that is UNDER 50 characters
        Task task = new Task("Login Feature", 0, "Create Login to authenticate users", "Robyn Harrison", 8, "To Do");
        assertTrue(task.checkTaskDescription());
    }

    @Test
    public void testCheckTaskDescriptionFailure() {
        // Tests a description that is OVER 50 characters
        Task task = new Task("Login Feature", 0, "This description is intentionally made to be way too long so that it exceeds the fifty character limit set by the POE", "Robyn Harrison", 8, "To Do");
        assertFalse(task.checkTaskDescription());
    }

    @Test
    public void testCreateTaskIDSuccess() {
        // Tests if the exact string matches the expected output for the test data
        Task task1 = new Task("Login Feature", 0, "Create Login", "Robyn Harrison", 8, "To Do");
        assertEquals("LO:0:SON", task1.createTaskID());
        
        Task task2 = new Task("Add Task Feature", 1, "Create Add Task feature", "Mike Smith", 10, "Doing");
        assertEquals("AD:1:ITH", task2.createTaskID());
    }

    @Test
    public void testReturnTotalHours() {
        // Sets up test tasks and calculates the accumulated hours
        Task task1 = new Task("Login Feature", 0, "Create Login", "Robyn Harrison", 8, "To Do");
        Task task2 = new Task("Add Task Feature", 1, "Create Add Task", "Mike Smith", 10, "Doing");
        Task task3 = new Task("Report Feature", 2, "Create Report", "Edward Harrison", 12, "Done");
        Task task4 = new Task("Array Feature", 3, "Create Arrays", "Glenda Craddock", 55, "To Do");
        Task task5 = new Task("Another Feature", 4, "Create Another", "Samantha Paulson", 4, "Doing");

        // Manually adding the hours as they would be in your for-loop
        int totalHours = task1.returnTotalHours() + 
                         task2.returnTotalHours() + 
                         task3.returnTotalHours() + 
                         task4.returnTotalHours() + 
                         task5.returnTotalHours();

        // 8 + 10 + 12 + 55 + 4 = 89
        assertEquals(89, totalHours);
    }
}