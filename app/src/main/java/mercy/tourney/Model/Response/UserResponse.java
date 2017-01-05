package mercy.tourney.Model.Response;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Basic.User;

/**
 * Created by Andrianto on 12/1/2016.
 */
public class UserResponse extends Response {
    private User users;

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }
}
