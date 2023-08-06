package chessPiece;

import chess.Board;
import chess.Spot;

public class Pawn extends Piece {
    private boolean firstMove = true;

    public Pawn(boolean white) {
        super(white);
        if (white) name = "Pw";
        else name = "Pb";
    }

    public boolean isFirstMove() {
        return (this.firstMove);
    }

    public void setFirstMove(boolean state) {
        this.firstMove = state;
    }

    @Override
    public boolean canMove(Board board, Spot start, Spot end) {
        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());
        if (end.getPiece() != null) {
            if (end.getPiece().isWhite() != this.isWhite()) {
                if ((x == 1 & y == 1)) {
                    if (this.isWhite()) {
                        if (Integer.signum(end.getY() - start.getY()) == 1) {
                            setFirstMove(false);
                            return true;
                        } else {
                            setFirstMove(false);
                            return false;
                        }
                    } else if (!this.isWhite()) {
                        if (Integer.signum(start.getY() - end.getY()) == 1) {
                            setFirstMove(false);
                            return true;
                        } else {
                            setFirstMove(false);
                            return false;
                        }

                    }
                }
                return false;
            }
        } else if (y == 1 && x == 0) {
            if (this.isWhite()) {
                if (Integer.signum(end.getY() - start.getY()) == 1) {
                    setFirstMove(false);
                    return true;
                } else {
                    setFirstMove(false);
                    return false;
                }
            } else if (!this.isWhite()) {
                if (Integer.signum(start.getY() - end.getY()) == 1) {
                    setFirstMove(false);
                    return true;
                } else {
                    setFirstMove(false);
                    return false;
                }

            }

        } else if (isFirstMove()) {
            if (y == 2 && x == 0) {
                if (!board.isThereSpotWithXY((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2)) {
                    setFirstMove(false);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
