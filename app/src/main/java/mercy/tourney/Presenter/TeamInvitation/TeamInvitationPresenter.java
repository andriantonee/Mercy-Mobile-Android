package mercy.tourney.Presenter.TeamInvitation;

import mercy.tourney.Model.Connection.TourneyConnectionAPI;
import mercy.tourney.Model.Response.TeamInvitationResponse;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Andrianto on 12/14/2016.
 */
public class TeamInvitationPresenter {
    iPresenterResponse iTeamInvitationPresenter;

    /**
     * For Communicating Between View and Presenter
     *
     * @param iTeamInvitationPresenter
     */
    public TeamInvitationPresenter(iPresenterResponse iTeamInvitationPresenter) {
        this.iTeamInvitationPresenter = iTeamInvitationPresenter;
    }

    /**
     * For Communicating Between Apps and API
     *
     * @param token_type
     * @param token
     */
    public void get_team_invitation(String token_type, String token) {
        String Authorization = token_type.concat(" ").concat(token);

        TourneyConnectionAPI.getInstance().getAPIModel().doGetTeamInvitation(Authorization).enqueue(new Callback<TeamInvitationResponse>() {
            @Override
            public void onResponse(Call<TeamInvitationResponse> call, Response<TeamInvitationResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iTeamInvitationPresenter.doSuccess(response.body());
                    } else {
                        iTeamInvitationPresenter.doFail(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        iTeamInvitationPresenter.doFail("Not Authorize!");
                    } else {
                        iTeamInvitationPresenter.doFail("Something went wrong. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<TeamInvitationResponse> call, Throwable t) {
                iTeamInvitationPresenter.doConnectionError(R.string.connection_error);
            }
        });
    }

    /**
     * For Communicating Between Apps and API
     *
     * @param token_type
     * @param token
     * @param team_id
     */
    public void accept_team_invitation(String token_type, String token, Integer team_id) {
        String Authorization = token_type.concat(" ").concat(token);

        TourneyConnectionAPI.getInstance().getAPIModel().doAcceptTeamInvitation(Authorization, team_id).enqueue(new Callback<mercy.tourney.Model.Basic.Response>() {
            @Override
            public void onResponse(Call<mercy.tourney.Model.Basic.Response> call, Response<mercy.tourney.Model.Basic.Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iTeamInvitationPresenter.doSuccess(response.body());
                    } else {
                        iTeamInvitationPresenter.doFail(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        iTeamInvitationPresenter.doFail("Not Authorize!");
                    } else {
                        iTeamInvitationPresenter.doFail("Something went wrong. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<mercy.tourney.Model.Basic.Response> call, Throwable t) {
                iTeamInvitationPresenter.doConnectionError(R.string.connection_error);
            }
        });
    }

    /**
     * For Communicating Between Apps and API
     *
     * @param token_type
     * @param token
     * @param team_id
     */
    public void reject_team_invitation(String token_type, String token, Integer team_id) {
        String Authorization = token_type.concat(" ").concat(token);

        TourneyConnectionAPI.getInstance().getAPIModel().doRejectTeamInvitation(Authorization, team_id).enqueue(new Callback<mercy.tourney.Model.Basic.Response>() {
            @Override
            public void onResponse(Call<mercy.tourney.Model.Basic.Response> call, Response<mercy.tourney.Model.Basic.Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iTeamInvitationPresenter.doSuccess(response.body());
                    } else {
                        iTeamInvitationPresenter.doFail(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        iTeamInvitationPresenter.doFail("Not Authorize!");
                    } else {
                        iTeamInvitationPresenter.doFail("Something went wrong. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<mercy.tourney.Model.Basic.Response> call, Throwable t) {
                iTeamInvitationPresenter.doConnectionError(R.string.connection_error);
            }
        });
    }
}
