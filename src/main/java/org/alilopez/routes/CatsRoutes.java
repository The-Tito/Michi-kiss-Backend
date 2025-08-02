package org.alilopez.routes;

import io.javalin.Javalin;
import org.alilopez.controller.CatsController;

public class CatsRoutes {
    private final CatsController catsController;
    public CatsRoutes(CatsController catsController) {
        this.catsController = catsController;
    }

    public void catsRoutes(Javalin app) {
        app.post("/cats", catsController::create);
    }
}
