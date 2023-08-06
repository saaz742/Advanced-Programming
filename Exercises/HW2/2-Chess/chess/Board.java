package chess;

import chessPiece.*;

public class Board {
    Piece[][] boxes;

    public Board() {

        this.resetBoard();
    }

    private void removePieceAt(Spot spot) {

        boxes[spot.getY()][spot.getX()] = null;
    }

    public void placePieceAt(Player player, Spot start, Spot end) {

        if (end.getPiece() != null) if (player.whiteSide != end.getPiece().isWhite()) {
            end.getPiece().setKilled(true);
        }
        boxes[end.getY()][end.getX()] = start.getPiece();
        removePieceAt(start);

    }

    public void resetBoard() {
        this.boxes = new Piece[8][8];
        //white
        boxes[0][0] = new Rook(true);
        boxes[0][1] = new Knight(true);
        boxes[0][2] = new Bishop(true);
        boxes[0][3] = new Queen(true);
        boxes[0][4] = new King(true);
        boxes[0][5] = new Bishop(true);
        boxes[0][6] = new Knight(true);
        boxes[0][7] = new Rook(true);
        //
        boxes[1][0] = new Pawn(true);
        boxes[1][1] = new Pawn(true);
        boxes[1][2] = new Pawn(true);
        boxes[1][3] = new Pawn(true);
        boxes[1][4] = new Pawn(true);
        boxes[1][5] = new Pawn(true);
        boxes[1][6] = new Pawn(true);
        boxes[1][7] = new Pawn(true);

        //black
        boxes[7][0] = new Rook(false);
        boxes[7][1] = new Knight(false);
        boxes[7][2] = new Bishop(false);
        boxes[7][3] = new Queen(false);
        boxes[7][4] = new King(false);
        boxes[7][5] = new Bishop(false);
        boxes[7][6] = new Knight(false);
        boxes[7][7] = new Rook(false);
        //
        boxes[6][0] = new Pawn(false);
        boxes[6][1] = new Pawn(false);
        boxes[6][2] = new Pawn(false);
        boxes[6][3] = new Pawn(false);
        boxes[6][4] = new Pawn(false);
        boxes[6][5] = new Pawn(false);
        boxes[6][6] = new Pawn(false);
        boxes[6][7] = new Pawn(false);

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                boxes[i][j] = null;
            }
        }
    }

    public void showBoard() {
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                if (boxes[i][j] != null) System.out.print(boxes[i][j].getName() + '|');
                else System.out.print("  |");
            }
            System.out.println("");
        }
    }

    public boolean isThereSpotWithXY(int x, int y) {
        return (boxes[y][x] != null);

    }

    public Spot getSpotByXY(int x, int y) {

        if (boxes[y][x] == null) return null;
        return new Spot(x, y, boxes[y][x]);

    }


}
