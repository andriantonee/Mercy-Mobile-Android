package mercy.tourney.Model.Response;

import java.util.List;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Basic.TeamInvitation;

/**
 * Created by Andrianto on 12/14/2016.
 */
public class TeamInvitationResponse extends Response {
    private List<TeamInvitation> teams;

    public List<TeamInvitation> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamInvitation> teams) {
        this.teams = teams;
    }
}
