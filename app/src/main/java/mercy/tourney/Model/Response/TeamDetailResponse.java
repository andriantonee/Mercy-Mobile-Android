package mercy.tourney.Model.Response;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Basic.Team;

/**
 * Created by Andrianto on 12/6/2016.
 */
public class TeamDetailResponse extends Response {
    private Boolean isLeader;
    private Team team;

    public Boolean getLeader() {
        return isLeader;
    }

    public void setLeader(Boolean leader) {
        isLeader = leader;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
