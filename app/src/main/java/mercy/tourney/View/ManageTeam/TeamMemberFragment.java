package mercy.tourney.View.ManageTeam;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Response.TeamMemberResponse;
import mercy.tourney.Presenter.ManageTeam.TeamPresenter;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;

/**
 * Created by Andrianto on 12/8/2016.
 */
public class TeamMemberFragment extends Fragment implements iPresenterResponse {
    private TeamMemberFragment that;
    private Boolean isLeader = false;

    /**
     * Declare Component in XML Layout
     */
    RelativeLayout ScreenLoadingContainer, ScreenErrorContainer, ScreenDontHaveTeam, ScreenDontHaveMember;
    SwipeRefreshLayout team_member_swipe_refresh_layout, team_member_null_swipe_refresh_layout;
    RecyclerView team_member_recyclerview;
    FloatingActionButton add_member_floating_action_button;

    /**
     * Declare Variable for Instance Object
     */
    TeamPresenter instanceTeamPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_team_member, container, false);

        that = this;

        // Initialize Component from View
        ScreenLoadingContainer = (RelativeLayout) _view.findViewById(R.id.ScreenLoadingContainer);
        ScreenErrorContainer = (RelativeLayout) _view.findViewById(R.id.ScreenErrorContainer);
        ScreenDontHaveTeam = (RelativeLayout) _view.findViewById(R.id.ScreenDontHaveTeam);
        ScreenDontHaveMember = (RelativeLayout) _view.findViewById(R.id.ScreenDontHaveMember);
        team_member_swipe_refresh_layout = (SwipeRefreshLayout) _view.findViewById(R.id.SwipeRefreshLayoutTeamMember);
        team_member_null_swipe_refresh_layout = (SwipeRefreshLayout) _view.findViewById(R.id.SwipeRefreshLayoutTeamMemberNull);
        team_member_recyclerview = (RecyclerView) _view.findViewById(R.id.RecyclerViewTeamMember);

        // Initialize Instance Object
        instanceTeamPresenter = new TeamPresenter(this);

        // Configure SwipeRefreshLayout
        team_member_swipe_refresh_layout.setColorSchemeColors(getResources().getColor(R.color.colorSelectedTextPager));
        team_member_null_swipe_refresh_layout.setColorSchemeColors(getResources().getColor(R.color.colorSelectedTextPager));
        team_member_swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRetrofitGetTeamMember();
            }
        });
        team_member_null_swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRetrofitGetTeamMember();
            }
        });

        // Configure Recyclerview
        team_member_recyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        team_member_recyclerview.setLayoutManager(linearLayoutManager);

        // Set Click Listener to ScreenErrorContainer
        ScreenErrorContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGetTeamMember();
            }
        });

        ScreenDontHaveTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new CreateTeamDialogFragment(that)).show(getFragmentManager(), "Create Team Dialog - Team Member Fragment");
            }
        });

        // Floating Action Button
        add_member_floating_action_button = (FloatingActionButton) _view.findViewById(R.id.AddMemberFloatingActionButton);
        add_member_floating_action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ManageMemberActivity.class);
                startActivity(intent);
            }
        });

        return _view;
    }

    @Override
    public void doSuccess(Response response) {
        team_member_recyclerview.setAdapter(new MemberAdapter(((TeamMemberResponse) response).getMembers()));
        isLeader = ((TeamMemberResponse) response).getLeader();

        team_member_swipe_refresh_layout.setRefreshing(false);
        team_member_null_swipe_refresh_layout.setRefreshing(false);

        if (((TeamMemberResponse) response).getMembers() == null) {
            doShowScreen("Success But Null");
        } else {
            doShowScreen("Success");
        }
    }

    @Override
    public void doFail(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

        team_member_swipe_refresh_layout.setRefreshing(false);
        team_member_null_swipe_refresh_layout.setRefreshing(false);

        if (message.equals("User does not has a team.")) {
            doShowScreen("DontHaveTeam");
        } else {
            doShowScreen("Error");
        }
    }

    @Override
    public void doConnectionError(int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

        team_member_swipe_refresh_layout.setRefreshing(false);
        team_member_null_swipe_refresh_layout.setRefreshing(false);

        doShowScreen("Error");
    }

    public void doGetTeamMember() {
        doShowScreen("Loading");
        doRetrofitGetTeamMember();
    }

    public void doRetrofitGetTeamMember() {
        instanceTeamPresenter.get_team_member(((ManageTeamActivity) getActivity()).get_token_type(), ((ManageTeamActivity) getActivity()).get_access_token());
    }

    public void doShowScreen(String whichScreen) {
        if (whichScreen.equals("Error")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenDontHaveTeam.setVisibility(View.GONE);
            team_member_swipe_refresh_layout.setVisibility(View.GONE);
            team_member_null_swipe_refresh_layout.setVisibility(View.GONE);
            add_member_floating_action_button.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Loading")) {
            ScreenErrorContainer.setVisibility(View.GONE);
            ScreenDontHaveTeam.setVisibility(View.GONE);
            team_member_swipe_refresh_layout.setVisibility(View.GONE);
            team_member_null_swipe_refresh_layout.setVisibility(View.GONE);
            add_member_floating_action_button.setVisibility(View.GONE);
            ScreenLoadingContainer.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("DontHaveTeam")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.GONE);
            team_member_swipe_refresh_layout.setVisibility(View.GONE);
            team_member_null_swipe_refresh_layout.setVisibility(View.GONE);
            add_member_floating_action_button.setVisibility(View.GONE);
            ScreenDontHaveTeam.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Success")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.GONE);
            ScreenDontHaveTeam.setVisibility(View.GONE);
            team_member_null_swipe_refresh_layout.setVisibility(View.GONE);
            team_member_swipe_refresh_layout.setVisibility(View.VISIBLE);
            if (isLeader) {
                add_member_floating_action_button.setVisibility(View.VISIBLE);
            } else {
                add_member_floating_action_button.setVisibility(View.GONE);
            }
        } else if (whichScreen.equals("Success But Null")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.GONE);
            ScreenDontHaveTeam.setVisibility(View.GONE);
            team_member_swipe_refresh_layout.setVisibility(View.GONE);
            team_member_null_swipe_refresh_layout.setVisibility(View.VISIBLE);
            if (isLeader) {
                add_member_floating_action_button.setVisibility(View.VISIBLE);
            } else {
                add_member_floating_action_button.setVisibility(View.GONE);
            }
        }
    }
}
