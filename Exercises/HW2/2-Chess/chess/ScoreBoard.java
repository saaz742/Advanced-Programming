package chess;


public class ScoreBoard implements Comparable<ScoreBoard> {

    private int wins;
    private int losses;
    private int raws;
    private int haveForfeit;
    private int getForfeit;
    private int winForfeit;
    private Player player;

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setWinForfeit() {
        this.winForfeit++;
    }

    public int getLosses() {
        return losses;
    }

    public int getRaws() {
        return raws;
    }

    public int getScores() {
        return wins * 3 + raws + haveForfeit * -1 + getForfeit * 2;
    }

    public void setHaveForfeit() {
        this.haveForfeit++;
    }

    public void setGetForfeit() {
        this.getForfeit++;
    }

    public int getWins() {
        return wins + winForfeit;
    }

    public void setWins() {
        this.wins++;
    }

    public void setRaws() {
        this.raws++;
    }

    public void setLoses() {
        this.losses++;
    }

    @Override
    public int compareTo(ScoreBoard o) {
        if (this.getScores() < o.getScores()) return 1;
        else if (this.getScores() == o.getScores()) if (this.getWins() < o.getWins()) return 1;
        else if (this.getWins() == o.getWins()) if ((this.getRaws() < o.getRaws())) return 1;
        else if ((this.getRaws() == o.getRaws())) if (this.getLosses() > o.getLosses()) return 1;
        else if (this.getLosses() == o.getLosses()) return this.player.getName().compareTo(o.player.getName());
        return -1;
    }
}
