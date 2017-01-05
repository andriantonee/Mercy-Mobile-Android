package mercy.tourney.Presenter.Authentication;

import java.util.HashMap;
import java.util.Map;

import mercy.tourney.Model.Connection.TourneyConnectionAPI;
import mercy.tourney.Model.Response.LoginResponse;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Andrianto on 11/25/2016.
 */
public class LoginPresenter {
    iPresenterResponse iLoginResponse;

    /**
     * For Communicating Between View and Presenter
     *
     * @param iLoginResponse
     */
    public LoginPresenter(iPresenterResponse iLoginResponse) {
        this.iLoginResponse = iLoginResponse;
    }

    /**
     * For Communicating Between Apps and API
     *
     * @param username
     * @param password
     */
    public void doLogin(String username, String password, String client_id, String client_secret, String scope) {
        Map<String, String> LoginData = new HashMap<>();
        LoginData.put("grant_type", "password");
        LoginData.put("username", username);
        LoginData.put("password", password);
        LoginData.put("client_id", client_id);
        LoginData.put("client_secret", client_secret);
        LoginData.put("scope", scope);

        TourneyConnectionAPI.getInstance().getAPIModel().doLogin(LoginData).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().getCode() >= 200 && response.body().getCode() <= 299) {
                    iLoginResponse.doSuccess(response.body());
                } else {
                    iLoginResponse.doFail(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                iLoginResponse.doConnectionError(R.string.connection_error);
            }
        });
    }
}
