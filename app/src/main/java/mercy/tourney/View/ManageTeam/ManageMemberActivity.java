package mercy.tourney.View.ManageTeam;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mercy.tourney.Model.SessionManager;
import mercy.tourney.R;

public class ManageMemberActivity extends AppCompatActivity {
    private Boolean mustRefreshInviteMember = false;
    private Boolean mustRefreshInviteMemberPending = true;

    /**
     * Declare Component in XML Layout
     */
    ViewPager manage_member_view_pager;
    TabLayout manage_member_tab_layout;

    /**
     * Declare Variable for Instance Object
     */
    SessionManager instanceSessionManager;
    ManageMemberFragmentPagerAdapter instanceManageMemberFragmentPagerAdapter;
    InviteMemberFragment instanceInviteMemberFragment;
    InviteMemberPendingFragment instanceInviteMemberPendingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_member);

        // Set Action Bar Title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Manage Member");

        // Initialize Component from View
        manage_member_view_pager = (ViewPager) findViewById(R.id.ViewPagerManageMember);
        manage_member_tab_layout = (TabLayout) findViewById(R.id.TabLayoutManageMember);

        // Initialize Instance Object
        instanceSessionManager = new SessionManager(getApplicationContext());
        instanceInviteMemberFragment = new InviteMemberFragment();
        instanceInviteMemberPendingFragment = new InviteMemberPendingFragment();
        instanceManageMemberFragmentPagerAdapter = new ManageMemberFragmentPagerAdapter(getSupportFragmentManager(), ManageMemberActivity.this, instanceInviteMemberFragment, instanceInviteMemberPendingFragment);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        manage_member_view_pager.setAdapter(instanceManageMemberFragmentPagerAdapter);

        // Set On Page Change Listener to manage_team_view_pager
        manage_member_view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    if (mustRefreshInviteMember) {
                        setMustRefreshInviteMember(false);
                    }
                } else if (position == 1) {
                    if (mustRefreshInviteMemberPending) {
                        instanceInviteMemberPendingFragment.doGetPendingMember();
                        setMustRefreshInviteMemberPending(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Give the TabLayout the ViewPager
        manage_member_tab_layout.setupWithViewPager(manage_member_view_pager);
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

    public String get_token_type() {
        return instanceSessionManager.getTokenLoggedIn().getToken_type();
    }

    public String get_access_token() {
        return instanceSessionManager.getTokenLoggedIn().getAccess_token();
    }

    public void setMustRefreshInviteMember(Boolean mustRefreshInviteMember) {
        this.mustRefreshInviteMember = mustRefreshInviteMember;
    }

    public void setMustRefreshInviteMemberPending(Boolean mustRefreshInviteMemberPending) {
        this.mustRefreshInviteMemberPending = mustRefreshInviteMemberPending;
    }
}
