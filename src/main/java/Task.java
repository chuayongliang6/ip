/**
 * Represents a basic task with a description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with the given description, initially not done.
     *
     * @param description the description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns an icon representing the task's completion status.
     *
     * @return "X" if done, otherwise a space.
     */
    public String getStatusIcon() {
        // Mark task as done using "X"
        return (isDone ? "X" : " ");
    }

    /**
     * Marks this task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Returns a string representation of the task, including status icon and description.
     *
     * @return the formatted string of the task.
     */
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
