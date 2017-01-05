package mercy.tourney.View.TeamInvitations;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Response.TeamInvitationResponse;
import mercy.tourney.Model.SessionManager;
import mercy.tourney.Presenter.TeamInvitation.TeamInvitationPresenter;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;

public class TeamInvitationActivity extends AppCompatActivity {
    private Activity that;

    /**
     * Declare Component in XML Layout
     */
    RelativeLayout ScreenLoadingContainer, ScreenErrorContainer;
    SwipeRefreshLayout team_invitation_swipe_refresh_layout, team_invitation_null_swipe_refresh_layout;
    RecyclerView team_invitation_recyclerview;

    /**
     * Declare Variable for Instance Object
     */
    SessionManager instanceSessionManager;
    TeamInvitationPresenter instanceGetTeamInvitationPresenter, instanceManageTeamInvitationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_invitations);

        that = this;

        // Set Action Bar Title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Team Invitation");

        // Initialize Component from View
        ScreenLoadingContainer = (RelativeLayout) findViewById(R.id.ScreenLoadingContainer);
        ScreenErrorContainer = (RelativeLayout) findViewById(R.id.ScreenErrorContainer);
        team_invitation_swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayoutTeamInvitation);
        team_invitation_null_swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayoutTeamInvitationNull);
        team_invitation_recyclerview = (RecyclerView) findViewById(R.id.RecyclerViewTeamInvitation);

        // Initialize Instance Object
        instanceSessionManager = new SessionManager(getApplicationContext());
        instanceGetTeamInvitationPresenter = new TeamInvitationPresenter(new iPresenterResponse() {
            @Override
            public void doSuccess(Response response) {
                team_invitation_recyclerview.setAdapter(new TeamInvitationAdapter(((TeamInvitationResponse) response).getTeams(), instanceManageTeamInvitationPresenter, that));

                team_invitation_swipe_refresh_layout.setRefreshing(false);
                team_invitation_null_swipe_refresh_layout.setRefreshing(false);

                if (((TeamInvitationResponse) response).getTeams() == null) {
                    doShowScreen("Success But Null");
                } else {
                    doShowScreen("Success");
                }
            }

            @Override
            public void doFail(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                team_invitation_swipe_refresh_layout.setRefreshing(false);
                team_invitation_null_swipe_refresh_layout.setRefreshing(false);

                doShowScreen("Error");
            }

            @Override
            public void doConnectionError(int message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                team_invitation_swipe_refresh_layout.setRefreshing(false);
                team_invitation_null_swipe_refresh_layout.setRefreshing(false);

                doShowScreen("Error");
            }
        });
        instanceManageTeamInvitationPresenter = new TeamInvitationPresenter(new iPresenterResponse() {
            @Override
            public void doSuccess(Response response) {
                Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void doFail(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                doGetTeamInvitation();
            }

            @Override
            public void doConnectionError(int message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        // Configure SwipeRefreshLayout
        team_invitation_swipe_refresh_layout.setColorSchemeColors(getResources().getColor(R.color.colorSelectedTextPager));
        team_invitation_null_swipe_refresh_layout.setColorSchemeColors(getResources().getColor(R.color.colorSelectedTextPager));
        team_invitation_swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRetrofitGetTeamInvitation();
            }
        });
        team_invitation_null_swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRetrofitGetTeamInvitation();
            }
        });

        // Set Click Listener to ScreenErrorContainer
        ScreenErrorContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGetTeamInvitation();
            }
        });

        // Configure Recyclerview
        team_invitation_recyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        team_invitation_recyclerview.setLayoutManager(linearLayoutManager);

        // Load Data From API
        doGetTeamInvitation();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            this.finish();
        }

        return true;
    }

    public void doShowScreen(String whichScreen) {
        if (whichScreen.equals("Error")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            team_invitation_swipe_refresh_layout.setVisibility(View.GONE);
            team_invitation_null_swipe_refresh_layout.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Loading")) {
            ScreenErrorContainer.setVisibility(View.GONE);
            team_invitation_swipe_refresh_layout.setVisibility(View.GONE);
            team_invitation_null_swipe_refresh_layout.setVisibility(View.GONE);
            ScreenLoadingContainer.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Success")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.GONE);
            team_invitation_null_swipe_refresh_layout.setVisibility(View.GONE);
            team_invitation_swipe_refresh_layout.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Success But Null")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.GONE);
            team_invitation_swipe_refresh_layout.setVisibility(View.GONE);
            team_invitation_null_swipe_refresh_layout.setVisibility(View.VISIBLE);
        }
    }

    public String getTokenType() {
        return instanceSessionManager.getTokenLoggedIn().getToken_type();
    }

    public String getAccessToken() {
        return instanceSessionManager.getTokenLoggedIn().getAccess_token();
    }

    public void doGetTeamInvitation() {
        doShowScreen("Loading");
        doRetrofitGetTeamInvitation();
    }

    public void doRetrofitGetTeamInvitation() {
        instanceGetTeamInvitationPresenter.get_team_invitation(getTokenType(), getAccessToken());
    }
}
