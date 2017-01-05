package mercy.tourney.Model.Basic;

/**
 * Created by Andrianto on 12/1/2016.
 */
public class User {
    private String username;
    private String first_name;
    private String last_name;
    private String phone;
    private Integer teams_id;
    private Integer status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getTeams_id() {
        return teams_id;
    }

    public void setTeams_id(Integer teams_id) {
        this.teams_id = teams_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
