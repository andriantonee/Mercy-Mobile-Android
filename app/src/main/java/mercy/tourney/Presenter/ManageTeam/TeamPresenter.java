package mercy.tourney.Presenter.ManageTeam;

import java.util.HashMap;
import java.util.Map;

import mercy.tourney.Model.Connection.TourneyConnectionAPI;
import mercy.tourney.Model.Response.TeamDetailResponse;
import mercy.tourney.Model.Response.TeamMemberResponse;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Andrianto on 12/6/2016.
 */
public class TeamPresenter {
    iPresenterResponse iTeamResponse;

    /**
     * For Communicating Between View and Presenter
     *
     * @param iTeamResponse
     */
    public TeamPresenter(iPresenterResponse iTeamResponse) {
        this.iTeamResponse = iTeamResponse;
    }

    /**
     * For Communicating Between Apps and API
     *
     * @param token_type
     * @param token
     */
    public void get_team_detail(String token_type, String token) {
        String Authorization = token_type.concat(" ").concat(token);

        TourneyConnectionAPI.getInstance().getAPIModel().doGetTeamDetail(Authorization).enqueue(new Callback<TeamDetailResponse>() {
            @Override
            public void onResponse(Call<TeamDetailResponse> call, Response<TeamDetailResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iTeamResponse.doSuccess(response.body());
                    } else {
                        iTeamResponse.doFail(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        iTeamResponse.doFail("Not Authorize!");
                    } else {
                        iTeamResponse.doFail("Something went wrong. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<TeamDetailResponse> call, Throwable t) {
                iTeamResponse.doConnectionError(R.string.connection_error);
            }
        });
    }

    /**
     * For Communicating Between Apps and API
     *
     * @param token_type
     * @param token
     */
    public void get_team_member(String token_type, String token) {
        String Authorization = token_type.concat(" ").concat(token);

        TourneyConnectionAPI.getInstance().getAPIModel().doGetTeamMember(Authorization).enqueue(new Callback<TeamMemberResponse>() {
            @Override
            public void onResponse(Call<TeamMemberResponse> call, Response<TeamMemberResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iTeamResponse.doSuccess(response.body());
                    } else {
                        iTeamResponse.doFail(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        iTeamResponse.doFail("Not Authorize!");
                    } else {
                        iTeamResponse.doFail("Something went wrong. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<TeamMemberResponse> call, Throwable t) {
                iTeamResponse.doConnectionError(R.string.connection_error);
            }
        });
    }

    /**
     * For Communicating Between Apps and API
     *
     * @param token_type
     * @param token
     * @param team_name
     */
    public void create_team(String token_type, String token, String team_name) {
        String Authorization = token_type.concat(" ").concat(token);
        Map<String, String> TeamData = new HashMap<>();
        TeamData.put("team_name", team_name);

        TourneyConnectionAPI.getInstance().getAPIModel().doCreateTeam(Authorization, TeamData).enqueue(new Callback<mercy.tourney.Model.Basic.Response>() {
            @Override
            public void onResponse(Call<mercy.tourney.Model.Basic.Response> call, Response<mercy.tourney.Model.Basic.Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iTeamResponse.doSuccess(response.body());
                    } else {
                        iTeamResponse.doFail(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        iTeamResponse.doFail("Not Authorize!");
                    } else {
                        iTeamResponse.doFail("Something went wrong. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<mercy.tourney.Model.Basic.Response> call, Throwable t) {
                iTeamResponse.doConnectionError(R.string.connection_error);
            }
        });
    }

    /**
     * For Communicating Between Apps and API
     *
     * @param token_type
     * @param token
     * @param team_name
     */
    public void update_team(String token_type, String token, String team_name) {
        String Authorization = token_type.concat(" ").concat(token);
        Map<String, String> TeamData = new HashMap<>();
        TeamData.put("team_name", team_name);

        TourneyConnectionAPI.getInstance().getAPIModel().doTeamUpdate(Authorization, TeamData).enqueue(new Callback<mercy.tourney.Model.Basic.Response>() {
            @Override
            public void onResponse(Call<mercy.tourney.Model.Basic.Response> call, Response<mercy.tourney.Model.Basic.Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iTeamResponse.doSuccess(response.body());
                    } else {
                        iTeamResponse.doFail(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        iTeamResponse.doFail("Not Authorize!");
                    } else {
                        iTeamResponse.doFail("Something went wrong. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<mercy.tourney.Model.Basic.Response> call, Throwable t) {
                iTeamResponse.doConnectionError(R.string.connection_error);
            }
        });
    }

    /**
     * For Communicating Between Apps and API
     *
     * @param token_type
     * @param token
     */
    public void disband_team(String token_type, String token) {
        String Authorization = token_type.concat(" ").concat(token);

        TourneyConnectionAPI.getInstance().getAPIModel().doTeamDisband(Authorization).enqueue(new Callback<mercy.tourney.Model.Basic.Response>() {
            @Override
            public void onResponse(Call<mercy.tourney.Model.Basic.Response> call, Response<mercy.tourney.Model.Basic.Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iTeamResponse.doSuccess(response.body());
                    } else {
                        iTeamResponse.doFail(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        iTeamResponse.doFail("Not Authorize!");
                    } else {
                        iTeamResponse.doFail("Something went wrong. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<mercy.tourney.Model.Basic.Response> call, Throwable t) {
                iTeamResponse.doConnectionError(R.string.connection_error);
            }
        });
    }
}
