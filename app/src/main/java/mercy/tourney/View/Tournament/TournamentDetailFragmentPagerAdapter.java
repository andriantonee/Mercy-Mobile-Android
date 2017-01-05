package mercy.tourney.View.Tournament;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mercy.tourney.Model.Basic.Match;

/**
 * Created by Andrianto on 12/19/2016.
 */
public class TournamentDetailFragmentPagerAdapter extends FragmentPagerAdapter {
    private String match_round[];
    private List<List<Match>> match;
    private Context context;

    public TournamentDetailFragmentPagerAdapter(FragmentManager fm, Map<Integer, List<Match>> match, Map<Integer, String> match_round) {
        super(fm);

        this.match_round = new String[match_round.size()];
        this.match = new ArrayList<>();

        Integer index = 0;
        for (Map.Entry<Integer, String> entry : match_round.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();

            this.match_round[index] = value;
            this.match.add(index, match.get(key));

            index++;
        }
    }

    @Override
    public int getCount() {
        return match_round.length;
    }

    @Override
    public Fragment getItem(int position) {
        return MatchFragment.newInstance(match.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return match_round[position];
    }
}
