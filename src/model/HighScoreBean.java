package model;

public class HighScoreBean {
    private String username;
    private int score;

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public HighScoreBean(String username, int score) {
        this.username = username;
        this.score = score;
    }
}