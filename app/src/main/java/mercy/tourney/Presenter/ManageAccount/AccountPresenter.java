package mercy.tourney.Presenter.ManageAccount;

import java.util.HashMap;
import java.util.Map;

import mercy.tourney.Model.Connection.TourneyConnectionAPI;
import mercy.tourney.Model.Response.UserResponse;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Andrianto on 12/2/2016.
 */
public class AccountPresenter {
    iPresenterResponse iAccountResponse;

    /**
     * For Communicating Between View and Presenter
     *
     * @param iAccountResponse
     */
    public AccountPresenter(iPresenterResponse iAccountResponse) {
        this.iAccountResponse = iAccountResponse;
    }

    /**
     * For Communicating Between Apps and API
     *
     * @param token_type
     * @param token
     * @param first_name
     * @param last_name
     * @param phone
     */
    public void update_profile(String token_type, String token, String first_name, String last_name, String phone) {
        String Authorization = token_type.concat(" ").concat(token);
        Map<String, String> ProfileData = new HashMap<>();
        ProfileData.put("first_name", first_name);
        ProfileData.put("last_name", last_name);
        ProfileData.put("phone", phone);

        TourneyConnectionAPI.getInstance().getAPIModel().doProfileUpdate(Authorization, ProfileData).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iAccountResponse.doSuccess(response.body());
                    } else {
                        iAccountResponse.doFail(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        iAccountResponse.doFail("Not Authorize!");
                    } else {
                        iAccountResponse.doFail("Something went wrong. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                iAccountResponse.doConnectionError(R.string.connection_error);
            }
        });
    }

    public void update_profile_password(String token_type, String token, String old_password, String password, String password_confirmation) {
        String Authorization = token_type.concat(" ").concat(token);
        Map<String, String> PasswordData = new HashMap<>();
        PasswordData.put("old_password", old_password);
        PasswordData.put("password", password);
        PasswordData.put("password_confirmation", password_confirmation);

        TourneyConnectionAPI.getInstance().getAPIModel().doProfilePasswordUpdate(Authorization, PasswordData).enqueue(new Callback<mercy.tourney.Model.Basic.Response>() {
            @Override
            public void onResponse(Call<mercy.tourney.Model.Basic.Response> call, Response<mercy.tourney.Model.Basic.Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iAccountResponse.doSuccess(response.body());
                    } else {
                        iAccountResponse.doFail(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        iAccountResponse.doFail("Not Authorize!");
                    } else {
                        iAccountResponse.doFail("Something went wrong. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<mercy.tourney.Model.Basic.Response> call, Throwable t) {
                iAccountResponse.doConnectionError(R.string.connection_error);
            }
        });
    }
}
