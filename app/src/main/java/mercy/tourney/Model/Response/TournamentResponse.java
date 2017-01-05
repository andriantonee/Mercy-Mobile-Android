package mercy.tourney.Model.Response;

import java.util.List;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Basic.Tournament;

/**
 * Created by Andrianto on 12/15/2016.
 */
public class TournamentResponse extends Response {
    private List<Tournament> tournaments;

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }
}
