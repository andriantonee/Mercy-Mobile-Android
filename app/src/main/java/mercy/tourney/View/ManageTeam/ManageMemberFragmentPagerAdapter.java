package mercy.tourney.View.ManageTeam;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Andrianto on 12/12/2016.
 */
public class ManageMemberFragmentPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"INVITE MEMBER", "MEMBER PENDING"};
    private Context context;
    private InviteMemberFragment inviteMemberFragment;
    private InviteMemberPendingFragment inviteMemberPendingFragment;

    public ManageMemberFragmentPagerAdapter(FragmentManager fm, Context context, InviteMemberFragment inviteMemberFragment, InviteMemberPendingFragment inviteMemberPendingFragment) {
        super(fm);
        this.context = context;
        this.inviteMemberFragment = inviteMemberFragment;
        this.inviteMemberPendingFragment = inviteMemberPendingFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return inviteMemberFragment;
        } else if (position == 1) {
            return inviteMemberPendingFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
