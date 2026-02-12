/**
 * Represents an event task that extends Task, with a description and a time range.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates a new event task with the given description and time range.
     *
     * @param description the description of the task.
     * @param from        the start time as a string.
     * @param to          the end time as a string.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the event task, including type indicator and time range.
     *
     * @return the formatted string of the event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
