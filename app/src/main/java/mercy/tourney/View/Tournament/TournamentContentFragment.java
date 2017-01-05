package mercy.tourney.View.Tournament;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Response.TournamentContentResponse;
import mercy.tourney.Model.SessionManager;
import mercy.tourney.Presenter.Portal.TournamentPresenter;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;
import mercy.tourney.View.Authentication.AuthActivity;

/**
 * Created by Andrianto on 12/17/2016.
 */
public class TournamentContentFragment extends Fragment {
    private Integer tournament_id;
    private AlertDialog alertDialogRegisterTournament;

    /**
     * Declare Component in XML Layout
     */
    RelativeLayout ScreenLoadingContainer, ScreenErrorContainer, ScreenTournamentContent;
    ScrollView tournament_content_scroll_view;
    TextView tournament_name_text_view, game_category_text_view, registration_open_text_view, registration_close_text_view, member_per_team_capacity_text_view, location_text_view, tournament_description_text_view, tournament_rules_text_view;
    Button register_or_tournament_detail_button;

    /**
     * Used for handle spamming button
     */
    ProgressDialog progress;

    /**
     * Declare Variable for Instance Object
     */
    SessionManager instanceSessionManager;
    TournamentPresenter instanceTournamentPresenter, instanceRegisterTournamentPresenter;

    public static TournamentContentFragment newInstance(Integer tournament_id) {
        Bundle args = new Bundle();
        args.putInt("TOURNAMENT ID", tournament_id);
        TournamentContentFragment fragment = new TournamentContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            tournament_id = bundle.getInt("TOURNAMENT ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_tournament_content, container, false);

        // Initialize Component from View
        ScreenLoadingContainer = (RelativeLayout) _view.findViewById(R.id.ScreenLoadingContainer);
        ScreenErrorContainer = (RelativeLayout) _view.findViewById(R.id.ScreenErrorContainer);
        ScreenTournamentContent = (RelativeLayout) _view.findViewById(R.id.ScreenTournamentContent);
        tournament_content_scroll_view = (ScrollView) _view.findViewById(R.id.ScrollViewTournamentContent);
        tournament_name_text_view = (TextView) _view.findViewById(R.id.TextViewTournamentName);
        game_category_text_view = (TextView) _view.findViewById(R.id.TextViewGameCategory);
        registration_open_text_view = (TextView) _view.findViewById(R.id.TextViewRegistrationOpen);
        registration_close_text_view = (TextView) _view.findViewById(R.id.TextViewRegistrationOpen);
        member_per_team_capacity_text_view = (TextView) _view.findViewById(R.id.TextViewMemberPerTeamCapacity);
        location_text_view = (TextView) _view.findViewById(R.id.TextViewLocation);
        tournament_description_text_view = (TextView) _view.findViewById(R.id.TextViewTournamentDescription);
        tournament_rules_text_view = (TextView) _view.findViewById(R.id.TextViewTournamentRules);
        register_or_tournament_detail_button = (Button) _view.findViewById(R.id.ButtonRegisterOrTournamentDetail);

        // Initialize Progress Dialog
        progress = new ProgressDialog(getContext());
        progress.setIndeterminate(true);
        progress.setMessage("Loading...");

        // Initialize Instance Object
        instanceSessionManager = new SessionManager(getContext());
        instanceTournamentPresenter = new TournamentPresenter(new iPresenterResponse() {
            @Override
            public void doSuccess(Response response) {
                TournamentContentResponse tournamentContentResponse = (TournamentContentResponse) response;

                alertDialogRegisterTournament = new AlertDialog.Builder(getActivity())
                        .setTitle(tournamentContentResponse.getTournament().getName())
                        .setView(R.layout.alert_dialog_register_tournament)
                        .setPositiveButton("Yes, I am sure.",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        progress.show();
                                        instanceRegisterTournamentPresenter.register_tournament(instanceSessionManager.getTokenLoggedIn().getToken_type(), instanceSessionManager.getTokenLoggedIn().getAccess_token(), tournament_id);
                                    }
                                })
                        .setNegativeButton("No, I am not sure.",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                        .create();

                // Setting action bar title
                ((TournamentContentActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ((TournamentContentActivity) getActivity()).getSupportActionBar().setTitle(tournamentContentResponse.getTournament().getName());

                Date registration_open_date = new Date(Long.parseLong(String.valueOf(tournamentContentResponse.getTournament().getRegistration_open())) * 1000);
                Date registration_close_date = new Date(Long.parseLong(String.valueOf(tournamentContentResponse.getTournament().getRegistration_close())) * 1000);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

                // Set data from API to textview
                tournament_name_text_view.setText(tournamentContentResponse.getTournament().getName());
                game_category_text_view.setText(tournamentContentResponse.getTournament().getCategory().getName());
                registration_open_text_view.setText(simpleDateFormat.format(registration_open_date));
                registration_close_text_view.setText(simpleDateFormat.format(registration_close_date));
                member_per_team_capacity_text_view.setText(String.valueOf(tournamentContentResponse.getTournament().getMember_participant_in_one_team()));
                location_text_view.setText(tournamentContentResponse.getTournament().getLocation());
                tournament_description_text_view.setText(tournamentContentResponse.getTournament().getDescription());
                tournament_rules_text_view.setText(tournamentContentResponse.getTournament().getRules());

                // Setting onClickListener on register_button
                if (tournamentContentResponse.getAlreadyRegister() || !tournamentContentResponse.getLeader()) {
                    register_or_tournament_detail_button.setText("Tournament Detail");
                    register_or_tournament_detail_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), TournamentDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("TOURNAMENT ID", tournament_id);
                            intent.putExtra("BUNDLE", bundle);
                            startActivity(intent);
                        }
                    });
                } else {
                    register_or_tournament_detail_button.setText("Register");
                    register_or_tournament_detail_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialogRegisterTournament.show();
                        }
                    });
                }
//                doShowRegisterOrTournamentDetailButton(tournamentContentResponse.getAlreadyRegister() || (tournamentContentResponse.getRegisterOpen() && tournamentContentResponse.getLeader()));

                doShowScreen("Success");
            }

            @Override
            public void doFail(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                doShowScreen("Error");
            }

            @Override
            public void doConnectionError(int message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                doShowScreen("Error");
            }
        });
        instanceRegisterTournamentPresenter = new TournamentPresenter(new iPresenterResponse() {
            @Override
            public void doSuccess(Response response) {
                progress.dismiss();

                // Setting onClickListener on register_button
                register_or_tournament_detail_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), TournamentDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("TOURNAMENT ID", tournament_id);
                        intent.putExtra("BUNDLE", bundle);
                        startActivity(intent);
                    }
                });
                register_or_tournament_detail_button.setText("Tournament Detail");
//                doShowRegisterOrTournamentDetailButton(true);

                Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void doFail(String message) {
                progress.dismiss();

                if (message.equals("Not Authorize!")) {
                    // If not authorize destroy this activity and call authentication activity
                    Intent intent = new Intent(getContext(), AuthActivity.class);
                    startActivity(intent);
                }

                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void doConnectionError(int message) {
                progress.dismiss();
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        // Setting onClickListener to ScreenErrorContainer
        ScreenErrorContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGetTournamentContent();
            }
        });

        // Get Tournament Content Data
        doGetTournamentContent();

        return _view;
    }

    public void doShowScreen(String whichScreen) {
        if (whichScreen.equals("Error")) {
            // Setting action bar title
            ((TournamentContentActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((TournamentContentActivity) getActivity()).getSupportActionBar().setTitle("Error");

            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenTournamentContent.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Loading")) {
            // Setting action bar title
            ((TournamentContentActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((TournamentContentActivity) getActivity()).getSupportActionBar().setTitle("Loading...");

            ScreenErrorContainer.setVisibility(View.GONE);
            ScreenTournamentContent.setVisibility(View.GONE);
            ScreenLoadingContainer.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Success")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.GONE);
            ScreenTournamentContent.setVisibility(View.VISIBLE);
        }
    }

    public void doGetTournamentContent() {
        doShowScreen("Loading");

        String token_type = null;
        String token = null;
        if (instanceSessionManager.isUserLoggedIn()) {
            token_type = instanceSessionManager.getTokenLoggedIn().getToken_type();
            token = instanceSessionManager.getTokenLoggedIn().getAccess_token();
        }
        instanceTournamentPresenter.get_tournament_content(token_type, token, tournament_id);
    }

    public void doShowRegisterOrTournamentDetailButton(Boolean show) {
        if (show) {
            register_or_tournament_detail_button.setVisibility(View.VISIBLE);
        } else {
            register_or_tournament_detail_button.setVisibility(View.GONE);
        }
    }
}
