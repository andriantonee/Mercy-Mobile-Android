package mercy.tourney.Presenter.Portal;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import mercy.tourney.Model.Connection.TourneyConnectionAPI;
import mercy.tourney.Model.Response.TournamentContentResponse;
import mercy.tourney.Model.Response.TournamentDetailResponse;
import mercy.tourney.Model.Response.TournamentResponse;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Andrianto on 12/15/2016.
 */
public class TournamentPresenter {
    iPresenterResponse iTournamentResponse;

    /**
     * For Communicating Between View and Presenter
     *
     * @param iTournamentResponse
     */
    public TournamentPresenter(iPresenterResponse iTournamentResponse) {
        this.iTournamentResponse = iTournamentResponse;
    }

    /**
     * For Communicating Between Apps and API
     *
     * @param keyword
     * @param game_category
     */
    public void get_tournaments(@Nullable String keyword, @Nullable Integer game_category) {
        Map<String, Object> FilteringData = new HashMap<>();
        if (keyword != null) {
            FilteringData.put("keyword", keyword);
        }
        if (game_category != null && game_category != 0) {
            FilteringData.put("game_category", game_category);
        }

        TourneyConnectionAPI.getInstance().getAPIModel().doGetTournament(FilteringData).enqueue(new Callback<TournamentResponse>() {
            @Override
            public void onResponse(Call<TournamentResponse> call, Response<TournamentResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iTournamentResponse.doSuccess(response.body());
                    } else {
                        iTournamentResponse.doFail(response.body().getMessage());
                    }
                } else {
                    iTournamentResponse.doFail("Something went wrong. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<TournamentResponse> call, Throwable t) {
                iTournamentResponse.doConnectionError(R.string.connection_error);
            }
        });
    }

    public void get_tournament_content(@Nullable String token_type, @Nullable String token, Integer tournament_id) {
        String Authorization = null;
        if (token_type != null && token != null) {
            Authorization = token_type.concat(" ").concat(token);
        }

        TourneyConnectionAPI.getInstance().getAPIModel().doGetTournamentContent(Authorization, tournament_id).enqueue(new Callback<TournamentContentResponse>() {
            @Override
            public void onResponse(Call<TournamentContentResponse> call, Response<TournamentContentResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iTournamentResponse.doSuccess(response.body());
                    } else {
                        iTournamentResponse.doFail(response.body().getMessage());
                    }
                } else {
                    iTournamentResponse.doFail("Something went wrong. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<TournamentContentResponse> call, Throwable t) {
                iTournamentResponse.doConnectionError(R.string.connection_error);
            }
        });
    }

    public void register_tournament(String token_type, String token, Integer tournament_id) {
        String Authorization = token_type.concat(" ").concat(token);

        TourneyConnectionAPI.getInstance().getAPIModel().doRegisterTournament(Authorization, tournament_id).enqueue(new Callback<mercy.tourney.Model.Basic.Response>() {
            @Override
            public void onResponse(Call<mercy.tourney.Model.Basic.Response> call, Response<mercy.tourney.Model.Basic.Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iTournamentResponse.doSuccess(response.body());
                    } else {
                        iTournamentResponse.doFail(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        iTournamentResponse.doFail("Not Authorize!");
                    } else {
                        iTournamentResponse.doFail("Something went wrong. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<mercy.tourney.Model.Basic.Response> call, Throwable t) {
                iTournamentResponse.doConnectionError(R.string.connection_error);
            }
        });
    }

    public void get_tournament_detail(@Nullable String token_type, @Nullable String token, Integer tournament_id) {
        String Authorization = null;
        if (token_type != null && token != null) {
            Authorization = token_type.concat(" ").concat(token);
        }

        TourneyConnectionAPI.getInstance().getAPIModel().doGetTournamentDetail(Authorization, tournament_id).enqueue(new Callback<TournamentDetailResponse>() {
            @Override
            public void onResponse(Call<TournamentDetailResponse> call, Response<TournamentDetailResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iTournamentResponse.doSuccess(response.body());
                    } else {
                        iTournamentResponse.doFail(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        iTournamentResponse.doFail("Not Authorize!");
                    } else {
                        iTournamentResponse.doFail("Something went wrong. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<TournamentDetailResponse> call, Throwable t) {
                iTournamentResponse.doConnectionError(R.string.connection_error);
            }
        });
    }

    public void get_team_tournament(String token_type, String token) {
        String Authorization = token_type.concat(" ").concat(token);

        TourneyConnectionAPI.getInstance().getAPIModel().doGetTeamTournament(Authorization).enqueue(new Callback<TournamentResponse>() {
            @Override
            public void onResponse(Call<TournamentResponse> call, Response<TournamentResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iTournamentResponse.doSuccess(response.body());
                    } else {
                        iTournamentResponse.doFail(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        iTournamentResponse.doFail("Not Authorize!");
                    } else {
                        iTournamentResponse.doFail("Something went wrong. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<TournamentResponse> call, Throwable t) {
                iTournamentResponse.doConnectionError(R.string.connection_error);
            }
        });
    }
}
