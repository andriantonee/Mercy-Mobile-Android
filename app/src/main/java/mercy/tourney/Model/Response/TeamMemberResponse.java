package mercy.tourney.Model.Response;

import java.util.List;

import mercy.tourney.Model.Basic.Member;
import mercy.tourney.Model.Basic.Response;

/**
 * Created by Andrianto on 12/8/2016.
 */
public class TeamMemberResponse extends Response {
    private Boolean isLeader;
    private Integer teams_id;
    private List<Member> members;

    public Boolean getLeader() {
        return isLeader;
    }

    public void setLeader(Boolean leader) {
        isLeader = leader;
    }

    public Integer getTeams_id() {
        return teams_id;
    }

    public void setTeams_id(Integer teams_id) {
        this.teams_id = teams_id;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
