package app.controllers;

import app.entities.Task;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.TaskMapper;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TaskController {
    public static void addTask(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");

        String taskName = ctx.formParam("addtask");

        try {
            Task newTask = TaskMapper.addTask(user, taskName, connectionPool);
            List<Task> tasklist = TaskMapper.getAllTasksPerUser(user.getUserId(), connectionPool);
            ctx.attribute("taskList", tasklist);
            ctx.render("/tasks");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }

    }

    public static void done(Context ctx, boolean done, ConnectionPool connectionPool) {

        int taskId = Integer.parseInt(ctx.formParam("task_id"));
        try {
            User user = ctx.sessionAttribute("currentUser");
            TaskMapper.setDoneTo(done, taskId, connectionPool);
            List<Task> tasklist = TaskMapper.getAllTasksPerUser(user.getUserId(), connectionPool);
            ctx.attribute("taskList", tasklist);
            ctx.render("/tasks");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }
    }

    public static void delete(Context ctx, ConnectionPool connectionPool) {
        int taskId = Integer.parseInt(ctx.formParam("task_id"));
        try {
            User user = ctx.sessionAttribute("currentUser");
            TaskMapper.delete( taskId, connectionPool);
            List<Task> tasklist = TaskMapper.getAllTasksPerUser(user.getUserId(), connectionPool);
            ctx.attribute("taskList", tasklist);
            ctx.render("/tasks");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }
    }

    public static void edit(Context ctx, ConnectionPool connectionPool) {
        int taskId = Integer.parseInt(ctx.formParam("task_id"));
        try {
            Task task = TaskMapper.getTaskById(taskId, connectionPool);
            ctx.attribute("task", task);
            ctx.render("edittask.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }


    }

    public static void update(Context ctx, ConnectionPool connectionPool) {
        int taskId = Integer.parseInt(ctx.formParam("task_id"));
        String name = ctx.formParam("task_name");
        User user = ctx.sessionAttribute("currentUser");

        try {
            TaskMapper.update(taskId, name, connectionPool);
            List<Task> tasklist = TaskMapper.getAllTasksPerUser(user.getUserId(), connectionPool);
            ctx.attribute("taskList", tasklist);
            ctx.render("/tasks");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }
    }
}
