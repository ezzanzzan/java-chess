package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.model.piece.Piece;
import chess.model.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SquareDaoImplTest {

    private final String url = "jdbc:mysql://localhost:3306/chess";
    private final String user = "user";
    private final String password = "password";

    @DisplayName("데이터가 저장되는지 확인한다.")
    @Test()
    void save() {
        SquareDaoImpl squareDaoImpl = new SquareDaoImpl(new FakeDataSource());
        squareDaoImpl.delete();
        squareDaoImpl.save(Position.from("a1"), Piece.getPiece("BLACK_KING"));

        assertThat(squareDaoImpl.find().get("a1")).isNotNull();
    }

    @DisplayName("데이터가 삭제되는지 확인한다.")
    @Test()
    void delete() {
        SquareDaoImpl squareDaoImpl = new SquareDaoImpl(new FakeDataSource());
        squareDaoImpl.save(Position.from("a2"), Piece.getPiece("BLACK_KING"));
        squareDaoImpl.delete();

        assertThat(squareDaoImpl.find().size()).isEqualTo(0);
    }

    @DisplayName("데이터가 최신화 되는지 확인한다.")
    @Test()
    void update() {
        SquareDaoImpl squareDaoImpl = new SquareDaoImpl(new FakeDataSource());
        squareDaoImpl.save(Position.from("a1"), Piece.getPiece("BLACK_KING"));
        squareDaoImpl.save(Position.from("a1"), Piece.getPiece("BLACK_PAWN"));

        assertThat(squareDaoImpl.find().get("a1")).isEqualTo("BLACK_PAWN");
    }
}