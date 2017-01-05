package mercy.tourney.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import mercy.tourney.Model.Basic.Token;
import mercy.tourney.Model.Basic.User;
import mercy.tourney.Model.Response.LoginResponse;

/**
 * Created by Andrianto on 12/3/2016.
 */
public class SessionManager {
    SharedPreferences sharedprf;
    SharedPreferences.Editor editor;

    Context context;

    private final int PRIVATEMODE = 0;
    private final String PREFNAME = "UserSession";
    private final String KEYISPREFLOGIN = "isUserLogin";
    private final String KEYTOKENDATA = "TokenDatas";
    private final String KEYUSERDATA = "UserDatas";

    public SessionManager(Context context) {
        this.context = context;
        sharedprf = context.getSharedPreferences(PREFNAME, PRIVATEMODE);
        editor = sharedprf.edit();
    }

    public void doCreateSession(LoginResponse tokens_and_users) {
        editor.putBoolean(KEYISPREFLOGIN, true);
        editor.putString(KEYTOKENDATA, new Gson().toJson(tokens_and_users.getTokens()));
        editor.putString(KEYUSERDATA, new Gson().toJson(tokens_and_users.getUsers()));
        editor.commit();
    }

    public void doChangeUserDatas(User users) {
        editor.putString(KEYUSERDATA, new Gson().toJson(users));
        editor.commit();
    }

    public void doChangeTokenDatas(Token tokens) {
        editor.putString(KEYTOKENDATA, new Gson().toJson(tokens));
        editor.commit();
    }

    public boolean isUserLoggedIn() {
        return sharedprf.getBoolean(KEYISPREFLOGIN, false);
    }

    public Token getTokenLoggedIn() {
        return new Gson().fromJson(sharedprf.getString(KEYTOKENDATA, ""), Token.class);
    }

    public User getUserLoggedIn() {
        return new Gson().fromJson(sharedprf.getString(KEYUSERDATA, ""), User.class);
    }

    public void doClearSession() {
        editor.clear();
        editor.commit();
    }
}
