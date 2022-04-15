package chess.controller;

import static spark.Spark.get;
import static spark.Spark.post;

import chess.domain.GameManager;
import chess.service.GameService;
import chess.dao.PieceDaoImpl;
import chess.dao.TurnDaoImpl;
import chess.dto.MoveDto;
import chess.util.DataSource;
import chess.util.DataSourceImpl;
import chess.util.JdbcContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class GameController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GameService gameService = gameService();

    public void run() {

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return render(model, "index.html");
        });

        get("/start", (request, response) ->
                objectMapper.writeValueAsString(gameService.start())
        );

        post("/move", (request, response) -> {
                    MoveDto moveDto = objectMapper.readValue(request.body(), MoveDto.class);
                    return objectMapper.writeValueAsString(gameService.move(moveDto));
                }
        );

        get("/status", (request, response) ->
                objectMapper.writeValueAsString(gameService.status()));

        get("/load", (request, response) ->
                objectMapper.writeValueAsString(gameService.load()));
    }

    private String render(Map<String, Object> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
    }

    private GameService gameService() {
        DataSource dataSource = new DataSourceImpl();
        JdbcContext jdbcContext = new JdbcContext(dataSource);
        return new GameService(new GameManager(), new PieceDaoImpl(jdbcContext), new TurnDaoImpl(jdbcContext));
    }
}