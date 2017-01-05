package mercy.tourney.Model.Basic;

/**
 * Created by Andrianto on 12/15/2016.
 */
public class Tournament {
    private Integer id;
    private String name;
    private Game category;
    private Integer registration_open;
    private Integer registration_close;
    private Integer member_participant_in_one_team;
    private String location;
    private String description;
    private String rules;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getCategory() {
        return category;
    }

    public void setCategory(Game category) {
        this.category = category;
    }

    public Integer getRegistration_open() {
        return registration_open;
    }

    public void setRegistration_open(Integer registration_open) {
        this.registration_open = registration_open;
    }

    public Integer getRegistration_close() {
        return registration_close;
    }

    public void setRegistration_close(Integer registration_close) {
        this.registration_close = registration_close;
    }

    public Integer getMember_participant_in_one_team() {
        return member_participant_in_one_team;
    }

    public void setMember_participant_in_one_team(Integer member_participant_in_one_team) {
        this.member_participant_in_one_team = member_participant_in_one_team;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }
}
