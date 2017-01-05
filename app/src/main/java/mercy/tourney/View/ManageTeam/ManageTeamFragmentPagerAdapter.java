package mercy.tourney.View.ManageTeam;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Andrianto on 12/4/2016.
 */
public class ManageTeamFragmentPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"TEAM DETAIL", "TEAM MEMBER"};
    private Context context;
    private TeamDetailFragment teamDetailFragment;
    private TeamMemberFragment teamMemberFragment;

    public ManageTeamFragmentPagerAdapter(FragmentManager fm, Context context, TeamDetailFragment teamDetailFragment, TeamMemberFragment teamMemberFragment) {
        super(fm);
        this.context = context;
        this.teamDetailFragment = teamDetailFragment;
        this.teamMemberFragment = teamMemberFragment;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return teamDetailFragment;
        } else if (position == 1) {
            return teamMemberFragment;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
