package chess;

import chessPiece.Piece;

public class Move {
    private final Player player;
    private Spot start;
    private final Spot end;
    private Piece pieceKilled = null;

    public Move(Player player, Spot start, Spot end) {
        this.player = player;
        this.start = start;
        this.end = end;
    }

    public Spot getStart() {
        return start;
    }

    public void setStart(Spot spot) {
        this.start = spot;
    }

    public Spot getEnd() {
        return end;
    }

    public Piece getPieceKilled() {
        return pieceKilled;
    }

    public void setPieceKilled(Piece destPiece) {
        this.pieceKilled = destPiece;
    }

    public Player getPlayer() {
        return this.player;
    }


}

