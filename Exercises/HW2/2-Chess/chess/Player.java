package chess;

import java.util.*;

public class Player {
    private static final ArrayList<Player> allPlayers = new ArrayList<Player>();
    private static final ArrayList<String> allPlayersName = new ArrayList<String>();
    public boolean whiteSide;
    private final String name;
    private final String password;
    private final ScoreBoard scoreBoard;
    private int undoCount = 0;
    private final boolean sortNext = false;

    public Player(String name, String password) {
        this.name = name;
        this.password = password;
        this.scoreBoard = new ScoreBoard();
        this.getScoreBoard().setPlayer(this);
        allPlayers.add(this);
        allPlayersName.add(this.name);
    }

    public static boolean isTherePlayerWithPassword(String name, String password) {
        if (password != null) for (Player player : allPlayers) {
            if (player.password.equals(password) && player.name.equals(name)) return true;
        }
        return false;
    }

    public static boolean isTherePlayerWithName(String name) {
        if (name != null) {
            for (Player player : allPlayers) {
                if (player.name.equals(name)) return true;
            }
        }
        return false;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public String getName() {
        return name;
    }

    public int getUndoCount() {
        return undoCount;
    }

    public void setUndoCount(int undoCount) {
        this.undoCount = undoCount;
    }

    public void removePlayer(Player player) {
        allPlayersName.remove(player.name);
        allPlayers.remove(player);
    }

    public Player getPlayerByName(String name) {
        if (name != null) {
            for (Player player : allPlayers) {
                if (player.name.equals(name)) return player;
            }
        }
        return null;
    }

    public void sortPlayerName() {
        if (allPlayersName.size() > 0) {
            Collections.sort(allPlayersName);
            for (String names : allPlayersName) {
                System.out.println(names);
            }
        }
    }

    public boolean isWhiteSide() {
        return this.whiteSide;
    }


    public void showScoreboard() {
        ArrayList<ScoreBoard> scoreboards = new ArrayList<ScoreBoard>();
        for (int i = 0; i < allPlayers.size(); i++)
            scoreboards.add(allPlayers.get(i).scoreBoard);

        Collections.sort(scoreboards);

        for (int i = 0; i < scoreboards.size(); i++)
            System.out.println(scoreboards.get(i).getPlayer().name + " " + scoreboards.get(i).getScores() + " " + scoreboards.get(i).getWins() + " " + scoreboards.get(i).getRaws() + " " + scoreboards.get(i).getLosses());

    }

    public boolean isNotEmpty() {
        return (allPlayers.size() > 0);
    }

}



