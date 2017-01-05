package mercy.tourney.Model.Basic;

/**
 * Created by Andrianto on 12/13/2016.
 */
public class SearchMember extends Member {
    private Boolean isInvited;
    private Integer invited_at;

    public Boolean getInvited() {
        return isInvited;
    }

    public void setInvited(Boolean invited) {
        isInvited = invited;
    }

    public Integer getInvited_at() {
        return invited_at;
    }

    public void setInvited_at(Integer invited_at) {
        this.invited_at = invited_at;
    }
}
