package mercy.tourney.Model.Response;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Basic.Token;
import mercy.tourney.Model.Basic.User;

/**
 * Created by Andrianto on 11/25/2016.
 */
public class LoginResponse extends Response {
    private Token tokens;
    private User users;

    public Token getTokens() {
        return tokens;
    }

    public void setTokens(Token tokens) {
        this.tokens = tokens;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }
}
