package chess.domain.board;

import chess.domain.piece.Empty;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Team;
import chess.exception.PathBlockedException;
import chess.exception.PawnMoveDiagonalException;
import chess.exception.PawnMoveForwardException;
import chess.exception.TargetSameTeamException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Board {
    private static final boolean IS_MOVED = true;

    private final Map<Square, Piece> board;

    public Board(Map<Square, Piece> board) {
        this.board = board;
    }

    public void move(Square source, Square target) {
        validateMovable(source, target);

        if (board.get(source).isSamePieceType(PieceType.PAWN)) {
            board.put(source, new Pawn(board.get(source).getTeam(), IS_MOVED));
        }
        board.put(target, board.get(source));
        board.put(source, new Empty());
    }

    public void validateMovable(Square source, Square target) {
        Direction direction = Direction.calculateDirection(source, target);

        validatePathBlocked(source, target, direction);
        if (board.get(source).isSamePieceType(PieceType.PAWN)) {
            validatePawnPathBlocked(target, direction);
        }
        board.get(source).validateMovableRange(source, target);
    }

    private void validatePathBlocked(Square source, Square target, Direction direction) {
        Piece sourcePiece = board.get(source);
        Piece targetPiece = board.get(target);

        if (isBlocked(source, target, direction) && !board.get(source).isSamePieceType(PieceType.KNIGHT)) {
            throw new PathBlockedException();
        }
        if (sourcePiece.isSameTeam(targetPiece.getTeam())) {
            throw new TargetSameTeamException();
        }
    }

    private boolean isBlocked(Square source, Square target, Direction direction) {
        Square nextSquare = source.nextSquare(source, direction.getFile(), direction.getRank());
        if (nextSquare.equals(target)) {
            return false;
        }
        if (board.get(nextSquare).isEmpty()) {
            return isBlocked(nextSquare, target, direction);
        }
        return true;
    }

    private void validatePawnPathBlocked(Square target, Direction direction) {
        boolean isTargetEmpty = board.get(target).isEmpty();
        if (!isTargetEmpty && Direction.isMoveForward(direction)) {
            throw new PawnMoveForwardException();
        }
        if (isTargetEmpty && Direction.isMoveDiagonal(direction)) {
            throw new PawnMoveDiagonalException();
        }
    }

    public boolean isEmptyPiece(Square square) {
        return board.get(square).isEmpty();
    }

    public boolean isSquarePieceNotCurrentTurn(Square square, Team turn) {
        return board.get(square).isAnotherTeam(turn);
    }

    public boolean haveOneKing() {
        int kingCount = (int) board.values().stream()
                .filter(piece -> piece.isSamePieceType(PieceType.KING))
                .count();

        return kingCount == 1;
    }

    public Map<Piece, Square> getWhitePieces() {
        return board.entrySet().stream()
            .filter(entry -> entry.getValue().isSameTeam(Team.WHITE))
            .collect(Collectors.toMap(Entry::getValue, Entry::getKey));
    }

    public Map<Piece, Square> getBlackPieces() {
        return board.entrySet().stream()
                .filter(entry -> entry.getValue().isSameTeam(Team.BLACK))
                .collect(Collectors.toMap(Entry::getValue, Entry::getKey));
    }

    public List<Piece> getPieces() {
        return new ArrayList<>(board.values());
    }

    public Map<Square, Piece> getBoard() {
        return board;
    }
}
