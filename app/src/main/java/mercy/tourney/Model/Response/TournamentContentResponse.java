package mercy.tourney.Model.Response;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Basic.Tournament;

/**
 * Created by Andrianto on 12/16/2016.
 */
public class TournamentContentResponse extends Response {
    private Tournament tournament;
    private Boolean isRegisterOpen;
    private Boolean isLeader;
    private Boolean isAlreadyRegister;

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Boolean getRegisterOpen() {
        return isRegisterOpen;
    }

    public void setRegisterOpen(Boolean registerOpen) {
        isRegisterOpen = registerOpen;
    }

    public Boolean getLeader() {
        return isLeader;
    }

    public void setLeader(Boolean leader) {
        isLeader = leader;
    }

    public Boolean getAlreadyRegister() {
        return isAlreadyRegister;
    }

    public void setAlreadyRegister(Boolean alreadyRegister) {
        isAlreadyRegister = alreadyRegister;
    }
}
