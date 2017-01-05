package mercy.tourney.Presenter.ManageTeam;

import mercy.tourney.Model.Connection.TourneyConnectionAPI;
import mercy.tourney.Model.Response.MemberResponse;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Andrianto on 12/12/2016.
 */
public class InviteMemberPresenter {
    iPresenterResponse iInviteMemberPresenter;

    /**
     * For Communicating Between View and Presenter
     *
     * @param iInviteMemberPresenter
     */
    public InviteMemberPresenter(iPresenterResponse iInviteMemberPresenter) {
        this.iInviteMemberPresenter = iInviteMemberPresenter;
    }

    /**
     * For Communicating Between Apps and API
     *
     * @param token_type
     * @param token
     */
    public void get_team_member_pending_invitations(String token_type, String token) {
        String Authorization = token_type.concat(" ").concat(token);

        TourneyConnectionAPI.getInstance().getAPIModel().doGetTeamMemberPendingInvitations(Authorization).enqueue(new Callback<MemberResponse>() {
            @Override
            public void onResponse(Call<MemberResponse> call, Response<MemberResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iInviteMemberPresenter.doSuccess(response.body());
                    } else {
                        iInviteMemberPresenter.doFail(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        iInviteMemberPresenter.doFail("Not Authorize!");
                    } else {
                        iInviteMemberPresenter.doFail("Something went wrong. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<MemberResponse> call, Throwable t) {
                iInviteMemberPresenter.doConnectionError(R.string.connection_error);
            }
        });
    }

    /**
     * For Communicating Between Apps and API
     *
     * @param keyword
     */
    public void search_member(String token_type, String token, String keyword) {
        String Authorization = token_type.concat(" ").concat(token);

        TourneyConnectionAPI.getInstance().getAPIModel().doSearchMember(Authorization, keyword).enqueue(new Callback<MemberResponse>() {
            @Override
            public void onResponse(Call<MemberResponse> call, Response<MemberResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iInviteMemberPresenter.doSuccess(response.body());
                    } else {
                        iInviteMemberPresenter.doFail(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        iInviteMemberPresenter.doFail("Not Authorize!");
                    } else {
                        iInviteMemberPresenter.doFail("Something went wrong. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<MemberResponse> call, Throwable t) {
                iInviteMemberPresenter.doConnectionError(R.string.connection_error);
            }
        });
    }

    /**
     * For Communicating Between Apps and API
     *
     * @param token_type
     * @param token
     * @param username
     */
    public void invite_member(String token_type, String token, String username) {
        String Authorization = token_type.concat(" ").concat(token);

        TourneyConnectionAPI.getInstance().getAPIModel().doInviteMember(Authorization, username).enqueue(new Callback<mercy.tourney.Model.Basic.Response>() {
            @Override
            public void onResponse(Call<mercy.tourney.Model.Basic.Response> call, Response<mercy.tourney.Model.Basic.Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iInviteMemberPresenter.doSuccess(response.body());
                    } else {
                        iInviteMemberPresenter.doFail(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        iInviteMemberPresenter.doFail("Not Authorize!");
                    } else {
                        iInviteMemberPresenter.doFail("Something went wrong. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<mercy.tourney.Model.Basic.Response> call, Throwable t) {
                iInviteMemberPresenter.doConnectionError(R.string.connection_error);
            }
        });
    }

    /**
     * For Communicating Between Apps and API
     *
     * @param token_type
     * @param token
     * @param username
     */
    public void cancel_invite_member(String token_type, String token, String username) {
        String Authorization = token_type.concat(" ").concat(token);

        TourneyConnectionAPI.getInstance().getAPIModel().doCancelInviteMember(Authorization, username).enqueue(new Callback<mercy.tourney.Model.Basic.Response>() {
            @Override
            public void onResponse(Call<mercy.tourney.Model.Basic.Response> call, Response<mercy.tourney.Model.Basic.Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        iInviteMemberPresenter.doSuccess(response.body());
                    } else {
                        iInviteMemberPresenter.doFail(response.body().getMessage());
                    }
                } else {
                    if (response.code() == 401) {
                        iInviteMemberPresenter.doFail("Not Authorize!");
                    } else {
                        iInviteMemberPresenter.doFail("Something went wrong. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<mercy.tourney.Model.Basic.Response> call, Throwable t) {
                iInviteMemberPresenter.doConnectionError(R.string.connection_error);
            }
        });
    }
}
