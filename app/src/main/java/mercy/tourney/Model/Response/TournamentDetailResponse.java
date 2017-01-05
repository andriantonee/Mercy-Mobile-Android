package mercy.tourney.Model.Response;

import java.util.List;
import java.util.Map;

import mercy.tourney.Model.Basic.Match;
import mercy.tourney.Model.Basic.Response;

/**
 * Created by Andrianto on 12/19/2016.
 */
public class TournamentDetailResponse extends Response {
    private String tournament_name;
    private Map<Integer, List<Match>> match;
    private Map<Integer, String> match_round;

    public String getTournament_name() {
        return tournament_name;
    }

    public void setTournament_name(String tournament_name) {
        this.tournament_name = tournament_name;
    }

    public Map<Integer, List<Match>> getMatch() {
        return match;
    }

    public void setMatch(Map<Integer, List<Match>> match) {
        this.match = match;
    }

    public Map<Integer, String> getMatch_round() {
        return match_round;
    }

    public void setMatch_round(Map<Integer, String> match_round) {
        this.match_round = match_round;
    }
}
