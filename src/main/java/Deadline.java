public class Deadline extends Task {
    protected String by;

    /**
     * Class constructor specifying the description of the deadline task.
     * @param description the string description of the task
     * @param by the deadline of the task
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns a string description which contains the deadline of the task
     * @return a string representation of the deadline
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }
}

