package chess.dao;

import chess.model.board.Board;
import chess.model.state.State;
import chess.dao.converter.StateToStringConverter;
import chess.dao.converter.StringToStateConverter;

public class FakeStateDao implements StateDao {

    private String name;

    @Override
    public void save(State state) {
        this.name = StateToStringConverter.convert(state);
    }

    @Override
    public State find(Board board) {
        return StringToStateConverter.convert(name, board);
    }

    @Override
    public void delete() {
        name = null;
    }
}