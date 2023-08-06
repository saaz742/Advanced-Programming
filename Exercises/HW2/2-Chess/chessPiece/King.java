package chessPiece;

import chess.Board;
import chess.Spot;

public class King extends Piece {

    public King(boolean white) {
        super(white);
        if (white) name = "Kw";
        else name = "Kb";
    }

    @Override
    public boolean canMove(Board board, Spot start, Spot end) {
        if (end.getPiece() != null) if (end.getPiece().isWhite() == this.isWhite()) {
            return false;
        }
        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());
        return (x + y == 1) || (x * y == 1);
    }

}
