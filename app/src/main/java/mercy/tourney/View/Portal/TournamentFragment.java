package mercy.tourney.View.Portal;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import mercy.tourney.Model.Response.TournamentResponse;
import mercy.tourney.Model.SessionManager;
import mercy.tourney.Presenter.Portal.TournamentPresenter;
import mercy.tourney.Presenter.iPresenterResponse;
import mercy.tourney.R;

/**
 * Created by Andrianto on 12/16/2016.
 */
public class TournamentFragment extends Fragment {
    private String keyword;
    private Integer game_category;
    private Boolean FLAG_TEAM;

    /**
     * Declare Component in XML Layout
     */
    RelativeLayout ScreenLoadingContainer, ScreenErrorContainer;
    SwipeRefreshLayout tournament_swipe_refresh_layout, tournament_null_swipe_refresh_layout;
    RecyclerView tournament_recycler_view;

    /**
     * Declare Variable for Instance Object
     */
    SessionManager instanceSessionManager;
    TournamentPresenter instanceTournamentPresenter;

    public static TournamentFragment newInstance(@Nullable String keyword, @Nullable Integer game_category, Boolean FLAG_TEAM) {
        Bundle args = new Bundle();
        if (keyword != null) {
            args.putString("KEYWORD", keyword);
        }
        if (game_category != null) {
            args.putInt("GAME CATEGORY", game_category);
        }
        args.putBoolean("FLAG TEAM", FLAG_TEAM);

        TournamentFragment fragment = new TournamentFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        keyword = null;
        game_category = null;
        FLAG_TEAM = false;

        Bundle args = getArguments();
        if (args != null) {
            keyword = args.getString("KEYWORD");
            game_category = args.getInt("GAME CATEGORY");
            FLAG_TEAM = args.getBoolean("FLAG TEAM");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_tournament, container, false);

        // Initialize Component from View
        ScreenLoadingContainer = (RelativeLayout) _view.findViewById(R.id.ScreenLoadingContainer);
        ScreenErrorContainer = (RelativeLayout) _view.findViewById(R.id.ScreenErrorContainer);
        tournament_swipe_refresh_layout = (SwipeRefreshLayout) _view.findViewById(R.id.SwipeRefreshLayoutTournament);
        tournament_null_swipe_refresh_layout = (SwipeRefreshLayout) _view.findViewById(R.id.SwipeRefreshLayoutTournamentNull);
        tournament_recycler_view = (RecyclerView) _view.findViewById(R.id.RecyclerViewTournament);

        // Initialize Instance Object
        instanceSessionManager = new SessionManager(getContext());
        instanceTournamentPresenter = new TournamentPresenter(new iPresenterResponse() {
            @Override
            public void doSuccess(Response response) {
                tournament_recycler_view.setAdapter(new TournamentAdapter(((TournamentResponse) response).getTournaments()));

                tournament_swipe_refresh_layout.setRefreshing(false);
                tournament_null_swipe_refresh_layout.setRefreshing(false);

                if (((TournamentResponse) response).getTournaments() == null) {
                    doShowScreen("Success But Null");
                } else {
                    doShowScreen("Success");
                }
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

        // Configure SwipeRefreshLayout
        tournament_swipe_refresh_layout.setColorSchemeColors(getResources().getColor(R.color.colorSelectedTextPager));
        tournament_null_swipe_refresh_layout.setColorSchemeColors(getResources().getColor(R.color.colorSelectedTextPager));
        tournament_swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRetrofitGetTournament();
            }
        });
        tournament_null_swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRetrofitGetTournament();
            }
        });

        // Set Click Listener to ScreenErrorContainer
        ScreenErrorContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGetTournament();
            }
        });

        // Configure Recyclerview
        tournament_recycler_view.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tournament_recycler_view.setLayoutManager(linearLayoutManager);

        doGetTournament();

        return _view;
    }

    public void doShowScreen(String whichScreen) {
        if (whichScreen.equals("Error")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            tournament_swipe_refresh_layout.setVisibility(View.GONE);
            tournament_null_swipe_refresh_layout.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Loading")) {
            ScreenErrorContainer.setVisibility(View.GONE);
            tournament_swipe_refresh_layout.setVisibility(View.GONE);
            tournament_null_swipe_refresh_layout.setVisibility(View.GONE);
            ScreenLoadingContainer.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Success")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.GONE);
            tournament_null_swipe_refresh_layout.setVisibility(View.GONE);
            tournament_swipe_refresh_layout.setVisibility(View.VISIBLE);
        } else if (whichScreen.equals("Success But Null")) {
            ScreenLoadingContainer.setVisibility(View.GONE);
            ScreenErrorContainer.setVisibility(View.GONE);
            tournament_swipe_refresh_layout.setVisibility(View.GONE);
            tournament_null_swipe_refresh_layout.setVisibility(View.VISIBLE);
        }
    }

    public void doGetTournament() {
        doShowScreen("Loading");
        doRetrofitGetTournament();
    }

    public void doRetrofitGetTournament() {
        if (FLAG_TEAM) {
            instanceTournamentPresenter.get_team_tournament(instanceSessionManager.getTokenLoggedIn().getToken_type(), instanceSessionManager.getTokenLoggedIn().getAccess_token());
        } else {
            instanceTournamentPresenter.get_tournaments(keyword, game_category);
        }
    }
}
