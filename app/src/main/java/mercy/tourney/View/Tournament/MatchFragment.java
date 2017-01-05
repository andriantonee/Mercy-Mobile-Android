package mercy.tourney.View.Tournament;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

import mercy.tourney.Model.Basic.Match;
import mercy.tourney.R;

/**
 * Created by Andrianto on 12/19/2016.
 */
public class MatchFragment extends Fragment {
    private List<Match> match;

    /**
     * Declare Component in XML Layout
     */
    RecyclerView match_recycler_view;

    public static MatchFragment newInstance(List<Match> match) {
        Bundle args = new Bundle();
        args.putSerializable("MATCH", (Serializable) match);
        MatchFragment fragment = new MatchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            match = (List<Match>) bundle.getSerializable("MATCH");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_match, container, false);

        // Initialize Component from View
        match_recycler_view = (RecyclerView) _view.findViewById(R.id.RecyclerViewMatch);

        // Configure Recyclerview
        match_recycler_view.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        match_recycler_view.setLayoutManager(linearLayoutManager);

        // Set Adapter to RecyclerView
        match_recycler_view.setAdapter(new MatchAdapter(match));

        return _view;
    }
}
