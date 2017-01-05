package mercy.tourney.Model.Controller;

import android.support.annotation.Nullable;

import java.util.Map;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Response.LoginResponse;
import mercy.tourney.Model.Response.MemberResponse;
import mercy.tourney.Model.Response.TeamDetailResponse;
import mercy.tourney.Model.Response.TeamInvitationResponse;
import mercy.tourney.Model.Response.TeamMemberResponse;
import mercy.tourney.Model.Response.TournamentContentResponse;
import mercy.tourney.Model.Response.TournamentDetailResponse;
import mercy.tourney.Model.Response.TournamentResponse;
import mercy.tourney.Model.Response.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Andrianto on 11/25/2016.
 */
public interface TourneyAPIModel {
    /**
     * Routes From API
     */
    @POST("/api/login")
    Call<LoginResponse> doLogin(@Body Map<String, String> LoginData);

    @PUT("/api/register")
    Call<Response> doRegister(@Body Map<String, String> RegisterData);

    @POST("/api/profile/update")
    Call<UserResponse> doProfileUpdate(@Header("Authorization") String Authorization, @Body Map<String, String> ProfileData);

    @POST("/api/profile/password/update")
    Call<Response> doProfilePasswordUpdate(@Header("Authorization") String Authorization, @Body Map<String, String> PasswordData);

    @GET("/api/profile/team/invitation")
    Call<TeamInvitationResponse> doGetTeamInvitation(@Header("Authorization") String Authorization);

    @FormUrlEncoded
    @POST("/api/profile/team/invitation/accept")
    Call<Response> doAcceptTeamInvitation(@Header("Authorization") String Authorization, @Field("teams_id") Integer TeamsId);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/api/profile/team/invitation/reject", hasBody = true)
    Call<Response> doRejectTeamInvitation(@Header("Authorization") String Authorization, @Field("teams_id") Integer TeamsId);

    @GET("/api/team/detail")
    Call<TeamDetailResponse> doGetTeamDetail(@Header("Authorization") String Authorization);

    @GET("/api/team/member")
    Call<TeamMemberResponse> doGetTeamMember(@Header("Authorization") String Authorization);

    @GET("/api/team/member/pending-invitations")
    Call<MemberResponse> doGetTeamMemberPendingInvitations(@Header("Authorization") String Authorization);

    @PUT("/api/team/create")
    Call<Response> doCreateTeam(@Header("Authorization") String Authorization, @Body Map<String, String> TeamData);

    @POST("/api/team/detail/update")
    Call<Response> doTeamUpdate(@Header("Authorization") String Authorization, @Body Map<String, String> TeamData);

    @DELETE("/api/team/disband")
    Call<Response> doTeamDisband(@Header("Authorization") String Authorization);

    @GET("/api/member/search")
    Call<MemberResponse> doSearchMember(@Header("Authorization") String Authorization, @Query("keyword") String keyword);

    @FormUrlEncoded
    @PUT("/api/member/invite")
    Call<Response> doInviteMember(@Header("Authorization") String Authorization, @Field("username") String Username);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/api/member/invite/cancel", hasBody = true)
    Call<Response> doCancelInviteMember(@Header("Authorization") String Authorization, @Field("username") String Username);

    @GET("/api/tournaments")
    Call<TournamentResponse> doGetTournament(@QueryMap Map<String, Object> FilteringData);

    @GET("/api/tournament/{id}/content")
    Call<TournamentContentResponse> doGetTournamentContent(@Nullable @Header("Authorization") String Authorization, @Path("id") Integer TournamentID);

    @PUT("/api/tournament/{id}/register")
    Call<Response> doRegisterTournament(@Header("Authorization") String Authorization, @Path("id") Integer TournamentID);

    @GET("/api/tournament/{id}/detail")
    Call<TournamentDetailResponse> doGetTournamentDetail(@Nullable @Header("Authorization") String Authorization, @Path("id") Integer TournamentID);

    @GET("/api/tournament/team")
    Call<TournamentResponse> doGetTeamTournament(@Header("Authorization") String Authorization);
}
