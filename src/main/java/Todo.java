/**
 * Represents a todo task that extends Task, with just a description.
 */
public class Todo extends Task {

    /**
     * Creates a new todo task with the given description.
     */
    public Todo(String description) {

        super(description);
    }

    /**
     * Returns a string representation of the todo task, including type indicator.
     *
     * @return the formatted string of the todo task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
