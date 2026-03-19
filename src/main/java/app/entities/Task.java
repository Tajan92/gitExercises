package app.entities;

public class Task {
    int id;
    String name;
    boolean done;
    int userId;

    public Task(int id, String name, boolean done, int userId) {
        this.id = id;
        this.name = name;
        this.done = done;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isDone() {
        return done;
    }
}
