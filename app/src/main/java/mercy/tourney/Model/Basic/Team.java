package mercy.tourney.Model.Basic;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andrianto on 12/6/2016.
 */
public class Team implements Serializable {
    private Integer id;
    private String team_name;
    private String leader_username;
    private String leader_name;
    private Integer created_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getLeader_username() {
        return leader_username;
    }

    public void setLeader_username(String leader_username) {
        this.leader_username = leader_username;
    }

    public String getLeader_name() {
        return leader_name;
    }

    public void setLeader_name(String leader_name) {
        this.leader_name = leader_name;
    }

    public Integer getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Integer created_at) {
        this.created_at = created_at;
    }
}
