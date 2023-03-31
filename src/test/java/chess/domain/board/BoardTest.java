package chess.domain.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Team;
import chess.exception.PathBlockedException;
import chess.exception.PawnMoveDiagonalException;
import chess.exception.PawnMoveForwardException;
import chess.exception.TargetSameTeamException;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BoardTest {
    private Board board;

    private static Stream<Arguments> boardTestProvider() {
        return Stream.of(
                Arguments.of(
                        0,
                        PieceType.ROOK
                ),
                Arguments.of(
                        1,
                        PieceType.KNIGHT
                ),
                Arguments.of(
                        30,
                        PieceType.EMPTY

                ),
                Arguments.of(
                        63,
                        PieceType.ROOK
                )
        );
    }

    @BeforeEach
    void setUp() {
        Map<Square, Piece> initializeBoard = new BoardFactory().generateBoard();
        board = new Board(initializeBoard);
    }

    @DisplayName("체스 게임을 할 수 있는 체스판을 초기화한다.")
    @ParameterizedTest(name = "{displayName} [{index}]")
    @MethodSource("boardTestProvider")
    void Should_Create_When_Board(int index, PieceType pieceType) {
        assertThat(board.getPieces().get(index).getPieceType()).isEqualTo(pieceType);
    }

    @DisplayName("Source부터 Target까지의 경로 상에 피스가 없을 경우 이동할 수 있다.")
    @Test
    void Should_Move_When_NoPieceOnPath() {
        Square source = new Square(File.A, Rank.TWO);
        Square target = new Square(File.A, Rank.THREE);

        Assertions.assertDoesNotThrow(() -> board.move(source, target));
    }

    @DisplayName("Source부터 Target까지의 경로 상에 피스가 없을 경우 이동할 수 있다.")
    @Test
    void Should_Move_When_NoPieceOnPath2() {
        Square source = new Square(File.B, Rank.ONE);
        Square target = new Square(File.A, Rank.THREE);

        Assertions.assertDoesNotThrow(() -> board.move(source, target));
    }

    @DisplayName("Source부터 Target까지의 경로 상에 피스가 있을 경우 이동할 수 없다.")
    @Test
    void Should_DontMove_When_PieceOnPath() {
        Square source = new Square(File.A, Rank.ONE);
        Square target = new Square(File.A, Rank.TWO);

        assertThatThrownBy(() -> board.move(source, target))
                .isInstanceOf(TargetSameTeamException.class);
    }

    @DisplayName("Source부터 Target까지의 경로 상에 피스가 있을 경우 이동할 수 없다.")
    @Test
    void Should_DontMove_When_PieceOnPath2() {
        Square source = new Square(File.A, Rank.ONE);
        Square target = new Square(File.A, Rank.FIVE);

        board.move(new Square(File.A, Rank.TWO), new Square(File.A, Rank.FOUR));

        assertThatThrownBy(() -> board.move(source, target))
                .isInstanceOf(PathBlockedException.class);
    }

    @DisplayName("Target에 상대 피스가 있을 경우 이동할 수 있다.")
    @Test
    void Should_Move_When_OtherCampPieceOnTarget() {
        Square source = new Square(File.B, Rank.SIX);
        Square target = new Square(File.A, Rank.SEVEN);

        board.move(new Square(File.B, Rank.TWO), new Square(File.B, Rank.FOUR));
        board.move(new Square(File.B, Rank.FOUR), new Square(File.B, Rank.FIVE));
        board.move(new Square(File.B, Rank.FIVE), new Square(File.B, Rank.SIX));

        Assertions.assertDoesNotThrow(() -> board.move(source, target));
    }

    @DisplayName("폰이 전진 방향 이동을 할 때 Target 위치에 말이 있을 경우 움직일 수 없다.")
    @Test
    void Should_ThrowException_When_PawnForwardWithTargetNotEmpty() {
        Square source = new Square(File.B, Rank.SIX);
        Square target = new Square(File.B, Rank.SEVEN);

        board.move(new Square(File.B, Rank.TWO), new Square(File.B, Rank.FOUR));
        board.move(new Square(File.B, Rank.FOUR), new Square(File.B, Rank.FIVE));
        board.move(new Square(File.B, Rank.FIVE), new Square(File.B, Rank.SIX));

        assertThatThrownBy(() -> board.move(source, target))
                .isInstanceOf(PawnMoveForwardException.class);
    }

    @DisplayName("폰이 대각선 방향 이동을 할 때 Target 위치에 같은 팀의 말이면 움직일 수 없다.")
    @Test
    void Should_ThrowException_When_PawnDiagonalWithTargetSameTeam() {
        Square source = new Square(File.C, Rank.TWO);
        Square target = new Square(File.B, Rank.THREE);

        board.move(new Square(File.B, Rank.TWO), new Square(File.B, Rank.THREE));

        assertThatThrownBy(() -> board.move(source, target))
                .isInstanceOf(TargetSameTeamException.class);
    }

    @DisplayName("폰이 대각선 방향 이동을 할 때 Target 위치에 빈 말이 있으면 움직일 수 없다.")
    @Test
    void Should_ThrowException_When_PawnDiagonalWithTargetEmpty() {
        Square source = new Square(File.B, Rank.TWO);
        Square target = new Square(File.C, Rank.THREE);

        assertThatThrownBy(() -> board.move(source, target))
                .isInstanceOf(PawnMoveDiagonalException.class);
    }

    @DisplayName("Pawn이 같은 줄에 여러개라면 0.5점으로 계산한다.")
    @Test
    void Should_PawnScoreHalf_When_SameLinePawnOverThanTwo() {
        board.move(new Square(File.B, Rank.TWO), new Square(File.B, Rank.FOUR));
        board.move(new Square(File.C, Rank.SEVEN), new Square(File.C, Rank.FIVE));
        board.move(new Square(File.B, Rank.FOUR), new Square(File.C, Rank.FIVE));

        assertThat(board.calculateScoreOfTeam(Team.WHITE)).isEqualTo(37.0);
    }
}
