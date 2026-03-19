package app.controllers;

import app.entities.Task;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.TaskMapper;
import app.persistence.UserMapper;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;


import java.util.List;

public class UserController {

    public static void login(Context ctx, ConnectionPool connectionPool) {

        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        try {
            User user = UserMapper.login(username, password, connectionPool);
            ctx.sessionAttribute("currentUser", user);
            // Get tasks to current user
            List<Task> tasklist = TaskMapper.getAllTasksPerUser(user.getUserId(), connectionPool);
            ctx.attribute("taskList", tasklist);
            ctx.render("/tasks");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }
    }

    public static void createuser(Context ctx, ConnectionPool connectionPool) {
        String username = ctx.formParam("username");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");

        // Validering af password (match)
        if (!password1.equals(password2)) {
            ctx.attribute("message", "Dine password matcher ikke");
            ctx.render("createuser.html");
        } else {

            try {
                UserMapper.createuser(username, password1, connectionPool);
                ctx.attribute("message", "Du er nu oprettet. Log på for at komme i gang.");
                ctx.render("index.html");
            } catch (DatabaseException e) {
                ctx.attribute("message", e.getMessage());
                ctx.render("createuser.html");
            }
        }

    }

    public static void logout(Context ctx) {
        // Invalidate session
        ctx.req().getSession().invalidate();

        ctx.redirect("/");
    }
}
