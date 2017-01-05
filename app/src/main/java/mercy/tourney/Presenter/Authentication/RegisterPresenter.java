package mercy.tourney.Presenter.Authentication;

import java.util.HashMap;
import java.util.Map;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Connection.TourneyConnectionAPI;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Andrianto on 11/25/2016.
 */
public class RegisterPresenter {
    iPresenterResponse iRegisterResponse;

    /**
     * For Communicating Between View and Presenter
     *
     * @param iRegisterResponse
     */
    public RegisterPresenter(iPresenterResponse iRegisterResponse) {
        this.iRegisterResponse = iRegisterResponse;
    }

    /**
     * For Communicating Between Apps and API
     *
     * @param username
     * @param password
     * @param password_confirmation
     * @param first_name
     * @param last_name
     * @param phone
     */
    public void doRegister(String username, String password, String password_confirmation, String first_name, String last_name, String phone) {
        Map<String, String> RegisterData = new HashMap<>();
        RegisterData.put("username", username);
        RegisterData.put("password", password);
        RegisterData.put("password_confirmation", password_confirmation);
        RegisterData.put("first_name", first_name);
        RegisterData.put("last_name", last_name);
        RegisterData.put("phone", phone);

        TourneyConnectionAPI.getInstance().getAPIModel().doRegister(RegisterData).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body().getCode() == 201) {
                    iRegisterResponse.doSuccess(response.body());
                } else if (response.body().getCode() == 400 || response.body().getCode() == 500) {
                    iRegisterResponse.doFail(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                iRegisterResponse.doConnectionError(R.string.connection_error);
            }
        });
    }
}
