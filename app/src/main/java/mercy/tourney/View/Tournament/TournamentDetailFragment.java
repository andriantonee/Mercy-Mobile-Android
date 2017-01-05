package mercy.tourney.View.Tournament;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import mercy.tourney.Model.Basic.Response;
import mercy.tourney.Model.Response.TournamentDetailResponse;
import mercy.tourney.Model.SessionManager;
import mercy.tourney.Presenter.Portal.TournamentPresenter;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;

/**
 * Created by Andrianto on 12/18/2016.
 */
public class TournamentDetailFragment extends Fragment {
    private Integer tournament_id;

    /**
     * Declare Component in XML Layout
     */
    RelativeLayout ScreenLoadingContainer, ScreenErrorContainer, ScreenTournamentDetailNull;
    LinearLayout ScreenTournamentDetail;
    ViewPager tournament_detail_view_pager;
    TabLayout tournament_detail_tab_layout;

    /**
     * Declare Variable for Instance Object
     */
    SessionManager instanceSessionManager;
    TournamentPresenter instanceTournamentDetailPresenter;

    public static TournamentDetailFragment newInstance(Integer tournament_id) {
        Bundle args = new Bundle();
        args.putInt("TOURNAMENT ID", tournament_id);
        TournamentDetailFragment fragment = new TournamentDetailFragment();
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
        View _view = inflater.inflate(R.layout.fragment_tournament_detail, container, false);

        // Initialize Component from View
        ScreenLoadingContainer = (RelativeLayout) _view.findViewById(R.id.ScreenLoadingContainer);
        ScreenErrorContainer = (RelativeLayout) _view.findViewById(R.id.ScreenErrorContainer);
        ScreenTournamentDetailNull = (RelativeLayout) _view.findViewById(R.id.ScreenTournamentDetailNull);
        ScreenTournamentDetail = (LinearLayout) _view.findViewById(R.id.ScreenTournamentDetail);
        tournament_detail_view_pager = (ViewPager) _view.findViewById(R.id.ViewPagerTournamentDetail);
        tournament_detail_tab_layout = (TabLayout) _view.findViewById(R.id.TabLayoutTournamentDetail);

        // Initialize Instance Object
        instanceSessionManager = new SessionManager(getContext());
        instanceTournamentDetailPresenter = new TournamentPresenter(new iPresenterResponse() {
            @Override
            public void doSuccess(Response response) {
                TournamentDetailResponse tournamentDetailResponse = (TournamentDetailResponse) response;

                // Setting action bar title
                ((TournamentDetailActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ((TournamentDetailActivity) getActivity()).getSupportActionBar().setTitle(tournamentDetailResponse.getTournament_name());

//                if (tournamentDetailResponse.getMatch_round().size() <= 3) {
//                    tournament_detail_tab_layout.setTabMode(TabLayout.MODE_FIXED);
//                } else {
//                    tournament_detail_tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
//                }

                // Get the ViewPager and set it's PagerAdapter so that it can display items
                tournament_detail_view_pager.setAdapter(new TournamentDetailFragmentPagerAdapter(getFragmentManager(), tournamentDetailResponse.getMatch(), tournamentDetailResponse.getMatch_round()));

                if (!tournamentDetailResponse.getMatch().isEmpty() && !tournamentDetailResponse.getMatch_round().isEmpty()) {
                    doShowScreen("Success");
                } else {
                    doShowScreen("Success But Null");
                }
            }

            @Override
            public void doFail(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                doShowScreen("Error");

//                if (message.equals("Not Authorize!")) {
//                    // If not authorize destroy this activity and call authentication activity
//                    Intent intent = new Intent(getContext(), AuthActivity.class);
//                    startActivity(intent);
//                    getActivity().finish();
//                }
            }

            @Override
            public void doConnectionError(int message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                doShowScreen("Error");
            }
        });

        // Give the TabLayout the ViewPager
        tournament_detail_tab_layout.setupWithViewPager(tournament_detail_view_pager);

        // Setting onClickListener to ScreenErrorContainer
        ScreenErrorContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGetTournamentDetail();
            }
        });

        // Get Tournament Content Data
        doGetTournamentDetail();

        return _view;
    }

    public void doShowScreen(String whichScreen) {
        if (whichScreen.equals("Error")) {
            // Setting action bar title
            ((TournamentDetailActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((TournamentDetailActivity) getActivity()).getSupportActionBar().setTitle("Error");

            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenTournamentDetail.setVisibility(View.GONE);
            ScreenTournamentDetailNull.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Loading")) {
            // Setting action bar title
            ((TournamentDetailActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((TournamentDetailActivity) getActivity()).getSupportActionBar().setTitle("Loading...");

            ScreenErrorContainer.setVisibility(View.GONE);
            ScreenTournamentDetail.setVisibility(View.GONE);
            ScreenTournamentDetailNull.setVisibility(View.GONE);
            ScreenLoadingContainer.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Success")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.GONE);
            ScreenTournamentDetailNull.setVisibility(View.GONE);
            ScreenTournamentDetail.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Success But Null")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.GONE);
            ScreenTournamentDetail.setVisibility(View.GONE);
            ScreenTournamentDetailNull.setVisibility(View.VISIBLE);
        }
    }

    public void doGetTournamentDetail() {
        doShowScreen("Loading");

        String token_type = null;
        String token = null;
        if (instanceSessionManager.isUserLoggedIn()) {
            token_type = instanceSessionManager.getTokenLoggedIn().getToken_type();
            token = instanceSessionManager.getTokenLoggedIn().getAccess_token();
        }

        instanceTournamentDetailPresenter.get_tournament_detail(token_type, token, tournament_id);
    }
}
