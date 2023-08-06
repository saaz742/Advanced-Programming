package chessPiece;

import chess.Board;
import chess.Spot;

public class Bishop extends Piece {

    public Bishop(boolean white) {
        super(white);
        if (white) name = "Bw";
        else name = "Bb";
    }

    @Override
    public boolean canMove(Board board, Spot start, Spot end) {
        if (end.getPiece() != null) if (end.getPiece().isWhite() == this.isWhite()) {
            return false;
        }
        int x = end.getX() - start.getX();
        int y = end.getY() - start.getY();
        if (Math.abs(x) == Math.abs(y)) {

            int xs = start.getX();
            int ys = start.getY();
            int xStep = Integer.signum(x);
            int yStep = Integer.signum(y);

            for (int i = 1; i < Math.abs(x); i++) {
                xs += xStep;
                ys += yStep;
                if (board.isThereSpotWithXY(xs, ys)) return false;
            }


            return true;
        }
        return false;

    }


}
