package mercy.tourney.View.ManageTeam;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import mercy.tourney.Model.SessionManager;
import mercy.tourney.R;

public class ManageTeamActivity extends AppCompatActivity {
    private Boolean mustRefreshTeamDetail = false;
    private Boolean mustRefreshTeamMember = true;

    /**
     * Declare Component in XML Layout
     */
    ViewPager manage_team_view_pager;
    TabLayout manage_team_tab_layout;

    /**
     * Declare Variable for Instance Object
     */
    SessionManager instanceSessionManager;
    ManageTeamFragmentPagerAdapter manageTeamFragmentPagerAdapter;
    TeamDetailFragment teamDetailFragment;
    TeamMemberFragment teamMemberFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_team);

        // Set Action Bar Title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Manage Team");

        // Initialize Component from View
        manage_team_view_pager = (ViewPager) findViewById(R.id.ViewPagerManageTeam);
        manage_team_tab_layout = (TabLayout) findViewById(R.id.TabLayoutManageTeam);

        // Initialize Instance Object
        instanceSessionManager = new SessionManager(getApplicationContext());
        teamDetailFragment = new TeamDetailFragment();
        teamMemberFragment = new TeamMemberFragment();
        manageTeamFragmentPagerAdapter = new ManageTeamFragmentPagerAdapter(getSupportFragmentManager(), ManageTeamActivity.this, teamDetailFragment, teamMemberFragment);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        manage_team_view_pager.setAdapter(manageTeamFragmentPagerAdapter);

        // Set On Page Change Listener to manage_team_view_pager
        manage_team_view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    if (mustRefreshTeamDetail) {
                        teamDetailFragment.doGetTeamDetail();
                        setMustRefreshTeamDetail(false);
                    }
                } else if (position == 1) {
                    if (mustRefreshTeamMember) {
                        teamMemberFragment.doGetTeamMember();
                        setMustRefreshTeamMember(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Give the TabLayout the ViewPager
        manage_team_tab_layout.setupWithViewPager(manage_team_view_pager);
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

    public void setMustRefreshTeamDetail(Boolean mustRefreshTeamDetail) {
        this.mustRefreshTeamDetail = mustRefreshTeamDetail;
    }

    public void setMustRefreshTeamMember(Boolean mustRefreshTeamMember) {
        this.mustRefreshTeamMember = mustRefreshTeamMember;
    }
}
