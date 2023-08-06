package chessPiece;

import chess.Board;
import chess.Spot;

public class Rook extends Piece {

    public Rook(boolean white) {
        super(white);
        if (white) name = "Rw";
        else name = "Rb";
    }

    @Override
    public boolean canMove(Board board, Spot start, Spot end) {

        if (end.getPiece() != null) if (end.getPiece().isWhite() == this.isWhite()) {
            return false;
        }

        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());
        if ((x == 0 && y != 0) || (x != 0 && y == 0)) {
            return isFree(board, start, end);
        }
        return false;
    }

    private boolean isFree(Board board, Spot start, Spot end) {
        int x = start.getX();
        int y = start.getY();
        int xStep = 0;
        int yStep = 0;
        int count = 0;
        if (start.getX() == end.getX()) {
            yStep = Integer.signum(end.getY() - y);
            count = Math.abs(end.getY() - y);
        }
        if (start.getY() == end.getY()) {
            xStep = Integer.signum(end.getX() - x);
            count = Math.abs(end.getX() - x);
        }
        for (int i = 1; i < count; i++) {
            x += xStep;
            y += yStep;
            if (board.isThereSpotWithXY(x, y)) return false;
        }
        return true;
    }


}
