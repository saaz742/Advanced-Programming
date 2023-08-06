package chess;

import chessPiece.King;
import chessPiece.Pawn;
import chessPiece.Piece;

import java.util.ArrayList;
import java.util.Iterator;

public class Game {
    public Spot moveStart;
    public Spot moveEnd;
    public int moveDone;
    public boolean moveUsedUndo;
    public int moveCount = 0;
    private Player player1;
    private Player player2;
    private Board board;
    private Player currentTurn;
    private GameStatus status;
    private ArrayList<Move> movesPlayed;
    private int limit;
    private boolean killedText = false;

    public Move removeLastMove() {

        Move lastMove = movesPlayed.get(moveCount - 1);
        board.placePieceAt(currentTurn, new Spot(lastMove.getEnd().getX(), lastMove.getEnd().getY(), lastMove.getStart().getPiece()), new Spot(lastMove.getStart().getX(), lastMove.getStart().getY(), lastMove.getEnd().getPiece()));
        if (lastMove.getPieceKilled() != null) {
            board.boxes[lastMove.getEnd().getY()][lastMove.getEnd().getX()] = lastMove.getPieceKilled();
        }
        if (lastMove.getStart().getPiece() instanceof Pawn) ((Pawn) lastMove.getStart().getPiece()).setFirstMove(true);
        if (status != GameStatus.ACTIVE) {
            status = GameStatus.ACTIVE;
        }
        movesPlayed.remove(moveCount - 1);
        moveCount--;
        currentTurn.setUndoCount(currentTurn.getUndoCount() + 1);
        return lastMove;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getLimit() {
        return limit;
    }

    public Player getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(Player player) {
        this.currentTurn = player;
    }

    public void startGame(Player p1, Player p2, int limit) {
        player1 = p1;
        player2 = p2;
        this.limit = limit;
        currentTurn = p1;
        p1.setUndoCount(0);
        p2.setUndoCount(0);
        board = new Board();
        moveStart = null;
        moveEnd = null;
        moveDone = 0;
        moveCount = 0;
        movesPlayed = new ArrayList<Move>();
        moveUsedUndo = false;

    }

    public GameStatus getStatus() {
        return this.status;
    }

    public void setStatus(GameStatus status) {

        this.status = status;
    }

    public boolean playerMove(Player player, Spot start, Spot end) {

        boolean moved = this.canMakeMove(player, start, end);
        if (moved) {
            if (killedText) {
                System.out.println("rival piece destroyed");
                killedText = false;
            } else {
                System.out.println("moved");
            }
            moveDone = 1;
            moveCount++;

        } else {
            System.out.println("cannot move to the spot");
        }
        return moved;
    }

    private boolean canMakeMove(Player player, Spot start, Spot end) {

        Piece sourcePiece = start.getPiece();
        if (sourcePiece == null) {
            return false;
        }

        if (player != currentTurn) {
            return false;
        }

        if (sourcePiece.isWhite() != player.isWhiteSide()) {
            return false;
        }

        if (!sourcePiece.canMove(board, start, end)) {
            return false;
        }
        if (limit != 0) {
            if (moveCount == limit - 1) {
                setStatus(GameStatus.ROW);
            }
        }

        Move move = new Move(player, start, end);
        movesPlayed.add(move);

        Piece destPiece = end.getPiece();
        if (destPiece != null) {
            destPiece.setKilled(true);
            move.setPieceKilled(destPiece);
            killedText = true;
        }

        this.getBoard().placePieceAt(player, start, end);

        if (destPiece != null && destPiece instanceof King) {
            if (currentTurn == player1) {
                this.setStatus(GameStatus.WHITE_WIN);
            } else {
                this.setStatus(GameStatus.BLACK_WIN);
            }
        }

        return true;
    }

    public void showMoves(Player player) {

        Iterator iterator = movesPlayed.iterator();
        while (iterator.hasNext()) {
            Move selectedMove = (Move) iterator.next();
            Player selectedPlayer = selectedMove.getPlayer();
            if (selectedPlayer == player || player == null) {
                if (selectedMove.getPieceKilled() == null) {
                    System.out.println(selectedMove.getStart().getPiece().getName() + " " + (selectedMove.getStart().getY() + 1) + "," + (selectedMove.getStart().getX() + 1) + " " + "to " + (selectedMove.getEnd().getY() + 1) + "," + (selectedMove.getEnd().getX() + 1));
                } else
                    System.out.println(selectedMove.getStart().getPiece().getName() + " " + (selectedMove.getStart().getY() + 1) + "," + (selectedMove.getStart().getX() + 1) + " " + "to " + (selectedMove.getEnd().getY() + 1) + "," + (selectedMove.getEnd().getX() + 1) + " destroyed " + selectedMove.getPieceKilled().getName());

            }
        }
    }

    public void showKilled(Player player) {

        Iterator iterator = movesPlayed.iterator();
        while (iterator.hasNext()) {
            Move selectedMove = (Move) iterator.next();
            Player selectedPlayer = selectedMove.getPlayer();
            if (selectedPlayer != player || player == null) {
                if (selectedMove.getPieceKilled() != null) {
                    System.out.println(selectedMove.getPieceKilled().getName() + " killed in spot " + (selectedMove.getEnd().getY() + 1) + "," + (selectedMove.getEnd().getX() + 1));
                }
            }

        }

    }

    public Board getBoard() {
        return board;
    }
}


