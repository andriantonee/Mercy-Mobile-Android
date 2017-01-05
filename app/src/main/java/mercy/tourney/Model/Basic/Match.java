package mercy.tourney.Model.Basic;

/**
 * Created by Andrianto on 12/19/2016.
 */
public class Match {
    private String player_1;
    private String player_2;
    private Integer player_1_score;
    private Integer player_2_score;
    private Boolean has_winner;
    private Boolean player_1_win;
    private Boolean player_2_win;
    private Integer scheduled_time;

    public String getPlayer_1() {
        return player_1;
    }

    public void setPlayer_1(String player_1) {
        this.player_1 = player_1;
    }

    public String getPlayer_2() {
        return player_2;
    }

    public void setPlayer_2(String player_2) {
        this.player_2 = player_2;
    }

    public Integer getPlayer_1_score() {
        return player_1_score;
    }

    public void setPlayer_1_score(Integer player_1_score) {
        this.player_1_score = player_1_score;
    }

    public Integer getPlayer_2_score() {
        return player_2_score;
    }

    public void setPlayer_2_score(Integer player_2_score) {
        this.player_2_score = player_2_score;
    }

    public Boolean getHas_winner() {
        return has_winner;
    }

    public void setHas_winner(Boolean has_winner) {
        this.has_winner = has_winner;
    }

    public Boolean getPlayer_1_win() {
        return player_1_win;
    }

    public void setPlayer_1_win(Boolean player_1_win) {
        this.player_1_win = player_1_win;
    }

    public Boolean getPlayer_2_win() {
        return player_2_win;
    }

    public void setPlayer_2_win(Boolean player_2_win) {
        this.player_2_win = player_2_win;
    }

    public Integer getScheduled_time() {
        return scheduled_time;
    }

    public void setScheduled_time(Integer scheduled_time) {
        this.scheduled_time = scheduled_time;
    }
}
