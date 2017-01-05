package mercy.tourney.Model.Response;

import java.util.List;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Basic.SearchMember;

/**
 * Created by Andrianto on 12/12/2016.
 */
public class MemberResponse extends Response {
    private List<SearchMember> members;

    public List<SearchMember> getMembers() {
        return members;
    }

    public void setMembers(List<SearchMember> members) {
        this.members = members;
    }
}
