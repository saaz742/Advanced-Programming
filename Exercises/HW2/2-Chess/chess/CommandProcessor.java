package chess;

import chessPiece.Piece;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandProcessor {
    String input;
    Player player;
    Player loginPlayer;
    Player invitedPlayer;
    Game currentGame;
    int menuNumber = 0;
    String[] regex = {"register\\s(\\S+)\\s(\\S+)", "login\\s(\\S+)\\s(\\S+)", "remove\\s(\\S+)\\s(\\S+)", "new_game\\s(\\S+)\\s(-?\\d+)", "select\\s(-?\\d+),(-?\\d+)", "move\\s(-?\\d+),(-?\\d+)"};
    private boolean error = true;

    private static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        return matcher;
    }

    public void checkRegister(String input) {
        String name = (getMatcher(input, regex[0]).group(1));
        String password = (getMatcher(input, regex[0]).group(2));
        if (name.matches("[a-zA-Z0-9_]+")) {
            if (password.matches("[a-zA-Z0-9_]+")) {
                if (!(player.isTherePlayerWithName(name))) {
                    player = new Player(name, password);
                    System.out.println("register successful");
                } else System.out.println("a user exists with this username");
            } else System.out.println("password format is invalid");
        } else System.out.println("username format is invalid");
        error = false;
    }

    public void checkLogin(String input) {
        String name = getMatcher(input, regex[1]).group(1);
        String password = getMatcher(input, regex[1]).group(2);
        if (name.matches("[a-zA-Z0-9_]+")) {
            if (password.matches("[a-zA-Z0-9_]+")) {
                if (player.isTherePlayerWithName(name)) {
                    if (player.isTherePlayerWithPassword(name, password)) {
                        loginPlayer = player.getPlayerByName(name);
                        loginPlayer.whiteSide = true;
                        menuNumber = 1;
                        System.out.println("login successful");
                    } else System.out.println("incorrect password");
                } else System.out.println("no user exists with this username");
            } else System.out.println("password format is invalid");
        } else System.out.println("username format is invalid");
        error = false;
    }

    public void checkRemove(String input) {
        String name = (getMatcher(input, regex[2]).group(1));
        String password = (getMatcher(input, regex[2]).group(2));
        if (name.matches("[a-zA-Z0-9_]+")) {
            if (password.matches("[a-zA-Z0-9_]+")) {
                if ((player.isTherePlayerWithName(name))) {
                    if (player.isTherePlayerWithPassword(name, password)) {
                        player.removePlayer(player.getPlayerByName(name));
                        System.out.println("removed " + name + " successfully");
                    } else System.out.println("incorrect password");
                } else System.out.println("no user exists with this username");
            } else System.out.println("password format is invalid");
        } else System.out.println("username format is invalid");
        error = false;
    }

    public void ckeckListUsers() {
        if (player.isNotEmpty()) {
            player.sortPlayerName();
        }
        error = false;
    }

    public void checkHelp() {
        if (menuNumber == 0) {
            System.out.println("register [username] [password]\n" + "login [username] [password]\n" + "remove [username] [password]\n" + "list_users\n" + "help\n" + "exit");
        }
        if (menuNumber == 1) {
            System.out.println("new_game [username] [limit]\n" + "scoreboard\n" + "list_users\n" + "help\n" + "logout");
        }
        if (menuNumber == 2) {
            System.out.println("select [x],[y]\n" + "deselect\n" + "move [x],[y]\n" + "next_turn\n" + "show_turn\n" + "undo\n" + "undo_number\n" + "show_moves [-all]\n" + "show_killed [-all]\n" + "show_board\n" + "help\n" + "forfeit");
        }
        error = false;
    }

    public void checkLogout() {
        currentGame = null;
        loginPlayer = null;
        System.out.println("logout successful");
        menuNumber = 0;
        error = false;
    }

    public void checkScoreboard() {
        player.showScoreboard();
        error = false;
    }

    public void checkNewGame(String input) {
        String name = (getMatcher(input, regex[3]).group(1));
        int limit = Integer.parseInt(getMatcher(input, regex[3]).group(2));
        if (name.matches("[a-zA-Z0-9_]+")) {
            if (limit >= 0) {
                if (!loginPlayer.equals(player.getPlayerByName(name))) {
                    if (player.isTherePlayerWithName(name)) {
                        invitedPlayer = player.getPlayerByName(name);
                        invitedPlayer.whiteSide = false;
                        if (currentGame == null) currentGame = new Game();
                        currentGame.startGame(loginPlayer, invitedPlayer, limit);
                        currentGame.setStatus(GameStatus.ACTIVE);
                        menuNumber = 2;
                        System.out.println("new game started successfully between " + loginPlayer.getName() + " and " + invitedPlayer.getName() + " with limit " + currentGame.getLimit());
                    } else System.out.println("no user exists with this username");
                } else System.out.println("you must choose another player to start a game");
            } else System.out.println("number should be positive to have a limit or 0 for no limit");
        } else System.out.println("username format is invalid");
        error = false;
    }

    public void checkSelect(String input) {
        if ((getMatcher(input, regex[4]).group(1).matches("[1-8]")) && (getMatcher(input, regex[4]).group(2).matches("[1-8]"))) {
            int y = Integer.parseInt(getMatcher(input, regex[4]).group(1)) - 1;
            int x = Integer.parseInt(getMatcher(input, regex[4]).group(2)) - 1;
            if (currentGame.getBoard().isThereSpotWithXY(x, y)) {
                if (currentGame.getCurrentTurn() == currentGame.getPlayer1()) {
                    if (currentGame.getBoard().getSpotByXY(x, y).getPiece().isWhite()) {
                        currentGame.moveStart = currentGame.getBoard().getSpotByXY(x, y);
                        System.out.println("selected");
                    } else System.out.println("you can only select one of your pieces");
                } else {
                    if (!(currentGame.getBoard().getSpotByXY(x, y).getPiece().isWhite())) {
                        currentGame.moveStart = currentGame.getBoard().getSpotByXY(x, y);
                        System.out.println("selected");
                    } else System.out.println("you can only select one of your pieces");
                }
            } else System.out.println("no piece on this spot");
        } else System.out.println("wrong coordination");
        error = false;
    }

    public void checkDeselected() {
        if (currentGame.moveStart != null) {
            currentGame.moveStart = null;
            System.out.println("deselected");
        } else System.out.println("no piece is selected");
        error = false;
    }

    public void checkMove(String input) {
        if (currentGame.moveDone == 0) {
            if ((getMatcher(input, regex[5]).group(2).matches("[1-8]")) && (getMatcher(input, regex[5]).group(1).matches("[1-8]"))) {
                int y = Integer.parseInt((getMatcher(input, regex[5]).group(1))) - 1;
                int x = Integer.parseInt((getMatcher(input, regex[5]).group(2))) - 1;
                if (currentGame.moveStart != null) {
                    Piece endPiece;
                    if (currentGame.getBoard().getSpotByXY(x, y) != null) {
                        endPiece = currentGame.getBoard().getSpotByXY(x, y).getPiece();
                    } else {
                        endPiece = null;
                    }
                    currentGame.moveEnd = new Spot(x, y, endPiece);
                    currentGame.playerMove(currentGame.getCurrentTurn(), currentGame.moveStart, currentGame.moveEnd);
                } else System.out.println("do not have any selected piece");
            } else System.out.println("wrong coordination");
        } else System.out.println("already moved");
        error = false;
    }

    public void checkUndo() {
        // if (currentGame.moveDone == 1)(currentGame.moveEnd != null)|| {
        if (currentGame.getCurrentTurn().getUndoCount() < 2) {
            if (currentGame.moveDone == 1) {
                if (currentGame.moveUsedUndo == false) {
                    Move lastMove = currentGame.removeLastMove();
                    currentGame.moveUsedUndo = true;
                    currentGame.moveDone = 0;
                    System.out.println("undo completed");
                } else System.out.println("you have used your undo for this turn");
            } else System.out.println("you must move before undo");
        } else System.out.println("you cannot undo anymore");
        // } else System.out.println("you must move before undo");
        error = false;
    }

    public void checkNextTurn() {
        if (currentGame.moveDone == 1) {
            if (currentGame.getStatus() == GameStatus.ACTIVE) {
                if (currentGame.getCurrentTurn() == currentGame.getPlayer1()) {
                    currentGame.setCurrentTurn(currentGame.getPlayer2());
                } else {
                    currentGame.setCurrentTurn(currentGame.getPlayer1());
                }
                currentGame.moveUsedUndo = false;
                currentGame.moveStart = null;
                currentGame.moveEnd = null;
                currentGame.moveDone = 0;
                System.out.println("turn completed");
            }
            if (currentGame.getStatus() == GameStatus.ROW) {
                currentGame.getPlayer1().getScoreBoard().setRaws();
                currentGame.getPlayer2().getScoreBoard().setRaws();
                System.out.println("turn completed");
                System.out.println("draw");
                menuNumber = 1;
            }
            if (currentGame.getStatus() == GameStatus.WHITE_WIN) {
                currentGame.getPlayer1().getScoreBoard().setWins();
                currentGame.getPlayer2().getScoreBoard().setLoses();
                System.out.println("turn completed");
                System.out.println("player " + currentGame.getPlayer1().getName() + " with color white won");
                menuNumber = 1;
            } else if (currentGame.getStatus() == GameStatus.BLACK_WIN) {
                System.out.println("turn completed");
                currentGame.getPlayer2().getScoreBoard().setWins();
                currentGame.getPlayer1().getScoreBoard().setLoses();
                System.out.println("player " + currentGame.getPlayer2().getName() + " with color black won");
                menuNumber = 1;
            }
        } else System.out.println("you must move then proceed to next turn");
        error = false;
    }

    public void checkShowTurn() {
        if (currentGame.getCurrentTurn() == currentGame.getPlayer1()) {
            System.out.println("it is player " + currentGame.getPlayer1().getName() + " turn with color " + "white");
        } else System.out.println("it is player " + currentGame.getPlayer2().getName() + " turn with color " + "black");
        error = false;
    }

    public void checkForfait() {
        if (currentGame.getCurrentTurn() == currentGame.getPlayer1()) {
            currentGame.setStatus(GameStatus.BLACK_WIN);
            currentGame.getPlayer1().getScoreBoard().setHaveForfeit();
            currentGame.getPlayer1().getScoreBoard().setLoses();
            currentGame.getPlayer2().getScoreBoard().setGetForfeit();
            currentGame.getPlayer2().getScoreBoard().setWinForfeit();
            menuNumber = 1;
            System.out.println("you have forfeited");
            System.out.println("player " + currentGame.getPlayer2().getName() + " with color black won");
        } else {

            currentGame.setStatus(GameStatus.WHITE_WIN);
            currentGame.getPlayer1().getScoreBoard().setGetForfeit();
            currentGame.getPlayer1().getScoreBoard().setWinForfeit();
            currentGame.getPlayer2().getScoreBoard().setHaveForfeit();
            currentGame.getPlayer2().getScoreBoard().setLoses();
            menuNumber = 1;
            System.out.println("you have forfeited");
            System.out.println("player " + currentGame.getPlayer1().getName() + " with color white won");
        }
        error = false;
    }

    public void checkShowMoves() {
        currentGame.showMoves(currentGame.getCurrentTurn());
        error = false;
    }

    public void checkShowAllMoves() {
        currentGame.showMoves(null);
        error = false;
    }

    public void checkShowKilled() {
        currentGame.showKilled(currentGame.getCurrentTurn());
        error = false;
    }

    public void checkShowAllKilled() {
        currentGame.showKilled(null);
        error = false;
    }

    public void run() {
        int j = 0;
        Scanner scanner = new Scanner(System.in);
        while (!(input = scanner.nextLine()).equals("exit")) {

            j++;
            if (menuNumber == 0) {
                if (input.matches(regex[0])) {
                    checkRegister(input);
                }
                if (input.matches(regex[1])) {
                    checkLogin(input);
                }
                if (input.matches(regex[2])) {
                    checkRemove(input);
                }
            }
            if (menuNumber == 0 || menuNumber == 1) {
                if (input.equals("list_users")) {
                    ckeckListUsers();
                }
            }
            if (menuNumber == 1) {
                if (input.matches(regex[3])) {
                    checkNewGame(input);
                }
                if (input.equals("scoreboard")) {
                    checkScoreboard();
                }
                if (input.equals("logout")) {
                    checkLogout();
                }
            }
            if (menuNumber == 2) {
                if (input.matches(regex[4])) {
                    checkSelect(input);
                }
                if (input.matches(regex[5])) {
                    checkMove(input);
                }
                if (input.equals("deselect")) {
                    checkDeselected();
                }
                if (input.equals("next_turn")) {
                    checkNextTurn();
                }
                if (input.equals("show_turn")) {
                    checkShowTurn();
                }
                if (input.equals("undo")) {
                    checkUndo();
                }
                if (input.equals("undo_number")) {
                    System.out.println("you have " + (2 - (currentGame.getCurrentTurn().getUndoCount())) + " undo moves");
                    error = false;
                }
                if (input.equals("show_moves")) {
                    checkShowMoves();

                }
                if (input.equals("show_moves -all")) {
                    checkShowAllMoves();
                }
                if (input.equals("show_killed")) {
                    checkShowKilled();
                }
                if (input.equals("show_killed -all")) {
                    checkShowAllKilled();
                }
                if (input.equals("show_board")) {
                    currentGame.getBoard().showBoard();
                    error = false;
                }
                if (input.equals("forfeit")) {
                    checkForfait();
                }
            }

            if (input.equals("help")) {
                checkHelp();
            }
            if (error == true) {
                System.out.println("invalid command");
            }
            error = true;
        }
        if (input.equals("exit")) {
            System.out.println("program ended");
        }

    }

}
