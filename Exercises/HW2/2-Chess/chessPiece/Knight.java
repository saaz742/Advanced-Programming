package chessPiece;

import chess.Board;
import chess.Spot;

public class Knight extends Piece {

    public Knight(boolean white) {
        super(white);
        if (white) name = "Nw";
        else name = "Nb";
    }

    @Override
    public boolean canMove(Board board, Spot start, Spot end) {

        if (end.getPiece() != null) if (end.getPiece().isWhite() == this.isWhite()) {
            return false;
        }

        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());
        return x * y == 2;
    }
}
