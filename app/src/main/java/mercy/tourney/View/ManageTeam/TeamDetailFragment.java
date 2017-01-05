package mercy.tourney.View.ManageTeam;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Basic.Team;
import mercy.tourney.Model.Response.TeamDetailResponse;
import mercy.tourney.Presenter.ManageTeam.TeamPresenter;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;
import mercy.tourney.View.Authentication.AuthActivity;
import mercy.tourney.View.Portal.PortalSearchResultActivity;

/**
 * Created by Andrianto on 12/4/2016.
 */
public class TeamDetailFragment extends Fragment implements iPresenterResponse {
    private Team team;
    private Boolean isLeader;
    private AlertDialog alertDialogDisband;
    private TeamDetailFragment that;

    /**
     * Declare Component in XML Layout
     */
    RelativeLayout ScreenLoadingContainer, ScreenErrorContainer, ScreenDontHaveTeam;
    ScrollView team_detail_scroll_view;
    TextView team_name_text_view, team_leader_name_text_view, team_leader_username_text_view, team_created_at_text_view;
    LinearLayout EditTeamNameContainer;
    EditText team_name_edit_text;
    Button update_team_name_button, disband_team_button;
    FloatingActionButton tournament_list_floating_action_button;

    /**
     * Used for handle spamming button
     */
    ProgressDialog progress;

    /**
     * Declare Variable for Instance Object
     */
    TeamPresenter instanceTeamPresenter, instanceUpdateTeamPresenter, instanceDisbandTeamPresenter;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_team_detail, container, false);

        alertDialogDisband = new AlertDialog.Builder(getActivity())
                .setTitle("Are you sure want to disband this team?")
                .setPositiveButton("Yes, I am sure.",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                instanceDisbandTeamPresenter.disband_team(((ManageTeamActivity) getActivity()).get_token_type(), ((ManageTeamActivity) getActivity()).get_access_token());
                            }
                        })
                .setNegativeButton("No, Thanks.",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                .create();
        that = this;

        // Initialize Component from View
        ScreenLoadingContainer = (RelativeLayout) _view.findViewById(R.id.ScreenLoadingContainer);
        ScreenErrorContainer = (RelativeLayout) _view.findViewById(R.id.ScreenErrorContainer);
        ScreenDontHaveTeam = (RelativeLayout) _view.findViewById(R.id.ScreenDontHaveTeam);
        team_detail_scroll_view = (ScrollView) _view.findViewById(R.id.ScrollViewTeamDetail);
        team_name_text_view = (TextView) _view.findViewById(R.id.TextViewTeamName);
        team_leader_name_text_view = (TextView) _view.findViewById(R.id.TextViewTeamLeaderName);
        team_leader_username_text_view = (TextView) _view.findViewById(R.id.TextViewTeamLeaderUsername);
        team_created_at_text_view = (TextView) _view.findViewById(R.id.TextViewTeamCreatedAt);
        EditTeamNameContainer = (LinearLayout) _view.findViewById(R.id.EditTeamNameContainer);
        team_name_edit_text = (EditText) _view.findViewById(R.id.EditTextTeamName);
        update_team_name_button = (Button) _view.findViewById(R.id.ButtonUpdateTeamName);
        disband_team_button = (Button) _view.findViewById(R.id.ButtonDisbandTeam);
        tournament_list_floating_action_button = (FloatingActionButton) _view.findViewById(R.id.TournamentListFloatingActionButton);

        // Initialize Progress Dialog
        progress = new ProgressDialog(getContext());
        progress.setIndeterminate(true);
        progress.setMessage("Loading...");

        // Initialize Instance Object
        instanceTeamPresenter = new TeamPresenter(this);
        instanceUpdateTeamPresenter = new TeamPresenter(new iPresenterResponse() {
            @Override
            public void doSuccess(Response response) {
                Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();

                team.setTeam_name(team_name_edit_text.getText().toString());
                team_name_text_view.setText(team.getTeam_name());

                progress.dismiss();
            }

            @Override
            public void doFail(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                progress.dismiss();

                if (message.equals("Not Authorize!")) {
                    Intent intent = new Intent(getContext(), AuthActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }

            @Override
            public void doConnectionError(int message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                progress.dismiss();
            }
        });
        instanceDisbandTeamPresenter = new TeamPresenter(new iPresenterResponse() {
            @Override
            public void doSuccess(Response response) {
                doGetTeamDetail();
                ((ManageTeamActivity) getActivity()).setMustRefreshTeamMember(true);
            }

            @Override
            public void doFail(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void doConnectionError(int message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        // Team Detail Scroll View
        team_detail_scroll_view.setVerticalScrollBarEnabled(false);
        team_detail_scroll_view.setHorizontalScrollBarEnabled(false);

        // Set Button (Update Team Name) OnClickListener
        update_team_name_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String team_name = team_name_edit_text.getText().toString();
                if (TextUtils.isEmpty(team_name)) {
                    Toast.makeText(getContext(), "Please fill the blank field.", Toast.LENGTH_SHORT).show();
                } else {
                    progress.show();
                    instanceUpdateTeamPresenter.update_team(((ManageTeamActivity) getActivity()).get_token_type(), ((ManageTeamActivity) getActivity()).get_access_token(), team_name);
                }
            }
        });

        // Set Button (Disband) OnClickListener
        disband_team_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogDisband.show();
            }
        });

        // Set Click to Retry in RelativeLayout ScreenErrorContainer
        ScreenErrorContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGetTeamDetail();
            }
        });

        // Add OnClickListener to create team
        ScreenDontHaveTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new CreateTeamDialogFragment(that)).show(getFragmentManager(), "Create Team Dialog - Team Detail Fragment");
            }
        });

        // Set Floating Button Action
        tournament_list_floating_action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PortalSearchResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TEAM NAME", team_name_text_view.getText().toString());
                intent.putExtra("TEAM", bundle);
                startActivity(intent);
            }
        });

        doGetTeamDetail();

        return _view;
    }

    @Override
    public void doSuccess(Response response) {
        team = ((TeamDetailResponse) response).getTeam();
        isLeader = ((TeamDetailResponse) response).getLeader();

        // Set TextView Value from Private Variable
        team_name_text_view.setText(team.getTeam_name());
        team_leader_name_text_view.setText(team.getLeader_name());
        team_leader_username_text_view.setText(team.getLeader_username());
        Date date = new Date(Long.parseLong(String.valueOf(team.getCreated_at())) * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        team_created_at_text_view.setText(simpleDateFormat.format(date));

        // Set EditText Value From API
        team_name_edit_text.setText(team.getTeam_name());

        // Check user status if leader visible EditTeamNameContainer
        if (isLeader) {
            EditTeamNameContainer.setVisibility(View.VISIBLE);
        } else {
            EditTeamNameContainer.setVisibility(View.GONE);
        }

        doShowScreen("Success");
    }

    @Override
    public void doFail(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

//        if (message.equals("Not Authorize!")) {
//            Intent intent = new Intent(getContext(), AuthActivity.class);
//            startActivity(intent);
//            getActivity().finish();
//        } else {
            if (message.equals("User does not has a team.")) {
                doShowScreen("DontHaveTeam");
            } else {
                doShowScreen("Error");
            }
//        }
    }

    @Override
    public void doConnectionError(int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

        doShowScreen("Error");
    }

    public void doGetTeamDetail() {
        doShowScreen("Loading");
        instanceTeamPresenter.get_team_detail(((ManageTeamActivity) getActivity()).get_token_type(), ((ManageTeamActivity) getActivity()).get_access_token());
    }

    public void doShowScreen(String whichScreen) {
        if (whichScreen.equals("Error")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenDontHaveTeam.setVisibility(View.GONE);
            team_detail_scroll_view.setVisibility(View.GONE);
            tournament_list_floating_action_button.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Loading")) {
            ScreenErrorContainer.setVisibility(View.GONE);
            ScreenDontHaveTeam.setVisibility(View.GONE);
            team_detail_scroll_view.setVisibility(View.GONE);
            tournament_list_floating_action_button.setVisibility(View.GONE);
            ScreenLoadingContainer.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("DontHaveTeam")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.GONE);
            team_detail_scroll_view.setVisibility(View.GONE);
            tournament_list_floating_action_button.setVisibility(View.GONE);
            ScreenDontHaveTeam.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Success")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.GONE);
            ScreenDontHaveTeam.setVisibility(View.GONE);
            team_detail_scroll_view.setVisibility(View.VISIBLE);
            tournament_list_floating_action_button.setVisibility(View.VISIBLE);
        }
    }
}
