package mercy.tourney.View.Portal;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import mercy.tourney.Model.Basic.User;
import mercy.tourney.Model.SessionManager;
import mercy.tourney.R;
import mercy.tourney.View.Authentication.AuthActivity;
import mercy.tourney.View.ManageAccount.ManageAccountActivity;
import mercy.tourney.View.ManageTeam.ManageTeamActivity;
import mercy.tourney.View.TeamInvitations.TeamInvitationActivity;

/**
 * Created by Andrianto on 11/26/2016.
 */
public class PortalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    /**
     * Declare Component in XML Layout
     */
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    View nav_header;
    TextView name_text_view, username_text_view;
    Menu nav_menu;

    /**
     * Declare Variable for Instance Object
     */
    SessionManager instanceSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);

        // Initialize Component from View
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        nav_header = navigationView.getHeaderView(0);
        name_text_view = (TextView) nav_header.findViewById(R.id.TextViewName);
        username_text_view = (TextView) nav_header.findViewById(R.id.TextViewUsername);
        nav_menu = navigationView.getMenu();

        // Initialize Instance Object
        instanceSessionManager = new SessionManager(getApplicationContext());

        // Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mercy - Portal");

        // 3 Stick Menu Button
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // 3 Stick Menu Container
        navigationView.setNavigationItemSelectedListener(this);

        // Add Fragment to PortalContainer
        getSupportFragmentManager().beginTransaction().add(R.id.PortalContainer, TournamentFragment.newInstance(null, null, false)).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.portal, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchActionBarItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchActionBarItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        } else if (id == R.id.filter) {
            (new FilterTournamentDialogFragment()).show(getSupportFragmentManager(), "Filter");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_authentication) {
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage_account) {
            Intent intent = new Intent(this, ManageAccountActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage_team) {
            Intent intent = new Intent(this, ManageTeamActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_team_invitations) {
            Intent intent = new Intent(this, TeamInvitationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            instanceSessionManager.doClearSession();
            doUserLogin(instanceSessionManager.isUserLoggedIn());
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        doUserLogin(instanceSessionManager.isUserLoggedIn());
    }

    public void doUserLogin(Boolean isLogin) {
        if (isLogin) {
            User user = instanceSessionManager.getUserLoggedIn();
            name_text_view.setText(user.getFirst_name().concat(TextUtils.isEmpty(user.getLast_name()) ? "" : " ".concat(user.getLast_name())));
            username_text_view.setText(user.getUsername().concat(" / +".concat(user.getPhone())));
        } else {
            name_text_view.setText("Guest");
            username_text_view.setText("Login your account");
        }

        nav_menu.setGroupVisible(R.id.group_authentication, !isLogin);
        nav_menu.setGroupVisible(R.id.group_personal, isLogin);
        nav_menu.setGroupVisible(R.id.group_logout, isLogin);
    }
}
