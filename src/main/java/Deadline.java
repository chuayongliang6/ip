/**
 * Represents a deadline task that extends Task, with a description and a due date.
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Creates a new deadline task with the given description and due date.
     * @param description the description of the task
     * @param by the due date as a string
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns a string representation of the deadline task, including type indicator and due date.
     * @return the formatted string of the deadline task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

}
