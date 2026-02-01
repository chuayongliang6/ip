public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        // Mark task as done using "X"
        return (isDone ? "X" : " ");
    }
    public void markAsDone() {

        this.isDone = true;
    }

    public String toString() {

        return "[" + getStatusIcon() + "] " + description;
    }
}
